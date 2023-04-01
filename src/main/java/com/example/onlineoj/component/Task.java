package com.example.onlineoj.component;

import com.example.onlineoj.model.Answer;
import com.example.onlineoj.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class Task {
    private final Logger logger= LoggerFactory.getLogger(Task.class);
    private final Command command;
    private final FileUtil fileUtil;
    private final String WORK_DIR;
    private final String CLASS ;
    private final String CODE ;
    private final String COMPILE_ERROR ;
    private final String STDOUT ;
    private final String STDERR ;

    public Task(Command command, FileUtil fileUtil) {
        WORK_DIR = "./tmp/" + UUID.randomUUID() + "/";
        CLASS = "Solution";
        CODE = WORK_DIR + "Solution.java";
        COMPILE_ERROR = WORK_DIR + "compileError.txt";
        STDOUT = WORK_DIR + "stdout.txt";
        STDERR = WORK_DIR + "stderr.txt";
        this.command = command;
        this.fileUtil = fileUtil;
    }
    public Answer run(Question question){
        Answer answer=new Answer();
        File file=new File(WORK_DIR);
        if(!file.exists()){
            boolean create=file.mkdirs();
            if(!create){
                answer.setError(1);
                answer.setCompileErr("文件操作异常");
                return answer;
            }
        }
        // 代码写入Solution.java文件中
        fileUtil.writeFile(CODE, question.getCode());
        // 使用javac命令编译
        String compileCmd=String.format("javac -encoding utf8 %s -d %s", CODE, WORK_DIR);
        command.run(compileCmd,null,COMPILE_ERROR);
        String compileErrMsg=fileUtil.readFile(COMPILE_ERROR);
        if(compileErrMsg.length()>0){
            //error:1 代表编译错误
            logger.error(CODE+"\n error:1"+"\n"+compileErrMsg);
            answer.setError(1);
            answer.setCompileErr(compileErrMsg);
            return answer;
        }
        // 使用java命令运行
        String runCmd = String.format("java -classpath %s %s", WORK_DIR, CLASS);
        command.run(runCmd,STDOUT,STDERR);
        String runErrMsg=fileUtil.readFile(STDERR);
        if(runErrMsg.length()>0){
            // error 2代表运行错误，可能异常 堆栈溢出 等
            logger.error(CODE+"\n error:2"+"\n"+runErrMsg);
            answer.setError(2);
            answer.setStderr(runErrMsg);
            return answer;
        }
        answer.setError(0);
        answer.setStdout(fileUtil.readFile(STDOUT));
        return answer;
    }

    public boolean checkCodeSafe(String code) {
        List<String> blackList = new ArrayList<>();
        // 防止提交的代码运行恶意程序
        blackList.add("Runtime");
        blackList.add("exec");
        // 禁止提交的代码读写文件
        blackList.add("java.io");
        // 禁止提交的代码访问网络
        blackList.add("java.net");
        for (String target : blackList) {
            int pos = code.indexOf(target);
            if (pos >= 0) {
                // 找到任意的恶意代码特征, 返回 false 表示不安全
                return false;
            }
        }
        return true;
    }
}

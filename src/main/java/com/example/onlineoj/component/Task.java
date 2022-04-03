package com.example.onlineoj.component;

import com.example.onlineoj.model.Answer;
import com.example.onlineoj.model.Question;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.UUID;


@Component
public class Task {
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
            file.mkdirs();
        }
        fileUtil.writeFile(CODE, question.getCode());
        String compileCmd=String.format("javac -encoding utf8 %s -d %s", CODE, WORK_DIR);
        System.out.println(compileCmd);
        command.run(compileCmd,null,COMPILE_ERROR);
        String content=fileUtil.readFile(COMPILE_ERROR);
        if(content.length()>0){
            System.out.println("编译出错!");
            answer.setError(1);
            answer.setReason(content);
            return answer;
        }
        String runCmd = String.format("java -classpath %s %s", WORK_DIR, CLASS);
        System.out.println(runCmd);
        command.run(runCmd,STDOUT,STDERR);
        String content1=fileUtil.readFile(STDERR);
        if(content1.length()>0){
            answer.setError(2);
            answer.setStderr(content1);
            return answer;
        }
        answer.setError(0);
        answer.setStdout(fileUtil.readFile(STDOUT));
        return answer;
    }
}

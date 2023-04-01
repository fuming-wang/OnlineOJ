package com.example.onlineoj.controller;


import com.example.onlineoj.component.FileUtil;
import com.example.onlineoj.component.Sort;
import com.example.onlineoj.component.Task;
import com.example.onlineoj.dao.ProblemMapper;
import com.example.onlineoj.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ProblemController {
    private final ProblemMapper problemMapper;
    private final Task task;
    private final FileUtil fileUtil;

    private final Sort sort;

    public ProblemController(ProblemMapper problemMapper, Task task, FileUtil fileUtil, Sort sort) {
        this.problemMapper = problemMapper;
        this.task = task;
        this.fileUtil = fileUtil;
        this.sort = sort;
    }
    // 通过读文件修改一个题目，需要提前修改题目信息
    @ResponseBody
    @RequestMapping("/user/change")
    public Object updateProblem(Integer id){
        if(id==null) {
            return "id错误";
        }
        Problem problem=fileUtil.getProblemFromFile(id);
        if(problem==null){
            return "确认文件是否存在";
        }
        problem.setId(id);
        int ret=problemMapper.update(problem);
        if(ret>0){
            return "成功,请点击回退";
        }else{
            return "失败,请点击回退";
        }
    }
    // 通过读文件来上传一下新的题目
    @ResponseBody
    @RequestMapping("/user/submit")
    public Object addProblem(Integer id){
        if(id==null) {
            return "id错误,请返回重试";
        }
        Problem isAdmitUpload=problemMapper.selectOne(id);
        if(isAdmitUpload!=null){
            return "该题目已经存在，如果需要请点击修改";
        }
        Problem problem=fileUtil.getProblemFromFile(id);
        if(problem==null){
            return "确认文件是否存在";
        }
        int ret=problemMapper.add(problem);
        if(ret>0){
            return "成功上传一个题目";
        }else{
            return "失败,原因未知";
        }
    }
    // 查看题目，id为空查看题目列表，确定数字查看具体的题目
    @ResponseBody
    @RequestMapping("/problem")
    public Object getAll(Integer id){
        if(id!=null){
            Problem problem = problemMapper.selectOne(id);
            String code="//已自动为你导入 import java.util.* 如需需要其他，请自行导入\n"+problem.getTemplateCode();
            problem.setTemplateCode(code);
            return problem;
        }else{
            return problemMapper.selectAll();
        }
    }
    // 题目的编译运行模块
    @ResponseBody
    @RequestMapping("/compile")
    public Object compile(@RequestBody CompileRequest compileRequest){
        CompileResponse compileResponse=new CompileResponse();
        try{
            // 获取题目的其他代码信息，以便进行代码拼接
            Problem problem=problemMapper.selectOneInDetail(compileRequest.id);
            if(problem==null){
                compileResponse.error = 3;
                compileResponse.reason = "没有找到指定的题目! id=" + compileRequest.id;
                throw new Exception("未查询到题目");
            }
            String testCode = problem.getTestCode();
            String requestCode = compileRequest.code;
            if(!task.checkCodeSafe(requestCode)){
                compileResponse.error = 3;
                compileResponse.reason = "提交代码存在非法控制代码,终止运行,请输入题解相关代码";
                throw new Exception("恶意代码");
            }
            String finalCode = mergeCode(requestCode, testCode);
            if (finalCode == null) {
                compileResponse.error = 3;
                compileResponse.reason = "提交的代码不符合要求!";
                throw new Exception();
            }
            Question question = new Question();
            question.setCode(finalCode);
            // 代码的编译运行命令
            Answer answer = task.run(question);
            compileResponse.error = answer.getError();
            if(compileResponse.error==0){
                //正常运行
                int index1 = answer.getStdout().lastIndexOf("is ");
                int index2 = answer.getStdout().indexOf(" ms");
                System.out.println("index1="+index1+" index2 = " + index2);
                if(index1==-1||index2==-1){
                    //这里是没有代码编译错误、没有运行错误。
                    //可能结果是代码的结果错误或者超时被杀死
                    //如果是结果错误，我们约定打印Last Input。如果是超时，则什么也没有
                    int tmp = answer.getStdout().lastIndexOf("Last Input");
                    if(tmp>=0){
                        compileResponse.stdout = answer.getStdout();
                    }else{
                        compileResponse.stdout = "程序超时,请优化时间复杂度或者检测死循环";
                    }
                }else{
                    System.out.println(answer.getStdout().substring(index1+3,index2));
                    String s1 = answer.getStdout().substring(index1+3,index2);
                    Integer t = Integer.valueOf(s1);
                    System.out.println("t = "+t);
                    String s = sort.operation(compileRequest.id,t);
                    System.out.println("s = " + s);
                    compileResponse.stdout=answer.getStdout() +"\n" + s;
                }
            }else if(compileResponse.error==1){
                //编译错误
                compileResponse.reason=answer.getCompileErr();
            }else{
                //运行错误
                compileResponse.reason=answer.getStderr();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return compileResponse;
    }

    private String mergeCode(String requestCode, String testCode) {
        String preCode="import java.util.*;\n";
        int pos = requestCode.lastIndexOf("}");
        if (pos == -1) {
            return null;
        }
        String subStr = requestCode.substring(0, pos);
        return preCode+subStr + testCode + "\n}";
    }
}

package com.example.onlineoj.controller;


import com.example.onlineoj.component.FileUtil;
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
    public ProblemController(ProblemMapper problemMapper, Task task, FileUtil fileUtil) {
        this.problemMapper = problemMapper;
        this.task = task;
        this.fileUtil = fileUtil;
    }
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
    @ResponseBody
    @RequestMapping("/compile")
    public Object compile(@RequestBody CompileRequest compileRequest){
        CompileResponse compileResponse=new CompileResponse();
        try{
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
            Answer answer = task.run(question);
            compileResponse.error = answer.getError();
            if(compileResponse.error==0){
                compileResponse.stdout=answer.getStdout();
            }else if(compileResponse.error==1){
                compileResponse.reason=answer.getCompileErr();
            }else{
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

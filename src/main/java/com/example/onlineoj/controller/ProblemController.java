package com.example.onlineoj.controller;


import com.example.onlineoj.component.Task;
import com.example.onlineoj.dao.ProblemMapper;
import com.example.onlineoj.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ProblemController {
    private final ProblemMapper problemMapper;
    private final Task task;

    @Autowired
    public ProblemController(ProblemMapper problemMapper, Task task) {
        this.problemMapper = problemMapper;
        this.task = task;
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
            String finalCode = mergeCode(requestCode, testCode);
            if (finalCode == null) {
                compileResponse.error = 3;
                compileResponse.reason = "提交的代码不符合要求!";
                throw new Exception();
            }
            Question question = new Question();
            question.setCode(finalCode);
            Answer answer = task.run(question);
            System.out.println(answer);
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

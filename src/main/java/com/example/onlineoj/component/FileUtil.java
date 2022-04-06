package com.example.onlineoj.component;


import com.example.onlineoj.model.Problem;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FileUtil {
    public Problem getProblemFromFile(int id){
        String path="D:\\Github\\OnlineOJ\\problemMsg\\"+id+"\\";
        File file=new File(path);
        if(!file.exists()){
            return null;
        }
        Problem problem=new Problem();
        String title=readFile(path+"title.txt");
        String level=readFile(path+"level.txt");
        String description=readFile(path+"description.txt");
        String templateCode=readFile(path+"templateCode.txt");
        String testCode=readFile(path+"testCode.txt");
        problem.setTitle(title);
        problem.setLevel(level);
        problem.setDescription(description);
        problem.setTemplateCode(templateCode);
        problem.setTestCode(testCode);
        return problem;
    }
    public String readFile(String path){
        StringBuilder sb=new StringBuilder();
        try (FileReader fileReader=new FileReader(path)){
            while(true){
                int ch=fileReader.read();
                if(ch!=-1){
                    sb.append((char)ch);
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public void writeFile(String filePath, String content) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

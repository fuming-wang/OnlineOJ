package com.example.onlineoj.component;


import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FileUtil {

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

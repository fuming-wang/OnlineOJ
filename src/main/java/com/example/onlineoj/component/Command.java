package com.example.onlineoj.component;


import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class Command {
    public int run(String cmd,String stdoutFile,String stderrFile){
        try {
            Process process=Runtime.getRuntime().exec(cmd);
            if (stdoutFile != null) {
                try(InputStream stdoutFrom = process.getInputStream();
                    FileOutputStream stdoutTo = new FileOutputStream(stdoutFile)){
                    while (true) {
                        int ch = stdoutFrom.read();
                        if (ch == -1) {
                            break;
                        }
                        stdoutTo.write(ch);
                    }
                }
            }
            if (stderrFile != null) {
                try(InputStream stderrFrom = process.getErrorStream();
                    FileOutputStream stderrTo = new FileOutputStream(stderrFile)){
                    while (true) {
                        int ch = stderrFrom.read();
                        if (ch == -1) {
                            break;
                        }
                        stderrTo.write(ch);
                    }
                }
            }
            return process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
}

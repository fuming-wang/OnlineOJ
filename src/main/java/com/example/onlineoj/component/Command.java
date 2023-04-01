package com.example.onlineoj.component;


import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class Command {

    static class MyThread extends Thread{
        private Process process = null;
        public MyThread(Process process){
            this.process = process;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
                System.out.println("线程程序休眠结束");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(process.isAlive()){
                System.out.println("程序超时，已被杀死");
                process.destroy();
            }
        }
    }
    public int run(String cmd,String stdoutFile,String stderrFile){
        try {
            Process process=Runtime.getRuntime().exec(cmd);
            MyThread t = new MyThread(process);
            t.start();
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

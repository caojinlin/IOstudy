package com.cjl.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

public class OldIOClient {

    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 8899);
        String fileName = "/Users/caojinlin/cjl/码农翻身/0.单元测试.mp4";
        FileInputStream fileInputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];

        long readCount;
        long total = 0 ;
        long startTime = System.currentTimeMillis();
        while ((readCount = fileInputStream.read(buffer)) >= 0) {
            total += readCount;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送总字节数:" + total + ",耗时: "+ (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        socket.close();
        fileInputStream.close();
    }
}

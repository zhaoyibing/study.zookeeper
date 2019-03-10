package com.xiaobing.basic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @see com.xiaobing.basic.socket.SocketServerTest1
 * @author zhaoyibing
 *
 */
public class SocketClientTest1 {

	public static void main(String[] args) throws UnknownHostException, IOException {
		int port = 55533;
		String host = "127.0.0.1";
		Socket socket = new Socket(host, port);
		
		String message = "hello 你好 遗忘在春风似";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(message.getBytes("UTF-8"));
		socket.shutdownOutput();
		
		InputStream inputStream = socket.getInputStream();
		byte[] bytes = new byte[1024];
	    int len;
	    StringBuilder sb = new StringBuilder();
	    while ((len = inputStream.read(bytes)) != -1) {
	      sb.append(new String(bytes, 0, len,"UTF-8"));
	    }
	    System.out.println("get message from server: " + sb);
		
	    inputStream.close();
	    outputStream.close();
		socket.close();
		
	}
}

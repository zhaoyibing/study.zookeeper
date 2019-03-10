package com.xiaobing.basic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket服务端 与 客户端通信, 服务端监听 55533 端口，
 * 客户端连接host 与 指定port ，向服务端发送信息，服务端反馈结果。
 * 
 * @see com.xiaobing.basic.socket.SocketClientTest1
 * @author zhaoyibing
 *
 */
public class SocketServerTest1 {

	public static void main(String[] args) throws IOException {
		int port = 55533;
		ServerSocket server = new ServerSocket(port);
		
		System.out.println("server 将一直等待连接的到来---");
		
		Socket socket = server.accept();
		
		InputStream inputStream = socket.getInputStream();
		byte[] bytes = new byte[1024];
		int len;
		StringBuilder sb = new StringBuilder();
		while((len = inputStream.read(bytes)) != -1) {
			sb.append(new String(bytes, 0, len, "UTF-8"));
		}
		System.out.println("get message from client:" + sb);

		OutputStream outputStream = socket.getOutputStream();
		outputStream.write("Hello Client,I get the message.".getBytes("UTF-8"));
		
		inputStream.close();
		outputStream.close();
		socket.close();
		server.close();
	}
}

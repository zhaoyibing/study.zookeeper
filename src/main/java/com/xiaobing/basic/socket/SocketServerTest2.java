package com.xiaobing.basic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @see com.xiaobing.basic.socket.SocketClientTest2
 * @author zhaoyibing
 *
 */
public class SocketServerTest2 {

	public static void main(String[] args) throws IOException {
		int port = 55533;
		ServerSocket server = new ServerSocket(port);
		
		System.out.println("server 将一直等待连接的到来---");
		
		Socket socket = server.accept();
		InputStream inputStream = socket.getInputStream();
		byte[] bytes;
		
		// 因为可以复用Socket且能判断长度，所以可以一个Socket用到底
		while (true) {
			// 首先读取两个字节表示的长度
			int first = inputStream.read();
			
			//如果读取的值为-1  说明到了流的末尾，Socket 已经被关闭了，此时将不能再去读取
			if(first == -1)
				break;
			
			int second = inputStream.read();
			int length = (first << 8) + second;
			//然后构造一个指定长的数组
			bytes = new byte[length];
			inputStream.read(bytes);
			System.out.println("get message from client:" + new String(bytes, "UTF-8"));
			
		}
		
		inputStream.close();
		socket.close();
		server.close();
	}
}

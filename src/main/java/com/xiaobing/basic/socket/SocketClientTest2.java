package com.xiaobing.basic.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @see com.xiaobing.basic.socket.SocketServerTest2
 * @author zhaoyibing
 *
 */
public class SocketClientTest2 {

	public static void main22(String[] args) throws UnsupportedEncodingException {
		//int len = 2*2*2*2*2*2*2*2;
		String message = "hello world";
		// 首先需要计算得知消息的长度
		byte[] sendBytes = message.getBytes("UTF-8");
		System.out.println(sendBytes.length >> 8);
		System.out.println(sendBytes.length);
	}
	
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		int port = 55533;
		String host = "127.0.0.1";
		Socket socket = new Socket(host, port);
		OutputStream outputStream = socket.getOutputStream();

		String message = "hello 你好 遗忘在春风似";
		// 首先需要计算得知消息的长度
		byte[] sendBytes = message.getBytes("UTF-8");
		// 然后将消息的长度优先发送出去
		outputStream.write(sendBytes.length >> 8);
		outputStream.write(sendBytes.length);

		// 然后将消息再次发送出去
		outputStream.write(sendBytes);
		outputStream.flush();

		// ==========此处重复发送一次，实际项目中为多个命名，此处只为展示用法
		message = "第二条消息";
		sendBytes = message.getBytes("UTF-8");
		outputStream.write(sendBytes.length >> 8);
		outputStream.write(sendBytes.length);
		outputStream.write(sendBytes);
		outputStream.flush();

		// ==========此处重复发送一次，实际项目中为多个命名，此处只为展示用法
		message = "the third message!";
		sendBytes = message.getBytes("UTF-8");
		outputStream.write(sendBytes.length >> 8);
		outputStream.write(sendBytes.length);
		outputStream.write(sendBytes);

		outputStream.close();
		socket.close();

	}
}

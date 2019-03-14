package com.xiaobing.redis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.CharBuffer;

public class RedisScoketTest2 {

	public static void main(String[] args) {
		// 定义redis服务端默认端口
		int port = 6379;

		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			// 创建tcp连接
			socket = new Socket("10.1.120.89", port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			// 传送info命令
			// 客户端向Redis服务器发送命令，以RESP整块字符串数组的形式
			
			out.println("*2\r\n$4\r\nauth\r\n$6\r\n123456\r\n");
			
			System.out.println("Redis command wat sent successfully.");

			// 接收服务器的回复
			CharBuffer response = CharBuffer.allocate(1024);
			int readedLen = in.read(response);
			String responseBody = response.flip().toString();

			// 输出服务器的回复
			System.out.println(responseBody);
			
			//out.println("*1\r\n$4\r\ninfo\r\n");
			out.println("*3\r\n$5\r\nSETNX\r\n$5\r\nmykey\r\n$7\r\nmyvalue\r\n");

			// 接收服务器的回复
			CharBuffer response2 = CharBuffer.allocate(1024);
			int readedLen2 = in.read(response2);
			String responseBody2 = response2.flip().toString();

			// 输出服务器的回复
			System.out.println(responseBody2);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 最后关闭相关的流
			if (out != null) {
				out.close();
				out = null;
			}

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				in = null;
			}

			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				socket = null;
			}
		}
	}
}

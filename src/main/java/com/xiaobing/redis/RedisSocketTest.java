package com.xiaobing.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * redis 使用socket连接测试
 * @author zhao_
 *
 */
public class RedisSocketTest {

	public static void main(String[] args) throws Exception {
		//String host = "192.168.0.150";
		String host = "10.1.120.89";
		Integer port = 6379;
		Socket socket = new Socket(host, port);
		
		socket.setSoTimeout(10000);
		//getResult(outputStream, inputStream, "*3\\r\\n$3\\r\\nSET\\r\\n$6\\r\\nsocket\\r\\n$10\\r\\nsocketTest\\r\\n");
		getResult(socket, "*2\r\n$4\r\nauth\r\n$6\r\n123456\r\n");
		
		//getResult(socket, "*3\\r\\n$5\\r\\nSETNX\\r\\n$5\\r\\nmykey\\r\\n$7\\r\\nmyvalue\\r\\n");

		socket.close();
		
	}
	

	
	public static void getResult(Socket socket, String command) throws Exception {
		//System.out.println("\r\nsend command : " + command.replaceAll("\\\\r\\\\n", " "));
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(command.getBytes(StandardCharsets.UTF_8));
		outputStream.flush();
		
		InputStream inputStream = socket.getInputStream();
		byte[] bytes = new byte[128];
        int length = inputStream.read(bytes);
        String result = new String(bytes, 0, length, StandardCharsets.UTF_8);
        while(result.length() > 0) {
        	int index = result.indexOf("\r\n");
        	
        	String currentStr = result.substring(0, index);
        	result = result.substring(index + 2);
        	if (currentStr.startsWith("+")) {
                System.err.println("+ status reply message：" + currentStr.substring(1));
            } else if (currentStr.startsWith("-")) {
                System.err.println("- error reply message：" + currentStr.substring(1));
            } else if (currentStr.startsWith(":")) {
                System.err.println(": integer reply message：" + currentStr.substring(1));
            } else if (currentStr.startsWith("$")) {
                System.err.println("$ bulk reply message：" + currentStr.substring(1));
            } else if (currentStr.startsWith("*")) {
                System.err.println("* multi bulk reply message：" + currentStr.substring(1));
            }else {
                System.err.println("other message : " + currentStr);
            }
        }
		
	}
	
	
}

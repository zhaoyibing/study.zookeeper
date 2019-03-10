package com.xiaobing.basic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class URLTest {

	
	public static void main(String[] args) throws Exception {
		URL baidu = new URL("http://www.baidu.com");
		URL url = new URL(baidu, "/index.html?username=tom#test");// ？表示参数，#表示锚点
		System.out.println(url.getProtocol());// 获取协议
		System.out.println(url.getHost());// 获取主机
		System.out.println(url.getPort());// 如果没有指定端口号，根据协议不同使用默认端口。此时getPort()方法的返回值为 -1
		System.out.println(url.getPath());// 获取文件路径
		System.out.println(url.getFile());// 文件名，包括文件路径+参数
		System.out.println(url.getRef());// 相对路径，就是锚点，即#号后面的内容
		System.out.println(url.getQuery());// 查询字符串，即参数

		System.out.println("--------------baidu.com content start --------------");
		InputStream is = baidu.openStream();
		InputStreamReader isr = new InputStreamReader(is,"UTF-8");
		BufferedReader br =new BufferedReader(isr);
		String data = br.readLine();// 读取数据
		while (data != null) {
			System.out.println(data);// 输出数据
			data = br.readLine();
		}
		br.close();
		isr.close();
		is.close();
		System.out.println("--------------baidu.com content end --------------");
	}
}

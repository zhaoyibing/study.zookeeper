package com.xiaobing.basic;

import java.net.InetAddress;

public class InetAddressTest {

	
	public static void main(String[] args) throws Exception {
		InetAddress address =InetAddress.getLocalHost();
		System.out.println(address.getHostName());
		System.out.println(address.getHostAddress());
		byte[] address2 = address.getAddress();
		System.out.println(address2);
		
		 InetAddress address3 =InetAddress.getByName("192.168.0.150");
		 System.out.println(address3.getHostName());
		 System.out.println(address3.getHostAddress());
		 
		
		
		
	}
}

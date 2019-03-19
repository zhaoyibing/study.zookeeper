package com.xiaobing.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * vm-args : -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @author e_gaoyunc
 * 
 * 堆溢出
 *
 */
public class HeapOOM {
	
	static class OOMObject{
		
	}

	
	/**
	 * java.lang.OutOfMemoryError: Java heap space
	 *  java堆空间溢出
	 * @param args
	 */
	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<HeapOOM.OOMObject>();
		
		while(true){
			list.add(new OOMObject());
		}
	}

}

package com.xiaobing.jvm;

/**
 * VM args : -Xmx20M -XX:MaxDirectMemorySize=10M
 * @author e_gaoyunc
 *
 */
public class DirectMemoryOOM {
	/*
	private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) {
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		Unsafe unsafe = (Unsafe) unsafeField.get(null);
		while(true){
			unsafe.allocateMemory(_1MB);
		}
	}
	*/
}

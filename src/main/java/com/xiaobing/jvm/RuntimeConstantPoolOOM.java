package com.xiaobing.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * VM args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * @author e_gaoyunc
 *
 */
public class RuntimeConstantPoolOOM {

	public static void main(String[] args) {
		//使用list保持着常量池引用，避免Full GC 回收常量池行为
		List<String> list = new ArrayList<String>();
		int i = 0;
		while(true){
			list.add(String.valueOf(i++).intern());
		}
	}

}

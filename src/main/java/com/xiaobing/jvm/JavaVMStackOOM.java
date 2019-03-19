package com.xiaobing.jvm;

/**
 * VM args: -Xss2M
 * @author e_gaoyunc
 * 虚拟机栈溢出 抛出OutOfMemoryError
 * 
 * 【运行之后，电脑会死掉，谨慎运行】
 * 
 *
 */
public class JavaVMStackOOM {

	private void donotstop() {
		while (true) {

		}
	}

	public void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {

				public void run() {
					donotstop();
				}
			});
			thread.start();
		}
	}

	public static void main(String[] args) throws Throwable {
		JavaVMStackOOM oom = new JavaVMStackOOM();
		oom.stackLeakByThread();
	}
}

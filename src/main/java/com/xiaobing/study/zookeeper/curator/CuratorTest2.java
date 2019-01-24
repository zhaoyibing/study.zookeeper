package com.xiaobing.study.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

public class CuratorTest2 {

	// Curator客户端
	public CuratorFramework client = null;

	// 集群模式则是多个ip
	private static final String zkServerIps = "192.168.0.150:2181";

	public CuratorTest2() {
		// 设置重连策略
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);

		// 实例化Curator客户端，Curator的编程风格可以让我们使用方法链的形式完成客户端的实例化
		client = CuratorFrameworkFactory.builder()
				.connectString(zkServerIps)
				.sessionTimeoutMs(10000)
				.namespace("workspace")
				.retryPolicy(retryPolicy)
				.build();

		client.start();

	}

	public void closeZkClient() {
		if (client != null) {
			client.close();
		}
	}

	public static void main(String[] args) throws Exception {
		CuratorTest2 curator = new CuratorTest2();

		boolean isZkStarted = CuratorFrameworkState.STARTED == curator.client.getState();
		System.out.println("当前客户端的状态：" + (isZkStarted ? "连接中..." : "已关闭..."));

		String nodePath = "/super/testNode";  // 节点路径
		byte[] data = "this is a test data".getBytes();  // 节点数据
		String result = curator.client.create()
				.creatingParentsIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
				.forPath(nodePath, data);
		
		
		System.out.println(result + "节点，创建成功...");

		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		curator.closeZkClient();
		
		isZkStarted = CuratorFrameworkState.STARTED == curator.client.getState();
		System.out.println("当前客户端的状态：" + (isZkStarted ? "连接中..." : "已关闭..."));
		
		
	}

}

package com.xiaobing.study.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorWatcher {

	// Curator客户端
	public static CuratorFramework client = null;

	// 集群模式则是多个ip
	private static final String zkServerIps = "192.168.0.150:2181";
	
	private static String zkpath = "/zkpathtest";

	public CuratorWatcher() {
		
		System.out.println("in watcher branch!!!");
		
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

	public static void closeZkClient() {
		if (client != null) {
			client.close();
		}
	}

	public static void main(String[] args) throws Exception {
		CuratorWatcher curator = new CuratorWatcher();

		boolean isZkStarted = CuratorFrameworkState.STARTED == client.getState();
		System.out.println("当前客户端的状态：" + (isZkStarted ? "连接中..." : "已关闭..."));

		/*
		 * String nodePath = "/super/testNode"; // 节点路径 byte[] data =
		 * "this is a test data".getBytes(); // 节点数据 String result = client.create()
		 * .creatingParentsIfNeeded() .forPath(nodePath, data);
		 * System.out.println(result + "节点，创建成功...");
		 */
		
		/*
		 * 监控指定节点的数据变化
		 */
		final NodeCache nodeCache = new NodeCache(client, zkpath);
		nodeCache.getListenable().addListener(new NodeCacheListener() {
			public void nodeChanged() throws Exception {
				System.out.println("NodeCache listener working... , currentData:" + new String(nodeCache.getCurrentData().getData()));
			}
		});
		nodeCache.start(true);
		
		/*
		 * 监控指定节点的下一级节点事件：
		 * 	CHILD_ADDED
		 * 	CHILD_UPDATED
		 * 	CHILD_REMOVED
		 */
		PathChildrenCache pathChildrenCache = new PathChildrenCache(client, zkpath, true);
		pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				System.out.println("PathChildrenCache listener working... event is " + event);
			}
		});
		pathChildrenCache.start(StartMode.BUILD_INITIAL_CACHE);
		
		/*
		 * 上面2种监听的全部：监听
		 * 		指定节点数据变化，
		 * 		子节点变化，
		 * 		子节点对应的所有下级节点的所有事件
		 */
		TreeCache treeCache = new TreeCache(client, zkpath);
		treeCache.getListenable().addListener(new TreeCacheListener() {
			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
				System.out.println("TreeCache listener working... event is " + event);
			}
		});
		treeCache.start();
		
		
		try {
			Thread.sleep(90000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		closeZkClient();
		
		isZkStarted = CuratorFrameworkState.STARTED == client.getState();
		System.out.println("当前客户端的状态：" + (isZkStarted ? "连接中..." : "已关闭..."));
		
		
	}

}

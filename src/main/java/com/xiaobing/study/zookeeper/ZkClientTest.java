package com.xiaobing.study.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Hello world!
 *
 */
public class ZkClientTest {
	
	private ZooKeeper zookeeper = null;
	private ZookeeperWatcher watcher = new ZookeeperWatcher();
	
	/*
	 * public static void main(String[] args) throws Exception { ZooKeeper zk = new
	 * ZooKeeper("192.168.0.150:2181", 5000, new ZookeeperEventWater()); String
	 * createResult = zookeeper.create("/testMehtodCreate13", "赵义兵".getBytes(),
	 * Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT);
	 * System.out.println("create result = " + createResult); }
	 */
	
	@Before
	public void init() throws IOException {
		System.out.println("zookeeper is initing...");
		zookeeper = new ZooKeeper("192.168.0.150:2181", 5000, watcher);
		//watcher.setZk(zookeeper);
	}
	
	@Test
	public void test() throws KeeperException, InterruptedException {
		String nodeName = "/testMehtodCreate18";
		String data = "123456767";
		zookeeper.exists(nodeName, true);
		testCreateNode(nodeName, data);
		zookeeper.exists(nodeName, true);
		testGetData(nodeName);
		zookeeper.exists(nodeName, true);
		testDeleteNode(nodeName);
	}
	
	
	public void testCreateNode(String path, String data) throws KeeperException, InterruptedException {
		System.out.println("method testCreateNode invoking...");
		String createResult = zookeeper.create(path, data.getBytes(), Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("create result = " + createResult);
	}
	
	public void testGetData(String path) throws KeeperException, InterruptedException {
		System.out.println("method testGetData invoking...");
		String dataStr = new String(zookeeper.getData(path, true, new Stat()));
		System.out.println("testGetData result = " + dataStr);
	}
	
	public void testDeleteNode(String nodeName) throws InterruptedException, KeeperException {
		System.out.println("method testDeleteNode invoking...");
		zookeeper.delete(nodeName, -1);
		System.out.println("testDeleteNode end");
	}
	
	
	@After
	public void destory() throws InterruptedException {
		if (zookeeper != null) {
			System.out.println("zookeeper is destory....");
			zookeeper.close();
		}
	}
	
}

package com.xiaobing.study.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ZookeeperWatcher implements Watcher {

	private ZooKeeper zk = null;

	public ZookeeperWatcher() {
	}

	public ZookeeperWatcher(ZooKeeper zk) {
		this.zk = zk;
	}

	public void setZk(ZooKeeper zk) {
		this.zk = zk;
	}

	public void process(WatchedEvent event){
		String nodePath = event.getPath();
		String log_process = "Watcher-";
		System.out.println(log_process + "收到Watcher的通知");
		System.out.println(log_process + "连接状态：" + event.getState());
		System.out.println(log_process + "事件类型：" + event.getType());

		if (EventType.None == event.getType()) {
			System.out.println("成功链接zookeeper服务器");
		}
		else if (EventType.NodeCreated == event.getType()) { // 当服务器端创建节点的时候触发
			System.out.println(log_process + " zookeeper服务端创建新的节点");
			// zookeeper服务端创建一个新的节点后并对其进行监控,创建完后接着对该节点进行监控,没有此代码将不会在监控该节点
			try {
				zk.exists(nodePath, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (EventType.NodeDataChanged == event.getType()) { // 被监控该节点的数据发生变更的时候触发
			System.out.println(log_process + "节点的数据更新");
			try {
				zk.getData(nodePath, true, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (EventType.NodeChildrenChanged == event.getType()) {
			// 对应本代码而言只能监控根节点的一级节点变更。如：在根节点直接创建一级节点，
			// 或者删除一级节点时触发。如修改一级节点的数据，不会触发，创建二级节点时也不会触发。
			System.out.println("子节点发生变更");
			try {
				System.out.println(log_process + "子节点列表：" + zk.getChildren(nodePath, true));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(EventType.NodeDeleted == event.getType()){
			System.out.println(log_process+"节点："+nodePath+"被删除");
		}
		System.out.println("===== node has one event end=====");

	}
}

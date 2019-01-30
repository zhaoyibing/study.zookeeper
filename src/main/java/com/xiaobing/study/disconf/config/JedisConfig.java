package com.xiaobing.study.disconf.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;


@DisconfFile(filename="redis.properties")
public class JedisConfig {
	
	private String host;
	
	private String port;
	
	private int database;

	@DisconfFileItem(name="redis.host", associateField="host")
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@DisconfFileItem(name="redis.port", associateField="port")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@DisconfFileItem(name="redis.database", associateField="database")
	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}
	
	

}

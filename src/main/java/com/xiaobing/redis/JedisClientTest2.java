package com.xiaobing.redis;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class JedisClientTest2 {
	
	 /**
     * jedis配置对象 .
     */
    public JedisPoolConfig config;
    
    /**
     * jedis连接池对象 .
     */
    public JedisSentinelPool jedisSentinelPool;

    public Integer dbNum = 0;
	
	public JedisClientTest2(){
	}
	
	public void init() {
		
		config = new JedisPoolConfig();
		
	    Set<String> sentinels = new HashSet<String>();
	    sentinels.add("192.168.0.150:26379");
	    sentinels.add("192.168.0.150:26380");
	    sentinels.add("192.168.0.150:26381");
		jedisSentinelPool = new JedisSentinelPool("redis-master", sentinels, config, "123456");
	}
	
	public Jedis getResource() {
        Jedis jedis = jedisSentinelPool.getResource();
        return jedis;
    }
	
	/**
     * 回收jedis连接，异常时使用 .
     * 
     * @param jedis
     *            jedis连接
     */
	public void closeJedid(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
	
	public String ping(){
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.ping();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJedid(jedis);
		}
		return result;
	}
	
	public Long setnx(String key, String value) {
		Long lockVal = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			System.out.println("sentinel 获取的主库信息：" + jedis.getClient().getHost() + ":" + jedis.getClient().getPort()); 
			jedis.select(11);
			lockVal = jedis.setnx(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeJedid(jedis);
		}
		return lockVal;
	}

	public String getStr(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	closeJedid(jedis);
        }
        return value;
    }
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		JedisClientTest2 clientTest = new JedisClientTest2();
		clientTest.init();
		
		int i = 1;
		while(true) {
			String key = "key"+i;
			String value = "value"+i;
			try {
				clientTest.setnx(key, value);
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("set key=" + key);
			i++;
			
			try {
				Thread.sleep(5000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

}

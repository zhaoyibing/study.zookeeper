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
        //jedis.select(dbNum);
        return jedis;
    }
	
	public void returnResource(Jedis jedis) {
        if (jedis != null) {
        	jedisSentinelPool.returnResource(jedis);
        }
    }
	
	/**
     * 回收jedis连接，异常时使用 .
     * 
     * @param jedis
     *            jedis连接
     */
	public void returnBrokenResource(Jedis jedis) {
		if (jedis != null) {
			jedisSentinelPool.returnBrokenResource(jedis);
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
			returnBrokenResource(jedis);
		} finally {
			returnResource(jedis);
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
			returnBrokenResource(jedis);
		} finally {
			returnResource(jedis);
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
            returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
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
		
		
		/*
		 
		byte[] btyeArray = new byte[43];    //{((byte)'42'),'51', 13};
		btyeArray[0] = 42;
		btyeArray[1] = 51;
		btyeArray[2] = 13;
		btyeArray[3] = 10;
		btyeArray[4] = 36;
		btyeArray[5] = 53;
		btyeArray[6] = 13;
		btyeArray[7] = 10;
		btyeArray[8] = 83;
		btyeArray[9] = 69;
		btyeArray[10] = 84;
		btyeArray[11] = 78;
		btyeArray[12] = 88;
		btyeArray[13] = 13;
		btyeArray[14] = 10;
		btyeArray[15] = 36;
		btyeArray[16] = 52;
		btyeArray[17] = 13;
		btyeArray[18] = 10;
		btyeArray[19] = 107;
		btyeArray[20] = 101;
		btyeArray[21] = 121;
		btyeArray[22] = 49;
		btyeArray[23] = 13;
		btyeArray[24] = 10;
		btyeArray[25] = 36;
		btyeArray[26] = 49;
		btyeArray[27] = 50;
		btyeArray[28] = 13;
		btyeArray[29] = 10;
		btyeArray[30] = 104;
		btyeArray[31] = 101;
		btyeArray[32] = 108;
		btyeArray[33] = 108;
		btyeArray[34] = 111;
		btyeArray[35] = 32;
		btyeArray[36] = 119;
		btyeArray[37] = 111;
		btyeArray[38] = 114;
		btyeArray[39] = 108;
		btyeArray[40] = 100;
		btyeArray[41] = 33;
		btyeArray[42] = 13;
		System.out.println(new String(btyeArray, "UTF-8"));
		 */
		
	}
	

}

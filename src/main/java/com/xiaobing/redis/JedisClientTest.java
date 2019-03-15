package com.xiaobing.redis;

import java.io.UnsupportedEncodingException;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class JedisClientTest {
	
	 /**
     * jedis配置对象 .
     */
    public JedisPoolConfig config;
    
    /**
     * jedis连接池对象 .
     */
    public JedisPool jedisPool;

    public Integer dbNum = 14;
	
	public JedisClientTest(){
	}
	
	public void init() {
		config = new JedisPoolConfig();
		
		jedisPool = new JedisPool(config, "localhost", 6379, 3000, "123456", dbNum);
	}
	
	public void destory(){
		jedisPool.destroy();
	}
	
	public Jedis getResource() {
        Jedis jedis = jedisPool.getResource();
        //jedis.select(dbNum);
        return jedis;
    }
	
	public void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
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
			jedisPool.returnBrokenResource(jedis);
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
	
	public List<Object> exec() {
		List<Object> execRes = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			jedis.watch("");
			Transaction tr = jedis.multi();

			Response<Long> set1Res = tr.setnx("key1", "value1");
			Response<String> set2Res = tr.set("key2", "value2");
			Response<String> set3Res = tr.set("key3", "value3");

			execRes = tr.exec();

		} catch (Exception e) {
			e.printStackTrace();
			returnBrokenResource(jedis);
		} finally {
			returnResource(jedis);
		}
		return execRes;
	}
	
	
	public static void main(String[] args) {
		JedisClientTest clientTest = new JedisClientTest();
		clientTest.init();
		
		List<Object> execRes = clientTest.exec();
		
		for (Object object : execRes) {
			System.out.println(object);
		}
		
		//System.out.println(clientTest.ping());
		/*
		Long setRes = clientTest.setnx("key1", "hello world!");
		System.out.println(setRes);
		
		String getRes = clientTest.getStr("key1");
		System.out.println(getRes);
		*/
		
		clientTest.destory();
	}
	
	public void testPortal() throws UnsupportedEncodingException {
		/*
		 	命令：*3\r\n$5\r\nSETNX\r\n$4\r\nkey1\r\n$12\r\nhello world!\r\n
		 */
		System.out.println("命令对应的socket内容：*3\r\n$5\r\nSETNX\r\n$4\r\nkey1\r\n$12\r\nhello world!\r\n" );
		byte[] btyeArray = new byte[43];
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
		
	}
	

}

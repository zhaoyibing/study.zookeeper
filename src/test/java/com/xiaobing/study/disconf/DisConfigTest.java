package com.xiaobing.study.disconf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baidu.disconf.client.DisconfMgrBean;
import com.xiaobing.study.disconf.config.DisConfConfig;
import com.xiaobing.study.disconf.config.JedisConfig;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@ContextConfiguration(classes={DisConfConfig.class})
public class DisConfigTest {

	@Autowired
	DisconfMgrBean disconfMgrBean;
	
	@Autowired
	JedisConfig jedisConfig;

	@Test
	public void test1() {
		System.out.println("2222");
		System.out.println(disconfMgrBean);
		System.out.println(jedisConfig.getHost());
		System.out.println("2222");
	}
}

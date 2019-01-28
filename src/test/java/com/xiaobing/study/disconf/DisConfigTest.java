package com.xiaobing.study.disconf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baidu.disconf.client.DisconfMgrBean;
import com.xiaobing.study.disconf.config.DisConfConfig;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@ContextConfiguration(classes={DisConfConfig.class})
public class DisConfigTest {

	@Autowired
	DisconfMgrBean disconfMgrBean;

	@Test
	public void test1() {
		System.out.println("2222");
		System.out.println(disconfMgrBean);
		System.out.println("2222");
	}
}

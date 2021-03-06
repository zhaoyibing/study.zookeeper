package com.xiaobing.study.disconf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;


@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@Import({RedisConfig.class})
public class DisConfConfig {
	
	@Bean(name="disconfMgrBean", destroyMethod="destroy")
	public DisconfMgrBean disconfMgrBean(){
		DisconfMgrBean disconfMgrBean = new DisconfMgrBean();
		disconfMgrBean.setScanPackage("com.xiaobing.study.disconf");
		return disconfMgrBean;
	}
	
	@Bean(name="disconfMgrBeanSecond",initMethod="init", destroyMethod="destroy")
	public DisconfMgrBeanSecond disconfMgrBeanSecond(){
		DisconfMgrBeanSecond disconfMgrBeanSecond = new DisconfMgrBeanSecond();
		return disconfMgrBeanSecond;
	}

}

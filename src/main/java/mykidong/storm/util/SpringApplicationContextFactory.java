package mykidong.storm.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationContextFactory {
	
	private static ApplicationContext applicationContext;
	
	public static final String CONF_PATH = "classpath*:META-INF/spring/*.xml";
	
	public static ApplicationContext newInstance()
	{
		if(applicationContext == null)
		{
			synchronized(ApplicationContext.class)
			{
				applicationContext = new ClassPathXmlApplicationContext(CONF_PATH);
			}
		}
		
		return applicationContext;
	}
}

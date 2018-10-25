package com.zhenyi.remoting.test;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhenyi.remoting.framework.serialization.engine.SerializerEngine;


public class MainClient
{

	private static final Logger logger = LoggerFactory.getLogger(MainClient.class);
	
	public static void main(String[] args) throws Exception
	{

		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test-client.xml");
		
		int count = 15;
		while(count > 0)
		{
			new Thread(new Runnable() 
			{
				public void run()
				{
					HashMap<Integer,TestEntity> lst = new HashMap<Integer,TestEntity>();
					TestEntity test = new TestEntity();
					test.test = 1;
					test.test2 = "str" + 1;
					lst.put(0,test);
					long time = System.currentTimeMillis();
					for(;;)
					{
						try
						{
							Thread.sleep(2);
						}
						catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						test.serialize();
						final HelloService helloService = (HelloService) context.getBean("testService");
						
						int a = helloService.sayHello(test.test);
					}
				}
			}).start();
			count--;
		}





		
		
		
		
		
//		final HelloService helloService = (HelloService) context.getBean("remoteHelloService");
//		long count = 10;
//		
//		final Hello2Service helloService2 = (Hello2Service) context.getBean("remoteHello2Service");
//		
//		for (int i = 0; i < count; i++)
//		{
//			try
//			{
//				String result = helloService.sayHello("binge,i=" + i);
//				System.out.println(result);
//			} 
//			catch (Exception e)
//			{
//				logger.warn("--------", e);
//			}
//		}
//		
//		for (int i = 0; i < count; i++)
//		{
//			try
//			{
//				String result = helloService2.sayYeah("binge2,i=" + i);
//				System.out.println(result);
//			} 
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		}

//		System.exit(0);
	}
}

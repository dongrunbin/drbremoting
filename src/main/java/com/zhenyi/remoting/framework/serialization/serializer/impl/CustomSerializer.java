package com.zhenyi.remoting.framework.serialization.serializer.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.zhenyi.remoting.framework.interfaces.ISerializable;
import com.zhenyi.remoting.framework.serialization.serializer.ISerializer;

public class CustomSerializer implements ISerializer
{
	@Override
	public <T> byte[] serialize(T obj)
	{
		Class<?> cls = ((ISerializable)obj).getClass();
		Method method;
		byte[] data = null;
		try
		{
			method = cls.getMethod("serialize");
			data = (byte[])method.invoke(obj);
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> clazz)
	{
		T obj = null;
		try
		{
			Method method = clazz.getMethod("deserialize", byte[].class);
			obj = clazz.newInstance();
			method.invoke(obj,data);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}

		return obj;
	}
}

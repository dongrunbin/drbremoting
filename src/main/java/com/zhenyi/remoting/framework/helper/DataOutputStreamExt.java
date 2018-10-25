package com.zhenyi.remoting.framework.helper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.zhenyi.remoting.framework.interfaces.ISerializable;

public class DataOutputStreamExt extends DataOutputStream {

	public DataOutputStreamExt(OutputStream out) {
		super(out);
	}
	
	public void writeBytes(byte[] buffer) throws IOException
	{
		super.writeInt(buffer == null ? 0 : buffer.length);
		if(buffer != null)
		{
			super.write(buffer, 0, buffer.length);
		}
	}
	
	
	public void writeObject(Object obj) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		if(obj == null)
		{
			super.writeUTF("null");
		}
		else if(obj instanceof Integer)
		{
			super.writeUTF("int");
			super.writeInt((Integer)obj);
		}
		else if(obj instanceof String)
		{
			super.writeUTF("string");
			super.writeUTF((String)obj);
		}
		else if(obj instanceof Boolean)
		{
			super.writeUTF("bool");
			super.writeBoolean((Boolean)obj);
		}
		else if(obj instanceof Long)
		{
			super.writeUTF("long");
			super.writeLong((Long)obj);
		}
		else if(obj instanceof Byte)
		{
			super.writeUTF("byte");
			super.writeByte((Byte)obj);
		}
		else if(obj instanceof Enum)
		{
			super.writeUTF("enum");
			Enum<?> e = (Enum<?>)obj;
			super.writeUTF(e.getClass().getName());
			super.writeUTF(e.name());
			super.writeInt(e.ordinal());
		}
		else if(obj instanceof Short)
		{
			super.writeUTF("short");
			super.writeShort((Short)obj);
		}
		else if(obj instanceof Float)
		{
			super.writeUTF("float");
			super.writeFloat((Float)obj);
		}
		else if(obj instanceof Double)
		{
			super.writeUTF("double");
			super.writeDouble((Double)obj);
		}
		else if(obj instanceof Character)
		{
			super.writeUTF("char");
			super.writeChar((Character)obj);
		}
		else if(obj instanceof List)
		{
			super.writeUTF("list");
			List<?> lst = (List<?>)obj;
			super.writeShort(lst.size());
			for(int i = 0; i < lst.size(); ++i)
			{
				this.writeObject(lst.get(i));
			}
		}
		else if(obj instanceof Map)
		{
			super.writeUTF("map");
			Map<?,?> map = (Map<?,?>)obj;
			super.writeShort(map.size());
			for(Map.Entry<?,?> entry : map.entrySet())
			{
				this.writeObject(entry.getKey());
				this.writeObject(entry.getValue());
			}
		}
		else
		{
    		Class<?> cls = ((ISerializable)obj).getClass();
			super.writeUTF(cls.getName());
			Method method;
			method = cls.getMethod("serialize");
			byte[] data = (byte[])method.invoke(obj);
			this.writeBytes(data);
		}
	}
}
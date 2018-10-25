package com.zhenyi.remoting.framework.helper;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataInputStreamExt extends DataInputStream {

	public DataInputStreamExt(InputStream in) {
		super(in);
	}
	
	
	public byte[] readBytes() throws IOException
	{
		int length = super.readInt();
		if(length == 0) return null;
		byte[] ret = new byte[length];
		super.read(ret, 0, length);
		return ret;
	}
	
	public Object readObject() throws IOException
	{
		Object obj = null;
		String type = super.readUTF();
		if(type.equals("null"))
		{
			
		}
		else if(type.equals("int"))
		{
			obj = super.readInt();
		}
		else if(type.equals("long"))
		{
			obj = super.readLong();
		}
		else if(type.equals("short"))
		{
			obj = super.readShort();
		}
		else if(type.equals("byte"))
		{
			obj = super.readByte();
		}
		else if(type.equals("float"))
		{
			obj = super.readFloat();
		}
		else if(type.equals("double"))
		{
			obj = super.readDouble();
		}
		else if(type.equals("char"))
		{
			obj = super.readChar();
		}
		else if(type.equals("bool"))
		{
			obj = super.readBoolean();
		}
		else if(type.equals("string"))
		{
			obj = super.readUTF();
		}
		else if(type.equals("enum"))
		{
			String clazzName = super.readUTF();
			String name = super.readUTF();
			try
			{
				obj = Enum.valueOf((Class<Enum>)Class.forName(clazzName), name);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			int value = super.readInt();
		}
		else if(type.equals("list"))
		{
			short length = super.readShort();
			ArrayList<Object> lst = new ArrayList<Object>();
			for(int i = 0; i < length; ++i)
			{
				lst.add(this.readObject());
			}
			obj = lst;
		}
		else if(type.equals("map"))
		{
			short length = super.readShort();
			HashMap<Object,Object> map = new HashMap<Object,Object>();
			for(int i = 0; i < length; ++i)
			{
				map.put(this.readObject(),this.readObject());
			}
			obj = map;
		}
		else
		{
    		byte[] data = this.readBytes();
    		try
			{
				Class<?> cls = Class.forName(type);
				
				try
				{
					obj = cls.newInstance();
					Method method = cls.getMethod("deserialize", byte[].class);
					method.invoke(obj, data);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		return obj;
	}

}

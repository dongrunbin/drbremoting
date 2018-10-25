package com.zhenyi.remoting.framework.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import com.zhenyi.remoting.framework.helper.DataInputStreamExt;
import com.zhenyi.remoting.framework.helper.DataOutputStreamExt;
import com.zhenyi.remoting.framework.interfaces.ISerializable;

/**
 * 请求数据实体类
 * 
 * @author Binge
 *
 */
public class RequestEntity implements ISerializable, Serializable
{

	// UUID,唯一标识一次返回值
	private String uniqueKey = "";
	// 服务提供者信息
	private ProviderService providerService = null;
	// 调用的方法名称
	private String invokedMethodName = "";
	// 传递参数
	private Object[] args = null;
	// 消费端应用名
	private String appName = "";
	// 消费请求超时时长
	private long invokeTimeout = 0;

	public String getUniqueKey()
	{
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey)
	{
		this.uniqueKey = uniqueKey;
	}

	public ProviderService getProviderService()
	{
		return providerService;
	}

	public void setProviderService(ProviderService providerService)
	{
		this.providerService = providerService;
	}

	public String getInvokedMethodName()
	{
		return invokedMethodName;
	}

	public void setInvokedMethodName(String invokedMethodName)
	{
		this.invokedMethodName = invokedMethodName;
	}

	public Object[] getArgs()
	{
		return args;
	}

	public void setArgs(Object[] args)
	{
		this.args = args;
	}

	public String getAppName()
	{
		return appName;
	}

	public void setAppName(String appName)
	{
		this.appName = appName;
	}

	public long getInvokeTimeout()
	{
		return invokeTimeout;
	}

	public void setInvokeTimeout(long invokeTimeout)
	{
		this.invokeTimeout = invokeTimeout;
	}

	@Override
	public byte[] serialize()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStreamExt dos = new DataOutputStreamExt(baos);
        byte[] ret = null;
        try{
            dos.writeUTF(uniqueKey);
            dos.writeBytes(providerService.serialize());
            dos.writeUTF(invokedMethodName);
            dos.writeInt(args == null ? 0 : args.length);
            if(args != null)
            {
                for(int i = 0; i < args.length; ++i)
                {
                	dos.writeObject(args[i]);
                }
            }
            
            dos.writeUTF(appName);
            dos.writeLong(invokeTimeout);
            ret = baos.toByteArray();
            dos.close();
            baos.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return ret;
	}

	@Override
	public void deserialize(byte[] buffer)
	{
		if(buffer == null) return;
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        DataInputStreamExt dis = new DataInputStreamExt(bais);
        try
        {
        	uniqueKey = dis.readUTF();
        	providerService = new ProviderService();
        	providerService.deserialize(dis.readBytes());
        	invokedMethodName = dis.readUTF();
        	int length = dis.readInt();
        	args = new Object[length];
        	for(int i = 0; i < args.length; ++i)
        	{
        		args[i] = dis.readObject();
        	}
        	
        	appName = dis.readUTF();
        	invokeTimeout = dis.readLong();
            dis.close();
            bais.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
	}
}

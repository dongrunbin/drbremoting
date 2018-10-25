package com.zhenyi.remoting.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhenyi.remoting.framework.helper.DataInputStreamExt;
import com.zhenyi.remoting.framework.helper.DataOutputStreamExt;
import com.zhenyi.remoting.framework.interfaces.ISerializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 服务注册中心的服务提供者注册信息
 * 
 * @author Binge
 *
 */
public class ProviderService implements ISerializable, Serializable
{

	private Class<?> serviceItf;
	private transient Object serviceObject;
	@JsonIgnore
	private transient Method serviceMethod;
	private String serverIp = "";
	private int serverPort;
	private long timeout;
	// 该服务提供者权重
	private int weight;
	// 服务端线程数
	private int workerThreads;
	// 服务提供者唯一标识
	private String appKey = "";
	// 服务分组组名
	private String groupName = "";
	// 参数索引
	private int paramIndex;
	// 最小参数
	private long minParam;
	// 最大参数
	private long maxParam;
	
	public byte[] serialize()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStreamExt dos = new DataOutputStreamExt(baos);
        byte[] ret = null;
        try{
            dos.writeUTF(serviceItf.getName());
            dos.writeUTF(serverIp);
            dos.writeInt(serverPort);
            dos.writeLong(timeout);
            dos.writeInt(weight);
            dos.writeInt(workerThreads);
            dos.writeUTF(appKey);
            dos.writeUTF(groupName);
            dos.writeInt(paramIndex);
            dos.writeLong(minParam);
            dos.writeLong(maxParam);
            ret = baos.toByteArray();
            dos.close();
            baos.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return ret;
	}
	
	public void deserialize(byte[] buffer)
	{
		if(buffer == null) return;
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        DataInputStreamExt dis = new DataInputStreamExt(bais);
        try
        {
        	serviceItf = Class.forName(dis.readUTF());
        	serverIp = dis.readUTF();
        	serverPort = dis.readInt();
        	timeout = dis.readLong();
        	weight = dis.readInt();
        	workerThreads = dis.readInt();
        	appKey = dis.readUTF();
        	groupName = dis.readUTF();
        	paramIndex = dis.readInt();
        	minParam = dis.readLong();
        	maxParam = dis.readLong();
            dis.close();
            bais.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public ProviderService copy()
	{
		ProviderService providerService = new ProviderService();
		providerService.setServiceItf(serviceItf);
		providerService.setServiceObject(serviceObject);
		providerService.setServiceMethod(serviceMethod);
		providerService.setServerIp(serverIp);
		providerService.setServerPort(serverPort);
		providerService.setTimeout(timeout);
		providerService.setWeight(weight);
		providerService.setWorkerThreads(workerThreads);
		providerService.setAppKey(appKey);
		providerService.setGroupName(groupName);
		providerService.setParamIndex(paramIndex);
		providerService.setMinParam(minParam);
		providerService.setMaxParam(maxParam);
		return providerService;
	}

	public Class<?> getServiceItf()
	{
		return serviceItf;
	}

	public void setServiceItf(Class<?> serviceItf)
	{
		this.serviceItf = serviceItf;
	}

	public Object getServiceObject()
	{
		return serviceObject;
	}

	public void setServiceObject(Object serviceObject)
	{
		this.serviceObject = serviceObject;
	}

	public Method getServiceMethod()
	{
		return serviceMethod;
	}

	public void setServiceMethod(Method serviceMethod)
	{
		this.serviceMethod = serviceMethod;
	}

	public String getServerIp()
	{
		return serverIp;
	}

	public void setServerIp(String serverIp)
	{
		this.serverIp = serverIp;
	}

	public int getServerPort()
	{
		return serverPort;
	}

	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}

	public long getTimeout()
	{
		return timeout;
	}

	public void setTimeout(long timeout)
	{
		this.timeout = timeout;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	public int getWorkerThreads()
	{
		return workerThreads;
	}

	public void setWorkerThreads(int workerThreads)
	{
		this.workerThreads = workerThreads;
	}

	public String getAppKey()
	{
		return appKey;
	}

	public void setAppKey(String appKey)
	{
		this.appKey = appKey;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}
	
	public int getParamIndex()
	{
		return this.paramIndex;
	}
	
	public void setParamIndex(int paramIndex)
	{
		this.paramIndex = paramIndex;
	}

	public long getMinParam()
	{
		return this.minParam;
	}

	public void setMinParam(long minParam)
	{
		this.minParam = minParam;
	}

	public long getMaxParam()
	{
		return this.maxParam;
	}

	public void setMaxParam(long maxParam)
	{
		this.maxParam = maxParam;
		;
	}
}

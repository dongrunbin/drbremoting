package com.zhenyi.remoting.framework.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import com.zhenyi.remoting.framework.helper.DataInputStreamExt;
import com.zhenyi.remoting.framework.helper.DataOutputStreamExt;
import com.zhenyi.remoting.framework.interfaces.ISerializable;

/**
 * 返回数据实体类
 * 
 * @author Binge
 *
 */
public class ResponseEntity implements ISerializable, Serializable
{

	// UUID,唯一标识一次返回值
	private String uniqueKey = "";
	// 接口调用返回的结果对象
	private Object result = null;
	// 客户端指定的服务超时时间
	private long invokeTimeout = 0;

	public String getUniqueKey()
	{
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey)
	{
		this.uniqueKey = uniqueKey;
	}

	public long getInvokeTimeout()
	{
		return invokeTimeout;
	}

	public void setInvokeTimeout(long invokeTimeout)
	{
		this.invokeTimeout = invokeTimeout;
	}

	public Object getResult()
	{
		return result;
	}

	public void setResult(Object result)
	{
		this.result = result;
	}


	@Override
	public byte[] serialize()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStreamExt dos = new DataOutputStreamExt(baos);
        byte[] ret = null;
        try{
            dos.writeUTF(uniqueKey);
            dos.writeObject(result);
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
        	result = dis.readObject();
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

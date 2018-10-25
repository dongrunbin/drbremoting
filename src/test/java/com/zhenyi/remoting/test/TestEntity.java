package com.zhenyi.remoting.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import com.zhenyi.remoting.framework.helper.DataInputStreamExt;
import com.zhenyi.remoting.framework.helper.DataOutputStreamExt;
import com.zhenyi.remoting.framework.interfaces.ISerializable;

public class TestEntity implements ISerializable, Serializable
{
	public int test;
	
	public String test2 = "";

	public byte[] serialize()
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStreamExt dos = new DataOutputStreamExt(baos);
        byte[] ret = null;
        try
        {
        	dos.writeInt(test);
            dos.writeUTF(test2);
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
        	test = dis.readInt();
        	test2 = dis.readUTF();
            dis.close();
            bais.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
	}
}

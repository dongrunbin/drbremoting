package com.zhenyi.remoting.framework.interfaces;

public interface ISerializable
{
	byte[] serialize();
	
	void deserialize(byte[] buffer);
}

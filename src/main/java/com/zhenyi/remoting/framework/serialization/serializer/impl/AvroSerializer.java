package com.zhenyi.remoting.framework.serialization.serializer.impl;

import com.zhenyi.remoting.framework.serialization.serializer.ISerializer;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Avro
 * 
 * @author Binge
 *
 */
public class AvroSerializer implements ISerializer
{

	@Override
	public <T> byte[] serialize(T obj)
	{
		try
		{
			DatumWriter userDatumWriter = new SpecificDatumWriter(obj.getClass());
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BinaryEncoder binaryEncoder = EncoderFactory.get().directBinaryEncoder(outputStream, null);
			userDatumWriter.write(obj, binaryEncoder);
			return outputStream.toByteArray();
		} 
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> clazz)
	{
		try
		{
			DatumReader userDatumReader = new SpecificDatumReader(clazz);
			BinaryDecoder binaryDecoder = DecoderFactory.get().directBinaryDecoder(new ByteArrayInputStream(data),
					null);
			return (T) userDatumReader.read(clazz.newInstance(), binaryDecoder);
		} 
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

}

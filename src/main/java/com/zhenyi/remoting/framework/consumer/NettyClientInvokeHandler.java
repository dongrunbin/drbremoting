package com.zhenyi.remoting.framework.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhenyi.remoting.framework.helper.PropertyConfigeHelper;
import com.zhenyi.remoting.framework.model.ResponseEntity;
import com.zhenyi.remoting.framework.serialization.engine.SerializerEngine;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Netty客户端逻辑处理
 * 
 * @author Binge
 *
 */
public class NettyClientInvokeHandler extends SimpleChannelInboundHandler<byte[]>
{
	
	private static final Logger logger = LoggerFactory.getLogger(NettyClientInvokeHandler.class);

	public NettyClientInvokeHandler()
	{
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
	{
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		logger.info(cause.toString());
//		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] data) throws Exception
	{
		ResponseEntity response = SerializerEngine.deserialize(data, ResponseEntity.class, PropertyConfigeHelper.getSerializeType().toString());
		ConsumerResponseHolder.putResultValue(response);
	}

}

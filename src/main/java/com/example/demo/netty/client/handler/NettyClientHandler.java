package com.example.demo.netty.client.handler;

import com.example.demo.netty.protocol.CharLenProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;

/**
 * @author liuzhongshuai
 *         Created on 2018/6/20.
 */
@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<CharLenProtocol> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 发送SmartCar协议的消息
        // 要发送的信息
        String data = "刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅刘中帅";
        // 获得要发送信息的字节数组
        byte[] content = data.getBytes();
        // 要发送信息的长度
        int contentLength = content.length;

        CharLenProtocol protocol = new CharLenProtocol(contentLength, content);

        ctx.writeAndFlush(protocol);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CharLenProtocol msg) throws Exception {
        try {
            // 用于获取客户端发来的数据信息
            CharLenProtocol body = msg;
            System.out.println("Client接受的客户端的信息 :" + new String(body.getContent(),"UTF-8"));

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

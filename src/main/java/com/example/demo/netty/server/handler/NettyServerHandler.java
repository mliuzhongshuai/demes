package com.example.demo.netty.server.handler;

import com.example.demo.netty.protocol.CharLenProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;

/**
 * @author liuzhongshuai
 *         Created on 2018/6/20.
 */
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 用于获取客户端发来的数据信息
        CharLenProtocol body = (CharLenProtocol) msg;
        try {
            System.out.println("Server接受的客户端的信息 :" + new String(body.getContent(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 会写数据给客户端
        String str = "Hi I am Server ...";
        CharLenProtocol response = new CharLenProtocol(str.getBytes().length,str.getBytes());
        // 当服务端完成写操作后，关闭与客户端的连接
        ctx.writeAndFlush(response);
        // ctx.write(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("消息接收完毕，连接关闭......");
       // ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

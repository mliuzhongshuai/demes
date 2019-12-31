package com.example.demo.netty.server;

import com.example.demo.netty.codec.CharLenDecode;
import com.example.demo.netty.codec.CharLenEncode;
import com.example.demo.netty.server.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author liuzhongshuai
 *         Created on 2018/6/20.
 */
public class NettyServer {

    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.err.println("Usage: " + NettyServer.class.getSimpleName() + " <port>");
        }
        // Integer.parseInt(args[0]);
        int port = 8899;
        new NettyServer(port).start();
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 添加自定义协议的编解码工具
                            ch.pipeline().addLast(new CharLenEncode());
                            ch.pipeline().addLast(new CharLenDecode());
                            // 处理网络IO
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture f = b.bind().sync();
            f.addListener((channelFuture) -> {
                System.out.println("服务启动成功!");
            });
            f.channel().closeFuture().sync();
        } finally {

            group.shutdownGracefully().sync();
        }
    }
}

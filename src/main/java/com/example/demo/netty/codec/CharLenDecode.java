package com.example.demo.netty.codec;

import com.example.demo.netty.Constant;
import com.example.demo.netty.protocol.CharLenProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器实现，处理 tcp buf 造成的 半包 粘包
 *
 * @author liuzhongshuai
 *         Created on 2018/6/22.
 */
public class CharLenDecode extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buffer, List list) throws Exception {
        // 可读长度必须大于基本长度
        if (buffer.readableBytes() >= 8) {
            // 防止socket字节流攻击
            // 防止，客户端传来的数据过大
            // 因为，太大的数据，是不合理的
            if (buffer.readableBytes() > 2048) {
                buffer.skipBytes(buffer.readableBytes());
            }
            // 记录包头开始的index
            int beginReader;
            while (true) {
                // 获取包头开始的index
                beginReader = buffer.readerIndex();
                // 标记包头开始的index
                buffer.markReaderIndex();
                // 读到了协议的开始标志，结束while循环
                if (buffer.readInt() == Constant.PROTOCOL_HEADER) {
                    break;
                }
                // 未读到包头，略过一个字节
                // 每次略过，一个字节，去读取，包头信息的开始标记
                buffer.resetReaderIndex();
                buffer.readByte();
                // 当略过，一个字节之后，
                // 数据包的长度，又变得不满足
                // 此时，应该结束。等待后面的数据到达
                if (buffer.readableBytes() < 8) {
                    return;
                }
            }
            // 消息的长度
            int length = buffer.readInt();
            // 判断请求数据包数据是否到齐
            if (buffer.readableBytes() < length) {
                // 还原读指针
                buffer.readerIndex(beginReader);
                return;
            }
            // 读取data数据
            byte[] data = new byte[length];
            buffer.readBytes(data);

            CharLenProtocol protocol = new CharLenProtocol(data.length, data);
            list.add(protocol);
        }

    }
}

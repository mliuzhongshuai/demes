package com.example.demo.netty.codec;

import com.example.demo.netty.protocol.CharLenProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * @author liuzhongshuai
 *         Created on 2018/6/27.
 */
public class CharLenEncode extends MessageToByteEncoder<CharLenProtocol> {

    @Override
    protected void encode(ChannelHandlerContext tcx, CharLenProtocol msg,ByteBuf out) throws Exception {
        // 写入消息SmartCar的具体内容
        // 1.写入消息的开头的信息标志(int类型)
        out.writeInt(msg.getProtocolFlag());
        // 2.写入消息的长度(int 类型)
        out.writeInt(msg.getContentLen());
        // 3.写入消息的内容(byte[]类型)
        out.writeBytes(msg.getContent());
    }
}

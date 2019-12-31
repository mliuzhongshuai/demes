package com.example.demo.netty.protocol;

import com.example.demo.netty.Constant;

/**
 * 自定义协议 按传输长度
 *
 * @author liuzhongshuai
 *         Created on 2018/6/22.
 */
public class CharLenProtocol {

    public CharLenProtocol(int contentLength, byte[] content) {
        this.contentLen = contentLength;
        this.content = content;
    }
    /**
     * 报文开始标记
     */
    private int protocolFlag = Constant.PROTOCOL_HEADER;
    /**
     * 消息正文长度 字节
     */
    private int contentLen;
    /**
     * 消息正文
     */
    private byte[] content;


    public int getProtocolFlag() {
        return protocolFlag;
    }

    public void setProtocolFlag(int protocolFlag) {
        this.protocolFlag = protocolFlag;
    }

    public int getContentLen() {
        return contentLen;
    }

    public void setContentLen(int contentLen) {
        this.contentLen = contentLen;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}

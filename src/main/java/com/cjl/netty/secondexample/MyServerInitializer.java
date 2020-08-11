package com.cjl.netty.secondexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author caojinlin
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * （1）消息长度固定：累计读取到固定长度为LENGTH之后就认为读取到了一个完整的消息。然后将计数器复位，重新开始读下一个数据报文。
     *
     * （2）回车换行符作为消息结束符：在文本协议中应用比较广泛。
     *
     * （3）将特殊的分隔符作为消息的结束标志，回车换行符就是一种特殊的结束分隔符。
     *
     * （4）通过在消息头中定义长度字段来标示消息的总长度。
     */


    /**
     * （1）通过FixedLengthFrameDecoder 定长解码器来解决定长消息的黏包问题；
     *
     * （2）通过LineBasedFrameDecoder和StringDecoder来解决以回车换行符作为消息结束符的TCP黏包的问题；
     *
     * （3）通过DelimiterBasedFrameDecoder 特殊分隔符解码器来解决以特殊符号作为消息结束符的TCP黏包问题；
     *
     * （4）最后一种，通过LengthFieldBasedFrameDecoder 自定义长度解码器解决TCP黏包问题。
     */

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("lengthFieldBasedFrameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0 ,4, 0, 4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new MyServerHandler());
    }

}

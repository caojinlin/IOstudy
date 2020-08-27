package com.cjl.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        switch (msg.getDataType()){
            case PersonType:
                System.out.println(msg.getPerson());
                System.out.println(msg.getPerson().getAddress());
                System.out.println(msg.getPerson().getName());
                System.out.println(msg.getPerson().getAge());
                break;
            case DogType:
                System.out.println(msg.getDog());
                System.out.println(msg.getDog().getCity());
                System.out.println(msg.getDog().getName());
                break;
            case CatType:
                System.out.println(msg.getCat());
                break;
            default:
                System.out.println("异常");
        }
    }
}

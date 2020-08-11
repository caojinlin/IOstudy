package com.cjl.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class MyClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Integer randomInt = new Random().nextInt(3);
        MyDataInfo.MyMessage.Builder builder = MyDataInfo.MyMessage.newBuilder();
        MyDataInfo.MyMessage myMessage = null;
        System.out.println(randomInt);
        switch (randomInt) {
            case 0:
                MyDataInfo.Person person = MyDataInfo.Person.newBuilder().setName("张三").setAge(20).setAddress("上海").build();
                myMessage = builder.setDataType(MyDataInfo.MyMessage.DataType.PersonType).setPerson(person).build();
                break;
            case 1:
                MyDataInfo.Dog dog = MyDataInfo.Dog.newBuilder().setName("dog").setCity("上海").build();
                myMessage = builder.setDataType(MyDataInfo.MyMessage.DataType.DogType).setDog(dog).build();
                break;
            case 2:
                MyDataInfo.Cat cat = MyDataInfo.Cat.newBuilder().setAge("20").setName("Cat").build();
                myMessage = builder.setDataType(MyDataInfo.MyMessage.DataType.CatType).setCat(cat).build();
                break;
            default:
                System.out.println("异常");
        }
        ctx.channel().writeAndFlush(myMessage);
    }
}

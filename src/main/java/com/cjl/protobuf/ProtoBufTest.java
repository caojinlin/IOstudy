package com.cjl.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder().setName("cjl").setAge(25).setAddress("上海").build();

        byte[] student2ByArray = student.toByteArray();

        DataInfo.Student student1 = DataInfo.Student.parseFrom(student2ByArray);
        System.out.println(student1);
        System.out.println(student1.getName());
        System.out.println(student1.getAge());
        System.out.println(student1.getAddress());
    }
}

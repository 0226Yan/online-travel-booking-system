package com.example.common;

import java.util.UUID;

public class MyUUID {
    public static void main(String[] args) {
        // 生成UUID
        UUID uuid = UUID.randomUUID();
        // 获取UUID的字符串表示
        String uuidString = uuid.toString();

        System.out.println("生成的UUID：" + uuidString);
    }
}
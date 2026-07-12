package com.example.common.enums;

public enum OrderEnum {
    STATUS_UNPAID("未支付"),
    STATUS_PAID("已支付"),
    STATUS_FINISHED("已完成"),
    ;

    public String status;
    OrderEnum(String status) { this.status = status; }
}

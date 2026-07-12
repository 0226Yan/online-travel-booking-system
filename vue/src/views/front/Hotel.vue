<template>
  <div class="main-content" style="margin-top: 30px">
    <div style="height: 300px; width: 70%; margin: 0 auto; display: flex">
      <div style="flex: 1.5;">
        <img :src="hotelData.hotelImg" alt="" style="width: 100%; height: 100%; border-radius: 10px">
      </div>
      <div style="flex: 2; margin-left: 30px;">
        <div style="font-size: 30px; font-weight: bold;">{{hotelData.hotelName}}</div>
        <div style="font-size: 18px; margin-top: 25px;">酒店地址：{{hotelData.hotelAddress}}</div>
        <div style="font-size: 18px; margin-top: 10px;">联系电话：{{hotelData.tel}}</div>
        <div style="font-size: 18px; margin-top: 10px;">酒店邮箱：{{hotelData.email}}</div>
        <div style="font-size: 18px; margin-top: 10px;">客房价格：
          <span style="color: red">¥{{hotelData.price}}</span><span style="color: #8D8C95">&nbsp;起</span></div>
        <div style="font-size: 18px; margin-top: 10px; overflow: hidden; text-overflow: ellipsis; display: -webkit-box;
        -webkit-box-orient: vertical;">酒店介绍：{{hotelData.description}}</div>
      </div>
    </div>

    <div style="height: 200px; width: 70%; margin: 40px auto">
      <div style="font-weight: bold; font-size: 25px; letter-spacing: 1px; ">房间种类</div>
      <el-row :gutter="70" style="margin-top: 15px">
        <el-col :span="4.8" :key="item.roomId" v-for="(item, index) in roomData" v-if="index < 4">
          <div :class="{'grayed-out': item.vacantNum === 0}" @click="navToDetail(item)">
            <img :src="item.roomImg" style="position: relative; width: 285px; height: 180px;
            border-top-right-radius: 10px; border-top-left-radius: 10px" alt="">
            <div class="background2" style="margin-top: -180px;"></div>
            <div class="sold-out" v-if="item.vacantNum === 0">已售罄</div>
            <div style="margin-top: -30px; margin-left:15px; font-size: 16px; font-weight: bold;">
              {{ item.roomName }}
              <span style="color: red; font-size: 13px;margin-left: 5px ">¥{{ item.price }}</span>
              <span style="font-size: 13px; color: #8D8C95; margin-left: 80px; font-weight: normal">
                 <i class="el-icon-s-home"></i>&nbsp;&nbsp;剩余{{ item.vacantNum}}间</span>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Hotel",
  data() {
    let hotelId = this.$route.query.hotelId
    return {
      hotelData: [],
      roomData: [],
      hotelId: hotelId,
    }
  },
  mounted() {
    this.loadHotels()
    this.loadRooms()
  },
  //methods:本页面所有的点击事件或者其他函数定义区
  methods: {
    loadHotels() {
      request.get("hotelInfo/selectById?hotelId=" + this.hotelId).then(res => {
        if(res.code === '0') {
          this.hotelData = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadRooms() {
      request.get("roomInfo/selectByHotelId?hotelId=" + this.hotelId).then(res => {
        if(res.code === '0') {
          this.roomData = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    navToDetail(item) {
      if (item.vacantNum == 0) {
        this.$message.warning("该类房型暂时没有空余房间，请选择其他房型")
        return;
      }
      location.href = '/travel/detail?roomId=' + item.roomId
    },
  }
}
</script>

<style>
.background2 {
  display: block;
  width: 285px;
  height: 220px;
  background-color: transparent;
  border-width: 2px;
  border-color: transparent;
  box-shadow: 4px 4px 5px 1px rgba(0, 0, 0, 0.5);
  border-radius: 10px;
  border-style: solid; /* 实线 */
}
.grayed-out {
  position: relative;
  opacity: 0.3; /* 透明度 */
  cursor: not-allowed; /* 禁止符号 */
}
.sold-out {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: black;
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  font-size: 14px;
  z-index: 1;
}
</style>

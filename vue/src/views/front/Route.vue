<template>
  <div class="main-content" style="margin-top: 30px">
    <div style="height: 371px; width: 70%; margin: 0 auto; display: flex">
      <div style="flex: 2;">
        <img :src="routeData.img" alt="" style="width: 100%; height: 100%; border-radius: 10px">
      </div>
      <div style="flex: 1; margin-left: 50px;">
        <div style="font-size: 30px; font-weight: bold; letter-spacing: 5px">{{routeData.tourName}}</div>
        <div style="font-size: 18px; margin-top: 25px;">出发地点：{{routeData.dept}}</div>
        <div style="font-size: 18px; margin-top: 25px;">出发日期：{{routeData.deptDate}}</div>
        <div style="font-size: 18px; margin-top: 25px;">出行天数：{{routeData.days}}</div>
        <div style="font-size: 18px; margin-top: 25px;">剩余名额：{{routeData.amount}}&nbsp;人</div>
        <div style="font-size: 18px; margin-top: 25px;">游玩价格：
          <span style="color: red">¥{{routeData.price}}</span></div>
        <div style="margin-top: 35px; margin-left: -3px">
          <el-button style="width: 180px; height: 50px; font-size: 17px; letter-spacing: 5px;" class="reserve_button"
                     @click="reserve" :disabled="isAmountZero()">
            <span style="font-weight: bold;">预&nbsp;&nbsp;&nbsp;订</span></el-button>
        </div>
      </div>
    </div>
    <div style="height: 200px; width: 70%; margin: 40px auto">
      <span style="font-weight: bold; font-size: 25px; letter-spacing: 1px;">简介</span>
      <div style="font-size: 18px; margin-top: 10px; ">{{routeData.tourDesc}}</div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Route",
  data() {
    let tourId = this.$route.query.tourId
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      routeData: [],
      tourId: tourId,
      form: {},
    }
  },
  mounted() {
    this.loadRoutes()
  },
  //methods:本页面所有的点击事件或者其他函数定义区
  methods: {
    loadRoutes() {
      request.get("routeInfo/selectById?tourId=" + this.tourId).then(res => {
        if(res.code === '0') {
          this.routeData = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reserve() {
      let data = {
        userId: this.user.userId,
        tourId: this.tourId,
      }
      request.post("routeOrder/add", data).then(res => {
        if (res.code === '0') {
          this.$message.success("预定成功")
          this.loadRoutes()
          this.$router.push("/travel/routeOrder");
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    isAmountZero() {
      return this.routeData.amount === 0;
    }
  }
}
</script>

<style>

</style>

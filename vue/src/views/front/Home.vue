<template>
  <div class="main-content">
    <div style="position: absolute; font-size: 40px; font-weight: bold; color:#fff;
                    font-style: italic; top:400px; left: 1000px; z-index:2; letter-spacing: 5px;">探索新旅途</div>
    <div style="height: 600px; position: relative;">
      <div style="display: flex; justify-content: center;">
        <img :src="publicPath + '1.jpg'" alt="Background Image" style="position: relative; width: 90%; height: 580px; border-radius: 50px; z-index:1;">
      </div>
    </div>

    <div style="text-align: center; margin-top: 100px; margin-bottom: -50px;font-size: 25px; font-weight: bold;
                letter-spacing: 5px">在线旅游系统</div>
    <div style="height: 100px; width: 60%; margin: 100px auto;display: flex; justify-content: space-around;">
      <div class="gray-circle">
        <i class="el-icon-data-line" style="color: #969696; font-size: 30px;"></i>
      </div>
      <div class="gray-circle">
        <i class="el-icon-pie-chart" style="color: #969696; font-size: 30px;"></i>
      </div>
      <div class="gray-circle">
        <i class="el-icon-discount" style="color: #969696; font-size: 30px;"></i>
      </div>
    </div>

    <div style="height: 10px;width: 60%;margin: 0 auto;display: flex; justify-content: space-around;">
      <div class="circle-text" style="margin-top: -80px; color: #969696; letter-spacing: 3px;">服务保障</div>
      <div class="circle-text" style="margin-top: -80px; color: #969696; letter-spacing: 3px;">种类丰富</div>
      <div class="circle-text" style="margin-top: -80px; color: #969696; letter-spacing: 3px;">优惠多多</div>
    </div>

    <div style="width: 100%; margin: -10px auto; text-align: left">
      <div style="text-align: center; margin-bottom: -50px;font-size: 25px;
                  font-weight: bold; letter-spacing: 8px">品质酒店</div>
      <div style="margin-top: 110px; display: flex; justify-content: center;">
        <el-row :gutter="70">
          <el-col :span="4.8" :key="item.hotelId" v-for="(item, index) in hotelData" v-if="index < 4">
            <div style="position: relative; overflow: hidden;">
              <img :src="item.hotelImg" style="width: 265px; height: 200px;
                 border-top-right-radius: 20px; border-top-left-radius: 20px" alt="" @click="navToHotel(item.hotelId)">
              <div v-if="item.offPrice === '是'" class="discount">
                今日特价
              </div>
            </div>
            <div class="background1" style="margin-top: -200px;"></div>
            <div style="margin-top: -49px; margin-left:15px; font-size: 16px; font-weight: bold; color: #454545">{{ item.hotelName }}</div>
            <div style="margin-top: 4px;margin-left:15px;font-size: 13px; font-weight: bold; color: red"> ¥{{ item.price }}
              <span style="color: #919090">起</span></div>
          </el-col>
        </el-row>
      </div>
      <div style="margin: 50px auto; text-align: center;">
        <el-button round style="width: 180px; height: 50px; font-size: 16px; font-weight: bold; letter-spacing: 10px;
                   margin-bottom: 20px" class="more1_button" @click="moreHotel">更多</el-button>
      </div>
    </div>

    <div style="width: 100%; margin: 10px auto; text-align: left">
      <div style="text-align: center; margin-bottom: -50px; font-size: 25px;
              font-weight: bold; letter-spacing: 8px">热门景点</div>
      <div style="margin-top: 110px; display: flex; justify-content: center;">
        <div class="block" style="display: flex; justify-content: center;">
          <el-carousel height="600px" trigger="click" style="width: 1265px">
            <el-carousel-item v-for="(item, index) in filteredSpotData.slice(0, 3)" :key="index">
              <div class="image-container">
                <img :src="item.spotImage" style="width: 1265px; height: 600px; border-radius: 20px;" alt="" @click="navToSpot(item.spotId)">
                <div class="ribbon" v-if="item.offPrice === '是'">今日特价</div>
                <div class="overlay"><p>{{ item.spotName }}</p></div>
              </div>
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>
      <div style="margin: 50px auto; text-align: center;">
        <el-button round style="width: 180px; height: 50px; font-size: 16px; font-weight: bold; letter-spacing: 10px"
                   class="more2_button" @click="moreSpot">更多</el-button>
      </div>
    </div>

    <div style="width: 100%; margin: 10px auto; text-align: left">
      <div style="text-align: center; margin-bottom: -50px; font-size: 25px;
                  font-weight: bold; letter-spacing: 8px">推荐线路</div>
      <div style="margin-top: 110px; display: flex; justify-content: center;">
        <el-row :gutter="70">
          <el-col :span="4.8" v-for="(item, index) in filteredRouteData.slice(0, 2)" v-if="index < 2" >
            <div style="position: relative; overflow: hidden;">
              <img :src="item.img" style="width: 600px; height: 350px; border-radius: 20px;" alt="" @click="navToRoute(item.tourId)">
              <div v-if="item.offPrice === '是'" class="discount">
                今日特价
              </div>
              <div style="position: absolute; margin-top: -50px; margin-left:20px; font-size: 18px; font-weight: bold;
                 letter-spacing: 3px; font-style: italic; color: #fff">
                {{ item.tourName }}
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <div style="margin: 50px auto; text-align: center;">
        <el-button round style="width: 180px; height: 50px; font-size: 16px; font-weight: bold; letter-spacing: 10px; margin-bottom: 10px"
                   class="more2_button" @click="moreRoute">更多</el-button>
      </div>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Home",
  data() {
    return {
      publicPath: process.env.BASE_URL,
      hotelData: [],
      spotData: [],
      routeData: [],
      isSpecialOffer: false,
    }
  },
  computed: {
    filteredSpotData() {
      return this.spotData.filter(item => item.amount > 0);
    },
    filteredRouteData() {
      return this.routeData.filter(item => item.amount > 0);
    }
  },
  mounted() {
    this.loadHotels();
    this.loadSpots();
    this.loadRoutes();
  },
  //methods:本页面所有的点击事件或者其他函数定义区
  methods: {
    loadHotels() {
      request.get("/hotelInfo/selectAll").then(res => {
        if (res.code === '0') {
          this.hotelData = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadSpots() {
      request.get("/spotInfo/selectAll").then(res => {
        if (res.code === '0') {
          this.spotData = res.data;
          // 根据offPrice的值设置isSpecialOffer的状态
          this.isSpecialOffer = this.spotData.some(item => item.offPrice === '是');
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadRoutes() {
      request.get("/routeInfo/selectAll").then(res => {
        if (res.code === '0') {
          this.routeData = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    navToHotel(hotelId) {
      this.$router.push({
        path: "/travel/hotel",
        query: { hotelId }
      });
    },
    navToSpot(spotId) {
      this.$router.push({
        path: "/travel/spot",
        query: { spotId }
      });
    },
    navToRoute(tourId) {
      this.$router.push({
        path: "/travel/route",
        query: { tourId }
      });
    },
    moreHotel() {
      this.$router.push("/travel/hotelReserve");
    },
    moreSpot() {
      this.$router.push("/travel/spotReserve");
    },
    moreRoute() {
      this.$router.push("/travel/routeReserve");
    }
  }
}
</script>

<style>
/* 形状 */
.gray-circle {
  width: 100px;
  height: 100px;
  background-color: #E8E8E8;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.circle-text {
  text-align: center;
  font-size: 14px;
}
.background1 {
  display: block;
  width: 265px;
  height: 250px;
  background-color: transparent;
  border-width: 2px;
  border-color: transparent;
  box-shadow: 4px 4px 5px 1px rgba(0, 0, 0, 0.5);
  border-radius: 20px;
  border-style: solid; /* 实线 */
}
.more1_button,
.more2_button {
  background-color: #fff !important;
  color: #0A0A0D !important;
  border-color: #0A0A0D;
}
.more1_button:hover,
.more2_button:hover {
  background-color: #0A0A0D  !important;
  color: #FFFFFF !important;
  border-color: #0A0A0D  !important;
}
.more1_button:focus
.more2_button:focus{
  background-color: #fff !important;
  color: #0A0A0D !important;
  border-color: #0A0A0D;
}
.image-container {
  position: relative;
  overflow: hidden;
}
/* 走马灯 */
.overlay {
  position: absolute;
  bottom: 37%;
  left: 0;
  width: 100%;
  height: 20%;
}
.overlay p {
  color: white;
  margin: 0;
  font-size: 60px;
  text-align: center;
  font-style: italic;
  letter-spacing: 30px;
  text-shadow: 4px 4px 6px black;
}
.ribbon {
  position: absolute;
  top: 23px;
  right: -60px;
  padding: 10px 100px;
  background-color: red;
  color: white;
  font-weight: bold;
  transform: rotate(30deg);
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.5);
}
</style>

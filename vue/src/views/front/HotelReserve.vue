<template>
  <div class="main-content">
    <div style="width: 100%; margin: 10px auto;">
      <div style="text-align: center; margin-bottom: -50px; font-size: 25px;
              font-weight: bold; letter-spacing: 8px">全部酒店</div>
    </div>

    <div style="position: relative; margin-top: 100px;">
      <div class="search" style="margin: 10px auto; position: relative;">
        <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); display: flex; width: 700px">
          <el-input v-model="hotelName" placeholder="请输入酒店名称" style="flex: 6" ></el-input>
          <el-input v-model="hotelAddress" placeholder="请输入酒店地址" style="flex: 6; margin-left: 30px" ></el-input>
          <el-button style="margin-left: 30px; flex: 1" class="button_search" @click="findBySearch()">
            <span style="font-weight: bold;">搜&nbsp;&nbsp;索</span></el-button>
          <el-button plain style="flex: 1" class="button_reset" @click="reset">
            <span style="font-weight: bold;">重&nbsp;&nbsp;置</span></el-button>
        </div>
      </div>
    </div>

    <div style="width: 100%; padding: 110px; text-align: left; margin-top: -100px;">
      <div style="margin-top: 60px; display: flex; justify-content: center; ">
        <el-row :gutter="70">
          <el-col :span="4.8" v-for="item in currentPageData" :key="item.hotelId">
            <div style="position: relative; overflow: hidden;">
              <img :src="item.hotelImg" style="width: 350px; height: 200px; margin: 0px auto; border-top-right-radius: 20px; border-top-left-radius: 20px" alt="" @click="navToHotel(item.hotelId)">
              <div v-if="item.offPrice === '是'" class="discount">
                今日特价
              </div>
            </div>
            <div class="background3" style="margin-top: -200px; "></div>
            <div style="margin-top: -49px; margin-left:15px; font-size: 16px; font-weight: bold; color: #454545">{{ item.hotelName }}</div>
            <div style="margin-top: 4px;margin-left:15px;font-size: 13px; font-weight: bold; color: red"> ¥{{ item.price }}
              <span style="color: #919090">起</span></div>
          </el-col>
        </el-row>
      </div>
      <div v-if="totalItems === 0" style="text-align: center; margin-top: 20px; font-size: 16px; color: #8D8C95; letter-spacing: 3px">
        暂无酒店
      </div>
    </div>

    <div class="block" style="display: flex; justify-content: center; margin-top: -20px">
      <el-pagination
          small
          layout="prev, pager, next"
          :total="totalItems"
          :current-page="currentPage"
          :page-size="pageSize"
          @current-change="handleCurrentChange">
      </el-pagination>
    </div>

  </div>
</template>


<script>
import request from "@/utils/request";

export default {
  name: "hotelReserve",
  data() {
    return {
      hotelData: [],
      filteredHotelData: [],
      hotelName:'',
      hotelAddress:'',
      currentPage: 1, // 当前页
      pageSize: 3, // 每页显示条数
    }
  },
  computed: {
    totalItems() {
      return this.filteredHotelData.length;
    },
    currentPageData() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.filteredHotelData.slice(startIndex, endIndex);
    },
  },
  mounted() {
    this.loadHotels();
  },
  //methods:本页面所有的点击事件或者其他函数定义区
  methods: {
    loadHotels() {
      request.get("/hotelInfo/selectAll").then(res => {
        if (res.code === '0') {
          this.hotelData = res.data;
          this.filteredHotelData = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    findBySearch() {
      // 过滤数据
      this.filteredHotelData = this.hotelData.filter(item => {
        const hotelNameMatch = item.hotelName.includes(this.hotelName);
        const hotelAddressMatch = item.hotelAddress.includes(this.hotelAddress);
        return hotelNameMatch && hotelAddressMatch;
      });
      this.currentPage = 1;
    },
    reset() {
      this.hotelName = '';
      this.hotelAddress = '';
      this.filteredHotelData = this.hotelData;
      this.currentPage = 1;
    },
    navToHotel(hotelId) {
      location.href = '/travel/hotel?hotelId=' + hotelId
    },
    handleCurrentChange(page) {
      this.currentPage = page;
    },
  }
}
</script>

<style>
.search {
  display: block;
  width: 800px;
  height: 100px;
  background-color: transparent;
  border-width: 1px;
  border-color: transparent;
  box-shadow: 0px 0px 10px 0.1px rgba(0, 0, 0, 0.5);
  border-style: solid; /* 实线 */
}
.search .el-input__inner:focus {
  border-color: #171717;
}
.button_search {
  background-color: #0A0A0D !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
.button_search:hover {
  background-color: #323332 !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border-color: #323332 !important;
}
.button_search:active {
  background-color: #0A0A0D !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
.button_reset{
  background-color: #fff !important;
  color: #0A0A0D !important; /* 按钮文本颜色 */
  border-color: #0A0A0D !important;
}
.button_reset:hover {
  background-color: #fff !important;
  color: #8D8C95 !important; /* 按钮文本颜色 */
  border-color: #8D8C95 !important;
}
/* 分页栏 */
.block .el-pager li{
  background-color: transparent; /* 设置数字背景颜色 */
  color: #171717; /* 设置数字颜色 */
}
.block .el-pager li:not(.disabled):hover {
  background-color: #E9E9E9; /* 设置数字背景颜色 */
  color: #171717; /* 设置数字颜色 */
  border-radius: 50%;
}
.block .el-pager li:not(.disabled).active {
  background-color: #171717; /* 设置数字背景颜色 */
  color: #FFFFFF; /* 设置数字颜色 */
  border-radius: 50%;
}
.el-pagination .el-input__inner {
  border-color: #EFEFEF !important;
}
.el-pagination button:hover {
  color: #919090 !important;
}
.background3 {
  display: block;
  width: 350px;
  height: 250px;
  background-color: transparent;
  border-width: 2px;
  border-color: transparent;
  box-shadow: 4px 4px 5px 1px rgba(0, 0, 0, 0.5);
  border-radius: 20px;
  border-style: solid; /* 实线 */
}
.discount {
  position: absolute;
  top: 8px;
  right: -30px;
  padding: 5px 40px;
  background-color: red;
  color: white;
  font-weight: bold;
  transform: rotate(30deg);
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.5);
}
</style>

<template>
  <div class="main-content">
    <div style="width: 100%; margin: 10px auto;">
      <div style="text-align: center; margin-bottom: -50px; font-size: 25px;
              font-weight: bold; letter-spacing: 8px">机票预订</div>
    </div>

    <div style="position: relative; margin-top: 100px;">
      <div class="search2" style="margin: 10px auto; position: relative;">
        <div style="position: absolute; top: 25%; left: 50%; transform: translate(-50%, -50%); display: flex; width: 700px">
          <el-input v-model="dept" placeholder="出发地" style="flex: 1" ></el-input>
          <el-input v-model="destn" placeholder="目的地" style="flex: 1; margin-left: 30px" ></el-input>
        </div>
        <div style="position: absolute; top: 55%; left: 50%; transform: translate(-50%, -50%); display: flex; width: 700px">
          <el-input v-model="number" placeholder="航班号" style="flex: 1" ></el-input>
          <el-date-picker v-model="deptTime" type="date" placeholder="出发日期" value-format="yyyy-MM-dd" style="flex: 1;margin-left: 30px"></el-date-picker>
        </div>
        <div style="position: absolute; top: 85%; left: 50%; transform: translate(-50%, -50%); display: flex; width: 250px">
          <el-button style=" flex: 1" class="button_search" @click="findBySearch()">
            <span style="font-weight: bold;">搜&nbsp;&nbsp;索</span></el-button>
          <el-button plain style="flex: 1; margin-left: 30px" class="button_reset" @click="reset">
            <span style="font-weight: bold;">重&nbsp;&nbsp;置</span></el-button>
        </div>
      </div>
    </div>

    <div style="width: 100%; padding: 70px; text-align: left; margin-top: -10px;">
      <div style="display: flex; justify-content: center; margin: 0 auto">
        <el-row :gutter="70" >
          <el-col :span="4.8" v-for="item in currentPageData" :key="item.flightId" >
            <div class="detail" style="position: relative; margin-top: 5px; display: flex; overflow: hidden;">
              <div v-if="item.offPrice === '是'" class="flight-discount">
                <span style="font-size: 11px">今日特价</span>
              </div>
              <div style="flex: 1">
                <div style="margin-top: 18px; margin-left:40px; font-size: 17px; font-weight: bold;
                 letter-spacing: 3px; color: #0A0A0D">
                  {{ item.company }}</div>
                <div style="margin-top: 3px; margin-left: 40px; font-size: 13px; font-weight: bold;">
                  {{ item.number }}</div>
                <div style="margin-top: 3px; margin-left: 40px; font-size: 13px; font-weight: bold; color: #8D8C95">
                  {{ item.flightName }}</div>
              </div>
              <div style="flex: 1">
                <div style="margin-top: 40px; margin-left:-20px; font-size: 17px; font-weight: bold;
                 letter-spacing: 3px; color: #0A0A0D">
                  <div> {{ item.type }} </div>
                </div>
              </div>
              <div style="flex: 2">
                <div style="margin-top: 30px; margin-left:-100px; font-size: 17px; font-weight: bold;
                 letter-spacing: 3px; color: #0A0A0D">
                  <div> {{ item.deptTime }} </div>
                  <div style="color: #8D8C95"> {{ item.dept }} </div>
                </div>
              </div>
              <div style="flex: 1; margin-top: 40px; margin-left: -260px">
                <span style="font-weight: bolder; color: #8D8C95;">-----------------------></span>
              </div>
              <div style="flex: 2">
                <div style="margin-top: 30px; margin-left:40px; font-size: 17px; font-weight: bold;
                 letter-spacing: 3px; color: #0A0A0D">
                  <div> {{ item.arrTime }} </div>
                  <div style="color: #8D8C95"> {{ item.destn }} </div>
                </div>
              </div>
              <div style="flex: 0.5; margin-top: 40px; margin-left: -100px">
                <span style="font-weight: bold; color: red">￥{{ item.price }}</span>
                <div style="color: #8D8C95; font-weight: bold; font-size: 12px">(余{{ item.vacantNum }}张)</div>
              </div>
              <div style="flex: 0.5; margin-top: 35px; margin-left: 5px">
                <el-button style="width: 80px" class="reserve_button" @click="reserve(item)" :disabled="isAmountZero(item)">
                  <span style="font-weight: bold;">预&nbsp;&nbsp;订</span></el-button>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <div v-if="totalItems === 0" style="text-align: center; margin-top: 20px; font-size: 16px; color: #8D8C95; letter-spacing: 3px">
        暂无航班
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
  name: "flightReserve",
  data() {
    let flightId = this.$route.query.flightId
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      flightData: [],
      filteredFlightData: [],
      flightId: flightId,
      dept:'',
      destn:'',
      number:'',
      deptTime:'',
      currentPage: 1, // 当前页
      pageSize: 3, // 每页显示条数
    }
  },
  computed: {
    totalItems() {
      return this.filteredFlightData.length;
    },
    currentPageData() {
      const filteredData = this.filteredFlightData.filter(item => item.vacantNum > 0);
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return filteredData.slice(startIndex, endIndex);
    },
  },
  mounted() {
    this.loadFlights();
  },
  //methods:本页面所有的点击事件或者其他函数定义区
  methods: {
    loadFlights() {
      request.get("/flightInfo/selectAll").then(res => {
        if (res.code === '0') {
          this.flightData = res.data;
          this.filteredFlightData = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    findBySearch() {
      // 过滤数据
      this.filteredFlightData = this.flightData.filter(item => {
        const deptMatch = item.dept.includes(this.dept);
        const destnMatch = item.destn.includes(this.destn);
        const numberMatch = item.number.includes(this.number);
        const deptTimeMatch = item.deptTime.includes(this.deptTime);
        return deptMatch && destnMatch && numberMatch && deptTimeMatch;
      });
      this.currentPage = 1;
    },
    reset() {
      this.dept = '';
      this.destn = '';
      this.number = '';
      this.deptTime = '';
      this.filteredFlightData = this.flightData;
      this.currentPage = 1;
    },
    handleCurrentChange(page) {
      this.currentPage = page;
    },
    reserve(row) {
      console.log(row);
      let data = {
        userId: this.user.userId,
        flightId: row.flightId,
      }
      request.post("flightOrder/add", data).then(res => {
        if (res.code === '0') {
          this.$message.success("预定成功")
          this.loadFlights()
          this.$router.push("/travel/flightOrder");
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    isAmountZero(row) {
      return row.vacantNum === 0;
      this.$message.warning("暂无机票")
    }
  }
}
</script>

<style>
.search2 {
  display: block;
  width: 800px;
  height: 200px;
  background-color: transparent;
  border-width: 1px;
  border-color: transparent;
  box-shadow: 0px 0px 10px 0.1px rgba(0, 0, 0, 0.5);
  border-style: solid; /* 实线 */
}
.detail {
  display: block;
  width: 1300px;
  height: 100px;
  margin-top: -25px;
  background-color: transparent;
  border-width: 2px;
  border-color: #F0F0F0;
/*  box-shadow: 0px 0px 10px 0.1px rgba(0, 0, 0, 0.5);*/
  border-style: solid; /* 实线 */
}
.detail:hover {

    box-shadow: 0px 0px 10px 0.1px rgba(0, 0, 0, 0.5);
}
.search .el-input__inner:focus {
  border-color: #171717;
}
.button_search,
.reserve_button {
  background-color: #0A0A0D !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
.button_search:hover,
.reserve_button:hover {
  background-color: #323332 !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border-color: #323332 !important;
}
.button_search:active,
.reserve_button:active {
  background-color: #0A0A0D !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
.reserve_button:disabled {
  background-color: #ccc !important; /* 灰色背景 */
  color: #666 !important; /* 灰色文本颜色 */
  border-color: #ccc !important; /* 灰色边框 */
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
/* 输入框 */
.el-input .el-input__inner:focus {
  border-color: #171717;
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
.flight-discount {
  position: absolute;
  top: 0px;
  right: -45px;
  padding: 3px 50px;
  background-color: red;
  color: white;
  font-weight: bold;
  transform: rotate(30deg);
  box-shadow: 0px 3px 3px 1px rgba(0, 0, 0, 0.5);
}
</style>

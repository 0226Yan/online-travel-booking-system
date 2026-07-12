<template>
  <div class="main-content" style="margin-top: 30px">
    <div style="height: 300px; width: 70%; margin: 0 auto; display: flex">
      <div style="flex: 1.5;">
        <img :src="spotData.spotImage" alt="" style="width: 100%; height: 100%; border-radius: 10px">
      </div>
      <div style="flex: 2; margin-left: 30px;">
        <div style="font-size: 30px; font-weight: bold;">{{spotData.spotName}}</div>
        <div style="font-size: 18px; margin-top: 25px;">景点地址：{{spotData.spotAddress}}</div>
        <div style="font-size: 18px; margin-top: 25px;">开放时间：{{spotData.openingHours}}</div>
        <div style="font-size: 18px; margin-top: 25px;">剩余票数：{{spotData.amount}}&nbsp;张</div>
        <div style="font-size: 18px; margin-top: 25px;">门票价格：
          <span style="color: red; font-size: 18px;">¥&nbsp;{{spotData.price}}</span></div>
        <div style="margin-top: 20px; margin-left: -3px">
          <el-button style="width: 175px; height: 42px; font-size: 17px; letter-spacing: 5px;" class="reserve_button"
                     @click="reserve">
            <span style="font-weight: bold;">预&nbsp;&nbsp;&nbsp;订</span></el-button>
        </div>
      </div>
    </div>

    <div style="height: 200px; width: 70%; margin: 40px auto;">
      <span style="font-weight: bold; font-size: 25px; letter-spacing: 1px; ">简介</span>
      <div style="font-size: 18px; margin-top: 10px; ">{{spotData.description}}</div>
    </div>

    <div>
      <!--嵌套表单-->
      <el-dialog :visible.sync="dialogFormVisible" width="30%"  :modal-append-to-body="false">
        <template slot="title">
          <div style="text-align: center;">请确认订单信息</div>
        </template>
        <div>
          <div style="font-size: 13px; margin: 10px 80px; letter-spacing: 4px">用户名：{{ user.username }}</div>
          <div style="font-size: 13px; margin: 10px 80px;">开放时间：{{spotData.openingHours}}</div>
          <div style="font-size: 13px; margin: 10px 80px;">景点名称：{{spotData.spotName}}</div>
          <div style="font-size: 13px; margin: 10px 80px;">景点地址：{{spotData.spotAddress}}</div>
        </div>
        <div slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
          <el-button round style="width: 100px" @click="submit()" class="sure_button">去支付</el-button>
          <el-button round style="width: 100px" @click="cancel()" class="cancel_button">再想想</el-button>
        </div>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Spot",
  data() {
    let spotId = this.$route.query.spotId
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      spotData: [],
      spotId: spotId,
      form: {},
      dialogFormVisible: false,
    }
  },
  mounted() {
    this.loadSpots()
  },
  //methods:本页面所有的点击事件或者其他函数定义区
  methods: {
    loadSpots() {
      request.get("spotInfo/selectById?spotId=" + this.spotId).then(res => {
        if(res.code === '0') {
          this.spotData = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reserve() {
      this.dialogFormVisible = true;
    },
    submit() {
      let data = {
        userId: this.user.userId,
        spotId: this.spotId,
      }
      request.post("spotOrder/add", data).then(res => {
        if (res.code === '0') {
          this.$message.success("预订成功")
          this.$router.push("/travel/spotOrder");
          this.loadSpots()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    cancel() {
      this.dialogFormVisible = false;
    }
  }
}
</script>

<style>

</style>

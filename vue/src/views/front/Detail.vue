<template>
  <div class="main-content" style="margin-top: 30px">
    <div style="width: 80%; margin: 10px auto; ">
      <div style="font-weight: 700; font-size: 18px; padding: 15px 30px;
           border-bottom: 1px solid #eeeeee">详情信息</div>
      <div style="display: flex; height: 100px; margin-top: 20px; padding: 0 28px">
        <div style="flex: 3; margin-right: 10px">
          <img :src="roomData.roomImg" alt="" style="width: 100%; height: 480px; border-radius: 5px;">
        </div>
        <div style="flex: 2; margin-left: 10px">
          <div style="font-size: 16px; font-weight: bold; margin: 0 15px">基本信息</div>
          <div style="font-size: 14px; margin: 15px 15px; display: flex; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>类型：{{roomData.roomName}}
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>间数：{{roomData.vacantNum}}
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>酒店：{{roomData.hotelName}}
            </span>
          </div>

          <div style="font-size: 16px; font-weight: bold; margin: 15px 15px">洗浴用品</div>
          <div style="font-size: 14px; margin: 15px 15px; display: flex; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>牙刷
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>牙膏
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>沐浴露
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>洗发水
            </span>
          </div>
          <div style="font-size: 14px; margin: 15px 15px; display: flex; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>护发素
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>香皂
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>梳子
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>剃须刀
            </span>
          </div>

          <div style="font-size: 16px; font-weight: bold; margin: 15px 15px">卫浴设施</div>
          <div style="font-size: 14px; margin: 15px 15px; display: flex; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>浴室
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>卫生间
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>吹风机
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>淋浴
            </span>
          </div>
          <div style="font-size: 14px; margin: 15px 15px; display: flex; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>毛巾
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>浴巾
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>拖鞋
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>24h热水
            </span>
          </div>

          <div style="font-size: 16px; font-weight: bold; margin: 15px 15px">客房设施</div>
          <div style="font-size: 14px; margin: 15px 15px; display: flex; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>空调
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>手动窗帘
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>鸭绒被
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>毛毯
            </span>
          </div>

          <div style="font-size: 16px; font-weight: bold; margin: 15px 15px">媒体科技</div>
          <div style="font-size: 14px; margin: 15px 15px; display: flex; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>电视机
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>有线频道
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>智能门锁
            </span>
            <span style="flex: 1;">
              <i class="el-icon-circle-check" style="margin-right: 5px"></i>智能客控
            </span>
          </div>

          <div style="margin-left: 15px; color: red; font-size: 16px">价格：¥ {{roomData.price}} / 晚</div>

          <div style="margin-left: 15px; margin-top: 15px;">
            <el-button style="width: 145px; font-size: 15px; letter-spacing: 3px;" class="reserve_button" @click="initDialog">
              <span style="font-weight: bold;">预&nbsp;&nbsp;&nbsp;订</span></el-button>
          </div>
        </div>
      </div>
    </div>
    <el-dialog title="请选择时间" :visible.sync="fromVisible" width="30%" :close-on-click-modal="false" destroy-on-close style="text-align: center">
      <el-form label-width="100px" style="padding-right: 50px" :model="form">
        <el-form-item prop="inTime" label="入住时间">
          <el-date-picker v-model="inTime" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width: 100%"></el-date-picker>
        </el-form-item>
        <el-form-item prop="outTime" label="离开时间">
          <el-date-picker v-model="outTime" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width: 100%"></el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button round @click="fromVisible = false" class="cancel_button" style="width: 25%">取 消</el-button>
        <el-button round @click="save" class="sure_button" style="width: 25%">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Detail",
  data() {
    let roomId = this.$route.query.roomId
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      roomData: {},
      roomId: roomId,
      form: {},
      inTime: null,
      outTime: null,
      fromVisible: false,
    }
  },
  mounted() {
    this.loadRooms()
  },
  //methods:本页面所有的点击事件或者其他函数定义区
  methods: {
    loadRooms() {
      request.get("roomInfo/selectById?roomId=" + this.roomId).then(res => {
        if(res.code === '0') {
          this.roomData = res.data;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    initDialog() {
      this.fromVisible = true
    },
    save() {
      let data = {
        userId: this.user.userId,
        roomId: this.roomId,
        hotelId: this.roomData.hotelId,
        inTime: this.inTime,
        outTime: this.outTime
      }
      request.post("hotelOrder/add",data).then(res => {
        if(res.code === '0') {
          this.$message.success("预订成功")
          this.fromVisible = false
          this.loadRooms()
          this.$router.push("/travel/hotelOrder");
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style>
.cancel_button:hover {
  background-color: #FAFAFA !important;
  color: #8D8C95 !important; /* 按钮文本颜色 */
  border-color: #8D8C95 !important;
}
.sure_button {
  background-color: #0A0A0D !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
.sure_button:hover {
  background-color: #323332 !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border-color: #323332 !important;
}
.sure_button:active {
  background-color: #0A0A0D !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
</style>

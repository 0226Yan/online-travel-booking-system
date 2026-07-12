<template>
  <div style="margin-left: 240px; width: 65%">
    <div style="margin: 20px 0; font-weight: bold">系统公告</div>
    <el-collapse v-model="activeName" accordion>
      <el-collapse-item v-for="item in data" :title="item.noticeName" :name="item.noticeId" class="bold-title">
        <div style="color: #0A0A0D; padding-left: 10px;">{{item.content}}</div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: 'notice',
  data() {
    return {
      activeName: '1',
      data:[]
    };
  },
  mounted() {
    this.findNotice();
  },
  methods: {
    findNotice() {
      request.get("/noticeInfo").then(res => {
        if(res.code === '0') {
          this.data = res.data;
          this.activeName = res.data[0].noticeId;
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style>
.el-collapse-item__header {
  font-weight: bold !important;
  padding-left: 10px !important; /* 向右移动标题文字 */
}
</style>

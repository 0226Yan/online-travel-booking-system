<template>
  <div>
    <div style="margin-top: 10px">
      <el-input v-model="hotelOrdersData.orderId" style="width: 200px; margin-left: 240px"
                class="name_input" placeholder="请输入订单号"></el-input>
      <el-button type="info" plain icon="el-icon-search" class="search_button"
                 circle style="margin-left: 5px" @click="load(1)"></el-button>
      <el-button type="info" plain icon="el-icon-refresh" class="refresh_button"
                 circle style="margin-left: 5px" @click="refresh()"></el-button>
    </div>
    <div style="margin-left: 240px;margin-top: 30px" class="table" >
      <!--表单-->
      <el-table
          :data="hotelOrdersData"
          :header-cell-style="{color: '#323335',fontSize: '14px', backgroundColor: '#F0F0F0'}"
          style="width: 100%; ">
        <el-table-column prop="id"         label="序号"     align="center"  :width="50"></el-table-column>
        <el-table-column label="房间图片"   align="center" :width="100">
          <template v-slot="scope">
            <el-image
                style="width: 70px; height: 70px; border-radius: 50%"
                :src="'http://localhost:8080/api/files/' + scope.row.roomImg"
                :preview-src-list="['http://localhost:8080/api/files/' + scope.row.roomImg]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="orderId"      label="订单编号"      align="center" ></el-table-column>
        <el-table-column prop="userName"     label="用户"         align="center"></el-table-column>
        <el-table-column prop="hotelName"    label="酒店"         align="center"></el-table-column>
        <el-table-column prop="roomName"     label="房间类型"      align="center"></el-table-column>
        <el-table-column prop="time"         label="预订时间"      align="center" :width="90"></el-table-column>
        <el-table-column prop="price"        label="价格"         align="center" :width="60"></el-table-column>
        <el-table-column prop="inTime"       label="入住时间"      align="center" :width="90"></el-table-column>
        <el-table-column prop="outTime"      label="离开时间"      align="center" :width="90"></el-table-column>
        <el-table-column prop="days"         label="入住天数"      align="center"></el-table-column>
        <el-table-column prop="status"       label="订单状态"      align="center"></el-table-column>
        <el-table-column label="操作" align="center" :width="120">
          <template slot-scope="scope">
            <el-button plain class="finish_button" size="mini" @click="finished(scope.row.id)"
                       v-if="!isDisabled(scope.row.status)" style="font-weight: bold">完成</el-button>
            <el-button plain v-else-if="scope.row.status === '已完成'" class="delete" size="mini" @click=del(scope.row.id) style="font-weight: bold">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div>
      <!--分页栏-->
      <el-pagination
          small
          class="custom-pagination"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "HotelOrderInfoView",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      hotelOrdersData: {},
      pageNum: 1,
      pageSize: 5,
      total: 0,
    }
  },
  //页面加载时，created中做的事情
  created() {
    this.load(1)
  },
  //定义页面上触发事件调用的方法
  methods: {
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum
      request.get("/hotelOrder/selectPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          orderId: this.hotelOrdersData.orderId,
        }
      }).then(res => {
        if (res.code === '0') {
          this.hotelOrdersData = res.data?.list
          this.total = res.data?.total
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    refresh() {
      this.orderId = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    del(id) {   // 单个删除
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(response => {
        request.delete('/hotelOrder/delete/' + id).then(res => {
          if (res.code === '0') {
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    finished(id) {
      request.get('/hotelOrder/finished/' + id).then(res => {
        if(res.code === '0') {
          this.$message.success("订单已完成")
          this.load(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    isDisabled(status) {
      return status === '已完成' || status === '未支付';
    }
  }
}
</script>
<style>
/* 搜索按钮 / 刷新按钮 / 新增按钮 */
.search_button,
.refresh_button,
.add_button {
  background-color: #EFEFEF !important;
  color: #272727 !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
.search_button:hover,
.refresh_button:hover,
.add_button:hover {
  background-color: #EFEFEF !important;
  color: #272727 !important; /* 按钮文本颜色 */
  border-color: #272727 !important;
}
.search_button:active,
.refresh_button:active,
.add_button:active {
  background-color: #272727 !important;
  color: #EFEFEF !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
/* 退房按钮 */
.finish_button {
  background-color: #2F6DB8 !important;
  color: #fff !important; /* 按钮文本颜色 */
  border-color: #2F6DB8 !important;
}
.finish_button:hover {
  background-color: #2F6DB8 !important;
  color: #F8F8F8 !important; /* 按钮文本颜色 */
  border-color: #2F6DB8 !important;
}
.finish_button:disabled {
  background-color: #ccc !important; /* 灰色背景 */
  color: #666 !important; /* 灰色文本颜色 */
  border-color: #ccc !important; /* 灰色边框 */
}
/* 删除按钮 */
.delete {
  background-color: #F93423 !important;
  color: #fff !important; /* 按钮文本颜色 */
  border-color: #F93423 !important;
}
.delete:hover {
  background-color: #F93423 !important;
  color: #F8F8F8 !important; /* 按钮文本颜色 */
  border-color: #F93423 !important;
}
.delete:active {
  background-color: #F93423 !important;
  color: #EFF1F7 !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}

/* 设置输入框 */
.name_input .el-input__inner {
  border-radius: 30px;
  border-color:#F0F0F0;
  background-color: #EFEFEF;
}
.name_input .el-input__inner:focus {
  border-color:#F0F0F0;
}
/* 设置分页栏 */
.el-pagination {
  text-align: right;
}
.custom-pagination .el-pager li{
  background-color: transparent; /* 设置数字背景颜色 */
  color: #171717; /* 设置数字颜色 */
}
.custom-pagination .el-pager li:not(.disabled):hover {
  background-color: #E9E9E9; /* 设置数字背景颜色 */
  color: #171717; /* 设置数字颜色 */
  border-radius: 50%;
}
.custom-pagination .el-pager li:not(.disabled).active {
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
.el-select-dropdown .el-select-dropdown__item {
  color: #919090 !important;
}
</style>
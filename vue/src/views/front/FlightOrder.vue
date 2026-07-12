<template style="background-color: #FAFAFA">
  <div class="main-content" >
    <div class="table" style="width: 80%; margin: 20px auto">
      <el-table
          :data="flightOrdersData"
          :header-cell-style="{color: '#8D8C95',fontSize: '14px', backgroundColor: '#FAFAFA'}"
          style="width: 100%; ">
        <el-table-column prop="orderId"      label="订单编号"     align="center" ></el-table-column>
        <el-table-column prop="userName"     label="用户"         align="center"></el-table-column>
        <el-table-column prop="number"       label="航班号"      align="center"></el-table-column>
        <el-table-column prop="dept"         label="出发地"      align="center"></el-table-column>
        <el-table-column prop="destn"        label="目的地"      align="center"></el-table-column>
        <el-table-column prop="company"      label="航司"      align="center"></el-table-column>
        <el-table-column prop="type"         label="类型"      align="center"></el-table-column>
        <el-table-column prop="price"        label="价格"         align="center"></el-table-column>
        <el-table-column prop="status"       label="订单状态"      align="center"></el-table-column>
        <el-table-column label="操作" align="center" :width="160">
          <template slot-scope="scope">
            <el-button plain class="finish_button" size="mini" @click=pay(scope.row.id)  v-if="!isDisabled(scope.row.status)">支付</el-button>
            <el-button plain class="delete" size="mini" @click=del(scope.row.id)  v-if="!isCancel(scope.row.status)" style="float: right;">取消</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="margin-top: 20px; width:90%;">
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
  name: "FlightOrder",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      flightOrdersData: [],
      flightData: {},
      pageNum: 1,
      pageSize: 10,
      total: 0,
    }
  },
  mounted() {
    this.loadFlightOrders(1)
  },
  //methods:本页面所有的点击事件或者其他函数定义区
  methods: {
    loadFlightOrders(pageNum) {
      if (pageNum) this.pageNum = pageNum
      request.get('/flightOrder/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.user.userId,
        }
      }).then(res => {
        if (res.code === '0') {
          this.flightOrdersData = res.data?.list
          this.total = res.data?.total
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    /* 当前页 */
    handleCurrentChange(pageNum) {
      this.loadFlightOrders(pageNum)
    },    pay(id) {
      request.get('/flightOrder/pay/' + id).then(res => {
        if(res.code === '0') {
          this.$message.success("支付成功")
          this.loadFlightOrders(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定取消订单吗？', '确认取消', {type: "warning"}).then(response => {
        request.delete('/flightOrder/delete/' + id).then(res => {
          if (res.code === '0') {
            this.$message.success('操作成功')
            this.loadFlightOrders(1)
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {
      })
    },
    isDisabled(status) {
      return status === '已完成' || status === '已支付';
    },
    isCancel(status) {
      return status === '已完成';
    },

  }
}
</script>

<style>
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

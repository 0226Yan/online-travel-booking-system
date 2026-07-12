<template style="background-color: #FAFAFA">
  <div class="main-content" >
    <div class="table" style="width: 80%; margin: 20px auto">
      <el-table
          :data="hotelOrdersData"
          :header-cell-style="{color: '#8D8C95',fontSize: '14px', backgroundColor: '#FAFAFA'}"
          style="width: 100%; ">
        <el-table-column label="房间图片"   align="center" :width="120">
          <template v-slot="scope">
            <el-image
                style="width: 80px; height: 80px; border-radius: 50%"
                :src="'http://localhost:8080/api/files/' + scope.row.roomImg"
                :preview-src-list="['http://localhost:8080/api/files/' + scope.row.roomImg]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="orderId"      label="订单编号"     align="center" ></el-table-column>
        <el-table-column prop="userName"     label="用户"         align="center"></el-table-column>
        <el-table-column prop="hotelName"    label="酒店"         align="center"></el-table-column>
        <el-table-column prop="roomName"     label="房间类型"      align="center"></el-table-column>
        <el-table-column prop="time"         label="预订时间"      align="center"></el-table-column>
        <el-table-column prop="price"        label="价格"         align="center"></el-table-column>
        <el-table-column prop="inTime"       label="入住时间"      align="center"></el-table-column>
        <el-table-column prop="outTime"      label="离开时间"      align="center"></el-table-column>
        <el-table-column prop="days"         label="入住天数"      align="center"></el-table-column>
        <el-table-column prop="status"       label="订单状态"      align="center"></el-table-column>
        <el-table-column label="操作" align="center" :width="160">
          <template slot-scope="scope">
            <el-button plain v-if="!isDisabled(scope.row.status)" class="finish_button" size="mini" @click=pay(scope.row.id)>支付</el-button>
            <el-button plain v-if="!isCancel(scope.row.status)" class="delete" size="mini" @click=del(scope.row.id) :disabled="isCancel(scope.row.status)" style="float: right;">取消</el-button>
<!--            <el-button plain v-if="!isSale(scope.row.status)" style="font-weight: bold" class="sale" size="mini" @click="sale()" >售后</el-button>-->
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

    <!--售后-->
<!--    <el-dialog :visible.sync="dialogFormVisible" width="30%"  :modal-append-to-body="false">
      <template slot="title">
        <div style="text-align: center;">请输入您遇到的问题</div>
      </template>
      <el-form>
        <el-input type="textarea" autocomplete="off"></el-input>
      </el-form>
      <div slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button round style="width: 100px" @click="dialogFormVisible = false" class="cancel_button">取 消</el-button>
        <el-button round style="width: 100px" @click="checkPassword()" class="sure_button">确 定</el-button>
      </div>
    </el-dialog>-->

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "HotelOrder",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      hotelOrdersData: [],
      dialogFormVisible: false,
      pageNum: 1,
      pageSize: 10,
      total: 0,
    }
  },
  mounted() {
    this.loadHotelOrders(1)
  },
  //methods:本页面所有的点击事件或者其他函数定义区
  methods: {
    loadHotelOrders(pageNum) {
      if (pageNum) this.pageNum = pageNum
      request.get('/hotelOrder/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.user.userId,
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
    /* 当前页 */
    handleCurrentChange(pageNum) {
      this.loadHotelOrders(pageNum)
    },
    pay(id) {
      request.get('/hotelOrder/pay/' + id).then(res => {
        if(res.code === '0') {
          this.$message.success("支付成功")
          this.loadHotelOrders(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    del(id) {   // 单个删除
      this.$confirm('您确定取消订单吗？', '确认取消', {type: "warning"}).then(response => {
        request.delete('/hotelOrder/delete/' + id).then(res => {
          if (res.code === '0') {
            this.$message.success('操作成功')
            this.loadHotelOrders(1)
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
    /*isSale(status) {
      return status === '未支付' || status === '已支付';
    },
    sale(){
      this.dialogFormVisible = true;
    }*/
  }
}
</script>

<style>
.sale {
  background-color: #FFFFFF !important;
  color: #F3C443 !important; /* 按钮文本颜色 */
  border-color: #F3C443 !important;
}
.sale:hover {
  background-color: #F3C443 !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border-color: #F3C443 !important;
}
.sale:active {
  background-color: #FFFFFF !important;
  color: #F3C443 !important; /* 按钮文本颜色 */
  border-color: #F3C443 !important;
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

<template>
  <div>
    <div style="margin-top: 10px">
      <el-input v-model="spotOrdersData.orderId" style="width: 200px; margin-left: 240px"
                class="name_input" placeholder="请输入订单号"></el-input>
      <el-button type="info" plain icon="el-icon-search" class="search_button"
                 circle style="margin-left: 5px" @click="load(1)"></el-button>
      <el-button type="info" plain icon="el-icon-refresh" class="refresh_button"
                 circle style="margin-left: 5px" @click="refresh()"></el-button>
    </div>
    <div style="margin-left: 240px;margin-top: 30px" class="table" >
      <!--表单-->
      <el-table
          :data="spotOrdersData"
          :header-cell-style="{color: '#323335',fontSize: '14px', backgroundColor: '#F0F0F0'}"
          style="width: 100%; ">
        <el-table-column prop="id"         label="序号"     align="center"  :width="50"></el-table-column>
        <el-table-column label="景点图片"   align="center" :width="100">
          <template v-slot="scope">
            <el-image
                style="width: 70px; height: 70px; border-radius: 50%"
                :src="'http://localhost:8080/api/files/' + scope.row.spotImage"
                :preview-src-list="['http://localhost:8080/api/files/' + scope.row.spotImage]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="orderId"      label="订单编号"      align="center" ></el-table-column>
        <el-table-column prop="userName"     label="用户"         align="center"></el-table-column>
        <el-table-column prop="spotName"     label="景点名称"         align="center"></el-table-column>
        <el-table-column prop="time"         label="预订时间"      align="center"></el-table-column>
        <el-table-column prop="price"        label="价格"         align="center"></el-table-column>
        <el-table-column prop="status"       label="订单状态"      align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button plain class="finish_button" size="mini" @click="finished(scope.row.id)"
                       v-if="scope.row.status === '已支付'" style="font-weight: bold">完成</el-button>
            <el-button plain v-else-if="scope.row.status === '已完成'" type="danger" size="mini" class="delete" @click=del(scope.row.id)>删除</el-button>
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
  name: "SpotOrderInfoView",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      spotOrdersData: {},
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
      request.get("/spotOrder/selectPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          orderId: this.spotOrdersData.orderId,
        }
      }).then(res => {
        if (res.code === '0') {
          this.spotOrdersData = res.data?.list
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
        request.delete('/spotOrder/delete/' + id).then(res => {
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
      request.get('/spotOrder/finished/' + id).then(res => {
        if(res.code === '0') {
          this.$message.success("订单已完成")
          this.load(1)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
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
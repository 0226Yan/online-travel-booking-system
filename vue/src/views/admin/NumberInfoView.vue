<template>
  <div>
    <div style="margin-top: 10px">
      <el-input v-model="params.name" style="width: 200px; margin-left: 240px"
                class="name_input" placeholder="请输入房间编号"></el-input>
      <el-button type="info" plain icon="el-icon-search" class="search_button"
                 circle style="margin-left: 5px" @click="findBySearch()"></el-button>
      <el-button type="info" plain icon="el-icon-refresh" class="refresh_button"
                 circle style="margin-left: 5px" @click="refresh()"></el-button>
      <el-button type="info" plain icon="el-icon-plus"  class="add_button"
                 circle style="margin-left: 5px" @click="add()"></el-button>
    </div>
    <div style="margin-left: 240px;margin-top: 30px" class="admin_table">
      <!--表单-->
      <el-table
          :data="tableData"
          :header-cell-style="{color: '#323335',fontSize: '14px', backgroundColor: '#F0F0F0'}"
          style="width: 100%; ">
        <el-table-column prop="id"          label="序号"      align="center" :width="50"></el-table-column>
        <el-table-column prop="name"        label="房间编号"   align="center"></el-table-column>
        <el-table-column prop="status"      label="房间状态"   align="center"></el-table-column>
        <el-table-column prop="roomId"      label="所属房型"   align="center"></el-table-column>
        <el-table-column prop="hotelId"     label="所属酒店"   align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button icon="el-icon-edit" class="edit_button" circle @click="edit(scope.row)"></el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
              <el-button slot="reference" icon="el-icon-delete"
                         class="delete_button" circle style="margin-left: 10px"></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div>
      <!--分页栏-->
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
    <div>
      <!--嵌套表单-->
      <el-dialog :visible.sync="dialogFormVisible" width="35%"  :modal-append-to-body="false">
        <template slot="title">
          <div style="text-align: center;">请填写信息</div>
        </template>
        <el-form :model="form" >
          <el-form-item label="房间编号" label-width="30%">
            <el-input v-model="form.name" autocomplete="off" style="width: 70%"></el-input>
          </el-form-item>
          <el-form-item label="房间状态" label-width="30%">
            <el-input v-model="form.status" autocomplete="off" style="width: 70%"></el-input>
          </el-form-item>
          <el-form-item label="所属房型" label-width="30%" class="type-select">
            <el-select v-model="form.roomId" placeholder="请选择" style="width: 70%">
              <el-option
                  v-for="item in roomObjs" :key="item.roomId" :label="item.roomName" :value="item.roomId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属酒店" label-width="30%" class="type-select">
            <el-select v-model="form.hotelId" placeholder="请选择" style="width: 70%">
              <el-option
                  v-for="item in roomObjs" :key="item.hotelId" :label="item.hotelName" :value="item.hotelId">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
          <el-button round style="width: 100px" @click="dialogFormVisible = false" class="cancel_button">取 消</el-button>
          <el-button round style="width: 100px" @click="submit()" class="sure_button">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "NumberInfoView",
  data() {
    return {
      params: {
        name: '',
        pageNum: 1,
        pageSize: 5
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false, //不能看到dialog
      form: {},
      roomObjs: [],
    }
  },
  //页面加载时，created中做的事情
  created() {
    this.findBySearch();
    this.findRooms();
  },
  //定义页面上触发事件调用的方法
  methods: {
    findRooms() {
      request.get("/roomInfo/search").then(res => {
        if(res.code === '0') {
          this.roomObjs = res.data;
          console.log(this.roomObjs)
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    //查询函数
    findBySearch() {
      request.get("/numberInfo/search",{
        params: this.params
      }).then(res => {
        if(res.code === '0'){
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    edit(obj) {
      this.form = obj;
      this.dialogFormVisible = true;
    },
    refresh() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: ''
      };
      this.findBySearch();
    },
    /* 每页条数 */
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.findBySearch();
    },
    /* 当前页 */
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.findBySearch();
    },
    submit() {
      request.post("/numberInfo",this.form).then(res => {
        if (res.code === '0') {
          this.$message.success("操作成功");
          this.dialogFormVisible = false;
          this.findBySearch();
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    del(id) {
      request.delete("/numberInfo/" + id).then(res => {
        if(res.code === '0') {
          this.$message.success("删除成功");
          this.findBySearch();
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    successUpload(res) {
      this.form.roomImg = res.data;
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
/* 编辑按钮 */
.edit_button {
  background-color: #fff !important;
  color: #2F6DB8 !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
.edit_button:hover {
  background-color: #F8F8F8 !important;
  color: #2F6DB8 !important; /* 按钮文本颜色 */
  border-color: #2F6DB8 !important;
}
.edit_button:active {
  background-color: #2F6DB8 !important;
  color: #EFF1F7 !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
/* 删除按钮 */
.delete_button {
  background-color: #fff !important;
  color: #F93423 !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
.delete_button:hover {
  background-color: #F8F8F8 !important;
  color: #F93423 !important; /* 按钮文本颜色 */
  border-color: #F93423 !important;
}
.delete_button:active {
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
.address_input .el-input__inner {
  border-radius: 30px;
  border-color:#F0F0F0;
  background-color: #EFEFEF;
}
.address_input .el-input__inner:focus {
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
/* 设置内嵌表单 */
.el-dialog input.el-input__inner:focus {
  border-color: #171717;
}
.el-dialog .el-form-item .el-textarea__inner:focus {
  border-color: #171717 !important;
}
.type-select .el-input.is-focus .el-input__inner {
  border-color: #171717 !important; /* 这里更改为您想要的边框颜色 */
}
.custom-radio .el-radio__inner:hover {
  border-color: #8D8C95;
}
.custom-radio .el-radio__input.is-checked .el-radio__inner {
  border-color: #8D8C95 !important; /* 设置radio按钮选中时的边框颜色 */
  background-color: #8D8C95 !important; /* 设置radio按钮选中时的背景颜色 */
}
.custom-radio .el-radio__input.is-checked + .el-radio__label {
  color: #171717 !important; /* 设置radio按钮选中时的文字颜色 */
}
.cancel_button:hover {
  background-color: #FAFAFA !important;
  color: #8D8C95 !important; /* 按钮文本颜色 */
  border-color: #FAFAFA !important;
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
/* 上传图片按钮 */
.upload_button {
  background-color: #8D8C95 !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border: none !important; /* 去除按钮边框 */
}
.upload_button:hover {
  background-color: #323332 !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border: none !important;
}
.upload_button:active {
  background-color: #8D8C95 !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}
</style>
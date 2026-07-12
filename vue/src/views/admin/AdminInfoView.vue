<template>
  <div>
    <div style="margin-top: 10px">
      <el-input v-model="params.username" style="width: 200px; margin-left: 240px"
                class="name_input" placeholder="请输入用户名"></el-input>
      <el-button type="info" plain icon="el-icon-search" class="search_button"
                 circle style="margin-left: 5px" @click="findBySearch()"></el-button>
      <el-button type="info" plain icon="el-icon-refresh" class="refresh_button"
                 circle style="margin-left: 5px" @click="refresh()"></el-button>
      <el-button type="info" plain icon="el-icon-plus"  class="add_button"
                 circle style="margin-left: 5px" @click="add()"></el-button>
    </div>
    <div style="margin-left: 240px;margin-top: 30px" class="admin_table" >
      <!--表单-->
      <el-table
          :data="tableData"
          :header-cell-style="{color: '#323335',fontSize: '14px', backgroundColor: '#F0F0F0'}"
          style="width: 100%; ">
        <el-table-column prop="adminId"  label="序号"   align="center" :width="50"></el-table-column>
        <el-table-column label="头像"   align="center">
          <template v-slot="scope">
            <el-image
                style="width: 60px; height: 60px; border-radius: 50%"
                :src="'http://localhost:8080/api/files/' + scope.row.img"
                :preview-src-list="['http://localhost:8080/api/files/' + scope.row.img]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" align="center"></el-table-column>
        <el-table-column prop="realName" label="姓名"   align="center"></el-table-column>
        <el-table-column prop="gender"   label="性别"   align="center"></el-table-column>
        <el-table-column prop="phone"    label="电话"   align="center"></el-table-column>
        <el-table-column prop="email"    label="邮箱"   align="center"></el-table-column>
<!--        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button icon="el-icon-edit" class="edit_button" circle @click="edit(scope.row)"></el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.adminId)">
              <el-button slot="reference" icon="el-icon-delete"
                         class="delete_button" circle style="margin-left: 10px"></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>-->
      </el-table>
    </div>
    <div>
      <!--分页栏-->
      <el-pagination
          small
          style="margin-left: 240px; margin-top: 10px"
          class="custom-pagination"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>
    <div>
      <!--嵌套表单-->
      <el-dialog :visible.sync="dialogFormVisible" width="30%"  :modal-append-to-body="false">
        <template slot="title">
          <div style="text-align: center;">请填写信息</div>
        </template>
        <el-form :model="form" >
          <el-form-item label="用户名" label-width="20%" label-align="left">
            <el-input v-model="form.username" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="头像" label-width="20%">
            <el-upload
                action="http://localhost:8080/api/files/upload" :on-success="successUpload">
              <el-button size="small" type="primary" class="upload_button">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="姓名" label-width="20%">
            <el-input v-model="form.realName" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="性别" label-width="20%">
            <el-radio v-model="form.gender" label="男" class="custom-radio">男</el-radio>
            <el-radio v-model="form.gender" label="女" class="custom-radio">女</el-radio>
          </el-form-item>
          <el-form-item label="电话" label-width="20%">
            <el-input v-model="form.phone" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" label-width="20%">
            <el-input v-model="form.email" autocomplete="off" style="width: 90%"></el-input>
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
  name: "AdminInfoView",
  data() {
    return {
      params: {
        username: '',
        pageNum: 1,
        pageSize: 5
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false, //不能看到dialog
      form: {}
    }
  },
  //页面加载时，created中做的事情
  created() {
    this.findBySearch();
  },
  //定义页面上触发事件调用的方法
  methods: {
    //查询函数
    findBySearch() {
      request.get("/adminInfo/search",{
        params: this.params
      }).then(res => {
        if(res.code === '0'){
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
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
        username: ''
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
      request.post("/adminInfo",this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.findBySearch();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      })
    },
    del(adminId) {
      request.delete("/adminInfo/" + adminId).then(res => {
        if(res.code === '0') {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.findBySearch();
        } else {
          this.$message({
            message: res.msg,
            type: 'success'
          });
        }
      })
    },
    successUpload(res) {
      this.form.img = res.data;
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
  border: none;
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
  background-color: #F93423 !important;
  color: #F8F8F8 !important; /* 按钮文本颜色 */
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
</style>
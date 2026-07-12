<template>
  <div>
    <div style="margin-top: 10px">
      <el-input v-model="params.number" style="width: 200px; margin-left: 240px"
                class="input" placeholder="请输入航班号"></el-input>
      <el-input v-model="params.dept" style="width: 200px; margin-left: 5px"
                class="input" placeholder="请输入出发地"></el-input>
      <el-input v-model="params.destn" style="width: 200px; margin-left: 5px"
                class="input" placeholder="请输入目的地"></el-input>
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
        <el-table-column prop="flightId"  label="序号"      align="center" :width="50"></el-table-column>
        <el-table-column prop="number"    label="航班号"    align="center"></el-table-column>
        <el-table-column prop="dept"      label="出发地"    align="center"></el-table-column>
        <el-table-column prop="destn"     label="目的地"    align="center"></el-table-column>
        <el-table-column prop="deptTime"  label="出发时间"   align="center"></el-table-column>
        <el-table-column prop="arrTime"   label="到达时间"   align="center"></el-table-column>
        <el-table-column prop="company"   label="航空公司"   align="center"></el-table-column>
        <el-table-column prop="flightName"   label="型号"   align="center"></el-table-column>
        <el-table-column prop="type"      label="类型"      align="center"></el-table-column>
        <el-table-column prop="price"     label="票价"      align="center"></el-table-column>
        <el-table-column prop="vacantNum" label="余票"      align="center"></el-table-column>
        <el-table-column prop="offPrice"  label="今日特价"   align="center" :width="80"></el-table-column>
        <el-table-column label="操作" align="center" :width="120">
          <template slot-scope="scope">
            <el-button icon="el-icon-edit" class="edit_button" circle @click="edit(scope.row)"></el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.flightId)">
              <el-button slot="reference" icon="el-icon-delete"
                         class="delete_button" circle style="margin-left: 10px"></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
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
      <el-dialog :visible.sync="dialogFormVisible" width="60%"  :modal-append-to-body="false">
        <template slot="title">
          <div style="text-align: center;">请填写信息</div>
        </template>
        <el-form :model="form" >
          <el-form-item label="航班号" label-width="20%" label-align="left">
            <el-input v-model="form.number" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="出发地" label-width="20%">
            <el-input v-model="form.dept" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="目的地" label-width="20%">
            <el-input v-model="form.destn" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="出发时间" label-width="20%">
            <el-date-picker v-model="form.deptTime" type="datetime" placeholder="选择日期时间" value-format="yyyy-MM-dd HH:mm" style="width: 90%"></el-date-picker>
          </el-form-item>
          <el-form-item label="到达时间" label-width="20%">
            <el-date-picker v-model="form.arrTime" type="datetime" placeholder="选择日期时间" value-format="yyyy-MM-dd HH:mm" style="width: 90%"></el-date-picker>
          </el-form-item>
          <el-form-item label="航空公司" label-width="20%">
            <el-input v-model="form.company" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="飞机型号" label-width="20%">
            <el-input v-model="form.flightName" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
<!--          <el-form-item label="类型" label-width="20%">
            <el-input v-model="form.type" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>-->
          <el-form-item label="类型" label-width="20%" label-align="left" class="type-select">
            <el-select v-model="form.type" placeholder="请选择" style="width: 90%">
              <el-option
                  v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="票价" label-width="20%">
            <el-input v-model="form.price" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="余票" label-width="20%">
            <el-input v-model="form.vacantNum" autocomplete="off" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="是否特价" label-width="20%">
            <el-radio v-model="form.offPrice" label="是" class="custom-radio">是</el-radio>
            <el-radio v-model="form.offPrice" label="否" class="custom-radio">否</el-radio>
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
  name: "FlightInfoView",
  data() {
    return {
      params: {
        number: '',
        dept:'',
        destn:'',
        pageNum: 1,
        pageSize: 10
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false, //不能看到dialog
      form: {},
      options: [{
        value: '经济舱',
        label: '经济舱'
      }, {
        value: '公务舱',
        label: '公务舱'
      }, {
        value: '头等舱',
        label: '头等舱'
      }],
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
      request.get("/flightInfo/search",{
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
        pageSize: 10,
        number: '',
        dept: '',
        destn:''
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
      request.post("/flightInfo",this.form).then(res => {
        if (res.code === '0') {
          this.$message.success("操作成功");
          this.dialogFormVisible = false;
          this.findBySearch();
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    del(flightId) {
      request.delete("/flightInfo/" + flightId).then(res => {
        if(res.code === '0') {
          this.$message.success("删除成功")
          this.findBySearch();
        } else {
          this.$message.error(res.msg)
        }
      })
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
.input .el-input__inner {
  border-radius: 30px;
  border-color:#F0F0F0;
  background-color: #EFEFEF;
}
.input .el-input__inner:focus {
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
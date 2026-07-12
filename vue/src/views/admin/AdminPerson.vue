<template>
  <div style="margin-left: 300px; width: 40%">
    <el-card class="box-card">
      <el-form :model="user" label-width="100px" style="padding-right: 50px">
        <div style="margin: 15px; text-align: center">
          <el-upload
              class="avatar-uploader"
              :action="'http://localhost:8080/api/files/' + user.img"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="user.img" :src="'http://localhost:8080/api/files/' + user.img" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="用户名" label-width="20%" label-align="left">
          <el-input v-model="user.username" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="姓名" label-width="20%">
          <el-input v-model="user.realName" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
<!--        <el-form-item label="密码" label-width="20%">
          <el-input v-model="user.password" autocomplete="off" style="width: 90%" show-password></el-input>
        </el-form-item>-->
        <el-form-item label="性别" label-width="20%" label-align="left" class="type-select">
        <el-select v-model="user.gender" placeholder="请选择" style="width: 90%">
          <el-option
              v-for="user in options" :key="user.value" :label="user.label" :value="user.value">
          </el-option>
        </el-select>
      </el-form-item>
        <el-form-item label="电话" label-width="20%">
          <el-input v-model="user.phone" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="20%">
          <el-input v-model="user.email" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <div style="text-align: center;">
          <el-button style="width: 100px; margin-top: 20px; font-weight: bold;" @click="submit()" class="sure_button">
            保&nbsp;&nbsp;&nbsp;存</el-button>
          <el-button style="width: 100px; margin-top: 20px; font-weight: bold;" @click="password()" class="password_button">
            修 改 密 码</el-button>
        </div>
        <div style="text-align: center;">
          <el-button style="width: 210px; margin-top: 20px; font-weight: bold;" @click="del()" class="del_button">
            注 销 账 号</el-button>
        </div>
      </el-form>
    </el-card>

    <!--原密码-->
    <el-dialog :visible.sync="dialogFormVisible1" width="30%"  :modal-append-to-body="false">
      <template slot="title">
        <div style="text-align: center;">请输入原始密码</div>
      </template>
      <el-form :model="form1" >
        <el-form-item label="原始密码" label-width="20%">
          <el-input v-model="form1.password" style="width: 90%" placeholder="请输入原始密码"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button round style="width: 100px" @click="dialogFormVisible1 = false" class="cancel_button">取 消</el-button>
        <el-button round style="width: 100px" @click="checkPassword()" class="sure_button">确 定</el-button>
      </div>
    </el-dialog>

    <!--修改密码-->
    <el-dialog :visible.sync="dialogFormVisible2" width="30%"  :modal-append-to-body="false">
      <template slot="title">
        <div style="text-align: center;">请输入新密码</div>
      </template>
      <el-form :model="form2" >
        <el-form-item label="新密码" label-width="20%">
          <el-input v-model="form2.newPassword" placeholder="请输入新密码" style="width: 90%" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码" label-width="20%">
          <el-input v-model="form2.confirmPassword" placeholder="请再次输入新密码" style="width: 90%" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button round style="width: 100px" @click="dialogFormVisible2 = false" class="cancel_button">取 消</el-button>
        <el-button round style="width: 100px" @click="savePassword()" class="sure_button">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 删除确认对话框 -->
    <el-dialog :visible.sync="deleteDialogVisible" width="30%" :modal-append-to-body="false">
      <template slot="title">
        <div style="text-align: center;">确认删除</div>
      </template>
      <div style="text-align: center;">
        确定要删除此账号吗？
      </div>
      <div slot="footer" class="dialog-footer" style="text-align: center;">
        <el-button round style="width: 100px" @click="deleteUser" class="sure_button">确 定</el-button>
        <el-button round style="width: 100px" @click="cancelDelete" class="cancel_button">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: 'adminPerson',
  data() {
    return {
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {gender: ''},
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      deleteDialogVisible: false,
      form1: {
        password: ''
      },
      form2: {
        newPassword: '',
        confirmPassword: ''
      },
      options: [{
        value: '男',
        label: '男'
      }, {
        value: '女',
        label: '女'
      }],
    };
  },
  mounted() {
  },
  methods: {
    submit() {
      request.post("/adminInfo",this.user).then(res => {
        if (res.code === '0') {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          // 更新本地的user数据
          localStorage.setItem('user', JSON.stringify(this.user));
          this.$forceUpdate();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      })
    },
    password() {
      this.dialogFormVisible1 = true;
    },
    checkPassword() {
      const enteredPassword = this.form1.password;
      const storedPassword = this.user.password;
      if (enteredPassword === storedPassword) {
        this.dialogFormVisible1 = false;
        this.dialogFormVisible2 = true;
      } else {
        this.$message.error('原始密码不正确');
      }
    },
    savePassword() {
      // 保存新密码
      const newPassword = this.form2.newPassword;
      const confirmPassword = this.form2.confirmPassword;
      const adminId = this.user.adminId; // 使用 adminId
      if (newPassword === confirmPassword) {
        // 发送修改密码请求
        request.post("/adminInfo/changePassword", {
          adminId: adminId, // 使用 adminId
          newPassword: newPassword
        }).then(res => {
          if (res.code === '0') {
            // 更新界面上的用户密码字段为新密码
            this.user.password = newPassword;

            this.$message({
              message: '密码修改成功',
              type: 'success'
            });
            this.dialogFormVisible2 = false;
            // 清空原始密码输入框
            this.form1.password = '';
          } else {
            this.$message.error(res.msg);
          }
        });
      } else {
        this.$message.error('确认密码与新密码不匹配');
      }
    },
    del() {
      this.deleteDialogVisible = true;
    },
    deleteUser() {
      // 发送删除请求
      const adminId = this.user.adminId;
      request.delete("/adminInfo/" + adminId).then(res => {
        if(res.code === '0') {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          // 清空本地存储中的用户信息并跳转到登录页面
          localStorage.removeItem('user');
          this.$router.push("/login");
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      });
      this.deleteDialogVisible = false;
    },
    cancelDelete() {
      this.deleteDialogVisible = false;
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把user的头像属性换成上传的图片的链接
      this.$set(this.user, 'avatar', response.data)
    },
  }
}
</script>

<style>
.box-card {
  width: 1000px;
  height: 560px;
}
.avatar {
  width: 100px;
  height: 100px;
}
</style>

<template>
  <div style="position: relative;">
    <img :src="publicPath + 'login_background.jpg'" style="position: absolute; top: 0; left: 0;
    width: 100%; height: 100%; filter: blur(5px); opacity: 0.97;" alt="Background Image">
      <div style="padding: 300px;display: flex; justify-content: center;
      align-items: center; height: 100vh; overflow: hidden;">
    <el-container style="position: relative; width: 400px;">

      <el-aside style="flex: 1.3; height: 500px; display: flex; flex-direction: column;
      justify-content: center; align-items: center; position: relative;
      border-top-left-radius: 10px; border-bottom-left-radius: 10px;">
        <img :src="publicPath + 'login.jpg'" style="width: 100%; height: 100%; object-fit: cover;" alt="Login Image">
        <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);
        text-align: center; color: #fff; font-size: 45px; opacity: 0.8; letter-spacing: 5px;">
          WELCOME
        </div>
      </el-aside>

      <el-main style="background-color: #fff; flex: 1; height: 500px;
      border-top-right-radius: 10px; border-bottom-right-radius: 10px">
        <div style="width: 100%; height: 100px; font-size: 30px; line-height: 100px;
        text-align: center; color: #454545">注 册</div>
        <div style="margin-top: 25px; text-align: center; height: 320px;">
          <el-form :model="user">
            <el-form-item>
              <el-input v-model="user.username" prefix-icon="el-icon-user"
                        style="width: 80%" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input v-model="user.password" show-password prefix-icon="el-icon-lock"
                        style="width: 80%" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input v-model="confirmPassword" show-password prefix-icon="el-icon-lock"
                        style="width: 80%" placeholder="请确认密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-radio-group v-model="user.gender">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-link type="info" class="login-link" @click="goToLogin">已有账号，去登录</el-link>
            <el-form-item>
              <el-button round class="login_button" style="width: 80%; margin-top: 10px" @click="register()">
                注 册
              </el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-main>
    </el-container>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "RegisterView",
  data() {
    return {
      publicPath: process.env.BASE_URL,
      user: {
        username: '',
        password: '',
        gender: '女'
      },
      confirmPassword: ''
    };
  },
  created() {
  },
  methods: {
    goToLogin() {
      this.$router.push("/login");
    },

    register() {
      // 验证密码和确认密码是否匹配
      if (this.user.password !== this.confirmPassword) {
        this.$message.error('密码和确认密码不匹配，请重新输入');
        return;
      }
      // 发送注册请求
      request.post("/userInfo/register",this.user).then(res => {
        if (res.code === '0'){
          this.$message({
            message: '注册成功',
            type: 'success'
          });
          this.$router.push("/login");
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      })
    }
  }
};
</script>

<style>
/* 输入框 */
.el-form input.el-input__inner:focus {
  border-color: #171717;
}
.login_button {
  background-color: #0A0A0D !important;
  color: #FFFFFF !important; /* 按钮文本颜色 */
  border: none; /* 去除按钮边框 */
}

</style>

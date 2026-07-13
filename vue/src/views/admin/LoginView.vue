<template>
  <div style="position: relative;">
    <img :src="publicPath + 'login_background.jpg'" style="position: absolute; top: 0; left: 0;
    width: 100%; height: 100%; filter: blur(5px); opacity: 0.97;" alt="Background Image">
    <div style="padding: 300px;display: flex; justify-content: center;
      align-items: center; height: 100vh; overflow: hidden;">
      <el-container style="position: relative; width: 400px;">

        <el-main style="background-color: #fff; flex: 1; height: 500px;
        border-top-left-radius: 10px; border-bottom-left-radius: 10px">
          <div style="width: 100%; height: 100px; font-size: 30px; line-height: 100px;
          text-align: center; color: #454545">登 录</div>
          <div style="margin-top: 25px; text-align: center; height: 320px;">
            <el-form :model="admin">
              <el-form-item>
                <el-input v-model="admin.username" prefix-icon="el-icon-user"
                          style="width: 80%" placeholder="请输入用户名"></el-input>
              </el-form-item>
              <el-form-item>
                <el-input v-model="admin.password" show-password prefix-icon="el-icon-lock"
                          style="width: 80%" placeholder="请输入密码"></el-input>
              </el-form-item>
              <div :style='{"width":"80%","margin":"20px auto"}'  class="list-type">
                <el-radio name="role"  label="管理员" v-model="admin.role" class="role-radio">管理员</el-radio>
                <el-radio name="role" label="用户"  v-model="admin.role" class="role-radio">会员</el-radio>
              </div>
              <el-link type="info" class="register-link" @click="goToRegister">注册账号</el-link>
              <el-form-item>
                <el-button round class="login_button" style="width: 80%; margin-top: 10px" @click="login()">
                  登 录
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-main>

        <el-aside style="flex: 1.3; height: 500px; display: flex; flex-direction: column;
        justify-content: center; align-items: center; position: relative;
        border-top-right-radius: 10px; border-bottom-right-radius: 10px;">
          <img :src="publicPath + 'login.jpg'" style="width: 100%; height: 100%; object-fit: cover;" alt="Login Image">
          <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);
          text-align: center; color: #fff; font-size: 45px; opacity: 0.8; letter-spacing: 5px;">
            WELCOME BACK
          </div>
        </el-aside>

      </el-container>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "LoginView",
  data() {
    return {
      publicPath: process.env.BASE_URL,
      admin: {
        role: '管理员'
      }
    };
  },
  created() {
  },
  methods: {
    goToRegister() {
      this.$router.push("/register");
    },

    login() {
      if (process.env.VUE_APP_DEMO_MODE === "true") {
        const demoUser =
            this.admin.role === "用户"
                ? {
                  userId: 1,
                  username: "demo_user",
                  role: "用户"
                }
                : {
                  adminId: 1,
                  username: "admin",
                  role: "管理员"
                };

        localStorage.setItem("user", JSON.stringify(demoUser));

        this.$message({
          message: "已进入静态演示模式",
          type: "success"
        });

        if (this.admin.role === "用户") {
          this.$router.push("/travel");
        } else {
          this.$router.push("/");
        }

        return;
      }

      request.post("/adminInfo/login",this.admin).then(res => {
        if (res.code === '0'){
          this.$message({
            message: '登录成功',
            type: 'success'
          });
          localStorage.setItem("user", JSON.stringify(res.data));
          if(this.admin.role==='用户'){
            this.$router.push("/travel");
          }else{
            this.$router.push("/");
          }
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
.role-radio .el-radio__inner:hover {
  border-color: #8D8C95;
}
.role-radio .el-radio__input.is-checked .el-radio__inner {
  border-color: #8D8C95 !important; /* 设置radio按钮选中时的边框颜色 */
  background-color: #8D8C95 !important; /* 设置radio按钮选中时的背景颜色 */
}
.role-radio .el-radio__input.is-checked + .el-radio__label {
  color: #171717 !important; /* 设置radio按钮选中时的文字颜色 */
}
</style>

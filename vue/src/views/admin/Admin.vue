<template>
  <div style="padding: 10px;">
    <el-container style="position: relative; height: 100vh;">
      <el-aside style="background-color: #171717; width: 240px; height: 100vh;
      border-radius: 20px;position: absolute; z-index: 2;
      box-shadow: 2px 0px 3px 1px rgba(0, 0, 0, 0.5);">
        <!--aside部分-->
        <div style="display: flex; align-items: center; justify-content: center;">
          <i class="el-icon-ship" style="color: #fff; margin-top: 15px; font-size: 23px;"></i>
          &nbsp;
          <span style="color: #fff; font-size: 16px; margin-top: 15px;" >
            在线旅游系统
          </span>
        </div>
        <div class="menu-container">
          <el-menu :default-active="$route.path" router
                   ref="menu"
                   default-active="2"
                   class="el-menu-vertical-demo"
                   @open="handleOpen"
                   @close="handleClose"
                   :collapse="collapsed"
                   background-color="#171717"
                   text-color="#fff"
                   active-text-color="#323335"
                   style="margin-top: 86px; width: 240px;" accordion unique-opened>
            <el-menu-item index="/" style="border-radius: 10px;">
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <i class="el-icon-house"></i>
              &nbsp;&nbsp;
              <span slot="title">系统首页</span>
            </el-menu-item>
            <el-submenu index="2" style="border-radius: 10px;">
              <template slot="title">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <i class="el-icon-user"></i>
                &nbsp;&nbsp;
                <span>用户管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/userInfo" style="border-radius: 10px; text-align: center;">会员信息</el-menu-item>
                <el-menu-item index="/adminInfo" style="border-radius: 10px; text-align: center;">管理员信息</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="3" style="border-radius: 10px;">
              <template slot="title">
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <i class="el-icon-edit-outline"></i>
              &nbsp;&nbsp;
              <span slot="title">信息管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/spotInfo" style="border-radius: 10px; text-align: center;">景点信息</el-menu-item>
                <el-menu-item index="/routeInfo" style="border-radius: 10px; text-align: center;">线路信息</el-menu-item>
                <el-menu-item index="/hotelInfo" style="border-radius: 10px; text-align: center;">酒店信息</el-menu-item>
                <el-menu-item index="/roomInfo" style="border-radius: 10px; text-align: center;">房型信息</el-menu-item>
<!--                <el-menu-item index="/numberInfo" style="border-radius: 10px; text-align: center;">房间信息</el-menu-item>-->
                <el-menu-item index="/flightInfo" style="border-radius: 10px; text-align: center;">机票信息</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="4" style="border-radius: 10px;">
              <template slot="title">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <i class="el-icon-notebook-1"></i>
                &nbsp;&nbsp;
                <span slot="title">订单管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/hotelOrderInfo" style="border-radius: 10px; text-align: center;">酒店订单</el-menu-item>
                <el-menu-item index="/spotOrderInfo" style="border-radius: 10px; text-align: center;">门票订单</el-menu-item>
                <el-menu-item index="/routeOrderInfo" style="border-radius: 10px; text-align: center;">组团订单</el-menu-item>
                <el-menu-item index="/flightOrderInfo" style="border-radius: 10px; text-align: center;">机票订单</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="5" style="border-radius: 10px;">
              <template slot="title">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <i class="el-icon-service"></i>
                &nbsp;&nbsp;
                <span>在线营销</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/noticeInfo" style="border-radius: 10px; text-align: center;">发布公告</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </div>
      </el-aside>
      <el-container style="position: relative; z-index: 1;">
        <el-header style="background-color: #ffffff ; height: 50px;
        border-top-right-radius: 20px; border-top-left-radius: 20px;
        box-shadow: 5px 4px 5px 1px rgba(0, 0, 0, 0.5);">
          <!--box-shadow数值分别代表水平、竖直、模糊、扩散、颜色；向右和向下为正数-->
          <!--header部分-->
          <el-dropdown style="float:right; height: 60px; line-height: 50px;">
            <span class="el-dropdown-link" style="margin-left: 240px; color: #171717; font-size: 16px ">
              {{ user.username }}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item class="dropdown_item">
                <div @click="goToPerson()" >个人信息</div>
              </el-dropdown-item>
              <el-dropdown-item class="dropdown_item">
                <div @click="logout" class="item_logout">退出登录</div>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>
        <el-main style="background-color: #FAFAFA; left: 0;
        border-bottom-right-radius: 20px; border-bottom-left-radius: 20px;
        box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);">
          <!--main部分-->
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Admin",

  data() {
    return {
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {
        username:"",
        password:"",
      }
    }
  },
  methods: {
    logout() {
      localStorage.removeItem("user");
      this.$router.push("/login");
    },
    goToPerson() {
      // 获取当前路由路径
      const currentRoute = this.$router.currentRoute.path;
      // 如果当前路由不是目标路由，则进行导航
      if (currentRoute !== '/adminPerson') {
        this.$router.push("/adminPerson");
      }
    }
  }
}
</script>

<style>
/* 设置导航菜单选中项的背景颜色 */
.el-menu-item.is-active {
  background-color: #fff !important;
}
/*去除侧边栏右侧瑕疵*/
.el-menu{
  border-right:none !important;
}
/* 下拉菜单 */
.dropdown_item {
  color: #171717 !important;
  background-color: transparent !important;
}
.dropdown_item:hover {
  color: #8D8C95 !important;
}

</style>
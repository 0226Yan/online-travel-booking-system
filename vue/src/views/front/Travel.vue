<template>
  <div class="main-content">
    <div style="height: 600px; position: relative;">

      <el-menu :default-active="$route.path" router class="el-menu-demo" mode="horizontal"@select="handleSelect"
               style="position: relative; border-color: transparent; border-bottom: 1px solid #eeeeee">
        <el-menu-item index="/travel" style="margin-left: 350px;">首页</el-menu-item>
        <el-menu-item index="/travel/spotReserve" style="margin-left: 20px;">门票预订</el-menu-item>
        <el-menu-item index="/travel/hotelReserve" style="margin-left: 20px;">酒店预订</el-menu-item>
        <el-menu-item index="/travel/flightReserve" style="margin-left: 20px;">机票预订</el-menu-item>
        <el-menu-item index="/travel/routeReserve" style="margin-left: 20px;">报名组团</el-menu-item>
        <el-submenu index="6" style="margin-left: 20px;">
          <template slot="title">我的订单</template>
          <el-menu-item index="/travel/hotelOrder">酒店订单</el-menu-item>
          <el-menu-item index="/travel/spotOrder">门票订单</el-menu-item>
          <el-menu-item index="/travel/routeOrder">组团订单</el-menu-item>
          <el-menu-item index="/travel/flightOrder">机票订单</el-menu-item>
        </el-submenu>
        <el-dropdown style="float:right; height: 60px; line-height: 50px; margin-right: 20px">
            <span class="el-dropdown-link" style="color: #8D8C95; font-size: 16px ">
              {{ user.username }}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item class="dropdown_item">
              <div @click="goToPerson()" >个人信息</div>
            </el-dropdown-item>
            <el-dropdown-item class="dropdown_item">
              <div @click="goToNotice()" >系统消息</div>
            </el-dropdown-item>
            <el-dropdown-item class="dropdown_item">
              <div @click="logout" class="item_logout">退出登录</div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu>
      <router-view></router-view>
    </div>
  </div>

</template>

<script>

export default {
  name: "Travel",
  data() {
    return {
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  mounted() {

  },
  //methods:本页面所有的点击事件或者其他函数定义区
  methods: {
    logout() {
      localStorage.removeItem("user");
      this.$router.push("/login");
    },
    goToPerson() {
      // 获取当前路由路径
      const currentRoute = this.$router.currentRoute.path;
      // 如果当前路由不是目标路由，则进行导航
      if (currentRoute !== '/travel/userPerson') {
        this.$router.push("/travel/userPerson");
      }
    },
    goToNotice() {
      // 获取当前路由路径
      const currentRoute = this.$router.currentRoute.path;
      // 如果当前路由不是目标路由，则进行导航
      if (currentRoute !== '/travel/notice') {
        this.$router.push("/travel/notice");
      }
    },
    handleSelect(index) {
      console.log('Selected index:', index);
    }
  }
}
</script>

<style>

</style>

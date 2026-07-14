import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from "@/views/admin/HomeView.vue";
import LoginView from "@/views/admin/LoginView.vue";
import AdminInfoView from "@/views/admin/AdminInfoView.vue";
import Admin from "@/views/admin/Admin";
import RegisterView from "@/views/admin/RegisterView";
import SpotInfoView from "@/views/admin/SpotInfoView";
import HotelInfoView from "@/views/admin/HotelInfoView";
import FlightInfoView from "@/views/admin/FlightInfoView";
import RouteInfoView from "@/views/admin/RouteInfoView";
import NoticeInfoView from "@/views/admin/NoticeInfoView";
import Travel from "@/views/front/Travel";
import UserInfoView from "@/views/admin/UserInfoView";
import RoomInfoView from "@/views/admin/RoomInfoView";
import Hotel from "@/views/front/Hotel";
import Home from "@/views/front/Home";
import Spot from "@/views/front/Spot";
import Route from "@/views/front/Route";
import Detail from "@/views/front/Detail";
import HotelOrder from "@/views/front/HotelOrder";
import HotelOrderInfoView from "@/views/admin/HotelOrderInfoView";
import SpotReserve from "@/views/front/SpotReserve";
import HotelReserve from "@/views/front/HotelReserve";
import RouteReserve from "@/views/front/RouteReserve";
import FlightReserve from "@/views/front/FlightReserve";
import SpotOrder from "@/views/front/SpotOrder";
import SpotOrderInfoView from "@/views/admin/SpotOrderInfoView";
import RouteOrder from "@/views/front/RouteOrder";
import RouteOrderInfoView from "@/views/admin/RouteOrderInfoView";
import FlightOrder from "@/views/front/FlightOrder";
import FlightOrderInfoView from "@/views/admin/FlightOrderInfoView";
import NumberInfoView from "@/views/admin/NumberInfoView";
import AdminPerson from "@/views/admin/AdminPerson";
import UserPerson from "@/views/front/UserPerson";
import NoticeView from "@/views/front/NoticeView";

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/travel',
    name: 'travel',
    component: Travel,
    children:  [
      {
        path: '', // 默认子路由
        name: 'Home',
        component: Home
      },
      {
        path: 'userPerson',
        name: 'userPerson',
        component: UserPerson
      },
      {
        path: 'hotel',
        name: 'hotel',
        component: Hotel
      },
      {
        path: 'spot',
        name: 'spot',
        component: Spot
      },
      {
        path: 'route',
        name: 'route',
        component: Route
      },
      {
        path: 'detail',
        name: 'detail',
        component: Detail
      },
      {
        path: 'hotelOrder',
        name: 'hotelOrder',
        component: HotelOrder
      },
      {
        path: 'spotOrder',
        name: 'spotOrder',
        component: SpotOrder
      },
      {
        path: 'routeOrder',
        name: 'routeOrder',
        component: RouteOrder
      },
      {
        path: 'flightOrder',
        name: 'flightOrder',
        component: FlightOrder
      },
      {
        path: 'spotReserve',
        name: 'spotReserve',
        component: SpotReserve
      },
      {
        path: 'hotelReserve',
        name: 'hotelReserve',
        component: HotelReserve
      },
      {
        path: 'routeReserve',
        name: 'routeReserve',
        component: RouteReserve
      },
      {
        path: 'flightReserve',
        name: 'flightReserve',
        component: FlightReserve
      },
      {
        path: 'notice',
        name: 'notice',
        component: NoticeView
      },
    ]
  },
  {
    path: '/',
    name: 'Admin',
    component: Admin,
    children: [
      {
        path: '',
        name: 'home',
        component: HomeView
      },
      {
        path: 'adminPerson',
        name: 'adminPerson',
        component: AdminPerson
      },
      {
        path: 'userInfo',
        name: 'userInfo',
        component: UserInfoView
      },
      {
        path: 'adminInfo',
        name: 'adminInfo',
        component: AdminInfoView
      },
      {
        path: 'spotInfo',
        name: 'spotInfo',
        component: SpotInfoView
      },
      {
        path: 'hotelInfo',
        name: 'hotelInfo',
        component: HotelInfoView
      },
      {
        path: 'roomInfo',
        name: 'roomInfo',
        component: RoomInfoView
      },
      {
        path: 'numberInfo',
        name: 'numberInfo',
        component: NumberInfoView
      },
      {
        path: 'flightInfo',
        name: 'flightInfo',
        component: FlightInfoView
      },
      {
        path: 'routeInfo',
        name: 'routeInfo',
        component: RouteInfoView
      },
      {
        path: 'hotelOrderInfo',
        name: 'hotelOrderInfo',
        component: HotelOrderInfoView
      },
      {
        path: 'spotOrderInfo',
        name: 'spotOrderInfo',
        component: SpotOrderInfoView
      },
      {
        path: 'routeOrderInfo',
        name: 'routeOrderInfo',
        component: RouteOrderInfoView
      },
      {
        path: 'flightOrderInfo',
        name: 'flightOrderInfo',
        component: FlightOrderInfoView
      },
      {
        path: 'noticeInfo',
        name: 'noticeInfo',
        component: NoticeInfoView
      },
    ]
  },
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 登录和注册页面无需登录
  if (to.path === "/login" || to.path === "/register") {
    next();
    return;
  }

  let currentUser = null;

  try {
    currentUser = JSON.parse(
        localStorage.getItem("user")
    );
  } catch (error) {
    localStorage.removeItem("user");
  }

  // 没有登录信息时，统一返回登录页
  if (!currentUser) {
    next("/login");
    return;
  }

  const role = currentUser.role;

  const isUser =
      role === "用户" ||
      role === "user";

  const isAdmin =
      role === "管理员" ||
      role === "admin";

  //普通用户不能进入管理员后台。
  if (isUser && !to.path.startsWith("/travel")) {
    next("/travel");
    return;
  }

  //管理员不能进入普通用户页面。
  if (isAdmin && to.path.startsWith("/travel")) {
    next("/");
    return;
  }

  // 角色异常时重新登录，避免进入错误页面。
  if (!isUser && !isAdmin) {
    localStorage.removeItem("user");
    next("/login");
    return;
  }

  next();
});

export default router

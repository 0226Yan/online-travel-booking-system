const image = flag =>
    `${process.env.BASE_URL}demo/${flag}.jpg`;

const demoImage = image("1712247927179");

/*
 * 只在静态 Demo 模式生效。
 *
 * 你原来的订单页面会在图片前面拼接：
 * http://localhost:8080/api/files/
 *
 * 因此页面可能生成错误地址：
 * http://localhost:8080/api/files//demo/xxxx.jpg
 *
 * 这里会自动把它修正成：
 * /demo/xxxx.jpg
 *
 * 所以不用修改 HotelOrder.vue、SpotOrder.vue 和 RouteOrder.vue。
 */
function rewriteDemoImageUrl(value) {
    const url = String(value || "");
    const basePath = process.env.BASE_URL || "/";
    const demoMarker = `${basePath}demo/`;
    const markerIndex = url.indexOf(demoMarker);

    if (markerIndex !== -1) {
        return url.slice(markerIndex);
    }

    return value;
}

function installDemoImageRewrite() {
    if (
        process.env.VUE_APP_DEMO_MODE !== "true" ||
        typeof window === "undefined" ||
        typeof document === "undefined"
    ) {
        return;
    }

    if (window.__travelDemoImageRewriteInstalled) {
        return;
    }

    window.__travelDemoImageRewriteInstalled = true;

    const rewriteImage = element => {
        if (!element || element.tagName !== "IMG") {
            return;
        }

        const currentSrc = element.getAttribute("src") || "";
        const rewrittenSrc = rewriteDemoImageUrl(currentSrc);

        if (rewrittenSrc && rewrittenSrc !== currentSrc) {
            element.setAttribute("src", rewrittenSrc);
        }
    };

    const rewriteTree = node => {
        if (!node || node.nodeType !== 1) {
            return;
        }

        rewriteImage(node);

        if (typeof node.querySelectorAll === "function") {
            node.querySelectorAll("img").forEach(rewriteImage);
        }
    };

    const startObserver = () => {
        rewriteTree(document.documentElement);

        const observer = new MutationObserver(mutations => {
            mutations.forEach(mutation => {
                if (mutation.type === "attributes") {
                    rewriteImage(mutation.target);
                    return;
                }

                mutation.addedNodes.forEach(rewriteTree);
            });
        });

        observer.observe(document.documentElement, {
            childList: true,
            subtree: true,
            attributes: true,
            attributeFilter: ["src"]
        });
    };

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", startObserver, {
            once: true
        });
    } else {
        startObserver();
    }
}

installDemoImageRewrite();

const hotels = [
    {
        hotelId: 1,
        hotelName: "亚朵酒店",
        hotelImg: image("1712247927179"),
        hotelAddress: "四川省成都市锦江区春熙路附近",
        description: "位置便利，适合城市旅行和商务出行。",
        tel: "028-88880001",
        email: "atour@example.com",
        price: 599,
        offPrice: "否"
    },
    {
        hotelId: 2,
        hotelName: "汉庭酒店",
        hotelImg: image("1712248379817"),
        hotelAddress: "云南省昆明市五华区市中心",
        description: "经济舒适型酒店，交通便利。",
        tel: "0871-88880002",
        email: "hanting@example.com",
        price: 299,
        offPrice: "是"
    },
    {
        hotelId: 3,
        hotelName: "维也纳酒店",
        hotelImg: image("1712248604642"),
        hotelAddress: "湖南省张家界市武陵源区",
        description: "靠近景区，适合家庭和游客入住。",
        tel: "0744-88880003",
        email: "vienna@example.com",
        price: 399,
        offPrice: "否"
    },
    {
        hotelId: 4,
        hotelName: "丽枫酒店",
        hotelImg: image("1712253890106"),
        hotelAddress: "福建省武夷山市度假区",
        description: "环境安静，适合休闲度假。",
        tel: "0599-88880004",
        email: "lavande@example.com",
        price: 459,
        offPrice: "是"
    },
    {
        hotelId: 5,
        hotelName: "全季酒店",
        hotelImg: image("1712256978891"),
        hotelAddress: "新疆乌鲁木齐市天山区",
        description: "简约舒适，适合长途旅行中转。",
        tel: "0991-88880005",
        email: "quanji@example.com",
        price: 499,
        offPrice: "否"
    }
];

const rooms = [
    {
        roomId: 1,
        roomName: "大床房",
        roomImg: image("1712257355194"),
        price: 399,
        vacantNum: 8,
        hotelId: 1,
        hotelName: "亚朵酒店"
    },
    {
        roomId: 2,
        roomName: "双床房",
        roomImg: image("1712257346085"),
        price: 459,
        vacantNum: 6,
        hotelId: 1,
        hotelName: "亚朵酒店"
    },
    {
        roomId: 3,
        roomName: "家庭房",
        roomImg: image("1712257368415"),
        price: 599,
        vacantNum: 4,
        hotelId: 1,
        hotelName: "亚朵酒店"
    },
    {
        roomId: 4,
        roomName: "大床房",
        roomImg: image("1712257820644"),
        price: 299,
        vacantNum: 10,
        hotelId: 2,
        hotelName: "汉庭酒店"
    },
    {
        roomId: 5,
        roomName: "双床房",
        roomImg: image("1712257780394"),
        price: 359,
        vacantNum: 8,
        hotelId: 2,
        hotelName: "汉庭酒店"
    },
    {
        roomId: 6,
        roomName: "家庭房",
        roomImg: image("1712257861040"),
        price: 499,
        vacantNum: 5,
        hotelId: 2,
        hotelName: "汉庭酒店"
    },
    {
        roomId: 7,
        roomName: "大床房",
        roomImg: image("1712257673736"),
        price: 399,
        vacantNum: 7,
        hotelId: 3,
        hotelName: "维也纳酒店"
    },
    {
        roomId: 8,
        roomName: "双床房",
        roomImg: image("1712257659026"),
        price: 469,
        vacantNum: 6,
        hotelId: 3,
        hotelName: "维也纳酒店"
    },
    {
        roomId: 9,
        roomName: "家庭房",
        roomImg: image("1712257582288"),
        price: 569,
        vacantNum: 4,
        hotelId: 3,
        hotelName: "维也纳酒店"
    }
];

const spots = [
    {
        spotId: 1,
        spotName: "九寨沟",
        spotAddress: "四川省阿坝藏族羌族自治州九寨沟县",
        spotImage: image("1712313976458"),
        description: "以彩池、瀑布、雪山和森林景观闻名，是著名自然风景区。",
        openingHours: "08:30-17:00",
        price: 280,
        amount: 20,
        offPrice: "否"
    },
    {
        spotId: 2,
        spotName: "玉龙雪山",
        spotAddress: "云南省丽江市玉龙纳西族自治县",
        spotImage: image("1712314408676"),
        description: "雪山、冰川和高原风光结合，是云南热门旅游景点。",
        openingHours: "07:30-16:30",
        price: 320,
        amount: 80,
        offPrice: "是"
    },
    {
        spotId: 3,
        spotName: "张家界",
        spotAddress: "湖南省张家界市武陵源区",
        spotImage: image("1712316730989"),
        description: "以奇峰、峡谷和森林景观著称，适合自然风光游览。",
        openingHours: "08:00-18:00",
        price: 248,
        amount: 120,
        offPrice: "否"
    },
    {
        spotId: 4,
        spotName: "武夷山",
        spotAddress: "福建省南平市武夷山市",
        spotImage: image("1712317464817"),
        description: "拥有丹霞地貌、九曲溪和茶文化景观。",
        openingHours: "08:00-17:30",
        price: 210,
        amount: 90,
        offPrice: "是"
    },
    {
        spotId: 5,
        spotName: "云南",
        spotAddress: "云南省昆明市及周边地区",
        spotImage: image("1712327826053"),
        description: "包含古城、雪山、湖泊和民族文化体验。",
        openingHours: "全天开放",
        price: 199,
        amount: 149,
        offPrice: "否"
    },
    {
        spotId: 6,
        spotName: "新疆",
        spotAddress: "新疆维吾尔自治区",
        spotImage: image("1712332419666"),
        description: "拥有草原、雪山、湖泊和沙漠等多样自然景观。",
        openingHours: "全天开放",
        price: 260,
        amount: 100,
        offPrice: "否"
    }
];

const routes = [
    {
        tourId: 1,
        img: image("1712313976458"),
        tourName: "九寨沟三日游",
        tourDesc: "游览九寨沟主要景区，体验自然山水风光。",
        dept: "成都",
        deptDate: "2026-08-10",
        days: "3天2晚",
        price: 1599,
        amount: 28,
        offPrice: "否"
    },
    {
        tourId: 2,
        img: image("1712314408676"),
        tourName: "丽江玉龙雪山两日游",
        tourDesc: "包含玉龙雪山、丽江古城等热门景点。",
        dept: "丽江",
        deptDate: "2026-08-15",
        days: "2天1晚",
        price: 1299,
        amount: 24,
        offPrice: "是"
    },
    {
        tourId: 3,
        img: image("1712316730989"),
        tourName: "张家界森林公园三日游",
        tourDesc: "游览张家界国家森林公园和核心景区。",
        dept: "张家界",
        deptDate: "2026-08-20",
        days: "3天2晚",
        price: 1799,
        amount: 28,
        offPrice: "否"
    },
    {
        tourId: 4,
        img: image("1712317464817"),
        tourName: "武夷山休闲两日游",
        tourDesc: "体验九曲溪、天游峰和武夷山茶文化。",
        dept: "武夷山",
        deptDate: "2026-08-25",
        days: "2天1晚",
        price: 999,
        amount: 35,
        offPrice: "是"
    },
    {
        tourId: 5,
        img: image("1712327826053"),
        tourName: "云南经典五日游",
        tourDesc: "覆盖昆明、大理、丽江等云南经典目的地。",
        dept: "昆明",
        deptDate: "2026-09-01",
        days: "5天4晚",
        price: 2999,
        amount: 20,
        offPrice: "否"
    },
    {
        tourId: 6,
        img: image("1712332419666"),
        tourName: "新疆风光六日游",
        tourDesc: "体验新疆自然风光、草原湖泊和特色美食。",
        dept: "乌鲁木齐",
        deptDate: "2026-09-10",
        days: "6天5晚",
        price: 3999,
        amount: 16,
        offPrice: "否"
    }
];

const flights = [
    {
        flightId: 1,
        number: "CA4101",
        dept: "北京",
        destn: "成都",
        deptTime: "2026-08-10 08:30",
        arrTime: "2026-08-10 11:20",
        company: "中国国际航空",
        flightName: "空客A320",
        vacantNum: 59,
        price: 980,
        type: "经济舱",
        offPrice: "否"
    },
    {
        flightId: 2,
        number: "MU5712",
        dept: "上海",
        destn: "昆明",
        deptTime: "2026-08-15 09:10",
        arrTime: "2026-08-15 12:30",
        company: "东方航空",
        flightName: "波音737",
        vacantNum: 50,
        price: 1080,
        type: "经济舱",
        offPrice: "是"
    },
    {
        flightId: 3,
        number: "CZ3388",
        dept: "广州",
        destn: "张家界",
        deptTime: "2026-08-20 13:20",
        arrTime: "2026-08-20 15:10",
        company: "南方航空",
        flightName: "空客A320",
        vacantNum: 45,
        price: 860,
        type: "经济舱",
        offPrice: "否"
    },
    {
        flightId: 4,
        number: "MF8120",
        dept: "厦门",
        destn: "武夷山",
        deptTime: "2026-08-25 10:00",
        arrTime: "2026-08-25 11:15",
        company: "厦门航空",
        flightName: "波音737",
        vacantNum: 40,
        price: 650,
        type: "经济舱",
        offPrice: "是"
    },
    {
        flightId: 5,
        number: "CZ6901",
        dept: "广州",
        destn: "乌鲁木齐",
        deptTime: "2026-09-10 07:40",
        arrTime: "2026-09-10 12:50",
        company: "南方航空",
        flightName: "空客A330",
        vacantNum: 55,
        price: 1680,
        type: "经济舱",
        offPrice: "否"
    }
];

const notices = [
    {
        noticeId: 1,
        noticeName: "暑期旅游优惠活动",
        content: "热门景点、酒店和旅游线路已更新，部分产品享受今日特价。",
        time: "2026-07-13 10:00:00"
    }
];

const users = [
    {
        userId: 1,
        userImg: image("1711886704203"),
        account: "USER_DEMO001",
        username: "demo_user",
        gender: "女",
        password: "123",
        phone: "13900000000",
        email: "demo@example.com",
        role: "user"
    },
    {
        userId: 2,
        userImg: image("1711886704203"),
        account: "USER_447e1f40",
        username: "user02",
        gender: "女",
        password: "123",
        phone: "",
        email: "",
        role: "user"
    }
];

const admins = [
    {
        adminId: 1,
        username: "admin",
        password: "123",
        realName: "Demo Administrator",
        gender: "男",
        phone: "13800000000",
        email: "admin@example.com",
        img: image("1712086489328"),
        role: "管理员"
    }
];

/*
 * Demo 中每一种订单固定使用一张图片：
 *
 * 酒店订单：家庭房图片
 * 景点订单：九寨沟图片
 * 线路订单：玉龙雪山图片
 *
 * 航班表没有图片字段，所以航班订单不显示图片。
 */
let hotelOrders = [
    {
        id: 2,
        orderId: "20260708151748",
        userId: 1,
        userName: "demo_user",
        hotelId: 1,
        roomId: 3,
        hotelName: "亚朵酒店",
        roomName: "家庭房",
        roomImg: image("1712257368415"),
        time: "2026-07-08 15:17:48",
        inTime: "2026-07-23",
        outTime: "2026-07-30",
        days: 7,
        price: 4193,
        status: "未支付"
    }
];

let spotOrders = [
    {
        id: 1,
        orderId: "20260708151728",
        userId: 1,
        userName: "demo_user",
        spotId: 1,
        spotName: "九寨沟",
        spotImage: image("1712313976458"),
        time: "2026-07-08 15:17:28",
        price: 280,
        status: "已完成"
    }
];

let routeOrders = [
    {
        id: 1,
        orderId: "20260708151758",
        userId: 1,
        userName: "demo_user",
        tourId: 2,
        tourName: "丽江玉龙雪山两日游",
        img: image("1712314408676"),
        time: "2026-07-08 15:17:58",
        price: 1299,
        status: "未支付"
    }
];

let flightOrders = [
    {
        id: 1,
        orderId: "20260708151722",
        userId: 1,
        userName: "demo_user",
        flightId: 1,
        number: "CA4101",
        dept: "北京",
        destn: "成都",
        company: "中国国际航空",
        flightName: "空客A320",
        type: "经济舱",
        time: "2026-07-08 15:17:22",
        price: 980,
        status: "未支付"
    }
];

function success(data = null) {
    return {
        code: "0",
        data,
        msg: ""
    };
}

function normalizePath(url) {
    const value = String(url || "")
        .replace(/^https?:\/\/[^/]+\/api/, "");

    const path = value.split("?")[0];

    return path.startsWith("/") ? path : `/${path}`;
}

function getParams(url, config = {}) {
    const queryString = String(url || "").includes("?")
        ? String(url).split("?").slice(1).join("?")
        : "";

    const queryParams = Object.fromEntries(
        new URLSearchParams(queryString).entries()
    );

    return {
        ...queryParams,
        ...(config.params || {})
    };
}

function paginate(list, params = {}) {
    const pageNum = Number(params.pageNum || 1);
    const pageSize = Number(params.pageSize || 10);
    const start = (pageNum - 1) * pageSize;

    return {
        list: list.slice(start, start + pageSize),
        total: list.length,
        pageNum,
        pageSize
    };
}

function filterList(list, params, fields) {
    return list.filter(item =>
        fields.every(field => {
            const keyword = params[field];

            if (
                keyword === undefined ||
                keyword === null ||
                keyword === ""
            ) {
                return true;
            }

            return String(item[field] || "")
                .toLowerCase()
                .includes(String(keyword).toLowerCase());
        })
    );
}

function getOrderList(prefix) {
    if (prefix === "hotelOrder") {
        return hotelOrders;
    }

    if (prefix === "spotOrder") {
        return spotOrders;
    }

    if (prefix === "routeOrder") {
        return routeOrders;
    }

    if (prefix === "flightOrder") {
        return flightOrders;
    }

    return [];
}

function setOrderList(prefix, value) {
    if (prefix === "hotelOrder") {
        hotelOrders = value;
    }

    if (prefix === "spotOrder") {
        spotOrders = value;
    }

    if (prefix === "routeOrder") {
        routeOrders = value;
    }

    if (prefix === "flightOrder") {
        flightOrders = value;
    }
}

function updateOrder(prefix, id, status) {
    const list = getOrderList(prefix);

    const order = list.find(
        item => Number(item.id) === Number(id)
    );

    if (order) {
        order.status = status;
    }

    return success();
}

function addDemoOrder(prefix, form = {}) {
    const orderId = `DEMO-${Date.now()}`;

    const time = new Date().toLocaleString("zh-CN", {
        hour12: false
    });

    if (prefix === "hotelOrder") {
        const room = rooms.find(
            item => Number(item.roomId) === Number(form.roomId)
        );

        const hotel = hotels.find(
            item => Number(item.hotelId) === Number(form.hotelId)
        );

        hotelOrders.unshift({
            id: Date.now(),
            orderId,
            userId: form.userId || 1,
            userName: "demo_user",
            hotelId: form.hotelId,
            roomId: form.roomId,
            hotelName: hotel ? hotel.hotelName : "演示酒店",
            roomName: room ? room.roomName : "演示房型",

            // Demo 中所有酒店订单统一使用这一张图片
            roomImg: image("1712257368415"),

            time,
            inTime: form.inTime || "2026-08-10",
            outTime: form.outTime || "2026-08-12",
            days: form.days || 2,
            price:
                form.price ||
                (room ? room.price * 2 : 999),
            status: "未支付"
        });
    }

    if (prefix === "spotOrder") {
        const spot = spots.find(
            item => Number(item.spotId) === Number(form.spotId)
        );

        spotOrders.unshift({
            id: Date.now(),
            orderId,
            userId: form.userId || 1,
            userName: "demo_user",
            spotId: form.spotId,
            spotName: spot ? spot.spotName : "演示景点",

            // Demo 中所有景点订单统一使用九寨沟图片
            spotImage: image("1712313976458"),

            time,
            price: spot ? spot.price : 299,
            status: "未支付"
        });
    }

    if (prefix === "routeOrder") {
        const route = routes.find(
            item => Number(item.tourId) === Number(form.tourId)
        );

        routeOrders.unshift({
            id: Date.now(),
            orderId,
            userId: form.userId || 1,
            userName: "demo_user",
            tourId: form.tourId,
            tourName: route ? route.tourName : "演示线路",

            // Demo 中所有线路订单统一使用玉龙雪山图片
            img: image("1712314408676"),

            time,
            price: route ? route.price : 1999,
            status: "未支付"
        });
    }

    if (prefix === "flightOrder") {
        const flight = flights.find(
            item =>
                Number(item.flightId) ===
                Number(form.flightId)
        );

        flightOrders.unshift({
            id: Date.now(),
            orderId,
            userId: form.userId || 1,
            userName: "demo_user",
            flightId: form.flightId,
            number: flight ? flight.number : "DEMO001",
            dept: flight ? flight.dept : "广州",
            destn: flight ? flight.destn : "上海",
            company: flight
                ? flight.company
                : "演示航空",
            flightName: flight
                ? flight.flightName
                : "演示航班",
            type: flight ? flight.type : "经济舱",
            time,
            price: flight ? flight.price : 699,
            status: "未支付"
        });
    }

    return success();
}

export function demoRequest(
    method,
    url,
    data = {},
    config = {}
) {
    const requestMethod = String(method).toLowerCase();
    const path = normalizePath(url);
    const params = getParams(url, config);

    if (requestMethod === "get") {
        if (path === "/hotelInfo/selectAll") {
            return Promise.resolve(success(hotels));
        }

        if (path === "/hotelInfo/selectById") {
            const hotel = hotels.find(
                item =>
                    Number(item.hotelId) ===
                    Number(params.hotelId)
            );

            return Promise.resolve(
                success(hotel || hotels[0])
            );
        }

        if (path === "/roomInfo/selectByHotelId") {
            const result = rooms.filter(
                item =>
                    Number(item.hotelId) ===
                    Number(params.hotelId)
            );

            return Promise.resolve(success(result));
        }

        if (path === "/roomInfo/selectById") {
            const room = rooms.find(
                item =>
                    Number(item.roomId) ===
                    Number(params.roomId)
            );

            return Promise.resolve(
                success(room || rooms[0])
            );
        }

        if (path === "/spotInfo/selectAll") {
            return Promise.resolve(success(spots));
        }

        if (path === "/spotInfo/selectById") {
            const spot = spots.find(
                item =>
                    Number(item.spotId) ===
                    Number(params.spotId)
            );

            return Promise.resolve(
                success(spot || spots[0])
            );
        }

        if (path === "/routeInfo/selectAll") {
            return Promise.resolve(success(routes));
        }

        if (path === "/routeInfo/selectById") {
            const route = routes.find(
                item =>
                    Number(item.tourId) ===
                    Number(params.tourId)
            );

            return Promise.resolve(
                success(route || routes[0])
            );
        }

        if (path === "/flightInfo/selectAll") {
            return Promise.resolve(success(flights));
        }

        if (path === "/flightInfo/selectById") {
            const flight = flights.find(
                item =>
                    Number(item.flightId) ===
                    Number(params.flightId)
            );

            return Promise.resolve(
                success(flight || flights[0])
            );
        }

        if (path === "/noticeInfo") {
            return Promise.resolve(success(notices));
        }

        const searchMap = {
            "/hotelInfo/search": {
                list: hotels,
                fields: ["hotelName", "hotelAddress"]
            },
            "/roomInfo/search": {
                list: rooms,
                fields: ["roomName", "hotelName"]
            },
            "/spotInfo/search": {
                list: spots,
                fields: ["spotName", "spotAddress"]
            },
            "/routeInfo/search": {
                list: routes,
                fields: ["tourName", "dept"]
            },
            "/flightInfo/search": {
                list: flights,
                fields: ["number", "dept", "destn"]
            },
            "/userInfo/search": {
                list: users,
                fields: ["username", "account"]
            },
            "/adminInfo/search": {
                list: admins,
                fields: ["username", "realName"]
            },
            "/noticeInfo/search": {
                list: notices,
                fields: ["noticeName"]
            }
        };

        if (searchMap[path]) {
            const item = searchMap[path];

            const result = filterList(
                item.list,
                params,
                item.fields
            );

            return Promise.resolve(
                success(paginate(result, params))
            );
        }

        const orderSelectMatch = path.match(
            /^\/(hotelOrder|spotOrder|routeOrder|flightOrder)\/selectByUserId$/
        );

        if (orderSelectMatch) {
            const list = getOrderList(
                orderSelectMatch[1]
            ).filter(
                item =>
                    !params.userId ||
                    Number(item.userId) ===
                    Number(params.userId)
            );

            return Promise.resolve(success(list));
        }

        const orderPageMatch = path.match(
            /^\/(hotelOrder|spotOrder|routeOrder|flightOrder)\/selectPage$/
        );

        if (orderPageMatch) {
            return Promise.resolve(
                success(
                    paginate(
                        getOrderList(orderPageMatch[1]),
                        params
                    )
                )
            );
        }

        const orderActionMatch = path.match(
            /^\/(hotelOrder|spotOrder|routeOrder|flightOrder)\/(pay|finished)\/(\d+)$/
        );

        if (orderActionMatch) {
            const status =
                orderActionMatch[2] === "pay"
                    ? "已支付"
                    : "已完成";

            return Promise.resolve(
                updateOrder(
                    orderActionMatch[1],
                    orderActionMatch[3],
                    status
                )
            );
        }

        return Promise.resolve(success([]));
    }

    if (requestMethod === "post") {
        const addOrderMatch = path.match(
            /^\/(hotelOrder|spotOrder|routeOrder|flightOrder)\/add$/
        );

        if (addOrderMatch) {
            return Promise.resolve(
                addDemoOrder(addOrderMatch[1], data)
            );
        }

        // 注册、更新资料、后台新增和编辑等操作
        // 在静态 Demo 中统一返回成功
        return Promise.resolve(success());
    }

    if (requestMethod === "delete") {
        const deleteOrderMatch = path.match(
            /^\/(hotelOrder|spotOrder|routeOrder|flightOrder)\/(delete|cancel)\/(\d+)$/
        );

        if (deleteOrderMatch) {
            const prefix = deleteOrderMatch[1];
            const id = deleteOrderMatch[3];

            setOrderList(
                prefix,
                getOrderList(prefix).filter(
                    item =>
                        Number(item.id) !== Number(id)
                )
            );
        }

        return Promise.resolve(success());
    }

    if (requestMethod === "put") {
        return Promise.resolve(success());
    }

    return Promise.resolve(success());
}
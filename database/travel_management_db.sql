-- MySQL dump 10.13  Distrib 9.3.0, for macos13.7 (arm64)
--
-- Host: 127.0.0.1    Database: travel_management_db
-- ------------------------------------------------------
-- Server version	8.4.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `adminId` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `realName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `img` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`adminId`),
  UNIQUE KEY `uk_admin_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','123','Demo Administrator','男','13800000000','admin@example.com','1712086489328','管理员');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flight`
--

DROP TABLE IF EXISTS `flight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight` (
  `flightId` int NOT NULL AUTO_INCREMENT,
  `number` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dept` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `destn` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `deptTime` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `arrTime` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `company` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `flightName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `vacantNum` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `offPrice` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`flightId`),
  KEY `idx_flight_number` (`number`),
  KEY `idx_flight_dept_destn` (`dept`,`destn`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight`
--

LOCK TABLES `flight` WRITE;
/*!40000 ALTER TABLE `flight` DISABLE KEYS */;
INSERT INTO `flight` VALUES (1,'CA4101','北京','成都','2026-08-10 08:30','2026-08-10 11:20','中国国际航空','空客A320',59,980.00,'经济舱','否'),(2,'MU5712','上海','昆明','2026-08-15 09:10','2026-08-15 12:30','东方航空','波音737',50,1080.00,'经济舱','是'),(3,'CZ3388','广州','张家界','2026-08-20 13:20','2026-08-20 15:10','南方航空','空客A320',45,860.00,'经济舱','否'),(4,'MF8120','厦门','武夷山','2026-08-25 10:00','2026-08-25 11:15','厦门航空','波音737',40,650.00,'经济舱','是'),(5,'CZ6901','广州','乌鲁木齐','2026-09-10 07:40','2026-09-10 12:50','南方航空','空客A330',55,1680.00,'经济舱','否');
/*!40000 ALTER TABLE `flight` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flight_order`
--

DROP TABLE IF EXISTS `flight_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flight_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `flightId` int NOT NULL,
  `userId` int NOT NULL,
  `time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_flight_order_orderId` (`orderId`),
  KEY `idx_flight_order_flightId` (`flightId`),
  KEY `idx_flight_order_userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flight_order`
--

LOCK TABLES `flight_order` WRITE;
/*!40000 ALTER TABLE `flight_order` DISABLE KEYS */;
INSERT INTO `flight_order` VALUES (1,'20260708151722','未支付',1,1,'2026-07-08 15:17:22',980.00);
/*!40000 ALTER TABLE `flight_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel` (
  `hotelId` int NOT NULL AUTO_INCREMENT,
  `hotelName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `hotelImg` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hotelAddress` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `tel` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `offPrice` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`hotelId`),
  KEY `idx_hotel_name` (`hotelName`),
  KEY `idx_hotel_address` (`hotelAddress`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
INSERT INTO `hotel` VALUES (1,'亚朵酒店','1712247927179','四川省成都市锦江区春熙路附近','位置便利，适合城市旅行和商务出行。','028-88880001','atour@example.com',599.00,'否'),(2,'汉庭酒店','1712248379817','云南省昆明市五华区市中心','经济舒适型酒店，交通便利。','0871-88880002','hanting@example.com',299.00,'是'),(3,'维也纳酒店','1712248604642','湖南省张家界市武陵源区','靠近景区，适合家庭和游客入住。','0744-88880003','vienna@example.com',399.00,'否'),(4,'丽枫酒店','1712253890106','福建省武夷山市度假区','环境安静，适合休闲度假。','0599-88880004','lavande@example.com',459.00,'是'),(5,'全季酒店','1712256978891','新疆乌鲁木齐市天山区','简约舒适，适合长途旅行中转。','0991-88880005','quanji@example.com',499.00,'否');
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel_order`
--

DROP TABLE IF EXISTS `hotel_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `roomId` int NOT NULL,
  `hotelId` int NOT NULL,
  `userId` int NOT NULL,
  `time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `inTime` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `outTime` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `days` bigint NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_hotel_order_orderId` (`orderId`),
  KEY `idx_hotel_order_roomId` (`roomId`),
  KEY `idx_hotel_order_hotelId` (`hotelId`),
  KEY `idx_hotel_order_userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel_order`
--

LOCK TABLES `hotel_order` WRITE;
/*!40000 ALTER TABLE `hotel_order` DISABLE KEYS */;
INSERT INTO `hotel_order` VALUES (2,'20260708151748','未支付',3,1,1,'2026-07-08 15:17:48','2026-07-23','2026-07-30',7,4193.00),(3,'20260708153837','已支付',8,3,2,'2026-07-08 15:38:37','2026-07-25','2026-07-27',2,938.00);
/*!40000 ALTER TABLE `hotel_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `noticeId` int NOT NULL AUTO_INCREMENT,
  `noticeName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`noticeId`),
  KEY `idx_notice_time` (`time`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (1,'暑期旅游优惠活动','热门景点、酒店和线路已更新，部分产品今日特价。','2026-07-08 15:00:00');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `number`
--

DROP TABLE IF EXISTS `number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `number` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `roomId` int NOT NULL,
  `hotelId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_number_roomId` (`roomId`),
  KEY `idx_number_hotelId` (`hotelId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `number`
--

LOCK TABLES `number` WRITE;
/*!40000 ALTER TABLE `number` DISABLE KEYS */;
INSERT INTO `number` VALUES (1,'A101','空闲',1,1),(2,'A102','空闲',1,1),(3,'A201','空闲',2,1),(4,'A301','空闲',3,1),(5,'B101','空闲',4,2),(6,'B201','空闲',5,2),(7,'B301','空闲',6,2),(8,'C101','空闲',7,3),(9,'C201','空闲',8,3),(10,'C301','空闲',9,3);
/*!40000 ALTER TABLE `number` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `roomId` int NOT NULL AUTO_INCREMENT,
  `roomName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `roomImg` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `vacantNum` int NOT NULL,
  `hotelId` int NOT NULL,
  PRIMARY KEY (`roomId`),
  KEY `idx_room_hotelId` (`hotelId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'大床房','1712257355194',399.00,8,1),(2,'双床房','1712257346085',459.00,6,1),(3,'家庭房','1712257368415',599.00,4,1),(4,'大床房','1712257820644',299.00,10,2),(5,'双床房','1712257780394',359.00,8,2),(6,'家庭房','1712257861040',499.00,5,2),(7,'大床房','1712257673736',399.00,7,3),(8,'双床房','1712257659026',469.00,6,3),(9,'家庭房','1712257582288',569.00,4,3);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route` (
  `tourId` int NOT NULL AUTO_INCREMENT,
  `img` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tourName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tourDesc` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `dept` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `deptDate` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `days` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `amount` int NOT NULL,
  `offPrice` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`tourId`),
  KEY `idx_route_name` (`tourName`),
  KEY `idx_route_deptDate` (`deptDate`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (1,'1712313976458','九寨沟三日游','游览九寨沟主要景区，体验自然山水风光。','成都','2026-08-10','3天2晚',1599.00,28,'否'),(2,'1712314408676','丽江玉龙雪山两日游','包含玉龙雪山、丽江古城等热门景点。','丽江','2026-08-15','2天1晚',1299.00,24,'是'),(3,'1712316730989','张家界森林公园三日游','游览张家界国家森林公园和核心景区。','张家界','2026-08-20','3天2晚',1799.00,28,'否'),(4,'1712317464817','武夷山休闲两日游','体验九曲溪、天游峰和武夷山茶文化。','武夷山','2026-08-25','2天1晚',999.00,35,'是'),(5,'1712327826053','云南经典五日游','覆盖昆明、大理、丽江等云南经典目的地。','昆明','2026-09-01','5天4晚',2999.00,20,'否'),(6,'1712332419666','新疆风光六日游','体验新疆自然风光、草原湖泊和特色美食。','乌鲁木齐','2026-09-10','6天5晚',3999.00,16,'否');
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route_order`
--

DROP TABLE IF EXISTS `route_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tourId` int NOT NULL,
  `userId` int NOT NULL,
  `time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_route_order_orderId` (`orderId`),
  KEY `idx_route_order_tourId` (`tourId`),
  KEY `idx_route_order_userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route_order`
--

LOCK TABLES `route_order` WRITE;
/*!40000 ALTER TABLE `route_order` DISABLE KEYS */;
INSERT INTO `route_order` VALUES (1,'20260708151758','未支付',2,1,'2026-07-08 15:17:58',1299.00),(3,'20260708152137','已完成',1,2,'2026-07-08 15:21:37',1599.00),(4,'20260708153825','未支付',6,2,'2026-07-08 15:38:25',3999.00),(5,'20260709151332','未支付',6,2,'2026-07-09 15:13:32',3999.00);
/*!40000 ALTER TABLE `route_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spot`
--

DROP TABLE IF EXISTS `spot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spot` (
  `spotId` int NOT NULL AUTO_INCREMENT,
  `spotName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `spotAddress` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `spotImage` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `openingHours` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `amount` int NOT NULL,
  `offPrice` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`spotId`),
  KEY `idx_spot_name` (`spotName`),
  KEY `idx_spot_address` (`spotAddress`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spot`
--

LOCK TABLES `spot` WRITE;
/*!40000 ALTER TABLE `spot` DISABLE KEYS */;
INSERT INTO `spot` VALUES (1,'九寨沟','四川省阿坝藏族羌族自治州九寨沟县','1712313976458','以彩池、瀑布、雪山和森林景观闻名，是著名自然风景区。','08:30-17:00',280.00,20,'否'),(2,'玉龙雪山','云南省丽江市玉龙纳西族自治县','1712314408676','雪山、冰川和高原风光结合，是云南热门旅游景点。','07:30-16:30',320.00,80,'是'),(3,'张家界','湖南省张家界市武陵源区','1712316730989','以奇峰、峡谷和森林景观著称，适合自然风光游览。','08:00-18:00',248.00,120,'否'),(4,'武夷山','福建省南平市武夷山市','1712317464817','拥有丹霞地貌、九曲溪和茶文化景观。','08:00-17:30',210.00,90,'是'),(5,'云南','云南省昆明市及周边地区','1712327826053','包含古城、雪山、湖泊和民族文化体验。','全天开放',199.00,149,'否'),(6,'新疆','新疆维吾尔自治区','1712332419666','拥有草原、雪山、湖泊和沙漠等多样自然景观。','全天开放',260.00,100,'否');
/*!40000 ALTER TABLE `spot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spot_order`
--

DROP TABLE IF EXISTS `spot_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spot_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `spotId` int NOT NULL,
  `userId` int NOT NULL,
  `time` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_spot_order_orderId` (`orderId`),
  KEY `idx_spot_order_spotId` (`spotId`),
  KEY `idx_spot_order_userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spot_order`
--

LOCK TABLES `spot_order` WRITE;
/*!40000 ALTER TABLE `spot_order` DISABLE KEYS */;
INSERT INTO `spot_order` VALUES (1,'20260708151728','已完成',1,1,'2026-07-08 15:17:28',280.00),(2,'20260708153845','已支付',5,2,'2026-07-08 15:38:45',199.00);
/*!40000 ALTER TABLE `spot_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `userImg` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `uk_user_account` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'1711886704203','USER_DEMO001','demo_user','女','123','13900000000','demo@example.com','user'),(2,'1711886704203','USER_447e1f40','user02','女','123',NULL,NULL,'user');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-12 23:53:01

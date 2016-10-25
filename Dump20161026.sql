-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: couponsystem
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `ID` bigint(10) NOT NULL AUTO_INCREMENT,
  `COMP_NAME` varchar(45) DEFAULT NULL,
  `PASSWORD` varchar(45) DEFAULT NULL,
  `EMAIL` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `COMP_NAME_UNIQUE` (`COMP_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (50,'McHoland','f12e12','McHoland@walla.com\r\n'),(51,'CafeOfea','g777','CafeOfea@gmail.com'),(52,'GymX','uuu','GymX@yahoo.com'),(53,'CafeOfeapp','g777v','CafeOfea@gmai1l.com');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_coupon`
--

DROP TABLE IF EXISTS `company_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_coupon` (
  `COMP_ID` bigint(20) NOT NULL,
  `COUPON_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`COMP_ID`,`COUPON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_coupon`
--

LOCK TABLES `company_coupon` WRITE;
/*!40000 ALTER TABLE `company_coupon` DISABLE KEYS */;
INSERT INTO `company_coupon` VALUES (50,87),(50,88),(50,97);
/*!40000 ALTER TABLE `company_coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(45) DEFAULT NULL,
  `START_DATE` datetime DEFAULT NULL,
  `END_DATE` datetime DEFAULT NULL,
  `AMOUNT` int(11) DEFAULT NULL,
  `TYPE` varchar(45) DEFAULT NULL,
  `MESSAGE` varchar(45) DEFAULT NULL,
  `PRICE` decimal(8,2) DEFAULT NULL,
  `IMAGE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (67,'50% sandwich on the morning','2016-10-20 00:00:00','2017-01-20 00:00:00',1000,'FOOD','mmm.....',9.90,'subway.png'),(87,'1/2 price burger2','2016-10-08 00:00:00','2017-12-19 00:00:00',67,'FOOD','The best BURGER in the world',34.22,'burger.jpg'),(88,'Coke','2016-10-08 00:00:00','2017-12-25 00:00:00',8,'FOOD','So natrual',1.28,'coke.jpg'),(89,'Potatoes','2016-10-08 00:00:00','2017-12-08 00:00:00',100,'FOOD','So natrual',0.99,'Potatoes.jpg'),(91,'EMERALD®','2018-10-08 00:00:00','2019-12-08 00:00:00',149,'FOOD','ON ANY TWO  EMERALD® NUTS PRODUCT',0.45,'EMERALDA.jpg'),(92,'Hot Wire','2018-10-08 00:00:00','2019-12-08 00:00:00',14,'TRAVELLING',' $10 Off Your Next Hotel',150.00,'hotel.jpg'),(93,'Gray Line','2018-10-08 00:00:00','2019-12-08 00:00:00',1000,'TRAVELLING','15% Off Bus Tours',3.75,'GrayLine.png'),(94,'Camping World','2018-10-08 00:00:00','2019-12-08 00:00:00',30,'TRAVELLING','30% off camper & RV covers',99.99,'camper.jpg'),(95,'Amazon','2018-10-08 00:00:00','2019-12-08 00:00:00',15,'ELECTRICITY','Pre-order Mafia III and get 15% discount',32.15,'MafiaIII.jpg'),(96,' Walgreen Co.','2018-10-08 00:00:00','2019-12-08 00:00:00',99,'HEALTH','1+1 on flu shot',99.50,'flu.jpg'),(97,'gfhfg','2016-10-24 00:00:00','2016-10-26 00:00:00',0,'FOOD','trhytr',10.55,'390_290_5cc10a4d-9a3f-4cfc-8.jpg');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CUST_NAME` varchar(45) DEFAULT NULL,
  `PASSWORD` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=311 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (117,'Robert Woo','rw1234'),(130,'Bill Joe','bj'),(309,'Gabi Abutbul','ga');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_coupon`
--

DROP TABLE IF EXISTS `customer_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_coupon` (
  `CUST_ID` bigint(20) NOT NULL,
  `COUPON_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`CUST_ID`,`COUPON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_coupon`
--

LOCK TABLES `customer_coupon` WRITE;
/*!40000 ALTER TABLE `customer_coupon` DISABLE KEYS */;
INSERT INTO `customer_coupon` VALUES (130,67),(130,87),(130,88),(130,91),(130,92),(130,96),(130,97);
/*!40000 ALTER TABLE `customer_coupon` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-26  0:32:16

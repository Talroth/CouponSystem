CREATE DATABASE  IF NOT EXISTS `couponsystem` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `couponsystem`;
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
INSERT INTO `company` VALUES (50,'McHoland','f12e12','McHoland@walla.com\r\n'),(51,'CafeOfea','g777a','CafeOfea@gmail.com'),(52,'GymX','uuu','GymX@yahoo.com'),(53,'CafeOfeapp','g77','CafeOfea@gmai1l.com');
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
INSERT INTO `company_coupon` VALUES (50,88);
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
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (67,'50% sandwich on the morning','2016-10-20 00:00:00','2017-01-20 00:00:00',998,'FOOD','mmm.....',9.90,'subway.png'),(88,'Coke','2016-10-08 00:00:00','2017-12-24 00:00:00',10,'FOOD','So natrual',1.23,'coke.jpg'),(89,'Potatoes','2016-10-08 00:00:00','2017-12-08 00:00:00',96,'FOOD','So natrual',0.99,'Potatoes.jpg'),(91,'EMERALD®','2018-10-08 00:00:00','2019-12-08 00:00:00',147,'FOOD','ON ANY TWO  EMERALD® NUTS PRODUCT',0.45,'EMERALDA.jpg'),(92,'Hot Wire','2018-10-08 00:00:00','2019-12-08 00:00:00',1,'TRAVELLING',' $10 Off Your Next Hotel',150.00,'hotel.jpg'),(93,'Gray Line','2018-10-08 00:00:00','2019-12-08 00:00:00',998,'TRAVELLING','15% Off Bus Tours',3.75,'GrayLine.png'),(94,'Camping World','2018-10-08 00:00:00','2019-12-08 00:00:00',20,'TRAVELLING','30% off camper & RV covers',99.99,'camper.jpg'),(95,'Amazon','2018-10-08 00:00:00','2019-12-08 00:00:00',14,'ELECTRICITY','Pre-order Mafia III and get 15% discount',32.15,'MafiaIII.jpg'),(96,' Walgreen Co.','2018-10-08 00:00:00','2019-12-08 00:00:00',98,'HEALTH','1+1 on flu shot',99.50,'flu.jpg');
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
) ENGINE=InnoDB AUTO_INCREMENT=310 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (117,'Robert Woo','rw1234'),(130,'Bill Joe','bj'),(309,'Gabi Abutbul','gaa');
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
INSERT INTO `customer_coupon` VALUES (117,89),(117,91),(117,92),(117,93),(130,67),(130,88),(130,89),(130,91),(130,92),(309,96);
/*!40000 ALTER TABLE `customer_coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incomelog`
--

DROP TABLE IF EXISTS `incomelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incomelog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `description` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `custid` bigint(20) DEFAULT '0',
  `compid` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incomelog`
--

LOCK TABLES `incomelog` WRITE;
/*!40000 ALTER TABLE `incomelog` DISABLE KEYS */;
INSERT INTO `incomelog` VALUES (36,'McHoland','2016-12-26 21:54:53',2,-10,0,50),(37,'McHoland','2016-12-26 21:55:18',2,-10,0,50),(38,'McHoland','2016-12-26 21:59:39',2,-10,0,50),(39,'McHoland','2016-12-27 20:22:41',2,-10,0,50),(40,'McHoland','2016-12-27 20:43:30',1,-100,0,50),(41,'McHoland','2016-12-27 20:49:49',1,-100,0,50),(42,'McHoland','2016-12-27 20:50:42',2,-10,0,50),(43,'McHoland','2016-12-27 20:50:50',2,-10,0,50),(44,'McHoland','2016-12-27 20:51:17',2,-10,0,50),(45,'McHoland','2016-12-27 20:51:24',2,-10,0,50),(46,'McHoland','2016-12-27 20:55:01',2,-10,0,50);
/*!40000 ALTER TABLE `incomelog` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-27 21:21:30

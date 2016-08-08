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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (7,'G.M.H5','bbb','b'),(8,'G.M.H6','aaa','info@gmh.com'),(9,'G.M.H7','aaa','info@gmh.com'),(11,'G.M.H9','aaa','bb@bb.com'),(12,'t','t','t@tal.com');
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
INSERT INTO `company_coupon` VALUES (12,48),(12,49),(12,50);
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
  `PRICE` decimal(4,2) DEFAULT NULL,
  `IMAGE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (13,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,0.00,NULL),(14,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,0.00,NULL),(15,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,0.00,NULL),(16,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,0.00,NULL),(17,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,0.00,NULL),(18,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,0.00,NULL),(19,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,0.00,'c:\\blala'),(20,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,0.00,'c:\\bla\\bla'),(21,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.00,'c:\\bla\\bla'),(22,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.40,'c:\\bla\\bla'),(23,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(24,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(25,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(26,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(27,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(28,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(29,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(30,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(31,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(32,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(33,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(34,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(35,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(36,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(37,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(38,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(39,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(40,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(41,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(42,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(43,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(44,'H1','2016-06-25 22:43:00','2016-07-25 22:43:00',100,'OTHER',NULL,4.35,'c:\\bla\\bla'),(45,'t','2011-12-03 10:15:30','2011-12-03 10:15:30',20,'RESTURANS','Test',0.00,'none'),(46,'tt','2017-07-05 13:00:00','2017-09-05 13:00:00',20,'SPORTS','Testing',0.00,'u'),(47,'ttt','2017-07-05 00:00:00','2017-09-05 00:00:00',150,'ELECTRICTY','jojopj',0.00,'none'),(48,'testJoin','2016-07-07 00:00:00','2017-08-08 00:00:00',300,'FOOD','gfhgf',55.64,'none'),(49,'testJoin','2016-07-07 00:00:00','2017-08-08 00:00:00',300,'FOOD','gfhgf',55.64,'none'),(50,'bool','2018-01-01 00:00:00','2018-04-01 00:00:00',100,'OTHER','n',10.00,'n');
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
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (4,'Tal','Boo'),(5,'Tal','kpkp'),(6,'Tal','Boo'),(7,'Tal','Boo'),(8,'Tal','Boo2'),(9,'Tal','Boo3'),(10,'Tal','Boo3'),(11,'Tal','Boo3'),(12,'Tal','Boo4'),(13,'Tal','Boo5'),(14,'Tal','Boo5'),(15,'Tal','Boo5'),(16,'Tal','Boo6'),(17,'Tal','Boo7'),(19,'Tal','Boo9'),(20,'Moshe','Boo10'),(21,'Moshe','Boo11'),(22,'Moshe','Boo11'),(23,'Moshe','Boo11'),(24,'Moshe','Boo11'),(25,'Moshe','Boo11'),(26,'Moshe','Boo11'),(27,'Moshe','Boo11'),(28,'Moshe','Boo11'),(29,'Moshe','Boo11'),(30,'Moshe','Boo11'),(31,'Moshe','Boo11'),(32,'Moshe','Boo11'),(33,'Moshe','Boo11'),(34,'Moshe','Boo11'),(35,'Moshe','Boo11'),(50,'Tal','Boo10'),(51,'Tal','Boo10'),(52,'Tal','Boo10'),(53,'Tal','Boo10'),(54,'Tal','Boola2'),(55,'Tal','Boo10'),(56,'Tal','Boo'),(57,'Tal','Boo99'),(58,'Tal','Boo'),(59,'Tal','Boo'),(60,'Tal','Boo'),(61,'Tal','Boo95'),(62,'Tal','Boola'),(63,'Tal1','Boola'),(64,'Tal3','Boola'),(65,'Tal4','Boola'),(66,'Tal5','Boola'),(67,'Tal6','gh'),(68,'Tal7','Boola'),(69,'Tal7','Boola'),(70,'Tal7','Boola'),(71,'Tal7','Boola'),(72,'Tal7','Boola'),(73,'Tal7','Boola'),(74,'Tal7','Boola'),(75,'Tal7','Boola'),(76,'Tal7','Boola'),(77,'Tal7','Boola'),(78,'Tal7','Boola'),(79,'Tal7','Boola'),(80,'Tal7','Boola'),(81,'Tal7','Boola'),(82,'Tal8','Boola'),(83,'Tal9','Boola'),(84,'Testing','abcd'),(85,'Testing2','abcd'),(86,'Testing3','abcd'),(87,'Testing4','abcd'),(88,'Testing5','abcd'),(89,'Testing6','abcd'),(90,'Testing7','abcd'),(91,'Testing8','abcd'),(100,'tal','roth');
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
INSERT INTO `customer_coupon` VALUES (4,6),(5,6),(100,13),(100,14),(100,21);
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

-- Dump completed on 2016-07-06  0:43:56

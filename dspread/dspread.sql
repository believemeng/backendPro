-- MySQL dump 10.13  Distrib 5.7.17, for osx10.12 (x86_64)
--
-- Host: localhost    Database: dspread
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `monitor`
--

DROP TABLE IF EXISTS `monitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `phone_model` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称',
  `system_version` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称',
  `appid` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称',
  `is_root` varchar(10) CHARACTER SET utf8mb4 DEFAULT '1' COMMENT '是否root',
  `lnt` varchar(45) DEFAULT '0.0' COMMENT '经度',
  `lat` varchar(45) DEFAULT '0.0' COMMENT '纬度',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitor`
--

LOCK TABLES `monitor` WRITE;
/*!40000 ALTER TABLE `monitor` DISABLE KEYS */;
/*!40000 ALTER TABLE `monitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '订单号',
  `mch_name` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商户名称',
  `pin` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'PIN',
  `data_hash` varchar(100) CHARACTER SET utf8mb4 DEFAULT '1' COMMENT '数据HASH',
  `holder_data` varchar(100) CHARACTER SET utf8mb4 DEFAULT '1' COMMENT '持卡人数据',
  `amount` decimal(18,2) DEFAULT '0.00' COMMENT '交易金额',
  `tran_time` timestamp NULL DEFAULT NULL COMMENT '交易时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `openid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `creator_openid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `unionid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `sex` tinyint(2) unsigned DEFAULT '0' COMMENT '0位置，1男，2女',
  `wechat_no` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `union_id_index` (`unionid`) USING HASH,
  KEY `open_id_index` (`openid`) USING HASH,
  KEY `creator_open_id_index` (`creator_openid`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
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

-- Dump completed on 2020-08-21 19:13:36



--
-- Table structure for table `merchant`
--

DROP TABLE IF EXISTS `merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `merchant` (
  `merchant_id` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商户ID',
  `merchant_name` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商户名称',
  `merchant_password` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商户密码',
  `merchant_company` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '公司',
  `merchant_phone` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '手机号',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


LOCK TABLES `merchant` WRITE;
UNLOCK TABLES;



--
-- Table structure for table `terminal`
--

DROP TABLE IF EXISTS `terminal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal` (
  `terminal_id` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '设备id',
  `bootLoader_version` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商户名称',
  `firmware_version` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商户密码',
  `hardware_version` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '公司',
  `PCI_firmwareVresion` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '手机号',
  `PCI_hardwareVersion` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '手机号',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`terminal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

LOCK TABLES `terminal` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `baseline`;

CREATE TABLE `baseline` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
   `cots_model` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '设备型号',
    `security_patch_level` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '安全补丁级别',
     `cots_version` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '系统版本',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


LOCK TABLES `baseline` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `errormsg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `errormsg` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `flag` smallint unsigned COMMENT '1验证scrp，2验证cots',
  `terminal_id` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '设备id',
  `data` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '数据',
  `error_msg` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'error 信息',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `errormsg` WRITE;
UNLOCK TABLES;
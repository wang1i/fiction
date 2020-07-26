/*
Navicat MySQL Data Transfer

Source Server         : 服务器
Source Server Version : 80018
Source Host           : 47.107.116.236:3306
Source Database       : org_fiction

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-01-06 17:11:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for chapter
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `fiction_id` int(20) NOT NULL,
  `chapter_title` varchar(50) NOT NULL,
  `content_id` int(20) NOT NULL,
  `create_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `sort` int(11) NOT NULL,
  `chapter_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=695045 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for chapter_content
-- ----------------------------
DROP TABLE IF EXISTS `chapter_content`;
CREATE TABLE `chapter_content` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `content` mediumtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=673913 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  `open` int(1) NOT NULL DEFAULT '1' COMMENT '1表示采集 0表示不采集',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for fiction
-- ----------------------------
DROP TABLE IF EXISTS `fiction`;
CREATE TABLE `fiction` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `fiction_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_date` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `author` varchar(20) NOT NULL,
  `type` varchar(20) NOT NULL,
  `newest` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `number` int(50) NOT NULL,
  `brief` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `img_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `fiction_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `views` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=979 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for fiction_shelf
-- ----------------------------
DROP TABLE IF EXISTS `fiction_shelf`;
CREATE TABLE `fiction_shelf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fiction_id` int(11) NOT NULL,
  `sort` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `shelf_ids` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

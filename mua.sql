/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : mua

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2014-07-17 09:15:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `userId` int(11) NOT NULL COMMENT '用户的id',
  `friendId` int(11) NOT NULL COMMENT '好友的id',
  `status` int(11) DEFAULT NULL COMMENT '与好友的状态（0：申请中未接受，1：已接受好友，2：亲密好友）',
  `specialName` varchar(100) DEFAULT NULL COMMENT '对好友的特殊显示名称',
  PRIMARY KEY (`userId`,`friendId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('1', '1', '1', '1');

-- ----------------------------
-- Table structure for u_user
-- ----------------------------
DROP TABLE IF EXISTS `u_user`;
CREATE TABLE `u_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `channelId` varchar(255) DEFAULT NULL,
  `userId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_user
-- ----------------------------
INSERT INTO `u_user` VALUES ('1', 'admin', 'admin', 'admin', '9', '3596091222931009892', '703661026382240612');

-- ----------------------------
-- View structure for v_friend
-- ----------------------------
DROP VIEW IF EXISTS `v_friend`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER  VIEW `v_friend` AS select `user`.id id, friend.friendId friendId, `user`.`name` name,`user`.`level` level,friend.`status` status,friend.specialName specialName
 from friend LEFT JOIN `user` on `user`.id = friend.userId ;

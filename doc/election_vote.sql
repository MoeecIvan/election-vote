/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : election_vote

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 20/07/2021 11:59:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for BIZ_CANDIDATE
-- ----------------------------
DROP TABLE IF EXISTS `BIZ_CANDIDATE`;
CREATE TABLE `BIZ_CANDIDATE` (
  `CANDIDATE_ID` bigint NOT NULL AUTO_INCREMENT COMMENT '候选人编号',
  `CANDIDATE_NAME` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `IMAGE` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `PROFILE` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '简介',
  `ELECTION_ID` bigint NOT NULL COMMENT '选举编号',
  `TOTAL` int NOT NULL COMMENT '得票数',
  `IS_WINNER` tinyint NOT NULL COMMENT '是否赢家 0 - 否, 1-是',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`CANDIDATE_ID`),
  KEY `IDX_CANDIDATE_INFO` (`CANDIDATE_ID`,`ELECTION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='候选人表';


-- ----------------------------
-- Table structure for BIZ_ELECTION
-- ----------------------------
DROP TABLE IF EXISTS `BIZ_ELECTION`;
CREATE TABLE `BIZ_ELECTION` (
  `ELECTION_ID` bigint NOT NULL AUTO_INCREMENT COMMENT '选举编号',
  `ELECTION_NAME` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '选举名称',
  `BRIFT` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '简介',
  `STATUS` tinyint NOT NULL COMMENT '选举状态 0-未启动, 1-选举中, 2-已结束',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `TEMPLATE_ID` int DEFAULT NULL COMMENT '规则模板编号',
  `IS_DEL` tinyint NOT NULL COMMENT '是否删除 0-未删除,1-已删除',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ELECTION_ID`),
  KEY `IDX_ELECTION_STATUS` (`ELECTION_ID`,`STATUS`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='选举表';


-- ----------------------------
-- Table structure for BIZ_MEMBER
-- ----------------------------
DROP TABLE IF EXISTS `BIZ_MEMBER`;
CREATE TABLE `BIZ_MEMBER` (
  `MEMBER_ID` bigint NOT NULL AUTO_INCREMENT COMMENT '会员编号',
  `LOGIN_NAME` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `PASSWORD` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `SALT` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '盐值',
  `NICK_NAME` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '昵称',
  `AVATAR` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `EMAIL` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `IDCARD` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证',
  `AUTH_TYPE` int NOT NULL COMMENT '认证标识类型,采用位标记法 0-未认证,1-邮箱认证,2-身份证认证,3-邮箱和身份证认证',
  `STATUS` tinyint NOT NULL COMMENT '用户状态 0-禁用,1-正常',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`MEMBER_ID`),
  UNIQUE KEY `UNI_MEMBER_LOGIN_NAME` (`LOGIN_NAME`) USING BTREE,
  KEY `IDX_MEMBER_MEMBER_INFO` (`MEMBER_ID`,`AUTH_TYPE`,`STATUS`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会员用户表';


-- ----------------------------
-- Table structure for BIZ_RULE_TEMPLATE
-- ----------------------------
DROP TABLE IF EXISTS `BIZ_RULE_TEMPLATE`;
CREATE TABLE `BIZ_RULE_TEMPLATE` (
  `TEMPLATE_ID` int NOT NULL AUTO_INCREMENT COMMENT '模板编号',
  `TEMPLATE_NAME` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '模板名称',
  `REMARK` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `IS_DEL` tinyint NOT NULL COMMENT '是否删除 0-未删除,1-已删除',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`TEMPLATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='规则模板表';


-- ----------------------------
-- Table structure for BIZ_VOTE
-- ----------------------------
DROP TABLE IF EXISTS `BIZ_VOTE`;
CREATE TABLE `BIZ_VOTE` (
  `ID` bigint NOT NULL COMMENT '编号',
  `ELECTION_ID` bigint NOT NULL COMMENT '选举编号',
  `CANDIDATE_ID` bigint NOT NULL COMMENT '候选人',
  `MEMBER_ID` bigint NOT NULL COMMENT '投票人编号',
  `VOTER_NAME` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '投票人名称',
  `AVATAR` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `VOTE_TIME` datetime NOT NULL COMMENT '投票时间',
  `IS_NOTIFIED` tinyint NOT NULL COMMENT '是否通知 0-没有,1-已通知',
  `NOTIFIED_TIME` datetime DEFAULT NULL COMMENT '通知时间',
  PRIMARY KEY (`ID`),
  KEY `IDX_VOTE_CANDIDATE` (`ELECTION_ID`,`CANDIDATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户投票记录表';


-- ----------------------------
-- Table structure for BIZ_VOTE_RULE
-- ----------------------------
DROP TABLE IF EXISTS `BIZ_VOTE_RULE`;
CREATE TABLE `BIZ_VOTE_RULE` (
  `RULE_ID` bigint NOT NULL AUTO_INCREMENT COMMENT '规则编号',
  `TEMPLATE_ID` int NOT NULL COMMENT '模板编号',
  `RULE_NAME` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '规则名称',
  `CONTENT` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '规则内容',
  `IS_ENABLE` tinyint NOT NULL COMMENT '是否启用 0-未启用, 1-启用',
  `IS_DEL` tinyint NOT NULL COMMENT '是否删除 0-未删除,1-已删除',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`RULE_ID`),
  KEY `IDX_RULE_TEMPLATE` (`TEMPLATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='规则表';


-- ----------------------------
-- Table structure for SYS_USER
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER`;
CREATE TABLE `SYS_USER` (
  `USER_ID` int NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `USER_NAME` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `SALT` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '盐值',
  `AVATAR` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `NICK_NAME` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `EMAIL` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `CREATED_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`USER_ID`),
  KEY `UNI_USER_USER_NAME` (`USER_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of SYS_USER
-- ----------------------------
BEGIN;
INSERT INTO `SYS_USER` VALUES (1, 'admin', '2d3ffb819495701a3516f77e81e0955db0e7482149e6f8b10f2fa6c3c0a8e09d', 'a4ba5c967b7048a9812620201dd00245', ' ', 'admin', NULL, '2021-07-19 16:41:30', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

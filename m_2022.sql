/*

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80029

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 02/11/2022 13:30:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for m_2022
-- ----------------------------
DROP TABLE IF EXISTS `m_2022`;
CREATE TABLE `m_2022` (
  `m_id` bigint NOT NULL,
  `m_info` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;

/*
SQLyog Professional v12.14 (64 bit)
MySQL - 8.0.28 : Database - sim
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sim` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `sim`;

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `menu_id` int NOT NULL AUTO_INCREMENT,
  `component` varchar(100) DEFAULT NULL,
  `path` varchar(100) DEFAULT NULL,
  `redirect` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  `is_leaf` varchar(1) DEFAULT NULL,
  `hidden` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `menu` */

insert  into `menu`(`menu_id`,`component`,`path`,`redirect`,`name`,`title`,`icon`,`parent_id`,`is_leaf`,`hidden`) values 
(1,'Layout','/sys','/sys/teacher','sysManage','系统管理','userManage',0,'N',0),
(2,'sys/student','student',NULL,'studentList','学生信息管理','studentManage',1,'Y',0),
(3,'sys/teacher','teacher','','teacherList','教师信息管理','teacherManage',1,'Y',0),
(4,'sys/role','role',NULL,'roleList','角色信息管理','roleManage',1,'Y',0),
(5,'Layout','/test','/test/test1','test','测试模块','form',0,'N',0),
(6,'test/test1','test1','','test1','测试功能一','form',5,'Y',0),
(7,'test/test2','test2','','test2','测试功能二','form',5,'Y',0),
(8,'test/test3','test3','','test3','测试功能三','form',5,'Y',0);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` char(20) DEFAULT NULL,
  `role_desc` char(20) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role` */

insert  into `role`(`role_id`,`role_name`,`role_desc`) values 
(1,'admin1','学生管理教师'),
(2,'admin2','教师管理教师'),
(3,'admin3','人事部主任');

/*Table structure for table `role_menu` */

DROP TABLE IF EXISTS `role_menu`;

CREATE TABLE `role_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT NULL,
  `menu_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role_menu` */

insert  into `role_menu`(`id`,`role_id`,`menu_id`) values 
(218,2,3),
(219,2,5),
(220,2,6),
(221,2,7),
(222,2,8),
(223,2,1),
(224,3,4),
(225,3,5),
(226,3,6),
(227,3,7),
(228,3,8),
(229,3,1),
(244,1,2),
(245,1,5),
(246,1,6),
(247,1,7),
(248,1,8),
(249,1,1);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` char(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` char(5) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `birthday` char(10) DEFAULT NULL,
  `academy` char(10) DEFAULT NULL,
  `deleted` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `student` */

insert  into `student`(`id`,`name`,`sex`,`birthday`,`academy`,`deleted`) values 
('B21031113','刘娟','女','2001.4.2','计软院',0),
('B21031230','袁深','男','2001.4.1','计软院',0),
('B21031911','唐丽','女','2002.1.1','计软院',0),
('B21031915','王穆','男','2002.6.4','计软院',0),
('B21031918','张磊','男','2003.1.2','计软院',0),
('B21031926','于浩宇','男','2002.6.28','计软院',0),
('B21041211','李楠','女','2001.3.7','通信学院',0),
('B21041312','孙署','男','2003.6.6','通信学院',0),
('B21070214','陈泽','男','2002.6.1','波特兰学院',0);

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `id` int NOT NULL,
  `name` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `position` char(10) DEFAULT NULL,
  `academy` char(10) DEFAULT NULL,
  `avatar` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `deleted` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `teacher` */

insert  into `teacher`(`id`,`name`,`password`,`sex`,`position`,`academy`,`avatar`,`deleted`) values 
(11411,'cvbnm','$2a$10$ibiq0o0Ffra4PQqdrFbpeebVZNKSaCkO5i66K7bihwKSGksWbXss.','男','教授','计软学院',NULL,1),
(12312,'kikim','$2a$10$Ff68vakCVeq6/2HLzjxQBOgthsUdG2DdsfXAcgt7Gy08DhKMNjClS','男','教授','计软院',NULL,0),
(12802,'mokiu','$2a$10$RiEY2y758skjUNDmYYhKK.qf4xUkSqEc68pKTe2wZCUQYw8HMvUy.','男','教授','计软院','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',1),
(14293,'admin','$2a$10$RiEY2y758skjUNDmYYhKK.qf4xUkSqEc68pKTe2wZCUQYw8HMvUy.','男','副教授','通信学院','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',0),
(15234,'mokiu','$2a$10$RiEY2y758skjUNDmYYhKK.qf4xUkSqEc68pKTe2wZCUQYw8HMvUy.','男','教授','计软院','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',0),
(15826,'lllllll','$2a$10$nI5OHa5mgsLeXEmikM9Sxe5g8L8QCXbLIZCnOsUBmLLIN4RPuaJM.','男','男','计软院',NULL,0),
(17264,'kidult','$2a$10$RiEY2y758skjUNDmYYhKK.qf4xUkSqEc68pKTe2wZCUQYw8HMvUy.','女','副教授','波特兰学院','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',0);

/*Table structure for table `teacher_role` */

DROP TABLE IF EXISTS `teacher_role`;

CREATE TABLE `teacher_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `teacher_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `teacher_role` */

insert  into `teacher_role`(`id`,`teacher_id`,`role_id`) values 
(5,12312,3),
(6,15826,2),
(7,15826,1),
(11,15234,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(80) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `nickname` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '昵称',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `adduser` int(11) NOT NULL COMMENT '添加人',
  `status` int(11) NOT NULL COMMENT '状态【0正常、1冻结】',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

create unique index unique_username on sys_user
(
   username
);

CREATE TABLE `task_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `isrecemail` int(11) NOT NULL COMMENT '接收邮件（0否1是）',
  `recemail` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '接收邮箱（多个,分隔）',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `adduser` int(11) NOT NULL COMMENT '添加人',
  `sign` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '签名规则',
  `signstring` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '签名参数与内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `task_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `projectid` int(11) NOT NULL COMMENT '项目编号',
  `name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `remark` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '描叙',
  `link` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '调用链接',
  `cron` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '执行规则',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `isfailmail` int(11) NOT NULL COMMENT '失败发邮件（0否1是）',
  `adduser` int(11) NOT NULL COMMENT '添加人',
  `status` int(11) NOT NULL COMMENT '状态【枚举JobStatus】',
  `statusmsg` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '状态消息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `task_job_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `jobid` int(11) NOT NULL COMMENT '任务编号',
  `addtime` datetime NOT NULL COMMENT '添加时间',
  `status` int(11) NOT NULL COMMENT '状态',
  `link` varchar(300) COLLATE utf8_bin NOT NULL COMMENT '请求地址',
  `result` text COLLATE utf8_bin NOT NULL COMMENT '调度返回结果',
  `servicecode` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '服务编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL auto_increment COMMENT '编号',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `value` varchar(100) NOT NULL COMMENT '值',
  `remark` varchar(100) default NULL COMMENT '描叙',
  `exp1` varchar(100) default NULL COMMENT '扩展1',
  `exp2` varchar(100) default NULL COMMENT '扩展2',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `sys_config`(`id`,`code`,`name`,`value`,`remark`,`exp1`,`exp2`)
 VALUES (1,'task.main.cron','主线程的时间表达式','0/1 * * * * ?',default,default,default);
 
INSERT INTO `sys_config`(`id`,`code`,`name`,`value`,`remark`,`exp1`,`exp2`)
 VALUES (2,'mail.smtp','发送邮箱的smtp','smtp.163.com',default,default,default);
INSERT INTO `sys_config`(`id`,`code`,`name`,`value`,`remark`,`exp1`,`exp2`)
 VALUES (3,'mail.from','发送邮件的邮箱','xxx@163.com',default,default,default);
INSERT INTO `sys_config`(`id`,`code`,`name`,`value`,`remark`,`exp1`,`exp2`)
 VALUES (4,'mail.username','发送邮件的用户名','xxxx',default,default,default);
INSERT INTO `sys_config`(`id`,`code`,`name`,`value`,`remark`,`exp1`,`exp2`)
 VALUES (5,'mail.password','发送邮件的密码','xxxxxx',default,default,default);
INSERT INTO `sys_config`(`id`,`code`,`name`,`value`,`remark`,`exp1`,`exp2`)
 VALUES (6,'joblog.save.day','调度记录保存天数','7',default,default,default);
INSERT INTO `sys_config`(`id`,`code`,`name`,`value`,`remark`,`exp1`,`exp2`)
 VALUES (7,'joblog.clean.cron','清空调度记录表达式','0 0 23 * * ?',default,default,default);


INSERT INTO `sys_user`(`id`,`username`,`password`,`nickname`,`addtime`,`adduser`,`status`)
 VALUES (1,'admin','E00CF25AD42683B3DF678C61F42C6BDA','管理员',now(),1,0);


USE `task`;

/*Data for the table `task_project` */

insert  into `task_project`(`id`,`name`,`remark`,`isrecemail`,`recemail`,`addtime`,`adduser`,`sign`,`signstring`) 
values (1,'测试','测试',0,'','2016-04-28 00:20:22',1,'0','{}');

/*Data for the table `task_job` */
insert  into `task_job`(`id`,`projectid`,`name`,`remark`,`link`,`cron`,`addtime`,`isfailmail`,`adduser`,`status`,`statusmsg`,`servid`,`updatetime`) 
values 
(1,1,'1','1','http://127.0.0.1:8088/task/view/test/success.jsp?string=1','0/15 * * * * ?','2016-04-28 00:21:08',0,1,0,NULL,'','2016-04-28 00:38:33'),
(2,1,'2','2','http://127.0.0.1:8088/task/view/test/success.jsp?string=2','0/15 * * * * ?','2016-04-28 00:21:08',0,1,0,NULL,'','2016-04-28 00:38:33'),
(3,1,'3','3','http://127.0.0.1:8088/task/view/test/success.jsp?string=3','0/15 * * * * ?','2016-04-28 00:21:08',0,1,0,NULL,'','2016-04-28 00:38:33'),
(4,1,'4','4','http://127.0.0.1:8088/task/view/test/success.jsp?string=4','0/15 * * * * ?','2016-04-28 00:21:08',0,1,0,NULL,'','2016-04-28 00:38:33'),
(5,1,'5','5','http://127.0.0.1:8088/task/view/test/success.jsp?string=5','0/15 * * * * ?','2016-04-28 00:21:08',0,1,0,NULL,'','2016-04-28 00:38:33');


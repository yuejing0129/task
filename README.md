架构
	服务端：springMVC、mybatis、Quartz
	前端：jquery、boostarp

简要思路
	1.一个定时添加任务的线程
	2.一个定时执行任务的线程
	3.一个清空日志的任务线程

异常通知方案
	调用失败后，可以支持email提醒
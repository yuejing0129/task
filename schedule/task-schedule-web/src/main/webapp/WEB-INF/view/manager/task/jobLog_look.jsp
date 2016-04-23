<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-查看调度信息</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div>
  		<div class="form-group">执行状态：<my:text id="${taskJobLog.status}" dictcode="job_log_status"/></div>
  		<div class="form-group">调度时间：<my:date value="${taskJobLog.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
  		<div class="form-group">调用链接：${taskJobLog.link}</div>
  		<div class="form-group">响应内容：${taskJobLog.result}</div>
	</div>

	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
</body>
</html>
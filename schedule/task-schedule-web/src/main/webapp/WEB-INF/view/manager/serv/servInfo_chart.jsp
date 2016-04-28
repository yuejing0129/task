<%@page import="com.task.schedule.comm.enums.Boolean"%>
<%@page import="com.task.schedule.comm.enums.ServInfoStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-服务图表</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/manager/comm/left.jsp">
			<jsp:param name="first" value="project"/>
			<jsp:param name="second" value="servInfoManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading">任务管理 / <b>服务管理</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
							<!-- <span class="enter-panel">
								<input type="text" style="width: 100px;display: inline;" class="form-control input-sm" id="projectName" placeholder="项目名称">
					  			<button type="button" class="btn btn-sm btn-success enter-fn" onclick="info.loadInfo(1)">查询</button>
					  		</span> -->
						  	<div class="btn-group">
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
						  	</div>
						</div>
						<div class="col-sm-6 text-right">
						  	<div class="btn-group">
							  	<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">图表展示</a>
						  	</div>
						</div>
					</div>
				  	<hr/>
					<div id="infoPanel">
						<div id="main" style="width: 600px;height:400px;"></div>
					</div>
				</div>
			</div>
		</div>
		<br clear="all">
	</div>
	<jsp:include page="/WEB-INF/view/inc/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/utils/chart.jsp"></jsp:include>
<script type="text/javascript">
//基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

// 指定图表的配置项和数据
var option = {
    title: {
        text: 'ECharts 入门示例'
    },
    tooltip: {},
    legend: {
        data:['销量']
    },
    xAxis: {
        data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
    },
    yAxis: {},
    series: [{
        name: '销量',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
    }]
};

// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);
$(function() {
});
</script>
</body>
</html>
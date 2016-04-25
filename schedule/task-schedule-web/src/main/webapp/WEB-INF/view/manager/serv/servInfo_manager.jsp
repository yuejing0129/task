<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-服务管理</title>
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
				  	<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
				  	<div class="btn-group">
				  		<a href="javascript:$('#queryPanel').panelToggle()" class="btn btn-default btn-sm">查询</a>
				  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增项目</a>
				  	</div>
				  	<hr/>
				  	<div id="queryPanel" style="display: none;" class="enter-panel">
				  		<input type="text" style="width: 100px;display: inline;" class="form-control input-sm" id="projectName" placeholder="项目名称">
					  	<button type="button" class="btn btn-sm btn-default enter-fn" onclick="info.loadInfo(1)">查询</button>
						<hr/>
				  	</div>
					<div id="infoPanel"></div>
					<div id="infoPage"></div>
				</div>
			</div>
		</div>
		<br clear="all">
	</div>
	<jsp:include page="/WEB-INF/view/inc/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/utils/page.jsp"></jsp:include>
<script type="text/javascript">
var infoPage = undefined;
var info = {
		//获取用户信息
		loadInfo : function(page) {
			if(!infoPage) {
				infoPage = new Page('infoPage', info.loadInfo, 'infoPanel', 'infoPage');
				infoPage.beginString = ['<table class="table table-striped table-hover"><thead><tr class="info">',
				                         '<th>服务编号</th>',
				                         '<th>ip地址</th>',
				                         '<th>添加时间</th>',
				                         '<th>更新时间</th>',
				                         '<th>状态</th>',
				                         '<th width="120">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/servInfo/f_json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size, name:$('#projectName').val() },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.result === 'success') {
						function getResult(obj) {
							return ['<tr>',
							    	'<td>',obj.servid,'</td>',
							    	'<td>',obj.ip,'</td>',
							    	'<td>',obj.addtime,'</td>',
							    	'<td>',obj.updatetime,'</td>',
							    	'<td>',obj.statusname,'</td>',
							    	'<td><a class="btn btn-link btn-xs" href="',webroot,'/taskJob/f_view/manager.shtml?projectid=',obj.id,'">任务管理</a> ',
							    	'&nbsp; <a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(',obj.id,')" title="修改"></a></td>',
								'</tr>'].join('');
						}
						infoPage.operate(json, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//编辑项目
		edit : function(id) {
			dialog({
				title: '编辑项目',
				url: webroot + '/taskProject/f_view/edit.shtml?id='+(id?id:''),
				type: 'iframe',
				width: 420,
				height: 460
			});
		}
};
$(function() {
	info.loadInfo(1);
});
</script>
</body>
</html>
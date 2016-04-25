<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-登录</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
		<div class="form-group">
			<input type="text" class="form-control" id="loginUname" placeholder="登录名">
		</div>
		<div class="form-group">
			<input type="password" class="form-control" id="loginUpwd" placeholder="密码">
		</div>
		<div class="form-group">
			<button type="button" id="loginBtn" class="btn btn-success enter-fn">确认登录</button>
			<span class="text-danger" id="saveMsg"></span>
		</div>
	</div>

	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<script type="text/javascript">
	$(function() {
		$('#loginBtn').click(function() {
			var _loginBtn = $('#loginBtn');
			
			var _saveMsg = $('#saveMsg').empty();
			
			var _username = $('#loginUname');
			if(JUtil.isEmpty(_username.val())) {
				_username.focus();
				return;
			}
			var _password = $('#loginUpwd');
			if(JUtil.isEmpty(_password.val())) {
				_password.focus();
				return;
			}
			
			var _orgVal = _loginBtn.html();
			_loginBtn.attr('disabled', 'disabled').html('登录中...');
			JUtil.ajax({
				url: webroot + '/sysUser/json/login.shtml',
				data: {username:_username.val(),password:_password.val()},
				success: function(json) {
					if(json.result==='success') {
						parent.location = webroot + '/sysUser/f_view/main.shtml';
					}
					else if(json.result==='error') _saveMsg.append(JUtil.msg.ajaxErr);
					else _saveMsg.append(json.msg);
					_loginBtn.removeAttr('disabled').html(_orgVal);
				}
			});
		});
	});
	</script>
</body>
</html>
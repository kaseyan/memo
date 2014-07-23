<%@page session="true" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>WsMemo Login</title>
	<script type="text/javascript" src="./js/wsmemo.js"></script>
	<link rel="stylesheet" type="text/css" href="./login/login.css">

</head>
<body>
	<div id="header" >
	</div>
	<div id="bd">
	<div id="ttl">Memo</div>
	<hr size="5" color="red" width="90%"  align="left"/>
	<hr size="2" color="red" width="90%"  align="right"/>
	<form id="login-form" action="./sync" method="post">
		<input type="hidden" name="command" value="login"/>
		<input type="text" name="userName" />
		<input type="submit" value="ログイン" />
	</form>
	</div>
	<div id="footer">Tomcat V7.0.54, FireFox 30.0で動作確認。
	</div>
</body>
</html>
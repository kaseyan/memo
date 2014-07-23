<%@page import="java.util.Set"%>
<%@page import="ksd.memo.manager.PageBuilder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script type="text/javascript" src="/Memo/dir/top.js"></script>
	<link rel="stylesheet" type="text/css" href="/Memo/dir/top.css">
<title>Insert title here</title>
</head>
<%
	Set<String> names = PageBuilder.getNames();
%>
<body>
<form method="post" action="./sync">
	<input type="hidden" name="command" value="memo" />
	<input type="text"  name="pageName"   />
	<input type="submit"  value="作成" />
</form>

<%
	for (String name : names) {
%>
<form method="post" action="./sync" name="Form1">
	<input type="hidden" name="command" value="memo" />
	<input type="hidden"  name="pageName" value="<%= name %>"  />
	<a href="javascript:Form1.submit()"><%= name %></a>
</form>
<%
	}
%>


</body>
</html>
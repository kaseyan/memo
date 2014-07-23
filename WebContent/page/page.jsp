<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="ksd.common.manager.ManagerOperator"%>
<%@page import="ksd.memo.manager.PageBuilder" %>
<%@page import="ksd.memo.data.Consts"%>
<%@page import="ksd.memo.data.Line"%>
<%@page import="ksd.memo.data.Page"%>
<%@page import="java.util.List"%>
<%
	request.setCharacterEncoding("utf-8");
	String pageName = (String)request.getParameter("pageName");//request.getAttribute("pageName");//
	// URLDecoder.decode(pageName,"utf-8")
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><%= pageName %></title>
	<script type="text/javascript" src="/Memo/js/wsmemo.js"></script>
	<script type="text/javascript" src="/Memo/page/page.js"></script>
	<link rel="stylesheet" type="text/css" href="/Memo/page/page.css">

<!-- style>

</style -->

<script type="text/javascript">
	var ws;
	var SEPARATE = "<%= Consts.separate %>";
	var req;
	var onLock = false;

	// 初期化(bodyのonloadハンドラ)
	function init() {
		var url = "ws://<%= Consts.host %>/<%= Consts.context %>/messaging/<%= pageName %>/<%=session.getId() %>";
		ws = new WebSocket(url);
		ws.onopen    = function(e) {};
		ws.onmessage = function(e) { eventHandler(e); };
		ws.onclose   = function(e) {};

		req = createXMLHttpRequest();
		req.open("post", "/Memo/memo", true);
		req.setRequestHeader( 'Content-Type', 'application/x-www-form-urlencoded' );
		req.onreadystatechange = function (e) { responseHandler(e); };

	<%
		Page p = PageBuilder.getPage(pageName);
		List<Line> lines = p.getAll();
		for (Line l : lines) {
	%>
		document.getElementById('parent').appendChild(createLine('<%= l.hashCode() %>', '<%= l.getData() %>'));
	<%
		}
	%>
	}

	// サーバにイベントを伝える関数群
	function pressEnter(line) {
		postToServer("<%= Consts.COMMAND_FIXED %>", '&lineId='+line.id+'&pageId=' + '<%= pageName %>'+'&data='+line.innerHTML);
		// sendMessage("<%= Consts.COMMAND_FIXED %>", line.id + SEPARATE + line.innerHTML);
	}
	function onInput(id, txt) {
		sendMessage("<%= Consts.COMMAND_ON_EDIT %>", id + SEPARATE + txt);
	}
	function getLock(id) {
		if (!onLock) {
			postToServer("<%= Consts.COMMAND_LOCK %>", '&lineId='+id+'&pageId='+"<%= pageName %>");
		}
	}
	function releaseLock(id) {
		if (onLock) {
			postToServer("<%= Consts.COMMAND_UNLOCK %>", '&lineId='+id+'&pageId='+"<%= pageName %>");
		}
	}

	// サーバからのデータをハンドルする関数(HTTP POST)
	function responseHandler(e) {
		if (req.readyState == 4 && req.status == 200) {
			$("console").innerHTML += "res:" + req.responseText +"\n";
			eval("res=" + req.responseText);
			switch(res.command) {
			case "<%= Consts.COMMAND_FIXED %>":
				//resCommandFixedHandler(res);
				break;
			case "<%= Consts.COMMAND_LOCK %>":
				if (res.txt=="<%= Consts.COMMAND_SUCCESS %>") {
					$(res.targetId).style.backgroundColor = '#7FFF00';
					$(res.targetId).contentEditable = true;
					$(res.targetId).focus();
					onLock = true;
				} else {
				}
				break;
			case "<%= Consts.COMMAND_UNLOCK %>":
				msgCommandUnlockHandler(res);
				onLock = false;
				break;
			}
		}
	}

	// サーバからのデータをハンドルする関数(WebSocket)
	function eventHandler(message) {
		eval("msg=" + message.data);
		$("console").innerHTML += "msg:" + msg.command + "\n";
		switch(msg.command) {
		case "<%= Consts.COMMAND_FIXED %>":
			resCommandFixedHandler(msg);
			break;
		case "<%= Consts.COMMAND_ON_EDIT %>":
			msgCommandOnEditHandler(msg);
			break;
		case "<%= Consts.COMMAND_LOCK %>":
			msgCommandLockHandler(msg);
			break;
		case "<%= Consts.COMMAND_UNLOCK %>":
			msgCommandUnlockHandler(msg);
			break;
		}
	}

	// 通信関数
	function postToServer(command, data) {
		$("console").innerHTML += "POST:"+command + "\n";
		req.open("post", "/Memo/memo", true);
		req.setRequestHeader( 'Content-Type', 'application/x-www-form-urlencoded' );
		req.send('command='+command+data);
	}
	function sendMessage(command, data) {
		$("console").innerHTML += "MESSAGE:"+command + "\n";
		ws.send(command + SEPARATE + data);
	}
	function closeConnect() {
		ws.close();
	}

	// 画面編集関数
	function insertLine(pid, id, data) {
		$("console").innerHTML += "FUNC insertLine:"+pid+":"+id+":"+data;
		var newLine = createLine(id, data);
		$("parent").insertBefore(newLine, $(pid).nextSibling);
	}
	function createLine(id, str) {
		var line = document.createElement("div");
		line.id = id;
		line.innerHTML = str;
		line.onclick = function(e) {
			getLock(line.id);
		};
		line.onkeypress = function(e) {
			if (e.keyCode == 13) {
				//$("console").innerHTML += "Event:onkeypress.";
				pressEnter(line);
				return false;
			}
		};
		line.onkeyup = function(e) {
			if (e.keyCode == 13) {
				onInput(line.id, line.innerHTML);
				return false;
			}
		};
//		line.onfocus = function(e) {
//			getLock(line.id);
//		};
		line.onblur = function(e) {
			releaseLock(line.id);
		};
		line.contentEditable = false;
		line.style = "display:inline-block; _display:inline;"+
					"background-color: #DDD;" +
					"border-radius:6px;";
		return line;
	}
	function print(str) {
		$("console").innerHTML = str;
	}

</script>
</head>
<body style="background-color: #DDD;" onload="init();">
	<textarea id="console" style="position: absolute;"></textarea>
	<div id="parent" class="parent"></div>
</body>

</html>
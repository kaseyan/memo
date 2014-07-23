
	// LockHandler
	function msgCommandLockHandler(msg) {
		$(msg.targetId).contentEditable = false;
		$(msg.targetId).style.backgroundColor = '#FF0000';
	}

	// UnLockHandler
	function msgCommandUnlockHandler(msg) {
		$(msg.targetId).style.backgroundColor = '#DDD';
		$(msg.targetId).contentEditable = false;
	}

	// FixedHandler
	function resCommandFixedHandler(res) {
		var json = "{" + res.txt.split("'").join("\"") + "}";
		eval("msgs=" + json);
		var ary = msgs.messages;
		//1こ目
		$(ary[0].targetId).innerHTML = ary[0].txt;
		//2こ目以降
		var before = ary[0].targetId;
		for ( var i = 1; i < ary.length; i++) {
			insertLine(before, ary[i].targetId, ary[i].txt);
			before = ary[i].targetId;
		}
	}

	// OnEditHandler
	function msgCommandOnEditHandler(msg) {
		$(msg.targetId).innerHTML = msg.txt;
	}
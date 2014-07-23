
function $(id) {
	return document.getElementById(id);
}

function createXMLHttpRequest() {
	var res = null;
	try {
		res = new XMLHttpRequest();
	} catch (e) {
		try {
			res = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				res = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				return null;
			}
		}
	}
	return res;
}

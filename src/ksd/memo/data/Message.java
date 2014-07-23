package ksd.memo.data;

public class Message {
	private String command;
	private String targetId;
	private String txt;

	public Message(String cmd) {
		this.command = cmd;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String toJson() {
		return "{command:\"" + command + "\", targetId:\"" + targetId + "\",txt:\""+txt+"\"}";
	}

}

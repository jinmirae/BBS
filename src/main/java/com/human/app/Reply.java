package com.human.app;

public class Reply {
	int reply_id;
	String content;
	String writer;
	String created;
	String updated;
	
	public Reply() {}
	public Reply(int reply_id, String content, String writer, String created, String updated) {
		this.reply_id = reply_id;
		this.content = content;
		this.writer = writer;
		this.created = created;
		this.updated = updated;
	}
	public int getReply_id() {
		return reply_id;
	}
	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	
	
}

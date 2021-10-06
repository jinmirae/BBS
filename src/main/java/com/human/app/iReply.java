package com.human.app;

import java.util.ArrayList;

public interface iReply {
	void add(int bbs_id, String content, String userid);
	ArrayList<Reply> getReplyList(int bbs_id);
	void delete(int reply_id);

}

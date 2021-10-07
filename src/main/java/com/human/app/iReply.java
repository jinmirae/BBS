package com.human.app;

import java.util.ArrayList;

public interface iReply {
	void add(int bbs_id, String content, String userid);//void는 xml에서 언급한 변수들 모두 선언해주기
	ArrayList<Reply> getReplyList(int bbs_id);
	void delete(int reply_id);
	void update(String content, int reply_id);

}

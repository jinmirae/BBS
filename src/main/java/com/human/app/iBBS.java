package com.human.app;

import java.util.ArrayList;

public interface iBBS {
	void writebbs (String sTitle, String sContent, String sWriter, String sPasscode);
	ArrayList<BBSrec> getList();
	BBSrec getPost(int bbs_id);//selectmapper에서 선언한 테이블정보 중 고유번호인 bbs_id만을 param으로 불러옴
	void deletebbs (int nBbsId);//deletemapper에서 선언한 #{param1}을 인터페이스에서는 사용될 명칭 nBbsId 
	void updatebbs (int nBbsId, String sTitle, String sContent);//updatemapper에서 선언한 테이블 수와  일치한지 확인
}

package com.human.app;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		String formattedDate = dateFormat.format(date);
//		model.addAttribute("serverTime", formattedDate );
//		return "home";
//	}
	
	@RequestMapping("/login")
	public String doLogin(HttpServletRequest hsr) {
		if(loginUser(hsr)) return "redirect:/list";
		return "login";
	}
	
	@RequestMapping("/logout")
	public String doLogout(HttpServletRequest hsr) {
		HttpSession session=hsr.getSession();
		session.invalidate();
		return "redirect:/list";
	}
	
	@RequestMapping("/newbie")
	public String newbie(HttpServletRequest hsr) {
		if(loginUser(hsr)) return "redirect:/list";
		return "/newbie";
	}
	
	@RequestMapping(value="/signin",method=RequestMethod.POST)//회원가입 DB
	public String doSignin(HttpServletRequest hsr) {
		if(loginUser(hsr)) return "redirect:/list";
		String realname=hsr.getParameter("realname");
		String userid=hsr.getParameter("userid");
		String passcode=hsr.getParameter("passcode");
		System.out.println("realname= ["+realname+"]");
		System.out.println("userid= ["+userid+"]");
		System.out.println("passcode= ["+passcode+"]");
		iMember mem=sqlSession.getMapper(iMember.class);
		mem.doSignin(realname, userid, passcode);
		return "redirect:/login";
	}
	
	@RequestMapping(value="/check_user", method=RequestMethod.POST)
	public String reserve(HttpServletRequest hsr, Model model) {
		String id=hsr.getParameter("id");
		String pass=hsr.getParameter("pass");
		System.out.println("id= ["+id+"]");
		System.out.println("pass= ["+pass+"]");
		//DB에서 유저확인 : 기존유저면 reserve, 없으면 home으로.
		iMember member=sqlSession.getMapper(iMember.class);
		int n=member.doCheckUser(id,pass);
		if(n==1) {
			HttpSession session=hsr.getSession();
			session.setAttribute("userid", id);
			return "redirect:/list";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String getList(HttpServletRequest hsr, Model model) {
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		ArrayList<BBSrec> bbsrec = bbs.getList();
		HttpSession session=hsr.getSession();
		String userid=(String) session.getAttribute("userid");
		System.out.println("Userid ["+userid+"]");
		if(userid==null || userid.equals("")) {
			model.addAttribute("loggined","0");
		} else {
			model.addAttribute("loggined","1");
			model.addAttribute("userid",userid);
		}
		model.addAttribute("letlist",bbsrec);
		return "list";
	}
	
	@RequestMapping(value = "/view/{bbs_id}",method = RequestMethod.GET)
	public String selectOnBBS(@PathVariable("bbs_id") int bbs_id,
			HttpServletRequest hsr,Model model) {
		if(!loginUser(hsr)) return "redirect:/list";
		System.out.println("bbs_id ["+bbs_id+"]");
		//게시물 내용 가져오기
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		BBSrec post=bbs.getPost(bbs_id);
		model.addAttribute("post",post);
		//현재 사용자 아이디 가져오기
		HttpSession session=hsr.getSession();
		model.addAttribute("userid",session.getAttribute("userid"));
		
		//로그인여부 체크
		String userid=(String) session.getAttribute("userid");
		System.out.println("Userid ["+userid+"]");
		if(userid==null || userid.equals("")) {
			model.addAttribute("loggined","0");
		} else {
			model.addAttribute("loggined","1");
			model.addAttribute("userid",userid);
		}
		
		iReply r=sqlSession.getMapper(iReply.class);
		ArrayList<Reply> replyList=r.getReplyList(bbs_id);
		model.addAttribute("reply_list",replyList);
		return "view";
	}
	
	@RequestMapping(value = "/new",method = RequestMethod.GET)
	public String brandNew(HttpServletRequest hsr,Model model) {
		HttpSession s=hsr.getSession();
		String userid=(String) s.getAttribute("userid");
		if(userid==null || userid.equals("")) {
			return "redirect:/list";
		}
		model.addAttribute("userid",userid);
		return "new";
//		if(loginUser(hsr)) return "new";
//		return "redirect:list";
	}
	
//	@RequestMapping(value = "/update_view",method = RequestMethod.GET)
//	public String updateView() {
//		return "update";
//	}

	@RequestMapping(value = "/save",method = RequestMethod.POST)//보여지는 jsp from의 action
	public String insertBBS(HttpServletRequest hsr) {
		if(!loginUser(hsr)) return "redirect:/list";

		String pTitle = hsr.getParameter("title");
		String pContent = hsr.getParameter("content");
		String pWriter = hsr.getParameter("writer");
		//tring pPasscode = hsr.getParameter("passcode");
		//debugging code
		//System.out.println("title ["+pTitle+"] content ["+pContent+"] writer ["+pWriter+"] passcode ["+pPasscode+"]");
		//insert into DB table
		iBBS bbs=sqlSession.getMapper(iBBS.class);//인터페이스 호출 준비
		bbs.writebbs(pTitle, pContent, pWriter/*, pPasscode*/);//인터페이스 paramiter 전달
		return "redirect:/list";
	}
	
	@RequestMapping(value = "/update/{bbs_id}",method = RequestMethod.GET, 
			produces = "application/text; charset=utf8")
	public String upviewBBS(@PathVariable("bbs_id")int bbs_id,
			HttpServletRequest hsr,Model model) {
		if(!loginUser(hsr)) return "redirect:/list";
		
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		BBSrec post=bbs.getPost(bbs_id);
		model.addAttribute("update",post);
		return "/update";
		}
	 
	@RequestMapping(value = "/update",method = RequestMethod.POST,
			produces = "application/text; charset=utf8")
	public String updateBBS(HttpServletRequest hsr) {
		int uBbsid = Integer.parseInt(hsr.getParameter("bbs_id"));
		String uTitle = hsr.getParameter("title");
		String uContent = hsr.getParameter("content");
		iBBS bbs = sqlSession.getMapper(iBBS.class);
		bbs.updatebbs(uBbsid, uTitle, uContent);
		return "redirect:/list";
	}
	
	@RequestMapping(value = "/delete/{bbs_id}",method = RequestMethod.GET) 
		public String deleteBBS(@PathVariable("bbs_id")int bbs_id,
				HttpServletRequest hsr,Model model) {
		if(!loginUser(hsr)) return "redirect:/list";
		System.out.println("bbs_id ["+bbs_id+"]");
			iBBS bbs=sqlSession.getMapper(iBBS.class);
			bbs.deletebbs(bbs_id);
			return "redirect:/list";
		}	
	
	public boolean loginUser(HttpServletRequest hsr) {//로그인체크 
		HttpSession s=hsr.getSession();
		String userid=(String) s.getAttribute("userid");
		if(userid==null || userid.equals("")) return false;//아이디 확인이 되면 경로 오픈
		return true;
		
	}
	
	@RequestMapping(value ="/ReplyControl",method = RequestMethod.POST)
	@ResponseBody
	public String doReplyControl(HttpServletRequest hsr) {
		System.out.println("doReplyControl");
		String result="";
		try {
			String optype=hsr.getParameter("optype");
			iReply reply=sqlSession.getMapper(iReply.class);//get mapper는 필수!! 상단에 선언하여 사용 
			if(optype.equals("add")) {
				String reply_content=hsr.getParameter("content");
				int bbs_id=Integer.parseInt(hsr.getParameter("bbs_id"));
				System.out.println("bbs_id ["+bbs_id+"]");
				HttpSession s=hsr.getSession();
				String userid=(String) s.getAttribute("userid");
				System.out.println("userid ["+userid+"]");
				
				reply.add(bbs_id,reply_content,userid);
			} else if(optype.equals("delete")) {//댓글 삭제
				int reply_id=Integer.parseInt(hsr.getParameter("reply_id"));
				reply.delete(reply_id);
			} else if(optype.equals("update")) {//댓글 수정
				String content=hsr.getParameter("content");
				int reply_id=Integer.parseInt(hsr.getParameter("reply_id"));
				reply.update(content, reply_id);
			}
			result="ok";
		} catch(Exception e) {
			result="fail";
		} finally {
			return "result";
		}
	}
	
}
	
 

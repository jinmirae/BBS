package com.human.app;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String getList(HttpServletRequest hsr, Model model) {
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		ArrayList<BBSrec> bbsrec = bbs.getList();
		System.out.println(bbsrec);
		model.addAttribute("letlist",bbsrec);
		
		return "list";
	}
	
	@RequestMapping(value = "/view/{bbs_id}",method = RequestMethod.GET)
	public String selectOnBBS(@PathVariable("bbs_id")int bbs_id,Model model) {
		System.out.println("bbs_id ["+bbs_id+"]");
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		BBSrec post=bbs.getPost(bbs_id);
		model.addAttribute("post",post);
		return "view";
	}
	
	@RequestMapping(value = "/new",method = RequestMethod.GET)
	public String brandNew() {
		return "new";
	}
	
//	@RequestMapping(value = "/update_view",method = RequestMethod.GET)
//	public String updateView() {
//		return "update";
//	}
//	
	@RequestMapping(value = "/save",method = RequestMethod.POST)//보여지는 jsp from의 action
	public String insertBBS(HttpServletRequest hsr) {
		String pTitle = hsr.getParameter("title");
		String pContent = hsr.getParameter("content");
		String pWriter = hsr.getParameter("writer");
		String pPasscode = hsr.getParameter("passcode");
		//debugging code
		System.out.println("title ["+pTitle+"] content ["+pContent+"] writer ["+pWriter+"] passcode ["+pPasscode+"]");
		//insert into DB table
		iBBS bbs=sqlSession.getMapper(iBBS.class);//인터페이스 호출 준비
		bbs.writebbs(pTitle, pContent, pWriter, pPasscode);//인터페이스 paramiter 전달
		return "redirect:/list";
	}
	
	/*
	 * @RequestMapping(value = "/update",method = RequestMethod.POST, produces =
	 * "application/text; charset=utf8") public String updateBBS(HttpServletRequest
	 * hsr) { int bbs_id=Integer.parseInt(hsr.getParameter("bbs_id")); String
	 * title=hsr.getParameter("title"); String content=hsr.getParameter("content");
	 * iBBS bbs=sqlSession.getMapper(iBBS.class);
	 * bbs.updatebbs(bbs_id,title,content); return "redirect:/list"; }
	 */
	
	/*
	 * @RequestMapping(value = "/delete",method = RequestMethod.GET, produces =
	 * "application/text; charset=utf8") public String deleteBBS(HttpServletRequest
	 * hsr) { int bbs_id=Integer.parseInt(hsr.getParameter("bbs_id")); iBBS
	 * bbs=sqlSession.getMapper(iBBS.class); bbs.deletebbs(bbs_id); return
	 * "redirect:/list"; }
	 */
}

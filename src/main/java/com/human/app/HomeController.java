package com.human.app;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
		if (loginUser(hsr))
			return "redirect:/list/1";
		return "login";
	}

	@RequestMapping("/logout")
	public String doLogout(HttpServletRequest hsr) {
		HttpSession session = hsr.getSession();
		session.invalidate();
		return "redirect:/list/1";
	}

	@RequestMapping("/newbie")
	public String newbie(HttpServletRequest hsr) {
		if (loginUser(hsr))
			return "redirect:/list/1";
		return "/newbie";
	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST) // ???????????? DB
	public String doSignin(HttpServletRequest hsr) {
		if (loginUser(hsr))
			return "redirect:/list/1";
		String realname = hsr.getParameter("realname");
		String userid = hsr.getParameter("userid");
		String passcode = hsr.getParameter("passcode");
		System.out.println("realname= [" + realname + "]");
		System.out.println("userid= [" + userid + "]");
		System.out.println("passcode= [" + passcode + "]");
		iMember mem = sqlSession.getMapper(iMember.class);
		mem.doSignin(realname, userid, passcode);
		return "redirect:/login";
	}

	@RequestMapping(value = "/check_user", method = RequestMethod.POST)
	public String reserve(HttpServletRequest hsr, Model model) {
		String id = hsr.getParameter("id");
		String pass = hsr.getParameter("pass");
		System.out.println("id= [" + id + "]");
		System.out.println("pass= [" + pass + "]");
		// DB?????? ???????????? : ??????????????? reserve, ????????? home??????.
		iMember member = sqlSession.getMapper(iMember.class);
		int n = member.doCheckUser(id, pass);
		if (n == 1) {
			HttpSession session = hsr.getSession();
			session.setAttribute("userid", id);
			return "redirect:/list/1";
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(value = { "/list", "/list/{pageno}" }, method = RequestMethod.GET)
	public String getList(@PathVariable("pageno") int pageno, HttpServletRequest hsr, Model model) {
		iBBS bbs = sqlSession.getMapper(iBBS.class);
		int start = 20 * (pageno - 1) + 1;
		int end = 20 * pageno;
		ArrayList<BBSrec> bbsrec = bbs.getList(start, end);
		String pDirection = "";
		if (pageno == 1) {
			pDirection = "<a href='/app/list/" + (pageno + 1) + "'>???????????????</a>";
		} else {
			pDirection = "<a href='/app/list/" + (pageno - 1) + "'>???????????????</a>" + "<a href='/app/list/" + (pageno + 1)
					+ "'>???????????????</a>";
		}
		model.addAttribute("direct", pDirection);
		HttpSession session = hsr.getSession();
		String userid = (String) session.getAttribute("userid");
		System.out.println("Userid [" + userid + "]");
		if (userid == null || userid.equals("")) {
			model.addAttribute("loggined", "0");
		} else {
			model.addAttribute("loggined", "1");
			model.addAttribute("userid", userid);
		}
		model.addAttribute("letlist", bbsrec);
		return "list";
	}

	@RequestMapping(value = "/view/{bbs_id}", method = RequestMethod.GET)
	public String selectOnBBS(@PathVariable("bbs_id") int bbs_id, HttpServletRequest hsr, Model model) {
		if (!loginUser(hsr))
			return "redirect:/list/1";
		System.out.println("bbs_id [" + bbs_id + "]");
		// ????????? ?????? ????????????
		iBBS bbs = sqlSession.getMapper(iBBS.class);
		BBSrec post = bbs.getPost(bbs_id);
		model.addAttribute("post", post);
		// ?????? ????????? ????????? ????????????
		HttpSession session = hsr.getSession();
		model.addAttribute("userid", session.getAttribute("userid"));

		// ??????????????? ??????
		String userid = (String) session.getAttribute("userid");
		System.out.println("Userid [" + userid + "]");
		if (userid == null || userid.equals("")) {
			model.addAttribute("loggined", "0");
		} else {
			model.addAttribute("loggined", "1");
			model.addAttribute("userid", userid);
		}

		iReply r = sqlSession.getMapper(iReply.class);
		ArrayList<Reply> replyList = r.getReplyList(bbs_id);
		model.addAttribute("reply_list", replyList);
		return "view";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String brandNew(HttpServletRequest hsr, Model model) {
		HttpSession s = hsr.getSession();
		String userid = (String) s.getAttribute("userid");
		if (userid == null || userid.equals("")) {
			return "redirect:/list/1";
		}
		model.addAttribute("userid", userid);
		return "new";
//		if(loginUser(hsr)) return "new";
//		return "redirect:list";
	}

//	@RequestMapping(value = "/update_view",method = RequestMethod.GET)
//	public String updateView() {
//		return "update";
//	}

	/*
	 * @RequestMapping(value = "/save",method = RequestMethod.POST)//???????????? jsp from???
	 * action public String insertBBS(HttpServletRequest hsr) { if(!loginUser(hsr))
	 * return "redirect:/list/1";
	 * 
	 * String pTitle = hsr.getParameter("title"); String pContent =
	 * hsr.getParameter("content"); String pWriter = hsr.getParameter("writer");
	 * //tring pPasscode = hsr.getParameter("passcode"); //debugging code
	 * //System.out.println("title ["+pTitle+"] content ["+pContent+"] writer ["
	 * +pWriter+"] passcode ["+pPasscode+"]"); //insert into DB table iBBS
	 * bbs=sqlSession.getMapper(iBBS.class);//??????????????? ?????? ?????? bbs.writebbs(pTitle,
	 * pContent, pWriter, pPasscode);//??????????????? paramiter ?????? return "redirect:/list/1";
	 * }
	 */

	@Resource(name = "uploadPath")
	String uploadPath;

	@RequestMapping(value = "/save", method = RequestMethod.POST) // ???????????? jsp from??? action
	public String insertBBS(HttpServletRequest hsr, MultipartFile ufile) {
		if (!loginUser(hsr))
			return "redirect:/list/1";

		 String fileName = ufile.getOriginalFilename();
	       File target = new File(uploadPath, fileName);
	       //????????????
	       if(!new File(uploadPath).exists()) {
	          new File(uploadPath).mkdirs();
	       }
	       //????????????
	       try {
	         FileCopyUtils.copy(ufile.getBytes(), target);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }

		String pTitle = hsr.getParameter("title");
		String pContent = hsr.getParameter("content");
		String pWriter = hsr.getParameter("writer");
		// tring pPasscode = hsr.getParameter("passcode");
		// debugging code
		// System.out.println("title ["+pTitle+"] content ["+pContent+"] writer
		// ["+pWriter+"] passcode ["+pPasscode+"]");
		// insert into DB table
		iBBS bbs = sqlSession.getMapper(iBBS.class);// ??????????????? ?????? ??????
		bbs.writebbs(pTitle, pContent, pWriter/* , pPasscode */);// ??????????????? paramiter ??????
		return "redirect:/list/1";
	}

	@RequestMapping(value = "/update/{bbs_id}", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	public String upviewBBS(@PathVariable("bbs_id") int bbs_id, HttpServletRequest hsr, Model model) {
		if (!loginUser(hsr))
			return "redirect:/list/1";

		iBBS bbs = sqlSession.getMapper(iBBS.class);
		BBSrec post = bbs.getPost(bbs_id);
		model.addAttribute("update", post);
		return "/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String updateBBS(HttpServletRequest hsr) {
		int uBbsid = Integer.parseInt(hsr.getParameter("bbs_id"));
		String uTitle = hsr.getParameter("title");
		String uContent = hsr.getParameter("content");
		iBBS bbs = sqlSession.getMapper(iBBS.class);
		bbs.updatebbs(uBbsid, uTitle, uContent);
		return "redirect:/list/1";
	}

	@RequestMapping(value = "/delete/{bbs_id}", method = RequestMethod.GET)
	public String deleteBBS(@PathVariable("bbs_id") int bbs_id, HttpServletRequest hsr, Model model) {
		if (!loginUser(hsr))
			return "redirect:/list/1";
		System.out.println("bbs_id [" + bbs_id + "]");
		iBBS bbs = sqlSession.getMapper(iBBS.class);
		bbs.deletebbs(bbs_id);
		return "redirect:/list/1";
	}

	public boolean loginUser(HttpServletRequest hsr) {// ???????????????
		HttpSession s = hsr.getSession();
		String userid = (String) s.getAttribute("userid");
		if (userid == null || userid.equals(""))
			return false;// ????????? ????????? ?????? ?????? ??????
		return true;

	}

	@RequestMapping(value = "/ReplyControl", method = RequestMethod.POST)
	@ResponseBody
	public String doReplyControl(HttpServletRequest hsr) {
		System.out.println("doReplyControl");
		String result = "";
		try {
			String optype = hsr.getParameter("optype");
			iReply reply = sqlSession.getMapper(iReply.class);// get mapper??? ??????!! ????????? ???????????? ??????
			if (optype.equals("add")) {
				String reply_content = hsr.getParameter("content");
				int bbs_id = Integer.parseInt(hsr.getParameter("bbs_id"));
				System.out.println("bbs_id [" + bbs_id + "]");
				HttpSession s = hsr.getSession();
				String userid = (String) s.getAttribute("userid");
				System.out.println("userid [" + userid + "]");

				reply.add(bbs_id, reply_content, userid);
			} else if (optype.equals("delete")) {// ?????? ??????
				int reply_id = Integer.parseInt(hsr.getParameter("reply_id"));
				reply.delete(reply_id);
			} else if (optype.equals("update")) {// ?????? ??????
				String content = hsr.getParameter("content");
				int reply_id = Integer.parseInt(hsr.getParameter("reply_id"));
				reply.update(content, reply_id);
			}
			result = "ok";
		} catch (Exception e) {
			result = "fail";
		} finally {
			return "result";
		}
	}

}

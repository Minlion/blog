package net.limingliang.home.controller;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.fabric.xmlrpc.base.Param;

import net.limingliang.home.service.MainService;
import net.limingliang.ioc.comment.dto.Comment;
import net.limingliang.ioc.comment.service.CommentService;
import net.limingliang.ioc.post.dto.Post;
import net.limingliang.ioc.post.service.PostService;
import net.limingliang.ioc.user.dto.User;
import net.limingliang.ioc.user.service.UserService;
import net.limingliang.system.SystemConfig;
import net.limingliang.utils.HomeDir;
import net.limingliang.utils.HtmlEncoder;


@Controller
public class HomeController{
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger=Logger.getLogger(HomeController.class);
	private static SystemConfig sysConfig=new SystemConfig();

	@Resource
	private MainService mainService;
	@Resource
	private CommentService commentService;
	@Resource
	private PostService postService;
	@Resource
	private UserService userService;
	
	/**
	 * 首页
	 * @param request
	 * @param response
	 * @param model
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/all")
	public String main(HttpServletRequest request,HttpServletResponse response,Model model,Param param){
		try {
			
			String search=request.getParameter("s");
			String currentpage=request.getParameter("currentpage");
			if(StringUtils.isBlank(currentpage)){
				currentpage="1";
			}
			int totalCount=postService.getPostCount(search);
			List<Post> list=postService.getPostByPage(search,Integer.parseInt(currentpage),totalCount);
			//页面标题
			model.addAttribute("optionsMap",sysConfig.getOptionsMap());
			model.addAttribute("title",HtmlEncoder.titleEncode(currentpage,search,totalCount));
			//页面header + 文章列表  + 页脚
			model.addAttribute("getHeader", HtmlEncoder.headerEncode(request)+HtmlEncoder.postListEncode(request,list,Integer.parseInt(currentpage),totalCount)+HtmlEncoder.themeEncode());
			return "home/index.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("进入博客首页失败：",e);
			return "404.jsp";
		}
	}
	
	/**
	 * 文章明细页面
	 * @param request
	 * @param response
	 * @param year 年（1900-2099）正则：19[\d][\d]|20[\d][\d]
	 * @param month 月正则：0[1-9]|1[0-2]
	 * @param day 日正则：[0-2][\\d]|3[0-1]
	 * @param name 文章标题正则：.*
	 */
	@RequestMapping(value="/{year:19[\\d][\\d]|20[\\d][\\d]}/{month:0[1-9]|1[0-2]}/{day:[0-2][\\d]|3[0-1]}/{name:.*}")
	public String post(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("year") String year,@PathVariable("month") String month,
			@PathVariable("day") String day,@PathVariable("name") String name,Model model){
		try {
			Post post=postService.getPostByName(year+"-"+month+"-"+day,URLEncoder.encode(name, "utf-8"));
			User user=userService.selectByPrimaryKey(post.getPostAuthor());
			List<Comment> comments=commentService.getCommentsByPostId(post.getId());
			//页面标题
			model.addAttribute("optionsMap",sysConfig.getOptionsMap());
			model.addAttribute("title",post.getPostTitle()+" - "+"明亮");
			//页面header + 文章明细 + 页脚
			model.addAttribute("getHeader", HtmlEncoder.headerEncode(request)+HtmlEncoder.postEncode(post,user,comments,request)+HtmlEncoder.themeEncode());
			return "home/index.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("进入文章页面失败：",e);
			return "404.jsp";
		}
	}
	/**
	 * 底部留言板，单纯的String字符串返回会有乱码问题，使用PrintWriter返回信息。
	 * @param request
	 * @param response
	 */
	@RequestMapping(value ="/getFooterComment")
	@ResponseBody
	public void getFooterComment(HttpServletRequest request, HttpServletResponse response){
		try {
			List<Comment> comments=commentService.getFooterComments(request);
			String footer=HtmlEncoder.footerCommentEncode(comments);
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("pragma", "no-cache");
			response.setHeader("cache-control", "no-cache");
			PrintWriter out=response.getWriter();
			out.write(footer);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("获取评论列表失败：",e);
		}
	}
	/**
	 * 搜索文章
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/search")
	@ResponseBody
	public Map<String,String> getPostBySearch(HttpServletRequest request, HttpServletResponse response){
		try {
			String currentpage=request.getParameter("currentpage");
			if(StringUtils.isBlank(currentpage)){
				currentpage="1";
			}
			String search=request.getParameter("s");
			if(StringUtils.isBlank(currentpage)){
				currentpage="1";
			}
			int totalCount=postService.getPostCount(search);
			List<Post> list=postService.getPostByPage(search,Integer.parseInt(currentpage),totalCount);
			String postHtml=HtmlEncoder.postListEncode(request,list,Integer.parseInt(currentpage),totalCount);
			Map<String,String> m=new HashMap<String,String>();
			m.put("postHtml", postHtml);
			m.put("homeUrl", HomeDir.getHomeUrl(request)+"/search");
			m.put("title",HtmlEncoder.titleEncode(currentpage,search,totalCount));
			m.put("href", HomeDir.getHomeUrl(request)+"/all?s="+search);
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("文章搜索失败！",e);
			return null;
		}		
	}
	/**
	 * 根据id获取文章
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getPost")
	@ResponseBody
	public Map<String,String> getPost(HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String,String> m=new HashMap<String,String>();
			String postId=request.getParameter("id");
			Post post=postService.selectByPrimaryKey(Long.valueOf(postId));
			User user=userService.selectByPrimaryKey(post.getPostAuthor());
			List<Comment> comments=commentService.getCommentsByPostId(post.getId());
			String postHtml=HtmlEncoder.postEncode(post,user,comments,request);
			m.put("postHtml", postHtml);
			m.put("homeUrl", HomeDir.getHomeUrl(request)+"/search");
			m.put("title",post.getPostTitle()+" - "+"明亮");
			m.put("href", HomeDir.getPostUrl(request, post));
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("获取文章失败：",e);
			return null;
		}
	}
	/**
	 * 文章明细的背景图片
	 * @return
	 */
	//TODO 等文件上传功能做好后改为读取上传的文件列表里的图片
	@RequestMapping(value="/getBg")
	@ResponseBody
	public void getBg(HttpServletRequest request, HttpServletResponse response){
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("pragma", "no-cache");
			response.setHeader("cache-control", "no-cache");
			PrintWriter out=response.getWriter();
			out.write(HomeDir.getHomeUrl(request)+"/images/header-bg.jpg");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("获取文章失败：",e);
		}

	}
	
	
	public static void main(String[] args) {
		Pattern pattern=Pattern.compile("[1-9]|[1-2][\\d]|3[0-1]");
		for(int i=0;i<=33;i++){
			Matcher match=pattern.matcher(String.valueOf(i));
			System.out.print(match.matches());
		}
	}

}

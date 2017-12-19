package net.limingliang.ioc.comment.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import net.limingliang.ioc.comment.dto.Comment;
import net.limingliang.ioc.comment.service.CommentService;
import net.limingliang.utils.HtmlEncoder;

@Controller
@RequestMapping("comment")
public class CommentController {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger=Logger.getLogger(CommentController.class);

	@Resource
	private CommentService commentService;
	
	
	/**
	 * 新增、编辑评论
	 * @param request
	 * @param response
	 */
	@RequestMapping("/editComment")
	@ResponseBody
	public Map<String,Object> editComment(HttpServletRequest request,HttpServletResponse response,Comment comment){
		try {
			return commentService.editComment(request,response,comment);
		} catch (Exception e) {
			logger.debug("评论失败了：", e);
			return new HashMap<String,Object>(){
				private static final long serialVersionUID = 1L;
			{
					put("success", false);
					put("errorMsg", "评论出现了一些问题，刷新一下试试…");	
			}};
		}
		
	}
	/**
	 * 文章评论者明细：第一次在本博客的留言和留言时间
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCommentDetail")
	@ResponseBody
	public void getCommentDetail(HttpServletRequest request,HttpServletResponse response){
		try {
			String commentId=request.getParameter("id");
			Comment c=new Comment();
			if(StringUtils.isNotBlank(commentId)){
				c=commentService.getPostCommentDetail(request,Long.valueOf(commentId));
			}
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("pragma", "no-cache");
			response.setHeader("cache-control", "no-cache");
			PrintWriter out=response.getWriter();
			out.write(HtmlEncoder.postCommentEncode(c));
			out.flush();
		} catch (Exception e) {
			logger.debug("获取评论明细失败：", e);
		}

	}
	

}

package net.limingliang.ioc.comment.service.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.limingliang.ioc.comment.dao.CommentDao;
import net.limingliang.ioc.comment.dto.Comment;
import net.limingliang.ioc.comment.service.CommentService;
import net.limingliang.ioc.post.dto.Post;
import net.limingliang.utils.Constants;
import net.limingliang.utils.CookieUtil;
import net.limingliang.utils.HomeDir;
import net.limingliang.utils.HtmlEncoder;
import net.limingliang.utils.ImageUtil;
import net.limingliang.utils.NetworkUtil;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
	
	@Resource
	private CommentDao commentsDao;

	/**
	 *首页评论列表
	 */
	@Override
	public List<Comment> getFooterComments(HttpServletRequest request) throws Exception{
		List<Comment> comments=commentsDao.getFooterComments();
		for(Comment comment:comments){
			Post p=new Post();
			p.setPostDate(comment.getPostDate());
			p.setPostName(comment.getPostName());
			comment.setPostUrl(HomeDir.getPostUrl(request, p));
			comment.setCommentHeaderUrl(ImageUtil.getHeaderUrl(comment.getCommentAuthorEmail()));;
		}
		return comments;
	}
	/**
	 * 获取文章评论列表
	 */
	@Override
	public List<Comment> getCommentsByPostId(Long id) throws Exception{
		List<Comment> comments=commentsDao.getCommentsByPostId(id);
		for(Comment comment:comments){
			comment.setCommentHeaderUrl(ImageUtil.getHeaderUrl(comment.getCommentAuthorEmail()));
		}
		return comments;
		
	}
	/**
	 * 文章评论者明细：第一次在本博客的留言和留言时间
	 */
	@Override
	public Comment getPostCommentDetail(HttpServletRequest request,Long commentId) throws Exception{
		Comment c=commentsDao.getPostCommentDetail(commentId);
		c.setCommentHeaderUrl(ImageUtil.getHeaderUrl(c.getCommentAuthorEmail()));
		Post p=new Post();
		p.setPostDate(c.getPostDate());
		p.setPostName(c.getPostName());
		c.setPostUrl(HomeDir.getPostUrl(request, p));
		return c;
	}
	/**
	 * 新增评论
	 */
	@Override
	public int insert(Comment comment) throws Exception {
		int i=commentsDao.insert(comment);
		comment.setCommentHeaderUrl(ImageUtil.getHeaderUrl(comment.getCommentAuthorEmail()));
		return i;
	}
	/**
	 * 根据评论的ID获取该评论
	 */
	@Override
	public Comment selectByPrimaryKey(Long id) throws Exception{
		 Comment comment=commentsDao.selectByPrimaryKey(id);
		 comment.setCommentHeaderUrl(ImageUtil.getHeaderUrl(comment.getCommentAuthorEmail()));
		 return comment;
	}
	/**
	 * 根据评论id更新评论内容
	 */
	@Override
	public int updateByPrimaryKey(Comment c) {
		return commentsDao.updateByPrimaryKey(c);
	}
	/**
	 * 检查评论内容是否重复
	 */
	@Override
	public int checkCommentExist(Comment c) {
		return commentsDao.checkCommentExist(c);
	}
	/**
	 * 检查评论间隔时间是否过短
	 */
	@Override
	public Date getDateByEmail(String email) {
		return commentsDao.getDateByEmail(email);
	}
	/**
	 * 新增编辑评论
	 */
	@Override
	public Map<String, Object> editComment(HttpServletRequest request,HttpServletResponse response,Comment comment) throws Exception {
		Map<String,Object> m=new HashMap<String,Object>();
		//TODO 判断是否是正确的邮件地址
		//判断名字是否为空
		if(StringUtils.isBlank(comment.getCommentAuthor())){
			m.put("success", false);
			m.put("errorMsg", "名字不能为空。");
			return m;
		}
		//判断评论内容是否为空
		if(StringUtils.isBlank(comment.getCommentContent())){
			m.put("success", false);
			m.put("errorMsg", "评论内容不能为空。");
			return m;
		}
		//判断留言速度
		Date date=getDateByEmail(comment.getCommentAuthorEmail());
		if(date!=null && (new Date().getTime()-date.getTime())<Constants.COMMENTINTERVAL){
			m.put("success", false);
			m.put("errorMsg", "留言太快了，稍微休息一下。");
			return m;
		}
		//如果id为空新增评论，如果不为空为编辑评论
		if(null==comment.getCommentId()){
			return insertComment(request,response,comment);
		}
		return updateComment(request, response, comment);
	}

	/**
	 * 新增评论
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertComment(HttpServletRequest request,HttpServletResponse response,Comment comment) throws Exception{
		Map<String,Object> m=new HashMap<String,Object>();

		//判断评论内容是否重复
		if(checkCommentExist(comment)>0){
			m.put("success", false);
			m.put("errorMsg", "您似乎已经在本文中提交过这条评论了！");
			return m;
		}
		comment.setCommentAuthorIp(NetworkUtil.getIpAddress(request));
		//TODO 评论者的信息明细，以及评论者的网站地址前增加http://
		comment.setCommentKarma(0);
		comment.setUserId((long)0);
		comment.setCommentType("");
		comment.setCommentApproved("1");
		comment.setCommentDate(new Date());
		comment.setCommentDateGmt(new Date());
		comment.setCommentMailNotify((byte)0);
		comment.setCommentAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) Appl0eWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		if(insert(comment)<1){
			m.put("success", false);
			m.put("errorMsg", "在新增评论的时候出了一些问题，刷新一下试试……");
			return m;
		};
		addCookie(response,comment);
		m.put("success", true);
		m.put("successMsg", HtmlEncoder.newCommentEncode(comment));
		return m;
	}
	
	/**
	 * 编辑评论
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateComment(HttpServletRequest request,HttpServletResponse response,Comment c) throws Exception{
		//根据ID判断评论内容是否重复
		Map<String,Object> m=new HashMap<String,Object>();
		Comment oldComment=selectByPrimaryKey(c.getCommentId());
		if(oldComment.getCommentContent().equals(c.getCommentContent())){
			m.put("success", false);
			m.put("errorMsg", "您似乎已经在本文中提交过这条评论了！");
			return m;
		}
		c.setCommentId(oldComment.getCommentId());
		c.setCommentDate(oldComment.getCommentDate());
		c.setCommentDateGmt(oldComment.getCommentDateGmt());
		c.setCommentAuthorIp(NetworkUtil.getIpAddress(request));
		//TODO 评论者的信息明细，以及评论者的网站地址前增加http://
		c.setCommentKarma(0);
		c.setUserId((long)0);
		c.setCommentType("");
		c.setCommentApproved("1");
		c.setCommentMailNotify((byte)0);
		c.setCommentAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) Appl0eWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		if(updateByPrimaryKey(c)<1){
			m.put("success", false);
			m.put("errorMsg", "在编辑评论的时候出了一些问题，刷新一下试试……");
			return m;
		};
		addCookie(response,c);
		m.put("success", true);
		m.put("successMsg", HtmlEncoder.newCommentEncode(c));
		return m;
	}
	/**
	 * 新增、编辑评论后保存评论者信息在cookie中
	 * @param c
	 * @throws Exception 
	 */
	public void addCookie(HttpServletResponse response,Comment c) throws Exception{
		CookieUtil.addCookie(response, "email", URLEncoder.encode(c.getCommentAuthorEmail(), "utf-8"), Constants.COMMENTCOOKIEAGE);
		CookieUtil.addCookie(response, "url", URLEncoder.encode(c.getCommentAuthorUrl(),"utf-8"), Constants.COMMENTCOOKIEAGE);
		CookieUtil.addCookie(response, "author", URLEncoder.encode(c.getCommentAuthor(),"utf-8"), Constants.COMMENTCOOKIEAGE);
	}
	

}

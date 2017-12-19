package net.limingliang.ioc.comment.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.limingliang.ioc.comment.dto.Comment;


public interface CommentService {

	List<Comment> getFooterComments(HttpServletRequest request) throws Exception;

	List<Comment> getCommentsByPostId(Long id) throws Exception;
	
	int insert(Comment comment) throws Exception;

	Comment getPostCommentDetail(HttpServletRequest request,Long valueOf) throws Exception;

	Comment selectByPrimaryKey(Long id) throws Exception;

	int updateByPrimaryKey(Comment c) throws Exception;

	int checkCommentExist(Comment c) throws Exception;

	Date getDateByEmail(String email) throws Exception;

	Map<String, Object> editComment(HttpServletRequest request,HttpServletResponse response,Comment comment)throws Exception;

}

package net.limingliang.ioc.comment.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import net.limingliang.ioc.comment.dto.Comment;


public interface CommentDao {
	
    int deleteByPrimaryKey(Long commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);

	List<Comment> getFooterComments();

	List<Comment> getCommentsByPostId(@Param(value="id")Long id);
	
	Comment getPostCommentDetail(@Param(value="id")Long id);

	int checkCommentExist(Comment record);

	Date getDateByEmail(@Param(value="email")String email);
}
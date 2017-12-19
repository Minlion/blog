package net.limingliang.utils;

import java.util.ArrayList;
import java.util.List;

import net.limingliang.ioc.comment.dto.Comment;

/**
 * 子父评论
 * @author Sunny
 *
 */
public class TreeCommentUtil {

	/**
	 * 获取父类评论列表
	 * @param commentList
	 * @return
	 */
	public final static List<Comment> getFatherComment(List<Comment> commentList) {
		List<Comment> newCommentList = new ArrayList<Comment>();
		for (Comment comment : commentList) {
			if (comment.getCommentId()<0)
				continue;
			if (comment.getCommentParent()==0) {
				// 获取父节点下的子节点
				comment.setCommentChildren(getChildrenComment(comment.getCommentId(), commentList));
//				jsonTreeData.setExpanded(true); // 根节点
				newCommentList.add(comment);
			}
		}
		return newCommentList;
	}

	/**
	 * 获取子类评论列表
	 * @param commentId
	 * @param commentList
	 * @return
	 */
	private final static List<Comment> getChildrenComment(long commentId, List<Comment> commentList) {
		List<Comment> newCommentList = new ArrayList<Comment>();
		for (Comment comment : commentList) {
			if (comment.getCommentId() == null)
				continue;
			// 这是一个子节点
			if (commentId==comment.getCommentParent()) {
				// 递归获取子节点下的子节点
				comment.setCommentChildren(getChildrenComment(comment.getCommentId(),commentList));
				newCommentList.add(comment);
			}
		}
		return newCommentList;
	}

}
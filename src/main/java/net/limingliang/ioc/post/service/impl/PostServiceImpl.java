package net.limingliang.ioc.post.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.limingliang.ioc.post.dao.PostDao;
import net.limingliang.ioc.post.dto.Post;
import net.limingliang.ioc.post.service.PostService;
import net.limingliang.utils.Page;

@Service("postService")
public class PostServiceImpl implements PostService {
	
	@Resource
	private PostDao postDao;

	/**
	 * 根据文章名称获取文章明细
	 */
	@Override
	public Post getPostByName(String date, String name) {
		return postDao.getPostByName(date,name);
	}
	/**
	 * 根据文章ID获取文章明细
	 */
	@Override
	public Post selectByPrimaryKey(Long postId) {
		return postDao.selectByPrimaryKey(postId);
	}
	/**
	 * 获取搜索到的文章数量
	 */
	@Override
	public int getPostCount(String search) {
		return (int) postDao.getPostCount(search);
	}
	
	/**
	 * 分页获取文章列表
	 */
	@Override
	public List<Post> getPostByPage(String search, int currentPage, int totalCount) {
	    Page page = new Page(totalCount, currentPage);
	    page.setPageSize(9);
	    return postDao.getPostByPage(page.getStartPos(), page.getPageSize(), search);
	}
}

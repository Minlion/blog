package net.limingliang.ioc.post.service;

import java.util.List;

import net.limingliang.ioc.post.dto.Post;

public interface PostService {

	Post getPostByName(String date, String name);

	Post selectByPrimaryKey(Long postId);

	int getPostCount(String search) throws Exception;

	List<Post> getPostByPage(String search, int currentPage, int totalCount) throws Exception;

}

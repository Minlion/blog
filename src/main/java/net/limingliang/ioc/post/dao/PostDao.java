package net.limingliang.ioc.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.limingliang.ioc.post.dto.Post;

public interface PostDao {
    int deleteByPrimaryKey(Long id);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);
    
	List<Post> getPostByPage(@Param(value="startPos") Integer startPos,@Param(value="pageSize") Integer pageSize,@Param(value="search") String search);
	
	long getPostCount(@Param(value="search") String search);

	Post getPostByName(@Param(value="date") String date,@Param(value="name") String name);
}
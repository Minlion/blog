package net.limingliang.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import net.limingliang.ioc.post.dto.Post;

/**
 * 获取项目路径
 * @author Sunny
 *
 */
@SuppressWarnings("rawtypes")
public class HomeDir {

	/**
	 * 示例：/D:/SVN/git/blog/blog/target/classes/
	 * @param c
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getSysHomeDir(Class c) throws UnsupportedEncodingException{
		return URLDecoder.decode(c.getResource("/").getPath(), "UTF-8");
	}
	/**
	 * 示例：D:/SVN/git/blog/blog/target/
	 * @param c
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getHomeDir(Class c) throws UnsupportedEncodingException{
		return getSysHomeDir(c).substring(1, getSysHomeDir(c).lastIndexOf("classes"));
	}
	
	/**
	 * 博客访问地址
	 * 示例：http://localhost:8090/blog
	 * @param request
	 * @return
	 */
	public static String getHomeUrl(HttpServletRequest request) {
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
	
	/**
	 * 文章访问地址
	 * 示例：http://localhost:8090/blog/2017/01/01/hello world
	 * @param request
	 * @param date
	 * @return
	 */
	public static String getPostUrl(HttpServletRequest request,Post post){
		Date date=post.getPostDate();
		return HomeDir.getHomeUrl(request)+"/"+DateUtil.getYear(date)+"/"+DateUtil.getMonth(date)+"/"+DateUtil.getDay(date)+"/"+post.getPostName();
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(getHomeDir(HomeDir.class));
	}
}

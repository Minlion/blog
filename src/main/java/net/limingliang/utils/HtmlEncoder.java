package net.limingliang.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import net.limingliang.ioc.comment.dto.Comment;
import net.limingliang.ioc.post.dto.Post;
import net.limingliang.ioc.user.dto.User;
import net.limingliang.system.SystemConfig;

/**
 * 博客前端页面格式化
 * @author Sunny
 *
 */
public class HtmlEncoder {
	
	private static SystemConfig sys=new SystemConfig();
	
	
	/**
	 * 博客标题
	 * @param currentpage
	 * @param search
	 * @param totalCount
	 * @return
	 */
	public static String titleEncode(String currentpage, String search, int totalCount)  throws Exception {
		Map<String,String> optionsMap=sys.getOptionsMap();
		String blogName= optionsMap.get("blogname")==null?"你好":optionsMap.get("blogname");
		String blogdescription=optionsMap.get("blogdescription")==null?"这是一个SSM框架的博客":optionsMap.get("blogdescription");
		if(StringUtils.isEmpty(search)){
			return blogName+" - "+blogdescription;
		}else{
			if(Integer.parseInt(currentpage)>1){
				return currentpage+" - "+blogName;
			}else{
				return search+" - "+totalCount+"个搜索结果";
			}
		}
	}
	/**
	 * 博客头部
	 * @param currentpage
	 * @param search
	 * @param totalCount
	 * @return
	 */
	public static String headerEncode(HttpServletRequest request)  throws Exception {
		Map<String,String> optionsMap=sys.getOptionsMap();
		StringBuffer buffer=new StringBuffer();
		//TODO search路径放在数据库中设置
		String homeUrl=HomeDir.getHomeUrl(request)+"/search";
		//TOTO 背景图片
		buffer.append("<header id='main-header' "
				+ "style='background-image:url("+HomeDir.getHomeUrl(request)+"/home/images/header-bg.jpg);background-position:center center;background-size:cover;'>");
		buffer.append("	<div id='header-wrap'>                                                                               ");
		buffer.append("		<div id='logo'>                                                                                  ");
		buffer.append("			<h1>                                                                                         ");
		buffer.append("				<a href='"+optionsMap.get("home")+"' title='"+optionsMap.get("blogname")+"'>             ");
		buffer.append(					optionsMap.get("blogname")                                                            );
		buffer.append("				</a>                                                                                     ");
		buffer.append("			</h1>                                                                                        ");
		buffer.append("			<h2>                                                                                         ");
		buffer.append(				optionsMap.get("blogdescription")                                                         );
		buffer.append("			</h2>                                                                                        ");
		buffer.append("			<div class='logo-img'>                                                                       ");
		buffer.append("				<img class='avatar' src='home/images/header-96x96.png' title='"+optionsMap.get("blogname")+"'>");
		buffer.append("			</div>                                                                                       ");
		buffer.append("			<div id='logo-music'>                                                                        ");
		buffer.append("				<div id='logo-music-name'>                                                               ");
		buffer.append("				</div>                                                                                   ");
		buffer.append("				<div id='logo-music-prev'>                                                               ");
		buffer.append("				</div>                                                                                   ");
		buffer.append("				<div id='logo-music-play'>                                                               ");
		buffer.append("				</div>                                                                                   ");
		buffer.append("				<div id='logo-music-pause'>                                                              ");
		buffer.append("				</div>                                                                                   ");
		buffer.append("				<div id='logo-music-next'>                                                               ");
		buffer.append("				</div>                                                                                   ");
		buffer.append("				<div class='loading'>                                                                    ");
		buffer.append("					<div class='loading-bar'>                                                            ");
		buffer.append("						<div class='bar1'>                                                               ");
		buffer.append("						</div>                                                                           ");
		buffer.append("						<div class='bar2'>                                                               ");
		buffer.append("						</div>                                                                           ");
		buffer.append("						<div class='bar3'>                                                               ");
		buffer.append("						</div>                                                                           ");
		buffer.append("						<div class='bar4'>                                                               ");
		buffer.append("						</div>                                                                           ");
		buffer.append("					</div>                                                                               ");
		buffer.append("				</div>                                                                                   ");
		buffer.append("			</div>                                                                                       ");
		buffer.append("			<div id='logo_jplayer' class='jp-jplayer'>                                                   ");
		buffer.append("			</div>                                                                                       ");
		buffer.append("		</div>                                                                                           ");
		buffer.append("		<button id='openlist' class='open hide'>                                                         ");
		buffer.append("			<span>                                                                                       ");
		buffer.append("			</span>                                                                                      ");
		buffer.append("			<span>                                                                                       ");
		buffer.append("			</span>                                                                                      ");
		buffer.append("			<span>                                                                                       ");
		buffer.append("			</span>                                                                                      ");
		buffer.append("			playlist                                                                                     ");
		buffer.append("		</button>                                                                                        ");
		buffer.append("		<button id='openmenu' class='open'>                                                              ");
		buffer.append("			<span>                                                                                       ");
		buffer.append("			</span>                                                                                      ");
		buffer.append("			<span>                                                                                       ");
		buffer.append("			</span>                                                                                      ");
		buffer.append("			<span>                                                                                       ");
		buffer.append("			</span>                                                                                      ");
		buffer.append("			menu                                                                                         ");
		buffer.append("		</button>                                                                                        ");
		//TODO 菜单栏，单独分出来
		buffer.append("		<nav id='main-nav'>                                                                              ");
		buffer.append("			<div class='nav-menu'>                                                                       ");
		buffer.append("				<ul>                                                                                     ");
		buffer.append("					<li class='page_item page-item-2'>                                                   ");
		buffer.append("						<a href='http://localhost/wordpress/sample-page/'>                               ");
		buffer.append("							示例页面                                                                                                     ");
		buffer.append("						</a>                                                                             ");
		buffer.append("					</li>                                                                                ");
		buffer.append("				</ul>                                                                                    ");
		buffer.append("			</div>                                                                                       ");
		buffer.append("		</nav>                                                                                           ");
		buffer.append("		<form role='search' method='get' id='search-form' action='"+homeUrl+"'>                          ");
		buffer.append("			<div>                                                                                        ");
		buffer.append("<input type='text' placeholder='搜索' name='s' id='s' "
				+ "onblur=\"if ( this.value == '' ){this.placeholder='搜索';}\" "
				+ "onfocus=\"if ( this.placeholder == '搜索' ){this.value = '';}\">"											 );
		buffer.append("			</div>                                                                                       ");
		buffer.append("		</form>                                                                                          ");
		buffer.append("		<div class='clear'>                                                                              ");
		buffer.append("		</div>                                                                                           ");
		buffer.append("	</div>                                                                                               ");
		buffer.append("</header>                                                                                             ");
		buffer.append("<!--header-->                                                                                         ");
		buffer.append("<div id='main' class='main-full'>                                                      				 ");
		return buffer.toString();
	}
	
	/**
	 * 文章列表
	 * @param articles
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String postListEncode(HttpServletRequest request,List<Post> posts,int currentPage,int totalCount) throws Exception {
		StringBuffer buffer=new StringBuffer();
		buffer.append("	<div id='content' class='post-index'>                                                 ");
		for(Post post:posts){
			buffer.append("		<article id='post-"+post.getId()+"'>                                                            ");
			buffer.append("			<div class='article-wrap'>                                                        			");
			buffer.append("				<header class='entry-header'>                                                   		");
			buffer.append("					<h1>                                                                          		");
			buffer.append("						<a href='"+HomeDir.getPostUrl(request, post)+"' rel='bookmark' class='post-title'");
			buffer.append("						title='"+post.getPostTitle()+"' id='"+post.getId()+"'>                          ");
			buffer.append(							post.getPostTitle()                                                          );
			buffer.append("						</a>                                                                        	");
			buffer.append("						<span class='meta-time'>                                                    	");
			buffer.append(							new DateTime(post.getPostDate()).toString("yy年MM月dd日")                    );
			buffer.append("						</span>                                                                     	");
			buffer.append("					</h1>                                                                        		");
			buffer.append("				</header>                                                                       		");
			buffer.append("				<div class='entry-content'>                                                     		");
			buffer.append("					<p>                                                                           		");
			buffer.append("						<a href='"+HomeDir.getPostUrl(request, post)+"' class='post-title has-img-a' title='"+post.getPostTitle()+"' rel='bookmark' id='"+post.getId()+"'>");
			buffer.append(							ImageUtil.getFirstImageUrl(post.getPostContent())                          	 );
			buffer.append("						</a>                                                                        	");
			buffer.append("					</p>                                                                          		");
			buffer.append(				getPostListContent(post.getPostContent())																	 );
			buffer.append("				</div>                                                                          		");
			buffer.append("			</div>                                                                            			");
			buffer.append("		</article>                                                                          			");
		}
		buffer.append("	</div>                                                                               					");
		buffer.append(pageNumEncode(currentPage,totalCount));
		return buffer.toString();
	}
	/**
	 * 首页文章列表的文章内容概略
	 * @param post
	 * @return
	 */
	public static String getPostListContent(String content){
		StringBuffer buffer=new StringBuffer();
		if(StringUtils.isBlank(content)){
			buffer.append("					<p>                                                                           		");
			buffer.append(						content                                       					 				 );
			buffer.append("					</p>                                                                          		");
			return buffer.toString();
		}
		//如果文章有音乐标签，取第一个音乐标签
		Pattern pattern=Pattern.compile("(?=\\[mp3 auto=[0-1]\\])(.+?)(?<=\\[\\/mp3\\])");
		Matcher m=pattern.matcher(content);
		while(m.find()){
			if("[mp3 auto=0][/mp3]".equals(m.group()) || "[mp3 auto=1][/mp3]".equals(m.group())){
				continue;
			}
			buffer.append("					<p>                                                                           		");
			buffer.append(						getMp3Label(m.group(),"list")                                       			 );
			buffer.append("					</p>                                                                          		");
			return buffer.toString();
		}
		//如果文章有图片标签
		pattern=Pattern.compile("(?=\\<img class=)(.+?)(?<=\\/>)");
		m=pattern.matcher(content);
		while(m.find()){
			content=content.replace(m.group(), "");
		}
		
		//如果文章长度过长
		if(content.length()>86){
			buffer.append("					<p>                                                                           		");
			buffer.append(						content.substring(0, 86)+"…"                                       				 );
			buffer.append("					</p>                                                                          		");
			return buffer.toString();
		}
		buffer.append("					<p>                                                                           		");
		buffer.append(						content						                                       				 );
		buffer.append("					</p>                                                                          		");
		return buffer.toString();
	}
	
	/**
	 * 博客备案号
	 * @return
	 * @throws Exception
	 */
	public static String themeEncode()  throws Exception {
		Map<String,String> optionsMap=sys.getOptionsMap();
		StringBuffer buffer=new StringBuffer();
		buffer.append("</div>                                           ");
		buffer.append("<!--main-->                                      ");
		buffer.append("<footer id='main-footer'>                     	");
		buffer.append("	<div id='footer-copy'>                       	");
		buffer.append("		©2017&nbsp;&nbsp;                          	");
		buffer.append("		<a href='' target='_blank'>                	");
		buffer.append(			optionsMap.get("blogname")             	 );
		buffer.append("		</a>                                       	");
		buffer.append("		&nbsp;&nbsp;|&nbsp;&nbsp;豫ICP备17048687号 	");
		buffer.append("	</div>                                       	");
		buffer.append("	<div class='clear'>                          	");
		buffer.append("	</div>                                       	");
		buffer.append("</footer>                                     	");
		buffer.append("<!--footer-->                                 	");
		return buffer.toString();
	}
	
	/**
	 * 文章明细
	 * @param request
	 * @param post
	 * @return
	 */
	public static String postEncode(Post post,User user,List<Comment> comments,HttpServletRequest request) throws Exception{
		String postUrl=HomeDir.getPostUrl(request, post);
		StringBuffer buffer=new StringBuffer();
		buffer.append("<div id='content' class='post-single'>                                                                                                 ");
		buffer.append("	<div id='singular-content'>                                                                                                           ");
		buffer.append("		<article id='post-"+post.getId()+"'>                                                                                              ");
		buffer.append("			<header class='entry-header'>                                                                                                 ");
		buffer.append("				<h1>                                                                                                                      ");
		buffer.append(					post.getPostTitle()                                                                                                    );
		buffer.append("				</h1>                                                                                                                     ");
		buffer.append("			</header>                                                                                                                     ");
		buffer.append("			<div class='entry-content'>                                                                                                   ");
		buffer.append("				<p align='center'>                                                                                                        ");
		buffer.append("				</p>                                                                                                                      ");
		buffer.append("				<p>                                                                                                                       ");
		buffer.append(					getPostContentDetail(post.getPostContent())                                                                            );
		buffer.append("				</p>                                                                                                                      ");
		buffer.append("			</div>                                                                                                                        ");
		//分享按钮
		buffer.append(			HtmlEncoder.shareEncode(post,user,postUrl)																						   );
		buffer.append("			<div class='clear'>                                                                                                           ");
		buffer.append("			</div>                                                                                                                        ");
		buffer.append("		</article>                                                                                                                        ");
		buffer.append("		<!--post-->                                                                                                                       ");
		//回复表单
		buffer.append(			HtmlEncoder.commentEncode(post,comments,request)																									   );
		buffer.append("	</div>                                                                                                                                ");
		buffer.append("</div>                                                                                                                                 ");
		buffer.append("<!--content-->                                                                                                                         ");
		
		return buffer.toString();
	}
	/**
	 * 文章明细的文章内容
	 * @param post
	 * @return
	 */
	public static String getPostContentDetail(String content){
		//如果文章有音乐标签
		Pattern pattern=Pattern.compile("(?=\\[mp3 auto=[0-1]\\])(.+?)(?<=\\[\\/mp3\\])");
		Matcher m=pattern.matcher(content);
		while(m.find()){
			if("[mp3 auto=0][/mp3]".equals(m.group()) || "[mp3 auto=1][/mp3]".equals(m.group())){
				continue;
			}
			content=content.replace(m.group(), getMp3Label(m.group(),"detail"));
		}
		//TODO 待修改，修改为和后台文章编辑器一致
		if(StringUtils.isNotBlank(content)){
			String[] ary=content.split("\r\n\r\n");
			content="";
			for(String s:ary){
				content+="<p>"+s+"</p>";
			}
		}
		return content;
	}
	/**
	 * 音乐标签
	 * 零宽断言规则：(?=exp)匹配exp前面的位置       (?<=exp)匹配exp后面的位置	(?!exp)	匹配后面跟的不是exp的位置	(?<!exp)	匹配前面不是exp的位置
	 * @param rel
	 * @return
	 */
	public static String getMp3Label(String rel,String isList){
		Pattern pattern =Pattern.compile("(?<=\\[mp3 auto=[0-1]\\])(.+?)(?=\\[\\/mp3\\])");
		Matcher m=pattern.matcher(rel);
		while(m.find()){
			StringBuffer buffer=new StringBuffer();
			buffer.append("				<div id='jp_container' class='jp-audio'>                                                      ");
			buffer.append("					<span rel='"+m.group()+"'    															  ");
			buffer.append("					class='play-switch play' title='play'>                                                    ");
			buffer.append("					</span>                                                                                   ");
			buffer.append("					<span class='play-switch stop' title='stop'>                                              ");
			buffer.append("					</span>                                                                                   ");
			buffer.append("					<span rel='"+(char)("list".equals(isList)?0:rel.charAt(10))+"' class='auto'>              ");
			buffer.append("					</span>                                                                                   ");
			buffer.append("					<div class='length-bar'>                                                                  ");
			buffer.append("						<div class='seek-bar'>                                                                ");
			buffer.append("							<div class='play-bar'>                                                            ");
			buffer.append("							</div>                                                                            ");
			buffer.append("						</div>                                                                                ");
			buffer.append("					</div>                                                                                    ");
			buffer.append("					<span class='current-time'>                                                               ");
			buffer.append("						00:00                                                                                 ");
			buffer.append("					</span>                                                                                   ");
			buffer.append("				</div>                                                                                        ");
			return buffer.toString();
		}
		return "";
	}
	
	/**
	 * 分享按钮
	 * @return
	 */
	public static String shareEncode(Post post,User user,String postUrl){
		StringBuffer buffer=new StringBuffer();
		
		String postTitle=post.getPostTitle();
		buffer.append("			<footer class='entry-footer'>                                                                                                 ");
		buffer.append("				<div class='meta-author'>                                                                                                 ");
		buffer.append("					文 / "+user.getDisplayName()+"                                                                                    	  ");
		buffer.append("				</div>                                                                                                                    ");
		buffer.append("				<div class='share'>                                                                                                       ");
		buffer.append("					<ul class='share-ul'>                                                                                                 ");
		buffer.append("						<li>                                                                                                              ");
		buffer.append("							<a href='http://twitter.com/share?url="+postUrl+"&text="+postTitle+"'                                         ");
		buffer.append("							target='_blank' rel='nofollow' class='twitter-share' title='Twitter'>                                         ");
		buffer.append("							</a>                                                                                                          ");
		buffer.append("						</li>                                                                                                             ");
		buffer.append("						<li>                                                                                                              ");
		buffer.append("							<a href='http://facebook.com/share.php?u="+postUrl+"/&t="+postTitle+"'                                        ");
		buffer.append("							target='_blank' rel='nofollow' class='facebook-share' title='facebook'>                                       ");
		buffer.append("							</a>                                                                                                          ");
		buffer.append("						</li>                                                                                                             ");
		buffer.append("						<li>                                                                                                              ");
		buffer.append("							<a href='http://v.t.sina.com.cn/share/share.php?url="+postUrl+"&title="+postTitle+"'                          ");
		buffer.append("							target='_blank' rel='nofollow' class='sina-share' title='新浪微博'>                      					  ");
		buffer.append("							</a>                                                                                                          ");
		buffer.append("						</li>                                                                                                             ");
		buffer.append("						<li>                                                                                                              ");
		buffer.append("							<a href='http://v.t.qq.com/share/share.php?title="+postTitle+"&url="+postUrl+"'                               ");
		buffer.append("							target='_blank' rel='nofollow' class='tencent-share' title='腾讯微博'>                                         ");
		buffer.append("							</a>                                                                                                          ");
		buffer.append("						</li>                                                                                                             ");
		buffer.append("						<li>                                                                                                              ");
		buffer.append("							<a href='http://www.douban.com/recommend/?url="+postUrl+"&title="+postTitle+"'                                ");
		buffer.append("							target='_blank' rel='nofollow' class='douban-share' title='豆瓣网'>                                            ");
		buffer.append("							</a>                                                                                                          ");
		buffer.append("						</li>                                                                                                             ");
		buffer.append("						<li>                                                                                                              ");
		buffer.append("							<a href='http://fanfou.com/sharer?u="+postUrl+"&t="+postTitle+"'                                              ");
		buffer.append("							target='_blank' rel='nofollow' class='fanfou-share' title='饭否网'>                                            ");
		buffer.append("							</a>                                                                                                          ");
		buffer.append("						</li>                                                                                                             ");
		buffer.append("						<li>                                                                                                              ");
		buffer.append("							<a href='http://share.renren.com/share/buttonshare?link="+postUrl+"&title="+postTitle+"'                      ");
		buffer.append("							target='_blank' rel='nofollow' class='renren-share' title='人人网'>                                           ");
		buffer.append("							</a>                                                                                                          ");
		buffer.append("						</li>                                                                                                             ");
		buffer.append("						<li>                                                                                                              ");
		buffer.append("							<a href='http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url="+postUrl+"&title="+postTitle+"'      ");
		buffer.append("							target='_blank' rel='nofollow' class='qzone-share' title='QQ空间'>                                            ");
		buffer.append("							</a>                                                                                                          ");
		buffer.append("						</li>                                                                                                             ");
		buffer.append("					</ul>                                                                                                                 ");
		buffer.append("					<span class='share-c'>                                                                                                ");
		buffer.append("						分享到                                                                                                                      												  ");
		buffer.append("					</span>                                                                                                               ");
		buffer.append("				</div>                                                                                                                    ");
		buffer.append("			</footer>                                                                                                                     ");
		return buffer.toString();
	}
	
	/**
	 * 获取文章评论列表
	 * @param post
	 * @param comments
	 * @return
	 */
	public static String commentEncode(Post post,List<Comment> comments,HttpServletRequest request) throws Exception{
		String postUrl=HomeDir.getPostUrl(request, post);
		List<Comment> treeC=TreeCommentUtil.getFatherComment(comments);
		StringBuffer buffer=new StringBuffer();
		buffer.append("		<div id='comments'>                                                                                                               ");
		//循环遍历评论列表
		if(comments.size()>0){
			buffer.append("			<div class='comment-title'> 																							  ");
			buffer.append("				<span>                    																							  ");
			buffer.append(					comments.size() +" 个评论"              																			   );
			buffer.append("				</span>                   																							  ");
			buffer.append("			</div>                      																							  ");
			buffer.append("			<ol class='comment-list'>																								  ");
			//循环
			for(Comment c:treeC){
				buffer.append(HtmlEncoder.getFatherComment(post,c,postUrl));
			}
			//循环结束
			buffer.append("			</ol>"																													   );
		}
		//评论表单
		buffer.append(HtmlEncoder.getCommentForm(post,request)																										   );
		buffer.append("		</div>                                                                                                                            ");
		return buffer.toString();
	}
	/**
	 * 评论表单
	 * @return
	 * @throws Exception 
	 */
	public static String getCommentForm(Post post,HttpServletRequest request) throws Exception{
		StringBuffer buffer=new StringBuffer();
		buffer.append("			<!--comentlist-->                                                                                                             ");
		buffer.append("			<div class='comment-title'>                                                                                                   ");
		buffer.append("				<span>                                                                                                                    ");
		buffer.append("					留 言                                                                                                                 														  ");
		buffer.append("				</span>                                                                                                                   ");
		buffer.append("			</div>                                                                                                                        ");
		buffer.append("			<div id='respond'>                                                                                                            ");
		buffer.append("				<form action='' method='post'                                                    										  ");
		buffer.append("				id='commentform'>                                                                                                         ");
		buffer.append("					<span class='cancel_comment_reply'>                                                                                   ");
		buffer.append("						<a rel='nofollow' id='cancel-comment-reply-link' href=''                                   						  ");
		buffer.append("						style='display:none;'>                                                                                            ");
		buffer.append("							取消回复                                                                                                                  											  ");
		buffer.append("						</a>                                                                                                              ");
		buffer.append("					</span>                                                                                                               ");
		
        if (null!=CookieUtil.getCookieByName(request, "author")) {
        	buffer.append("					<div class='welcome'>																								  ");
    		buffer.append("						欢迎回来，"+URLDecoder.decode(CookieUtil.getCookieByName(request, "author").getValue(), "utf-8")+"！				  ");
    		buffer.append("						<span class='info-edit'>																						  ");
    		buffer.append("							修改																											  ");
    		buffer.append("						</span>																											  ");
    		buffer.append("					</div>																												  ");
    		buffer.append("					<div class='author-info'>                                                                                             ");
    		buffer.append("						<div>                                                                                                             ");
    		buffer.append("							<label>名字：</label>                                                                                         ");
    		buffer.append("							<input type='text' required='' name='commentAuthor' id='author' value='"+URLDecoder.decode(CookieUtil.getCookieByName(request, "author").getValue(), "utf-8")+"' tabindex='1' aria-required='true'/> ");
    		buffer.append("						</div>                                                                                                            ");
    		buffer.append("						<div>                                                                                                             ");
    		buffer.append("							<label>邮箱：</label>                                                                                         ");
    		buffer.append("							<input type='email' required='' name='commentAuthorEmail' id='email' value='"+URLDecoder.decode(CookieUtil.getCookieByName(request, "email").getValue(), "utf-8")+"' tabindex='2' aria-required='true'/> ");
    		buffer.append("						</div>                                                                                                            ");
    		buffer.append("						<div>                                                                                                             ");
    		buffer.append("							<label>网站：</label>                                                                                         ");
    		buffer.append("							<input type='text' name='commentAuthorUrl' id='url' value='"+URLDecoder.decode(CookieUtil.getCookieByName(request, "url").getValue(), "utf-8")+"' tabindex='3' /> ");
    		buffer.append("						</div>                                                                                                            ");
    		buffer.append("					</div>                                                                                                                ");
        } else {
    		buffer.append("					<div class='author-info'>                                                                                             ");
    		buffer.append("						<div>                                                                                                             ");
    		buffer.append("							<label>名字：</label>                                                                                         ");
    		buffer.append("							<input type='text' required='' name='commentAuthor' id='author' value='' tabindex='1' aria-required='true'/>                     ");
    		buffer.append("						</div>                                                                                                            ");
    		buffer.append("						<div>                                                                                                             ");
    		buffer.append("							<label>邮箱：</label>                                                                                         ");
    		buffer.append("							<input type='email' required='' name='commentAuthorEmail' id='email' value='' tabindex='2' aria-required='true'/>                       ");
    		buffer.append("						</div>                                                                                                            ");
    		buffer.append("						<div>                                                                                                             ");
    		buffer.append("							<label>网站：</label>                                                                                         ");
    		buffer.append("							<input type='text' name='commentAuthorUrl' id='url' value='' tabindex='3' />                                               ");
    		buffer.append("						</div>                                                                                                            ");
    		buffer.append("					</div>                                                                                                                ");
        }
		buffer.append("					<div class='comment-textarea'>                                                                                        ");
		buffer.append("						<textarea name='commentContent' required='' id='comment' tabindex='4' 	"
				+ "onkeydown=\"if(event.ctrlKey&&event.keyCode==13){document.getElementById('submit').click();return false;};\"></textarea>					  ");
		buffer.append("					</div>                                                                                                                ");
		buffer.append("					<div>                                                                                                                 ");
		buffer.append("						<input name='submit' type='submit' id='submit' tabindex='5' value='发布'/>                                        ");
		buffer.append("						<input type='hidden' name='commentPostId' value='"+post.getId()+"' id='comment_post_ID'/>                       ");
		buffer.append("						<input type='hidden' name='commentParent' id='comment_parent' value='0'/>                                        ");
		buffer.append("					</div>                                                                                                                ");
		buffer.append("				</form>                                                                                                                   ");
		buffer.append("			</div>                                                                                                                        ");
		return buffer.toString();
	}
	/**
	 * 获取父类评论列表
	 * @param post
	 * @param c
	 * @return
	 */
	public static String getFatherComment(Post post,Comment c,String postUrl){
		StringBuffer buffer=new StringBuffer();
		buffer.append("				<li  id='li-comment-"+c.getCommentId()+"'>                                                       				  ");
		buffer.append("					<div id='comment-"+c.getCommentId()+"' class='comment-body'>                                                  ");
		buffer.append("						<div class='author'>                                                                                      ");
		buffer.append("							<img alt='' src='"+c.getCommentHeaderUrl()+"'														  ");
		buffer.append("							srcset='"+c.getCommentHeaderUrl()+"'     															  ");
		buffer.append("							class='avatar avatar-38 photo' height='38' width='38' />                                              ");
		buffer.append("						</div>                                                                                                    ");
		buffer.append("						<span class='time'>                                                                                       ");
		buffer.append("							"+RelativeDateFormat.format(c.getCommentDate())+"前                                                                                           	  ");
		buffer.append("						</span>                                                                                                   ");
		buffer.append("						<div class='commlist-middle'>                                                                             ");
		buffer.append("							<span class='name'>                                                                                   ");
		buffer.append("								<a href='"+c.getCommentAuthorUrl()+"' rel='external nofollow' class='url'                         ");
		buffer.append("								target='_blank'>                                                                                  ");
		buffer.append(									c.getCommentAuthor()																		   );
		buffer.append("								</a>                                                                                              ");
		buffer.append("							</span>                                                                                               ");
		buffer.append("							<div class='reply'>                                                                                   ");
		buffer.append("								<a rel='nofollow' class='comment-reply-link' href='"+postUrl+"'  								  ");
		buffer.append("								onclick='return addComment.moveForm(\"comment-"+c.getCommentId()+"\",\""+c.getCommentId()+"\", \"respond\", \""+post.getId()+"\")'");
		buffer.append("								aria-label='回复给"+c.getCommentAuthor()+"'>回复</a>                                               ");
		buffer.append("							</div>                                                                                                 ");
		buffer.append("							<div class='text'>                                                                                     ");
		buffer.append("								<p>                                                                                                ");
		buffer.append(									c.getCommentContent()																			);
		buffer.append("								</p>                                                                                               ");
		buffer.append("							</div>                                                                                                 ");
		buffer.append("						</div>                                                                                                     ");
		buffer.append("					</div>                                                                                                         ");
		//获取子类评论列表
		if(c.getCommentChildren()!=null){
			buffer.append("					<ul class='children'>																					   ");
			for(Comment comment:c.getCommentChildren()){
				buffer.append(HtmlEncoder.getFatherComment(post, comment,postUrl));
			}
			buffer.append("					</ul>"																										);
			buffer.append("					<!-- .children -->																						   ");
		}
		buffer.append("				</li>                                                                                                              ");
		buffer.append("				<!-- #comment-## -->																							   ");
		return buffer.toString();
	}
	
	/**
	 * 分页页码
	 * @param pageNum
	 * @param totalCount
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String pageNumEncode(int currentPage,int totalCount ) throws Exception{
		StringBuffer buffer=new StringBuffer();
		buffer.append("<nav id='wide-page-navi' class='page-navi'>");
		int totalPage=0;
		if(totalCount>9){
			totalPage=totalCount%9==0?totalCount/9:totalCount/9+1;
		}
		if(totalPage>1){
			ArrayList<Integer> list=new ArrayList<Integer>();
			//向前追加4页
			for(int i=currentPage;i>=currentPage-4;i--){
				list.add(i);
				if(i==1)break;
			}
			//向后追加4页
			for(int j=currentPage;j<=currentPage+4;j++){
				list.add(j);
				if(j==totalPage)break;
			}
			if(list.size()>0){
				//排序去重后拼接页码
				Collections.sort(list);
				list=method(list);
				if(list.get(0)>2){
					buffer.append("<a class='page-numbers' href=''>"+1+"</a>");
					buffer.append("<span class='page-numbers current'>…</span>");
				}else if(list.get(0)>1){
					buffer.append("<a class='page-numbers' href=''>"+1+"</a>");
				}
				for(int k:list){
					if(k==currentPage){
						buffer.append("<span class='page-numbers current'>"+k+"</span>");
					}else{
						buffer.append("<a class='page-numbers' href=''>"+k+"</a>");
					}
				}
				if(list.get(list.size()-1) != null){
					if(list.get(list.size()-1)<totalPage-1) {
						buffer.append("<span class='page-numbers current'>…</span>");
						buffer.append("<a class='page-numbers' href=''>"+totalPage+"</a>");
					}else if(list.get(list.size()-1)<totalPage){
						buffer.append("<a class='page-numbers' href=''>"+totalPage+"</a>");
					}
				}
			}
		}
		buffer.append("</nav>");
		return buffer.toString();
	}
	

	
	/**
	 * list列表去重
	 * @param list
	 * @return
	 */
	private static ArrayList<Integer> method(ArrayList<Integer> list) {
		ArrayList<Integer> arr=new ArrayList<Integer>();
		for(Iterator<Integer> it=list.iterator();it.hasNext();){
			Integer j=it.next();
			if(!arr.contains(j)){
				arr.add(j);
			}
		}
		return arr;
	}
	/**
	 * 底部留言列表，获取每个人最新的一个评论。
	 * @param comments
	 * @return
	 */
	public static String footerCommentEncode(List<Comment> comments) {
		StringBuffer buffer=new StringBuffer();
		buffer.append("<div id='full-footer-widget'>                                                           ");
		buffer.append("	<aside id='full-footer-readwall' class='foo-widget readwall'>                          ");
		buffer.append("		<ul>                                                                               ");
		for(Comment comment:comments){
			buffer.append("			<li>                                                                               				");
			buffer.append("				<a href='"+comment.getCommentAuthorUrl()+"' target='_blank'>                                ");
			buffer.append("					<img alt='' src='"+comment.getCommentHeaderUrl()+"' srcset='"+comment.getCommentHeaderUrl()+" 2x'  ");
			buffer.append("					class='avatar avatar-46 photo avatar-default'>                                 			");
			buffer.append("				</a>                                                                             			");
			buffer.append("				<span class='author'>                                                            			");
			buffer.append("				</span>                                                                          			");
			buffer.append("				<div class='detail'>                                                             			");
			buffer.append("					<a href='"+comment.getCommentAuthorUrl()+"' target='_blank' class='author'>             ");
			buffer.append("						<img alt='' src='"+comment.getCommentHeaderUrl()+"' srcset='"+comment.getCommentHeaderUrl()+" 2x'");
			buffer.append("						class='avatar avatar-46 photo'>                                              		");
			buffer.append(comment.getCommentAuthor()																				 );
			buffer.append("					</a>                                                                           			");
			buffer.append("					<span class='count'>                                                           			");
			buffer.append("						总评论数："+comment.getCommentCount()+"                                     			");
			buffer.append("					</span>                                                                        			");
			buffer.append("					<a href='"+comment.getPostUrl()+"' class='recent-comment' id='"+comment.getCommentPostId()+"'>");
			if(!org.apache.commons.lang3.StringUtils.isBlank(comment.getCommentContent()) && comment.getCommentContent().length()>29){
				buffer.append(comment.getCommentContent().substring(0, 29)+"…");
			}else{
				buffer.append(comment.getCommentContent());
			}
			buffer.append("					</a>                                                                           			");
			buffer.append("					<span>                                                                         			");
			buffer.append("						（这家伙已经\""+RelativeDateFormat.format(comment.getCommentDate())+"\"没有留言了！） ");
			buffer.append("					</span>                                                                        			");
			buffer.append("					<div class='triangle'>                                                         			");
			buffer.append("						<div>                                                                        		");
			buffer.append("						</div>                                                                       		");
			buffer.append("					</div>                                                                         			");
			buffer.append("				</div>                                                                           			");
			buffer.append("			</li>		                                                                           			");
		}
		buffer.append("		</ul>                                                                              ");
		buffer.append("	</aside>                                                                               ");
		buffer.append("</div>                                                                                  ");
		return buffer.toString();
	}
	/**
	 * 文章评论者明细：第一次在本博客的留言和留言时间
	 * @return
	 */
	public static String postCommentEncode(Comment c){
		StringBuffer buffer=new StringBuffer();
		buffer.append("<div class='list-detail'>                                    ");
		buffer.append("	<div class='triangle'>                                      ");
		buffer.append("		<div>                                                   ");
		buffer.append("		</div>                                                  ");
		buffer.append("	</div>                                                      ");
		buffer.append("	<a href='"+c.getCommentAuthorUrl()+"' target='_blank' class='author'>");
		buffer.append("		<img alt='' src='"+c.getCommentHeaderUrl()+"'           ");
		buffer.append("		srcset='"+c.getCommentHeaderUrl()+" 2x'                 ");
		buffer.append("		class='avatar avatar-46 photo' height='46' width='46' />");
		buffer.append(		c.getCommentAuthor()									 );
		buffer.append("	</a>                                                        ");
		buffer.append("	<span class='count'>                                        ");
		buffer.append("		总评论数："+c.getCommentCount()                          );
		buffer.append("	</span>                                                     ");
		buffer.append("	<a href='"+c.getPostUrl()+"' class='earlist-comment' id='"+c.getCommentPostId()+"'>");
		buffer.append("		第一次留言："+c.getCommentContent()						 );
		buffer.append("	</a>                                                        ");
		buffer.append("	<span>                                                      ");
		buffer.append("（这家伙从\""+RelativeDateFormat.format(c.getCommentDate())+"\"前开始在本博客留言！）");
		buffer.append("	</span>                                                     ");
		buffer.append("</div>                                                       ");
		return buffer.toString();
	}
	/**
	 * 新增评论后，返回该评论的内容
	 * @param c
	 * @return
	 */
	public static String newCommentEncode(Comment c){
		StringBuffer buffer=new StringBuffer();
		buffer.append("<li class='comment even thread-even depth-1' id='li-comment-"+c.getCommentId()+"'>          ");
		buffer.append("	<div id='comment-"+c.getCommentId()+"' class='comment-body'>                               ");
		buffer.append("		<div class='author'>                                                                   ");
		buffer.append("			<img alt='' src='"+c.getCommentHeaderUrl()+"'                                      ");
		buffer.append("			srcset='"+c.getCommentHeaderUrl()+" 2x'                                            ");
		buffer.append("			class='avatar avatar-33 photo' height='33' width='33' />                           ");
		buffer.append("		</div>                                                                                 ");
		buffer.append("		<span class='time'>                                                                    ");
		buffer.append( 			RelativeDateFormat.format(c.getCommentDate())+"前                                                                    ");
		buffer.append("		</span>                                                                                ");
		buffer.append("		<div class='commlist-middle'>                                                          ");
		buffer.append("			<span class='name'>                                                                ");
		buffer.append("				<a href='"+c.getCommentAuthorUrl()+"' rel='external nofollow' class='url' target='_blank'>  ");
		buffer.append(					c.getCommentAuthor()														);
		buffer.append("				</a>                                                                           ");
		buffer.append("			</span>                                                                            ");
		buffer.append("			<div class='text'>                                                                 ");
//		if(c.getCommentParent()==0){
			buffer.append("			<p>"+c.getCommentContent()+"</p>											   ");
//		}else{
//			buffer.append("				<span class='comment-to'>                                                  ");
//			buffer.append("					<a href='#comment-"+c.getCommentParent()+"' title='父评论内容'>     	   ");
//			buffer.append("						@父评论作者                                                                 						   ");
//			buffer.append("					</a>                                                                   ");
//			buffer.append("				</span>                                                                    ");
//			buffer.append(				c.getCommentContent()														);
//		}
		buffer.append("			</div>                                                                             ");
		buffer.append("		</div>                                                                                 ");
		//如果开启评论审核机制
//		buffer.append("		<em>                                                                                   ");
//		buffer.append("			<span class='moderation'>                                                          ");
//		buffer.append("				您的评论正在等待审核。                                           									   ");
//		buffer.append("			</span>                                                                            ");
//		buffer.append("		</em>                                                                                  ");
		buffer.append("	</div>                                                                                     ");
		return buffer.toString();
	}

}

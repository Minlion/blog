<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 	<title>
		${title}
	</title>
    <base href="<%=basePath%>">
    <meta content="text/html;charset=${optionsMap.blog_charset}">
    <meta name="keywords" content="${optionsMap.blogname }">
    <meta name="description" content="${optionsMap.blogdescription }">
    <link rel="stylesheet" href="<%=basePath %>/home/css/style.css">
  </head>
  
  <body>
    <div id="wrapper">
		${getHeader }
    </div>
    <div id="scrolltop" class="hide" >
	</div>
	<div id="loading-wrap">
		<div class="loading">
			<div class="loading-bar">
				<div class="bar1"></div>
				<div class="bar2"></div>
				<div class="bar3"></div>
				<div class="bar4"></div>
			</div>
			<div class="loading-text">努力加载中…</div>
		</div>
	</div><!--loading-->
	<div id="jquery_jplayer" class="jp-jplayer"></div>
	
	<script type="text/javascript" src="home/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="home/js/jquery.jplayer.min.js"></script>
	<script type="text/javascript" src="home/js/jquery.mousewheel.js"></script>
	<script type="text/javascript" src="home/js/responsive.js"></script>
	<script type="text/javascript" src="home/js/comment.js"></script>
	<script type="text/javascript" src="home/js/audio_player.js"></script>
	<script type="text/javascript" src="home/js/bg.js"></script>
	<script type="text/javascript" src="home/js/gallery.js"></script>
	<script type="text/javascript">
// 	http:\/\/www.limingliang.net\/blog\/
	/* <![CDATA[ */
	var Always = {"is_mobile":"0","ajaxurl":"<%=basePath%>","ajax_site_title":"${optionsMap.blogname }"};
	/* ]]> */
	</script>
	<script type="text/javascript" src="home/js/index.js"></script>
	<script type="text/javascript" src="home/js/site-ajax.js"></script>
	
  </body>
</html>

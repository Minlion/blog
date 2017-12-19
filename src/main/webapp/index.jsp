<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String mainPage=basePath+"all";
%>
<!--跳转到主页-->
<script type="text/javascript">
	location.href="<%=mainPage%>";
</script>

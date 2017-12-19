package net.limingliang.console.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.limingliang.ioc.user.dto.User;
import net.limingliang.ioc.user.service.UserService;
import net.limingliang.utils.Constants;
import net.limingliang.utils.CookieUtil;
import net.limingliang.utils.HomeDir;
import net.limingliang.utils.MD5;

@Controller
public class ConsoleController {
	
	private static final Logger logger=Logger.getLogger(ConsoleController.class);
//	private static SystemConfig sysConfig=new SystemConfig();
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value="console")
	public String console(HttpServletRequest request,HttpServletResponse response){
		try {
			//判断是否登录，如果未登录返回到登录界面，已登录返回到控制台
			if(null!=CookieUtil.getCookieByName(request, "userEmail")){
				User user=new User();
				user.setUserEmail(URLDecoder.decode(CookieUtil.getCookieByName(request, "userEmail").getValue(), "utf-8"));
				user.setUserPass(URLDecoder.decode(CookieUtil.getCookieByName(request, "userPass").getValue(), "utf-8"));
				User u=userService.checkUser(user);
				if(user.getUserPass().equals(u.getUserPass())){
					return "console/index.html";
				}
			}
			return "console/login_hide_navbar.html";
		} catch (Exception e) {
			logger.debug("访问控制台失败：",e);
			return "404.jsp";
		}

	}
	
	
	@RequestMapping(value="login",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request,HttpServletResponse response,User user){
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			String remem=request.getParameter("remem");
			User u=userService.checkUser(user);
			MD5 md5=new MD5();
			String password_md5=md5.toDigest(user.getUserPass());
			if(u==null){
				map.put("success",false);
				map.put("errorMsg", "邮箱不存在");
				return map;
			}
			if(StringUtils.isBlank(password_md5) || !password_md5.equals(u.getUserPass())){
				map.put("success",false);
				map.put("errorMsg", "密码错误");
				return map;
			}
			if(remem!=null && remem.equals("on")){
				//添加cookie
				CookieUtil.addCookie(response, "userEmail", URLEncoder.encode(user.getUserEmail(), "utf-8"), Constants.LOGINCOOKIEAGE);
				CookieUtil.addCookie(response, "userPass", URLEncoder.encode(password_md5,"utf-8"), Constants.LOGINCOOKIEAGE);
			}
			//删除cookie
			CookieUtil.delCookie(request,response,"userEmail");
			CookieUtil.delCookie(request,response,"userPass");
			map.put("success",true);
			map.put("successMsg", HomeDir.getHomeUrl(request)+"/console");//跳转路径
			return map;
		} catch (Exception e) {
			logger.debug("登录失败：",e);
			return new HashMap<String,Object>(){
				private static final long serialVersionUID = 1L;
				{
					put("success",false);
					put("errorMsg","登录出现了一些问题，请刷新页面后再试…");
				}
			};
		}
	}

}

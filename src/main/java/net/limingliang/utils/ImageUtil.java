package net.limingliang.utils;

import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


/**
 * 图片公共类
 * @author Sunny
 *
 */
public class ImageUtil {
	
	/**
	 * 获取评论头像，如果评论者使用的QQ邮箱，则获取其QQ头像
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	public static String getHeaderUrl(String mail) throws Exception{
			if(checkQQ(mail)){
				String qqUrl=getQQUrl(mail.split("@")[0]);
				String message=getUrlConnection(qqUrl).getHeaderField(0);
				if(StringUtils.isNotBlank(message) && message.startsWith("HTTP/1.1 200 OK")){
					if(!checkQQUrl(qqUrl)){
						return qqUrl;
					}
					return "home/images/header-46x46.png";
				}
				return "home/images/header-46x46.png";
			}
			return "home/images/header-46x46.png";
	}
	
	/**
	 * 获取文章中的第一张图片，如果没有则使用系统默认图片
	 * @param article
	 * @return
	 */
	public static String getFirstImageUrl(String content){
		if(StringUtils.isNotEmpty(content)){
			Pattern pattern=Pattern.compile("<img.*?src=[\'\"](.*?)[\'\"].*?>");
			Matcher ma=pattern.matcher(content);
			while(ma.find()){
				return ma.group();
			}
			return "<img src='home/images/thumb.jpg'>";
		}
		return "<img src='home/images/thumb.jpg'>";
	}
	
	/**
	 * 判断是否为QQ邮件地址(数字邮箱地址)
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private static boolean checkQQ(String mail) throws Exception{ 
       Pattern pattern=Pattern.compile("[0-9]{5,12}@(qq|QQ).(COM|com|CN|cn)");
       if(StringUtils.isNotBlank(mail) && pattern.matcher(mail).matches()){
    		   if(!mail.startsWith("0")){
    			   return true;
    		   }
    		   return false;
       }
       return false;
    }

	/**
	 * 判断QQ头像是否存在
	 * @param qqUrl
	 * @return
	 * @throws Exception
	 */
	private static boolean checkQQUrl(String qqUrl) throws Exception{
		InputStream qqIn=getUrlConnection(qqUrl).getInputStream();
		InputStream compareIn=getUrlConnection(Constants.QQHEADPHOTOURL).getInputStream();
		return StringUtils.equals(getFileMD5(qqIn), getFileMD5(compareIn));
	}
	
	/**
	 * 获取文件的 MD5 值
	 * @param file
	 * @return
	 */
	private static String getFileMD5(InputStream in) throws Exception{
	    MessageDigest digest = null;
	    byte buffer[] = new byte[8192];
	    int len;
        digest =MessageDigest.getInstance("MD5");
        while ((len = in.read(buffer)) != -1) {
            digest.update(buffer, 0, len);
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        in.close();
        return bigInt.toString(16);
	}
	
	/**
	 * 获取QQ头像地址
	 * @param url
	 * @return
	 */
	private static String getQQUrl(String url) throws Exception{
		return Constants.QQHEADPHOTOURL.replace("QQ", url);
	}
	
	/**
	 * 获取网络请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private static HttpURLConnection getUrlConnection(String url) throws Exception{
		return (HttpURLConnection)new URL(url).openConnection();
	}
	
	
	
	public static void main(String[] args) throws Exception {
		checkQQUrl("http://q2.qlogo.cn/headimg_dl?dst_uin=342786498&spec=100&term_type=PC");
	}
}

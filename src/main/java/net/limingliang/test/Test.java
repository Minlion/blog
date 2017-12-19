package net.limingliang.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	public static void main(String[] args) throws Exception {
//		System.out.println(URLDecoder.decode("测试", "utf-8"));
//		System.out.println(URLEncoder.encode("测试", "utf-8"));
		
		//零宽断言(?=exp)匹配exp前面的位置       (?<=exp)匹配exp后面的位置	(?!exp)	匹配后面跟的不是exp的位置	(?<!exp)	匹配前面不是exp的位置
		String str1="ytyr[mp3 auto=0][/mp3]ytrytryfsfsr[mp3 auto=0]sss[/mp3]	wqertyuio";
		String str2="图片文章图片文章"
				+ "<img class=aligncenter size-large wp-image-22 src=http://localhost/wordpress/wp-content/uploads/2017/06/b8389b504fc2d562ddb7cb45e71190ef77c66cde-1024x640.jpg alt=b8389b504fc2d562ddb7cb45e71190ef77c66cde width=1024 height=640 />"
				+ "图片文章图片文章图片文图片文章章图片文章<img class=aligncenter size-large wp-image-22 src=http://localhost/wordpress/wp-content/uploads/2017/06/b8389b504fc2d562ddb7cb45e71190ef77c66cde-1024x640.jpg alt=b8389b504fc2d562ddb7cb45e71190ef77c66cde width=1024 height=640 />图片文章图";
		
		getDetail(str1);
		getDetail(str2);
	}
	
	public static String getDetail(String str){
		Pattern pattern=Pattern.compile("(?=\\[mp3 auto=[0-1]\\])(.+?)(?<=\\[\\/mp3\\])");
//		Pattern pattern =Pattern.compile("(?<=\\[mp3 auto=[0-1]\\])(.+?)(?=\\[\\/mp3\\])");
//		Pattern pattern=Pattern.compile("(?=\\<img class=)(.+?)(?<=\\/>)");
		Matcher m=pattern.matcher(str);
		while(m.find()){
			System.out.println(1);
			System.out.println(m.group());
		}
		return "";
	}

}

package blog;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.limingliang.ioc.post.service.PostService;


@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"}) 
public class TestMyBatis {
	
	@Resource
	private PostService postService = null;
	
	@Test  
    public void test1() {  
		try {
	        System.out.println(postService.getPostCount(""));
		} catch (Exception e) {
			e.printStackTrace();
		}

    } 

}

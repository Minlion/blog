package net.limingliang.system;

import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Spring 容器初始化时加载 
 * @author Sunny
 *
 */
public class TrackManagerPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if(bean instanceof InitService) {  
            ((InitService)bean).init();  
        }  
        return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		return bean;
	}

}

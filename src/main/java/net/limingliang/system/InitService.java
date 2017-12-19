package net.limingliang.system;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import net.limingliang.home.service.MainService;

/**
 * 初始化系统信息
 * @author Sunny
 *
 */
public class InitService {
	
	private static final Logger logger=Logger.getLogger(InitService.class);
	
	@Resource
	private MainService mainService;
  
    public void init() {
    	logger.info("========================================初始化系统信息========================================");
    	try {
        	SystemConfig sysConfig=new SystemConfig();
        	sysConfig.setOptionsMap(mainService.initOptionsInfo());
        	logger.info("========================================初始化系统信息成功========================================");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("初始化系统信息失败:",e);
		}

    }  
}

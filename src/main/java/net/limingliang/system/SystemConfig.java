package net.limingliang.system;

import java.util.Map;

/**
 * 缓存系统信息
 * @author Sunny
 *
 */
//TODO 修改使用缓存方法
public class SystemConfig {
	
	public static Map<String, String> optionsMap;

	
	public  Map<String, String> getOptionsMap() {
		return optionsMap;
	}

	public void setOptionsMap(Map<String, String> optionsMap) {
		SystemConfig.optionsMap = optionsMap;
	}
	
	
}

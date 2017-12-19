package net.limingliang.home.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.limingliang.home.service.MainService;
import net.limingliang.ioc.option.dao.OptionDao;
import net.limingliang.ioc.option.dto.Option;

@Service("mainService")
public class MainServiceImpl implements MainService {
	
	@Resource
	private OptionDao optionsDao;
	
	
	/**
	 * 初始化系统信息
	 */
	@Override
	public Map<String, String> initOptionsInfo() {
		List<Option> options=optionsDao.selectAll();
		Map<String,String> map=new HashMap<String,String>();
		for(Iterator<Option> it=options.iterator();it.hasNext();){
			Option option=it.next();
			map.put(option.getOptionName(), option.getOptionValue());
		}
		return map;
	}
	

}

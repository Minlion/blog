package net.limingliang.ioc.option.dao;

import java.util.List;

import net.limingliang.ioc.option.dto.Option;

public interface OptionDao {
    int deleteByPrimaryKey(Long optionId);

    int insert(Option record);

    int insertSelective(Option record);

    Option selectByPrimaryKey(Long optionId);

    int updateByPrimaryKeySelective(Option record);

    int updateByPrimaryKey(Option record);
    
    List<Option> selectAll();
}
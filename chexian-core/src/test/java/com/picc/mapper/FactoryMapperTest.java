package com.picc.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.picc.AbstractSpringTest;
import com.picc.chexian.core.entity.Factory;
import com.picc.chexian.core.mapper.FactoryMapper;

public class FactoryMapperTest extends AbstractSpringTest {

	@Autowired
	FactoryMapper mapper;


    @Test
    public void testSelectAll() {
    	Factory fact = mapper.selectByPrimaryKey(1);    	
    	logger.debug("aList content:{}", fact);
    }

}

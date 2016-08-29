package com.picc.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.picc.AbstractSpringTest;
import com.picc.chexian.core.upyun.UpYunImageStore;
import com.picc.chexian.core.upyun.UpYun.FolderItem;

public class UpYunTest extends AbstractSpringTest {


	@Autowired
	UpYunImageStore upYunStore;
	
	
	@Test
	public void testRead(){
		try {
			List<FolderItem> list = upYunStore.readDir("");
			if (list != null){
				System.out.println("list size="+list.size());
				for (FolderItem item: list){
					if (item.name.endsWith(".ipa")){
						logger.debug(item.name);
//						if (!item.name.endsWith("234879204.ipa")){
//							upYunStore.delete(item.name);
//						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete(){
		String names = "20150905_1486656431.png,20150905_892069325.png,20150905_2126415316.png,20150905_347191510.png,20150905_1608079091.png";
		String[] name = names.split(",");
		for (String a: name){
			upYunStore.delete(a);
		}
	}

}

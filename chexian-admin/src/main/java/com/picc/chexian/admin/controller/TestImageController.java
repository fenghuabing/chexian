package com.picc.chexian.admin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.picc.chexian.core.upyun.UpYunImageStore;
import com.picc.chexian.core.util.FileUtils;

/**
 * 上传图片到CDN(又拍云)的例子
 * @author huabing.feng
 *
 */
public class TestImageController {

    @Autowired
    UpYunImageStore imageStore;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFile(@RequestParam MultipartFile file,
                            @RequestParam("name") String fileName) throws IOException {

    	String imagePath = "/tmp/" + fileName;
        FileUtils.writeToFile(file.getInputStream(), imagePath);
    	String storeName = FileUtils.hash(fileName);;
      	boolean storeResult = imageStore.store(storeName, imagePath);
      	if (storeResult){
          	return storeName;
      	}
      	return null;
    }

}

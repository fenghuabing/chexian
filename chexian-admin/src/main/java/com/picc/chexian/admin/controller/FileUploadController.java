package com.picc.chexian.admin.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.picc.chexian.core.upyun.UpYunImageStore;

@Controller
@RequestMapping(value = "/upload")
public class FileUploadController extends BaseController {

	public static DateFormat format = new SimpleDateFormat("yyyyMMdd");
	public static final String FILE_ROOT_PATH = "/mnt/data/xigua";

	public static final String FILE_ROOT_URL = "http://image.xiguamei.com/";
	@Autowired
	UpYunImageStore upYunStore;
	
	@ResponseBody
	@RequestMapping(value = "/uploadfile")
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		try {
			logger.debug("enter /upload/uploadfile");
			String randomFilename = "";
			Random rand = new Random();// 生成随机数
			int random = rand.nextInt();
//			Calendar calCurrent = Calendar.getInstance();
//			int intDay = calCurrent.get(Calendar.DATE);
//			int intMonth = calCurrent.get(Calendar.MONTH) + 1;
//			int intYear = calCurrent.get(Calendar.YEAR);
			String now = format.format(new Date()) + "_";
			randomFilename = now
					+ String.valueOf(random > 0 ? random : (-1) * random)
					+ file.getOriginalFilename()
							.substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
			File targetFile = new File(FILE_ROOT_PATH, randomFilename);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				throw e;
			}
			upYunStore.store(randomFilename, targetFile.getAbsolutePath());

			logger.debug("exit /upload/uploadfile");
			return upYunStore.URL + randomFilename;
//			return FILE_ROOT_URL + randomFilename;
		} catch (Exception ex) {
			logger.debug(ex.getMessage(), ex);
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

}
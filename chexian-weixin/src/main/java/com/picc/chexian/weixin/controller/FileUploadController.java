package com.picc.chexian.weixin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/upload")
public class FileUploadController extends BaseController {

	public static DateFormat format = new SimpleDateFormat("yyyyMMdd");
	public static final String FILE_ROOT_PATH = "/app/wwwroot/static";

	
	@ResponseBody
	@RequestMapping(value = "/uploadfile")
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		try {
			logger.debug("enter /upload/uploadfile");
			String randomFilename = "";
			Random rand = new Random();// 生成随机数
			int random = rand.nextInt();
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

			logger.debug("exit /upload/uploadfile");
			return randomFilename;
//			return FILE_ROOT_URL + randomFilename;
		} catch (Exception ex) {
			logger.debug(ex.getMessage(), ex);
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

	
	@ResponseBody
	@RequestMapping(value = "/uploadbase64")
	public String uploadBase64(@RequestParam(value = "file", required = true) String content,
			HttpServletRequest request) {
		try {
			logger.debug("enter /upload/uploadbase64");
			String randomFilename = "";
			Random rand = new Random();// 生成随机数
			int random = rand.nextInt();
			String now = format.format(new Date()) + "_";
			randomFilename = now
					+ String.valueOf(random > 0 ? random : (-1) * random)
					+ ".png";
			File dir = new File(FILE_ROOT_PATH);
			if (!dir.exists()){
				dir.mkdirs();
			}
			File targetFile = new File(FILE_ROOT_PATH, randomFilename);
			int beginIndex = content.indexOf("base64,")+7;
			byte[] b = Base64.decodeBase64(content.substring(beginIndex));
			// 保存
			
			try {
				FileOutputStream out = new FileOutputStream(targetFile);
				out.write(b);
				out.close();
			} catch (Exception e) {
				throw e;
			}

			logger.debug("exit /upload/uploadfile");
			return randomFilename;
//			return FILE_ROOT_URL + randomFilename;
		} catch (Exception ex) {
			logger.debug(ex.getMessage(), ex);
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

}
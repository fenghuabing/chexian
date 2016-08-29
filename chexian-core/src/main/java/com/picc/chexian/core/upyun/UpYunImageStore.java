/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picc.chexian.core.upyun;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.picc.chexian.core.service.BaseService;
import com.picc.chexian.core.upyun.UpYun.FolderItem;

/**
 * 只用来存取图片
 * <p/>
 * * <b>upyun图片存储/访问策略,目前为简化使用策略4</b>
 * 所有图片按照绑定空间域名+相对路径+唯一hash值来访问,hash值用image对应实体的唯一id和实体下唯一的imageName生成，访问时可随时生成图片url
 * </b>
 * @see UpYunFileStore
 */
@UpYunImage
@Service
public class UpYunImageStore extends BaseService {


    private static final String SCHEME = "http://";

    //TODO store those information in config
    //图片空间
    private static final String BUCKET_NAME = "xiguaimage";

    //空间操作人
    private static final String USER_NAME = "zhangtong";

    //操作人密码
    private static final String USER_PWD = "zhangtong";

    //绑定的域名
    public static final String URL = SCHEME + BUCKET_NAME
            + ".b0.upaiyun.com/";

    private static UpYun upyun;

    static {
        // 初始化空间
        upyun = new UpYun(BUCKET_NAME, USER_NAME, USER_PWD);
    }


    /**
     * store an image
     *
     * @param imageName
     * @param imagePath
     * @return
     */
    public boolean store(String imageName, String imagePath) {
        try {
            // 本地待上传的图片文件
            File file = new File(imagePath);
            if (!file.isFile()) {
                logger.warn("file not exist!");
            }

            boolean result = upyun.writeFile(imageName, file, true);
            logger.debug("store image.[imageName=" + imageName + "][result=" + result + "]");
            return result;
        } catch (IOException ex) {
            logger.warn("fail to upload image.[imageName=" + imageName + "][imagePath=" + imagePath + "]", ex);
        }
        return false;
    }

    public boolean store(File tempImageFile, String hashImageName) {
        try {
            // 本地待上传的图片文件
            if (!tempImageFile.isFile() || !tempImageFile.canRead() || !tempImageFile.exists()) {
                logger.warn("file not exist!");
            }

            boolean result = upyun.writeFile(hashImageName, tempImageFile, true);
            return result;
        } catch (IOException ex) {
            logger.warn("fail to upload image.[imageName=" + hashImageName + "][imagePath=" + hashImageName + "]", ex);
        }
        return false;
    }


    /**
     * store an image by stream
     *
     * @param imageName
     * @param imageStream
     * @return
     */
    public boolean store(String imageName, InputStream imageStream) {
        try {
            boolean result = upyun.writeFile(imageName, imageStream, true);
            logger.debug("store image by stream.[imageName=" + imageName + "][result=" + result + "]");
            return result;
        } catch (IOException ioe) {
            logger.warn("fail to upload image by stream.[imageName=" + imageName + "]", ioe);
        }

        return false;
    }

    /**
     * get image uri
     *
     * @param imageName
     * @return
     */
    public String getURI(String imageName) {
        return URL.concat(imageName);
    }

    /**
     * delete an image
     *
     * @param imageName
     * @return
     */
    public boolean delete(String imageName) {
        boolean result = upyun.deleteFile(imageName);
        logger.debug("delete image.[imageName=" + imageName + "][result=" + result + "]");
        return result;
    }
    
    public List<FolderItem> readDir(String path){
    	return upyun.readDir(path);
    }
    
}
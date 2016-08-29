/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picc.chexian.core.upyun;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.picc.chexian.core.service.BaseService;

/**
 * 只用来存取文件
 *
 * @see UpYunImageStore
 */
@Service
@UpYunFile
public class UpYunFileStore extends BaseService {


    //TODO store those information in config
    //文件空间
    private static final String BUCKET_NAME = "rmbboxfile";

    //空间操作人
    private static final String USER_NAME = "rmbboxfile";

    //操作人密码
    private static final String USER_PWD = "rmbboxfile";

    //绑定的域名
    private static final String URL = "http://" + BUCKET_NAME
            + ".b0.upaiyun.com/";


    private static UpYun upyun;

    static {
        upyun = new UpYun(BUCKET_NAME, USER_NAME, USER_PWD);
    }

    /**
     * store a file by file path
     *
     * @param fileName
     * @param filePath
     * @return
     */
    public boolean store(String fileName, String filePath) {
        try {
            logger.debug("store file.[fileName=" + fileName + "][filePath=" + filePath + "]");
            // 本地待上传的文件
            File file = new File(filePath);

            if (!file.isFile()) {
                logger.warn("file not exist!");
            }

            // 上传文件，并自动创建父级目录（最多10级）
            return upyun.writeFile(fileName, file, true);
        } catch (IOException ex) {
            logger.warn("fail to upload file.[fileName=" + fileName + "][filePath=" + filePath + "]", ex);
        }
        return false;
    }

    /**
     * store a file by input stream
     *
     * @param fileName
     * @param fileStream
     * @return
     */
    public boolean store(String fileName, InputStream fileStream) {
        try {
            logger.debug("store file by stream.[fileName=" + fileName + "]");
            return upyun.writeFile(fileName, fileStream, true);
        } catch (IOException ioe) {
            logger.warn("fail to upload file by stream.[fileName=" + fileName + "]", ioe);
        }

        return false;
    }

    /**
     * get file uri
     *
     * @param fileName
     * @return
     */
    public String getURI(String fileName) {
        return URL.concat(fileName);
    }

    /**
     * delete and file
     *
     * @param fileName
     * @return
     */
    public boolean delete(String fileName) {
        logger.debug("delete file.[fileName=" + fileName + "]");
        return upyun.deleteFile(fileName);
    }
}

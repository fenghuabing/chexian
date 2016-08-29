package com.picc.chexian.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.picc.chexian.core.enums.SMSType;
import com.picc.chexian.core.service.BaseService;

@Service
public class SMSService extends BaseService {
	public static final String SMS_WC_URL = "http://utf8.sms.webchinese.cn/";
	public static final String SMS_WC_UID = "zhangtongwireless";
	public static final String SMS_WC_KEY = "4e40d5a2077f4584278b";

	public static final String SMS_CL_URL = "http://222.73.117.156/msg/HttpBatchSendSM";
	public static final String SMS_CL_UID = "xiguamei";
	public static final String SMS_CL_PSWD = "Xiguamei123";

//	public static final String SMS_SHORTNAME = "【西瓜妹】";
	public static final String SMS_SHORTNAME = "";

	Properties props;
	
	@PostConstruct
	public void init(){
		try {
			InputStream fis = getClass().getClassLoader()
					.getResourceAsStream("sms.properties");
			props = new Properties();
			props.load(fis);
		} catch (IOException e) {
			logger.error("ERROR:", e);
		}
	}
	
    @Async
    public void sendMessage(SMSType type, String mobile, String... contents) throws IOException {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("com.ipeaksoft.sms.messages", Locale.CHINESE);
//        String contentTemplate = resourceBundle.getString(type.getKey());
        String contentTemplate = props.getProperty(type.getKey());

        switch (type) {
            case CONFIRM_AUTHENTICATE_MOBILE:
                sendSMS(mobile, String.format(contentTemplate, SMS_SHORTNAME, contents[0]));
                
//                sendSMS(mobile, String.format("客户你好，你的验证码为：%s，5分钟内有效，请完成注册。", contents[0]));
                break;
            default:
                //do nothing
        }
    }


    /**
     * @param destination Phone number
     * @param type        smsType
     * @param content     Actual content sent to user as sms
     * @throws IOException 
     */
    private void sendSMS(String destination, String content) throws IOException {
        HttpPost post = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            logger.debug("Sending messge. [destination=" + destination + "][content=" + content + "]");
            HttpClient httpClient = new DefaultHttpClient();
//            post = new HttpPost(SMS_WC_URL);
            post = new HttpPost(SMS_CL_URL);
            post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(initnameValuePairs(destination, content), "UTF-8");
            post.setEntity(formEntity);
            HttpResponse resp = httpClient.execute(post);
            //记录短信平台返回
            resp.getEntity().writeTo(baos);
            logger.debug("Message sent. [destination=" + destination + "][content=" + content + "][responseCode=" + resp.getStatusLine().getStatusCode() + "][response=" + baos.toString() + "]");
        } catch (UnsupportedEncodingException ex) {
        	logger.info("Exception when sending short message", ex);
        	throw ex;
        } catch (ClientProtocolException ex) {
            logger.info("Exception when sending short message", ex);
            throw ex;
        } catch (IOException ex) {
            logger.info("Exception when sending short message", ex);
            throw ex;
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
    }

    private CopyOnWriteArrayList<NameValuePair> initnameValuePairs(String destination, String... content) {
//        return initWebChineseValuePairs(destination, content);
        return initCLValuePairs(destination, content);
    }

    // 配置 webChinese 参数
    private CopyOnWriteArrayList<NameValuePair> initWebChineseValuePairs(String destination, String... content) {
        CopyOnWriteArrayList<NameValuePair> nameValuePairs = new CopyOnWriteArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("Uid", SMS_WC_UID));
        nameValuePairs.add(new BasicNameValuePair("Key", SMS_WC_KEY));
        nameValuePairs.add(new BasicNameValuePair("smsMob", destination));
        nameValuePairs.add(new BasicNameValuePair("smsText", content[0]));
        return nameValuePairs;
    }

    // 配置 创蓝cl2009 参数
    private CopyOnWriteArrayList<NameValuePair> initCLValuePairs(String destination, String... content) {
        CopyOnWriteArrayList<NameValuePair> nameValuePairs = new CopyOnWriteArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("account", SMS_CL_UID));
        nameValuePairs.add(new BasicNameValuePair("pswd", SMS_CL_PSWD));
        nameValuePairs.add(new BasicNameValuePair("mobile", destination));
        nameValuePairs.add(new BasicNameValuePair("msg", content[0]));
        nameValuePairs.add(new BasicNameValuePair("needstatus", "true"));
        return nameValuePairs;
    }

}

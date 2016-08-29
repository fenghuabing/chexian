/**
 * 
 */
package com.picc.chexian.core.spring;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 主要为非web环境获得bean时使用，会自动初始化spring
 * @author liyazi
 * @date 2015年6月15日
 */
public class BeanFactory implements ApplicationContextAware {

    private static volatile ApplicationContext applicationContext;

    private static volatile boolean initByBeanFactory = false;

    private static final Log logger = LogFactory.getLog(BeanFactory.class);

    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        logger.info("init bean factory via setter.");
        if ((applicationContext != null) && (applicationContext != ac)) {
            logger.info("found exist applicationContext while setting, old one:"
                    + applicationContext + ", new one:" + ac);
        }
        applicationContext = ac;
    }

    public static final void init() {
        if (applicationContext == null) {
            synchronized (BeanFactory.class) {
                if (applicationContext == null) {
                    logger.warn("init bean factory self.");
                    initByBeanFactory = true;
                    applicationContext = new ClassPathXmlApplicationContext(
                            "classpath*:spring/*.xml");
                }
            }
        }
    }

    public static final <T> T getBean(Class<T> clazz) {
        init();
        return applicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T getBean(String beanName) {
        init();
        return (T) applicationContext.getBean(beanName);
    }

    public static final <T> Map<String, T> getBeansOfType(Class<T> type) {
        init();
        return applicationContext.getBeansOfType(type);
    }

    /**
     * @return 是否被beanFactory初始化
     */
    public static final boolean initByBeanFactory() {
        return initByBeanFactory;
    }

    /**
     * @return spring是否被初始化
     */
    public static final boolean inited() {
        return applicationContext != null;
    }

}

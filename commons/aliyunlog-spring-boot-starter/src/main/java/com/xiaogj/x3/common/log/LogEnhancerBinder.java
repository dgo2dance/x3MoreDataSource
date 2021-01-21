package com.xiaogj.x3.common.log;

import com.xiaogj.x3.common.log.enhancer.DefaultLog4j2Enhancer;
import com.xiaogj.x3.common.log.enhancer.DefaultLogbackEnhancer;
import com.xiaogj.x3.common.log.enhancer.LogEnhancer;
import com.xiaogj.x3.common.log.utils.LogEnvUtils;

/**
 * @description:
 * @date: 2020/9/08 9:26
 * @author: Frank
 * @version: 1.0
 */
public class LogEnhancerBinder {

    private volatile static LogEnhancer INSTANCE;

    private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();


    public static void bindClassLoader(ClassLoader classLoader) {

        if(INSTANCE == null) {

            synchronized (LogEnhancerBinder.class) {

                if(INSTANCE == null) {
                    LogEnhancerBinder.classLoader = classLoader;
                }
            }
        }
    }

    public static LogEnhancer getInstance() {

        if(INSTANCE == null) {

            synchronized (LogEnhancerBinder.class) {

                if(INSTANCE == null) {

                    INSTANCE = doInit(classLoader);
                }

            }
        }

        return INSTANCE;
    }

    private static LogEnhancer doInit(ClassLoader classLoader) {

        LogEnhancer enhancer = null;
        if(LogEnvUtils.isLog4j2Usable(classLoader)) {
            enhancer = new DefaultLog4j2Enhancer(classLoader);
        } else if(LogEnvUtils.isLogbackUsable(classLoader)) {
            enhancer = new DefaultLogbackEnhancer(classLoader);
        } else {
            throw new IllegalStateException("No applicable logging system found");
        }

        return enhancer;
    }

}

package com.xiaogj.x3.common.log.config;

import org.springframework.core.env.ConfigurableEnvironment;

import java.lang.reflect.InvocationTargetException;

/**
 * @description: Config loader
 * @date: 2020/8/13 9:26
 * @author: Frank
 * @version: 1.0
 */

public interface LogAppenderConfigLoader {

    /**
     * @description: 从spring environment获取配置 <br>
     * @version: 1.0 <br>
     * @date: 2020/9/8 14:36 <br>
     * @author: Frank <br>
     *
     * @param environment
     * @return cn.keking.common.log.config.LogAppenderConfig
     */
    LogAppenderConfig load(ConfigurableEnvironment environment) throws InvocationTargetException, IllegalAccessException;

}

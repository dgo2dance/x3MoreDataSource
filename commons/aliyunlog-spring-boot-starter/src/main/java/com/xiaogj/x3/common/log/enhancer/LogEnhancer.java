package com.xiaogj.x3.common.log.enhancer;

import com.xiaogj.x3.common.log.config.LogAppenderConfig;

/**
 * @description: 日志增强接口
 * @date: 2020/8/13 9:26
 * @author: Frank
 * @version: 1.0
 */
public interface LogEnhancer {

    /**
     * @description: 判断是否已经绑定阿里云SDK <br>
     * @version: 1.0 <br>
     * @date: 2020/9/8 14:38 <br>
     * @author: Frank <br>
     * 
     * @param 
     * @return boolean
     */ 
    boolean alreadyBound();

    /**
     * @description: 接入阿里云日志服务 <br>
     * @version: 1.0 <br>
     * @date: 2020/9/8 14:35 <br>
     * @author: Frank <br>
     * 
     * @param config
     * @return void
     */ 
    void enhance(LogAppenderConfig config);

    void cleanUp();
}

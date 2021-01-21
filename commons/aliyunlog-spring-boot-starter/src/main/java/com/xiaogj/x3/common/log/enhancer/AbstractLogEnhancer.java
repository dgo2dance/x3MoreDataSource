package com.xiaogj.x3.common.log.enhancer;

import com.xiaogj.x3.common.log.config.LogAppenderConfig;

/**
 * @description: Abstract log enhancer
 * @date: 2020/8/13 9:26
 * @author: Frank
 * @version: 1.0
 */

public abstract class AbstractLogEnhancer implements LogEnhancer {

    @Override
    public void enhance(LogAppenderConfig config) {

        try {
            if(!hasBeanEnhanced()) {
                doEnhance(config);
            }
        } catch (Exception e) {
            handlerEnhanceError(e);
        } finally {
            afterEnhance();
        }
    }

    protected abstract boolean hasBeanEnhanced();

    protected abstract void doEnhance(LogAppenderConfig config);

    protected abstract void afterEnhance();

    protected void handlerEnhanceError(Exception exception) {

        cleanUp();
        exception.printStackTrace(System.err);
        throw new IllegalStateException(exception);
    }
}

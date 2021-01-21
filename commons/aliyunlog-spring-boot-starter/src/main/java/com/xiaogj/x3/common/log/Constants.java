package com.xiaogj.x3.common.log;

/**
 * @description:  常量类
 * @date: 2020/9/08 9:26
 * @author: Frank
 * @version: 1.0
 */
public interface Constants {

    String LOG_CONFIG_PREFIX = "aliyun.log";

    String LOG_PROJECT_CONFIG_PREFIX = LOG_CONFIG_PREFIX + ".project";

    String LOG_PRODUCER_CONFIG_PREFIX = LOG_CONFIG_PREFIX + ".producer";

    String LOG_CONFIG_LOGGER_FILTER = "logger.filter";

    String DEFAULT_ROOT_LOGGER_NAME = "ROOT";
}

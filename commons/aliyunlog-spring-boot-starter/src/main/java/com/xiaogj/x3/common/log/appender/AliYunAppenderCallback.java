package com.xiaogj.x3.common.log.appender;

import com.xiaogj.x3.common.log.config.LogAppenderConfig;
import com.aliyun.openservices.aliyun.log.producer.Callback;
import com.aliyun.openservices.aliyun.log.producer.Result;
import com.aliyun.openservices.log.common.LogItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @description: Common Aliyun appender producer callback
 * @date: 2020/8/13 9:26
 * @author: Frank
 * @version: 1.0
 */

public class AliYunAppenderCallback implements Callback {

    private final Logger logger = LoggerFactory.getLogger(AliYunAppenderCallback.class);

    private final String project;

    private final String logStore;

    private final String topic;

    private final String source;

    private final List<LogItem> logItems;

    public AliYunAppenderCallback(LogAppenderConfig config, List<LogItem> logItems) {
        super();
        this.project = config.getProjectConfig().getName();
        this.logStore = config.getProjectConfig().getLogstore();
        this.topic = config.getTopic();
        this.source = config.getSource();
        this.logItems = logItems;
    }

    @Override
    public void onCompletion(Result result) {

        if (!result.isSuccessful()) {
            logger.error("Failed to putLogs. project=" + project + " logStore=" + logStore +
                    " topic=" + topic + " source=" + source + " logItems=" + logItems, result);
        }

    }
}

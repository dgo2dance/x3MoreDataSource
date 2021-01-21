package com.xiaogj.x3.common.log.appender.logback;

import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import com.aliyun.openservices.aliyun.log.producer.LogProducer;
import com.aliyun.openservices.aliyun.log.producer.ProducerConfig;
import com.aliyun.openservices.aliyun.log.producer.ProjectConfigs;
import com.aliyun.openservices.aliyun.log.producer.errors.LogSizeTooLargeException;
import com.aliyun.openservices.aliyun.log.producer.errors.ProducerException;
import com.aliyun.openservices.aliyun.log.producer.errors.TimeoutException;
import com.aliyun.openservices.log.common.LogItem;
import com.xiaogj.x3.common.log.appender.AliYunAppenderCallback;
import com.xiaogj.x3.common.log.config.LogAppenderConfig;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: LogbackAliYunAppender
 * @date: 2020/8/13 9:26
 * @author: Frank
 * @version: 1.0
 */
public class LogbackAliYunAppender<E> extends UnsynchronizedAppenderBase<E> {

    private Encoder<E> encoder;

    protected LogAppenderConfig config;

    private LogProducer producer;

    private DateTimeFormatter formatter;

    public LogbackAliYunAppender() {
    }

    public LogbackAliYunAppender(LogAppenderConfig logAppenderConfig) {

        this.config = logAppenderConfig;
    }

    @Override
    public void start() {
        try {
            doStart();
        } catch (Exception e) {
            addError("Failed to start LogbackAliYunAppender.", e);
        }
    }

    private void doStart() {

        formatter = DateTimeFormat.forPattern(config.getTimeFormat()).withZone(DateTimeZone.forID(config.getTimeZone()));
        if (config.getProducerConfig() == null) {
            ProjectConfigs projectConfigs = new ProjectConfigs();
            projectConfigs.put(config.getProjectConfig().buildProjectConfig());
            ProducerConfig producerConfig = new ProducerConfig(projectConfigs);
            config.setProducerConfig(producerConfig);
        }
        producer = new LogProducer(config.getProducerConfig());

        super.start();
    }

    @Override
    public void stop() {
        try {
            doStop();
        } catch (Exception e) {
            addError("Failed to stop LogbackAliYunAppender.", e);
        }
    }

    private void doStop() throws InterruptedException, ProducerException {
        if (!isStarted()) {
            return;
        }
        super.stop();
        if (producer != null) {
            producer.close();
        }
    }

    @Override
    public void append(E eventObject) {

        //init Event Object
        if (!(eventObject instanceof LoggingEvent)) {
            return;
        }
        LoggingEvent event = (LoggingEvent) eventObject;

        List<LogItem> logItems = new ArrayList<LogItem>();
        LogItem item = new LogItem();

        logItems.add(item);
        item.SetTime((int) (event.getTimeStamp() / 1000));

        DateTime dateTime = new DateTime(event.getTimeStamp());
        item.PushBack("time", dateTime.toString(formatter));
        item.PushBack("level", event.getLevel().toString());
        item.PushBack("thread", event.getThreadName());




        item.PushBack("status", event.getLevel().toString());
//        item.PushBack("serverName", event.getThreadName());
//        item.PushBack("companyId", "111111");
//        item.PushBack("companyName", "XXXXXX");



        StackTraceElement[] caller = event.getCallerData();
        if (caller != null && caller.length > 0) {
            item.PushBack("location", caller[0].toString());
        }

        String message = event.getFormattedMessage();
        item.PushBack("message", message);

        IThrowableProxy iThrowableProxy = event.getThrowableProxy();
        if (iThrowableProxy != null) {
            String throwable = getExceptionInfo(iThrowableProxy);
            throwable += fullDump(event.getThrowableProxy().getStackTraceElementProxyArray());
            item.PushBack("throwable", throwable);
        }

        if (this.encoder != null) {
            item.PushBack("log", new String(this.encoder.encode(eventObject)));
        }

        try {
            producer.send(
                    config.getProjectConfig().getName(),
                    config.getProjectConfig().getLogstore(),
                    config.getTopic(),
                    null,
                    logItems,
                    new AliYunAppenderCallback(config, logItems));
        } catch (InterruptedException e) {
            addError("The current thread has been interrupted during send logs.");
        } catch (Exception e) {
            if (e instanceof LogSizeTooLargeException) {
                addError("The size of log is larger than the maximum allowable size, e={}", e);
            } else if (e instanceof TimeoutException) {
                addError("The time taken for allocating memory for the logs has surpassed., e={}", e);
            } else {
                addError("Failed to send log, e=", e);
            }
        }
    }

    private String getExceptionInfo(IThrowableProxy iThrowableProxy) {
        String s = iThrowableProxy.getClassName();
        String message = iThrowableProxy.getMessage();
        return (message != null) ? (s + ": " + message) : s;
    }

    private String fullDump(StackTraceElementProxy[] stackTraceElementProxyArray) {
        StringBuilder builder = new StringBuilder();
        for (StackTraceElementProxy step : stackTraceElementProxyArray) {
            builder.append(CoreConstants.LINE_SEPARATOR);
            String string = step.toString();
            builder.append(CoreConstants.TAB).append(string);
            ThrowableProxyUtil.subjoinPackagingData(builder, step);
        }
        return builder.toString();
    }

    public LogAppenderConfig getConfig() {
        return config;
    }

    public void setConfig(LogAppenderConfig config) {
        this.config = config;
    }

    public void setEncoder(Encoder<E> encoder) {
        this.encoder = encoder;
    }
}

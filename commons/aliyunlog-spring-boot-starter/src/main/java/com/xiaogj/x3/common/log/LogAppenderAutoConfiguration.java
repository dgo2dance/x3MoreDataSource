package com.xiaogj.x3.common.log;
import com.xiaogj.x3.common.log.config.CommonLogAppenderConfigLoader;
import com.xiaogj.x3.common.log.config.LogAppenderConfig;
import com.xiaogj.x3.common.log.config.LogAppenderConfigLoader;
import com.xiaogj.x3.common.log.enhancer.LogEnhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;


/**
 * @description:  增强配置入口
 * @date: 2020/9/08 9:26
 * @author: Frank
 * @version: 1.0
 */
@Configuration
public class LogAppenderAutoConfiguration implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(LogAppenderAutoConfiguration.class);
    @Override
    public void setEnvironment(Environment environment) {

        logger.info("X3 aliyun log appender has been integrated into system");

        ConfigurableEnvironment env = (ConfigurableEnvironment)environment;

        // load config
        LogAppenderConfigLoader loader = new CommonLogAppenderConfigLoader();
        final LogAppenderConfig config;
        try {
            config = loader.load(env);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load log appender config", e);
        }

        if(!config.isEnable()) {
            return;
        }

        // init logEnhancer
        ClassLoader classLoader = env.getClass().getClassLoader();
        LogEnhancerBinder.bindClassLoader(classLoader);
        LogEnhancer logEnhancer = LogEnhancerBinder.getInstance();

        // do nothing if aliyun appender has been bound
        if(!logEnhancer.alreadyBound()) {

            logEnhancer.enhance(config);
            logger.info("X3 aliyun log appender has been successfully initialized");
        }
    }


}

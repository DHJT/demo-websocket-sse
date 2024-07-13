package tech.dhjt.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步线程异常处理器
 * UncaughtExceptionHandler
 */
public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    public static final Logger logger = LoggerFactory.getLogger(CustomUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("thread [{}] occur exception", t.getName(), e);
    }

}

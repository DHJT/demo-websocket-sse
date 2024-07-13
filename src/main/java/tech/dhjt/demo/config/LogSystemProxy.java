package tech.dhjt.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.PrintStream;
import java.util.function.Consumer;

/**
 * inspired by [解决logback不能输出e.printStackTrace()的日志问题](https://www.jianshu.com/p/6e258a7d1026)
 */
@Configuration
public class LogSystemProxy {

    private final static Logger logger = LoggerFactory.getLogger("proxy.system.log");

    @PostConstruct
    public void initProxy() {
        logger.debug("LogSystemProxy init .....");
        System.setOut(getLoggerProxy(StdType.OUT));
        System.setErr(getLoggerProxy(StdType.ERR));
    }

    private enum StdType {
        OUT(System.out, logger::info),
        ERR(System.err, logger::error),
        ;
        PrintStream stream;
        Consumer<String> consumer;

        StdType(PrintStream stream, Consumer<String> consumer) {
            this.stream = stream;
            this.consumer = consumer;
        }
    }

    private PrintStream getLoggerProxy(StdType stdType) {
        return new PrintStream(stdType.stream) {
            @Override
            public void print(String s) {
                stdType.stream.print(s);
                stdType.consumer.accept(s);
            }
        };
    }
}

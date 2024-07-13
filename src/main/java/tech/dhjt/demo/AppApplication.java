package tech.dhjt.demo;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;
import io.prometheus.client.spring.web.EnablePrometheusTiming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

@EnableAsync
@EnableScheduling
//@EnablePrometheusEndpoint
@EnablePrometheusTiming
//@EnableSpringBootMetricsCollector
@SpringBootApplication
public class AppApplication {

    private static final Logger logger = LoggerFactory.getLogger(AppApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Scheduled(fixedDelay = 5000)
    public void test () {
        boolean b = new Random().nextBoolean();
        Exception e = new Exception("test exception!!!");
        e.printStackTrace();
        if (b) {
            logger.error("error ocurd");
            System.err.println("test error;");
        } else {
            logger.warn("warn fired");
            System.out.println("test out;");
        }
        logger.info("info fired");
    }

}

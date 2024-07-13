package tech.dhjt.demo.common;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.distribution.Histogram;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author DHJT
 */
@Component
public class PrometheusCustomMonitor {

    /**
     * 累加计数
     * 比如：订单数的累加
     */
    private Counter counter;
    /**
     * 摘要
     * 记录最大值，总数，平均值和累加计数。
     * 比如：订单金额的统计
     */
    private DistributionSummary summary;
    /**
     * 测量值
     * 可变动的数值
     * 比如：进行中的订单数
     */
    private AtomicInteger gauge;
    /**
     * 直方图、柱状图
     * 不同分端的数字
     * 暂不处理
     */
    private Histogram histogram;

    @Autowired
    private PrometheusMeterRegistry registry;

    @PostConstruct
    private void init() {
        counter = registry.counter("test_counter", "order", "test-svc");
        summary = registry.summary("test_summary", "amount", "test-svc");
        gauge = registry.gauge("test_gauge",new AtomicInteger(0));
    }

    public Counter getCounter() {
        return counter;
    }

    public DistributionSummary getSummary() {
        return summary;
    }
    public AtomicInteger getGauge() {
        return gauge;
    }

}

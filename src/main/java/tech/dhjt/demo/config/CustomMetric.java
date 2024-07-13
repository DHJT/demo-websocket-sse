package tech.dhjt.demo.config;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义指标
 * @author DHJT
 */
@Component
public class CustomMetric implements MeterBinder {

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        Gauge.builder("exception.error.count", atomicInteger, c -> Double.valueOf(String.valueOf(c)))
                .description("exception of service error meter")
                .register(meterRegistry);
    }

}

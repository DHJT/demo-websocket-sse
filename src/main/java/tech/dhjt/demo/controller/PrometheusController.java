package tech.dhjt.demo.controller;

import io.micrometer.core.annotation.Timed;
import io.prometheus.client.spring.web.PrometheusTimeMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.dhjt.demo.common.PrometheusCustomMonitor;

import javax.annotation.Resource;

/**
 * Prometheus 业务指标相关
 * @author DHJT
 */
@RestController
@RequestMapping("/extends")
public class PrometheusController {

    @Resource
    private PrometheusCustomMonitor monitor;

    @RequestMapping("/order")
    @PrometheusTimeMethod(name = "my_controller_path_duration_seconds", help = "Some helpful info here")
    @Timed(extraTags = { "demo", "orderTime" })
    public String order(int amount) {
        // 订单总数累加
        monitor.getCounter().increment();
        // 最新订单金额
        monitor.getGauge().set(amount);
        // 增加的订单金额
        monitor.getSummary().record(amount);
        return "下单成功";
    }

}

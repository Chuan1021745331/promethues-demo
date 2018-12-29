package com.xb.prometheus.intercepter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author JingChuan
 * @since 2018/12/29 17:37
 */
@Component
public class RequestCounterInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(RequestCounterInterceptor.class);

    @Autowired
    private PrometheusMeterRegistry prometheusRegistry;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestUrl = request.getRequestURL().toString();
        String method = request.getMethod();
        int status = response.getStatus();
        Counter counter = Metrics.counter("test.request.total", "method", method, "url", requestUrl, "status", String.valueOf(status));
        CompositeMeterRegistry globalRegistry = Metrics.globalRegistry;
        Set<MeterRegistry> registries = globalRegistry.getRegistries();
        for(MeterRegistry registry : registries){
            logger.info("global: {}",registry.toString());
            logger.info("auto: {}",prometheusRegistry.toString());
            boolean compareResult = registry == prometheusRegistry;
            logger.info(compareResult+"");
        }
        counter.increment();
        logger.info("measure:  {}",counter.measure());
        super.afterCompletion(request, response, handler, ex);
    }
}

package com.xb.prometheus.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *
 * </p>
 *
 * @author JingChuan
 * @since 2018/12/29 16:50
 */
@RestController
@RequestMapping("/test")
public class PrometheusController {


    @GetMapping("/get")
    public String testGet(HttpServletRequest request, HttpServletResponse response){
        return "test post success";
    }

    @PostMapping("/post")
    public String testPost(){
        return "test post success";
    }
}

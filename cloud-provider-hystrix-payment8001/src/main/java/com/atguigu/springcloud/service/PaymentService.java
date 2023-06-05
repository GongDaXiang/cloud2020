package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhangHao
 */
@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "线程池: "+Thread.currentThread().getName()+" paymentInfo_OK,id: "+id+"\tlalala";

    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",
            commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
            value = "3000")
            })
    public String paymentInfo_Timeout(Integer id){
        int time = 5;
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池: "+Thread.currentThread().getName()+" paymentInfo_Timeout,id: "+id+"\tlalala,耗时"+time+"秒";
    }

    public String payment_TimeoutHandler(Integer id){
        return "线程池"+Thread.currentThread().getName()+"系统繁忙,请稍后试试"+id;
    }
}

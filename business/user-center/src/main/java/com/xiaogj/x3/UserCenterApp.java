package com.xiaogj.x3;

import com.xiaogj.x3.search.annotation.EnableSearchClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 作者 owen E-mail: 624191343@qq.com
 */
@EnableSearchClient
@EnableTransactionManagement
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class UserCenterApp implements CommandLineRunner {

    public static void main(String[] args) {


        SpringApplication.run(UserCenterApp.class, args);
    }

    @Override
    public void run(String... args) {
        MDC.put("status", "ERROR");
        MDC.put("companyId", "1");
        MDC.put("companyName", "机构名称");
//        ;status={},companyid={},companyName={}", "ERROR", 1, "机构名称"
        log.error("error test aliyun log center XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }
}

package test.ivan.vote.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableEurekaClient
@EnableFeignClients
@EnableConfigurationProperties
@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"test.ivan.vote.admin"})
@MapperScan("test.ivan.vote.admin.**.mapper")
public class ElectionVoteAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectionVoteAdminApplication.class, args);
    }

}

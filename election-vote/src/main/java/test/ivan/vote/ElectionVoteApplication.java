package test.ivan.vote;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@EnableEurekaClient
@EnableAsync
@EnableConfigurationProperties
@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"test.ivan.vote"})
@MapperScan("test.ivan.vote.**.mapper")
public class ElectionVoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectionVoteApplication.class, args);
    }

}

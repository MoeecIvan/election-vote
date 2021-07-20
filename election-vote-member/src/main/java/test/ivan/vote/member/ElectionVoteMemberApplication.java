package test.ivan.vote.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableEurekaClient
@EnableConfigurationProperties
@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"test.ivan.vote.member", "test.ivan.vote.common"})
@MapperScan("test.ivan.vote.member.**.mapper")
public class ElectionVoteMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectionVoteMemberApplication.class, args);
    }

}

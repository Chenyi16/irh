package top.imuster.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: MallProviderApplication
 * @Description: MallProvider启动类
 * @author: hmr
 * @date: 2019/11/25 10:32
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MallProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallProviderApplication.class, args);
    }
}

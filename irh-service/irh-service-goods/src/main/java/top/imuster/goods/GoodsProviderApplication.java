package top.imuster.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: GoodsProviderApplication
 * @Description: TODO
 * @author: lpf
 * @date: 2019/12/1 15:21
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
        "top.imuster.common.core",
        "top.imuster.goods",
        "top.imuster.user.api.service"})
public class GoodsProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsProviderApplication.class, args);
    }
}

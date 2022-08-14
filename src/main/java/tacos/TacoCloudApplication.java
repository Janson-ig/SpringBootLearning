package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication //spring boot应用
public class TacoCloudApplication implements WebMvcConfigurer {//实现WebMvcConfigurer接口并覆盖addViewController方法

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args); // 运行应用;run()传递两个参数：1.配置类，2.命令行参数
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("home");
    }
//采⽤扩展已有配置类的⽅式能够避免创建新的配置类，从⽽减少项⽬中制件的数量
// 但是更倾向于为每种配置（Web、数据、安全等）创建新的配置类，这样能够保持应⽤的引导配置类尽可能地整洁和简单。
}

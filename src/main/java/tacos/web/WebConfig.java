package tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//视图控制器：只将请求转发到视图，而不做其他事情的控制器
//代替原HomeController
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    //addViewControllers()方法会接受一个ViewControllerRegistry对象，使用它注册一个或多个视图控制器
    public void addViewControllers(ViewControllerRegistry registry) {
        //针对“/”路径执行GET请求。这个方法返回ViewControllerRegistration对象，基于该对象调用setViewName()方法——指明当请求“/”的时候转发到“home”视图上
        registry.addViewController("/").setViewName("home");
    }
}
/*
package tacos.web;

        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.GetMapping;

@Controller //⇽--- 控制器
public class HomeController {
    @GetMapping("/") // ⇽--- 处理对根路径“/”的请求
    public String home() {
        return "home"; //⇽--- 返回视图名
    }
}*/
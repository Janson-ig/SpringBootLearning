package tacos.web;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import tacos.Order;

@Slf4j
@Controller
@RequestMapping("/orders") //处理路径以“/orders”开头的请求
public class OrderController {
    @GetMapping("/current") //指定orderForm方法，处理针对“/orders/current”的HTTP GET请求
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    //@Valid注释告诉Spring MVC对Taco对象进行校验，校验时机是在它绑定完表单数据后；若存在校验错误，错误细节捕捉到Errors对象中
    public String processOrder(@Valid Order order, Errors errors) {
        //hasErrors方法判断是否有校验错误
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("Order submitted: " + order);
        return "redirect:/";
    }
}

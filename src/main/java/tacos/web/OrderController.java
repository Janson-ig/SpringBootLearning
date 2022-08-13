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
    public String processOrder(Order order) {
        log.info("Order submitted: " + order);
        return "redirect:/";
    }
}
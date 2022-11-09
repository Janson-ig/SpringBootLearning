package tacos.web;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.TacoOrder;
import tacos.data.OrderRepository;

@Controller
@RequestMapping("/orders") //处理路径以“/orders”开头的请求
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepo;
    public OrderController(OrderRepository orderRepo){
        this.orderRepo = orderRepo;
    }
    @GetMapping("/current") //指定orderForm方法，处理针对“/orders/current”的HTTP GET请求
    public String orderForm(){
    //public String orderForm(Model model) {
        //model.addAttribute("order", new Order());
        return "orderForm";
    }
    @PostMapping
    /**
     * @Valid注释告诉Spring MVC对Taco对象进行校验，校验时机是在它绑定完表单数据后；若存在校验错误，错误细节捕捉到Errors对象中
     * 通过表单提交的Order对象（session中持有的Object对象）通过注入的OrderRepository的save()方法进行保存
     * hasErrors方法判断是否有校验错误
     */
    public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepo.save(tacoOrder);
        //清理订单，重置session
        sessionStatus.setComplete();
        //log.info("Order submitted: " + order);
        return "redirect:/";
    }
}

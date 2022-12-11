package tacos.web;

import javax.validation.Valid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import tacos.TacoOrder;
import tacos.User;
import tacos.data.OrderRepository;
import tacos.data.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/orders") //处理路径以“/orders”开头的请求
@SessionAttributes("order")
/*
@ConfigurationProperties注解。
它的prefix属性设置成了taco.orders，这意味着当设置pageSize的时候，我们需要使⽤名为taco.orders.pageSize的配置属性。
 */
//@ConfigurationProperties(prefix="taco.orders")
public class OrderController {

    private OrderRepository orderRepo;
    private OrderProps props;
    public OrderController(OrderRepository orderRepo){
        this.orderRepo = orderRepo;
        this.props = props;
    }
    @GetMapping("/current") //指定orderForm方法，处理针对“/orders/current”的HTTP GET请求
    public String orderForm(){
    //public String orderForm(Model model) {
        //model.addAttribute("order", new Order());
        return "orderForm";
    }

    /**
     * @Valid注释告诉Spring MVC对Taco对象进行校验，校验时机是在它绑定完表单数据后；若存在校验错误，错误细节捕捉到Errors对象中
     * 通过表单提交的Order对象（session中持有的Object对象）通过注入的OrderRepository的save()方法进行保存
     * hasErrors方法判断是否有校验错误
     */
    @PostMapping
    public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        tacoOrder.setUser(user);

        orderRepo.save(tacoOrder);
        //清理订单，重置session
        sessionStatus.setComplete();
        //log.info("Order submitted: " + order);
        return "redirect:/";
    }

    //private int pageSize = 20;
    //public void setPageSize(int pageSize){
    @GetMapping
    public String ordersForUser(
            @AuthenticationPrincipal User user, Model model) {
        /*
        * Pageable是Spring Data根据页号和每页数量选取结果的子集的一种方法。
        * 这里构建PageRequest对象，实现Pageable，请求第一页（序号0）的数据，每页数量20
        */
        //Pageable pageable = PageRequest.of(0, pageSize);
        Pageable pageable = PageRequest.of(0, props.getPageSize());
        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }
}
package tacos.service;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tacos.TacoOrder;
import tacos.data.OrderRepository;

@Service
public class OrderAdminService {

  private OrderRepository orderRepository;

  public OrderAdminService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  /**
   * “@PreAuthorize”注释根据SpEL表达式进行判断，如果表达式值为false，则不会调用方法：
   * "@PreAuthorize"阻止调用，Spring Security抛出AccessDeniedException未检查的异常，不需要进行捕捉。除非想对其进行自定义处理：
   * 如果不进行捕获，异常会被Spring Security的过滤器捕捉，要么显示HTTP 403错误；要么如果用户没登录的话，被重定向到登录界面
   */
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteAllOrders() {
      orderRepository.deleteAll();
  }

  /**
   * “@PostAuthorize”注解：表达式在调用目标方法并返回之前不会被计算
   *
   *@PostAuthorize("hasRole('ADMIN') || " +
   *           "returnObject.user.username == authentication.name")
   *   public TacoOrder getOrder(long id) {
   *   ...
   *   }
   */

}

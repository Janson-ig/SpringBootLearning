package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.TacoOrder;
import tacos.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
/*    //Order save(Order order);
    List<Order> findByDeliveryZip(String deliverZip);//根据deliveryZip属性来查找Order
    List<Order> raedOrderByDeliveryZipPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);//查找指定邮编在一定时间范围内的订单
    List<Order> findByDeliveryToAndDeliveryCityAllIgnoresCase(String deliveryTo, String deliveryCity); //忽略string对比的大小写
    List<Order> findByDeliveryCityOrderByDeliveryTo(String city);//按照deliveryTo属性排序
    @Query("Order o where o.deliveryCity='Seattle'") List<Order> readOrdersDeliveredInSeattle();//指明方法调用时要执行的查询*/

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user);

}

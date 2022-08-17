/*
package tacos.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import tacos.Taco;
import tacos.Order;

@Repository
//通过构造器将JdbcTemplate注入，没有将JdbcTemplate直接赋给实例变量，使用了两个SimpleJdbcInsert实例：
// 1.orderInsert，配置为Taco_Order表协作，并假定id属性会由数据库提供或生成
// 2.实例赋值给orderTacoInserter实例，配置为Taco_Order_Tacos表协作，但是没有声明ID是如何生成的
public class JdbcOrderRepository implements OrderRepository {

    //SimpleJdbcInsert两个方法，execute()和executeAndReturnKey()都接受Map<String,Object>作为参数
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    //
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        this.orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");
        //创建Jackson中ObjectMapper类的一个实例，将其赋值给一个实例变量
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for(Taco taco : tacos){
            saveTacoToOrder(taco, orderId);
        }
        return order;
    }

    private long saveOrderDetails(Order order){
        @SuppressWarnings("unchecked")
        Map<String, Object> values = objectMapper.convertValue(order, Map.class);//convertValue()将Order转换为Map
        //将placedAt的值设置为Order对象placedAt属性的值，objectMapper会将Date属性转换为long，会导致与Taco_Order表中的placedAt字段不兼容
        values.put("placedAt", order.getPlacedAt());
        //将订单信息保存到Taco_Order表中，并以Number对象的形式返回数据库生成的ID，调用longValue()方法将返回值转换为long类型
        long orderId = orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId){
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        //map存储数据，execute()方法插入数据
        orderTacoInserter.execute(values);
    }
}*/

package tacos.web;

import org.springframework.boot.context.properties.
        ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import javax.validation.Valid;

/*
1.使⽤了@ConfigurationProperties注解并且将前缀设置成了taco.orders。
2.@Component注解，这样Spring的组件扫描功能会⾃动发现它并将其创建为Spring应⽤上下⽂中的bean。
3.加上@Validated注解，@Min，@Max，@NotNull等注解才会生效
 */
@Component
@ConfigurationProperties(prefix="taco.orders")
@Data
@Validated
public class OrderProps {
    @Min(value=5, message="must be between 5 and 25")
    @Max(value=25, message="must be between 5 and 25")
    private int pageSize = 20;

}
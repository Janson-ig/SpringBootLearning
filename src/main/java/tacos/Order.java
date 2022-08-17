package tacos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data//创建有参构造器
@Entity//将此类声明为JPA实体
@Table(name = "Taco_Order") //Order实体应该持久化到数据库名为Taco_Order的表中
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id//JPA的id属性
    @GeneratedValue(strategy = GenerationType.AUTO) //JPA通用生成策略
    private Long id;

    private Date placedAt;//创建日期和时间

    //不满足声明的校验规则时，返回message
    @NotBlank(message = "Name is required")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    private String deliveryState;

    @NotBlank(message = "Zip code is required")
    private String deliveryZip;

    @CreditCardNumber(message = "Not a valid credit card number") //属性值必须为合法的信用卡号，要能通过Luhn算法的检测
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
            message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV") //确保值包含3位数字
    private String ccCVV;

    @ManyToMany(targetEntity = Taco.class) //多对多关系
    private  List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

    @PrePersist //在持久化之前自动填充实体属性
    void placedAt(){
        this.placedAt = new Date();
    }
}
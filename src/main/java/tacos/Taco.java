package tacos;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Generated;

@Data//创建有参构造器
@Entity//将此类声明为JPA实体
public class Taco {

    @Id //JPA的id属性需要用@Id注解，指定为数据库中唯一标识该实体的属性
    @GeneratedValue(strategy = GenerationType.AUTO) //依赖数据库自动生成ID
    private Long id;

    private Date createdAt;//创建日期和时间

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    //声明Taco与Ingredient列表之间的关系，每个Taco可以有多个Ingredient，每个Ingredient可以是多个Taco的组成部分
    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    @PrePersist //在持久化之前自动填充实体属性
    void  createdAt(){
        this.createdAt = new Date();
    }
}
package tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data   //创建有参构造器，使用@NoArgsConstructor，这个构造器会被移除
@RequiredArgsConstructor//确保了private的无参构造器之外，还有一个有参构造器
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true) //JPA需要实体有一个无参的构造器，Lombok的@NoArgsConstructor注解实现这个构造器
@Entity //将此类声明为JPA实体
public class Ingredient {

    @Id //JPA的id属性需要用@Id注解，指定为数据库中唯一标识该实体的属性
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
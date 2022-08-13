package tacos;

import java.util.List;
import lombok.Data;

@Data
public class Taco {
    private String name; //简单文本值
    private List<String> ingredients; //ingredients捕获选中的每种配料
}
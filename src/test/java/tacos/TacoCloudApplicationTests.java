package tacos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) //使用spring运行器。是junit的注解,提供测试运行器runner来指导junit如何运行测试
@SpringBootTest //spring boot测试
public class TacoCloudApplicationTests {

    @Test // test方法
    public void contextLoads() {
    }

}

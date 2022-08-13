package tacos;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class) //⇽--- 针对HomeController的Web测试
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc; //⇽--- 注⼊MockMvc
    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/")) //⇽--- 发起对“/”的GET
                .andExpect(status().isOk()) //⇽--- 期望得到HTTP 200
                .andExpect(view().name("home")) //⇽--- 期望得到home视图
                .andExpect(content().string( //⇽--- 期望包含“Welcome to...”
        containsString("Welcome to...")));
    }
}
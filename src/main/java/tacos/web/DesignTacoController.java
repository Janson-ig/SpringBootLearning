package tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.Taco;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.TacoOrder;
import tacos.data.TacoRepository;
import tacos.data.IngredientRepository;

//@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order") //能指定模型对象保存在session，保证跨请求使用
//将jdbcIngredientRepository注入到controller中，使用它提供Ingredient对象的列表
public class DesignTacoController {
    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

   @ModelAttribute(name = "order")
   public TacoOrder order(){
       return new TacoOrder();
   }
    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();

    }
    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco()); //p.149 bug fixed
        return "design";

    }

    //@ModelAttribute注释能够保证在模型中创建一个Order对象，需要订单信息在多个请求中出现

    @PostMapping
    //@Valid注释告诉Spring MVC对Taco对象进行校验，校验时机是在它绑定完表单数据后；若存在校验错误，错误细节捕捉到Errors对象中
    //processDesign()方法接受order对象作为参数，包括Taco和Errors对象
    //@ModelAttribute注解表明它的值来自模型，SpringMVC不会尝试请求参数绑定到它上面
    //检查完错误后，使用注入的TacoRepository保存taco；它将Taco对象保存到session里的Order中
    // 在用户提交订单表单之前，Order对象会一直保存在session中，没有保存到数据库中
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        //hasErrors方法判断是否有校验错误
        if (errors.hasErrors()) {
            return "design";}
        Taco saved = designRepo.save(design);
        tacoOrder.addDesign(saved);
        // Save the taco design...
        // We'll do this in chapter 3
        //log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient>ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }


}
/*
package tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import tacos.Taco;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }

    private List<Ingredient> filterByType(
            List<Ingredient>ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    //@Valid注释告诉Spring MVC对Taco对象进行校验，校验时机是在它绑定完表单数据后；若存在校验错误，错误细节捕捉到Errors对象中
    public String processDesign(@Valid Taco design, Errors errors) {
        //hasErrors方法判断是否有校验错误
        if (errors.hasErrors()) {
            return "design";}
        // Save the taco design...
        // We'll do this in chapter 3
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }
}
*/
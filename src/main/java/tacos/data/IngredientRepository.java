package tacos.data;

import org.springframework.data.repository.CrudRepository;
import tacos.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    //查询所有的配料信息，将它们放到一个Ingredient对象的集合中
   // Iterable<Ingredient> findAll();
    //根据id，查询单个Ingredient
   // Ingredient findById(String id);
    //保存Ingredient对象
  //  Ingredient save(Ingredient ingredient);
}


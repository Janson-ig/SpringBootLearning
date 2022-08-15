package tacos.data;

import tacos.Ingredient;

public interface IngredientRepository {
    //查询所有的配料信息，将它们放到一个Ingredient对象的集合中
    Iterable<Ingredient> findAll();
    //根据id，查询单个Ingredient
    Ingredient findOne(String id);
    //保存Ingredient对象
    Ingredient save(Ingredient ingredient);
}


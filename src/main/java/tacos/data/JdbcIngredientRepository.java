package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository { //implements实现接口，Extends继承类
    private JdbcTemplate jdbc;

    @Autowired //创建JdbcIngredientRepository bean时，会通过此注解，将JdbcTemplate注入进来
    public JdbcIngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        //query中可设置任意参数
        //query返回对象的List
        return jdbc.query("select id, name, type from Ingredient", this::mapRowToIngredient);
    }

    @Override
    public Ingredient findOne(String id) {
        //queryForObject()方法返回对象
        return jdbc.queryForObject("select id, name, type from Ingredient where id=?", this::mapRowToIngredient, id);
    }
    /*
    // 显式RowMapper实现fineOne()方法
    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject(
            "select id, name, type from Ingredient where id=?",new RowMapper<Ingredient>() {
                public Ingredient mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                   return new Ingredient(
                    rs.getString("id"),
                    rs.getString("name"),
                    Ingredient.Type.valueOf(rs.getString("type")));
                };
            }, id);
    }
    */

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        //update()方法可以执行向数据库写入或更新数据的查询语句
        jdbc.update(
                "insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }
}
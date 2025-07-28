package poly.cafe.dao.impl;

import poly.cafe.entity.Food;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;
import java.util.List;
import poly.cafe.dao1.FoodDAO;

public class FoodDAOImpl implements FoodDAO {

    @Override
    public List<Food> selectByCategoryId(String categoryId) {
        String sql = "SELECT * FROM Food WHERE CategoryId = ?";
        return XQuery.getBeanList(Food.class, sql, categoryId);
    }

    @Override
    public void insert(Food food) {
        String sql = "INSERT INTO Food (Id, Name, UnitPrice, Discount, Image, Available, CategoryId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        XJdbc.executeUpdate(sql,
                food.getId(),
                food.getName(),
                food.getUnitPrice(),
                food.getDiscount(),
                food.getImage(),
                food.isAvailable(),
                food.getCategoryId());
    }

    @Override
    public void update(Food food) {
        String sql = "UPDATE Food SET Name = ?, UnitPrice = ?, Discount = ?, Image = ?, Available = ?, CategoryId = ? WHERE Id = ?";
        XJdbc.executeUpdate(sql,
                food.getName(),
                food.getUnitPrice(),
                food.getDiscount(),
                food.getImage(),
                food.isAvailable(),
                food.getCategoryId(),
                food.getId());
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Food WHERE Id = ?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public Food selectById(String id) {
        String sql = "SELECT * FROM Food WHERE Id = ?";
        return XQuery.getSingleBean(Food.class, sql, id);
    }

    @Override
    public List<Food> selectAll() {
        String sql = "SELECT * FROM Food";
        return XQuery.getBeanList(Food.class, sql);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cafe.dao.impl;

import poly.cafe.entity.FoodCategories;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;
import java.util.List;
import poly.cafe.dao1.FoodCategoryDAO;

public class FoodCategoryDAOImpl implements FoodCategoryDAO {

    @Override
    public List<FoodCategories> selectAll() {
        String sql = "SELECT * FROM FoodCategories";
        return XQuery.getBeanList(FoodCategories.class, sql);
    }

    @Override
    public void insert(FoodCategories category) {
        String sql = "INSERT INTO FoodCategories (Id, Name, Description) VALUES (?, ?, ?)";
        XJdbc.executeUpdate(sql,
                category.getId(),
                category.getName(),
                category.getDescription());
    }

    @Override
    public void update(FoodCategories category) {
        String sql = "UPDATE FoodCategories SET Name = ?, Description = ? WHERE Id = ?";
        XJdbc.executeUpdate(sql,
                category.getName(),
                category.getDescription(),
                category.getId());
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM FoodCategories WHERE Id = ?";
        XJdbc.executeUpdate(sql, id);
    }

    @Override
    public FoodCategories selectById(String id) {
        String sql = "SELECT * FROM FoodCategories WHERE Id = ?";
        return XQuery.getSingleBean(FoodCategories.class, sql, id);
    }
}
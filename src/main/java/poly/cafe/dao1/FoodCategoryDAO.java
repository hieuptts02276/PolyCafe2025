/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cafe.dao1;

import poly.cafe.entity.FoodCategories;
import java.util.List;

/**
 * Interface for Food Category Data Access Operations.
 */
public interface FoodCategoryDAO {

    /**
     * Retrieves all food categories from the database.
     *
     * @return A list of all FoodCategories objects.
     */
    List<FoodCategories> selectAll();

    /**
     * Inserts a new food category into the database.
     *
     * @param category The FoodCategories object to be inserted.
     */
    void insert(FoodCategories category);

    /**
     * Updates an existing food category in the database.
     *
     * @param category The FoodCategories object with updated information.
     */
    void update(FoodCategories category);

    /**
     * Deletes a food category from the database by its ID.
     *
     * @param id The ID of the food category to be deleted.
     */
    void delete(String id);

    /**
     * Retrieves a single food category by its ID.
     *
     * @param id The ID of the food category.
     * @return The FoodCategories object if found, otherwise null.
     */
    FoodCategories selectById(String id);
}

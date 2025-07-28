package poly.cafe.dao1;

import poly.cafe.entity.Food;
import java.util.List;

/**
 * Interface for Food Data Access Operations.
 */
public interface FoodDAO {

    /**
     * Retrieves a list of food items based on their category ID.
     *
     * @param categoryId The ID of the food category.
     * @return A list of Food objects belonging to the specified category.
     */
    List<Food> selectByCategoryId(String categoryId);

    /**
     * Inserts a new food item into the database.
     *
     * @param food The Food object to be inserted.
     */
    void insert(Food food);

    /**
     * Updates an existing food item in the database.
     *
     * @param food The Food object with updated information.
     */
    void update(Food food);

    /**
     * Deletes a food item from the database by its ID.
     *
     * @param id The ID of the food item to be deleted.
     */
    void delete(String id);

    /**
     * Retrieves a single food item by its ID.
     *
     * @param id The ID of the food item.
     * @return The Food object if found, otherwise null.
     */
    Food selectById(String id);

    /**
     * Retrieves all food items from the database.
     *
     * @return A list of all Food objects.
     */
    List<Food> selectAll();
}

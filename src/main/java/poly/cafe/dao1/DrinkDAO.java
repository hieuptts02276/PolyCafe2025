package poly.cafe.dao1;

import poly.cafe.dao1.CrudDAO;
import poly.cafe.entity.Drink;
import java.util.List;

public interface DrinkDAO extends CrudDAO<Drink, String> {
    List<Drink> findByCategoryId(String categoryId);
}

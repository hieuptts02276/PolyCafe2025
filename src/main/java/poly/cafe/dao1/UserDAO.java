package poly.cafe.dao1;

import poly.cafe.dao1.CrudDAO;
import poly.cafe.entity.User;

public interface UserDAO extends CrudDAO<User, String> {
    boolean updatePhoto(String username, String photoName);

}


package poly.cafe.dao.impl;

import java.util.List;
import poly.cafe.dao1.UserDAO;
import poly.cafe.entity.User;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;

public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl instance;

    public static UserDAOImpl getInstance() {
        if (instance == null) {
            instance = new UserDAOImpl();
        }
        return instance;
    }
    private final String createSql = 
    "INSERT INTO Users (Username, Password, Enabled, Fullname, Photo, Manager) " +
    "VALUES (?, ?, ?, ?, ?, ?)";

private final String updateSql = 
    "UPDATE Users SET Password = ?, Enabled = ?, Fullname = ?, Photo = ?, Manager = ? " +
    "WHERE Username = ?";

private final String deleteByIdSql = 
    "DELETE FROM Users WHERE Username = ?";

private final String findAllSql = 
    "SELECT * FROM Users";

private final String findByIdSql = 
    "SELECT * FROM Users WHERE Username = ?";
private final String selectAllUsersSql = "SELECT *, DATEDIFF(YEAR, CreatedDate, GETDATE()) AS YearsUsed FROM Users";


    @Override
    public User create(User entity) {
        Object[] values = {
            entity.getUsername(),
            entity.getPassword(),
            entity.isEnabled(),
            entity.getFullname(),
            entity.getPhoto(),
            entity.isManager()
        };
        XJdbc.executeUpdate(createSql, values);
        return entity;
    }

    @Override
    public void update(User entity) {
        Object[] values = {
            entity.getPassword(),
            entity.isEnabled(),
            entity.getFullname(),
            entity.getPhoto(),
            entity.isManager(),
            entity.getUsername()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(String id) {
        XJdbc.executeUpdate(deleteByIdSql, id);
    }

    @Override
    public List<User> findAll() {
        return XQuery.getBeanList(User.class, findAllSql);
    }

    @Override
    public User findById(String id) {
        return XQuery.getSingleBean(User.class, findByIdSql, id);
    }

    @Override
    public boolean updatePhoto(String username, String photoName) {
       String sql = "UPDATE Users SET Photo = ? WHERE Username = ?";
    int rows = XJdbc.executeUpdate(sql, photoName, username);
    return rows > 0;
    }
}

package poly.cafe.dao.impl;

import java.util.Date;
import java.util.List;
import poly.cafe.entity.Bill;
import poly.cafe.util.XJdbc;
import poly.cafe.dao1.BillDAO;
import poly.cafe.util.XAuth;
import poly.cafe.util.XQuery;

public class BillDAOImpl implements BillDAO {

    // SQL statements for working with the Bills table
private final String createSql = 
    "INSERT INTO Bills(Username, CardId, Checkin, Status) VALUES (?, ?, ?, ?)";

private final String updateSql = 
    "UPDATE Bills SET Username=?, CardId=?, Checkin=?, Checkout=?, Status=? WHERE Id=?";

private final String deleteByIdSql = 
    "DELETE FROM Bills WHERE Id=?";

private final String selectAllSql = 
    "SELECT * FROM Bills";

private final String selectByIdSql = 
    selectAllSql + " WHERE Id=?";

private final String selectLastSql = 
    "SELECT * FROM Bills WHERE Id = (SELECT MAX(Id) FROM Bills)";

private final String selectByUsernameSql = 
    selectAllSql + " WHERE Username=?";

private final String selectByCardIdSql = 
    selectAllSql + " WHERE CardId=?";

private final String selectServicingByCardIdSql = 
    selectAllSql + " WHERE CardId=? AND Status=0";  // Status=0 có thể là "đang phục vụ"

private final String selectByUsernameAndTimeRangeSql = 
    selectAllSql + " WHERE Username=? AND Checkin BETWEEN ? AND ? ORDER BY Checkin DESC";

private final String selectByTimeRangeSql = 
    selectAllSql + " WHERE Checkin BETWEEN ? AND ? ORDER BY Checkin DESC";


    @Override
    public Bill create(Bill entity) {
        Object[] values = {
            entity.getUsername(),
            entity.getCardId(),
            entity.getCheckin(),
            entity.getStatus()
        };
        XJdbc.executeUpdate(createSql, values);
        return XQuery.getSingleBean(Bill.class, selectLastSql);
    }

    @Override
    public void update(Bill entity) {
        Object[] values = {
            entity.getUsername(),
            entity.getCardId(),
            entity.getCheckin(),
            entity.getCheckout(),
            entity.getStatus(),
            entity.getId()
        };
        XJdbc.executeUpdate(updateSql, values);
    }

    @Override
    public void deleteById(Long id) {
        XJdbc.executeUpdate(deleteByIdSql, id);
    }

    @Override
    public List<Bill> findAll() {
        return XQuery.getBeanList(Bill.class, selectAllSql);
    }

    @Override
    public Bill findById(Long id) {
        return XQuery.getSingleBean(Bill.class, selectByIdSql, id);
    }

    @Override
    public List<Bill> findByUsername(String username) {
        return XQuery.getBeanList(Bill.class, selectByUsernameSql, username);
    }

    @Override
    public List<Bill> findByCardId(Integer cardId) {
        return XQuery.getBeanList(Bill.class, selectByCardIdSql, cardId);
    }

    @Override
    public Bill findServicingByCardId(Integer cardId) {
        Bill bill = XQuery.getSingleBean(Bill.class, selectServicingByCardIdSql, cardId);
        if (bill == null) {
            Bill newBill = new Bill();
            newBill.setCardId(cardId);
            newBill.setCheckin(new Date());
            newBill.setStatus(0);
            if (XAuth.user != null) {
                newBill.setUsername(XAuth.user.getUsername());
            } else {
                newBill.setUsername("anonymous");
            }
            bill = this.create(newBill);
        }
        return bill;
    }

    @Override
    public List<Bill> findByUserAndTimeRange(String username, Date begin, Date end) {
        return XQuery.getBeanList(Bill.class, selectByUsernameAndTimeRangeSql, username, begin, end);
    }

    @Override
    public List<Bill> findByTimeRange(Date begin, Date end) {
        return XQuery.getBeanList(Bill.class, selectByTimeRangeSql, begin, end);
    }
}

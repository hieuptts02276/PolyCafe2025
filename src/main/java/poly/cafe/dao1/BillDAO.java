package poly.cafe.dao1;

import java.util.Date;
import java.util.List;
import poly.cafe.entity.Bill;

public interface BillDAO {
    Bill create(Bill entity);
    void update(Bill entity);
    void deleteById(Long id);
    List<Bill> findAll();
    Bill findById(Long id);
    List<Bill> findByUsername(String username);
    List<Bill> findByCardId(Integer cardId);
    Bill findServicingByCardId(Integer cardId);
    List<Bill> findByUserAndTimeRange(String username, Date begin, Date end);
    List<Bill> findByTimeRange(Date begin, Date end);
}

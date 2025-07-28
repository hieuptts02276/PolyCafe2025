package poly.cafe.dao1;

import java.util.List;
import poly.cafe.entity.BillDetail;

public interface BillDetailDAO {
    BillDetail create(BillDetail entity);
    void update(BillDetail entity);
    void deleteById(Long id);
    List<BillDetail> findAll();
    BillDetail findById(Long id);
    List<BillDetail> findByBillId(Long billId);
    List<BillDetail> findByDrinkId(String drinkId);
}

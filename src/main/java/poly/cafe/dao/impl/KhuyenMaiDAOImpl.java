
package poly.cafe.dao.impl;

import java.util.List;
import poly.cafe.dao1.KhuyenMaiDAO;
import poly.cafe.entity.KhuyenMai;
import poly.cafe.entity.User;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XQuery;

public class KhuyenMaiDAOImpl implements KhuyenMaiDAO{
    private final String findAllSql = 
    "SELECT * FROM KhuyenMais";
    @Override
    public void insert(KhuyenMai khuyenmai) {
       String sql = "INSERT INTO KhuyenMais (Ma, TenKhuyenMai, NgayBatDau, NgayKetThuc, TyLeGiamGia) VALUES (?,?,?,?,?)";
        XJdbc.executeUpdate(sql, khuyenmai.getMaKhuyenMai(), khuyenmai.getNgayBatDau(), khuyenmai.getNgayKetThuc(), khuyenmai.getTenKhuyenMai(), khuyenmai.getTyLeGiamGia());
    }

    @Override
    public void update(KhuyenMai khuyenmai) {
      String sql = "UPDATE INTO KhuyenMais SET TenKhuyenMai = ?, NgayBatDau = ?, NgayKetThuc = ?, TyLeGiamGia = ? WHERE Ma=?";
      XJdbc.executeUpdate(sql, khuyenmai.getMaKhuyenMai(), khuyenmai.getTenKhuyenMai(), khuyenmai.getNgayBatDau(), khuyenmai.getNgayKetThuc(), khuyenmai.getTyLeGiamGia());
    }

    @Override
    public List<KhuyenMai> findAll() {
        return XQuery.getBeanList(KhuyenMai.class, findAllSql);
    }
    

}

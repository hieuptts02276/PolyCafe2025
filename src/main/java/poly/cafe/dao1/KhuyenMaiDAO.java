/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package poly.cafe.dao1;

import java.util.List;
import poly.cafe.entity.KhuyenMai;

/**
 *
 * @author Hieu
 */
public interface KhuyenMaiDAO {
    void insert( KhuyenMai khuyenmai);
    void update(KhuyenMai khuyenmai);

    public List<KhuyenMai> findAll();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.cafe.entity;

import java.util.Date;

public class KhuyenMai {
    private int MaKhuyenMai;
    private String TenKhuyenMai;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private int TyLeGiamGia;

    public KhuyenMai() {
    }

    public KhuyenMai(int MaKhuyenMai, String TenKhuyenMai, Date ngayBatDau, Date ngayKetThuc, int TyLeGiamGia) {
        this.MaKhuyenMai = MaKhuyenMai;
        this.TenKhuyenMai = TenKhuyenMai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.TyLeGiamGia = TyLeGiamGia;
    }

    public int getMaKhuyenMai() {
        return MaKhuyenMai;
    }

    public void setMaKhuyenMai(int MaKhuyenMai) {
        this.MaKhuyenMai = MaKhuyenMai;
    }

    public String getTenKhuyenMai() {
        return TenKhuyenMai;
    }

    public void setTenKhuyenMai(String TenKhuyenMai) {
        this.TenKhuyenMai = TenKhuyenMai;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getTyLeGiamGia() {
        return TyLeGiamGia;
    }

    public void setTyLeGiamGia(int TyLeGiamGia) {
        this.TyLeGiamGia = TyLeGiamGia;
    }
    
}

package poly.cafe.entity; 

public class KhachHang {
    private int maKH;
    private String hoTen;
    private boolean gioiTinh; // true for Nam, false for Nữ
    private String dienThoai;

    public KhachHang() {
    }

    public KhachHang(int maKH, String hoTen, boolean gioiTinh, String dienThoai) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.dienThoai = dienThoai;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isGioiTinh() { // Use isGioiTinh() for boolean getter
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    @Override
    public String toString() {
        return hoTen; // Useful for displaying in combo boxes or lists
    }
}

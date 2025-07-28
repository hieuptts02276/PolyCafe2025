/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package poly.cafe.ui1;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import poly.cafe.entity.KhachHang;
import poly.cafe.util.XJdbc;
import poly.cafe.util.XNofication;
import poly.cafe.util.XQuery;

/**
 *
 * @author Hieu
 */
public class QuanLiKhachHangJDialog extends javax.swing.JDialog {
    private int currentIndex = 0;
    public QuanLiKhachHangJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    private void init() {
        setLocationRelativeTo(null); // Center the dialog on screen
        fillTable(); // Populate the table with customer data
        updateStatus(); // Update button states
    }

    // Fills the tblKhachHang with data from the database
    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0); // Xóa sạch dữ liệu cũ trong bảng

        try {
            String sql = "SELECT MaKH, HoTen, GioiTinh, DienThoai FROM KhachHang";
            // Lấy danh sách khách hàng từ cơ sở dữ liệu thông qua XQuery
            List<KhachHang> danhSachKhachHang = XQuery.getBeanList(KhachHang.class, sql);

            for (KhachHang kh : danhSachKhachHang) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getHoTen(),
                    kh.isGioiTinh() ? "Nam" : "Nữ", // Chuyển đổi giá trị boolean của giới tính thành chuỗi hiển thị
                    kh.getDienThoai()
                };
                model.addRow(row); // Thêm hàng mới vào bảng
            }
        } catch (RuntimeException e) {
            // Hiển thị thông báo lỗi nếu có vấn đề trong quá trình truy vấn dữ liệu
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu khách hàng: " + e.getMessage(), "Lỗi Truy Vấn", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Đổ dữ liệu từ một đối tượng KhachHang vào các trường nhập liệu trên biểu mẫu.
     * @param kh Đối tượng KhachHang chứa thông tin cần hiển thị.
     */
    private void setForm(KhachHang kh) {
        txtMa.setText(String.valueOf(kh.getMaKH()));
        txtName.setText(kh.getHoTen());
        txtSDT.setText(kh.getDienThoai());
        cboGioiTinh.setSelectedIndex(kh.isGioiTinh() ? 0 : 1); // Đặt ComboBox giới tính (0: Nam, 1: Nữ)
        updateStatus(); // Cập nhật trạng thái của các nút điều khiển sau khi đổ dữ liệu
    }

    /**
     * Thu thập dữ liệu từ các trường nhập liệu trên biểu mẫu và tạo một đối tượng KhachHang.
     * @return Đối tượng KhachHang chứa dữ liệu hiện có trên biểu mẫu.
     */
    private KhachHang getForm() {
        KhachHang kh = new KhachHang();
        // Mã khách hàng (MaKH) là trường tự động tăng trong DB, chỉ lấy giá trị nếu có (khi chỉnh sửa)
        if (!txtMa.getText().isEmpty()) {
            try {
                kh.setMaKH(Integer.parseInt(txtMa.getText()));
            } catch (NumberFormatException e) {
                // Ghi lỗi ra console nếu không thể chuyển đổi Mã Khách Hàng sang số
                System.err.println("Lỗi chuyển đổi Mã Khách Hàng: " + e.getMessage());
            }
        }
        kh.setHoTen(txtName.getText().trim()); // Lấy họ tên, loại bỏ khoảng trắng thừa
        kh.setDienThoai(txtSDT.getText().trim()); // Lấy số điện thoại, loại bỏ khoảng trắng thừa
        kh.setGioiTinh(cboGioiTinh.getSelectedIndex() == 0); // Đặt giới tính dựa trên lựa chọn ComboBox
        return kh;
    }

    /**
     * Xóa trắng nội dung của tất cả các trường nhập liệu và đặt lại ComboBox giới tính.
     */
    private void clearForm() {
        txtMa.setText("");
        txtName.setText("");
        txtSDT.setText("");
        cboGioiTinh.setSelectedIndex(0); // Đặt lại giới tính mặc định là "Nam"
        currentIndex = -1; // Đặt chỉ mục hiện tại về -1 (không có khách hàng nào được chọn)
        updateStatus(); // Cập nhật lại trạng thái các nút
    }

    /**
     * Cập nhật trạng thái bật/tắt của các nút điều khiển (Thêm mới, Cập nhật, Xóa)
     * dựa trên trạng thái chọn khách hàng và dữ liệu trên biểu mẫu.
     */
    private void updateStatus() {
        // Nút "Tạo mới" (btnNew) chỉ được bật khi không có khách hàng nào đang được chọn/chỉnh sửa
        btnNew.setEnabled(txtMa.getText().isEmpty());
        // Các nút "Cập nhật" (btnUpdate) và "Xóa" (btnDelete) chỉ bật khi có khách hàng đang được chọn
        boolean isCustomerSelected = currentIndex != -1 && !txtMa.getText().isEmpty();
        btnUpdate.setEnabled(isCustomerSelected);
        btnDelete.setEnabled(isCustomerSelected);
        btnReset.setEnabled(true); // Nút "Nhập mới" (Reset) luôn luôn bật
    }

    /**
     * Kiểm tra tính hợp lệ của dữ liệu nhập vào từ biểu mẫu trước khi thực hiện các thao tác
     * thêm/cập nhật.
     * @return `true` nếu tất cả dữ liệu hợp lệ, ngược lại là `false`.
     */
    private boolean validateForm() {
        String hoTen = txtName.getText().trim();
        String dienThoai = txtSDT.getText().trim();

        if (hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống.", "Lỗi Nhập Liệu", JOptionPane.WARNING_MESSAGE);
            txtName.requestFocus(); // Đặt con trỏ vào trường Họ tên
            return false;
        }
        if (dienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống.", "Lỗi Nhập Liệu", JOptionPane.WARNING_MESSAGE);
            txtSDT.requestFocus(); // Đặt con trỏ vào trường Số điện thoại
            return false;
        }
        // Kiểm tra định dạng số điện thoại (chỉ chứa số, 10 hoặc 11 chữ số)
        if (!dienThoai.matches("^\\d{10,11}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là 10 hoặc 11 chữ số.", "Lỗi Nhập Liệu", JOptionPane.WARNING_MESSAGE);
            txtSDT.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Thực hiện thêm mới một khách hàng vào cơ sở dữ liệu.
     */
    private void insert() {
        if (!validateForm()) {
            return; // Dừng lại nếu dữ liệu không hợp lệ
        }
        try {
            KhachHang kh = getForm();
            String sql = "INSERT INTO KhachHang (HoTen, GioiTinh, DienThoai) VALUES (?, ?, ?)";
            XJdbc.executeUpdate(sql, kh.getHoTen(), kh.isGioiTinh(), kh.getDienThoai());

            fillTable(); // Tải lại dữ liệu bảng
            clearForm(); // Xóa trắng biểu mẫu
            JOptionPane.showMessageDialog(this, "Thêm mới khách hàng thành công!", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Thêm mới khách hàng thất bại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cập nhật thông tin của một khách hàng đã tồn tại trong cơ sở dữ liệu.
     */
    private void update() {
        if (!validateForm()) {
            return; // Dừng lại nếu dữ liệu không hợp lệ
        }
        if (txtMa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần cập nhật.", "Thông Báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            KhachHang kh = getForm();
            String sql = "UPDATE KhachHang SET HoTen = ?, GioiTinh = ?, DienThoai = ? WHERE MaKH = ?";
            XJdbc.executeUpdate(sql, kh.getHoTen(), kh.isGioiTinh(), kh.getDienThoai(), kh.getMaKH());

            fillTable(); // Tải lại dữ liệu bảng
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Xóa một khách hàng khỏi cơ sở dữ liệu.
     */
    private void delete() {
        if (txtMa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa.", "Thông Báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Yêu cầu xác nhận trước khi xóa
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác Nhận Xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maKHToDelete = Integer.parseInt(txtMa.getText());
                String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
                XJdbc.executeUpdate(sql, maKHToDelete);

                fillTable(); // Tải lại dữ liệu bảng
                clearForm(); // Xóa trắng biểu mẫu
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        btnDeleteCheckedItems = new javax.swing.JButton();
        btnUnCheckAll = new javax.swing.JButton();
        btnCheckAll = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        cboGioiTinh = new javax.swing.JComboBox<>();
        btnNew = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã KH", "Họ tên", "giới tính", "Điện thoại", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);

        btnDeleteCheckedItems.setText("Xóa các mục chọn");
        btnDeleteCheckedItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCheckedItemsActionPerformed(evt);
            }
        });

        btnUnCheckAll.setText("Bỏ chọn tất cả");
        btnUnCheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnCheckAllActionPerformed(evt);
            }
        });

        btnCheckAll.setText("Chọn tất cả");
        btnCheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCheckAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUnCheckAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteCheckedItems)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteCheckedItems)
                    .addComponent(btnUnCheckAll)
                    .addComponent(btnCheckAll))
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab1", jPanel1);

        jTabbedPane1.addTab("DANH SÁCH", jTabbedPane2);

        jLabel1.setText("Mã Khách Hàng");

        jLabel2.setText("Họ tên");

        jLabel3.setText("Số điện thoại");

        jLabel4.setText("GIỚI TÍNH");

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        btnNew.setText("Tạo mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnReset.setText("Nhập mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel2))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete)
                        .addGap(2, 2, 2)
                        .addComponent(btnReset)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnReset)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addGap(26, 26, 26))
        );

        jTabbedPane4.addTab("tab1", jPanel2);

        jTabbedPane1.addTab("BIỂU MẪU", jTabbedPane4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        if (evt.getClickCount() == 1) {
        currentIndex = tblKhachHang.getSelectedRow();
        if (currentIndex >= 0) {
            try {
                int maKH = (int) tblKhachHang.getValueAt(currentIndex, 0);
                String sql = "SELECT MaKH, HoTen, GioiTinh, DienThoai FROM KhachHang WHERE MaKH = ?";
                KhachHang kh = XQuery.getSingleBean(KhachHang.class, sql, maKH);
                if (kh != null) {
                    setForm(kh);
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khách hàng này trong cơ sở dữ liệu.", "Lỗi Dữ Liệu", JOptionPane.WARNING_MESSAGE);
                    XNofication.showSystemTrayWarning("Cảnh báo dữ liệu", "Không tìm thấy khách hàng trong CSDL!");
                    clearForm();
                }
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tải thông tin khách hàng từ CSDL: " + e.getMessage(), "Lỗi Truy Vấn", JOptionPane.ERROR_MESSAGE);
                XNofication.showSystemTrayError("Lỗi truy vấn", "Không thể tải dữ liệu khách hàng.");
            }
        }
    }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnCheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckAllActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
    int checkboxColumnIndex = 4;
    if (model.getColumnCount() <= checkboxColumnIndex) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy cột checkbox trong bảng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        XNofication.showSystemTrayError("Lỗi bảng", "Không có cột checkbox để chọn tất cả.");
        return;
    }

    for (int i = 0; i < model.getRowCount(); i++) {
        model.setValueAt(true, i, checkboxColumnIndex);
    }
    XNofication.showSystemTraySuccess("Đã chọn", "Đã chọn tất cả khách hàng.");
    }//GEN-LAST:event_btnCheckAllActionPerformed

    private void btnUnCheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnCheckAllActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
    int checkboxColumnIndex = 4;
    if (model.getColumnCount() <= checkboxColumnIndex) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy cột checkbox trong bảng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        XNofication.showSystemTrayError("Lỗi bảng", "Không có cột checkbox để bỏ chọn.");
        return;
    }

    for (int i = 0; i < model.getRowCount(); i++) {
        model.setValueAt(false, i, checkboxColumnIndex);
    }
    XNofication.showSystemTraySuccess("Đã bỏ chọn", "Đã bỏ chọn tất cả khách hàng.");
    }//GEN-LAST:event_btnUnCheckAllActionPerformed

    private void btnDeleteCheckedItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCheckedItemsActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
    int checkboxColumnIndex = 4;
    int maKHColumnIndex = 0;

    if (model.getColumnCount() <= checkboxColumnIndex || model.getColumnCount() <= maKHColumnIndex) {
        JOptionPane.showMessageDialog(this, "Cấu trúc bảng không đúng để thực hiện chức năng xóa nhiều.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        XNofication.showSystemTrayError("Lỗi cấu trúc", "Không thể xóa do lỗi cấu trúc bảng.");
        return;
    }

    List<Integer> maKHToDeleteList = new ArrayList<>();
    for (int i = 0; i < model.getRowCount(); i++) {
        Boolean isChecked = (Boolean) model.getValueAt(i, checkboxColumnIndex);
        if (isChecked != null && isChecked) {
            int maKH = (int) model.getValueAt(i, maKHColumnIndex);
            maKHToDeleteList.add(maKH);
        }
    }

    if (maKHToDeleteList.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một khách hàng để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        XNofication.showSystemTrayWarning("Không có khách hàng nào được chọn", "Bạn cần chọn ít nhất 1 khách hàng.");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa " + maKHToDeleteList.size() + " khách hàng đã chọn?",
            "Xác nhận xóa nhiều", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

    if (confirm == JOptionPane.YES_OPTION) {
        try {
            int deletedCount = 0;
            for (Integer maKH : maKHToDeleteList) {
                String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
                XJdbc.executeUpdate(sql, maKH);
                deletedCount++;
            }

            fillTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Đã xóa thành công " + deletedCount + " khách hàng.", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
            XNofication.showSystemTraySuccess("Xóa thành công", "Đã xóa " + deletedCount + " khách hàng.");
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            XNofication.showSystemTrayError("Lỗi khi xóa", "Không thể xóa khách hàng: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_btnDeleteCheckedItemsActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        insert();
    XNofication.showSystemTraySuccess("Thêm khách hàng", "Thêm mới khách hàng thành công.");
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
         update();
    XNofication.showSystemTraySuccess("Cập nhật", "Đã cập nhật thông tin khách hàng.");
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    XNofication.showSystemTrayWarning("Đã xóa", "Một khách hàng đã bị xóa.");
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clearForm();
    XNofication.showSystemTraySuccess("Đặt lại", "Form đã được làm mới.");
    }//GEN-LAST:event_btnResetActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        fillTable();
    clearForm();
    XNofication.showSystemTraySuccess("Khởi động", "Bảng khách hàng đã được tải.");
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLiKhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLiKhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLiKhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLiKhachHangJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLiKhachHangJDialog dialog = new QuanLiKhachHangJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckAll;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteCheckedItems;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUnCheckAll;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables
}

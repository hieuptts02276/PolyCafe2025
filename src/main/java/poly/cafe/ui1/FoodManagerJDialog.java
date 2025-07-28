/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package poly.cafe.ui1;

import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import poly.cafe.dao.impl.FoodCategoryDAOImpl;
import poly.cafe.dao.impl.FoodDAOImpl;
import poly.cafe.dao1.FoodCategoryDAO;
import poly.cafe.dao1.FoodDAO;
import poly.cafe.entity.Food;
import poly.cafe.entity.FoodCategories;

/**
 *
 * @author Hieu
 */
public class FoodManagerJDialog extends javax.swing.JDialog {
    private List<Food> foodList;
    private List<FoodCategories> categoryList;
    private String currentImagePath = "";
    FoodDAO foodDAO = new FoodDAOImpl();
    FoodCategoryDAO foodCategoriesDAO = new FoodCategoryDAOImpl();
    public FoodManagerJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    // Phương thức khởi tạo ban đầu
    private void init() {
        setLocationRelativeTo(null); // Đặt cửa sổ ra giữa màn hình
        fillCategoryTable();
        fillCategoryComboBox(); // Gọi phương thức mới để điền dữ liệu vào ComboBox
        //fillFoodTable(); // Sẽ gọi sau khi chọn category
        // Khởi tạo button group cho radio buttons
        buttonGroup1.add(rdoAvaildable);
        buttonGroup1.add(rdoHetHang);
        rdoAvaildable.setSelected(true); // Mặc định chọn sẵn có
    }

    // Phương thức điền dữ liệu vào bảng loại thức ăn
    private void fillCategoryTable() {
        DefaultTableModel model = (DefaultTableModel) tblLoaiThucAn.getModel();
        model.setRowCount(0); // Xóa tất cả các hàng hiện có

        // Dữ liệu giả định (thay thế bằng dữ liệu từ FoodCategoryDAO của bạn)
        // categoryList = foodCategoryDAO.selectAll();
        categoryList = new java.util.ArrayList<>();
        categoryList.add(new FoodCategories("CAT01", "Đồ uống", "Các loại đồ uống"));
        categoryList.add(new FoodCategories("CAT02", "Đồ ăn nhẹ", "Các món ăn nhẹ"));
        categoryList.add(new FoodCategories("CAT03", "Món chính", "Các món ăn chính"));


        for (FoodCategories category : categoryList) {
            model.addRow(new Object[]{category.getName()});
        }
    }

    // Phương thức mới: Điền dữ liệu vào ComboBox loại thức ăn
    private void fillCategoryComboBox() {
        cboLoaiThucAn.removeAllItems(); // Xóa tất cả các mục hiện có
        for (FoodCategories category : categoryList) {
            cboLoaiThucAn.addItem(category.getName()); // Thêm tên loại vào ComboBox
        }
    }


    // Phương thức điền dữ liệu vào bảng thức ăn dựa trên loại đã chọn
    private void fillFoodTable(String categoryId) {
        DefaultTableModel model = (DefaultTableModel) tblThucAn.getModel();
        model.setRowCount(0); // Xóa tất cả các hàng hiện có

        // Dữ liệu giả định (thay thế bằng dữ liệu từ FoodDAO của bạn, lọc theo categoryId)
        // foodList = foodDAO.selectByCategoryId(categoryId);
        foodList = new java.util.ArrayList<>();
        if (categoryId != null) { // Đảm bảo categoryId không null khi lọc
            if (categoryId.equals("CAT01")) {
                foodList.add(new Food("FOOD001", "Cà phê đen", 25000, 0.05f, "coffee_black.jpg", true, "CAT01"));
                foodList.add(new Food("FOOD002", "Trà sữa", 35000, 0.0f, "milk_tea.jpg", true, "CAT01"));
            } else if (categoryId.equals("CAT02")) {
                foodList.add(new Food("FOOD003", "Bánh mì", 20000, 0.1f, "banhmi.jpg", true, "CAT02"));
                foodList.add(new Food("FOOD004", "Bánh ngọt", 40000, 0.0f, "banhngot.jpg", false, "CAT02"));
            } else if (categoryId.equals("CAT03")) {
                foodList.add(new Food("FOOD005", "Mì Ý", 60000, 0.0f, "miy.jpg", true, "CAT03"));
            }
        }


        for (Food food : foodList) {
            model.addRow(new Object[]{
                food.getId(),
                food.getName(),
                food.getUnitPrice(),
                food.getDiscount(),
                food.getImage(),
                food.isAvailable() ? "Sẵn có" : "Hết hàng"
            });
        }
    }

    // Phương thức hiển thị thông tin Food lên form
    private void setFoodForm(Food food) {
        txtMa.setText(food.getId());
        txtFoodName.setText(food.getName());
        txtGia.setText(String.valueOf(food.getUnitPrice()));
        sliDiscount.setValue((int) (food.getDiscount() * 100)); // Slider thường dùng int
        if (food.isAvailable()) {
            rdoAvaildable.setSelected(true);
        } else {
            rdoHetHang.setSelected(true);
        }
        if (food.getImage() != null && !food.getImage().isEmpty()) {
            displayImage(food.getImage());
            currentImagePath = food.getImage();
        } else {
            lblImage.setIcon(null); // Xóa ảnh nếu không có
            currentImagePath = "";
        }
    }

    // Phương thức lấy thông tin Food từ form
    private Food getFoodForm() {
        Food food = new Food();
        food.setId(txtMa.getText());
        food.setName(txtFoodName.getText());
        try {
            food.setUnitPrice(Double.parseDouble(txtGia.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá không hợp lệ. Vui lòng nhập số.");
            return null;
        }
        food.setDiscount(sliDiscount.getValue() / 100.0f); // Chuyển đổi từ int về float/double
        food.setAvailable(rdoAvaildable.isSelected());
        food.setImage(currentImagePath);

        // Lấy categoryId từ tblLoaiThucAn
        int selectedCategoryRow = tblLoaiThucAn.getSelectedRow();
        if (selectedCategoryRow != -1) {
            food.setCategoryId(categoryList.get(selectedCategoryRow).getId());
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một loại thức ăn.");
            return null;
        }
        return food;
    }

    // Phương thức hiển thị thông tin FoodCategory lên form
    private void setCategoryForm(FoodCategories category) {
        txtMaLoai.setText(category.getId());
        txtTenLoai.setText(category.getName());
        // Ở đây không có Description trên form, nếu có thì thêm vào
        // txtMoTaLoai.setText(category.getDescription());
    }

    // Phương thức lấy thông tin FoodCategory từ form
    private FoodCategories getCategoryForm() {
        FoodCategories category = new FoodCategories();
        category.setId(txtMaLoai.getText());
        category.setName(txtTenLoai.getText());
        // Nếu có Description trên form, thì thêm vào
        // category.setDescription(txtMoTaLoai.getText());
        return category;
    }

    // Phương thức chọn ảnh
    private void selectImage() {
        JFileChooser fileChooser = new JFileChooser("src/poly/cafe/icons"); // Đường dẫn mặc định đến thư mục icons
        fileChooser.setDialogTitle("Chọn ảnh món ăn");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg", "gif"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Lưu tên file để hiển thị hoặc lưu vào DB
            currentImagePath = selectedFile.getName();
            displayImage(currentImagePath);
        }
    }

    // Phương thức hiển thị ảnh lên JLabel
    private void displayImage(String imageName) {
        if (imageName != null && !imageName.isEmpty()) {
            String imagePath = "src/poly/cafe/icons/" + imageName; // Giả định ảnh nằm trong thư mục icons
            ImageIcon icon = new ImageIcon(imagePath);
            // Thay đổi kích thước ảnh để vừa với JLabel
            java.awt.Image image = icon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), java.awt.Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(image));
        } else {
            lblImage.setIcon(null);
        }
    }

    // Phương thức xóa trắng form Food
    private void clearFoodForm() {
        txtMa.setText("");
        txtFoodName.setText("");
        txtGia.setText("");
        sliDiscount.setValue(0);
        rdoAvaildable.setSelected(true);
        lblImage.setIcon(null);
        currentImagePath = "";
    }

    // Phương thức xóa trắng form FoodCategory
    private void clearCategoryForm() {
        txtMaLoai.setText("");
        txtTenLoai.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLoaiThucAn = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThucAn = new javax.swing.JTable();
        btnCheckAll = new javax.swing.JButton();
        btnUnCheckAll = new javax.swing.JButton();
        btnDeleteCheckedItems = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtFoodName = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        rdoAvaildable = new javax.swing.JRadioButton();
        rdoHetHang = new javax.swing.JRadioButton();
        sliDiscount = new javax.swing.JSlider();
        btnCreate = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cboLoaiThucAn = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        btnCreate1 = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        btnUpdate1 = new javax.swing.JButton();
        btnReset1 = new javax.swing.JButton();
        txtMaLoai = new javax.swing.JTextField();
        txtTenLoai = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tblLoaiThucAn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Loại thức ăn"
            }
        ));
        tblLoaiThucAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiThucAnMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLoaiThucAn);

        tblThucAn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Tên thức ăn", "Gía", "giảm giá", "Hình ảnh", "Trạng thái"
            }
        ));
        jScrollPane2.setViewportView(tblThucAn);

        btnCheckAll.setText("Chọn tất cả");
        btnCheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckAllActionPerformed(evt);
            }
        });

        btnUnCheckAll.setText("Bỏ chọn tất cả");
        btnUnCheckAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnCheckAllActionPerformed(evt);
            }
        });

        btnDeleteCheckedItems.setText("Xóa các mục chọn");
        btnDeleteCheckedItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCheckedItemsActionPerformed(evt);
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCheckAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUnCheckAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteCheckedItems)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteCheckedItems)
                    .addComponent(btnUnCheckAll)
                    .addComponent(btnCheckAll))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("BẢNG CHỨA ĐỒ ĂN THỨC UỐNG SẴN CÓ", jPanel2);

        jTabbedPane1.addTab("DANH SÁCH", jTabbedPane2);

        lblImage.setBackground(new java.awt.Color(255, 102, 102));
        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });

        jLabel1.setText("Mã");

        jLabel2.setText("Tên thức ăn");

        jLabel3.setText("Trạng thái");

        jLabel4.setText("giá");

        jLabel7.setText("giảm giá");

        txtMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaActionPerformed(evt);
            }
        });

        txtFoodName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFoodNameActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoAvaildable);
        rdoAvaildable.setText("Sẵn có");

        buttonGroup1.add(rdoHetHang);
        rdoHetHang.setText("Hết hàng");

        btnCreate.setText("Tạo mới");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
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

        jLabel8.setText("Loại");

        cboLoaiThucAn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(161, 161, 161)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(165, 165, 165)
                                        .addComponent(jLabel8))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(rdoAvaildable)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdoHetHang))
                                    .addComponent(txtFoodName, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(sliDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboLoaiThucAn, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnCreate)
                        .addGap(2, 2, 2)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFoodName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoAvaildable)
                            .addComponent(rdoHetHang))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sliDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboLoaiThucAn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnReset)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate))
                .addContainerGap(124, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("NHẬP LIỆU 1", jPanel3);

        btnCreate1.setText("Tạo mới");
        btnCreate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreate1ActionPerformed(evt);
            }
        });

        btnDelete1.setText("Xóa");
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });

        btnUpdate1.setText("Cập nhật");
        btnUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate1ActionPerformed(evt);
            }
        });

        btnReset1.setText("Nhập mới");
        btnReset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReset1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Mã loại");

        jLabel6.setText("Tên loại");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnCreate1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete1)
                .addGap(18, 18, 18)
                .addComponent(btnReset1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtTenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMaLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(134, 134, 134))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(14, 14, 14)
                .addComponent(txtTenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate1)
                    .addComponent(btnUpdate1)
                    .addComponent(btnDelete1)
                    .addComponent(btnReset1))
                .addGap(197, 197, 197))
        );

        jTabbedPane4.addTab("NHẬP LIỆU 2", jPanel4);

        jTabbedPane1.addTab("BIỂU MẪU", jTabbedPane4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFoodNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFoodNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFoodNameActionPerformed

    private void btnCheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckAllActionPerformed
         // Logic để chọn tất cả các món ăn trong bảng
        DefaultTableModel model = (DefaultTableModel) tblThucAn.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            // Nếu có cột checkbox, bạn sẽ set giá trị đó thành true
            // Hiện tại bảng không có cột checkbox, cần thêm vào nếu muốn dùng chức năng này
        }
        JOptionPane.showMessageDialog(this, "Chức năng 'Chọn tất cả' yêu cầu cột checkbox trong bảng món ăn.");
    }//GEN-LAST:event_btnCheckAllActionPerformed

    private void btnUnCheckAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnCheckAllActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblThucAn.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            // Nếu có cột checkbox, bạn sẽ set giá trị đó thành false
            // Hiện tại bảng không có cột checkbox, cần thêm vào nếu muốn dùng chức năng này
        }
        JOptionPane.showMessageDialog(this, "Chức năng 'Bỏ chọn tất cả' yêu cầu cột checkbox trong bảng món ăn.");
    }//GEN-LAST:event_btnUnCheckAllActionPerformed

    private void btnDeleteCheckedItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCheckedItemsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteCheckedItemsActionPerformed

    private void tblLoaiThucAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiThucAnMouseClicked
        int selectedRow = tblLoaiThucAn.getSelectedRow();
        if (selectedRow != -1) {
            FoodCategories selectedCategory = categoryList.get(selectedRow);
            setCategoryForm(selectedCategory); // Hiển thị thông tin loại lên form nhập liệu 2
            fillFoodTable(selectedCategory.getId()); // Hiển thị các món ăn thuộc loại này
            clearFoodForm(); // Xóa trắng form Food khi chọn loại mới
        }
    }//GEN-LAST:event_tblLoaiThucAnMouseClicked

    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked
        selectImage(); // Mở hộp thoại chọn ảnh khi click vào JLabel ảnh
    }//GEN-LAST:event_lblImageMouseClicked

    private void txtMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        Food newFood = getFoodForm();
        if (newFood != null) {
            foodDAO.insert(newFood); // Thêm vào DB
            JOptionPane.showMessageDialog(this, "Thêm món ăn thành công!");
            int selectedCategoryRow = tblLoaiThucAn.getSelectedRow();
            if (selectedCategoryRow != -1) {
                fillFoodTable(categoryList.get(selectedCategoryRow).getId()); // Cập nhật lại bảng
            }
            clearFoodForm();
        }
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Food updatedFood = getFoodForm();
        if (updatedFood != null) {
            foodDAO.update(updatedFood); // Cập nhật trong DB
            JOptionPane.showMessageDialog(this, "Cập nhật món ăn thành công!");
            int selectedCategoryRow = tblLoaiThucAn.getSelectedRow();
            if (selectedCategoryRow != -1) {
                fillFoodTable(categoryList.get(selectedCategoryRow).getId()); // Cập nhật lại bảng
            }
            clearFoodForm();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String foodId = txtMa.getText();
        if (foodId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã món ăn để xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa món ăn này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            foodDAO.delete(foodId); // Xóa trong DB
            JOptionPane.showMessageDialog(this, "Xóa món ăn thành công!");
            int selectedCategoryRow = tblLoaiThucAn.getSelectedRow();
            if (selectedCategoryRow != -1) {
                fillFoodTable(categoryList.get(selectedCategoryRow).getId()); // Cập nhật lại bảng
            }
            clearFoodForm();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        clearFoodForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnCreate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreate1ActionPerformed
        FoodCategories newCategory = getCategoryForm();
        if (newCategory != null) {
            foodCategoriesDAO.insert(newCategory); // Thêm vào DB
            JOptionPane.showMessageDialog(this, "Thêm loại thức ăn thành công!");
            fillCategoryTable(); // Cập nhật lại bảng loại
            clearCategoryForm();
        }
    }//GEN-LAST:event_btnCreate1ActionPerformed

    private void btnUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate1ActionPerformed
        FoodCategories updatedCategory = getCategoryForm();
        if (updatedCategory != null) {
            foodCategoriesDAO.update(updatedCategory); // Cập nhật trong DB
            JOptionPane.showMessageDialog(this, "Cập nhật loại thức ăn thành công!");
            fillCategoryTable(); // Cập nhật lại bảng loại
            clearCategoryForm();
        }
    }//GEN-LAST:event_btnUpdate1ActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        String categoryId = txtMaLoai.getText();
        if (categoryId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã loại để xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa loại thức ăn này? (Các món ăn thuộc loại này cũng sẽ bị xóa)", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            foodCategoriesDAO.delete(categoryId); // Xóa trong DB
            JOptionPane.showMessageDialog(this, "Xóa loại thức ăn thành công!");
            fillCategoryTable(); // Cập nhật lại bảng loại
            clearCategoryForm();
            fillFoodTable(null); // Xóa sạch bảng món ăn nếu category bị xóa
        }
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btnReset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReset1ActionPerformed
        clearCategoryForm();
    }//GEN-LAST:event_btnReset1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (tblLoaiThucAn.getRowCount() > 0) {
            tblLoaiThucAn.setRowSelectionInterval(0, 0); // Chọn dòng đầu tiên
            // Kích hoạt sự kiện mouseClicked để tự động điền dữ liệu và load bảng Food
            tblLoaiThucAnMouseClicked(null); // Truyền null vì chúng ta không có MouseEvent thực sự
        }
        // 2. Có thể đặt focus vào một trường nhập liệu cụ thể
        txtMaLoai.requestFocusInWindow();
        // 3. Hoặc hiển thị một thông báo chào mừng (ví dụ)
        // JOptionPane.showMessageDialog(this, "Chào mừng đến với quản lý đồ ăn!");
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
            java.util.logging.Logger.getLogger(FoodManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FoodManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FoodManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FoodManagerJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FoodManagerJDialog dialog = new FoodManagerJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnCreate1;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnDeleteCheckedItems;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnReset1;
    private javax.swing.JButton btnUnCheckAll;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdate1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboLoaiThucAn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JLabel lblImage;
    private javax.swing.JRadioButton rdoAvaildable;
    private javax.swing.JRadioButton rdoHetHang;
    private javax.swing.JSlider sliDiscount;
    private javax.swing.JTable tblLoaiThucAn;
    private javax.swing.JTable tblThucAn;
    private javax.swing.JTextField txtFoodName;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtMaLoai;
    private javax.swing.JTextField txtTenLoai;
    // End of variables declaration//GEN-END:variables
}

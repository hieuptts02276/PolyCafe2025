package poly.cafe.entity;

public class BillDetail {
    private Long id;
    private Long billId;
    private String drinkId;
    private double unitPrice;
    private double discount;
    private int quantity;
    private String drinkName;

    public BillDetail() {
    }

    public BillDetail(Long id, Long billId, String drinkId, double unitPrice, double discount, int quantity, String drinkName) {
        this.id = id;
        this.billId = billId;
        this.drinkId = drinkId;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.quantity = quantity;
        this.drinkName = drinkName;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getBillId() {
        return billId;
    }

    public String getDrinkId() {
        return drinkId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDrinkName() {
        return drinkName;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    @Override
    public String toString() {
        return "BillDetail{" +
                "id=" + id +
                ", billId=" + billId +
                ", drinkId='" + drinkId + '\'' +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", quantity=" + quantity +
                ", drinkName='" + drinkName + '\'' +
                '}';
    }
}

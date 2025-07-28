package poly.cafe.entity;

public class Drink {
    private String id;
    private String name;
    private String image = "product.png";
    private double unitPrice;
    private double discount;
    private boolean available;
    private String categoryId;

    public Drink() {
    }

    public Drink(String id, String name, String image, double unitPrice, double discount, boolean available, String categoryId) {
        this.id = id;
        this.name = name;
        this.image = (image != null && !image.isEmpty()) ? image : "product.png";
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.available = available;
        this.categoryId = categoryId;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getCategoryId() {
        return categoryId;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = (image != null && !image.isEmpty()) ? image : "product.png";
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", available=" + available +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}

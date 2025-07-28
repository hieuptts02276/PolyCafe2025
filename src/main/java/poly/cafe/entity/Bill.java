package poly.cafe.entity;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Bill {
    private Long id;
    private String username;
    private Integer cardId;
    private Date checkin = new Date();
    private Date checkout;
    private int status;

    public static final String DATE_PATTERN = "HH:mm:ss dd-MM-yyyy";

    public enum Status {
        Servicing, Completed, Canceled;
    }

    public Bill() {
    }

    public Bill(Long id, String username, Integer cardId, Date checkin, Date checkout, int status) {
        this.id = id;
        this.username = username;
        this.cardId = cardId;
        this.checkin = checkin != null ? checkin : new Date();
        this.checkout = checkout;
        this.status = status;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getCardId() {
        return cardId;
    }

    public Date getCheckin() {
        return checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public int getStatus() {
        return status;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return "Bill{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", cardId=" + cardId +
                ", checkin=" + (checkin != null ? sdf.format(checkin) : null) +
                ", checkout=" + (checkout != null ? sdf.format(checkout) : null) +
                ", status=" + status +
                '}';
    }
}

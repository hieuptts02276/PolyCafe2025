package poly.cafe.entity;

public class Card {
    private Integer id;
    private int status;

    public enum Status {
        Operating, Error, Lose;
    }

    public Card() {
    }

    public Card(Integer id, int status) {
        this.id = id;
        this.status = status;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}

package poly.cafe.entity;

import java.util.Date;

public class User {
    private String username;
    private String password;
    private boolean enabled;
    private String fullname;
    private String photo = "photo.png";
    private Boolean manager;

    public User() {
    }

    public User(String username, String password, boolean enabled, String fullname, String photo, Boolean manager) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.fullname = fullname;
        this.photo = (photo != null && !photo.isEmpty()) ? photo : "photo.png";
        this.manager = manager;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhoto() {
        return photo;
    }

    public Boolean getManager() {
        return manager;
    }

    // New method
    public boolean isManager() {
        return Boolean.TRUE.equals(manager);
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setPhoto(String photo) {
        this.photo = (photo != null && !photo.isEmpty()) ? photo : "photo.png";
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                ", enabled=" + enabled +
                ", fullname='" + fullname + '\'' +
                ", photo='" + photo + '\'' +
                ", manager=" + manager +
                '}';
    }

    // Static method để dễ gọi builder
    public static Builder builder() {
        return new Builder();
    }
    // Manual Builder class
    public static class Builder {
        private String username;
        private String password;
        private boolean enabled;
        private String fullname;
        private String photo;
        private Boolean manager;

        public Builder() {
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder fullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public Builder photo(String photo) {
            this.photo = photo;
            return this;
        }

        public Builder manager(Boolean manager) {
            this.manager = manager;
            return this;
        }

        public User build() {
            return new User(username, password, enabled, fullname,
                (photo != null && !photo.isEmpty()) ? photo : "photo.png",
                manager);
        }
    }
    private java.util.Date createdDate;

public Date getCreatedDate() {
    return createdDate;
}

public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
}

}

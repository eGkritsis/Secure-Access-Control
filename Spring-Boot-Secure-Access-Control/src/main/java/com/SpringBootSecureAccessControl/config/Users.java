package com.SpringBootSecureAccessControl.config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Users {
    @Id
    private Long id;
    private String username;
    private String password;
    private String description;
    private String salt;

    @Column(name = "account_non_locked")
    private boolean account_non_locked;

    @Column(name = "last_password_change")
    private LocalDateTime lastPasswordChange;

    public LocalDateTime getLastPasswordChange() {
        return lastPasswordChange;
    }
    public void setLastPasswordChange(LocalDateTime lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public int getLogin_attempts() {
        return login_attempts;
    }

    @Column(name = "login_attempts")
    private int login_attempts;

    public String getSalt() {
        return this.salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", account_non_locked=" + account_non_locked +
                ", login_attempts=" + login_attempts +
                '}';
    }

    public boolean isAccountNonLocked() {
        return this.account_non_locked;
    }

    public void setAccountNonLocked(boolean value) {
        this.account_non_locked = value;
    }
}

package com.expensemanager.project.dtos;

public class AccountRegisterDTO {
    private String username;
    private String password;
    private String rePassword;

    public AccountRegisterDTO(String username, String password, String rePassword) {
        setUsername(username);
        setPassword(password);
        setRePassword(rePassword);
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

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}

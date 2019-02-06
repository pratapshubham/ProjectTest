package com.example.braintech.projecttest.Model;

public class UserModel {

    private String name;
    private String email;
    private String state;
    private String city;
    private String mobile;
    private String password;

    public UserModel()
    {

    }

    public UserModel (String name, String email, String state, String city,String mobile,String password)
    {
        this.name = name;
        this.email = email;
        this.state = state;
        this.city = city;
        this.mobile = mobile;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

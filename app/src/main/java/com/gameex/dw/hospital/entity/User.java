package com.gameex.dw.hospital.entity;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class User extends LitePalSupport {
    private int id;
    private String userName;
    private String pwd;
    private String u_trueName;
    private String u_email;
    private String u_state;
    private Role role;
    private int r_id;

    public User() {
    }

    public User(int id, String userName, String pwd) {
        this.id = id;
        this.userName = userName;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getU_trueName() {
        return u_trueName;
    }

    public void setU_trueName(String u_trueName) {
        this.u_trueName = u_trueName;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_state() {
        return u_state;
    }

    public void setU_state(String u_state) {
        this.u_state = u_state;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        List<Role> roleList = LitePal.findAll(Role.class);
        for (Role role : roleList){
            if (role.getId() == r_id){
                this.r_id = r_id;
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", u_trueName='" + u_trueName + '\'' +
                ", u_email='" + u_email + '\'' +
                ", u_state='" + u_state + '\'' +
                ", role=" + role +
                ", r_id=" + r_id +
                '}';
    }
}

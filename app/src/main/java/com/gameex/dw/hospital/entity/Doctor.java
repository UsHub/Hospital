package com.gameex.dw.hospital.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends LitePalSupport {
    private int id;
    private String d_name;
    private String d_idCard;
    private String d_phone;
    private String d_telPhone;
    private int d_sex;
    private String d_birthday;
    private int d_age;
    private String d_email;
    private String d_keshi;
    private String d_xueli;
    private String d_descr;
    private String d_inTime;
    private int d_state;
    private List<Hosregister> hosregisters = new ArrayList<Hosregister>();

    public Doctor(){}

    public Doctor(String d_name, String d_keshi) {
        this.d_name = d_name;
        this.d_keshi = d_keshi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_idCard() {
        return d_idCard;
    }

    public void setD_idCard(String d_idCard) {
        this.d_idCard = d_idCard;
    }

    public String getD_phone() {
        return d_phone;
    }

    public void setD_phone(String d_phone) {
        this.d_phone = d_phone;
    }

    public String getD_telPhone() {
        return d_telPhone;
    }

    public void setD_telPhone(String d_telPhone) {
        this.d_telPhone = d_telPhone;
    }

    public int getD_sex() {
        return d_sex;
    }

    public void setD_sex(int d_sex) {
        this.d_sex = d_sex;
    }

    public String getD_birthday() {
        return d_birthday;
    }

    public void setD_birthday(String d_birthday) {
        this.d_birthday = d_birthday;
    }

    public int getD_age() {
        return d_age;
    }

    public void setD_age(int d_age) {
        this.d_age = d_age;
    }

    public String getD_email() {
        return d_email;
    }

    public void setD_email(String d_email) {
        this.d_email = d_email;
    }

    public String getD_keshi() {
        return d_keshi;
    }

    public void setD_keshi(String d_keshi) {
        this.d_keshi = d_keshi;
    }

    public String getD_xueli() {
        return d_xueli;
    }

    public void setD_xueli(String d_xueli) {
        this.d_xueli = d_xueli;
    }

    public String getD_descr() {
        return d_descr;
    }

    public void setD_descr(String d_descr) {
        this.d_descr = d_descr;
    }

    public String getD_inTime() {
        return d_inTime;
    }

    public void setD_inTime(String d_inTime) {
        this.d_inTime = d_inTime;
    }

    public int getD_state() {
        return d_state;
    }

    public void setD_state(int d_state) {
        this.d_state = d_state;
    }

    public List<Hosregister> getHosregisters() {
        return hosregisters;
    }

    public void setHosregisters(List<Hosregister> hosregisters) {
        this.hosregisters = hosregisters;
    }
}

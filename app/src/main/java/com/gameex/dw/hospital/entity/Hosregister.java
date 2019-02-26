package com.gameex.dw.hospital.entity;

import org.litepal.crud.LitePalSupport;

public class Hosregister extends LitePalSupport {

    private int id;
    private String hosR_name;
    private String hosR_idCard;
    private String hosR_medical;
    private double hosR_regPrice;
    private String hosR_phone;
    private int hosR_selfPrice;
    private int hosR_sex;
    private int hosR_age;
    private String hosR_work;
    private int hosR_lookDoctor;
    private String hosR_createTime;
    private String hosR_remark;
    private int hosR_state;
    private Doctor doctor;
    private int d_id;

    public Hosregister(){}

    public Hosregister(String hosR_createTime, int hosR_state, int d_id) {
        this.hosR_createTime = hosR_createTime;
        this.hosR_state = hosR_state;
        this.d_id = d_id;
    }

    public Hosregister(String hosR_name, String hosR_idCard, String hosR_medical,
                       double hosR_regPrice, String hosR_phone, int hosR_selfPrice,
                       int hosR_sex, int hosR_age, String hosR_work,
                       int hosR_lookDoctor, String hosR_remark,String hosR_createTime) {
        this.hosR_name = hosR_name;
        this.hosR_idCard = hosR_idCard;
        this.hosR_medical = hosR_medical;
        this.hosR_regPrice = hosR_regPrice;
        this.hosR_phone = hosR_phone;
        this.hosR_selfPrice = hosR_selfPrice;
        this.hosR_sex = hosR_sex;
        this.hosR_age = hosR_age;
        this.hosR_work = hosR_work;
        this.hosR_lookDoctor = hosR_lookDoctor;
        this.hosR_remark = hosR_remark;
        this.hosR_createTime=hosR_createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHosR_name() {
        return hosR_name;
    }

    public void setHosR_name(String hosR_name) {
        this.hosR_name = hosR_name;
    }

    public String getHosR_idCard() {
        return hosR_idCard;
    }

    public void setHosR_idCard(String hosR_idCard) {
        this.hosR_idCard = hosR_idCard;
    }

    public String getHosR_medical() {
        return hosR_medical;
    }

    public void setHosR_medical(String hosR_medical) {
        this.hosR_medical = hosR_medical;
    }

    public double getHosR_regPrice() {
        return hosR_regPrice;
    }

    public void setHosR_regPrice(double hosR_regPrice) {
        this.hosR_regPrice = hosR_regPrice;
    }

    public String getHosR_phone() {
        return hosR_phone;
    }

    public void setHosR_phone(String hosR_phone) {
        this.hosR_phone = hosR_phone;
    }

    public int getHosR_selfPrice() {
        return hosR_selfPrice;
    }

    public void setHosR_selfPrice(int hosR_selfPrice) {
        this.hosR_selfPrice = hosR_selfPrice;
    }

    public int getHosR_sex() {
        return hosR_sex;
    }

    public void setHosR_sex(int hosR_sex) {
        this.hosR_sex = hosR_sex;
    }

    public int getHosR_age() {
        return hosR_age;
    }

    public void setHosR_age(int hosR_age) {
        this.hosR_age = hosR_age;
    }

    public String getHosR_work() {
        return hosR_work;
    }

    public void setHosR_work(String hosR_work) {
        this.hosR_work = hosR_work;
    }

    public int getHosR_lookDoctor() {
        return hosR_lookDoctor;
    }

    public void setHosR_lookDoctor(int hosR_lookDoctor) {
        this.hosR_lookDoctor = hosR_lookDoctor;
    }

    public String getHosR_createTime() {
        return hosR_createTime;
    }

    public void setHosR_createTime(String hosR_createTime) {
        this.hosR_createTime = hosR_createTime;
    }

    public String getHosR_remark() {
        return hosR_remark;
    }

    public void setHosR_remark(String hosR_remark) {
        this.hosR_remark = hosR_remark;
    }

    public int getHosR_state() {
        return hosR_state;
    }

    public void setHosR_state(int hosR_state) {
        this.hosR_state = hosR_state;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
//        List<Doctor> doctorList = LitePal.findAll(Doctor.class);
//        for (Doctor doctor : doctorList){
//            if (doctor.getD_name() == d_name){
//                this.d_id = doctor.getId();
//                break;
//            }
//        }

        this.d_id = d_id;
    }
}

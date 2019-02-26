package com.gameex.dw.hospital.daoImpl;

import com.gameex.dw.hospital.dao.RegistrationDao;
import com.gameex.dw.hospital.entity.Doctor;
import com.gameex.dw.hospital.entity.Hosregister;
import com.gameex.dw.hospital.entity.Information;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class RegistrationDaoImpl implements RegistrationDao {


    @Override
    public List<Information> selectInformation(Integer hosR_id, String d_name, String d_keshi) {
        List<Information> informationList = new ArrayList<Information>();
        if (hosR_id != null) {
            List<Hosregister> hosregisters = LitePal.where("id = ?", hosR_id.toString()).find(Hosregister.class);
            for (Hosregister hosregister : hosregisters) {
                Integer d_id = hosregister.getD_id();
                List<Doctor> doctors = LitePal.select("d_name", "d_keshi").where("id = ?", d_id.toString()).find(Doctor.class);
                for (Doctor doctor : doctors) {
                    Information information = new Information();
                    information.setHosR_id(hosR_id);
                    information.setD_name(doctor.getD_name());
                    information.setHosR_state(hosregister.getHosR_state());
                    information.setD_keshi(doctor.getD_keshi());
                    information.setHosR_time(hosregister.getHosR_createTime());

                    information.setHosR_name(hosregister.getHosR_name());
                    information.setHosR_idCard(hosregister.getHosR_idCard());
                    information.setHosR_medical(hosregister.getHosR_medical());
                    information.setHosR_regPrice(hosregister.getHosR_regPrice());
                    information.setHosR_phone(hosregister.getHosR_phone());
                    information.setHosR_selfPrice(hosregister.getHosR_selfPrice());
                    information.setHosR_sex(hosregister.getHosR_sex());
                    information.setHosR_age(hosregister.getHosR_age());
                    information.setHosR_work(hosregister.getHosR_work());
                    information.setHosR_lookDoctor(hosregister.getHosR_lookDoctor());
                    information.setHosR_remark(hosregister.getHosR_remark());
                    informationList.add(information);
                }
            }
        } else if (!d_name.isEmpty()) {
            List<Doctor> doctors = LitePal.select("id", "d_name", "d_keshi").where("d_name like ?", "%" + d_name + "%").find(Doctor.class);
            for (Doctor doctor : doctors) {
                Integer d_id = doctor.getId();
                List<Hosregister> hosregisters = LitePal.where("d_id = ?", d_id.toString()).find(Hosregister.class);
                for (Hosregister hosregister : hosregisters) {
                    Information information = new Information();
                    information.setHosR_id(hosregister.getId());
                    information.setD_name(doctor.getD_name());
                    information.setHosR_state(hosregister.getHosR_state());
                    information.setD_keshi(doctor.getD_keshi());
                    information.setHosR_time(hosregister.getHosR_createTime());
                    information.setHosR_name(hosregister.getHosR_name());
                    information.setHosR_idCard(hosregister.getHosR_idCard());
                    information.setHosR_medical(hosregister.getHosR_medical());
                    information.setHosR_regPrice(hosregister.getHosR_regPrice());
                    information.setHosR_phone(hosregister.getHosR_phone());
                    information.setHosR_selfPrice(hosregister.getHosR_selfPrice());
                    information.setHosR_sex(hosregister.getHosR_sex());
                    information.setHosR_age(hosregister.getHosR_age());
                    information.setHosR_work(hosregister.getHosR_work());
                    information.setHosR_lookDoctor(hosregister.getHosR_lookDoctor());
                    information.setHosR_remark(hosregister.getHosR_remark());
                    informationList.add(information);
                }
            }
        } else if (!d_keshi.isEmpty()) {
            List<Doctor> doctors = LitePal.where("d_keshi like ?", "%" + d_keshi + "%").find(Doctor.class);
            for (Doctor doctor : doctors) {
                Integer d_id = doctor.getId();
                List<Hosregister> hosregisters = LitePal.select("id", "hosR_state", "hosR_createTime").where("d_id = ?", d_id.toString()).find(Hosregister.class);
                for (Hosregister hosregister : hosregisters) {
                    Information information = new Information();
                    information.setHosR_id(hosregister.getId());
                    information.setD_name(doctor.getD_name());
                    information.setHosR_state(hosregister.getHosR_state());
                    information.setD_keshi(doctor.getD_keshi());
                    information.setHosR_time(hosregister.getHosR_createTime());
                    information.setHosR_name(hosregister.getHosR_name());
                    information.setHosR_idCard(hosregister.getHosR_idCard());
                    information.setHosR_medical(hosregister.getHosR_medical());
                    information.setHosR_regPrice(hosregister.getHosR_regPrice());
                    information.setHosR_phone(hosregister.getHosR_phone());
                    information.setHosR_selfPrice(hosregister.getHosR_selfPrice());
                    information.setHosR_sex(hosregister.getHosR_sex());
                    information.setHosR_age(hosregister.getHosR_age());
                    information.setHosR_work(hosregister.getHosR_work());
                    information.setHosR_lookDoctor(hosregister.getHosR_lookDoctor());
                    information.setHosR_remark(hosregister.getHosR_remark());
                    informationList.add(information);
                }
            }
        } else {
            informationList = selectAll();
        }
        return informationList;
    }

    @Override
    public int insert(Hosregister hosregister, String d_name, String d_kehsi) {
        //返回值为0代表添加失败
        if (hosregister == null || d_name.equals("") || d_kehsi.equals("")) {
            return 0;
        } else {
            List<Doctor> doctorList = LitePal.select("id").
                    where("d_name = ? and d_keshi=?", d_name, d_kehsi).find(Doctor.class);
            for (Doctor doctor : doctorList) {
                hosregister.setD_id(doctor.getId());
                hosregister.save();
                return 1;
            }
            return 0;
        }

    }

    @Override
    public List<Information> selectAll() {
        List<Information> informationList = new ArrayList<Information>();
        List<Hosregister> hosregisters = LitePal.findAll(Hosregister.class);

        for (Hosregister hosregister : hosregisters) {
            String d_id = String.valueOf(hosregister.getD_id());
            List<Doctor> doctors = LitePal.select("d_name", "d_keshi").where("id = ?", d_id).find(Doctor.class);
            for (Doctor doctor : doctors) {
                Information information = new Information();
                information.setHosR_id(hosregister.getId());
                information.setD_name(doctor.getD_name());
                information.setHosR_state(hosregister.getHosR_state());
                information.setD_keshi(doctor.getD_keshi());
                information.setHosR_time(hosregister.getHosR_createTime());
                information.setHosR_name(hosregister.getHosR_name());
                information.setHosR_idCard(hosregister.getHosR_idCard());
                information.setHosR_medical(hosregister.getHosR_medical());
                information.setHosR_regPrice(hosregister.getHosR_regPrice());
                information.setHosR_phone(hosregister.getHosR_phone());
                information.setHosR_selfPrice(hosregister.getHosR_selfPrice());
                information.setHosR_sex(hosregister.getHosR_sex());
                information.setHosR_age(hosregister.getHosR_age());
                information.setHosR_work(hosregister.getHosR_work());
                information.setHosR_lookDoctor(hosregister.getHosR_lookDoctor());
                information.setHosR_remark(hosregister.getHosR_remark());
                informationList.add(information);
            }
        }
        return informationList;
    }

    @Override
    public int update(String d_name, String d_keshi, Integer hosR_id,Hosregister hosregister) {
        List<Doctor> doctorList = LitePal.select("id").where("d_name = ? and d_keshi = ?", d_name,d_keshi).find(Doctor.class);
        int res=-1;
        for (Doctor doctor : doctorList) {
            hosregister.setD_id(doctor.getId());
            String id = String.valueOf(hosR_id);
            res=hosregister.updateAll("id = ?", id);
        }
        return res;
    }

    @Override
    public int delete(Integer hosR_id) {
        String id = String.valueOf(hosR_id);
        int res=LitePal.deleteAll(Hosregister.class, "id = ?", id);
        return res;
    }
}

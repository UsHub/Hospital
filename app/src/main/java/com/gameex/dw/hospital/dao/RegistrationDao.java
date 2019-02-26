package com.gameex.dw.hospital.dao;

import com.gameex.dw.hospital.entity.Hosregister;
import com.gameex.dw.hospital.entity.Information;

import java.util.List;

public interface RegistrationDao {
    List<Information> selectInformation(Integer hosR_id, String d_name, String d_keshi);
    int insert(Hosregister hosregister, String d_name, String d_kehsi);
    List<Information> selectAll();
    int update(String d_name, String d_keshi, Integer hosR_id,Hosregister hosregister);
    int delete(Integer hosR_id);
}

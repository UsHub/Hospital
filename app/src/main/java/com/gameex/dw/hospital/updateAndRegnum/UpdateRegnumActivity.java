package com.gameex.dw.hospital.updateAndRegnum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.gameex.dw.hospital.R;
import com.gameex.dw.hospital.bottomBar.BottomBarActivity;
import com.gameex.dw.hospital.dao.RegistrationDao;
import com.gameex.dw.hospital.daoImpl.RegistrationDaoImpl;
import com.gameex.dw.hospital.entity.Hosregister;
import com.gameex.dw.hospital.entity.Information;
import com.gameex.dw.hospital.managePack.BaseActivity;
import com.gameex.dw.hospital.titleBar.OnViewClick;
import com.gameex.dw.hospital.titleBar.TitleBarView;

import org.litepal.LitePal;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class UpdateRegnumActivity extends BaseActivity {

    private String flag;
    private RegistrationDao mDao;

    private TitleBarView mTitleBarView;
    private String mTitleStr = "门诊挂号";
    private EditText hosR_nameET, hosR_idCardET, hosR_medicalET, hosR_regPriceET,
            hosR_phoneET, hosR_ageET, hosR_workET, hosR_remarkET;
    private RadioGroup selfPriceRG, sexRG, lookDoctorRG;
    private RadioButton selfPriceYesRB, selfPriceNoRB, sexManRB, sexWomanRB, lookDoctorFirstRB, lookDoctorSecondRB;
    private Integer selfPrice = 0, sex = 0, lookDoctor = 0;
    private Spinner keshiSp, nameSp;
    private String keshi, name;
    private Button regBtn;
    private ArrayAdapter<String> nameAdp;
    private Information information;

    public String getTitleStr() {
        return mTitleStr;
    }

    public void setTitleStr(String titleStr) {
        mTitleStr = titleStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_regnum_layout);

        initView();
        initListener();
    }

    private void initView() {
        mDao = new RegistrationDaoImpl();
        mTitleBarView = findViewById(R.id.update_regnum_title_bar);
        mTitleBarView.setTitle(getTitleStr());
        mTitleBarView.setLeftIVVisible(View.VISIBLE);
        mTitleBarView.setRightIVVisible(View.GONE);
        mTitleBarView.setTitleSize(23);

        hosR_nameET = findViewById(R.id.hosR_name_et_dur);
        hosR_idCardET = findViewById(R.id.hosR_idCard_et_dur);
        hosR_medicalET = findViewById(R.id.hosR_medical_et_dur);
        hosR_regPriceET = findViewById(R.id.hosR_regPrice_et_dur);
        hosR_phoneET = findViewById(R.id.hosR_phone_et_dur);
        hosR_ageET = findViewById(R.id.hosR_age_et_dur);
        hosR_workET = findViewById(R.id.hosR_work_et_dur);
        hosR_remarkET = findViewById(R.id.hosR_remark_et_dur);
        regBtn = findViewById(R.id.reg_btn_sur);
        selfPriceRG = findViewById(R.id.hosR_selfPrice_et_dur);
        selfPriceYesRB = findViewById(R.id.yes);
        selfPriceNoRB = findViewById(R.id.no);
        sexRG = findViewById(R.id.hosR_sex_et_dur);
        sexManRB = findViewById(R.id.man);
        sexWomanRB = findViewById(R.id.woman);
        lookDoctorRG = findViewById(R.id.hosR_lookDoctor_et_dur);
        lookDoctorFirstRB = findViewById(R.id.first);
        lookDoctorSecondRB = findViewById(R.id.second);
        nameSp = findViewById(R.id.d_name_sp_dur);
        keshiSp = findViewById(R.id.d_keshi_sp_dur);
        ArrayAdapter<String> keshiAdp = new ArrayAdapter<String>(this, R.layout.keshi_select_item, getResources().getStringArray(R.array.d_keshi));
        keshiAdp.setDropDownViewResource(R.layout.keshi_drop_item);
        keshiSp.setAdapter(keshiAdp);

        flag = getIntent().getStringExtra("flag");
        if (!TextUtils.isEmpty(flag)) {
            if (flag.equals("update")) {
                information = (Information) getIntent().getSerializableExtra("Update");
                if (information != null) {
                    initUpdateData();
                }
            } else if (flag.equals("detail")) {
                information = (Information) getIntent().getSerializableExtra("Detail");
                if (information != null) {
                    initDetailView();
                }
            }
        }
    }

    private void initListener() {
        mTitleBarView.setOnViewClick(new OnViewClick() {
            @Override
            public void leftClick() {
                finish();
            }

            @Override
            public void rightClick() {

            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hosR_regPrice = hosR_regPriceET.getText().toString();
                if (hosR_regPrice.isEmpty()) {
                    hosR_regPrice = "0";
                }
                String hosR_age = hosR_ageET.getText().toString();
                if (hosR_age.isEmpty()) {
                    hosR_age = "0";
                }
                Hosregister hosregister = new Hosregister(hosR_nameET.getText().toString(),
                        hosR_idCardET.getText().toString(), hosR_medicalET.getText().toString(),
                        Double.parseDouble(hosR_regPrice), hosR_phoneET.getText().toString(),
                        selfPrice, sex, Integer.parseInt(hosR_age), hosR_workET.getText().toString(),
                        lookDoctor, hosR_remarkET.getText().toString(), getNow());
                if (regBtn.getText().toString().equals("挂  号")) {
                    int result = mDao.insert(hosregister, name, keshi);
                    if (result == 1) {
                        Toast.makeText(UpdateRegnumActivity.this, "挂号成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent("com.gameex.dw.hospital.bottomBar.NOTIFY_INSERT_DATA");
                        sendBroadcast(intent);
                        finish();
                    }
                } else {
                    int result = mDao.update(name, keshi, information.getHosR_id(), hosregister);
                    if (result == 1) {
                        Toast.makeText(UpdateRegnumActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent("com.gameex.dw.hospital.bottomBar.NOTIFY_UPDATE_DATA");
                        intent.putExtra("update_to_position", information.getHosR_id());
                        sendBroadcast(intent);
                        finish();
                    }
                }
            }
        });
        selfPriceRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(radioGroup.getCheckedRadioButtonId());
                if (rb.getText().toString().equals("否")) {
                    selfPrice = 1;
                } else {
                    selfPrice = 0;
                }
            }
        });
        sexRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(radioGroup.getCheckedRadioButtonId());
                if (rb.getText().toString().equals("女")) {
                    sex = 1;
                } else {
                    sex = 0;
                }
            }
        });
        lookDoctorRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(radioGroup.getCheckedRadioButtonId());
                if (rb.getText().toString().equals("复诊")) {
                    lookDoctor = 1;
                } else {
                    lookDoctor = 0;
                }
            }
        });
        nameSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                name = (String) nameSp.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        keshiSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                keshi = (String) keshiSp.getItemAtPosition(position);
                if (keshi.equals("骨科")) {
                    nameAdp = new ArrayAdapter<String>(UpdateRegnumActivity.this, R.layout.name_select_item, getResources().getStringArray(R.array.d_name_boot));
                    nameAdp.setDropDownViewResource(R.layout.name_drop_item);
                    nameSp.setAdapter(nameAdp);
                }
                if (keshi.equals("血液科")) {
                    nameAdp = new ArrayAdapter<String>(UpdateRegnumActivity.this, R.layout.name_select_item, getResources().getStringArray(R.array.d_name_blood));
                    nameAdp.setDropDownViewResource(R.layout.name_drop_item);
                    nameSp.setAdapter(nameAdp);
                }
                if (keshi.equals("神经科")) {
                    nameAdp = new ArrayAdapter<String>(UpdateRegnumActivity.this, R.layout.name_select_item, getResources().getStringArray(R.array.d_name_head));
                    nameAdp.setDropDownViewResource(R.layout.name_drop_item);
                    nameSp.setAdapter(nameAdp);
                }
                if (information != null && nameAdp != null) {
                    for (int i = 0; i < nameAdp.getCount(); i++) {
                        if (information.getD_name().equals(nameAdp.getItem(i).toString())) {
                            nameSp.setSelection(i, true);
                        }
                    }
                }
                if (!TextUtils.isEmpty(flag) && flag.equals("detail")) {
                    nameSp.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    /**
     * 加载更新页面，填装数据
     */
    private void initUpdateData() {
        mTitleBarView.setTitle("更  新");

        hosR_nameET.setText(information.getHosR_name());
        hosR_idCardET.setText(information.getHosR_idCard());
        hosR_medicalET.setText(information.getHosR_medical());
        hosR_regPriceET.setText("" + information.getHosR_regPrice());
        hosR_phoneET.setText(information.getHosR_phone());
        hosR_ageET.setText("" + information.getHosR_age());
        hosR_workET.setText(information.getHosR_work());
        hosR_remarkET.setText(information.getHosR_remark());
        regBtn.setText("更  新");
        selfPrice = information.getHosR_selfPrice();
        if (selfPrice != null && selfPrice == 1) {
            selfPriceNoRB.setChecked(true);
        }
        sex = information.getHosR_sex();
        if (sex != null && sex == 1) {
            sexWomanRB.setChecked(true);
        }
        lookDoctor = information.getHosR_lookDoctor();
        if (lookDoctor != null && lookDoctor == 1) {
            lookDoctorSecondRB.setChecked(true);
        }
        keshi = information.getD_keshi();
        if (keshi != null) {
            SpinnerAdapter keshiAdapter = keshiSp.getAdapter();
            for (int i = 0; i < keshiAdapter.getCount(); i++) {
                if (keshi.equals(keshiAdapter.getItem(i).toString())) {
                    keshiSp.setSelection(i, true);
                }
            }
        }
    }

    /**
     * 加载详情页面，填充数据，并设置不可更改
     */
    private void initDetailView() {
        mTitleBarView.setTitle("详  情");

        hosR_nameET.setText(information.getHosR_name());
        hosR_nameET.setEnabled(false);
        hosR_idCardET.setText(information.getHosR_idCard());
        hosR_idCardET.setEnabled(false);
        hosR_medicalET.setText(information.getHosR_medical());
        hosR_medicalET.setEnabled(false);
        hosR_regPriceET.setText("" + information.getHosR_regPrice());
        hosR_regPriceET.setEnabled(false);
        hosR_phoneET.setText(information.getHosR_phone());
        hosR_phoneET.setEnabled(false);
        hosR_ageET.setText("" + information.getHosR_age());
        hosR_ageET.setEnabled(false);
        hosR_workET.setText(information.getHosR_work());
        hosR_workET.setEnabled(false);
        hosR_remarkET.setText(information.getHosR_remark());
        hosR_remarkET.setEnabled(false);
        regBtn.setVisibility(View.GONE);
        selfPrice = information.getHosR_selfPrice();
        if (selfPrice != null) {
            if (selfPrice == 1) {
                selfPriceNoRB.setChecked(true);
                selfPriceNoRB.setEnabled(false);
                selfPriceYesRB.setVisibility(View.GONE);
            } else {
                selfPriceYesRB.setEnabled(false);
                selfPriceNoRB.setVisibility(View.GONE);
            }
        }
        sex = information.getHosR_sex();
        if (sex != null) {
            if (sex == 1) {
                sexWomanRB.setChecked(true);
                sexWomanRB.setEnabled(false);
                sexManRB.setVisibility(View.GONE);
            } else {
                sexManRB.setEnabled(false);
                sexWomanRB.setVisibility(View.GONE);
            }
        }
        lookDoctor = information.getHosR_lookDoctor();
        if (lookDoctor != null) {
            if (lookDoctor == 1) {
                lookDoctorSecondRB.setChecked(true);
                lookDoctorSecondRB.setEnabled(false);
                lookDoctorFirstRB.setVisibility(View.GONE);
            } else {
                lookDoctorFirstRB.setEnabled(false);
                lookDoctorSecondRB.setVisibility(View.GONE);
            }
        }
        keshi = information.getD_keshi();
        if (keshi != null) {
            SpinnerAdapter keshiAdapter = keshiSp.getAdapter();
            for (int i = 0; i < keshiAdapter.getCount(); i++) {
                if (keshi.equals(keshiAdapter.getItem(i).toString())) {
                    keshiSp.setSelection(i, true);
                }
            }
        }
        keshiSp.setEnabled(false);
    }

    public String getNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String hosr;
        if (calendar.get(Calendar.AM_PM) == 0) {
            hosr = String.valueOf(calendar.get(Calendar.HOUR));
        } else {
            hosr = String.valueOf(calendar.get(Calendar.HOUR) + 12);
        }
        return String.valueOf(calendar.get(Calendar.YEAR)) + "-" +
                String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" +
                String.valueOf(calendar.get(Calendar.DATE)) + " " +
                hosr + ":" + String.valueOf(calendar.get(Calendar.MINUTE)) + ":" +
                String.valueOf(calendar.get(Calendar.SECOND));
    }
}

package com.gameex.dw.hospital.sign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gameex.dw.hospital.R;
import com.gameex.dw.hospital.entity.User;
import com.gameex.dw.hospital.managePack.BaseActivity;

public class SignActivity extends BaseActivity implements View.OnClickListener {
    private EditText user, pwd;
    private Button sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        init();
    }

    private void init() {
        user = findViewById(R.id.username_sign);
        pwd = findViewById(R.id.password_sign);
        sign = findViewById(R.id.sign_btn);
        sign.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_btn:
                String userStr = user.getText().toString();
                String pwdStr = pwd.getText().toString();
                if (!userStr.equals("")) {
                    if (!pwdStr.equals("")) {
                        User user = new User();
                        user.setUserName(userStr);
                        user.setPwd(pwdStr);
                        if (user.isSaved()) {
                            Toast.makeText(this, "已经注册过了", Toast.LENGTH_SHORT).show();
                        } else {
                            user.save();
                            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "设置好密码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "先设置好用户名", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}

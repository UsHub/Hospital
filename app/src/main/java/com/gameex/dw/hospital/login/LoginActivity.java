package com.gameex.dw.hospital.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gameex.dw.hospital.R;
import com.gameex.dw.hospital.bottomBar.BottomBarActivity;
import com.gameex.dw.hospital.entity.Doctor;
import com.gameex.dw.hospital.entity.Hosregister;
import com.gameex.dw.hospital.entity.Information;
import com.gameex.dw.hospital.entity.Role;
import com.gameex.dw.hospital.entity.User;
import com.gameex.dw.hospital.managePack.BaseActivity;
import com.gameex.dw.hospital.sign.SignActivity;
import com.gameex.dw.hospital.util.LogUtil;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * @version 1.0
 * 第一组
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText userNameEdit, pwdEdit;
    private TextView forgotPwdText, signText;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        getDatabase();
    }

    /**
     * 初始化组件
     */
    protected void init() {
        userNameEdit = findViewById(R.id.username);
        pwdEdit = findViewById(R.id.password);

        forgotPwdText = findViewById(R.id.forgot_pwd);
        forgotPwdText.setOnClickListener(this);
        signText = findViewById(R.id.sign_up);
        signText.setOnClickListener(this);
        loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(this);
    }

    /**
     * 传教按数据库
     */
    protected void getDatabase() {
        LitePal.getDatabase();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                List<User> users = LitePal.where("userName = ?",
                        userNameEdit.getText().toString()).find(User.class);
                if (users.size() > 0) {
                    for (User user : users) {
                        if (user.getPwd().equals(pwdEdit.getText().toString())) {
                            users.clear();
                            Intent i = new Intent(this, BottomBarActivity.class);
                            startActivity(i);
                            finish();
                            break;
                        }
                    }
                } else {
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
                if (!users.isEmpty()) {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sign_up:
                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this, SignActivity.class);
                startActivity(intent);
                break;
            case R.id.forgot_pwd:
                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

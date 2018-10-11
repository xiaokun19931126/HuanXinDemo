package com.peidou.customerservicec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import static com.peidou.customerservicec.Constants.USERNAME;
import static com.peidou.customerservicec.Constants.PASSWORD;
import static com.peidou.customerservicec.Constants.ISLOGIN;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText usernameet;
    private EditText passwordet;
    private Button loginbtn;

    private String userNameStr;
    private String passwordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String userName = MyApp.getSp().getString(USERNAME, "");
        String password = MyApp.getSp().getString(PASSWORD, "");

        this.loginbtn = (Button) findViewById(R.id.login_btn);
        this.passwordet = (EditText) findViewById(R.id.password_et);
        this.usernameet = (EditText) findViewById(R.id.username_et);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameStr = usernameet.getText().toString().trim();
                passwordStr = passwordet.getText().toString().trim();
                login(userNameStr, passwordStr);
            }
        });
        usernameet.setText(userName);
        passwordet.setText(password);
        //自动登陆
        if (MyApp.getSp().getBoolean(ISLOGIN, false)) {
            login(userName, password);
        }
    }

    private void login(final String userName, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("登陆中...");
        progressDialog.show();

        EMClient.getInstance().login(userName, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                saveUserData(userName, password, true);
                progressDialog.dismiss();
                startActivity(new Intent(MainActivity.this, FindAContactActivity.class));
                finish();
            }

            @Override
            public void onError(int code, String error) {
                saveUserData(userName, password, false);
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "登陆失败:" + code + ";" + error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int progress, String status) {
                saveUserData(userName, password, false);
                progressDialog.dismiss();
                Log.e(TAG, "onProgress(" + TAG + ".java:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ")" + progress);
            }
        });
    }

    private void saveUserData(String userName, String password, boolean b) {
        MyApp.getSp().edit().putString(USERNAME, userName).commit();
        MyApp.getSp().edit().putString(PASSWORD, password).commit();
        MyApp.getSp().edit().putBoolean(ISLOGIN, b).commit();
    }

}

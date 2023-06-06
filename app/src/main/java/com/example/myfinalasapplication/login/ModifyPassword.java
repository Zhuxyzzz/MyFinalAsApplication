package com.example.myfinalasapplication.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinalasapplication.R;
import com.example.myfinalasapplication.dialog.ConfirmDialog;
import com.example.myfinalasapplication.util.CommenUtil;
import com.example.myfinalasapplication.util.NetUtil;
import com.example.myfinalasapplication.util.ViewUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class ModifyPassword extends AppCompatActivity {
    @BindView(R.id.modify_password_account)
    EditText accountEdit;
    @BindView(R.id.modify_password_phone)
    EditText phoneEdit;
    @BindView(R.id.modify_password_old_password)
    EditText oldPasswordEdit;
    @BindView(R.id.modify_password_new_password)
    EditText newPasswordEdit;

    private String account;
    private String phone;
    private String newPassword;
    private String oldPassword;
    private int userType = 0;

    private String id;

    private ConfirmDialog confirmDialog;

    Handler modifyHandler = new Handler(msg -> {
        if(msg.what == 1){
            Toast.makeText(this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
        }
        finish();
        return false;
    });

    Handler confirmHandler = new Handler(msg -> {
        if(msg.what == 1){//账户存在，开始修改密码
            //do something
          /*  confirmDialog = new ConfirmDialog(this,phone);
            confirmDialog.setConfirmSuccessListener(() -> {*/
                Map<String,String> map = new HashMap<>();
                map.put(userType == 1 ? "teacherId" : "studentId",id);
                map.put("password",newPassword);
                NetUtil.getNetData("account/modify"+ (userType == 1 ? "Teacher" : "Student"),map,modifyHandler);
           /* });
            confirmDialog.show();
            confirmDialog.resend.performClick();*/
            id = msg.getData().getString("message");
            return true;
        } else {
            Toast.makeText(this, "验证失败", Toast.LENGTH_SHORT).show();
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        ViewUtils.initActionBar(this,"修改密码");
    }

    /**
     *点击事件，先判断是否为空，再判断输入手机号是否正确
     * @param v 按钮组件
     */
    @OnClick(R.id.modify_password_submit)
    public void onClicked(View v){
        account = accountEdit.getText().toString();
        phone = phoneEdit.getText().toString();
        oldPassword = oldPasswordEdit.getText().toString();
        newPassword = newPasswordEdit.getText().toString();

        if(account.isEmpty() || phone.isEmpty()|| oldPassword.isEmpty() || newPassword.isEmpty() || userType == 0){
            Toast.makeText(this, "请补全信息", Toast.LENGTH_SHORT).show();
        } else if (!oldPassword.equals(newPassword)){
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
        } else{
            Map<String,String> map = new HashMap<>();
            map.put("type",String.valueOf(userType));
            map.put("account",account);
            map.put("phone",phone);

            NetUtil.getNetData("account/confirmAccount",map,confirmHandler);//根据账号、角色、手机号验证账户是否存在
        }
    }

    /**
     * 监控单选结果
    **/
    @OnCheckedChanged({R.id.modify_password_teacher,R.id.modify_password_student})
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.modify_password_teacher:
                if (ischanged) {
                    userType = 1;
                }
                break;
            case R.id.modify_password_student:
                if (ischanged) {
                    userType = 2;
                }
                break;
            default:
                userType = 0;
                break;
        }
    }

}
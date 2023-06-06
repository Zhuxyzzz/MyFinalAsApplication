package com.example.myfinalasapplication.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.myfinalasapplication.R;
import com.example.myfinalasapplication.dialog.LoadingDialog;
import com.example.myfinalasapplication.util.NetUtil;
import com.example.myfinalasapplication.util.ProjectStatic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {
   // private static final String TestApp="TestApp";

    @BindView(R.id.login_account)
    EditText accountEdit;
    @BindView(R.id.login_password)
    EditText passwordEdit;

    private int userType;
    /**
     * 是Android平台上一个轻量级的存储辅助类，用来保存应用的一些常用配置，
     * 它提供了String，set，int，long，float，boolean六种数据类型。
     * SharedPreferences的数据以键值对的进行保存在以xml形式的文件中。在应用中通常做一些简单数据的持久化缓存。
     */
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        //获取SharedPreferences对象（键值对值）;getSharedPreferences(,)第一个参数是存储时的名称，第二个参数则是文件的打开方式
        preferences = getSharedPreferences("localRecord",MODE_PRIVATE);
    }

    @OnClick({R.id.login_register,R.id.login_forget_password,R.id.login_button})
    public void onClicked(View view){
        Log.d("TestApp","HelloWorld");

      /*  if (!initPermission()){//权限不足
            return;
        }*/
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.login_register:
                intent.setAction(ProjectStatic.LOGIN_REGISTER);
                startActivity(intent);
                break;
            case R.id.login_forget_password:
                intent.setAction(ProjectStatic.MODIFY_PASSWORD);
                startActivity(intent);
                break;
            case R.id.login_button:
                if (userType == 0 || accountEdit.getText().toString().isEmpty() || passwordEdit.getText().toString().isEmpty()) {
                    Toast.makeText(this, "请补全登录信息", Toast.LENGTH_SHORT).show();
                    break;
                }
                LoadingDialog dialog = new LoadingDialog(view.getContext());
                dialog.setTitle("登录操作");
                dialog.setMessage(StringUtils.getString(R.string.wait_message));
                dialog.show();
                Map<String,String> map = new HashMap<>();
                Log.d("type", String.valueOf(userType));
                Log.d("account", accountEdit.getText().toString());
                Log.d("password", passwordEdit.getText().toString());

                map.put("type", String.valueOf(userType));
                map.put("account",accountEdit.getText().toString());
                map.put("password",passwordEdit.getText().toString());
                NetUtil.getNetData("account/login",map,new Handler(msg -> {
                    if (msg.what == 1){//返回1成功得到data数据
                        dialog.dismiss();
                        String data = msg.getData().getString("data");
                        Intent loginIntent = new Intent(ProjectStatic.MAIN);
                        updateLoginInfo(preferences, data,String.valueOf(userType));
                        Toast.makeText(this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(loginIntent);
                        finish();//RuntimeException超时检测
                    } else{
                        dialog.showSingleButton();
                        dialog.setMessage(msg.getData().getString("message"));
                    }
                    return false;
                }));

                break;
        }
    }

    /**
     * 监控单选结果
     **/
    @OnCheckedChanged({R.id.login_radio_teacher,R.id.login_radio_student})//事件触发注解标签(选中，选中取消)
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.login_radio_teacher:
                if (ischanged) {
                    userType = 1;
                }
                break;
            case R.id.login_radio_student:
                if (ischanged) {
                    userType = 2;
                }
                break;
            default:
                userType = 0;
                break;
        }
    }

    //    将此次的登录信息记录到SharedPreferences中
    public static void updateLoginInfo(SharedPreferences preferences, String data, String type){
        JSONObject account = JSONObject.parseObject(data);
        //获取Editor对象的引用
        SharedPreferences.Editor editor = preferences.edit();
        //将获取过来的值放入文件
        editor.putString("userType",type);
        if (type.equals("1")){
            editor.putString("id",account.getString("teacherId"));
            editor.putString("account",account.getString("teacherAccount"));
            editor.putString("password",account.getString("teacherPassword"));
            editor.putString("name",account.getString("teacherName"));
            editor.putBoolean("sex",account.getBoolean("teacherSex"));
            editor.putString("phone",account.getString("teacherPhone"));
            editor.putString("email",account.getString("teacherEmail"));
            editor.putString("avatar",account.getString("teacherAvatar"));
        } else {
            editor.putString("id",account.getString("studentId"));
            editor.putString("account",account.getString("studentAccount"));
            editor.putString("password",account.getString("studentPassword"));
            editor.putString("name",account.getString("studentName"));
            editor.putBoolean("sex",account.getBoolean("studentSex"));
            editor.putString("phone",account.getString("studentPhone"));
            editor.putString("email",account.getString("studentEmail"));
            editor.putString("avatar",account.getString("studentAvatar"));
            editor.putString("class",account.getString("studentClass"));
            editor.putString("face",account.getString("studentFace"));
        }
        //异步提交你的改变，来替换当前 SharedPreferences 对象中的任何内容。没有返回值
        editor.apply();
    }

    private boolean initPermission(){
        List<String> permissionList = new ArrayList<>();
        if (!PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE)){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!PermissionUtils.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        //申请权限，返回false
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            PermissionUtils.permission(permissions).request();
            return false;
        }
        //拥有所有权限，返回true
        return true;
    }
}
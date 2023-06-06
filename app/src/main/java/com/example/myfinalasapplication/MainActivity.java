package com.example.myfinalasapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
/**
 * NavController字面意思就是导航控制器，它负责操作Navigation框架下的Fragment的跳转与退出、动画、监听当前Fragment信息，当然这些是基本操作。
 * 但是更重要的是知道它可以使用的范围，一般情况下我们以为它只能在Fragment里调用，实际情况下它在Activity里也可以调用。灵活的使用它，可以帮你实现所有形式的页面跳转。
 * 除此之外你甚至还能使用TabLayout配合Navigation进行主页的分页设计。极端点你甚至还能在某个分页里再次添加TabLayout配合Navigation进行嵌套设计。
 */
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfinalasapplication.R;
import com.example.myfinalasapplication.fragment.main.NoticeFragment;
import com.example.myfinalasapplication.fragment.student.StudentCourseAttend;
import com.example.myfinalasapplication.fragment.student.StudentCourseInfo;
import com.example.myfinalasapplication.fragment.student.StudentCourseLeave;
import com.example.myfinalasapplication.login.LoginActivity;
import com.example.myfinalasapplication.fragment.main.InfoFragment;
import com.example.myfinalasapplication.fragment.main.CourseListFragment;
import com.example.myfinalasapplication.fragment.main.ModifyPasswordFragment;
import com.example.myfinalasapplication.student.StudentCourseDetail;
import com.example.myfinalasapplication.util.ProjectStatic;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.ui.companent.CircleImageView;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.action_bar_title)
    TextView titleText;
/*    @BindView(R.id.drawer_layout_teacher)
    DrawerLayout drawerLayout;
    @BindView(R.id.action_bar_avatar)
    CircleImageView imageView;*/


    private NavController controller;
    private SharedPreferences preferences;

    private FragmentManager fragmentManager;
    private CourseListFragment courseListFragment;
    private InfoFragment infoFragment;
    private  ModifyPasswordFragment modifyPasswordFragment;
    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        titleText.setText("首页");

        preferences = getSharedPreferences("localRecord",MODE_PRIVATE);

        fragmentManager = getSupportFragmentManager();
       // initNavigation();

      //  showCourseList();
      //  initBottomNavigation();
       /* Picasso.with(this)
                .load(ProjectStatic.SERVICE_PATH + preferences.getString("avatar",null))
                .fit()
                .error(R.drawable.ic_net_error)
                .into(imageView);*/
        navigationView = findViewById(R.id.student_main_bottom);
        navigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setSelectedItemId(R.id.student_menu_main);
    }
    @SuppressLint("ResourceType")
    private void initBottomNavigation(){
        BottomNavigationView navigationView = findViewById(R.id.student_main_bottom);
        NavController controller = Navigation.findNavController(this,R.id.fragment_main_course);
        NavigationUI.setupWithNavController(navigationView,controller);

    }
 /*   public void showCourseList(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();////开启一个事务
        courseListFragment = new CourseListFragment();
        transaction.add(R.id.fragment_main_course,courseListFragment);//往布局id对应位置添加一个Fragment
        transaction.commit();//提交一个事务
    }
*/
    @Override
    public boolean onSupportNavigateUp() {//重写onSupportNavigateUp方法去启动fragment
        controller = Navigation.findNavController(this,R.id.fragment_main_course);//获取NavController实例；fragment_main是Activity布局里fragment控件的id
        return controller.navigateUp();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()){
            case R.id.student_menu_main:
              fragmentTransaction.replace(R.id.fragment_main_course, new CourseListFragment()).commit();
                return true;
            case R.id.student_menu_notice:
                fragmentTransaction.replace(R.id.fragment_main_course,new NoticeFragment()).commit();
                return true;
            case R.id.student_menu_note:
                fragmentTransaction.replace(R.id.fragment_main_course,new InfoFragment()).commit();
                return true;
            case R.id.student_menu_info:
                fragmentTransaction.replace(R.id.fragment_main_course,new InfoFragment()).commit();
                return true;
        }
        return true;
    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.student_main_bottom://首页
                Log.d("进入onclick的首页","点student_main_bottom");
                break;
            case R.id.student_menu_attend://消息
                Log.d("进入onclick的消息","点击student_menu_attend");
                setTabSelection(0);
             *//*   person_view.setVisibility(View.VISIBLE);
                car_view.setVisibility(View.INVISIBLE);*//*
                break;
            case R.id.student_menu_leave://笔记
                setTabSelection(1);*//*
                person_view.setVisibility(View.INVISIBLE);
                car_view.setVisibility(View.VISIBLE);*//*
                break;
            case R.id.student_menu_info://我
                setTabSelection(1);*//*
                person_view.setVisibility(View.INVISIBLE);
                car_view.setVisibility(View.VISIBLE);*//*
                break;
            default:
                break;
        }
    }

    private void setTabSelection(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (index){
            case 0:
                if(infoFragment == null){
                    infoFragment = new InfoFragment();
                    fragmentTransaction.add(R.id.fragment_main_course,infoFragment);
                }else {
                    fragmentTransaction.show(infoFragment);
                }
                break;
            case 1:
                if(modifyPasswordFragment == null){
                    modifyPasswordFragment = new ModifyPasswordFragment();
                    fragmentTransaction.add(R.id.fragment_main_course,modifyPasswordFragment);
                }else {
                    fragmentTransaction.show(modifyPasswordFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if(modifyPasswordFragment != null){
            fragmentTransaction.hide(modifyPasswordFragment);
        }
        if(infoFragment != null){
            fragmentTransaction.hide(infoFragment);
        }
    }*/

  /*  @OnClick({R.id.action_bar_avatar})
    public void onClicked(View view){
        switch (view.getId()){
            case R.id.action_bar_avatar:
//                设置标题栏左部小头像的点击事件
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }*/

/*    private void initNavigation(){
        NavigationView navigationView = findViewById(R.id.navigation_view);

        View view = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView userName = view.findViewById(R.id.drawer_header_userName);
        CircleImageView headerImage = view.findViewById(R.id.drawer_header_avatar);
        Picasso.with(this)
                .load(ProjectStatic.SERVICE_PATH + preferences.getString("avatar",null))
                .fit()
                .error(R.drawable.ic_net_error)
                .into(headerImage);
        userName.setText("欢迎您！" + preferences.getString("name",null));

        navigationView.setCheckedItem(R.id.main_menu_course);
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
//            执行具体跳转fragment的操作
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            hideAllFragment();
            switch (item.getItemId()){
                case R.id.main_menu_course:
                    titleText.setText("课程列表");
                    if (courseListFragment == null){
                        courseListFragment = new CourseListFragment();
                        transaction.add(R.id.fragment_main,courseListFragment);
                    } else {
                        transaction.show(courseListFragment);
                    }
                    transaction.commit();
                    break;
                case R.id.main_menu_info:
                    titleText.setText("我的信息");
                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                    if (infoFragment == null){
                        infoFragment = new InfoFragment();
                        transaction.add(R.id.fragment_main,infoFragment);
                    } else {
                        transaction.show(infoFragment);
                    }
                    transaction.commit();
                    break;
                case R.id.main_menu_password:
                    titleText.setText("修改密码");
                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                    if (modifyPasswordFragment == null){
                        modifyPasswordFragment = new ModifyPasswordFragment();
                        modifyPasswordFragment.setSuccessListener(() -> {
                            hideAllFragment();
                            showCourseList();
                            navigationView.setCheckedItem(R.id.main_menu_course);
                            titleText.setText("课程列表");
                        });
                        transaction.add(R.id.fragment_main,modifyPasswordFragment);
                    } else {
                        transaction.show(modifyPasswordFragment);
                    }
                    transaction.commit();
                    break;
                case R.id.main_menu_unregister:
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
            return true;
        });
    }*/

    public void hideAllFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (infoFragment != null){
            fragmentTransaction.hide(infoFragment);
        }
        if (courseListFragment != null){
            fragmentTransaction.hide(courseListFragment);
        }
        if (modifyPasswordFragment != null){
            fragmentTransaction.hide(modifyPasswordFragment);
        }
        fragmentTransaction.commit();
    }


}
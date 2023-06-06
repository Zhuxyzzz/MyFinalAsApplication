package com.example.myfinalasapplication.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.myfinalasapplication.R;
import com.example.myfinalasapplication.CourseViewModel;
import com.example.myfinalasapplication.entity.CourseList;
import com.example.myfinalasapplication.util.ViewUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentCourseDetail extends AppCompatActivity {

    private CourseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_detail);

        Intent intent = getIntent();
        CourseList course = (CourseList) intent.getExtras().getSerializable("course");//CourseList类已经implements Serializable，类中的所有对象都可以实现了可序列化了
        ViewUtils.initActionBar(this,course.getCourseName());
        viewModel = new ViewModelProvider(this).get(CourseViewModel.class);//
//        先初始化course
        viewModel.getCourse();//变量初始化new MutableLiveData<>();
        viewModel.setCourse(course);
        initBottomNavigation();
    }

    @SuppressLint("ResourceType")
    private void initBottomNavigation(){
        BottomNavigationView navigationView = findViewById(R.id.student_course_bottom);
        NavController controller = Navigation.findNavController(this,R.id.student_course_fragment);
        NavigationUI.setupWithNavController(navigationView,controller);

    }
}
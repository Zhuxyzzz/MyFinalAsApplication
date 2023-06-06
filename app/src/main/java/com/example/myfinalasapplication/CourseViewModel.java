package com.example.myfinalasapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfinalasapplication.entity.CourseList;

public class CourseViewModel extends ViewModel {
    private MutableLiveData<CourseList> course;

    public MutableLiveData<CourseList> getCourse() {
        if (course == null){
            course = new MutableLiveData<>();
        }
        return course;
    }

    public void setCourse(CourseList courseList){
        course.setValue(courseList);
    }
}

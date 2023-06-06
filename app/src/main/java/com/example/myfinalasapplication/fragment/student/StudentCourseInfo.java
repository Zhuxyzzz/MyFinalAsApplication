package com.example.myfinalasapplication.fragment.student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.example.myfinalasapplication.R;
import com.example.myfinalasapplication.CourseViewModel;
import com.example.myfinalasapplication.dialog.LoadingDialog;
import com.example.myfinalasapplication.entity.CourseList;
import com.example.myfinalasapplication.util.NetUtil;
import com.example.myfinalasapplication.util.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.ui.companent.CircleImageView;

@SuppressLint("NonConstantResourceId")
public class StudentCourseInfo extends Fragment {
    @BindView(R.id.teacher_course_info_avatar)
    CircleImageView courseAvatar;
    @BindView(R.id.teacher_course_info_name)
    TextView courseName;
    @BindView(R.id.teacher_course_info_code)
    TextView courseCode;
    @BindView(R.id.teacher_course_info_teacher_name)
    TextView teacherName;
    @BindView(R.id.teacher_course_info_teacher_phone)
    TextView teacherPhone;
    @BindView(R.id.teacher_course_info_teacher_email)
    TextView teacherEmail;
    @BindView(R.id.teacher_course_info_introduce)
    TextView courseIntroduce;

    private CourseViewModel viewModel;
    private CourseList course;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_course_info, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(CourseViewModel.class);
        // TODO: Use the ViewModel
        course = viewModel.getCourse().getValue();
        initView();
    }

    private void initView(){
        Picasso.with(this.getContext())
                .load(ProjectStatic.SERVICE_PATH + course.getCourseAvatar())
                .fit()
                .error(R.drawable.ic_net_error)
                .into(courseAvatar);

        courseName.setText(course.getCourseName());
        courseCode.setText(course.getCourseCode());
        courseIntroduce.setText(course.getCourseIntroduce());
        teacherEmail.setText(course.getUserEmail());
        teacherName.setText(course.getUesrName());
        teacherPhone.setText(course.getUserPhone());
    }

    @OnClick(R.id.student_course_info_delete)
    public void onClicked(View view){
        LoadingDialog dialog = new LoadingDialog(view.getContext());
        dialog.setTitle("警告");
        dialog.setMessage("该操作不可逆，请重复确认");
        dialog.setOnYesClickedListener(v -> {
            dialog.dismiss();
            LoadingDialog dialog1 = new LoadingDialog(v.getContext());
            dialog1.setTitle("退出课程");
            dialog1.setMessage(StringUtils.getString(R.string.wait_message));
            dialog1.show();
            Map<String, String> map = new HashMap<>();
            map.put("courseId",String.valueOf(course.getCourseId()));
            map.put("studentId",view.getContext().getSharedPreferences("localRecord", Context.MODE_PRIVATE).getString("id",""));
            NetUtil.getNetData("courseStudent/deleteCourseStudent",map,new Handler(msg -> {
                if (msg.what == 1){
                    dialog1.setOnDismissListener(dialog2 -> getActivity().finish());
                }
                dialog1.showSingleButton();
                dialog1.setMessage(msg.getData().getString("message"));
                return false;
            }));
        });
        dialog.show();
    }

}
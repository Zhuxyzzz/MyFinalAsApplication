package com.example.myfinalasapplication.fragment.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfinalasapplication.R;
import com.example.myfinalasapplication.login.LoginActivity;
import com.example.myfinalasapplication.adapter.CourseListAdapter;
import com.example.myfinalasapplication.entity.CourseList;
import com.example.myfinalasapplication.util.NetUtil;
import com.example.myfinalasapplication.util.ProjectStatic;
import com.example.myfinalasapplication.util.ViewUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.ui.companent.CircleImageView;

@SuppressLint("NonConstantResourceId")
public class NoticeFragment extends Fragment {
    @BindView(R.id.textView4)
    TextView accountName;

    private String type;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_notice, container, false);
        ButterKnife.bind(this,inflate);
        preferences = getActivity().getSharedPreferences("localRecord", Context.MODE_PRIVATE);
        initView();

        return inflate;
    }

    public void initView() {
      System.out.println("test"+"进入NoticeFragment.java文件的initView方法");

    }



}
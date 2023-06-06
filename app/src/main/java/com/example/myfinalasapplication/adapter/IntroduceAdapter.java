 package com.example.myfinalasapplication.adapter;


         import android.content.Context;
         import android.content.Intent;
         import android.content.SharedPreferences;
         import android.os.Bundle;
         import android.util.Log;
         import android.view.LayoutInflater;
         import android.view.View;
         import android.view.ViewGroup;
         import android.widget.ImageView;
         import android.widget.TextView;
         import android.widget.Toast;

         import androidx.annotation.NonNull;
         import androidx.recyclerview.widget.RecyclerView;

         import com.example.myfinalasapplication.R;
         import com.example.myfinalasapplication.entity.CourseList;
         import com.example.myfinalasapplication.entity.IntroduceList;
         import com.example.myfinalasapplication.util.MyApplication;
         import com.example.myfinalasapplication.util.MyTransForm;
         import com.example.myfinalasapplication.util.ProjectStatic;
         import com.squareup.picasso.Picasso;

         import java.util.List;

public class IntroduceAdapter extends RecyclerView.Adapter<IntroduceAdapter.ViewHolder> {
    private List<IntroduceList> introduceLists;

    public IntroduceAdapter(List<IntroduceList> introduceLists) {
        this.introduceLists = introduceLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_course_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IntroduceList introduceList = introduceLists.get(position);
        holder.lecturer.setText(introduceList.getDescription());
        holder.name.setText(introduceList.getIntroName());

    /*    Picasso.with(MyApplication.getContext())
                .load(ProjectStatic.SERVICE_PATH + courseList.getCourseAvatar())
                .fit()
                .transform(new MyTransForm.RoundCornerTransForm(30f))
                .error(R.drawable.ic_net_error)
                .into(holder.img);
*/
        holder.view.setOnClickListener(v -> {
            SharedPreferences localRecord = v.getContext().getSharedPreferences("localRecord", Context.MODE_PRIVATE);
            String userType = localRecord.getString("userType", "");
            Intent intent = new Intent();
            if (userType.equals("2")){
                intent.setAction(ProjectStatic.STUDENT_COURSE_DETAIL);
            }else {
                intent.setAction(ProjectStatic.TEACHER_COURSE_DETAIL);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("introduce",introduceList);
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return introduceLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView img;
        public TextView name;
        public TextView lecturer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            img = itemView.findViewById(R.id.course_list_avatar);//图片
            name = itemView.findViewById(R.id.course_list_course_name);//推荐名
            lecturer = itemView.findViewById(R.id.course_list_teacher_name);//描述
        }
    }
}

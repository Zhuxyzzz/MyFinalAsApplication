package com.example.myfinalasapplication.fragment.main;


        import androidx.lifecycle.MutableLiveData;
        import androidx.lifecycle.ViewModel;

        import com.alibaba.fastjson.JSONArray;
        import com.alibaba.fastjson.JSONObject;
        import com.example.myfinalasapplication.entity.CourseList;
        import com.example.myfinalasapplication.entity.IntroduceList;
        import com.example.myfinalasapplication.util.ProjectStatic;

        import java.sql.Timestamp;
        import java.util.ArrayList;
        import java.util.List;

public class IntroduceListViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<IntroduceList>> introduceLists;

    public MutableLiveData<List<IntroduceList>> getIntroduceLists() {
        if (introduceLists == null){
            introduceLists = new MutableLiveData<>();
        }
        return introduceLists;
    }

    //    更新recyclerview列表
    public void updateIntroduces(String s){
        List<IntroduceList> lists = new ArrayList<>();
        IntroduceList introduceList;
       JSONArray objects = JSONObject.parseArray(s);
        for (int i = 0; i < 9; i++) {
       //  JSONObject o = (JSONObject) objects.get(i);
           // JSONObject teacher = JSONObject.parseObject(o.getString("student"));
            introduceList = new IntroduceList(i,"推荐"+i,"这是推荐的描述词"+i,"00"+i,"图片url");
           // introduceList.setJoinTime(o.getTimestamp("joinTime"));
            lists.add(introduceList);
        }
        introduceLists.setValue(lists);
    }

}
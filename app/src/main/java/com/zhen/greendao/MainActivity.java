package com.zhen.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhen.greendao.adapter.GreenDaoAdapter;
import com.zhen.greendao.entity.Student;
import com.zhen.greendao.entity.gen.StudentDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyApp myApp;
    private AppCompatButton btn_insert, btn_query, btn_delete, btn_update;
    private AppCompatEditText et_id, et_name, et_age, et_gender;
    private RecyclerView rv_db;
    private StudentDao studentDao;
    private List<Student> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        myApp = (MyApp) getApplication();
        studentDao = myApp.getStudentDao();

        //插入
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert();
                //insertInTx();
                //insertOrReplace();
                //insertOrReplaceInTx();
                save();
            }
        });
        //查询
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rv_db.setLayoutManager(new LinearLayoutManager(this));
        GreenDaoAdapter adapter = new GreenDaoAdapter(list);
        rv_db.setAdapter(adapter);
    }

    //插入操作
    private void insert() {
        studentDao.insert(getStudent());
        clear();
    }

    //批量插入
    private void insertInTx() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1L, "小明", 18, "男"));
        list.add(new Student(2L, "小花", 16, "女"));
        list.add(new Student(3L, "小白", 15, "男"));
        studentDao.insertInTx(list);
    }

    //插入或替换
    private void insertOrReplace() {
        studentDao.insertOrReplace(new Student(null, "测试", 222, "男"));
        clear();
    }

    //批量插入或替换
    private void insertOrReplaceInTx() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1L, "one", 18, "man"));
        list.add(new Student(2L, "two", 16, "woman"));
        list.add(new Student(3L, "three", 15, "man"));
        studentDao.insertOrReplaceInTx(list);
    }

    //save
    private void save() {
        studentDao.save(new Student(1L, "xiaoming", 222, "man"));
    }


    private void query() {
        list = studentDao.queryRaw("id = ?", "1");
    }

    private Student getStudent() {
        Long id = Long.valueOf(et_id.getText().toString().trim());
        String name = et_name.getText().toString().trim();
        Integer age = Integer.valueOf(et_age.getText().toString().trim());
        String gender = et_gender.getText().toString().trim();

        return new Student(id, name, age, gender);

    }

    private void clear() {
        et_id.setText("");
        et_name.setText("");
        et_age.setText("");
        et_gender.setText("");
    }

    private void initView() {
        btn_insert = findViewById(R.id.btn_insert);
        btn_query = findViewById(R.id.btn_query);
        btn_delete = findViewById(R.id.btn_delete);
        btn_update = findViewById(R.id.btn_update);
        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_gender = findViewById(R.id.et_gender);
        rv_db = findViewById(R.id.rv_db);
    }
}

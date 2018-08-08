package com.zhen.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zhen.greendao.adapter.GreenDaoAdapter;
import com.zhen.greendao.entity.Student;
import com.zhen.greendao.entity.gen.StudentDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity-vv";

    private MyApp myApp;
    private AppCompatButton btn_insert, btn_query, btn_delete, btn_update;
    private AppCompatEditText et_id, et_name, et_age, et_gender;
    private RecyclerView rv_db;
    private StudentDao studentDao;
    private List<Student> queryList = new ArrayList<>();
    private GreenDaoAdapter adapter;
    private PublishSubject<Student> mPublish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        myApp = (MyApp) getApplication();
        studentDao = myApp.getStudentDao();
        mPublish = PublishSubject.create();

        //插入操作
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
                //insertInTx();
                //insertOrReplace();
                //insertOrReplaceInTx();
                //save();

                //RxJava批量插入 可批量同时插入三个
                //mPublish.onNext(getStudent());
            }
        });
        //查询操作
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryList.clear();

                //load();
                loadAll();
                //whereName();
                //like();
                //notEq();

                adapter.notifyDataSetChanged();

            }
        });

        //删除操作
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete();
                //deleteInTx();
                //deleteByKey();
                //deleteByKeyInTx();
                deleteAll();

            }
        });

        //修改操作
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update();
                updateInTx();
            }
        });

        rv_db.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GreenDaoAdapter(queryList);
        rv_db.setAdapter(adapter);

        //RxJava批量插入
        mPublish.buffer(3)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(RxInsertOrReplaceInTx());
    }

    //插入操作
    private void insert() {
        studentDao.insert(getStudent());
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
        studentDao.insertOrReplace(getStudent());
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

    //RxJava2批量插入操作
    private Observer<List<Student>> RxInsertOrReplaceInTx() {
        return new DisposableObserver<List<Student>>() {
            @Override
            public void onNext(List<Student> students) {
                studentDao.insertInTx(students);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
    }


    //查询操作

    //按主键查询
    private void load() {
        Student student = studentDao.load(Long.valueOf(et_id.getText().toString()));
        queryList.add(student);
    }

    //查询全部
    private void loadAll() {
        queryList.addAll(studentDao.loadAll());
    }

    //条件查询 name
    private void whereName() {
        String name = et_name.getText().toString();
        Student unique = studentDao.queryBuilder().where(StudentDao.Properties.Name.eq(name)).unique();
        queryList.add(unique);
    }

    //模糊查询 gender
    private void like() {
        String gender = et_gender.getText().toString();
        List<Student> list = studentDao.queryBuilder().where(StudentDao.Properties.Gender.like(gender + "%")).list();
        queryList.addAll(list);
    }

    //notEq 查询不是该字段的数据
    private void notEq() {
        String gender = et_gender.getText().toString();
        List<Student> list = studentDao.queryBuilder().where(StudentDao.Properties.Gender.notEq(gender)).list();
        queryList.addAll(list);
    }

    //删除

    //单个删除数据
    private void delete() {
        //获取要删除的数据
        String name = et_name.getText().toString();
        Student student = studentDao.queryBuilder().where(StudentDao.Properties.Name.eq(name)).unique();
        //删除操作
        studentDao.delete(student);
    }

    //批量删除数据
    private void deleteInTx() {
        //要删除的数据
        Student stu1 = new Student(1L, "one", 18, "man");
        Student stu2 = new Student(3L, "three", 15, "man");

        studentDao.deleteInTx(stu1, stu2);

    }

    //根据主键删除数据
    private void deleteByKey() {
        Long id = Long.valueOf(et_id.getText().toString());
        studentDao.deleteByKey(id);
    }

    //根据主键批量删除数据
    private void deleteByKeyInTx() {
        studentDao.deleteByKeyInTx(1L, 3L);
    }

    //全部删除
    private void deleteAll() {
        studentDao.deleteAll();
    }

    //修改操作

    //单个修改 update
    private void update() {
        //使用主键查询需要修改的数据
        Long id = Long.valueOf(et_id.getText().toString());
        Student newStudent = studentDao.load(id);

        //修改名字
        String name = et_name.getText().toString();
        newStudent.setName(name);
        //修改
        studentDao.update(newStudent);
    }

    //批量修改数据
    private void updateInTx() {
        Integer age = Integer.valueOf(et_age.getText().toString());
        //查询获取所有数据
        List<Student> list = studentDao.loadAll();
        for (int i = 0; i < list.size(); i++) {
            //年龄统一修改
            list.get(i).setAge(age);
        }
        //批量修改数据
        studentDao.updateInTx(list);
    }

    private Student getStudent() {
        Long id = Long.valueOf(et_id.getText().toString().trim());
        String name = et_name.getText().toString().trim();
        Integer age = Integer.valueOf(et_age.getText().toString().trim());
        String gender = et_gender.getText().toString().trim();
        clear();
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

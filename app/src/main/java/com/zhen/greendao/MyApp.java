package com.zhen.greendao;

import android.app.Application;

import com.zhen.greendao.entity.gen.StudentDao;
import com.zhen.greendao.green.DatabaseManage;

public class MyApp extends Application {

    private StudentDao studentDao;
    private DatabaseManage databaseManage;

    @Override
    public void onCreate() {
        super.onCreate();
        if (databaseManage == null) {
            databaseManage = DatabaseManage.getInstance().init(this);
            studentDao = databaseManage.getStudentDao();
        }
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

}

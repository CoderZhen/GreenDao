package com.zhen.greendao.green;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zhen.greendao.entity.gen.DaoMaster;
import com.zhen.greendao.entity.gen.DaoSession;
import com.zhen.greendao.entity.gen.StudentDao;

public class DatabaseManage {
    private DaoSession mSession = null;
    private StudentDao studentDao = null;


    public DatabaseManage init(Context context) {
        initData(context);
        return this;
    }

    private void initData(Context context){
        //创建数据库，mStu.db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"mStu.db");
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取Dao管理者Session
        mSession = new DaoMaster(db).newSession();
        //获取Dao对象
        studentDao = mSession.getStudentDao();
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    private static final class DatabaseManageHolder{
        private static final DatabaseManage INSTANCE = new DatabaseManage();
    }

    public static DatabaseManage getInstance(){
        return DatabaseManageHolder.INSTANCE;
    }
}

package com.zhen.greendao.green;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zhen.greendao.entity.gen.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class ReleaseOpenHelper extends DaoMaster.OpenHelper{

    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    public ReleaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

}

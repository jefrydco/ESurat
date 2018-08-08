package com.example.esurat.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.esurat.dao.UserDao;
import com.example.esurat.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDb extends RoomDatabase {
    public abstract UserDao userDao();

    private static UserDb INSTANCE;

    static UserDb getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (UserDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            UserDb.class,
                            "user_db").build();
                }
            }
        }
        return INSTANCE;
    }
}

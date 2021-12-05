package com.example.badpair;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PlayerName.class}, version = 1, exportSchema = false)
public abstract class PlayerDatabase extends RoomDatabase {

    public abstract PlayerDao playerDao();

    private static volatile PlayerDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PlayerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlayerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlayerDatabase.class, "player_database")
//                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            databaseWriteExecutor.execute(() -> {
//
//                // Populate the database in the background.
//                // If you want to start with more words, just add them.
//                PlayerDao dao = INSTANCE.playerDao();
//                dao.deleteAll();
//
////                PlayerName name = new PlayerName("Jake_1924");
////                dao.insert(name);
////                name = new PlayerName("World");
////                dao.insert(name);
//
//            });
//        }
//    };

}

package com.example.badpair;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

class PlayerRepositry {


    private final PlayerDao mPlayerDao;
    private  List<PlayerName> mAllPlayer;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
      PlayerRepositry(Application application) {
        PlayerDatabase db = PlayerDatabase.getDatabase(application);
        mPlayerDao = db.playerDao();
//        mAllPlayer = mPlayerDao.getAlphabetizedWords();  // Todo: this cannot be done in mainthread
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    List<PlayerName> getAllPlayer(){
        PlayerDatabase.databaseWriteExecutor.execute(() -> {
            mAllPlayer = mPlayerDao.getAlphabetizedWords();
        });
        return mAllPlayer;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(PlayerName word) {
        PlayerDatabase.databaseWriteExecutor.execute(() -> {
            mPlayerDao.insert(word);
        });
    }

    void delete(){
          PlayerDatabase.databaseWriteExecutor.execute(mPlayerDao::deleteAll);
    }

}


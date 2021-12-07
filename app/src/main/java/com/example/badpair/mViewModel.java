package com.example.badpair;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class mViewModel extends AndroidViewModel {
    private static String[] ar;
//    private final Application application;
    public String currentName;
    private final PlayerRepositry mRepository;
    private MutableLiveData<String> randomName;
    private  List<PlayerName> mAllName; // stored in background thread pool

    public mViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PlayerRepositry(application);
        mAllName = mRepository.getAllPlayer();  // it is on background thread so unable to use in main thread
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
        split();
    }

    public MutableLiveData<String> getRandomName() {
        if (randomName == null) {
            randomName = new MutableLiveData<String>();
        }
        return randomName;
    }

    public void loadplayers() {
    //todo: This should be copied in main thread to work UI for now this results in app crash
        //loading from database everytime in array (ar)
            int i = 0;
            for (PlayerName a : mAllName) {
                ar[i] = a.toString();
                i++;
            }


        //shuffling values of array
        shuffleArray();

        //attaching shuffled value to LiveData to display in textview
         i = 0;
        StringBuilder tmp = new StringBuilder();
        for (String a : ar) {
            i++;
            tmp.append(i).append(". ").append(a).append("\n");
        }
        randomName.setValue(tmp.toString());
    }


    private void split() {
        String s;
        {
            assert currentName != null;
            s = currentName;
        }

        ar = s.split(",");

        for (String a : ar) {
            PlayerName x = new PlayerName(a);
            mRepository.insert(x);
        }

    }

    public void delete() {
        mRepository.delete();
    }

    private void shuffleArray() {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

}

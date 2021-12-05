package com.example.badpair;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class mViewModel extends ViewModel {
    private PlayerRepositry mRepository;
    // Create a LiveData with a String
    public String currentName;
    private MutableLiveData<String> randomName;
    private LiveData<List<PlayerName>> mAllName;
    private static String[] ar;
    private Application application;

    public mViewModel(Application application) {
        this.application = application;
    }


    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public MutableLiveData<String> getRandomName() {
        if (randomName == null) {
            randomName = new MutableLiveData<String>();
        }
        return randomName;
    }

    public void loadplayers() {
        shuffleArray();

        int i = 0;
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

        mRepository = new PlayerRepositry(application);
        for (String a : ar) {
            PlayerName x = new PlayerName(a);
            mRepository.insert(x);
        }



    }
    public  void delete(){
        mRepository = new PlayerRepositry(application);
        mRepository.delete();
    }

    private void shuffleArray() {
        split();
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

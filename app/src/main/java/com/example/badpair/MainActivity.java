package com.example.badpair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel model = new ViewModelProvider(this).get(mViewModel.class);

        EditText editText = findViewById(R.id.EditText);
        TextView textview = findViewById(R.id.team);
        Button shuffle = findViewById(R.id.button);

        textview.setMovementMethod(new ScrollingMovementMethod());

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = editText.getText().toString();
                if(s.isEmpty()) {
                    textview.setText("");
                    Toast.makeText(MainActivity.this,"No player names has been entered",Toast.LENGTH_SHORT).show();
                }
                else {
                    model.setCurrentName(s);
                    model.loadplayers();
                }

            }
        });

        model.getRandomName().observe(this,randomName ->{
            textview.setText(model.getRandomName().getValue());
        });
    }


}
package com.example.badpair;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel model = new ViewModelProvider(this, new mViewModelFactory(getApplication())).get(mViewModel.class);

        EditText editText = findViewById(R.id.EditText);
        TextView textview = findViewById(R.id.team);
        Button shuffle = findViewById(R.id.button);
        Button add = findViewById(R.id.add_button);
        Button del = findViewById(R.id.delete_button);

        textview.setMovementMethod(new ScrollingMovementMethod());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = editText.getText().toString();
                if (s.isEmpty()) {
                    textview.setText("");
                    shuffle.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "No player names has been entered", Toast.LENGTH_SHORT).show();
                } else {

                    model.setCurrentName(s);
                    model.loadplayers();
                    shuffle.setVisibility(View.VISIBLE);
                }

            }
        });

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.loadplayers();
            }
        });

        model.getRandomName().observe(this, randomName -> {
            textview.setText(model.getRandomName().getValue());
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.delete();
                Toast.makeText(MainActivity.this, "all player name cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
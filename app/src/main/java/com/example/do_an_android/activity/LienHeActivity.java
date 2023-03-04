package com.example.do_an_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.do_an_android.R;

public class LienHeActivity extends AppCompatActivity {
    ImageButton ibtnBackLienHe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        addControls();
        addEvents();
    }

    private void addEvents() {
        ibtnBackLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        ibtnBackLienHe=findViewById(R.id.ibtnBackLienHe);
    }
}
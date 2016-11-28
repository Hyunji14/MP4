package com.example.lp.lastpictures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    //Test git

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);


        ImageButton mainB = (ImageButton)findViewById(R.id.imageButton);
        mainB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent intent1 = new Intent(MainActivity.this, SelectFindActivity.class);
                Intent intent1 = new Intent(MainActivity.this, NMapViewActivity.class);
                startActivity(intent1);
            }
        });
    }
}

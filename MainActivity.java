package com.example.intent;

import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity<find> extends AppCompatActivity {
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onClickButtonListener();
    }
    public void onClickButtonListener()
    {
        //btn
        btn2=(Button)findViewById(R.id.loginButton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SchedulePage.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Login Successfully", LENGTH_LONG).show();
            }
        });
    }
}

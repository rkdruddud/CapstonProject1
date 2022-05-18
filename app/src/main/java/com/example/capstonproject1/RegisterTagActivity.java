package com.example.capstonproject1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.SettingInjectorService;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegisterTagActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tag);

        Button personal = findViewById(R.id.personalbutton);
        Button group = findViewById(R.id.groupbutton);

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personal_intent = new Intent(RegisterTagActivity.this, PersonalTagActivity.class);
                RegisterTagActivity.this.startActivity(personal_intent);
            }
        });


        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent group_intent = new Intent(RegisterTagActivity.this, GroupRegisterActivity.class);
                RegisterTagActivity.this.startActivity(group_intent);
            }
        });
       /* num1 = 0;
        num2 = 0;*/

    }
}
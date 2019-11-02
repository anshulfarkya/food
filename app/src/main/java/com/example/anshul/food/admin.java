package com.example.anshul.food;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class admin extends AppCompatActivity {

    Button Addagent,trackagent,logout;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Addagent=(Button)findViewById(R.id.addagent);
        trackagent=(Button)findViewById(R.id.btntrack);
        logout=(Button)findViewById(R.id.logout);

        auth=FirebaseAuth.getInstance();

        Addagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(admin.this,addagent.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                finish();
                startActivity(new Intent(admin.this,LoginActivity.class));
                finish();
            }
        });
    }
}

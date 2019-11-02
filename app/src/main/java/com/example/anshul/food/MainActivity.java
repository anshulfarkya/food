package com.example.anshul.food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {
    EditText etnum,etpass;
    Button btnreg;
    Button tvlogin;
    ProgressDialog dialog;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("signindata");
        if (auth.getCurrentUser() != null) {
            //profile activity here
            //finish();

            final String useremail = auth.getCurrentUser().getEmail();
            dbref.getDatabase().getReference("signindata").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        signindata signin = snap.getValue(signindata.class);
                        String emai = signin.getEmail();
                        String type = signin.getType();
                        if (emai.equals(useremail)) {
                            if (type.equals("user")) {
                                finish();
                                startActivity(new Intent(MainActivity.this, Navigation.class));

                            } else if (type.equals("admin")) {
                                finish();
                                startActivity(new Intent(MainActivity.this, admin.class));

                            } else if (type.equals("agent")) {
                                finish();
                                Toast.makeText(MainActivity.this, "welcome agent", Toast.LENGTH_SHORT).show();

                            }
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(MainActivity.this,databaseError.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        etnum = (EditText) findViewById(R.id.etnum);
        etpass = (EditText) findViewById(R.id.etpass);
        btnreg = (Button) findViewById(R.id.btnsend);
        tvlogin = (Button) findViewById(R.id.tvlogin);
        dialog = new ProgressDialog(this);
        btnreg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    registerUser();
            }
        });
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                Intent in = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(in);
            }
        });

    }


    private void registerUser() {
        final String email=etnum.getText().toString().trim();
        final String password=etpass.getText().toString().trim();
        final DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("signindata");
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "enter email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage("Registering User....");
        dialog.show();


        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();
                if(task.isSuccessful())
                {//refreshed token hataya h
                   String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                    Toast.makeText(MainActivity.this,"registered successfully", Toast.LENGTH_SHORT).show();
                    String key=dbref.push().getKey();
                    signindata signin= new signindata(email,password,"user","null",refreshedToken);
                    dbref.child(key).setValue(signin);
                    finish();
                    startActivity(new Intent(MainActivity.this,Navigation.class));
                }
                else
                {

                    Toast.makeText(MainActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

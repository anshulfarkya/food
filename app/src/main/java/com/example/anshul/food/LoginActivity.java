package com.example.anshul.food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    EditText etemail, etpassword;
    Button btnlogin, btnsignup;
    ProgressDialog dialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etemail = (EditText) findViewById(R.id.etemail);
        etpassword = (EditText) findViewById(R.id.etpassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnsignup = (Button) findViewById(R.id.btnsignup);
        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {Userlogin();

            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

    }

    private void Userlogin() {
        final String email = etemail.getText().toString();
        final String pass = etpassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

            final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();

            dialog.setMessage("Authenticating.....");
            dialog.show();


            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                      //  String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                        dbref.getDatabase().getReference("signindata").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                    signindata signin = snap.getValue(signindata.class);
                                    String emai = signin.getEmail();
                                    String pas = signin.getPassword();
                                    String type = signin.getType();
                                    String area=signin.getArea();
                                    //String token=signin.getToken();
                                    if (emai.equals(email)) {
                                        if (pas.equals(pass)) {
                                           String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                           String key=snap.getKey();
                                           signindata sign=new signindata(emai,pas,type,area,refreshedToken);
                                            dbref.child("signindata").child(key).setValue(sign);
                                            if (type.equals("user")) {
                                                finish();
                                                startActivity(new Intent(LoginActivity.this, Navigation.class));

                                            } else if (type.equals("admin")) {
                                                finish();
                                                startActivity(new Intent(LoginActivity.this, admin.class));

                                            } else if (type.equals("agent")) {
                                                finish();
                                                Toast.makeText(LoginActivity.this, "welcome agent", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                        dialog.dismiss();
                                    }
                                }
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {

                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

package com.example.anshul.food;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addagent extends AppCompatActivity {
    EditText etname,etnum,etaddress,etemail,etpass;
    Button btn;
    Spinner spnarea;
    DatabaseReference dbref;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agentadd);
        etname=(EditText)findViewById(R.id.etname);
        etemail=(EditText)findViewById(R.id.etemail);
        etpass=(EditText)findViewById(R.id.etpass);
        etnum=(EditText)findViewById(R.id.etnum);
        etaddress=(EditText)findViewById(R.id.etaddress);
        spnarea=(Spinner)findViewById(R.id.spinarea);
        btn=(Button)findViewById(R.id.btn);
        dbref= FirebaseDatabase.getInstance().getReference("signindata");
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addagent();

            }
        });


    }

    private void addagent() {
        String name=etname.getText().toString();
        String email=etemail.getText().toString();
        String pass=etpass.getText().toString();
        String number=etnum.getText().toString();
        String area=spnarea.getSelectedItem().toString();
        String address=etaddress.getText().toString();
        if(TextUtils.isEmpty(name))
        {
            return;
        }
        else if(TextUtils.isEmpty(number))
        {
            return;
        }
        else if(TextUtils.isEmpty(email))
        {
            return;
        }
        else if(TextUtils.isEmpty(pass))
        {
            return;
        }
        else if(TextUtils.isEmpty(area))
        {
            return;
        }
        else if(TextUtils.isEmpty(address))
        {
            return;
        }

        else
        {
            auth=FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email,pass);

            String key=dbref.push().getKey();
            Toast.makeText(addagent.this, key, Toast.LENGTH_SHORT).show();
            signindata signin= new signindata(email,pass,"agent",area,"null");


            dbref.child(key).setValue(signin);/*.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful())
                 {
                     Toast.makeText(addagent.this, "agent added successfully", Toast.LENGTH_SHORT).show();
                 }
                    else
                 {
                     Toast.makeText(addagent.this, "unable to add agent", Toast.LENGTH_SHORT).show();
                 }
                }
            });*/
            dbref=FirebaseDatabase.getInstance().getReference("agentdata");
            String key1=dbref.push().getKey();
            //Toast.makeText(addagent.this, key, Toast.LENGTH_SHORT).show();
            agentdata agent=new agentdata(name,email,pass,number,address,area);
            dbref.child(key1).setValue(agent).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isComplete())
                    {
                        Toast.makeText(addagent.this, "agent added successfully", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        Toast.makeText(addagent.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}


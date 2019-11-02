package com.example.anshul.food;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by anshul on 03-Jun-18.
 */

public class Cloth extends Fragment {


    EditText etname, etnum, etaddress;
    Button btn;
    Spinner spnarea;
    DatabaseReference dbref, ref,ref2;
    FirebaseAuth auth;
    String key,name,number,area,address;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.donatecloth, null);
        etname = (EditText) v.findViewById(R.id.etname);
        etnum = (EditText) v.findViewById(R.id.etnumber);
        etaddress = (EditText) v.findViewById(R.id.etaddress);
        spnarea = (Spinner) v.findViewById(R.id.spinnerarea);
        btn = (Button) v.findViewById(R.id.GO);
        dbref = FirebaseDatabase.getInstance().getReference("users");
        ref2=FirebaseDatabase.getInstance().getReference("Notifications");
        ref=FirebaseDatabase.getInstance().getReference("signindata");
        auth=FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                adduser();

            }
        });

        return v;
    }

    private void adduser() {
        name = etname.getText().toString();
        number = etnum.getText().toString();
        area = spnarea.getSelectedItem().toString();
        address = etaddress.getText().toString();
        if (TextUtils.isEmpty(name)) {
            return;
        } else if (TextUtils.isEmpty(number)) {
            return;
        } else {
            key = dbref.push().getKey();
            User user = new User(key, name, number, address, area);
            dbref.child(key).setValue(user);

        }

        ref.getDatabase().getReference("signindata").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    signindata signin = snap.getValue(signindata.class);
                    //String emai = signin.getEmail();
                    //String pas = signin.getPassword();
                    String type = signin.getType();
                    String are = signin.getArea();
                    String token=signin.getToken();
                    String to=snap.getKey();
                    if(are.equals(area))
                    {
                        if(type.equals("agent"))
                        {
                           ref2.child(to).child(key).child("token").setValue(token);
                        }


                            //ref2=(DatabaseReference) FirebaseDatabase.getInstance().getReference("notifications");
                             }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               // Toast.makeText(this,databaseError.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
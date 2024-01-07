package com.example.realtime_crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.C;

public class Register extends AppCompatActivity {

    EditText email,password,confirm;

    Button register;
    TextView next;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.editTextText5);
        password=findViewById(R.id.editTextText6);
        confirm=findViewById(R.id.editTextText7);
        register=findViewById(R.id.button2);
        next=findViewById(R.id.textView5);
        firebaseAuth=FirebaseAuth.getInstance();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String USER = email.getText().toString();
                String PASSWORD = password.getText().toString();
                String CONFIRM = confirm.getText().toString();
                Register(USER,PASSWORD);
            }
        });


    }

    public  void Register(String Email, String Password){
        Log.e("skxhsix","dhcgdyd");

//        if (!Email.isEmpty() && Password.isEmpty()){


            firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Register.this,"Register is Successfull", Toast.LENGTH_SHORT)
                                .show();
                    }
                    else {
                        Toast.makeText(Register.this,"Error",Toast.LENGTH_SHORT)
                                .show();
                    }

                }
            });


//        }
    }
}
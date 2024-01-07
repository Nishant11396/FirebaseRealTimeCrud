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

public class Login extends AppCompatActivity {

    EditText user,password;

    Button login;
    TextView next;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=findViewById(R.id.editTextText);
        password=findViewById(R.id.editTextText2);
        login=findViewById(R.id.button);
        next=findViewById(R.id.textView2);
        firebaseAuth=FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User = user.getText().toString();
                String Pass = password.getText().toString();
                LOGin(User,Pass);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

    }
    public  void LOGin(String Email,String Password){
    if (!Email.isEmpty() && !Password.isEmpty()){
        firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
    if (task.isSuccessful()){
    Toast.makeText(Login.this,"Login Successfully",Toast.LENGTH_SHORT)
            .show();
    }
    else {
        Toast.makeText(Login.this,"Error",Toast.LENGTH_SHORT)
                .show();
    }
            }
        });


}

    }
}
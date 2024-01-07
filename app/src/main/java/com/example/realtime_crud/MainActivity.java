package com.example.realtime_crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.FirebaseDatabaseKtxRegistrar;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton button;

    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        button = findViewById(R.id.addNote);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_note_dialog, null);

                TextInputLayout title, content;
                TextInputEditText edt_title, edt_content;
                title = view.findViewById(R.id.titleLayout);
                content = view.findViewById(R.id.contentLayout);

                edt_title = view.findViewById(R.id.titleET);
                edt_content = view.findViewById(R.id.contentET);

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add").
                        setView(view).
                        setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (edt_title.getText().toString().isEmpty()){
                                    title.setError("This Fiels is Required");
                                }
                                else if (edt_content.getText().toString().isEmpty()) {
                                    content.setError("This Fiels is Required");
                                }
                                else{
                                    Model model = new Model();
                                    model.setTitle(edt_title.getText().toString());
                                    model.setContent(edt_content.getText().toString());
                                     firebaseDatabase.getReference().child("Notes").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void unused) {
                                             dialog.dismiss();
                                             Toast.makeText(MainActivity.this, "Add is Successful", Toast.LENGTH_SHORT).show();
                                         }
                                     }).addOnFailureListener(new OnFailureListener() {
                                         @Override
                                         public void onFailure(@NonNull Exception e) {
                                             dialog.dismiss();
                                             Toast.makeText(MainActivity.this, "Add is Failed", Toast.LENGTH_SHORT).show();
                                         }
                                     });
                                }

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).create();
                alertDialog.show();

            }
        });

        firebaseDatabase.getReference().child("Notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Model> data = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Model model = ds.getValue(Model.class);
                    Objects.requireNonNull(model).setKey(ds.getKey());
                    data.add(model);
                }

                MyAdapter myAdapter = new MyAdapter(data,MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(myAdapter);

                myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Model model) {
                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_note_dialog, null);

                        TextInputLayout title, content;
                        TextInputEditText edt_title, edt_content;
                        title = view.findViewById(R.id.titleLayout);
                        content = view.findViewById(R.id.contentLayout);

                        edt_title = view.findViewById(R.id.titleET);
                        edt_content = view.findViewById(R.id.contentET);

                        edt_title.setText(model.getTitle());
                        edt_content.setText(model.getContent());

                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).
                                setTitle("Edit").
                                setView(view).
                                setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (edt_title.getText().toString().isEmpty()){
                                            title.setError("This Fiels is Required");
                                        } else if (edt_content.getText().toString().isEmpty()) {
                                            content.setError("This Fiels is Required");
                                        }
                                        else {
                                            Model model1 = new Model();
                                            model1.setTitle(edt_title.getText().toString());
                                            model1.setContent(edt_content.getText().toString());
                                            firebaseDatabase.getReference().child("Notes").child(model.getKey()).setValue(model1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    dialog.dismiss();
                                                    Toast.makeText(MainActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialog.dismiss();
                                                    Toast.makeText(MainActivity.this, "Updated Failed", Toast.LENGTH_SHORT).show();


                                                }
                                            });
                                        }


                                    }
                                }).setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();


                                    }
                                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        firebaseDatabase.getReference().child("Notes").child(model.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss();
                                                Toast.makeText(MainActivity.this, "Data Deleted Succesfully", Toast.LENGTH_SHORT).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialog.dismiss();
                                                Toast.makeText(MainActivity.this, "Deleted Failed", Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                    }
                                }).create();
                        alertDialog.show();


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
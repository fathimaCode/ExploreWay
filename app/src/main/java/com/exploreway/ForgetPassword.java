package com.exploreway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.exploreway.utils.SessionManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassword extends AppCompatActivity {

    Button forgetPasswordBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        forgetPasswordBtn = findViewById(R.id.fgpwdbtn);
        TextInputLayout logUsername = findViewById(R.id.fgusername);
        TextInputLayout newPwd = findViewById(R.id.newPwd);
        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                usersRef.orderByChild("username").equalTo(logUsername.getEditText().getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot shot : snapshot.getChildren()) {

                                String key = shot.getKey();
                                String password = shot.child("password").getValue(String.class);
                                String username = shot.child("username").getValue(String.class);
                                usersRef.child(key).child("password").setValue(newPwd.getEditText().getText().toString().trim());

                                Toast.makeText(ForgetPassword.this, "Password update successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ForgetPassword.this, LoginActivity.class);
                                startActivity(i);



                            }
                            // Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ForgetPassword.this, "Sorry! something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
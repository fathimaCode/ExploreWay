package com.exploreway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Button accountBtn = findViewById(R.id.accountBtn);

        // Assuming you have a reference to the parent view containing the TextInputEditText
        TextInputLayout textInputLayout = findViewById(R.id.textInputLayout);
        TextInputLayout email = findViewById(R.id.email);
        TextInputLayout pwd = findViewById(R.id.pwd);
        TextInputLayout confirmPwd = findViewById(R.id.confirmPwd);// Replace R.id.textInputLayout with the ID of your TextInputLayout


        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userInput = textInputLayout.getEditText().getText().toString().trim();
                        String userEmail = email.getEditText().getText().toString().trim();
                        String userPwd = pwd.getEditText().getText().toString().trim();
                        String userConfirmPwd = confirmPwd.getEditText().getText().toString().trim();

                        if (!userPwd.equals(userConfirmPwd)) {
                            Toast.makeText(AccountActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                        } else {
                            createNewAccount(userInput, userEmail, userPwd);
                        }

                    }
                });

            }

            private void createNewAccount(String userInput, String userEmail, String userPwd) {
                HashMap<String, Object> userInfo = new HashMap<>();
                userInfo.put("username",userInput);
                userInfo.put("email",userEmail);
                userInfo.put("password",userPwd);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dbref = db.getReference("users");
                String key = dbref.push().getKey();
                userInfo.put("key",key);
                dbref.child(key).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AccountActivity.this, "User Added ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AccountActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
package com.exploreway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exploreway.utils.SessionManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

public class LoginActivity extends AppCompatActivity {
    SignInButton btSignIn;
    Button LogBtn;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

TextView fgBtn;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LogBtn = findViewById(R.id.loginButton);
        TextInputLayout logUsername = findViewById(R.id.logUsername);
        TextInputLayout logPwd = findViewById(R.id.logpwd);
        fgBtn = findViewById(R.id.fgclick);
        fgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(i);
            }
        });
        LogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = logUsername.getEditText().getText().toString().trim();
                String pwd = logPwd.getEditText().getText().toString().trim();
                isUsersExist(username,pwd);
            }
        });

        String selectedLocation = SessionManager.getInstance(this).getSelectedLocation();
        Log.d("line 17",selectedLocation);
        btSignIn = findViewById(R.id.sign_in_button);
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode, 0).show();
            } else {
                Log.d("Line 49:","Google api");
            }
        }
        // Initialize sign in options the client-id is copied form google-services.json file
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("897725500517-he815dmsn6kibr0403rufeeba1aas555.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);

        btSignIn.setOnClickListener((View.OnClickListener) view -> {
            // Initialize sign in intent
            Intent intent = googleSignInClient.getSignInIntent();

            // Start activity for result
            startActivityForResult(intent, 100);
        });

        // Initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        // Add authentication state listener
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    // When user already sign in redirect to profile activity
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish(); // Finish the current activity
                } else {
                    Log.d(TAG, "Not logged in");
                }
            }
        });






    TextView createBtnClick = findViewById(R.id.account);
        createBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void isUsersExist(String username, String pwd) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot shot : snapshot.getChildren()) {
                        String key = shot.getKey();
                        String password = shot.child("password").getValue(String.class);
                        String username = shot.child("username").getValue(String.class);

                        if(password.equals(pwd)){

                            SessionManager.getInstance(LoginActivity.this).setUserId(key);
                            SessionManager.getInstance(LoginActivity.this).setUsername(username);
                            // to save our data with key and value.

                            Log.d("Existing User Key", key);
                            Log.d("Existing User Password", password);
                            Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(i);
                            finish();
                        }

                    }
                   // Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Sorry Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    // Method to handle sign-in result
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            Log.d("Line 114","Singin result");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("Line 116","Singin result");
            // Signed in successfully, authenticate with Firebase
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // Sign-in failed, handle failure
            Log.w(TAG, "signInResult:failed code=" + e);
        }
    }

    // Method to authenticate with Firebase using Google Sign-In credentials
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        // Update UI or do something with the user information
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        // Update UI with failure message
                    }
                });
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
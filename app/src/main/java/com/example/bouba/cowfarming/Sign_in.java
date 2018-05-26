package com.example.bouba.cowfarming;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

    public class Sign_in extends AppCompatActivity{
        Button btnLogin;
        FirebaseAuth mAuth;
        EditText input_email,input_password;
        TextView btnSignup,btnForgotPass;
        RelativeLayout activity_main;
        ProgressBar progressBar;
        SharedPreferences muser;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
            setContentView(R.layout.activity_sign_in);
            btnLogin = (Button)findViewById(R.id.login_btn_login);
            input_email = (EditText)findViewById(R.id.login_email);
            progressBar = (ProgressBar) findViewById(R.id.progressbar);
            mAuth = FirebaseAuth.getInstance();
            input_password = (EditText)findViewById(R.id.login_password);
            btnSignup = (TextView)findViewById(R.id.login_btn_signup);
            btnForgotPass = (TextView)findViewById(R.id.login_btn_forgot_password);
            activity_main = (RelativeLayout)findViewById(R.id.activity_main);
            muser =  getBaseContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Sign_in.this, Sign_up.class);
                    startActivity(i);
                }
            });
            btnForgotPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Sign_in.this, ForgotPassword.class);
                    startActivity(i);
                }
            });
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userLogin(input_email, input_password);
                }
            });
        }
        private void userLogin(EditText editTextEmail, EditText editTextPassword) {
            final String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty()) {
                editTextEmail.setError("Email is required");
                editTextEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Please enter a valid email");
                editTextEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                editTextPassword.setError("Password is required");
                editTextPassword.requestFocus();
                return;
            }

            if (password.length() < 6) {
                editTextPassword.setError("Minimum lenght of password should be 6");
                editTextPassword.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        finish();
                        Intent intent = new Intent(Sign_in.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        SharedPreferences.Editor editor = muser.edit();
                        editor.putString("user", email);
                        editor.commit();
                    }else {
                        Toast.makeText(getApplicationContext(), "Authentification failed", Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        boolean doubleBackToExitPressedOnce = false;
        @Override
        public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                finish();
                moveTaskToBack(true);
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {@Override public void run() {doubleBackToExitPressedOnce=false;}}, 2000);
        }
    }
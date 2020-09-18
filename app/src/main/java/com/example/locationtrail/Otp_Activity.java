package com.example.locationtrail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class Otp_Activity extends AppCompatActivity implements View.OnClickListener{

    private String verificationId;
    private ProgressBar progressBar;
    private EditText editText;
    private String phonenumber;
    private Button buttonSignIn;
    private String code;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        editText = findViewById(R.id.editTextcode);
        buttonSignIn= findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(this);
        phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);
    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("Mobile num", MODE_PRIVATE).edit();
                            editor.putString("number", phonenumber);
                            editor.apply();
                            editor.commit();
                            Intent intent = new Intent(Otp_Activity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Otp_Activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Otp_Activity.this, e.getMessage(),Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public void onClick(View v) {

        code = editText.getText().toString().trim();

        if ((code.isEmpty() || code.length() < 6)){

            editText.setError("Enter code...");
            editText.requestFocus();
            return;
        }
        verifyCode(code);

    }
}


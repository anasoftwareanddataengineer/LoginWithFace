package com.example.loginwithface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    Button btnAuth;
    TextView tvAuthStatus;

    private Executor exectuor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAuth = findViewById(R.id.btnAuth);
        tvAuthStatus = findViewById(R.id.tvAuthStatus);

        //init the valules
        exectuor= ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, exectuor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //in case of errors during authentication process
                tvAuthStatus.setText("Error"+errString);

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //success while authenticating
                tvAuthStatus.setText("Successful Authentication");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //failed to authenticate
                tvAuthStatus.setText("Failed to authenticate");
            }
        });

        //setup tiitle, description on auth dialog
        promptInfo=new BiometricPrompt.PromptInfo.Builder().setTitle("Biometric Authentication").setSubtitle("Login using fingerprint or face")
                .setNegativeButtonText("Cancel").build();

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //show authentication dialog
                biometricPrompt.authenticate(promptInfo);

            }
        });
    }
}
package com.example.appposyandu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appposyandu.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import javax.annotation.Nonnull;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    ProgressDialog dialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(ForgotPasswordActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Memuat ...");

        binding.btnReset.setOnClickListener(view -> {
            forgotPassword();
        });
    }

    private Boolean validateEmail(){
        String val = binding.emailBox.getText().toString();

        if (val.isEmpty()){
            binding.emailBox.setError("tidak boleh kosong!");
            return false;
        }else {
            binding.emailBox.setError(null);
            return true;
        }
    }
    private void forgotPassword() {
        if (!validateEmail()) {
            return;
        }

        dialog.show();
        firebaseAuth.sendPasswordResetEmail(binding.emailBox.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@Nonnull Task<Void> task) {
                dialog.dismiss();
                if(task.isSuccessful()){
                    startActivity(new Intent(ForgotPasswordActivity.this, login.class));
                    finish();
                    Toast.makeText(ForgotPasswordActivity.this, "Periksa Email Masuk Anda", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ForgotPasswordActivity.this, "Masukkan Email terdaftar", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@Nonnull Exception e) {
                Toast.makeText(ForgotPasswordActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
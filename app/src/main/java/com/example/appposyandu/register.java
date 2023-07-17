package com.example.appposyandu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

public class register extends AppCompatActivity {
    EditText nama, email, phone, password;
    Button regist, login;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    boolean valid = true;
    CheckBox isKaderBox, isPesertaBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        nama = findViewById(R.id.editNama);
        email = findViewById(R.id.editEmail);
        phone = findViewById(R.id.editPhone);
        password = findViewById(R.id.editPassword);
        regist = findViewById(R.id.btnRegist);
        login = findViewById(R.id.btnLogin);

        isKaderBox = findViewById(R.id.isKader);
        isPesertaBox = findViewById(R.id.isPeserta);


        //checkbox logic
        isPesertaBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    isKaderBox.setChecked(false);
                }
            }
        });

        isKaderBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    isPesertaBox.setChecked(false);
                }
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(nama);
                checkField(email);
                checkField(phone);
                checkField(password);


                if (valid){
                    //proses registrasi
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    Toast.makeText(register.this, "Berhasil membuat akun", Toast.LENGTH_SHORT).show();
                                    DocumentReference df = firestore.collection("Users").document(user.getUid());
                                    Map<String,Object> userInfo = new HashMap<>();
                                    userInfo.put("nama", nama.getText().toString());
                                    userInfo.put("email", email.getText().toString());
                                    userInfo.put("phone", phone.getText().toString());
                                    // jika menjadi kader
                                    if (isKaderBox.isChecked()){
                                        userInfo.put("isKader", "1");
                                    }
                                    if (isPesertaBox.isChecked()){
                                        userInfo.put("isPeserta", "1");
                                    }

                                    df.set(userInfo);
                                    if (isKaderBox.isChecked()){
                                        startActivity(new Intent(getApplicationContext(), dashboard_kader.class));
                                        finish();
                                    }

                                    if (isPesertaBox.isChecked()){
                                        startActivity(new Intent(getApplicationContext(), dashboard_peserta.class));
                                        finish();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@Nonnull Exception e) {
                            Toast.makeText(register.this, "Gagal membuat akun", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login .class));
            }
        });
    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
}
package com.example.discoveryubi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText email;
    private EditText password;
    private Button login;
    private ProgressDialog progressDialog;
    private Button goRegistar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        progressDialog = new ProgressDialog(this);
        fbAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.emailLog);
        password = (EditText) findViewById(R.id.passwordLog);

        login = (Button) findViewById(R.id.login);

        goRegistar = (Button) findViewById(R.id.goToRegistar);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = fbAuth.getCurrentUser();
                if( user != null){
                    Toast.makeText(Login.this,"Já está logged in ! ",Toast.LENGTH_SHORT).show();
                }
            }
        };

        goRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(Login.this , Registo.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String pass = password.getText().toString();
                if(Email.isEmpty()){
                    email.setError("Please enter your email ");
                    email.requestFocus();
                }
                if(pass.isEmpty()){
                    password.setError("Please enter your password ");
                    password.requestFocus();
                }

                else {

                        progressDialog.setMessage("A Verificar Dados ....");
                        progressDialog.show();

                        fbAuth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Login.this,"O login foi feito com sucesso! ",Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                                else {
                                    Toast.makeText(Login.this,"Os dados que forneceu estão errados ! ",Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                            }
                        });



                }
            }
        });




    }

    @Override
    protected void onStart(){
        super.onStart();
        fbAuth.addAuthStateListener(mAuthListener);
    }
}

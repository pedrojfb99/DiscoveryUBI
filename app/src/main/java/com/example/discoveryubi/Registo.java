package com.example.discoveryubi;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registo extends Activity {


    private Button buttonRegisto;
    private EditText email;
    private EditText password;
    private EditText cPassword;

    private ProgressDialog progressDialog;

    private FirebaseAuth fbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        progressDialog = new ProgressDialog(this);

        buttonRegisto = (Button) findViewById(R.id.confirmarRegisto);
        email = (EditText) findViewById(R.id.emailLog);
        password = (EditText) findViewById(R.id.passwordLog);
        cPassword = (EditText) findViewById(R.id.confirmPassword);


        fbAuth = FirebaseAuth.getInstance();

        System.out.println("jhadhashdiuahsidhaish");


        buttonRegisto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String pass = password.getText().toString();
                String cPass = cPassword.getText().toString();
                if(Email.isEmpty()){
                    email.setError("Please enter your email ");
                    email.requestFocus();
                }
                if(pass.isEmpty()){
                    password.setError("Please enter your password ");
                    password.requestFocus();
                }
                if(cPass.isEmpty()){
                    cPassword.setError("Please confirm your password ");
                    cPassword.requestFocus();
                }
                else {


                    //Password confirmada
                    if(pass.equals(cPass)){

                        progressDialog.setMessage("A Registar Utilizador ....");
                        progressDialog.show();

                        fbAuth.createUserWithEmailAndPassword(Email, pass).addOnCompleteListener(Registo.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registo.this, "Registo feito com sucesso", Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                } else {
                                    Toast.makeText(Registo.this, "Registo falhou . Tente outra vez ! ", Toast.LENGTH_SHORT).show();

                                }
                                // TODO -> Ir para a pagina principal
                            }

                        });
                    }
                    else {
                        Toast.makeText(Registo.this, "As password não coincidem!  ", Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });



    }

}



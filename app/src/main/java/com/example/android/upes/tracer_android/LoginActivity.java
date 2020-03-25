package com.example.android.upes.tracer_android;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.text.Html.fromHtml;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String user;
    private String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
     //   textViewCreateAccount.setText(fromHtml("<font color='#ffffff'>I don't have account yet. </font><font color='#00b8d4'>create one</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        final EditText userEdit=(EditText)findViewById(R.id.editTextEmail);
        final EditText userPass=(EditText)findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();
        Button btn=(Button)findViewById(R.id.buttonLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String user= userEdit.getText().toString();
               String pass=userPass.getText().toString();
                ValidateLogin(user,pass);
                login(user,pass);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }
    public void login(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AUTH SUCCESS", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null){
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));}
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AUTH FAILED", "signInWithEmail:failure", task.getException());

                            Toast.makeText(LoginActivity.this,"Invalid ID", Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }
    public void ValidateLogin(String email,String password){
        if(email.equals(null)){
            Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show();
        }
        if(email.equals(null)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
    }
}

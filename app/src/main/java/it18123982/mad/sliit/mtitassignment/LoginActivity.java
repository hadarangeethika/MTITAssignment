package it18123982.mad.sliit.mtitassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText edtemail, edtpassword;
    Button btnLogin;
    TextView directRegister;
    ProgressBar progressBar2;
    FirebaseAuth fAuth;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //change color of the Action Bar
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6e050c")));

        edtemail = findViewById(R.id.edtEmail);
        edtpassword = findViewById(R.id.edtPassword1);
        progressBar2 = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btnLogin);
        directRegister = findViewById(R.id.txtAddText2);


        directRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }

    public void tappedLogin(View view) {
        String email = edtemail.getText().toString().trim();
        String password = edtpassword.getText().toString().trim();

        //validate data
        //checking whether the email & password fields are empty or not
        if (TextUtils.isEmpty(email)){
            edtemail.setError("Email is required");
            return;
        }if (TextUtils.isEmpty(password)){
            edtpassword.setError("Password is required");
            return;
        }

        //checking the length of the password
        if (password.length()<6){
            edtpassword.setError("Password must be more then 6 characters");
            return;
        }

        progressBar2.setVisibility(View.VISIBLE);

        //authenticate the user
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"You've logged in successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this,"Error ! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    progressBar2.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
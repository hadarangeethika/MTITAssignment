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

public class RegisterActivity extends AppCompatActivity {
    EditText edtfullname, edtemail, edtpassword, edtphone;
    Button btnRegister;
    TextView directLogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //change color of the Action Bar
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6e050c")));

        edtfullname = findViewById(R.id.edtFullname);
        edtemail = findViewById(R.id.edtEmail);
        edtpassword = findViewById(R.id.edtPassword1);
        edtphone = findViewById(R.id.edtPhone);
        btnRegister = findViewById(R.id.btnLogin);
        directLogin = findViewById(R.id.txtAddText2);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //checking whether the user has already got an account
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        directLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }

    public void tappedRegister(View view) {
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

        progressBar.setVisibility(View.VISIBLE);

        //register the user in Firebase
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"User Registered Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    Toast.makeText(RegisterActivity.this,"Error ! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


}
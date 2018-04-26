package com.hillygeeks.mdpproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hillygeeks.mdpproject.DataClasses.User;

import static android.text.TextUtils.isEmpty;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    //widgets
    private EditText mEmail,mName, mPhonenumber,mPassword, mConfirmPassword;
    private Button mRegister;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = (EditText) findViewById(R.id.input_email);
        mName= (EditText) findViewById(R.id.input_name);
        mPhonenumber = (EditText) findViewById(R.id.input_phonenumber);
        mPassword = (EditText) findViewById(R.id.input_password);
        mConfirmPassword = (EditText) findViewById(R.id.input_confirm_password);
        mRegister = (Button) findViewById(R.id.btn_register);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to register.");

                //check for null valued EditText fields
                if(!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())
                        && !isEmpty(mConfirmPassword.getText().toString())){

                    //check if user has a company email address
                    if(isValidDomain(mEmail.getText().toString())){
                        //check if passwords match
                        if(doStringsMatch(mPassword.getText().toString(), mConfirmPassword.getText().toString())){
                            if (mName.getText().toString().length()>2) {
                                //check if phone number if correct
                                if (mPhonenumber.getText().toString().length()>5) {
                                    //Initiate registration task
                                    registerNewEmail(mEmail.getText().toString(), mPassword.getText().toString(),mPhonenumber.getText().toString(),mName.getText().toString());
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, "Input Proper Phone Number", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Input Proper Name", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Please Register with Company Email", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegisterActivity.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        hideSoftKeyboard();
    }

     // Register a new email and password to Firebase Authentication
    public void registerNewEmail(final String email, String password, final String phonenumber, final String name){
        showDialog();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            Toast.makeText(RegisterActivity.this, "Account is created!", Toast.LENGTH_SHORT).show();
                            // Send verification email
                            sendVerificationEmail();
                            String user_key=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            //TODO set the firebase messaging id
                            User user=new User(email, FirebaseInstanceId.getInstance().getToken());
                            user.setPhone(phonenumber);
                            user.setName(name);
                            Application.UsersRef.child(user_key).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("db status","User Saved");
                                }
                            });
                            //save last email in sharedpreference
                            SharedPreferences.Editor editor = Application.sharedpreferences.edit();
                            editor.putString("user_email", email);
                            editor.putString("user_name", user.getName());
                            editor.commit();
                            FirebaseAuth.getInstance().signOut();
                            //redirect the user to the login screen
                            redirectLoginScreen();
                        }else {
                            Toast.makeText(RegisterActivity.this, "Failed to sign up!", Toast.LENGTH_SHORT).show();
                        }
                        hideDialog();

                    }
                });
    }

    /**
     * sends an email verification link to the user
     */
    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Couldn't Verification Send Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

     // verify if user enter invalid email address
    private boolean isValidDomain(String email){
        return true;
    }

   // Match if two string are equal
    private boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
    }

    private boolean isEmpty(String string){
        return string.equals("");
    }

    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}

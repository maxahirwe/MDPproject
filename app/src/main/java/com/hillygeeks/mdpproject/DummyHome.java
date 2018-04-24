package com.hillygeeks.mdpproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DummyHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_home);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkAuthenticate();
    }

    private void checkAuthenticate() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent intent = new Intent(DummyHome.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        checkAuthenticate();
    }

}

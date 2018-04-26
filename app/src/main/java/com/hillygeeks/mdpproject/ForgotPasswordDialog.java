package com.hillygeeks.mdpproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordDialog extends DialogFragment {
    private static final String TAG = "ForgotPasswordDialog";

    //widgets
    private EditText mEmail;

    //vars
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password_dialog, container, false);
        mEmail = (EditText) view.findViewById(R.id.email_password_reset);
        mContext = getActivity();

        TextView confirmDialog = (TextView) view.findViewById(R.id.dialogConfirm);
        confirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(mEmail.getText().toString())){
                    Log.d(TAG, "onClick: attempting to send reset link to: " + mEmail.getText().toString());
                    sendPasswordResetEmail(mEmail.getText().toString());
                    getDialog().dismiss();
                }

            }
        });
        return view;
    }

    /*
     * Send a password reset link to the email provided
     */
    public void sendPasswordResetEmail(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Password Reset Email sent.");
                            Toast.makeText(mContext, "Password Reset Link Sent to Email",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d(TAG, "onComplete: No user associated with that email.");
                            Toast.makeText(mContext, "No User is Associated with that Email",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private boolean isEmpty(String string){
        return string.equals("");
    }
}
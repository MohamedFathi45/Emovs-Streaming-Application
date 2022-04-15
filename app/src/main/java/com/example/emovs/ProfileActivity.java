package com.example.emovs;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emovs.utilities.CircleTransform;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;

public class ProfileActivity extends AppCompatActivity {

    TextView userName;
    ImageView userPhoto;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName = findViewById(R.id.id_user_name);
        userPhoto = findViewById(R.id.id_userPhoto);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("162836358215-jm067gc3plp7k8njb7cq2vk3858tkfoo.apps.googleusercontent.com").requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this , gso);
        setUserInfo();
    }

    private void setUserInfo() {
        if(com.example.emovs.MainActivity.account != null){
            userName.setText(com.example.emovs.MainActivity.account.getDisplayName());
            if(com.example.emovs.MainActivity.account.getPhotoUrl() != null){
                Picasso.get().load(com.example.emovs.MainActivity.account.getPhotoUrl()).transform(new CircleTransform()).into(userPhoto);
                //Picasso.get().load(MainActivity.account.getPhotoUrl()).fit().into(userPhoto);
            }
            else{
                    userPhoto.setImageResource(R.drawable.profile);
            }
        }
    }

    public void logoutClick(View view) {
        signOut();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(com.example.emovs.MainActivity.account != null){
            setUserInfo();
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        com.example.emovs.MainActivity.account = null;
                        com.example.emovs.MainActivity.userId = new BigInteger("0");
                        finish();
                    }
                });
    }

}

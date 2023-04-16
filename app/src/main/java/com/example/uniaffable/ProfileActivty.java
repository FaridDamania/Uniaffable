package com.example.uniaffable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.authenticator.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivty extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, search, notifications, profile, settings, logout;
    GoogleSignInClient gClient;
    GoogleSignInOptions gOptions;
    TextView profileName, profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activty);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        search = findViewById(R.id.search);
        //notifications = findViewById(R.id.notifications);
        profile = findViewById(R.id.profile);
        settings = findViewById(R.id.settings);
        logout = findViewById(R.id.logout);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);

        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(this, gOptions);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();


            profileName.setText(name);
            profileEmail.setText(email);

            // Use name and email as required
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ProfileActivty.this, MainActivity.class);
            }
        });



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(ProfileActivty.this, SettingsActivity.class);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(ProfileActivty.this, LoginActivity.class));
                    }
                });
            }
        });
    }

    public static void openDrawer (DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer (DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity (Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
}

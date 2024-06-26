package com.android.foodorderapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AboutUsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private SessionManager sessionManager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout1);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Hostel-Bites");
        } else {
            Log.e("AboutUsActivity", "Action bar is null");
            setTitle("About Us");
        }

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Show a toast message when the user interacts with the RatingBar
                Toast.makeText(AboutUsActivity.this, "You rated us: " + rating, Toast.LENGTH_SHORT).show();

                // Show an alert dialog when the user submits their rating
                if (rating >= 0) {
                    showThankYouDialog();

                }
            }
        });
    }

    private void showThankYouDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thank You!")
                .setMessage("Thank you for your rating!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId(); // Get the ID of the clicked item

        switch (id) {
            case R.id.login:
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    // User is logged in
                    Toast.makeText(this, "User already logged in", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(AboutUsActivity.this, SendOTPActivity.class));
                }
                break;
            case R.id.home:
                startActivity(new Intent(AboutUsActivity.this, MainActivity.class));
                break;
            case R.id.subscription:
                startActivity(new Intent(AboutUsActivity.this, SubscriptionActivity.class));
                break;
            case R.id.profile:
                startActivity(new Intent(AboutUsActivity.this, ProfileActivity.class));
                break;
            case R.id.about_us:
                break;
            case R.id.logout:
                mAuth.signOut();
                sessionManager.clearSession();
                Intent intent = new Intent(AboutUsActivity.this, SendOTPActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }
}

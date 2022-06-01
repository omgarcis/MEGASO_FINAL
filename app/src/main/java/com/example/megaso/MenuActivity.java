package com.example.megaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.megaso.fragments.HomeFragment;
import com.example.megaso.fragments.ProfileFragment;
import com.example.megaso.fragments.Settings_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {
    FirebaseAuth Auth;
    //objeto bottom
    BottomNavigationView mBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //instanciar
        Auth = FirebaseAuth.getInstance();

        showSelectedFragment(new HomeFragment());
        //fragments

        mBottom = (BottomNavigationView)findViewById(R.id.bottomNav);
        mBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    if(menuItem.getItemId() == R.id.menu_home){
                        showSelectedFragment(new HomeFragment());
                    }
                    if(menuItem.getItemId() == R.id.menu_profile){
                        showSelectedFragment(new ProfileFragment());
                    }
                    if(menuItem.getItemId() == R.id.menu_settings){
                        showSelectedFragment(new Settings_Fragment());
                    }
                return false;
            }
        });
    }

    //metodo que permite elegir fragment

    private void showSelectedFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
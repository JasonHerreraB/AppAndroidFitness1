package com.example.proj_appfitnes_simple;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerlayout;
    private TextView email;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerlayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.open_nav, R.string.close_nav);

        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            int userId;
            if (getSharedPreferences("user_prefs", Context.MODE_PRIVATE) != null){
                preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                userId = preferences.getInt("user_id", -1);
                if (userId != -1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_login);
                }else {

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IniciarSesionFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_login);
                }
            }else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IniciarSesionFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_login);
            }

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        } else {
            if (id == R.id.nav_login){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IniciarSesionFragment()).commit();
            }else {
                if ( id == R.id.nav_registrarse ){
                    int userId;
                    if (getSharedPreferences("user_prefs", Context.MODE_PRIVATE) != null){
                        preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                        userId = preferences.getInt("user_id", -1);
                        if (userId != -1){
                            Toast.makeText(MainActivity.this, "Solo se puede si cierras sesion",Toast.LENGTH_SHORT).show();
                        }else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegistrarUsuarioFragment()).commit();
                        }
                    }else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegistrarUsuarioFragment()).commit();
                    }

                }else {
                    if (id == R.id.nav_opciones){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ConfiguracionFragment()).commit();
                    }else {
                        if (id == R.id.nav_logout){
                            int userId;
                            if (getSharedPreferences("user_prefs", Context.MODE_PRIVATE) != null){
                                preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                                userId = preferences.getInt("user_id", -1);
                                if (userId != -1){
                                    SharedPreferences preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("user_id", -1);
                                    editor.apply();
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new IniciarSesionFragment()).commit();
                                }else {
                                    Toast.makeText(MainActivity.this, "Nadie está logueado",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(MainActivity.this, "Nadie está logueado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        }


        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
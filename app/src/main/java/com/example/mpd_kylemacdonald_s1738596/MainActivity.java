// Kyle MacDonald S1738596


package com.example.mpd_kylemacdonald_s1738596;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);
            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_incidents,
                    R.id.navigation_RoadWorks,
                    R.id.navigation_PRoadworks)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


            Toolbar searchbar = findViewById(R.id.searchbar);

            setSupportActionBar(searchbar);


            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

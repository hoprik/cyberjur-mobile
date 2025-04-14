package ru.egmohf.cyberjur.ui.activities;

import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import ru.egmohf.cyberjur.R;
import ru.egmohf.cyberjur.api.interfaces.User;
import ru.egmohf.cyberjur.api.objects.UserObject;
import ru.egmohf.cyberjur.databinding.ActivityMainBinding;
import ru.egmohf.cyberjur.saveData.Cache;
import ru.egmohf.cyberjur.saveData.ConfigKeys;
import ru.egmohf.cyberjur.ui.popup.PopupEngine;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PopupEngine.getINSTANCE().changeActivity(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User.getOne(profile -> {});

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setBackgroundColor(Color.parseColor(Cache.getInstance().getStringData(ConfigKeys.BG_SECOND.getKey(), "")));
        navView.setItemIconTintList(new ColorStateList(new int[][]{new int[]{android.R.attr.state_checked}, new int[]{}}, new int[]{Color.parseColor(Cache.getInstance().getStringData(ConfigKeys.GREEN.getKey(), "")), Color.parseColor(Cache.getInstance().getStringData(ConfigKeys.WHITE.getKey(), ""))}));
        navView.setItemTextColor(new ColorStateList(new int[][]{new int[]{android.R.attr.state_checked}, new int[]{}}, new int[]{Color.parseColor(Cache.getInstance().getStringData(ConfigKeys.GREEN.getKey(), "")), Color.parseColor(Cache.getInstance().getStringData(ConfigKeys.WHITE.getKey(), ""))}));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_diary, R.id.navigation_calendar)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}
package edu.psu.avp5564.mymovielist.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import edu.psu.avp5564.mymovielist.R;

public class SettingsActivity extends AppCompatActivity {

    ConstraintLayout settingsLayout;
    Button whiteButton;
    Button grayButton;
    Button greenButton;
    Button blueButton;
    Button redButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsLayout = (ConstraintLayout) findViewById(R.id.settingsLayout);

        whiteButton = (Button) findViewById(R.id.whiteButton);
        grayButton = (Button) findViewById(R.id.greyButton);
        greenButton = (Button) findViewById(R.id.greenButton);
        blueButton = (Button) findViewById(R.id.blueButton);
        redButton = (Button) findViewById(R.id.redButton);

        readTheme(settingsLayout);

        whiteButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        saveTheme(getString(R.string.WHITE_CAP), settingsLayout);
                        Toast.makeText(getApplicationContext(), R.string.setbgwhite, Toast.LENGTH_SHORT).show();

                    }
                });

        grayButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        saveTheme(getString(R.string.GRAY_CAP), settingsLayout);
                        Toast.makeText(getApplicationContext(), R.string.setbggray, Toast.LENGTH_SHORT).show();
                    }
                });

        greenButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        saveTheme(getString(R.string.GREEN_CAP), settingsLayout);
                        Toast.makeText(getApplicationContext(), R.string.setbggreen, Toast.LENGTH_SHORT).show();
                    }
                });

        blueButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        saveTheme(getString(R.string.BLUE_CAP), settingsLayout);
                        Toast.makeText(getApplicationContext(), R.string.setbgblue, Toast.LENGTH_SHORT).show();
                    }
                });

        redButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        saveTheme(getString(R.string.RED_CAP), settingsLayout);
                        Toast.makeText(getApplicationContext(), R.string.setbgred, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void saveTheme(String theme, ConstraintLayout constraintLayout) {

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.remove(getString(R.string.THEME_KEY));
        prefsEditor.commit();
        prefsEditor.putString(getString(R.string.THEME_KEY), theme);
        prefsEditor.commit();

        setTheme(theme, constraintLayout);
    }

    public void readTheme(ConstraintLayout constraintLayout) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());

        String theme = appSharedPrefs.getString(getString(R.string.THEME_KEY), null);

        setTheme(theme, constraintLayout);
    }

    public void setTheme(String theme, ConstraintLayout constraintLayout) {

        if (theme.equals(getString(R.string.WHITE_CAP))) {
//            setTheme(R.style.AppTheme);
            constraintLayout.setBackgroundColor(Color.WHITE);
        }
        else if (theme.equals(getString(R.string.GRAY_CAP))) {
            constraintLayout.setBackgroundColor(Color.GRAY);
        }
        else if (theme.equals(getString(R.string.GREEN_CAP))) {
            constraintLayout.setBackgroundColor(Color.GREEN);
        }
        else if (theme.equals(getString(R.string.BLUE_CAP))) {
            constraintLayout.setBackgroundColor(Color.BLUE);
        }
        else if (theme.equals(getString(R.string.RED_CAP))) {
            constraintLayout.setBackgroundColor(Color.RED);
        }
    }
}

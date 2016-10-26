package ryanharvey.ebaydemoproject.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import ryanharvey.ebaydemoproject.Constants;
import ryanharvey.ebaydemoproject.R;
import ryanharvey.ebaydemoproject.util.PermissionUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.weatherDisplayButton) Button weatherDisplayButton;
    @Bind(R.id.mainActvityTitleTextView) TextView mainActivityTitleTextView;
    @Bind(R.id.zipCodeButton) Button zipCodeButton;
    @Bind(R.id.zipCodeEditText) EditText zipCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Apply Bio Rhyme font to title text view at run time
        Typeface bioRhymeFont = Typeface.createFromAsset(getAssets(), "fonts/BioRhyme-Regular.ttf");
        mainActivityTitleTextView.setTypeface(bioRhymeFont);

        //Bind Buttons to Click Listener
        weatherDisplayButton.setOnClickListener(this);
        zipCodeButton.setOnClickListener(this);

        enableMyLocation();

    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(MainActivity.this, Constants.LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == weatherDisplayButton){
            //On Weather Display button click, start Weather Display Activity
            Intent weatherDisplayIntent = new Intent(MainActivity.this, WeatherDisplayActivity.class);
            weatherDisplayIntent.putExtra("zipCodeSelected", false);
            startActivity(weatherDisplayIntent);
        } else if (view == zipCodeButton){
            boolean isValidZip = isValidZipCode(zipCodeEditText.getText().toString());
            if(isValidZip) {
                Intent weatherDisplayIntent = new Intent(MainActivity.this, WeatherDisplayActivity.class);
                weatherDisplayIntent.putExtra("zipCodeSelected", true);
                weatherDisplayIntent.putExtra("zipCode", zipCodeEditText.getText().toString());
                startActivity(weatherDisplayIntent);
            }
        }
    }

    private boolean isValidZipCode(String zip) {
        String zipRegEx = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(zipRegEx);
        Matcher matcher = pattern.matcher(zip);
        if (matcher.matches()){
            return true;
        } else {
            zipCodeEditText.setError("Please Enter A Valid Zip Code");
            return false;
        }
    }
}

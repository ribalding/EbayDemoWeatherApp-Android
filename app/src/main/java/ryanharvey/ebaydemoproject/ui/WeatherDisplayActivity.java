package ryanharvey.ebaydemoproject.ui;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import ryanharvey.ebaydemoproject.Constants;
import ryanharvey.ebaydemoproject.R;
import ryanharvey.ebaydemoproject.WeatherService;
import ryanharvey.ebaydemoproject.util.PermissionUtils;

public class WeatherDisplayActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, View.OnClickListener{

    private GoogleApiClient googleApiClient;
    private ProgressDialog dialog;
    private String zipCode;
    private boolean zipCodeIsSelected;
    @Bind(R.id.cityNameTextView) TextView cityNameTextView;
    @Bind(R.id.mainWeatherTextView) TextView mainWeatherTextView;
    @Bind(R.id.tempTextView) TextView tempTextView;
    @Bind(R.id.backButton) Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);
        ButterKnife.bind(this);

        backButton.setOnClickListener(this);

        //Unpack Intent
        zipCode = getIntent().getStringExtra("zipCode");
        zipCodeIsSelected = getIntent().getBooleanExtra("zipCodeSelected", false);


        //Apply Bio Rhyme font to text views at run time
        Typeface bioRhymeFont = Typeface.createFromAsset(getAssets(), "fonts/BioRhyme-Regular.ttf");
        cityNameTextView.setTypeface(bioRhymeFont);
        mainWeatherTextView.setTypeface(bioRhymeFont);
        tempTextView.setTypeface(bioRhymeFont);

        //Open Progress Dialog
        dialog = ProgressDialog.show(this, "Please Hold", "Getting All Your Weathers...", true);

        createGoogleAPIClient();
    }

    //Create Google API Client
    public void createGoogleAPIClient(){
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(AppIndex.API).build();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //If location permission is granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //If search mode
            if (!zipCodeIsSelected) {
                Location deviceLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                LatLng deviceLocationLatLng = new LatLng(deviceLocation.getLatitude(), deviceLocation.getLongitude());
                WeatherService.findWeather(deviceLocationLatLng, weatherDisplayCallback);
            } else {
                WeatherService.findWeather(zipCode, weatherDisplayCallback);
            }
        } else {
            PermissionUtils.requestPermission(WeatherDisplayActivity.this, Constants.LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    //Google API Client onStart
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    //Google API Client onStop
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    private Callback weatherDisplayCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialog.dismiss();
                final ArrayList<String> results = WeatherService.processResults(response);
                WeatherDisplayActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        cityNameTextView.setText(results.get(0));
                        mainWeatherTextView.setText(results.get(1));
                        tempTextView.setText(results.get(2) + "°F");
                    }
                });
            }
    };

    @Override
    public void onClick(View view) {
        if (view == backButton){
            onBackPressed();
        }
    }
}

package ryanharvey.ebaydemoproject;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

public class WeatherDisplayActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks{
    private GoogleApiClient googleApiClient;
    private Location deviceLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);

        createGoogleAPIClient();
    }

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
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            deviceLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            WeatherService.findWeather(new LatLng(deviceLastLocation.getLatitude(), deviceLastLocation.getLongitude()));
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
}

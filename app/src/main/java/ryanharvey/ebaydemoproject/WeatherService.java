package ryanharvey.ebaydemoproject;

import android.util.Log;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Ryan on 10/24/2016.
 */
public class WeatherService {
    public static void findWeather(LatLng latLng){
        String lat = Double.toString(latLng.latitude);
        String lng = Double.toString(latLng.longitude);
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.WEATHER_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("lat", lat);
        urlBuilder.addQueryParameter("lon", lng);
        urlBuilder.addQueryParameter("APPID", Constants.API_KEY);
        String url = urlBuilder.build().toString();

        Log.d("TEST", url);
    }
}

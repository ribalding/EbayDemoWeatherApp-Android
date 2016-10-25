package ryanharvey.ebaydemoproject;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by Ryan on 10/24/2016.
 */
public class WeatherService {
    public static void findWeather(){
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.WEATHER_BASE_URL).newBuilder();
    }
}

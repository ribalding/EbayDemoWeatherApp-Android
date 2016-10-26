package ryanharvey.ebaydemoproject;

import android.content.Intent;
import android.os.Build;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import ryanharvey.ebaydemoproject.ui.MainActivity;
import ryanharvey.ebaydemoproject.ui.WeatherDisplayActivity;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Ryan on 10/26/2016.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)

public class MainActivityUnitTest {
    private MainActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void validateTextViewContent() {
        TextView mainActivityTitleTextView = (TextView) activity.findViewById(R.id.mainActvityTitleTextView);
        assertTrue("Epic Weather".equals(mainActivityTitleTextView.getText().toString()));
    }

    @Test
    public void weatherDisplayActivityStarted() {
        activity.findViewById(R.id.weatherDisplayButton).performClick();
        Intent expectedIntent = new Intent(activity, WeatherDisplayActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }


}

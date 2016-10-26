package ryanharvey.ebaydemoproject;

import android.os.Build;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ryanharvey.ebaydemoproject.ui.MainActivity;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Ryan on 10/26/2016.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)

public class MainActivityTest{
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
}

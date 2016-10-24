package ryanharvey.ebaydemoproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.mainActivityButton) Button mainActivityButton;
    @Bind(R.id.mainActvityTitleTextView) TextView mainActivityTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Apply Bio Rhyme font to title text view at run time
        Typeface bioRhymeFont = Typeface.createFromAsset(getAssets(), "fonts/BioRhyme-Regular.ttf");
        mainActivityTitleTextView.setTypeface(bioRhymeFont);

        mainActivityButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == mainActivityButton){
            //On Weather Display button click, start Weather Display Activity
            Intent weatherDisplayIntent = new Intent(MainActivity.this, WeatherDisplayActivity.class);
            startActivity(weatherDisplayIntent);
        }
    }
}

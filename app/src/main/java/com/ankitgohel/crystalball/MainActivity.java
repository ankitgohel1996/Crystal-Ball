package com.ankitgohel.crystalball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private CrystalBall mCrystalBall = new CrystalBall();
    private TextView mAnswerLabel;
    private ImageView mCrystalBallImage;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign the views from the layout file

        mAnswerLabel = (TextView)findViewById(R.id.textView);
        mCrystalBallImage = (ImageView)findViewById(R.id.imageView);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener()
        {
            @Override
            public void onShake()
            {
                handleNewAnswer();
            }
        });

        Toast welcomeToast = Toast.makeText(this,"Welcome to Crystal Ball!",Toast.LENGTH_LONG);
        welcomeToast.show();

    }

    @Override
    public void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
    }


    private void handleNewAnswer()
    {
        String answer = mCrystalBall.getAnAnswer();

        // Update the label with our selected answer
        mAnswerLabel.setText(answer);

        animateCrystalBall();
        animateAnswer();
        playSound();
    }

    private void animateCrystalBall()
    {
        mCrystalBallImage.setImageResource(R.drawable.ball_animation);
        AnimationDrawable ballAnimation = (AnimationDrawable)mCrystalBallImage.getDrawable();

        if(ballAnimation.isRunning())
        {
            ballAnimation.stop();
        }

        ballAnimation.start();

    }

    private void animateAnswer()
    {
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0,1);
        fadeInAnimation.setDuration(1500);
        fadeInAnimation.setFillAfter(true);

        mAnswerLabel.setAnimation(fadeInAnimation);
    }

    private void playSound()
    {
        MediaPlayer player = MediaPlayer.create(this,R.raw.crystal_ball);

        player.start();

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                mp.release();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       // Handle action bar item clicks here. The action bar will
       // automatically handle clicks on the Home/Up button, so long
       // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

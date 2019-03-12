package edu.upc.dsa.restclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private Track track;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_info);

        Intent intent = getIntent();
        track = new Track(intent.getStringExtra("trackTitle"), intent.getStringExtra("trackSinger"));
        track.setId(intent.getStringExtra("trackId"));

        TextView textViewId = findViewById(R.id.textViewId);
        textViewId.setText(track.getId());
    }
}

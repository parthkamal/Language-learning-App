package com.parth.learnmiwok;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    //media player to be called when a media is to be played
    private MediaPlayer mediaPlayer;

    AudioManager audioManager;
    //this is to be used for managing the audio interruptions

    AudioAttributes audioAttributes;
    //audio attributes to be used to tell the audio focus the type of media we want to play

    //defining the on focus change listener method and implementing its callback function onFocus change
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i == AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }
            else if(i== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);

            }
            else if(i== AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("one", "lutti", R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine", "wo’e", R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten", "na’aacha", R.drawable.number_ten,R.raw.number_ten));

        //managing audio focus
        audioManager =(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // defining playback attributes

        audioAttributes =new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

        AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(audioAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                .build();

       final int audioFocusRequest = audioManager.requestAudioFocus(focusRequest);


        //checking the log messages

        //attaching adapter to the data source

        wordsAdapter customAdapter = new wordsAdapter(this,words,R.color.category_numbers);
        //attaching view source to the array adapter
        ListView listView=findViewById(R.id.lv_number_items);
        listView.setAdapter(customAdapter);

        //setting on item click listener to play song
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            //release the mediaplayer if it it playing
            releaseMediaPlayer();


            Word current_data = (Word) adapterView.getItemAtPosition(i);
            if(audioFocusRequest ==AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer =MediaPlayer.create(NumbersActivity.this,current_data.getSongResource());
                mediaPlayer.start();
                //we need to detach the resource after the start of the audio
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        releaseMediaPlayer();
                    }
                });
            }
        });

    }

    //we also need to release the media source when we leave the activity

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
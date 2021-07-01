package com.parth.learnmiwok.fragments;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parth.learnmiwok.R;
import com.parth.learnmiwok.Word;
import com.parth.learnmiwok.wordsAdapter;

import java.util.ArrayList;


public class ColorsFragment extends Fragment {

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


    public ColorsFragment() {
        // Required empty public constructor
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_numbers, container, false);
        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red,R.raw.color_red));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        words.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("green", "chokokki", R.drawable.color_green,R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black,R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white,R.raw.color_white));

        //managing audio focus
        audioManager =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
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

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        wordsAdapter adapter = new wordsAdapter(getActivity(), words,R.color.category_colors);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.lv_number_items);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        //setting on item click listener to play song
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                releaseMediaPlayer();

                Word current_data = (Word) adapterView.getItemAtPosition(i);
                if(audioFocusRequest ==AudioManager.AUDIOFOCUS_GAIN){
                    mediaPlayer =MediaPlayer.create(getActivity(),current_data.getSongResource());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });
                }

            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onStop() {
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
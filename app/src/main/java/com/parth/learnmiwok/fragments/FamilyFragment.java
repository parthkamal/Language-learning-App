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

public class FamilyFragment extends Fragment {
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



    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_numbers, container, false);
        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("father", "әpә", R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother ", "ama", R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather,R.raw.family_grandfather));

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
        wordsAdapter adapter = new wordsAdapter(getActivity(), words,R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView =rootView.findViewById(R.id.lv_number_items);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        //setting on item click listener to play song
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word current_data = (Word) adapterView.getItemAtPosition(i);

                releaseMediaPlayer();

                if(audioFocusRequest ==AudioManager.AUDIOFOCUS_GAIN){
                    mediaPlayer = MediaPlayer.create(getActivity(),current_data.getSongResource());
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
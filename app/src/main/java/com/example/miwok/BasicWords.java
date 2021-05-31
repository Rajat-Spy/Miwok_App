package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class BasicWords extends AppCompatActivity {

    MediaPlayer mMediaPlayer = new MediaPlayer();

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFoxusChangeListner =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange ==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Hello", "Michuksüs", R.drawable.basic_words_one, R.raw.phrase_come_here));
        words.add(new Word("Good", "’Kuchi", R.drawable.basic_words_two, R.raw.phrase_lets_go));
        words.add(new Word("Stop", "Chelku'", R.drawable.basic_words_three, R.raw.phrase_lets_go));
        words.add(new Word("I Am", "Me a'", R.drawable.basic_words_four, R.raw.phrase_yes_im_coming));
        words.add(new Word("Yes", "hu'", R.drawable.basic_words_five ,R.raw.color_mustard_yellow));
        words.add(new Word("No", "’Ew:utu", R.drawable.basic_words_six, R.raw.phrase_im_feeling_good));
        words.add(new Word("Maybe", "‘ushe:mu", R.drawable.basic_words_seven, R.raw.phrase_where_are_you_going));
        words.add(new Word("Deer", "’uwu:ya", R.drawable.basic_words_eight, R.raw.phrase_im_coming));
        words.add(new Word("Finish!", "Lep:ani’!", R.drawable.basic_words_three, R.raw.phrase_what_is_your_name));

        WordAdapter itemsAdapter = new WordAdapter(this, words,R.color.category_basic_words);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFoxusChangeListner,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback

                    mMediaPlayer = MediaPlayer.create(BasicWords.this, word.hasAudio());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFoxusChangeListner);
        }
    }
}
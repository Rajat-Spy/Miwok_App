package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

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
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("One", "Lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("Two", "’oṭi:ko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("Three", "tolo:koshu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("Four", "oy:is:a", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("Five", "mash:ok:a", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("Six", "tem:ok:a", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("Seven", "kenek:aku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("Eight", "kaw:inṭa", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("Nine", "Wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("Ten", "na’a:cha", R.drawable.number_ten, R.raw.number_ten));
//        words.add(new Word("Eleven", "ke˙nghe˙ṭiya:ku", R.drawable.number_eleven, R.raw.number_one));
//        words.add(new Word("Twelve", "‘oṭikshake˙nu", R.drawable.number_twelve, ));
//        words.add(new Word("Thirteen", "‘na’a:cha’ tolo:koshu he˙y:i’", R.drawable.number_thirteen));
//        words.add(new Word("Fourteen", "‘na’a:cha’ ’oy:is:a’ hey:i", R.drawable.number_fourteen));
//        words.add(new Word("Fifteen", "na’a:cha’ mash:ok:a’ hey:i’", R.drawable.number_fifteen));

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_numbers);

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

                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.hasAudio());
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
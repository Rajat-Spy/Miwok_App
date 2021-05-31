package com.example.miwok;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;
     public WordAdapter(Context context, ArrayList<Word> words, int colorResourceId){
         super(context, 0, words);
         mColorResourceId = colorResourceId;
     }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
         View listItemView = convertView;
         if(listItemView == null){
             listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
         }
         Word currentWord = getItem(position);
        TextView wordtextView = (TextView) listItemView.findViewById(R.id.lutti_text_view);
        wordtextView.setText(currentWord.getMiwokTranslation());
        TextView miiwolTextView = (TextView) listItemView.findViewById(R.id.one_text_view);
        miiwolTextView.setText(currentWord.getDefaultTranslation());
        ImageView imageView = (ImageView)listItemView.findViewById((R.id.image_view));
        if(currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getImageId());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        View textContainer = listItemView.findViewById(R.id.container_view);
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);
        return listItemView;
    }
}

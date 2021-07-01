package com.parth.learnmiwok;

import com.parth.learnmiwok.Word;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



import java.util.ArrayList;

public class wordsAdapter extends ArrayAdapter<Word> {

    private int colorId;

    public wordsAdapter(Activity context, ArrayList<com.parth.learnmiwok.Word> numbers,int colorId) {
        super(context, 0, numbers);
        this.colorId =colorId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView ==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items1,parent,false);
        }

        Word currentWord = (Word) getItem(position);
        TextView tv_miwok = listItemView.findViewById(R.id.miwod_translation);
        TextView tv_english = listItemView.findViewById(R.id.default_translation);
        ImageView imageView =listItemView.findViewById(R.id.miwok_image);



        tv_miwok.setText(currentWord.getMiwokTranslation());
        tv_miwok.setBackgroundResource(colorId);
        tv_english.setText(currentWord.getDefaultTranslation());
        tv_english.setBackgroundResource(colorId);
        if(currentWord.hasImage()){
            imageView.setImageResource(currentWord.getImageResource());
        }
        else{
            imageView.setVisibility(View.GONE);
        }

        LinearLayout linearLayout =listItemView.findViewById(R.id.main_linear);
        linearLayout.setBackgroundResource(colorId);
//        imageView.setVisibility(View.GONE);
        return listItemView;
    }



}

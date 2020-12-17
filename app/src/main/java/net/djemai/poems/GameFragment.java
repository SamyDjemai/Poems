package net.djemai.poems;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.djemai.poems.async.AsyncPoemFetchTask;
import net.djemai.poems.async.AsyncRandomAuthorFetchTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameFragment extends PoemFragment {
    TextView title_tv;
    TextView content_tv;
    String author;
    Button[] buttonArray;
    ArrayList<String> authorArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        title_tv = view.findViewById(R.id.game_title_textView);
        content_tv = view.findViewById(R.id.game_content_textView);
        authorArrayList = new ArrayList<>();
        buttonArray = new Button[4];
        int[] buttonIdArray = new int[]{R.id.game_authorButton1, R.id.game_authorButton2, R.id.game_authorButton3, R.id.game_authorButton4};

        for (int i = 0; i<4; i++) {
            buttonArray[i] = view.findViewById(buttonIdArray[i]);
        }

        AsyncPoemFetchTask asTask = new AsyncPoemFetchTask(this, true);
        asTask.execute();
    }

    @Override
    public void setPoem(Poem poem) {
        title_tv.setText(poem.title);
        content_tv.setText(poem.content);
        author = poem.author.trim();
        authorArrayList.add(poem.author);
        AsyncRandomAuthorFetchTask asTask = new AsyncRandomAuthorFetchTask(this);
        asTask.execute();
    }

    public void setAuthors(String[] asyncAuthorArray) {
        for (int i = 0; i < asyncAuthorArray.length; i++) {
            authorArrayList.add(asyncAuthorArray[i].trim());
        }
        Collections.shuffle(authorArrayList);
        for (int i = 0; i < buttonArray.length; i++) {
            buttonArray[i].setText(authorArrayList.get(i).trim());
            buttonArray[i].setOnClickListener(v -> {
                Button button = (Button) v;
                if (button.getText().toString() == author) {
                    button.setBackgroundColor(Color.GREEN);
                } else {
                    button.setBackgroundColor(Color.RED);
                }
            });

            buttonArray[i].setEnabled(true);
        }
    }


}
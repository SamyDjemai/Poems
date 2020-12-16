package net.djemai.poems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import com.squareup.picasso.Picasso;

import net.djemai.poems.async.AsyncPoemFetchTask;

public class PoemActivity extends AppCompatActivity {

    private Fragment poemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem);

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        String author = extras.getString("author");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.poem_poemFragmentContainer, PoemFragment.newInstance(title, author))
                .commit();
    }
}
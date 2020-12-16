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

        poemFragment = getSupportFragmentManager().findFragmentById(R.id.poem_poemFragment);

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("title");
        String author = extras.getString("author");

        if (poemFragment instanceof PoemFragment) {
            PoemFragment castedPoemFragment = (PoemFragment) poemFragment;
            AsyncTask.execute(() -> {
                PoemDao poemDao = PoemRoomDatabase.getDatabase(this).poemDao();
                if (poemDao.exists(title, author)) {
                    Poem poem = poemDao.get(title, author);
                    castedPoemFragment.setTitle(poem.title);
                    castedPoemFragment.setAuthor(poem.author);
                    castedPoemFragment.setContent(poem.content);
                    castedPoemFragment.imageURL = poem.imageURL;
                    runOnUiThread(() -> {
                        Picasso.get()
                                .load(poem.imageURL)
                                .into(castedPoemFragment.getImageView());
                    });
                } else {
                    AsyncPoemFetchTask asTask = new AsyncPoemFetchTask(
                            (PoemFragment) poemFragment,
                            false);
                    asTask.execute(title, author);
                }
            });
        }
    }
}
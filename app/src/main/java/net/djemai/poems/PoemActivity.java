package net.djemai.poems;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PoemActivity extends AppCompatActivity {
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
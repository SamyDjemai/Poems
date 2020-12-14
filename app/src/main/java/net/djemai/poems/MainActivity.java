package net.djemai.poems;

import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private Fragment poemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        poemFragment = getSupportFragmentManager().findFragmentById(R.id.main_poemFragment);

        fetchRandomPoem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            fetchRandomPoem();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchRandomPoem() {
        if (poemFragment instanceof PoemFragment) {
            AsyncRandomPoemFetch asTask = new AsyncRandomPoemFetch((PoemFragment) poemFragment);
            asTask.execute();
        }
    }

}
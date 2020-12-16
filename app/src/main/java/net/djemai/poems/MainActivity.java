package net.djemai.poems;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import net.djemai.poems.async.AsyncPoemFetchTask;

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
        switch (id) {
            case R.id.action_refresh:
                fetchRandomPoem();
                return true;
            case R.id.action_search:
                Intent search = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(search);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchRandomPoem() {
        if (poemFragment instanceof PoemFragment) {
            AsyncPoemFetchTask asTask = new AsyncPoemFetchTask(
                    (PoemFragment) poemFragment,
                    true);
            asTask.execute();
        }
    }

}
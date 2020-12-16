package net.djemai.poems;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private Fragment poemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        poemFragment = getSupportFragmentManager().findFragmentById(R.id.main_poemFragment);
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
            refreshPoem();
            return true;
        }
        switch (id) {
            case R.id.action_refresh:
                refreshPoem();
                return true;
            case R.id.action_search:
                Intent search = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(search);
                return true;
            case R.id.favorites:
                Intent favorites = new Intent(getApplicationContext(), FavoritesActivity.class);
                startActivity(favorites);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshPoem() {
        PoemFragment fragment = new PoemFragment();
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.replace(R.id.main_poemFragment, fragment).commit();
    }

}
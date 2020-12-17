package net.djemai.poems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.game_gameFragmentContainer, new GameFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.game_replay) {
            GameFragment fragment = new GameFragment();
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            tr.replace(R.id.game_gameFragmentContainer, fragment).commit();
        }

        return super.onOptionsItemSelected(item);
    }
}
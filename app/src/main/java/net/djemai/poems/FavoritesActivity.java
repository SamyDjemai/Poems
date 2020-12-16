package net.djemai.poems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.favorites_recyclerView);

        PoemViewModel poemViewModel = new ViewModelProvider(this).get(PoemViewModel.class);

        PoemListAdapter adapter = new PoemListAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        poemViewModel.getAllPoems().observe(this, poems -> {
            recyclerView.setAdapter(new PoemListAdapter(new ArrayList<>(poems)));
        });
    }
}
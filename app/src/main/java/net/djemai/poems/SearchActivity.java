package net.djemai.poems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import net.djemai.poems.async.AsyncPoemSearchTask;
import net.djemai.poems.utils.KeyboardUtils;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recyclerView);
        PoemListAdapter adapter = new PoemListAdapter(new ArrayList<Poem>());
        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        this, LinearLayoutManager.VERTICAL, false
                )
        );
        recyclerView.setAdapter(adapter);

        Button searchButton = (Button) findViewById(R.id.search_button);
        RadioButton titleSearch = (RadioButton) findViewById(R.id.titleRadioButton);
        EditText queryEditText = (EditText) findViewById(R.id.search_query_editText);

        titleSearch.setChecked(true);

        searchButton.setOnClickListener(v -> {
            KeyboardUtils.hideKeyboard(this);
            AsyncPoemSearchTask asTask = new AsyncPoemSearchTask(adapter, titleSearch.isChecked());
            asTask.execute(queryEditText.getText().toString());
        });
    }

}
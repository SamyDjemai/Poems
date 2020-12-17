package net.djemai.poems;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PoemListAdapter extends RecyclerView.Adapter<PoemViewHolder> {

    private ArrayList<Poem> localDataSet;

    public PoemListAdapter(ArrayList<Poem> dataSet) {
        localDataSet = dataSet;
    }

    public void add(Poem poem) {
        localDataSet.add(poem);
    }

    public void reset() {
        localDataSet = new ArrayList<Poem>();
    }

    @NonNull
    @Override
    public PoemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poem_item, parent, false);
        return new PoemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoemViewHolder holder, int position) {
        View view = holder.itemView;
        Poem poem = localDataSet.get(position);
        holder.title_tv.setText(poem.title);
        holder.author_tv.setText(poem.author);
        view.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PoemActivity.class);
            intent.putExtra("title", poem.title);
            intent.putExtra("author", poem.author);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

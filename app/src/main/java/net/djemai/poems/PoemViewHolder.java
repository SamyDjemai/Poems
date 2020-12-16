package net.djemai.poems;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PoemViewHolder extends RecyclerView.ViewHolder {
    public TextView title_tv;
    public TextView author_tv;

    public PoemViewHolder(View itemView) {
        super(itemView);
        title_tv = (TextView) itemView.findViewById(R.id.poemItem_title_tv);
        author_tv = (TextView) itemView.findViewById(R.id.poemItem_author_tv);
    }
}

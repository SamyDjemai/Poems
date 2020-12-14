package net.djemai.poems;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PoemFragment extends Fragment {

    private TextView title_tv;
    private TextView content_tv;
    private TextView author_tv;
    ImageView imageView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.poem_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title_tv = view.findViewById(R.id.poem_title_textView);
        content_tv = view.findViewById(R.id.poem_content_textView);
        author_tv = view.findViewById(R.id.poem_author_textView);
        imageView = view.findViewById(R.id.poem_imageView);

        ToggleButton favoriteTB = view.findViewById(R.id.toggleButton);
        favoriteTB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO
            }
        });
    }

    public ImageView getImageView() { return imageView; }

    public void setTitle(String title) {
        title_tv.setText(title);
    }

    public void setContent(String content) {
        content_tv.setText(content);
    }

    public void setAuthor(String author) {
        author_tv.setText(author);
    }
}
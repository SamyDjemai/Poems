package net.djemai.poems;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PoemFragment extends Fragment {

    private TextView title_tv;
    private TextView content_tv;
    private TextView author_tv;
    private ImageView imageView;

    public String imageURL;

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
        favoriteTB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String title = title_tv.getText().toString();
            String author = author_tv.getText().toString();
            String content = content_tv.getText().toString();
            String imageURL = this.imageURL;

            buttonView.setClickable(false);
            if (isChecked) {
                AsyncTask.execute(() -> {
                    PoemDao poemDao = PoemRoomDatabase.getDatabase(getContext()).poemDao();
                    Poem poem = new Poem(title, author);
                    poem.content = content;
                    poem.imageURL = imageURL;
                    poemDao.insert(poem);
                    buttonView.setClickable(true);

                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), R.string.saved_fav, Toast.LENGTH_LONG).show();
                    });
                });
            } else {
                AsyncTask.execute(() -> {
                    PoemDao poemDao = PoemRoomDatabase.getDatabase(getContext()).poemDao();
                    poemDao.delete(new Poem(title, author));
                    buttonView.setClickable(true);

                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), R.string.removed_fav, Toast.LENGTH_LONG).show();
                    });
                });
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
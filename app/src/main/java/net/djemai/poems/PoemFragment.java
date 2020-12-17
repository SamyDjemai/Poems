package net.djemai.poems;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import net.djemai.poems.async.AsyncCheckFavoriteTask;
import net.djemai.poems.async.AsyncImageSearchTask;
import net.djemai.poems.async.AsyncPoemFetchTask;

public class PoemFragment extends Fragment {

    private TextView title_tv;
    private TextView content_tv;
    private TextView author_tv;
    private ImageView imageView;
    private ToggleButton favoriteTB;

    public String imageURL;

    public static PoemFragment newInstance(String title, String author) {
        PoemFragment fragment = new PoemFragment();
        Bundle args = new Bundle();
        args.putString("ARG_TITLE", title);
        args.putString("ARG_AUTHOR", author);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poem, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title_tv = view.findViewById(R.id.poem_title_textView);
        content_tv = view.findViewById(R.id.poem_content_textView);
        author_tv = view.findViewById(R.id.poem_author_textView);
        imageView = view.findViewById(R.id.poem_imageView);
        favoriteTB = view.findViewById(R.id.toggleButton);

        if (getArguments() != null) {
            String title = getArguments().getString("ARG_TITLE");
            String author = getArguments().getString("ARG_AUTHOR");
            AsyncTask.execute(() -> {
                PoemDao poemDao = PoemRoomDatabase.getDatabase(getContext()).poemDao();
                if (poemDao.exists(title, author)) {
                    Poem poem = poemDao.get(title, author);
                    setPoem(poem);
                } else {
                    AsyncPoemFetchTask asTask = new AsyncPoemFetchTask(
                            this,
                            false);
                    asTask.execute(title, author);
                }
            });
        } else {
            AsyncPoemFetchTask asTask = new AsyncPoemFetchTask(this, true);
            asTask.execute();
        }

        if (favoriteTB != null) {
            favoriteTB.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (buttonView.isEnabled()) {
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
                }
            });
        }


    }

    public void onFinishedLoading() {
        String title = title_tv.getText().toString();
        String author = author_tv.getText().toString();
        AsyncCheckFavoriteTask asTask = new AsyncCheckFavoriteTask(getContext(), favoriteTB);
        asTask.execute(title, author);
    }

    public void setPoem(Poem poem) {
        setTitle(poem.title);
        setAuthor(poem.author);
        setContent(poem.content);
        if (poem.imageURL != null && ! poem.imageURL.isEmpty()) {
            setImageURL(poem.imageURL);
            loadImage(poem.imageURL);
            onFinishedLoading();
        } else {
            AsyncImageSearchTask asTask = new AsyncImageSearchTask(this);
            asTask.execute(poem.title, poem.author);
        }
    }

    public void loadImage(String imageURL) {
        if (this.imageURL == null || this.imageURL.isEmpty()) {
            this.imageURL = imageURL;
        }
        new Handler(Looper.getMainLooper()).post(() -> {
            Picasso.get().load(imageURL).into(imageView);
        });
        onFinishedLoading();
    }

    public void setTitle(String title) {
        title_tv.setText(title);
    }

    public void setContent(String content) {
        content_tv.setText(content);
    }

    public void setAuthor(String author) {
        author_tv.setText(author);
    }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
}
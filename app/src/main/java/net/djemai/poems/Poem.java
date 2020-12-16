package net.djemai.poems;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"title", "author"})
public class Poem {
    public Poem(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @NonNull
    public String title;

    @NonNull
    public String author;

    public String content;

    public String imageURL;
}

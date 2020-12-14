package net.djemai.poems;

import androidx.room.Entity;

@Entity(primaryKeys = {"title", "author"})
public class Poem {
    public String title;
    public String author;
    public String content;
    public String imageURL;
}

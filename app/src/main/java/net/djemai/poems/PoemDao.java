package net.djemai.poems;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PoemDao {
    @Insert
    void insertPoem(Poem poem);

    @Delete
    void deletePoem(Poem poem);

    @Query("SELECT * FROM poem")
    Poem[] getAllPoems();

    @Query("SELECT * FROM poem WHERE title = :title AND author = :author")
    Poem getPoem(String title, String author);
}

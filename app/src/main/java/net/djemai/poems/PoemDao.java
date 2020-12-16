package net.djemai.poems;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PoemDao {
    @Insert
    void insert(Poem poem);

    @Delete
    void delete(Poem poem);

    @Query("SELECT * FROM Poem")
    LiveData<List<Poem>> getAllPoems();

    @Query("SELECT * FROM Poem WHERE title = :title AND author = :author")
    Poem get(String title, String author);

    @Query("SELECT EXISTS(SELECT * FROM Poem WHERE title = :title AND author = :author)")
    Boolean exists(String title, String author);
}

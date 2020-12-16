package net.djemai.poems;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Poem.class}, version = 1, exportSchema = false)
public abstract class PoemRoomDatabase extends RoomDatabase {
    public abstract PoemDao poemDao();

    private static volatile PoemRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PoemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PoemRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PoemRoomDatabase.class, "poem_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

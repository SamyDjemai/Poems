package net.djemai.poems;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class PoemRepository {

    private final PoemDao poemDao;
    private final LiveData<List<Poem>> allPoems;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    PoemRepository(Application application) {
        PoemRoomDatabase db = PoemRoomDatabase.getDatabase(application);
        poemDao = db.poemDao();
        allPoems = poemDao.getAllPoems();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Poem>> getAllPoems() {
        return allPoems;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Poem poem) {
        PoemRoomDatabase.databaseWriteExecutor.execute(() -> {
            poemDao.insert(poem);
        });
    }
}


package net.djemai.poems;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PoemViewModel extends AndroidViewModel {

    private PoemRepository mRepository;

    private final LiveData<List<Poem>> mAllPoems;

    public PoemViewModel (Application application) {
        super(application);
        mRepository = new PoemRepository(application);
        mAllPoems = mRepository.getAllPoems();
    }

    LiveData<List<Poem>> getAllPoems() { return mAllPoems; }

    public void insert(Poem poem) { mRepository.insert(poem); }
}

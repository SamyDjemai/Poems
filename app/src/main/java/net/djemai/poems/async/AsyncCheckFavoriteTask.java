package net.djemai.poems.async;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ToggleButton;

import net.djemai.poems.PoemDao;
import net.djemai.poems.PoemRoomDatabase;

public class AsyncCheckFavoriteTask extends AsyncTask<String, Void, Boolean> {

    private Context context;
    private ToggleButton favoriteTB;

    public AsyncCheckFavoriteTask(Context context, ToggleButton favoriteTB) {
        this.context = context;
        this.favoriteTB = favoriteTB;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        PoemDao poemDao = PoemRoomDatabase.getDatabase(context).poemDao();
        return poemDao.exists(strings[0], strings[1]);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        favoriteTB.setChecked(aBoolean);
        favoriteTB.setEnabled(true);
    }
}

package net.djemai.poems.async;

import android.os.AsyncTask;
import android.util.Log;

import net.djemai.poems.GameFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncRandomAuthorFetchTask extends AsyncTask<String, Void, JSONArray> {

    private final GameFragment gameFragment;

    public AsyncRandomAuthorFetchTask(GameFragment gameFragment) {
        this.gameFragment = gameFragment;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        String s = null;
        URL url = null;
        JSONArray json = null;
        try {
            url = new URL("https://poetrydb.org/random/3/author");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                s = readStream(in);
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            json = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONArray jsonResponse) {
        if (jsonResponse != null) {
            Log.i("SD", "Random authors downloaded! Parsing...");
            String[] authorArray = new String[3];
            try {
                for (int i = 0; i < jsonResponse.length(); i++) {
                    authorArray[i] = jsonResponse.getJSONObject(i).getString("author");
                }

                gameFragment.setAuthors(authorArray);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("SD", "Error in onPostExecute");
        }
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}

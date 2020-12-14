package net.djemai.poems;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncRandomPoemFetch extends AsyncTask<String, Void, JSONArray> {

    private PoemFragment poemFragment;

    public AsyncRandomPoemFetch(PoemFragment poemFragment) {
        this.poemFragment = poemFragment;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        String s = null;
        JSONArray json = null;
        try {
            URL url = new URL("https://poetrydb.org/random");
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
            Log.i("SD", "Poem downloaded! Parsing...");
            try {
                JSONObject poemObject = jsonResponse.getJSONObject(0);
                String title = poemObject.getString("title");
                String author = poemObject.getString("author");
                JSONArray linesArray = poemObject.getJSONArray("lines");
                String content = "";

                for (int i = 0; i < linesArray.length(); i++) {
                    content += linesArray.getString(i) + "\n";
                }

                poemFragment.setTitle(title);
                poemFragment.setContent(content);
                poemFragment.setAuthor(author);

                AsyncImageSearch asyncImageSearch = new AsyncImageSearch(poemFragment.getImageView());
                asyncImageSearch.execute(title, author);
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

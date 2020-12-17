package net.djemai.poems.async;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import net.djemai.poems.Poem;
import net.djemai.poems.PoemFragment;

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

public class AsyncPoemFetchTask extends AsyncTask<String, Void, JSONArray> {

    private final PoemFragment poemFragment;
    private final Boolean randomFetch;

    public AsyncPoemFetchTask(PoemFragment poemFragment, Boolean randomFetch) {
        this.poemFragment = poemFragment;
        this.randomFetch = randomFetch;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        String s = null;
        URL url = null;
        JSONArray json = null;
        try {
            if (randomFetch) {
                url = new URL("https://poetrydb.org/random");
            } else {
                url = new URL("https://poetrydb.org/title,author/"
                        + Uri.encode(strings[0])
                        + ";"
                        + Uri.encode(strings[1]));
            }

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

                Poem poem = new Poem(title, author);
                poem.content = content;
                poemFragment.setPoem(poem);

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

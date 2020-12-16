package net.djemai.poems.async;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import net.djemai.poems.Poem;
import net.djemai.poems.PoemListAdapter;

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

public class AsyncPoemSearchTask extends AsyncTask<String, Void, JSONArray> {
    private String queryType;
    private PoemListAdapter poemListAdapter;

    public AsyncPoemSearchTask(PoemListAdapter poemListAdapter, Boolean titleSearch) {
        this.poemListAdapter = poemListAdapter;
        if (titleSearch) {
            this.queryType = "title";
        } else {
            this.queryType = "author";
        }
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        String s = null;
        JSONArray json = null;
        try {
            URL url = new URL("https://poetrydb.org/" + queryType + "/" + Uri.encode(strings[0]) + "/author,title");
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
            Log.i("SD", "Poem list downloaded! Parsing...");
            try {
                poemListAdapter.reset();
                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject currentObject = jsonResponse.getJSONObject(i);
                    Poem poem = new Poem(currentObject.getString("title"), currentObject.getString("author"));
                    poemListAdapter.add(poem);
                }

                poemListAdapter.notifyDataSetChanged();
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

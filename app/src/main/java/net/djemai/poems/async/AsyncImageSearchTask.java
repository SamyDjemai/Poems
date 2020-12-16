package net.djemai.poems.async;

import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

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
import java.net.URLEncoder;

public class AsyncImageSearchTask extends AsyncTask<String, Void, JSONObject> {

    private PoemFragment poemFragment;

    public AsyncImageSearchTask(PoemFragment poemFragment) {
        this.poemFragment = poemFragment;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        String s = null;
        JSONObject json = null;
        try {
            URL url = new URL("https://api.qwant.com/api/search/images?count=1&q="
                    + Uri.encode(/* strings[0] + " "  + */ strings[1] + " poet")
                    + "&t=images&uiv=1");
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
            json = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (jsonObject != null) {
            Log.i("SD", "Image search results fetched! Parsing...");
            try {
                String imageURL = jsonObject
                        .getJSONObject("data")
                        .getJSONObject("result")
                        .getJSONArray("items")
                        .getJSONObject(0)
                        .getString("media");
                Log.i("SD", imageURL);
                poemFragment.imageURL = imageURL;
                Picasso.get()
                        .load(imageURL)
                        .into(poemFragment.getImageView());
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

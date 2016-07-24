package com.ar.siosi.swmaestrobackendproject.mixare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.ar.siosi.swmaestrobackendproject.mixare.reality.PhysicalPlace;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;

import com.ar.siosi.swmaestrobackendproject.R;

/**
 * Created by siosi on 2016-06-03.
 */
public class HttpPostSNS extends Activity implements OnClickListener {


    EditText etName, etMessage;
    Button btnPost;


    Person person;
    public static PhysicalPlace curlocate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodprint);

        Intent intent = getIntent();
        curlocate = (PhysicalPlace) intent.getSerializableExtra("currentGPSInfo2");

        etName = (EditText) findViewById(R.id.etName);
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnPost = (Button) findViewById(R.id.btnPost);

        // add click listener to Button "POST"
        btnPost.setOnClickListener(this);

    }



    public static String POST(String url, Person person) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();

            String userName = person.getName();
            String message = person.getMessage();
            Double curlon = curlocate.getLongitude();
            Double curlan = curlocate.getLatitude();


            // StringEntity params = new StringEntity()
            jsonObject.put("longitude", Double.toString(curlon));
            jsonObject.put("latitude", Double.toString(curlan));
            jsonObject.put("name", userName);
            jsonObject.put("message", message);

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            Log.i("5", json.toString());

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json, "utf-8");

            Log.i("6", se.toString());
            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnPost:
                if (!validate())
                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                // call AsynTask to perform network operation on separate thread
                person = new Person();
                person.setName(etName.getText().toString());
                person.setMessage(etMessage.getText().toString());
                new HttpAsyncTask().execute("http://lab.khlug.org/manapie/javap/postMessage.php");

                break;
        }

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return POST(urls[0], person);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validate() {
        if (etName.getText().toString().trim().equals(""))
            return false;
        else if (etMessage.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

}

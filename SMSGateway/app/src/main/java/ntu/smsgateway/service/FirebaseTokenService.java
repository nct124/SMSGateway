package ntu.smsgateway.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ntu.smsgateway.R;

/**
 * Created by n on 2/3/2017.
 */

public class FirebaseTokenService extends FirebaseInstanceIdService{
    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        //new Thread(new Task(refreshedToken)).start();
    }

    private class Task implements Runnable{
        private String refreshedToken;
        public Task(String refreshedToken){
            this.refreshedToken = refreshedToken;
        }
        @Override
        public void run() {
            String url_text = getResources().getString(R.string.server_url);
            URL url;
            HttpURLConnection urlConnection = null;
            String resp = null;
            try {
                url = new URL(url_text);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");

                JSONObject inputJO = new JSONObject();
                inputJO.put("token",refreshedToken);

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                String parameters = "data="+inputJO.toString();
                wr.write(parameters.getBytes());

                InputStream in = urlConnection.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);
                int data = isw.read();
                StringBuilder sb = new StringBuilder();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    sb.append(current);
                }
                resp = sb.toString();
                Log.d(TAG, "updateServer OK");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
    }
}

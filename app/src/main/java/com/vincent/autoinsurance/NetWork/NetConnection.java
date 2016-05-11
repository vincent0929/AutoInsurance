package com.vincent.autoinsurance.NetWork;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vincent.autoinsurance.Context.Config;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Vincent on 2016/4/2 0002.
 */
public class NetConnection {
    public NetConnection(final String url, final HttpMethod method, final SuccessCallback successCallback,
                         final FailCallback failCallback, final String... kvs) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                StringBuffer paramsStr = new StringBuffer();
                for (int i = 0; i < kvs.length; i += 2) {
                    paramsStr.append(kvs[i]).append("=").append(kvs[i + 1]).append("&");
                }

                URLConnection urlConnection = null;
                try {
                    switch (method) {
                        case Post:
                            urlConnection = new URL(url).openConnection();
                            urlConnection.setDoOutput(true);
                            BufferedWriter br = new BufferedWriter(
                                    new OutputStreamWriter(urlConnection.getOutputStream(), Config.CHARSET));
                            br.write(paramsStr.toString());
                            br.flush();
                            br.close();
                            break;
                        default:
                            urlConnection = new URL(url + "?" + paramsStr.toString()).openConnection();
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i(Config.NET_REQUST_URL,urlConnection.getURL().toString());
                Log.i(Config.NET_REQUST_DATA,paramsStr.toString());

                try {
                    BufferedReader br=new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream(),Config.CHARSET));
                    String line=null;
                    StringBuffer buffer=new StringBuffer();
                    while((line=br.readLine())!=null){
                        buffer.append(line);
                    }
                    Log.i(Config.RESULT_DATA,buffer.toString());

                    return buffer.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if(s!=null) {
                    if (successCallback != null) {
                        successCallback.onSuccess(s);
                    }
                }else {
                    if (failCallback != null) {
                        failCallback.onFail();
                    }
                }
                super.onPostExecute(s);
            }
        }.execute();
    }

    public interface SuccessCallback {
        public void onSuccess(String result);
    }

    public interface FailCallback {
        public void onFail();
    }
}

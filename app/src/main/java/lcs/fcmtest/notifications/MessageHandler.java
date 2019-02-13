package lcs.fcmtest.notifications;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import lcs.fcmtest.utils.Constants;


public class MessageHandler {

    public void sendMessage(Context context, String id) {


        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://fcm.googleapis.com/fcm/send";
        //String url = "https://fcm.googleapis.com/v1/projects/fcmtest-888b5/messages:send"; In case of OAUTH2.0
        JSONObject content = new JSONObject();

        try {
            content = new JSONObject(Constants.JSON_FCM);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, content, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Leandro", response.toString());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Leandro", error.networkResponse.data.toString());
                int mStatusCode = error.networkResponse.statusCode;
                try {
                    Log.d("Leandro", "[raw json]: " + (new String(error.networkResponse.data)));
                    Gson gson = new Gson();
                    String json = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                    String temp = gson.fromJson(json, String.class);
                    Log.d("Leandro", temp);
                } catch (UnsupportedEncodingException e) {

                } catch (JsonSyntaxException e) {

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<String,String>();
                headers.put("Content-Type","application/json; UTF-8");
                headers.put("Authorization","key=AAAA5HAHjLY:APA91bFlsxDDqoksqiG3tGVwYOhR16M5a0fUi25JwjPNoxRaSbTJy2XEoY61DcH7D3E_YMY8DYAwRYVTo_tC6wF5ecCcSUvUd7rADJCe8FWG-rdDbyc8m9xkq2gsQ-pn4-rGVvukaGaH");
                return headers;
            }
        };
        queue.add(request);

    }
}

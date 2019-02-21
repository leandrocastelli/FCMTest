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
import lcs.fcmtest.utils.Utils;


public class MessageHandler {
    public void sendMessage(Context context, JSONObject content) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.URL, content, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Leandro", response.toString());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Leandro", error.networkResponse.data.toString());

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
    public void sendMessage(Context context, String id, String notification_id, String senderName) {


        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://fcm.googleapis.com/fcm/send";

        JSONObject content = new JSONObject();

        try {
            String temp = String.format(Constants.JSON_DATA,notification_id,senderName,id);

            content = new JSONObject(temp);
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
    public void receiveMessage(final Context context, Map<String,String> data) {
        final String role = Utils.getRolePreference(context);
        final String sender_id = data.get(Constants.SENDER_ID);
        final String senderName = data.get(Constants.SENDER_NAME);
        final String packageName = data.get(Constants.PACKAGE_NAME);
        final String appName = data.get(Constants.APP_NAME);
        if ("parent".equals(role)) {
            NotificationHandler notificationHandler = new NotificationHandler(context);
            notificationHandler.createNotification(appName,packageName,sender_id,senderName);

        }

    }

    public void askPermissionFatherApp(Context context, String packageName, String appName) {
            String name = Utils.getNamePreference(context);
            String userName = Utils.getEmailPreference(context).split("@")[0];
            String parentId = Utils.getParentPreference(context);
            String message = String.format(Constants.JSON_DATA, userName, name, packageName, appName, parentId);
        try {
            JSONObject object = new JSONObject(message);
            sendMessage(context, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

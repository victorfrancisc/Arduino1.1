package com.example.arduino11;

import android.app.Activity;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.aware.WifiAwareManager;
import android.os.Build;
import android.os.Handler;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Formatter;

public class Rutas extends Activity {

    String movimiento;

    String datore = "";

    int tamanodejson=0;
    String datomnoviento;
    String categoria;
    String idArduinoMo;
    public String ru(String dato) {
       return "http://aplicaciones.uteq.edu.ec/ServiceSilentKiller/webresources/generic/" + dato;


       //http://aplicaciones.uteq.edu.ec/ServiceSilentKiller/
       // http://192.168.1.5:8080/ServiceSilentKiller/webresources/generic/
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String ipRed()
    {
       WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
       String url= String.valueOf(wifiManager.getConnectionInfo().getIpAddress());
        return url;
    }

    public String webserives() {


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();
        try {
            postData.put("idArduino", idArduinoMo);
          //  postData.put("bucle", tamanodejson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ru(categoria), postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray row = response.getJSONArray("datos");
                    if (row.length() > 0) {
                        for (int p = 0; p < row.length(); p++) {
                            JSONObject aux = row.getJSONObject(p);
                            movimiento = String.valueOf(aux.get("opcion1").toString());

                        }
                    }

                } catch (Exception e) {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
        return movimiento;
    }



}

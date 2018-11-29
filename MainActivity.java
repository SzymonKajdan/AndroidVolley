package com.example.szymo.androidvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText login;
    EditText haslo;
    Button button;
    String url="http://10.16.4.70:8080/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        login=findViewById(R.id.logintext);
        haslo=findViewById(R.id.haslotext);
        button=findViewById(R.id.sginupButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    InsertSv();

            }
        });


    }

    private void InsertSv() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://10.16.4.70:8080/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplication(),response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(),error+"",Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = login.getText().toString() + ":" + haslo.getText().toString();
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                String pass=haslo.getText().toString();
                String log=login.getText().toString();
               params.put("username",log);
                params.put("password",pass);
                return params;
            }

        };
        RequestQueue requestQueue =Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}

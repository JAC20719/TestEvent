package com.example.testevent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://www.ugrad.cs.jhu.edu/~jcanedy1/test_event.php";
    private ArrayList<TestEvent> test_events;

    private Button get;
    private TextView event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get = findViewById(R.id.get);
        event = findViewById(R.id.event);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTestEvents();
            }
        });

        test_events = new ArrayList<>();
    }

    private void getTestEvents() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            System.out.println(array);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                String test_event = object.getString("Test_event");
                                TestEvent te = new TestEvent(test_event);
                                test_events.add(te);
                            }
                        } catch (Exception e) {

                        }

                        event.setText(test_events.get(0).getTest_event());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("txt","Goodnight Abby");
                return params;
            }
        };
        //execute your request
        queue.add(stringRequest);
    }
}
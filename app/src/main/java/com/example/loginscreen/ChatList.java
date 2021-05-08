package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatList extends AppCompatActivity {

    RequestQueue requestQueue;
    ArrayAdapter<String> arrayAdapter;
     List<String> nameArray=null;
    public interface VolleyCallback {
        void onSuccessResponse(List result);
    }



    public void getCall(VolleyCallback callback){
        String url = "http://192.168.43.74:3000/check";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("users");
                    nameArray = new ArrayList<>();
                    for(int i =0 ; i<jsonArray.length();i++){
                        JSONObject user = jsonArray.getJSONObject(i);
                        String name= user.getString("username");
                        nameArray.add(name);

                    }
                      callback.onSuccessResponse(nameArray);
                     Log.i("GOT THE RES",jsonArray.toString());

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
//        Log.i("NameLISTTTRETURNss {} ",nameArray.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        ListView list = findViewById(R.id.userlist);
        requestQueue = Volley.newRequestQueue(this);
        /*nameArray = new ArrayList<>();
        nameArray.add("Rooban");
        nameArray.add("Vabaiv");
        nameArray.add("Ajith");*/
       /* ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,nameArray);
        list.setAdapter(arrayAdapter);*/
        getCall(new VolleyCallback(){
            @Override
            public void onSuccessResponse(List result) {
                arrayAdapter = new ArrayAdapter<String>(ChatList.this, android.R.layout.simple_list_item_1,result);
                list.setAdapter(arrayAdapter);
            }

        });


    }
}

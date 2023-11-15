package com.oulqaid.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oulqaid.exam.classes.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

        ArrayList<Service> serviceList;
        String[] services;

        private EditText lastName, firstName, dateNaissance;

        private Button btnSave;

        private int selectedServiceId = -1;
        private String url = "http://192.168.80.73:9089/api/";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add);

            String loadServicesUrl = this.url + "services";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, loadServicesUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response + "");
                            handleJsonResponseService(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("err", error + "");
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            Spinner spin = findViewById(R.id.services);
            spin.setOnItemSelectedListener(this);

            btnSave = findViewById(R.id.bnAdd);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendEmployeData();
                }
            });

        }

        private void sendEmployeData() {

            firstName = findViewById(R.id.nom);
            lastName = findViewById(R.id.prenom);
            dateNaissance = findViewById(R.id.date);

            JSONObject jsonObject = new JSONObject();
            try {
                JSONObject serviceObject = new JSONObject();
                serviceObject.put("id", selectedServiceId);

                jsonObject.put("prenom", firstName.getText().toString());
                jsonObject.put("nom", lastName.getText().toString());
                jsonObject.put("date", dateNaissance.getText().toString());
                jsonObject.put("service", serviceObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringRequest request = new StringRequest(Request.Method.POST, url + "employes", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(AddActivity.this, "Employe successfully created" +
                            "", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", error+"");
                }
            }) {
                @Override
                public byte[] getBody() {
                    return jsonObject.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }

        @Override
        public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {
            selectedServiceId = Math.toIntExact(serviceList.get(position).getId());
        }

        @Override
        public void onNothingSelected(AdapterView arg0) {
            // Auto-generated method stub
        }

        private void handleJsonResponseService(String response) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                serviceList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Service service = new Service(
                            jsonObject.getLong("id"),
                            jsonObject.getString("nom")
                    );
                    serviceList.add(service);
                }

                services = new String[serviceList.size()];

                for (int i = 0; i < serviceList.size(); i++) {
                    services[i] = serviceList.get(i).getNom();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner spinner = findViewById(R.id.services);
                spinner.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

}

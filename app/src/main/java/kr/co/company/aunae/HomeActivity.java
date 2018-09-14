package kr.co.company.aunae;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {


    ListView placeListView;
    ArrayList<String> placeImageURLList;
    ListViewAdapter_place placeListAdapter;

    private void setPlaceList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_PLACE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject message = new JSONObject(response);
                    if (!message.getBoolean("error")) {
                        JSONArray posts = message.getJSONArray("posts");

                        if (posts.length() > 0) {
                            for (int countItem = 0; countItem < posts.length(); countItem++) {
                                JSONObject object = posts.getJSONObject(countItem);

                                Log.d("DEBUG ", "object : " + object);

                                String name = object.isNull("place_name") ? "" : object.optString("place_name");

                                JSONArray image = object.isNull("image") ? new JSONArray() : new JSONArray(object.optString("image"));
                                JSONObject tempObject = image.getJSONObject(0);
                                String path = tempObject.optString("Path");
                                // 경로가 "./"으로 시작하기 때문에 . 제
                                path = path.substring(1, path.length());
                                String imageURL = Constants.URL_IMAGE + path;

                                setImage(name, imageURL);
                            }
                        }

                        setListView();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(getApplicationContext(), "서버와 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setListView() {
        placeListAdapter = new ListViewAdapter_place();
        placeListView.setAdapter(placeListAdapter);
    }

    private void setImage(String name, String URL) {
        ImageRequest imageRequest = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Bitmap resized = Bitmap.createScaledBitmap(response, 200, 200, true);

                placeListAdapter.addItem(name, resized);
                placeListAdapter.notifyDataSetChanged();
            } // TODO 이미지 스케일 적용방식 수정해주어야함
        }, 0, 0, ImageView.ScaleType.FIT_CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(getApplicationContext(), "서버와 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        Log.d("" + HomeActivity.class.getName(), "서버 연결 실패");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
//                placeImageURLList.add(R.drawable.image_load_error);
            }
        });

        imageRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(imageRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        placeListView = findViewById(R.id.placeListView);
        placeImageURLList = new ArrayList<>();

        setPlaceList();

        placeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(HomeActivity.this, PlaceActivity.class);

                intent.putExtra("place_id", position + 1);

                startActivity(intent);
            }
        });
    }

    public void homeClick(View view) {
        switch (view.getId()) {
            case R.id.home_back:
                finish();
                break;
            case R.id.mapImage:
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

    /*****************************************
     * 액션바 삭제
     * **************************************
     @Override public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.actions, menu);

     return true;
     }

     @Override public boolean onOptionsItemSelected(MenuItem item) {
     switch(item.getItemId()) {
     case R.id.action_back:
     finish();
     return true;
     default:
     return true;
     }
     }*/
}

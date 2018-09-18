package kr.co.company.aunae;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlaceActivity extends AppCompatActivity {

    ImageView imageView;
    ListView featureListView;
    ArrayAdapter featureListAdapter;
    ListViewItem_place featureCustomListView;
    ArrayList<ListViewItem_place> freatureCustomListArray;
    private int place_id;
    // place data iamge url
    private ArrayList<String> imageURL = new ArrayList<>();
    private TextView titleText, nameText, placeTextView;
    private ArrayList<String> featureListArray;
    private ListViewAdapter_feature featrueCustomListAdapter;

    private static void listViewHeightSet(BaseAdapter listAdapter, ListView listView) {
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void setPlaceData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_PLACE_DATA, response -> {
            try {
                JSONObject message = new JSONObject(response);

                if (!message.getBoolean("error")) {
                    JSONArray posts = message.getJSONArray("posts");

                    JSONObject object = posts.getJSONObject(0);

                    String name = object.isNull("name") ? "" : object.optString("name");
                    String text = object.isNull("text") ? "" : object.optString("text");
                    String audio = object.isNull("audio") ? "" : object.optString("audio");
                    JSONArray image = object.isNull("image") ? new JSONArray() : new JSONArray(object.optString("image"));

                    if (image.length() > 0) {
                        for (int j = 0; j < image.length(); j++) {
                            JSONObject tempObject = image.getJSONObject(j);

                            String path = tempObject.optString("Path");
                            // 경로가 "./"으로 시작하기 때문에 . 제거
                            path = path.substring(1);
                            imageURL.add(Constants.URL_IMAGE + path);
                        }
                    }
                    setPlaceDataView(name, text);
                    setImage();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            if (error.networkResponse == null) {
                if (error.getClass().equals(TimeoutError.class)) {
                    Toast.makeText(getApplicationContext(), "서버와 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("place_id", String.valueOf(place_id));

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setPlaceDataView(String name, String text) {
        titleText.setText(name);
        nameText.setText(name);
        placeTextView.setText(text);
    }

    private void setImage() {
        ImageRequest imageRequest = new ImageRequest(imageURL.get(0), response -> {
            Log.d("TEST", "imageURL : " + imageURL.get(0));
            imageView.setImageBitmap(response);
        }, 0, 0, ImageView.ScaleType.FIT_CENTER, null, error -> {
            if (error.networkResponse == null) {
                if (error.getClass().equals(TimeoutError.class)) {
                    Toast.makeText(getApplicationContext(), "서버와 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            imageView.setImageResource(R.drawable.image_load_error);
        });

        imageRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(imageRequest);
    }

    private void setFeatureList() {
        setFeatureView();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_FEATURE_LIST, response -> {
            try {
                JSONObject message = new JSONObject(response);
                if (!message.getBoolean("error")) {
                    JSONArray posts = message.getJSONArray("posts");

                    if (posts.length() > 0) {
                        for (int countItem = 0; countItem < posts.length(); countItem++) {
                            JSONObject object = posts.getJSONObject(countItem);

                            String name = object.isNull("feature_name") ? "" : object.optString("feature_name");
                            Log.d("NAME", name);
                            // featureListArray.add(name);
                            featrueCustomListAdapter.addItem(name);
                        }
                    }
                    //setFeatureView();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            if (error.networkResponse == null) {
                if (error.getClass().equals(TimeoutError.class)) {
                    Toast.makeText(getApplicationContext(), "서버와 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("place_id", String.valueOf(place_id));

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setFeatureView() {
        /*
        featureListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, featureListArray);

        listViewHeightSet(featureListAdapter, featureListView);

        featureListView.setAdapter(featureListAdapter);
        */
        featrueCustomListAdapter = new ListViewAdapter_feature();
        featureListView.setAdapter(featrueCustomListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        place_id = intent.getIntExtra("place_id", 0);

        // 이름
        titleText = findViewById(R.id.titleTextView);
        nameText = findViewById(R.id.nameTextView);
        // 이미지
        imageView = findViewById(R.id.imageView);
        // 설명
        placeTextView = findViewById(R.id.placeTextView);

        featureListView = findViewById(R.id.featureListView);
        featureListArray = new ArrayList<>();

        setPlaceData();
        setFeatureList();

        featureListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent ToFeatureIntent = new Intent(PlaceActivity.this, FeatureActivity.class);

            ToFeatureIntent.putExtra("place_id", place_id);
            ToFeatureIntent.putExtra("feature_id", position + 1);

            startActivity(ToFeatureIntent);
        });

    }

    public void placeClick(View view) {
        switch (view.getId()) {
            case R.id.placeBackButton:
                finish();
                break;
            default:
                break;
        }
    }

    /**********************************************
     * 액션바 삭제
     * *******************************************
     @Override public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.actions2, menu);

     return true;
     }

     @Override public boolean onOptionsItemSelected(MenuItem item) {
     switch(item.getItemId()) {
     case R.id.action_back2:
     finish();
     return true;
     default:
     return true;
     }
     }*/
    @Override
    protected void onResume() {
        super.onResume();

        ScrollView scv = findViewById(R.id.placeScrollView);
        LinearLayout linearLayout = (LinearLayout) scv.getParent();
        int x = linearLayout.getLeft();
        int y = linearLayout.getTop();
        scv.smoothScrollTo(x, y);
    }
}

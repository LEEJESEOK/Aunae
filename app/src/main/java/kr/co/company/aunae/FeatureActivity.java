package kr.co.company.aunae;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import java.util.Objects;

public class FeatureActivity extends AppCompatActivity {

    private int place_id, feature_id;

    private ArrayList<String> imageURL = new ArrayList<>();

    private ImageView imageView;
    private TextView titleText, nameText, featureTextView;


    private void setFeatureData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_FEATURE_DATA, response -> {
            try {
                JSONObject message = new JSONObject(response);

                if (!message.getBoolean("error")) {
                    JSONArray posts = message.getJSONArray("posts");

                    JSONObject object = posts.getJSONObject(0);

                    String name = object.isNull("name") ? "" : object.optString("name");
                    String text = object.isNull("text") ? "" : object.optString("text");
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
                params.put("feature_id", String.valueOf(feature_id));

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void setPlaceDataView(String name, String text) {
        titleText.setText(name);
        nameText.setText(name);
        featureTextView.setText(text);
    }

    private void setImage() {
        ImageRequest imageRequest = new ImageRequest(imageURL.get(0), response -> {
            Log.d("TEST", "imageURL" + imageURL.get(0));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();

        Intent intent = getIntent();

        place_id = intent.getIntExtra("place_id", 0);
        feature_id = intent.getIntExtra("feature_id", 0);

        // 이름
        titleText = findViewById(R.id.titleTextView);
        nameText = findViewById(R.id.nameTextView);
        // 이미지
        imageView = findViewById(R.id.imageView);
        // 설명
        featureTextView = findViewById(R.id.featureTextView);

        Log.d("Feature Activity", "place_id : " + place_id + " / feature_id : " + feature_id);
        setFeatureData();
    }

    public void featureClick(View view) {
        switch (view.getId()) {
            case R.id.featureBackButton:
                finish();
                break;
            default:
                break;
        }
    }
}

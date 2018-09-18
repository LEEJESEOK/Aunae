package kr.co.company.aunae;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ARHomeActivity extends AppCompatActivity {

    private ConstraintLayout background;
    private ImageView itemImage;
    private ImageView leftImage, rightImage;
    private TextView nameText;

    private ArrayList<String> itemList = new ArrayList<>(Arrays.asList("벽관", "목판", "매봉교회", "유관순열사 생가", "독립운동 기념비"));
    private ArrayList<Integer> itemImageIDList = new ArrayList<>(Arrays.asList(R.drawable.arhome_box, R.drawable.arhome_plane, R.drawable.arhome_house, R.drawable.arhome_church, R.drawable.arhome_stone));

    private int itemLength;
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arhome);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        background = findViewById(R.id.background);
        itemImage = findViewById(R.id.itemImage);
        leftImage = findViewById(R.id.leftImage);
        rightImage = findViewById(R.id.rightImage);
        nameText = findViewById(R.id.nameText);


        itemImage.setImageResource(itemImageIDList.get(index));
        nameText.setText(itemList.get(index));

        itemLength = itemList.size();


        background.setOnClickListener(v -> {
            Intent intent = new Intent(ARHomeActivity.this, ARPlayActivity.class);

            intent.putExtra("index", index);

            startActivity(intent);
        });

        leftImage.setOnClickListener(v -> {
            index = (index - 1 + itemLength) % itemLength;
            Log.d("left", "index : " + index);

            itemImage.setImageResource(itemImageIDList.get(index));
            nameText.setText(itemList.get(index));
        });

        rightImage.setOnClickListener(v -> {
            index = (index + 1) % itemLength;
            Log.d("right", "index : " + index);

            itemImage.setImageResource(itemImageIDList.get(index));
            nameText.setText(itemList.get(index));
        });
    }

    public void arClick(View view) {
       if (view.getId() == R.id.arhome_back) {
            finish();
        }
    }
}
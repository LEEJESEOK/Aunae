package kr.co.company.aunae;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout main_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences isDone = getSharedPreferences("isDone", MODE_PRIVATE);
        boolean answered = isDone.getBoolean("Answer", false);
        if(answered == false)
            question();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    public void onClick(View view) {
        if(view.getId() == R.id.main_button1) {
            Intent intent = new Intent(this, HomeActivity.class);
            //Intent intent = new Intent(this, PlaceActivity.class);
            startActivity(intent);
        } else if(view.getId() == R.id.main_button2) {
            Intent intent = new Intent(this, ARHomeActivity.class);
            startActivity(intent);
        }
    }

    public void question() {
        afterQustion();

        final CharSequence[] itemsAge = {"9세 이하", "10대", "20대", "30대", "40대", "50대", "60대", "70세 이상"};
        final CharSequence[] itemsGender = {"남성", "여성"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("나이대를 선택해주세요")
                .setItems(itemsAge, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // TODO: 나이 선택시 이벤트 처리

                        builder.setTitle("성별을 선택해주세요")
                                .setItems(itemsGender, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO: 성별 선택시 이벤트 처리
                                    }
                                });

                        AlertDialog dialogGender = builder.create();
                        dialogGender.show();
                    }
                });

        AlertDialog dialogAge = builder.create();
        dialogAge.show();
    }

    public void afterQustion() {
        SharedPreferences isDone = getSharedPreferences("isDone", MODE_PRIVATE);
        SharedPreferences.Editor editor = isDone.edit();

        boolean answered = true;
        editor.putBoolean("Answer", answered);
        editor.commit();
    }
}

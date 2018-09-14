package kr.co.company.aunae;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ARHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arhome);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    public void arClick(View view) {
        if(view.getId() == R.id.arhome_button1) {
            Intent intent = new Intent(this, ARPlayActivity.class);
            intent.putExtra("MODEL", "box");
            startActivity(intent);
        } else if(view.getId() == R.id.arhome_button2) {
            Intent intent = new Intent(this, ARPlayActivity.class);
            intent.putExtra("MODEL", "plane");
            startActivity(intent);
        } else if(view.getId() == R.id.arhome_button3) {
            Intent intent = new Intent(this, ARPlayActivity.class);
            intent.putExtra("MODEL", "mabong");
            startActivity(intent);
        } else if(view.getId() == R.id.arhome_button4) {
            Intent intent = new Intent(this, ARPlayActivity.class);
            intent.putExtra("MODEL", "house");
            startActivity(intent);
        } else if(view.getId() == R.id.arhome_button5) {
            Intent intent = new Intent(this, ARPlayActivity.class);
            intent.putExtra("MODEL", "stone");
            startActivity(intent);
        } else if(view.getId() == R.id.arhome_back) {
            finish();
        }
    }
}
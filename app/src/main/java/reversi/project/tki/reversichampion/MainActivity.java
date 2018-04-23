package reversi.project.tki.reversichampion;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import reversi.project.tki.reversichampion.databinding.ActivityMainBinding;
import reversi.project.tki.reversichampion.board.BoardActivity;
import reversi.project.tki.reversichampion.frag.FieldActivity;
import reversi.project.tki.reversichampion.ground.GroundActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        b.setActivity(this);


    }

    public void onClickBtn1(View view) {
        startActivity(new Intent(MainActivity.this, GroundActivity.class));
    }

    public void onClickBtn2(View view) {
        startActivity(new Intent(MainActivity.this, BoardActivity.class));
    }
    public void onClickBtn3(View view) {
        startActivity(new Intent(MainActivity.this, FieldActivity.class));
    }
}

package reversi.project.tki.reversichampion.board;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import reversi.project.tki.reversichampion.R;
import reversi.project.tki.reversichampion.databinding.ActivityBoardBinding;
import reversi.project.tki.reversichampion.util.CustomAnimationDrawable;

public class BoardActivity extends AppCompatActivity {

    private ActivityBoardBinding b;

    private int abc = R.id.iv_aa01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_board);
        b.setActivity(this);


    }


    public void onClickIV(final View v) {



        calculate();

    }

    private void  calculate() {

        showAnim( b.ivAa01);

    }
    private void showAnim(ImageView iv) {
        iv.setBackground(cad);
        cad.setOneShot(true);
        cad.start();

    }

    CustomAnimationDrawable cad = new CustomAnimationDrawable((AnimationDrawable) getResources().getDrawable(R.drawable.frame_loading)) {
        @Override
        public void onAnimationFinish() {
            Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onAnimationStart() {
//                v.setClickable(false);
        }
    };




}

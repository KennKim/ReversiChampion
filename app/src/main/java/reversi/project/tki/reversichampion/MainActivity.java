package reversi.project.tki.reversichampion;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import reversi.project.tki.reversichampion.board.BoardActivity;
import reversi.project.tki.reversichampion.databinding.ActivityMainBinding;
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


    public void onClickBtn4(View view) {


        HashSet<Integer> ssibal = new HashSet<>();
        ssibal.add(18);
        ssibal.add(28);
        ssibal.add(38);
        ssibal.add(48);
        ssibal.add(58);
        ssibal.add(68);
        Iterator<Integer> iterator = ssibal.iterator();

        StringBuilder gae = new StringBuilder();
        while (iterator.hasNext()) {

            switch (iterator.next()) {
                case 18:
                    break;
                case 28:
                    break;
                case 38:
                    gae.append(" ");
                    gae.append(iterator.next());
                    break;
                case 48:
                    break;
                case 58:
                    gae.append(" ");
                    gae.append(iterator.next());
                    break;

            }
            gae.append(" ");
            gae.append(iterator.next());

        }


        b.tv1.setText(gae);


        ArrayList<Integer> list123 = new ArrayList<>();
        list123.add(3);
        list123.add(34);
        list123.add(5);
        list123.add(6);

        for (int step = 0; step < 8; step++) {
            String assdfdf = "";
            for (int i : list123) {
                String asdf = "";
                int a = 23;
                for (int b : list123) {
                    if (i == b) {
                        int w2a = 9;
                        break;
                    }
                }
            }
            int a = 9;
            int b = a;
        }
        int sdfa = 9;


    }
}

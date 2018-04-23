package reversi.project.tki.reversichampion.frag;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import reversi.project.tki.reversichampion.R;
import reversi.project.tki.reversichampion.databinding.ActivityFieldBinding;

public class FieldActivity extends AppCompatActivity {

    private ActivityFieldBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = DataBindingUtil.setContentView(this, R.layout.activity_field);
        b.setActivity(this);


        Fragment fragment = FieldFragment.newInstance(FieldActivity.this);
        getFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment, "frag")
                .addToBackStack(null)
                .commit();





    }
}

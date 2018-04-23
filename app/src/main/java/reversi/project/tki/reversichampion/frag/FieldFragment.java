package reversi.project.tki.reversichampion.frag;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import reversi.project.tki.reversichampion.R;
import reversi.project.tki.reversichampion.databinding.FragmentFieldBinding;
import reversi.project.tki.reversichampion.databinding.ViewStoneBinding;

public class FieldFragment extends Fragment {

    private FragmentFieldBinding b;
    private ViewStoneBinding bStone;
    private Context mContext;


    public static FieldFragment newInstance(Context context) {
        FieldFragment fragment = new FieldFragment();
        fragment.mContext = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_field, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        b = DataBindingUtil.bind(getView());
        b.setFragment(this);


      /*  //Creating LinearLayout.
        LinearLayout linearlayout = new LinearLayout(mContext);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearlayout.setLayoutParams(para);

        LinearLayout.LayoutParams para2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Creating textview .
        TextView SampleTextView1 = new TextView(mContext);
        TextView SampleTextView2 = new TextView(mContext);

        //Adding text to TextView.
        SampleTextView1.setText("First TextView Text");
        SampleTextView2.setText("Second TextView Text");

        //Setting TextView text Size
        SampleTextView1.setTextSize(25);
        SampleTextView2.setTextSize(25);

        SampleTextView1.setLayoutParams(para2);
        SampleTextView2.setLayoutParams(para2);

        //Adding textview to linear layout using Add View function.
        linearlayout.addView(SampleTextView1);
        linearlayout.addView(SampleTextView2);
*/


        for (int i = 0; i < 8; i++) {
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout = new LinearLayout(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            linearLayout.setLayoutParams(params);
            linearLayout.setBackgroundColor(getResources().getColor(R.color.sky_blue));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            b.linearVer.addView(linearLayout);

            for (int j = 0; j < 8; j++) {
                LinearLayout v = (LinearLayout) b.linearVer.getChildAt(i);
                LayoutInflater inflater2 = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                bStone = ViewStoneBinding.inflate(inflater2, v, false);
                v.addView(bStone.getRoot());

                bStone.setFragment(this);
                bStone.tv.setText(i+":"+j);

//                v.addView(con);
            }


        }



    }

    private void changeStone(ImageView v) {

        v.setImageDrawable(getResources().getDrawable(R.drawable.frame_black));
        AnimationDrawable ad = (AnimationDrawable) v.getDrawable();
        ad.stop();
        Log.i("ttttt", "changeStone clicked");
    }


    public void onClickStone(View v) {
        changeStone((ImageView) v);
    }


}

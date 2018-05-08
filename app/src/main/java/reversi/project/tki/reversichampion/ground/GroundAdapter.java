package reversi.project.tki.reversichampion.ground;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import reversi.project.tki.reversichampion.R;
import reversi.project.tki.reversichampion.databinding.ViewHolderGroundBinding;
import reversi.project.tki.reversichampion.model.Stone;

/**
 * Created by Deviation on 2018-04-08.
 */

public class GroundAdapter extends RecyclerView.Adapter<GroundAdapter.GroundViewHolder> {

    interface HitListener {
        void onHit(Stone stone);
        void onFinished();
    }

    private HitListener mListener;
    private Context mContext;

//    private CustomAnimationDrawable cad, cad2;

    GroundAdapter(Context mContext, HitListener mListener) {
        this.mContext = mContext;
        this.mListener = mListener;

        /*this.cad = new CustomAnimationDrawable((AnimationDrawable) mContext.getResources().getDrawable(R.drawable.frame_black)) {
            @Override
            public void onAnimationFinish() {
            }

            @Override
            public void onAnimationStart() {
            }
        };
        this.cad2 = new CustomAnimationDrawable((AnimationDrawable) mContext.getResources().getDrawable(R.drawable.frame_white)) {
            @Override
            public void onAnimationFinish() {
            }

            @Override
            public void onAnimationStart() {
            }
        };*/

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public GroundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderGroundBinding binding = ViewHolderGroundBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GroundViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(GroundViewHolder h, int p) {

        Stone stone = GroundActivity.items.get(p);


        if (stone.hit) {
            stone.hit = false;
            h.showReverse(stone);

        } else {

            h.bind(stone);
        }


    }


    @Override
    public int getItemCount() {
        return GroundActivity.items.size();
    }


    public class GroundViewHolder extends RecyclerView.ViewHolder {

        ViewHolderGroundBinding b;
        private Stone stone;

        public GroundViewHolder(View itemView) {
            super(itemView);
            b = DataBindingUtil.bind(itemView);
            b.setHolder(this);

            b.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (GroundActivity.myStone == Stone.BL) {// clicked된 번호 MyStone으로 표시.
                        b.iv.setImageDrawable(mContext.getDrawable(R.drawable.frame_black));
                    } else {
                        b.iv.setImageDrawable(mContext.getDrawable(R.drawable.frame_white));
                    }
                    mListener.onHit(stone);

                }
            });
            b.iv.setClickable(false);
        }

        private void bind(Stone stone) {
            this.stone = stone;
            b.setStone(stone);
            initStone(stone);

        }

        private void initStone(Stone stone) {
            switch (stone.stone) {
                case Stone.BLANK:
                    b.iv.setImageDrawable(null);
                    return;
                case Stone.BL:
                    b.iv.setImageDrawable(mContext.getDrawable(R.drawable.frame_black));
                    return;
                case Stone.WH:
                    b.iv.setImageDrawable(mContext.getDrawable(R.drawable.frame_white));
                    return;
                case Stone.ENABLE:
                    b.iv.setImageDrawable(mContext.getDrawable(R.drawable.shape_dot));
                    b.iv.setClickable(true);
            }
        }


        private void showReverse(Stone stone) {

            initStone(stone);
            AnimationDrawable ad = (AnimationDrawable) b.iv.getDrawable();
            ad.start();


            if (GroundActivity.lastNo == stone.position) {
                mListener.onFinished();
            }


//            GroundActivity.items.get(stone.position).stone = GroundActivity.myStone;

        }


    }


}

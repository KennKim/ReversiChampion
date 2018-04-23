package reversi.project.tki.reversichampion.ground;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;

import reversi.project.tki.reversichampion.R;
import reversi.project.tki.reversichampion.databinding.ActivityGroundBinding;
import reversi.project.tki.reversichampion.model.Side;
import reversi.project.tki.reversichampion.model.Stone;

public class GroundActivity extends AppCompatActivity {

    private ActivityGroundBinding b;
    private GroundAdapter mAdapter;
    public static ArrayList<Stone> items = new ArrayList<>();
    public static Byte myStone;
    public static Byte vsStone;

    private StoneAsync stoneAsync;
    public static  ArrayList<Integer> itemsP = new ArrayList<>(); // 뒤집어질 번호.
    private HashSet<Integer> itemsB = new HashSet<>(); // Button 번호.

    private static int DELAY_TIME = 100;
    private static boolean animating;


    @Override
    protected void onDestroy() {
        items.clear();
        itemsP.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = DataBindingUtil.setContentView(this, R.layout.activity_ground);
        b.setActivity(this);

        mAdapter = new GroundAdapter(this, new GroundAdapter.HitListener() {
            @Override
            public void onHit(Stone stone) {
                items.get(stone.position).stone = GroundActivity.myStone;  // clicked된 번호 items의 stone값 변경.

                sortStone(stone);

                stoneAsync = new StoneAsync(GroundActivity.this);
                stoneAsync.execute(100);
            }

            @Override
            public void onFinished() {

                animating = false;
            }
        });

//        mAdapter.setHasStableIds(true);
//        b.rv.setLayoutManager(new GridLayoutManager(this, 8));
        b.rv.setAdapter(mAdapter);


        init();

    }

    private void init() {

        myStone = Stone.WH;
        vsStone = Stone.BL;

        setBoard();
        InitAsync initAsync = new InitAsync(GroundActivity.this);
        initAsync.execute(10);
    }

    /*     * initialize     */
    private void setBoard() {

        Log.i("tttt", " : InitThread sta" + System.currentTimeMillis());
        for (Byte i = 0; i < 64; i++) {

            Stone stone = new Stone();
            stone.position = i;
            stone.sideItem.add(new Side(i, (byte) (i - 9)));
            stone.sideItem.add(new Side(i, (byte) (i - 8)));
            stone.sideItem.add(new Side(i, (byte) (i - 7)));
            stone.sideItem.add(new Side(i, (byte) (i - 1)));
            stone.sideItem.add(new Side(i, (byte) (i + 1)));
            stone.sideItem.add(new Side(i, (byte) (i + 7)));
            stone.sideItem.add(new Side(i, (byte) (i + 8)));
            stone.sideItem.add(new Side(i, (byte) (i + 9)));

                /* Side의 테이블 밖 영역 null처리*/
            if (i % 8 == 0 || i < 8) {
                stone.sideItem.get(0).sideNo = null;
            }
            if (i < 8) {
                stone.sideItem.get(1).sideNo = null;
            }
            if ((i + 1) % 8 == 0 || i < 8) {
                stone.sideItem.get(2).sideNo = null;
            }
            if (i % 8 == 0) {
                stone.sideItem.get(3).sideNo = null;
            }
            if ((i + 1) % 8 == 0) {
                stone.sideItem.get(4).sideNo = null;
            }
            if (i % 8 == 0 || i > 55) {
                stone.sideItem.get(5).sideNo = null;
            }
            if (i > 55) {
                stone.sideItem.get(6).sideNo = null;
            }
            if ((i + 1) % 8 == 0 || i > 55) {
                stone.sideItem.get(7).sideNo = null;
            }

            items.add(stone);

        }

        Log.i("tttt", " : InitThread end" + System.currentTimeMillis());

          /*게임 시작 세팅*/
        items.get(27).stone = Stone.WH;
        items.get(36).stone = Stone.WH;
        items.get(28).stone = Stone.BL;
        items.get(35).stone = Stone.BL;

    }

    private synchronized void setSide() {
        Log.i("tttt", "setSide sta : " + System.currentTimeMillis());

        for (Stone s : items) {     /* Button 초기화 */
            if (s.stone.equals(Stone.ENABLE)) {
                s.stone = Stone.BLANK;
            }
        }

        for (final Stone s : items) {/* 각 SIDE의 놓여진 stone상태 세팅*/

            for (Side side : s.sideItem) {
                if (side.sideNo != null) {
                    side.stone = items.get(side.sideNo).stone;
                }
            }
        }
        Log.i("tttt", "setSide end : " + System.currentTimeMillis());
    }

    private synchronized void setButton() {

        Log.i("tttt", "setButton sta : " + System.currentTimeMillis());


        for (final Stone s : items) {

            if (s.stone.equals(Stone.BLANK)) {   /* 현재 돌 상태가 null 경우.*/

                int j = 0;
                for (Side side : s.sideItem) {

                    if (side.sideNo != null) {
                        if (checkSideVs(side)) { /*현재방향의 그 다음 돌 = 상대방돌.*/

                            Side nextSide = getNextSide(side.sideNo, j);
                            side.itemsTobeChangedNo.add(side.sideNo); /*현재방향의 돌 저장.*/

                            if (checkcheck(side, nextSide, s)) {
                                Side nextSide2 = getNextSide(nextSide.sideNo, j);
                                if (checkcheck(side, nextSide2, s)) {
                                    Side nextSide3 = getNextSide(nextSide2.sideNo, j);
                                    if (checkcheck(side, nextSide3, s)) {
                                        Side nextSide4 = getNextSide(nextSide3.sideNo, j);
                                        if (checkcheck(side, nextSide4, s)) {
                                            Side nextSide5 = getNextSide(nextSide4.sideNo, j);
                                            if (checkcheck(side, nextSide5, s)) {
                                                Side nextSide6 = getNextSide(nextSide5.sideNo, j);
                                                checkcheck(side, getNextSide(nextSide6.sideNo, j), s);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    j++;
                }
            }

        }
        Log.i("tttt", "setButton end : " + System.currentTimeMillis());
    }

    private boolean checkcheck(Side side, Side nextSide, Stone s) {
        if (checkSideVs(nextSide)) {
            side.itemsTobeChangedNo.add(nextSide.mainNo);
            return true;
        } else if (checkSideMy(nextSide)) {
            s.stone = Stone.ENABLE; /* Button으로 표시 */
            itemsB.add(s.position); /* itemsB에 추가 */
        } else {
            side.itemsTobeChangedNo.clear();
        }
        return false;

    }

    private boolean checkSideVs(Side side) {
        return side.stone.equals(vsStone);
    }

    private boolean checkSideMy(Side side) {
        return side.stone.equals(myStone);
    }

    private Side getNextSide(int no, int direction) {
        return items.get(no).sideItem.get(direction);
    }

    /*     * InitAsync Asynctask     */
    public static class InitAsync extends AsyncTask<Integer, Integer, Integer> {

        private WeakReference<GroundActivity> mActivity;

        private InitAsync(GroundActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            GroundActivity activity = mActivity.get();
//            activity.mAdapter.notifyDataSetChanged();

            ButtonAsync buttonAsync = new ButtonAsync(activity);
            buttonAsync.execute(10);

        }

        @Override
        protected Integer doInBackground(Integer... integers) {

            GroundActivity activity = mActivity.get();

            activity.setMyStone();
            activity.setSide();
            activity.setButton();

            return null;
        }
    }

    /*     * StoneAsync Asynctask     */
    public static class StoneAsync extends AsyncTask<Integer, Integer, Integer> {

        private WeakReference<GroundActivity> mActivity;

        private StoneAsync(GroundActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            GroundActivity activity = mActivity.get();


            InitAsync initAsync = new InitAsync(activity);
            initAsync.execute(10);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            animating = true;

            GroundActivity activity = mActivity.get();
            if (activity == null || activity.isFinishing()) return;

            int no = activity.itemsP.get(values[0]);
            activity.mAdapter.notifyItemChanged(no);


        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            GroundActivity activity = mActivity.get();
            if (activity == null || activity.isFinishing()) {
                return null;
            }

            int i = 0;
            while (activity.itemsP.size() > i) {

                publishProgress(i);

                /*try {
                    Thread.sleep(DELAY_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                i++;

            }
            return null;
        }

    }

    /*     * ButtonAsync Asynctask     */
    static class ButtonAsync extends AsyncTask<Integer, Integer, Integer> {
        private WeakReference<GroundActivity> mActivity;

        public ButtonAsync(GroundActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            GroundActivity activity = mActivity.get();
            int i = values[0];
            activity.mAdapter.notifyItemChanged(i);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {

            GroundActivity activity = mActivity.get();

            for (int i : activity.itemsB) {
                publishProgress(i);
            }

            return null;
        }
    }

    public synchronized void setMyStone() {
        if (myStone.equals(Stone.BL)) {
            myStone = Stone.WH;
            vsStone = Stone.BL;
        } else {
            myStone = Stone.BL;
            vsStone = Stone.WH;
        }

    }

    private synchronized void sortStone(Stone stone) {

        itemsP.clear();

        for (Side s : stone.sideItem) {

            for (int no : s.itemsTobeChangedNo) {
                itemsP.add(no);

                Stone stone2 = items.get(no);
                stone2.hit = true;
//                stone2.stone = myStone;
                items.set(no, stone2);

            }
        }
    }

    private void getAllSideNo(Stone stone) {


    }


















/*
    @BindingAdapter({"android:src_stone"})
    public static void src_stone(SquareImageView iv, Byte stone) {

        if (stone.equals(Stone.BL)) {
            iv.setImageDrawable(iv.getContext().getResources().getDrawable(R.drawable.stone_black));
        } else if (stone.equals(Stone.WH)) {
            iv.setImageDrawable(iv.getContext().getResources().getDrawable(R.drawable.stone_white));
        }

    }*/


}



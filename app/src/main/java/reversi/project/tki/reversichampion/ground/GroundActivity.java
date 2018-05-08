package reversi.project.tki.reversichampion.ground;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import reversi.project.tki.reversichampion.R;
import reversi.project.tki.reversichampion.databinding.ActivityGroundBinding;
import reversi.project.tki.reversichampion.model.Around;
import reversi.project.tki.reversichampion.model.Side;
import reversi.project.tki.reversichampion.model.Stone;

public class GroundActivity extends AppCompatActivity {

    private ActivityGroundBinding b;
    private GroundAdapter mAdapter;
    public static ArrayList<Stone> items;
    public static Byte myStone;
    public static Byte vsStone;

    private StoneAsync stoneAsync;
    private ButtonAsync buttonAsync;
    public static HashSet<Integer> itemsP; // 뒤집어질 번호.
    public static ArrayList<Around> itemsR; // 뒤집어질 aroundItems 번호.
    private HashSet<Integer> itemsB; // Button 번호.

    private static int DELAY_TIME = 100;
    private static boolean lastItem;

    public static int lastNo;


    @Override
    protected void onDestroy() {
        items.clear();
        itemsP.clear();
        itemsR.clear();
        itemsB.clear();
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

                setRound(stone);
                setItemsP(stone);

                stoneAsync = new StoneAsync(GroundActivity.this);
                stoneAsync.execute(100);


            }

            @Override
            public void onFinished() {

                for (int p : itemsP) {// 뒤집어진 번호 items의 stone값 변경.
                    items.get(p).stone = GroundActivity.myStone;
                }

                InitAsync initAsync = new InitAsync(GroundActivity.this);
                initAsync.execute(10);

            }
        });

//        mAdapter.setHasStableIds(true);
//        b.rv.setLayoutManager(new GridLayoutManager(this, 8));
        b.rv.setAdapter(mAdapter);


        init();

    }

    private void init() {

        items = new ArrayList<>();
        itemsP = new HashSet<>();
        itemsR = new ArrayList<>();
        itemsB = new HashSet<>();

        myStone = Stone.WH;
        vsStone = Stone.BL;

        setBoard();
        InitAsync initAsync = new InitAsync(GroundActivity.this);
        initAsync.execute(10);
    }

    /*     * initialize     */
    private void setBoard() {

        Log.i("tttt", " : InitThread sta" + System.currentTimeMillis());
        for (Byte no = 0; no < 64; no++) {

            Stone stone = new Stone();
            stone.position = no;
            stone.sideItem.add(new Side(no, (byte) (no - 9)));
            stone.sideItem.add(new Side(no, (byte) (no - 8)));
            stone.sideItem.add(new Side(no, (byte) (no - 7)));
            stone.sideItem.add(new Side(no, (byte) (no - 1)));
            stone.sideItem.add(new Side(no, (byte) (no + 1)));
            stone.sideItem.add(new Side(no, (byte) (no + 7)));
            stone.sideItem.add(new Side(no, (byte) (no + 8)));
            stone.sideItem.add(new Side(no, (byte) (no + 9)));

                /* Side의 테이블 밖 영역 null처리*/
            if (no % 8 == 0 || no < 8) {
                stone.sideItem.get(0).sideNo = null;
            }
            if (no < 8) {
                stone.sideItem.get(1).sideNo = null;
            }
            if ((no + 1) % 8 == 0 || no < 8) {
                stone.sideItem.get(2).sideNo = null;
            }
            if (no % 8 == 0) {
                stone.sideItem.get(3).sideNo = null;
            }
            if ((no + 1) % 8 == 0) {
                stone.sideItem.get(4).sideNo = null;
            }
            if (no % 8 == 0 || no > 55) {
                stone.sideItem.get(5).sideNo = null;
            }
            if (no > 55) {
                stone.sideItem.get(6).sideNo = null;
            }
            if ((no + 1) % 8 == 0 || no > 55) {
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

    private void resetButton() {

        if (itemsB != null && !itemsB.isEmpty()) {
            List<Integer> list = new ArrayList<>(itemsB);

            for (int i : list) {
                Stone stone = items.get(i);

                switch (stone.stone) {
                    case Stone.ENABLE:
                        stone.stone = Stone.BLANK;
                        break;
                    case Stone.BL:
                    case Stone.WH:
                        itemsB.remove(i);
                        break;
                }
            }
        }

/*
            if (stone.stone.equals(Stone.ENABLE)) {
                    stone.stone = Stone.BLANK;

            } else if (stone.stone.equals(Stone.BL)||stone.stone.equals(Stone.WH)) {
                    itemsB.remove(iterator.next());

            }*/

    }

    private synchronized void setSide() {
        Log.i("tttt", "setSide sta : " + System.currentTimeMillis());


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


        int i = 0;
        for (final Stone s : items) {

            if (i == 30) {
                int a = s.position;
            }

            if (s.stone.equals(Stone.BLANK)) {   /* 현재 돌 상태가 null 경우.*/

                int j = 0;
                for (Side side : s.sideItem) {

                    if (side.stone != null && checkSideVs(side)) {/*현재방향의 그 다음 돌 = 상대방돌.*/

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
                    j++;
                }
            }
            i++;
        }
        Log.i("tttt", "setButton end : " + System.currentTimeMillis());
    }

    private boolean checkcheck(Side side, Side nextSide, Stone s) {
        if (checkSideVs(nextSide)) {
            side.itemsTobeChangedNo.add(nextSide.sideNo);
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

            // view 변경
            activity.buttonAsync = new ButtonAsync(activity);
            activity.buttonAsync.execute(10);

        }

        @Override
        protected Integer doInBackground(Integer... integers) {

            GroundActivity activity = mActivity.get();

            activity.setMyStone();
            activity.resetButton();
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

            lastItem = true;


//            GroundActivity activity = mActivity.get();
//            if (activity == null || activity.isFinishing()) return;
//
//            InitAsync initAsync = new InitAsync(activity);
//            initAsync.execute(10);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


            GroundActivity activity = mActivity.get();
            if (activity == null || activity.isFinishing()) return;

            int i = values[0];
            Around round = itemsR.get(i);

            for (int j : round.items) {
                activity.mAdapter.notifyItemChanged(j);
            }


        }

        @Override
        protected Integer doInBackground(Integer... integers) {

            GroundActivity activity = mActivity.get();
            if (activity == null || activity.isFinishing()) return null;


            int i = 0;
            while (itemsR.size() > i) {

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


    private synchronized void setRound(Stone stone) {


        if (stone.aroundItems == null) {
            stone.aroundItems = new ArrayList<>();
        } else {
            stone.aroundItems.clear();
        }

        ArrayList<Boolean> switchItems = getSideSwitch();

        for (int step = 1; step < 8; step++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int d = 0; d < 8; d++) {
                if (!switchItems.get(d)) {
                    Integer no = getSideNo(d, step, stone.position); /* setRound */
                    if (no != null) {
                        list.add(no);
                        switchItems.set(d, isSideCompleted(d, no));
                    }
                }
            }

            if (!list.isEmpty()) {
                Around around = new Around(list);
                stone.aroundItems.add(around);
            }
        }

        /**
         *  todo: async 시간차 확인 할 것.
         *  aroundItems 수정되면 게임 진행되는지 확인 할 것.
         *
         *
         */


    }

    private int getRoundCount(int lastNo) {
        return 99;
    }

    /**
     * Side 정리를 위한 스위치 만들기.
     *
     * @return
     */
    private ArrayList<Boolean> getSideSwitch() {
        ArrayList<Boolean> switchItems = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            switchItems.add(false);
        }
        return switchItems;
    }

    private boolean isSideCompleted(int direction, int no) {
        switch (direction) {
            case 0:
            case 3:
            case 5:
                if (no % 8 == 0) {
                    return true;
                }
            case 1:
                if (0 <= no && no <= 7) {
                    return true;
                }
                break;
            case 2:
            case 4:
            case 7:
                if ((no + 1) % 8 == 0) {
                    return true;
                }
            case 6:
                if (56 <= no && no <= 63) {
                    return true;
                }
        }

        return false;
    }


    private Integer getSideNo(int direction, int step, int no) {

        Integer result = null;
        switch (direction) {
            case 0:
                result = no % 8 == 0 || no < 8 ? null : no - 9 * step;
                break;
            case 1:
                result = no < 8 ? null : no - 8 * step;
                break;
            case 2:
                result = (no + 1) % 8 == 0 || no < 8 ? null : no - 7 * step;
                break;
            case 3:
                result = no % 8 == 0 ? null : no - 1 * step;
                break;
            case 4:
                result = (no + 1) % 8 == 0 ? null : no + 1 * step;
                break;
            case 5:
                result = no % 8 == 0 || no > 55 ? null : no + 7 * step;
                break;
            case 6:
                result = no > 55 ? null : no + 8 * step;
                break;
            case 7:
                result = (no + 1) % 8 == 0 || no > 55 ? null : no + 9 * step;
                break;
        }

        if (result != null && !isBoundary(result)) {
            result = null;
        }

        return result;
    }

    private boolean isBoundary(int result) {
        return result >= 0 && result < 64;
    }


    private synchronized void setItemsP(Stone stone) {

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


        itemsR.clear();
        for (Around a : stone.aroundItems) {

            ArrayList<Integer> list = new ArrayList<>();

            for (Side s : stone.sideItem) {
                for (int cNo : s.itemsTobeChangedNo) {
                    for (int p : a.items) {
                        if (cNo == p) {
                            list.add(p);
                            /*if (s.itemsTobeChangedNo.size() == 1) {
                                break;
                            } else {
                                s.itemsTobeChangedNo.remove(cNo);
                            }*/
                        }
                    }
                }
            }
            if (!list.isEmpty()) {
                Around around = new Around(list);
                itemsR.add(around);
            }
        }


        Around lastR = itemsR.get(itemsR.size() - 1);
        int lastP = lastR.items.size() - 1;
        lastNo = lastR.items.get(lastP);


    }

    private void addListAndRemoveCno(ArrayList<Integer> list, int d, int cNo, int step, Stone stone) {

        for (int p : stone.aroundItems.get(step).items) {
            if (cNo == p) {
                list.add(p);
                stone.sideItem.get(d).itemsTobeChangedNo.remove(p);
            }
        }

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



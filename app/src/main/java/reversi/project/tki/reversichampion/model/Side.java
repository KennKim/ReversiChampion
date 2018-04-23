package reversi.project.tki.reversichampion.model;

import java.util.ArrayList;

/**
 * Created by Deviation on 2018-04-12.
 */

public class Side {

    public Byte sideNo; // 현재방향에 있는 items번호.
    public Byte mainNo; // 현재 Side의 parent 번호.
    public Byte stone =null;
    public ArrayList<Byte> itemsTobeChangedNo = new ArrayList<>();

    public Side() {

    }


    public Side(Byte mainNo, Byte sideNo) {
        this.mainNo = mainNo;
        this.sideNo = sideNo;
    }
}

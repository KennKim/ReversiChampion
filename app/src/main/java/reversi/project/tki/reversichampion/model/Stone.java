package reversi.project.tki.reversichampion.model;

import java.util.ArrayList;

/**
 * Created by Deviation on 2018-04-08.
 */

public class Stone {

    public static final byte INVALID = 117;

    public static final byte BLANK = 115;
    public static final byte ENABLE = 110;
    public static final byte BL = 100;
    public static final byte WH = 101;

    public boolean hit;
    public Byte stone = BLANK;
    public int position;
    public ArrayList<Side> sideItem = new ArrayList<>();

    public ArrayList<Around> aroundItems = new ArrayList<>();


    public Stone() {
    }


}

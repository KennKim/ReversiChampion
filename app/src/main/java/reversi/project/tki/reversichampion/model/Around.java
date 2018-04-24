package reversi.project.tki.reversichampion.model;

import java.util.ArrayList;

/**
 * Created by Deviation on 2018-04-24.
 */

public class Around {

    public ArrayList<Integer> items;


    public Around(ArrayList<Integer> items) {
        this.items = items;
    }

    /*
    private void setAround(int no) {

        items.add(get1(no));
    }

    private ArrayList<Integer> get1(int no) {
        ArrayList<Integer> list = new ArrayList<>();

        list.add(no - 9);
        list.add(no - 8);
        list.add(no - 7);
        list.add(no - 1);
        list.add(no + 1);
        list.add(no + 7);
        list.add(no + 8);
        list.add(no + 9);

        return list;

    }*/


}

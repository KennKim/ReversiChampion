package reversi.project.tki.reversichampion.util;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;

/**
 * Created by Deviation on 2018-04-11.
 */

public abstract class CustomAnimationDrawable extends AnimationDrawable {
    /**
     * Handles the animation callback.
     */
    Handler handler;

    public CustomAnimationDrawable(AnimationDrawable aniDrawable) {
        /* Add each frame to our animation drawable */
        for (int i = 0; i < aniDrawable.getNumberOfFrames(); i++) {
            this.addFrame(aniDrawable.getFrame(i), aniDrawable.getDuration(i));
        }
    }

    @Override
    public void start() {
        super.start();
        /*
         * Call super.start() to call the base class start animation method.
         * Then add a handler to call onAnimationFinish() when the total
         * duration for the animation has passed
         */

        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                onAnimationStart();
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onAnimationFinish();
            }
        }, getTotalDuration());

    }


    /**
     * Gets the total duration of all frames.
     *
     * @return The total duration.
     */
    public int getTotalDuration() {

        int iDuration = 0;
        for (int i = 0; i < this.getNumberOfFrames(); i++) {
            iDuration += this.getDuration(i);
        }

        iDuration -= 150;         //원래 finish시간 보다 조금더 빨리
        return iDuration;
    }

    /**
     * Called when the animation finishes.
     */
    public abstract void onAnimationFinish();

    /**
     * Called when the animation starts.
     */
    public abstract void onAnimationStart();
}

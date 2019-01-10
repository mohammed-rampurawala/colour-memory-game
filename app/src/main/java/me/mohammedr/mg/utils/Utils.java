package me.mohammedr.mg.utils;

import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import me.mohammedr.mg.R;

public class Utils {
    private static int[] drawableArray = {R.drawable.avatar_01, R.drawable.avatar_02,
            R.drawable.avatar_03, R.drawable.avatar_04, R.drawable.avatar_05};

    public static List<Integer> getResourceIds(TypedArray typedArray) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < typedArray.length(); i++) {
            list.add(typedArray.getResourceId(i, -1));
        }
        return list;
    }

    public static @DrawableRes
    int getRandomAvatar() {
        Random random = new Random();
        int drawableId = drawableArray[random.nextInt(drawableArray.length)];
        return drawableId;
    }

    public static @StringRes
    int decideMessage(String score) {
        int scr = Integer.parseInt(score);
        if (scr == 0) {
            //Thats okay
            return R.string.thats_okay;
        } else if (scr > -7 && scr < 0) {
            //You need to check your IQ
            return R.string.check_iq;
        } else if (scr > 0 && scr <= 3) {
            //You should try one more time
            return R.string.try_one_more_time;
        } else if (scr > 3 && scr <= 5) {
            //Somewhat good
            return R.string.somewhat_good;
        } else if (scr > 5 && scr <= 10) {
            //Ohhoo You are gaining IQ
            return R.string.gaining_iq;
        } else if (scr > 10 && scr <= 12) {
            //You are intelligent
            return R.string.intelligent;
        } else if (scr > 12 && scr <= 15) {
            //Bravoooooo!!!
            return R.string.bravo;
        }
        return R.string.bravo;
    }
}

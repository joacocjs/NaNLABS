package com.nanlabs.utils;

import com.nanlabs.configurations.Constants;
import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class Utils {
    public static String getBugTitle(){
        return String.join("-", "bug",
                Constants.BUGS_WORDS[new Random().nextInt(Constants.BUGS_WORDS.length)],
                Integer.toString(new Random().nextInt()& Integer.MAX_VALUE)); //only positives numbers
    }
}

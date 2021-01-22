package tld.victim.webapp_java_spring.blacklist;

import java.util.Arrays;

public class BlacklistConfDataHelper {

    /**
     * Parameters for blacklist configuration.
     */
    public final String CONFSTRING_HELP              = "help";
    public final String CONFSTRING_ALL_UPPERCASE     = "alluppercase";
    public final String CONFSTRING_ALL_LOWERCASE     = "alllowercase";
    public final String CONFSTRING_ODD_SINGLE_QUOTES = "oddsinglequotes";
    public final String CONFSTRING_KEYWORD_DETECTION = "keyworddetection";

    private static BlacklistConfDataHelper instance;

    private BlacklistConfDataHelper() {}

    public static synchronized BlacklistConfDataHelper get()
    {
        if (BlacklistConfDataHelper.instance == null)
        {
            BlacklistConfDataHelper.instance = new BlacklistConfDataHelper();
        }
        return BlacklistConfDataHelper.instance;
    }

    public boolean confContains(String[] blacklistConfig, String confstring_all_uppercase) {
        return (blacklistConfig!= null) && Arrays.stream(blacklistConfig).anyMatch(confstring_all_uppercase::equals);
    }
}

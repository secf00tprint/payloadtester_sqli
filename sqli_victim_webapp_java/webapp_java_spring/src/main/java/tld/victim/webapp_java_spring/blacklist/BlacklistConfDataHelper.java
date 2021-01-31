package tld.victim.webapp_java_spring.blacklist;

import java.util.Arrays;

public class BlacklistConfDataHelper {

    /**
     * Parameters for blacklist configuration.
     */
    public static final String CONFSTRING_HELP                          = "help";
    public static final String CONFSTRING_BLOCK_ANY_LOWERCASE           = "block_anylowercase";
    public static final String CONFSTRING_BLOCK_ANY_UPPERCASE           = "block_anyuppercase";
    public static final String CONFSTRING_BLOCK_KEYWORD_DETECTION       = "block_keywordsequences";
    public static final String CONFSTRING_BLOCK_COMMENT_DOUBLE_DASH     = "block_comment_doubledash";
    public static final String CONFSTRING_BLOCK_COMMENT_HASH            = "block_comment_hash";
    public static final String CONFSTRING_BLOCK_BAD_STRINGS_DETECTION   = "block_badstrings";
    public static final String CONFSTRING_BLOCK_CONCATENATION           = "block_concatenation";
    public static final String CONFSTRING_BLOCK_BASE64                  = "block_base64";
    public static final String CONFSTRING_BLOCK_CHAR_FUNCTION           = "block_char_function";
    public static final String CONFSTRING_STRIP_KEYWORD_DETECTION       = "strip_keywordsequences";
    public static final String CONFSTRING_ADD_ODD_SINGLE_QUOTES         = "add_oddsinglequotes";

    protected static final String[] CONCAT                              = {"CONCAT"};
    protected static final String[] CHAR_FUNCTION                       = {"CHAR"};
    protected static final String[] BASE64                              = {"FROM_BASE64"};
    protected static final String[] BADKEYWORD_SEQUENCE                 = {"UNION SELECT"};
    protected static final String[] BADSTRINGS                          = {"VERSION"};
    protected static final String[] COMMENT_DOUBLEDASH                  = {"-- "};
    protected static final String[] COMMENT_HASH                        = {"#"};

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

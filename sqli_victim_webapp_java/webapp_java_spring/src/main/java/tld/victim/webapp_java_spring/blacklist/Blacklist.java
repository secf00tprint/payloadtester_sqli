package tld.victim.webapp_java_spring.blacklist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class to perform blacklisting.
 */
public class Blacklist {

    private BlacklistConfDataHelper blacklistConfDataHelper = BlacklistConfDataHelper.get();

    /**
     * Delivered blacklist configuration to apply.
     */
    private final String[] blacklistConfig;
    /**
     * Log output of this class.
     */
    private BlacklistLog blacklistLog;
    /**
     * Is the query to block because of blacklisting?
     */
    private boolean queryIsBlocked = false;
    /**
     * Blacklisted query to deliver at the end.
     */
    private String[] blacklistedQuery;
    /**
     * Position of input part in sql query.
     */
    private int input_part_pos;

    /**
     * Position of table in sql query.
     */
    private int table_pos;

    public Blacklist(String[] blacklistConfig, String[] originalQuery, int table_pos, int input_part_pos)
    {
        this.blacklistConfig = blacklistConfig;
        this.input_part_pos = input_part_pos;
        this.table_pos = table_pos;
        blacklistLog = new BlacklistLog();
        apply(originalQuery);
    }

    /**
     * Apply the blacklist.
     * @param originalQuery original query to apply blacklist on.
     */
    public void apply(String[] originalQuery) {

        blacklistedQuery = originalQuery;
        List<Boolean> blockChecks = new ArrayList<>();
        if (blacklistConfig != null)
        {
            if (confContains(blacklistConfDataHelper.CONFSTRING_HELP))
                blacklistLog.addHelpScreen();

            else if (noMutualExclusionsInConfig())
            {
                blacklistLog.addInitialQuery(blacklistedQuery);
                if (confContains(blacklistConfDataHelper.CONFSTRING_ADD_ODD_SINGLE_QUOTES))
                    blacklistedQuery = replaceOddSingleQuote(blacklistedQuery);
                if (confContains(blacklistConfDataHelper.CONFSTRING_STRIP_KEYWORD_DETECTION))
                    blacklistedQuery = stripBadKeywordSequences(blacklistedQuery);
                if (confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_ANY_UPPERCASE))
                    blockChecks.add(isAllLowerCase(blacklistedQuery) == false);
                if (confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_ANY_LOWERCASE))
                    blockChecks.add(isAllUppercase(blacklistedQuery) == false);
                if (confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_KEYWORD_DETECTION))
                    blockChecks.add(hasBadKeywordSequences(blacklistedQuery) == true);
                if (confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_CONCATENATION))
                    blockChecks.add(hasConcatenation(blacklistedQuery) == true);
                if (confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_BASE64))
                    blockChecks.add(hasBase64(blacklistedQuery) == true);
                if (confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_CHAR_FUNCTION))
                    blockChecks.add(hasCharFunction(blacklistedQuery) == true);
                if (confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_BAD_STRINGS_DETECTION))
                    blockChecks.add(hasBadStrings(blacklistedQuery) == true);
                if (confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_COMMENT_DOUBLE_DASH))
                    blockChecks.add(hasCommentDoubleDash(blacklistedQuery) == true);
                if (confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_COMMENT_HASH))
                    blockChecks.add(hasCommentHash(blacklistedQuery) == true);

                if (blockChecks.stream().anyMatch(blockcheck -> blockcheck == true)) {
                    blacklistLog.add("BLOCKED\n");
                    queryIsBlocked = true;
                }
                else
                    blacklistLog.addUsedQuery(blacklistedQuery);
            }
            blacklistLog.addNewline();
        }
    }

    private boolean hasCharFunction(String[] blacklistedQuery) {
        blacklistLog.addStrong(blacklistConfDataHelper.CONFSTRING_BLOCK_CHAR_FUNCTION);
        return (checkForStringsinInputPart(blacklistedQuery, blacklistConfDataHelper.CHAR_FUNCTION, "Char function"));
    }

    private boolean hasBase64(String[] blacklistedQuery) {
        blacklistLog.addStrong(blacklistConfDataHelper.CONFSTRING_BLOCK_BASE64);
        return (checkForStringsinInputPart(blacklistedQuery, blacklistConfDataHelper.BASE64, "Base64"));
    }

    private boolean hasConcatenation(String[] blacklistedQuery) {
        blacklistLog.addStrong(blacklistConfDataHelper.CONFSTRING_BLOCK_CONCATENATION);
        return (checkForStringsinInputPart(blacklistedQuery, blacklistConfDataHelper.CONCAT, "Concat"));
    }

    private boolean hasCommentDoubleDash(String[] blacklistedQuery) {
        blacklistLog.addStrong(blacklistConfDataHelper.CONFSTRING_BLOCK_COMMENT_DOUBLE_DASH);
        return checkForStringsinInputPart(blacklistedQuery, blacklistConfDataHelper.COMMENT_DOUBLEDASH, "Double dash comment");
    }

    private boolean hasCommentHash(String[] blacklistedQuery) {
        blacklistLog.addStrong(blacklistConfDataHelper.CONFSTRING_BLOCK_COMMENT_HASH);
        return checkForStringsinInputPart(blacklistedQuery, blacklistConfDataHelper.COMMENT_HASH, "Hash comment");
    }

    public boolean confContains(String confstring_all_uppercase) {
        return Arrays.stream(blacklistConfig).anyMatch(confstring_all_uppercase::equals);
    }

    /**
     * Check if there are any blacklist configuration parameters which are mutual exclusive.
     * @return boolean true, if no mutual exclusion has been found.
     */
    private boolean noMutualExclusionsInConfig() {
        if (confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_ANY_LOWERCASE) &&
                confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_ANY_UPPERCASE))
        {
            blacklistLog.add("Mutual exclusion in config: " + blacklistConfDataHelper.CONFSTRING_BLOCK_ANY_UPPERCASE + " & " + blacklistConfDataHelper.CONFSTRING_BLOCK_ANY_LOWERCASE + "\n");
            return false;
        }
        else if (confContains(blacklistConfDataHelper.CONFSTRING_BLOCK_KEYWORD_DETECTION) &&
                confContains(blacklistConfDataHelper.CONFSTRING_STRIP_KEYWORD_DETECTION))
        {
            blacklistLog.add("Mutual exclusion in config: " + blacklistConfDataHelper.CONFSTRING_BLOCK_KEYWORD_DETECTION + " & " + blacklistConfDataHelper.CONFSTRING_STRIP_KEYWORD_DETECTION + "\n");
            return false;
        }
            return true;
    }

    /**
     * Replace a single ' with ' ' if odd number of single quotes are in the input part of the sql query.
     * @param originalquery query to apply on.
     * @return new blacklisted query string[] array.
     */
    public String[] replaceOddSingleQuote(String[] originalquery)
    {
        blacklistLog.addStrong(blacklistConfDataHelper.CONFSTRING_ADD_ODD_SINGLE_QUOTES);

        // make a copy of original query
        String[] newquery = getCopy(originalquery);
        long count = originalquery[input_part_pos].chars().filter(ch -> ch == '\'').count();
        if (count%2 != 0) // if odd
        {
            newquery[input_part_pos] = originalquery[input_part_pos].replaceFirst("'","' '");
            // log out changes
            blacklistLog.add("Replacing:");
            blacklistLog.add(String.join("", originalquery));
            blacklistLog.add("with:");
            blacklistLog.add(String.join("", newquery) + "\n");
        }
        else
            blacklistLog.addNothingToReplace(originalquery);

        return newquery;
    }

    /**
     * Get a copy of the query.
     * @param originalquery original query.
     * @return copy of query.
     */
    private String[] getCopy(String[] originalquery) {
        return Arrays.stream(originalquery).map(String::new).toArray(String[]::new);
    }

    /**
     * Check that all characters in the query are lower case.
     * @param originalquery original query to apply blacklist on.
     * @return true, if all characters are lower case.
     */
    public boolean isAllLowerCase(String[] originalquery) {

        blacklistLog.addStrong(blacklistConfDataHelper.CONFSTRING_BLOCK_ANY_UPPERCASE);

        AtomicInteger pos = new AtomicInteger(0);
        AtomicReference<Character> foundchar = new AtomicReference<>((char) 0);
        boolean result = Arrays.stream(originalquery).allMatch(querypart ->
        {
            for (char c : querypart.toCharArray()) {
                if (Character.isLetter(c) && (Character.isLowerCase(c) == false) && (pos.get() != table_pos)) {
                    foundchar.set(c);
                    return false;
                }
            }
            pos.getAndIncrement();
            return true;
        });
        if (result == false) {
            blacklistLog.add("Uppercase character found: " + foundchar);
            blacklistLog.addRefuseExecution();
        }
        else blacklistLog.addCheckOk(originalquery);

        return result;
    }

    /**
     * Check that all characters in the query are upper case.
     * @param originalquery original query to apply blacklist on.
     * @return true, if all characters are lower case.
     */
    public boolean isAllUppercase(String[] originalquery) {

        blacklistLog.addStrong(blacklistConfDataHelper.CONFSTRING_BLOCK_ANY_LOWERCASE);

        AtomicInteger pos = new AtomicInteger(0);
        AtomicReference<Character> foundchar = new AtomicReference<>((char) 0);
        boolean result = Arrays.stream(originalquery).allMatch(querypart ->
        {
            for (char c : querypart.toCharArray()) {
                if (Character.isLetter(c) && (Character.isUpperCase(c) == false) && (pos.get() != table_pos)) {
                    foundchar.set(c);
                    return false;
                }
            }
            pos.getAndIncrement();
            return true;
        });
        if (result == false) {
            blacklistLog.add("Lowercase character found: " + foundchar);
            blacklistLog.addRefuseExecution();
        }
        else blacklistLog.addCheckOk(originalquery);
        return result;
    }

    /**
     * Method to detect strings in query input part.
     * @param originalquery original query to analyze.
     * @param stringstocheck bad strings to use in check.
     * @param whattocheck type of check.
     * @return true if bad strings where found.
     */
    private boolean checkForStringsinInputPart(String[] originalquery, String[] stringstocheck, String whattocheck) {
        for (String stringtocheck : stringstocheck) {
            if (originalquery[input_part_pos].toUpperCase().contains(stringtocheck)) {
                blacklistLog.add(whattocheck + " found: " + stringtocheck);
                blacklistLog.addRefuseExecution();
                return true;
            }
        }
        blacklistLog.addCheckOk(originalquery);
        return false;
    }

    /**
     * Checks if query has bad strings.
     * @param blacklistedQuery
     * @return true if bad strings in query.
     */
    private boolean hasBadStrings(String[] blacklistedQuery) {
        blacklistLog.addStrong(blacklistConfDataHelper.CONFSTRING_BLOCK_BAD_STRINGS_DETECTION);
        return checkForStringsinInputPart(blacklistedQuery, blacklistConfDataHelper.BADSTRINGS, "Bad string");
    }

    /**
     * Checks if query has bad keyword sequences.
     * @param originalquery
     * @return true if bad keyword sequences in query.
     */
    public boolean hasBadKeywordSequences(String[] originalquery) {
        blacklistLog.addStrong(blacklistConfDataHelper.CONFSTRING_BLOCK_KEYWORD_DETECTION);
        return checkForStringsinInputPart(blacklistedQuery, blacklistConfDataHelper.BADKEYWORD_SEQUENCE, "Bad keyword sequence");
    }

    /**
     * Strip bad keyword sequences.
     * @param blacklistedQuery
     * @return Stripped query.
     */
    private String[] stripBadKeywordSequences(String[] blacklistedQuery) {
        blacklistLog.addStrong(blacklistConfDataHelper.CONFSTRING_STRIP_KEYWORD_DETECTION);
        // make a copy of original query
        String[] newquery = getCopy(blacklistedQuery);
        boolean stripped = false;
        // TODO add lower case
        for (String stringtocheck : blacklistConfDataHelper.BADKEYWORD_SEQUENCE) {
            if (newquery[input_part_pos].toUpperCase().contains(stringtocheck)) {
                // log out changes
                blacklistLog.add("Replacing:");
                blacklistLog.add(String.join("", newquery));
                int idx = newquery[input_part_pos].toUpperCase().indexOf(stringtocheck);
                newquery[input_part_pos] = newquery[input_part_pos].substring(0, idx) + newquery[input_part_pos].substring(idx+stringtocheck.length());
                blacklistLog.add("with:");
                blacklistLog.add(String.join("", newquery) + "\n");
                stripped = true;
            }
        }
        if (stripped == false)
            blacklistLog.addCheckOk(newquery);
        return newquery;
    }

    public boolean isQueryBlocked() {
        return queryIsBlocked;
    }

    public String getBlacklistedQuery() {
        return String.join("", blacklistedQuery);
    }

    public String getLog() {
        return blacklistLog.log;
    }
}

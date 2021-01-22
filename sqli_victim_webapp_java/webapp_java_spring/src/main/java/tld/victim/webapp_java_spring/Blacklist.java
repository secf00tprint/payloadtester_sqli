package tld.victim.webapp_java_spring;

import java.util.Arrays;

public class Blacklist {

    private String CONFSTRING_ALL_UPPERCASE     = "alluppercase";
    private String CONFSTRING_ALL_LOWERCASE     = "alllowercase";
    private String CONFSTRING_ODD_SINGLE_QUOTES = "oddsinglequotes";

    private String LOG_NOTHING_TO_REPLACE = "\n[Blacklist] Nothing to replace\n";
    private String LOG_CHECK_OK           = "\n[Blacklist] Check ok\n";

    private final String[] blacklistConfig;
    private String log = "";
    private boolean queryIsBlocked = false;
    private String[] blacklistedQuery;
    
    public Blacklist(String[] blacklistConfig, String[] originalQuery)
    {
        this.blacklistConfig = blacklistConfig;
        apply(originalQuery);
    }

    public void apply(String[] originalQuery) {

        blacklistedQuery = originalQuery;
        if (blacklistConfig != null)
        {
            if (noMutualExclusionsInConfig())
            {
                if (Arrays.stream(blacklistConfig).anyMatch(CONFSTRING_ODD_SINGLE_QUOTES::equals))
                    blacklistedQuery = replaceOddSingleQuote(blacklistedQuery);
                if (Arrays.stream(blacklistConfig).anyMatch(CONFSTRING_ALL_LOWERCASE::equals))
                    queryIsBlocked = (isAllLowerCase(blacklistedQuery) == false);
                if (Arrays.stream(blacklistConfig).anyMatch(CONFSTRING_ALL_UPPERCASE::equals))
                    queryIsBlocked = (isAllLowerCase(blacklistedQuery) == false);

                log = log + "\n";
            }
        }
    }

    private boolean noMutualExclusionsInConfig() {
        if (Arrays.stream(blacklistConfig).anyMatch(CONFSTRING_ALL_UPPERCASE::equals) &&
                Arrays.stream(blacklistConfig).anyMatch(CONFSTRING_ALL_LOWERCASE::equals))
        {
            log = log +
                    "\n[Blacklist] Mutual exclusion in config: " + CONFSTRING_ALL_LOWERCASE + " & " + CONFSTRING_ALL_UPPERCASE + "\n";
            return false;
        }
        return true;
    }

    public String[] replaceOddSingleQuote(String[] originalquery)
    {
        // make a copy of original query
        String[] newquery = Arrays.stream(originalquery).map(String::new).toArray(String[]::new);
        long count = originalquery[1].chars().filter(ch -> ch == '\'').count();
        if (count%2 != 0) // if odd
        {
            newquery[1] = originalquery[1].replaceFirst("'","' '");
            // log out changes
            log = log +
                    "\n[Blacklist] Replacing:" +
                    "\n[Blacklist] " + String.join("", originalquery) +
                    "\n[Blacklist] with:" +
                    "\n[Blacklist] " + String.join("", newquery) + "\n";
        }
        else
            log = log +
                    LOG_NOTHING_TO_REPLACE +
                    "\n[Blacklist] Using: " +
                    "\n[Blacklist] "+ String.join("", originalquery) + "\n";
        return newquery;
    }

    public boolean isAllLowerCase(String[] originalquery) {
        boolean result = Arrays.stream(originalquery).allMatch(querypart ->
        {
            for (char c : querypart.toCharArray()) {
                if (Character.isLetter(c) && (Character.isLowerCase(c) == false)) return false;
            }
            return true;
        });
        if (result == false) {
            log = log +
                    "\n[Blacklist] Non lowercase character found" +
                    "\n[Blacklist] Refusing execution of sql statement" + "\n";
        }
        else
            log = log +
                    LOG_CHECK_OK +
                    "\n[Blacklist] Using: " +
                    "\n[Blacklist] "+ String.join("", originalquery) + "\n";
        ;
        return result;
    }

    public boolean isAllUpperCase(String[] originalquery) {
        boolean result = Arrays.stream(originalquery).allMatch(querypart ->
        {
            for (char c : querypart.toCharArray()) {
                if (Character.isLetter(c) && (Character.isUpperCase(c) == false)) return false;
            }
            return true;
        });
        if (result == false) {
            log = log +
                    "\n[Blacklist] Non uppercase character found" +
                    "\n[Blacklist] Refusing execution of sql statement" + "\n";
        }
        else
            log = log +
                    LOG_CHECK_OK +
                    "\n[Blacklist] Using: " +
                    "\n[Blacklist] "+ String.join("", originalquery) + "\n";
        ;
        return result;
    }

    public boolean isQueryBlocked() {
        return queryIsBlocked;
    }

    public String getBlacklistedQuery() {
        return String.join("", blacklistedQuery);
    }

    public String getLog() {
        return log;
    }
}

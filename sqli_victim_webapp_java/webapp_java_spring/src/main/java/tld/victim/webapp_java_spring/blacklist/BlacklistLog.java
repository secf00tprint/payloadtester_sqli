package tld.victim.webapp_java_spring.blacklist;

class BlacklistLog {

    /**
     * Different reoccuring logs.
     */
    private final String LOG_NOTHING_TO_REPLACE = "Nothing to replace\n";
    private final String LOG_CHECK_OK           = "Check ok\n";
    private final String LOG_REFUSE_EXECUTION   = "Refusing execution of sql statement" + "\n";

    protected String log = "";

    private BlacklistConfDataHelper blacklistConfDataHelper = BlacklistConfDataHelper.get();

    public BlacklistLog() {
        addBanner();
    }

    protected void add(String line)
    {
        log = log + "\u001B[2m\n[Blacklist] \u001b[0m" + line;
    }

    protected void addStrong(String line)
    {
        add("\u001B[1m"+line+"\u001b[0m" + "\n");
    }

    private void addBanner() {
        log =  log +
                "\n" +
                "\u001b[34m__________                                     __________.__                 __   .__  .__          __    \n" +
                "\\______   \\___.__.___________    ______ ______ \\______   \\  | _____    ____ |  | _|  | |__| _______/  |_  \n" +
                " |    |  _<   |  |\\____ \\__  \\  /  ___//  ___/  |    |  _/  | \\__  \\ _/ ___\\|  |/ /  | |  |/  ___/\\   __\\ \n" +
                " |    |   \\\\___  ||  |_> > __ \\_\\___ \\ \\___ \\   |    |   \\  |__/ __ \\\\  \\___|    <|  |_|  |\\___ \\  |  |   \n" +
                " |______  // ____||   __(____  /____  >____  >  |______  /____(____  /\\___  >__|_ \\____/__/____  > |__|   \n" +
                "        \\/ \\/     |__|       \\/     \\/     \\/          \\/          \\/     \\/     \\/            \\/         \n" +
                "                _________________  .____    .__  ________                                                 \n" +
                "               /   _____/\\_____  \\ |    |   |__| \\______ \\   ____   _____   ____                          \n" +
                "               \\_____  \\  /  / \\  \\|    |   |  |  |    |  \\_/ __ \\ /     \\ /  _ \\                         \n" +
                "               /        \\/   \\_/.  \\    |___|  |  |    `   \\  ___/|  Y Y  (  <_> )                        \n" +
                "              /_______  /\\_____\\ \\_/_______ \\__| /_______  /\\___  >__|_|  /\\____/                         \n" +
                "                      \\/        \\__>       \\/            \\/     \\/      \\/         " +
                "\u001b[0m\n\n";
    }

    /**
     * @return The help screen as String.
     */
    protected void addHelpScreen() {
        log = log +
                "\nBlacklist demo for sql injection:" +
                "\n" +
                "\nPossible blacklist configs:" +
                "\n" +
                "\n| " + blacklistConfDataHelper.CONFSTRING_HELP                    + "\t\t\t\t| "      + "Shows this help information" +
                "\n| " + blacklistConfDataHelper.CONFSTRING_BLOCK_ANY_LOWERCASE + " *1"  + "\t\t| "     + "Blocks execution if query (except table) has upper case characters" +
                "\n| " + blacklistConfDataHelper.CONFSTRING_BLOCK_ANY_UPPERCASE + " *1"  + "\t\t| "     + "Blocks execution if query (except table) has lower case characters" +
                "\n| " + blacklistConfDataHelper.CONFSTRING_BLOCK_KEYWORD_DETECTION + " *2"   +"\t| "   + "Blocks execution if keyword sequences are used in input part (currently UNION SELECT)" +
                "\n| " + blacklistConfDataHelper.CONFSTRING_BLOCK_COMMENT_DOUBLE_DASH +   "\t| "        + "Blocks execution if double dashes are used" +
                "\n| " + blacklistConfDataHelper.CONFSTRING_BLOCK_BAD_STRINGS_DETECTION +   "\t\t| "    + "Blocks execution if specific strings are inside input part (currently VERSION)" +
                "\n| " + blacklistConfDataHelper.CONFSTRING_BLOCK_CONCATENATION         +   "\t\t| "    + "Blocks execution if input part contains concatenation CONCAT function" +
                "\n| " + blacklistConfDataHelper.CONFSTRING_BLOCK_BASE64         +   "\t\t\t| "         + "Blocks execution if input part contains base64 decrypt FROM_BASE64 function" +
                "\n| " + blacklistConfDataHelper.CONFSTRING_BLOCK_CHAR_FUNCTION +   "\t\t| "            + "Blocks execution if input part contains character value CHAR function" +
                "\n| " + blacklistConfDataHelper.CONFSTRING_STRIP_KEYWORD_DETECTION + " *2" +"\t| "     + "Checks if keyword sequences are used in input part (currently UNION SELECT)" +
                "\n| " +                                                          "\t\t\t\t  "          + "if present removes it from input " +
                "\n| " + blacklistConfDataHelper.CONFSTRING_ADD_ODD_SINGLE_QUOTES   + "\t\t| "          + "Checks input part of query for odd single quotes" +
                "\n| " +                                                          "\t\t\t\t  "          + "if present sustitutes ' with ' ' " +
                "\n" +
                "\n * Are mutual exclusive" +
                "\n";
    }

    protected void addNewline() {
        log = log + "\n";
    }

    public void addNothingToReplace(String[] query) {
        add(LOG_NOTHING_TO_REPLACE);
    }

    public void addCheckOk(String[] query) {
        add(LOG_CHECK_OK);
    }

    public void addRefuseExecution() {
        add(LOG_REFUSE_EXECUTION);
    }

    public void addInitialQuery(String[] query) {
        add("Initial query: ");
        add(String.join("", query) + "\n");
    }

    public void addUsedQuery(String[] query) {
        add("Using: ");
        add(String.join("", query) + "\n");
    }
}

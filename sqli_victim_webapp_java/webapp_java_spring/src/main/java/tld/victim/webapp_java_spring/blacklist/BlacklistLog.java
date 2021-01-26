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
                "\n|" + blacklistConfDataHelper.CONFSTRING_HELP                + "\t\t\t|" + "Shows this help information" + "\t\t\t\t|" +
                "\n|" + blacklistConfDataHelper.CONFSTRING_ALL_UPPERCASE + "*" + "\t\t|"   + "Checks if query (except table) is upper case" + "\t\t|" +
                "\n|" + blacklistConfDataHelper.CONFSTRING_ALL_LOWERCASE + "*" + "\t\t|"   + "Checks if query (except table) is lower case" + "\t\t|" +
                "\n|" + blacklistConfDataHelper.CONFSTRING_ODD_SINGLE_QUOTES   + "\t|"     + "Checks predicate part of query for odd single quotes" + "\t|" +
                "\n|" +                                                          "\t\t\t|" + "if present sustitutes ' with ' ' " + "\t\t\t|" +
                "\n|" + blacklistConfDataHelper.CONFSTRING_KEYWORD_DETECTION   + "\t|"     + "" + "\t\t\t\t\t\t\t| " +
                "\n" +
                "\n* Are mutual exclusive" +
                "\n" +
                "\n";
    }

    protected void addNewline() {
        log = log + "\n";
    }

    public void addNothingToReplace(String[] query) {
        add(LOG_NOTHING_TO_REPLACE);
        add("Using: ");
        add(String.join("", query) + "\n");
    }

    public void addCheckOk(String[] query) {
        add(LOG_CHECK_OK);
        add("Using: ");
        add(String.join("", query) + "\n");
    }

    public void addRefuseExecution() {
        add(LOG_REFUSE_EXECUTION);
    }
}

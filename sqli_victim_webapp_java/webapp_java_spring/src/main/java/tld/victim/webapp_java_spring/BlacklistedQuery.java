package tld.victim.webapp_java_spring;

public class BlacklistedQuery {

    private final String sql;

    public BlacklistedQuery(String sql) {
        this.sql = replaceStartingQuote(sql);
    }

    public String replaceStartingQuote(String query)
    {
        if (query.startsWith("'"))
            return "''" + query.substring(1);
        else
            return query;
    }

    @Override
    public String toString() {
        return sql;
    }
}

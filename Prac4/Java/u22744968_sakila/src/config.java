public class config
{
    String dbProtocol = System.getenv("dvdrental_DB_PROTO");
    String dbHost = System.getenv("dvdrental_DB_HOST");
    String dbPort = System.getenv("dvdrental_DB_PORT");
    String dbName = System.getenv("dvdrental_DB_NAME");
    String dbUsername = System.getenv("dvdrental_DB_USERNAME");
    String dbPassword = System.getenv("dvdrental_DB_PASSWORD");
    String dbDriver = "org.mariadb.jdbc.Driver";
    String dbURL = dbProtocol + dbHost + ":" + dbPort + "/" + dbName;

    public String getDBURL()
    {
        return dbURL;
    }
    public String getDBDriver()
    {
        return dbDriver;
    }
    public String getDBUsername()
    {
        return dbUsername;
    }
    public String getDBPassword()
    {
        return dbPassword;
    }
}

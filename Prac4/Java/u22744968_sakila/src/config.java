public class config
{
    String dbProtocol = "jdbc:mariadb://";
    String dbHost = "localhost";
    String dbPort = "3306";
    String dbName = "u22744968_sakila";
    String dbUsername = "root";
    String dbPassword = "Timone2357";
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

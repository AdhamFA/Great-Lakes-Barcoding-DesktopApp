package sample;

public class Credentials {
    private static String empName;
    private static String usrName;
    private static String pass;

    public Credentials(String empName, String usrName, String pass) {
        this.empName = empName;
        this.usrName = usrName;
        this.pass = pass;
    }

    public static String getUser() {return usrName;}

    public static String getPass() {return pass;}

    public static String getName() {return empName;}
}

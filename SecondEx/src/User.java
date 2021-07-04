import java.io.Serializable;

public class User implements Serializable
{
    public String firstName;
    public String lastName;
    public String userName;

    public User(String firstName, String lastName, String userName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }




}

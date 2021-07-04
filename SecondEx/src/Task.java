import java.io.Serializable;

public class Task implements Serializable
{
    public String userName;
    public String taskTitle;
    public String taskDescription;

    public Task(String userName, String taskTitle, String taskDescription) {
        this.userName = userName;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
    }

    @Override
    public String toString()
    {
        return userName + " " + taskTitle + " " + userName;
    }
}

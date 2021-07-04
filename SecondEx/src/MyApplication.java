import picocli.CommandLine;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@CommandLine.Command(name="MyApplication", mixinStandardHelpOptions = true)

public class MyApplication implements Runnable
{
    @CommandLine.Option(names = {"-showTasks", "-sT"},  description = "Show user tasks")
    boolean showTasks;
    @CommandLine.Option(names = {"-addTask", "-aT"}, description = "Add new task")
    boolean addTask;
    @CommandLine.Option(names = {"-showAllUsers", "-sAU"}, description = "Show all users")
    boolean showAllUsers;
    @CommandLine.Option(names = {"-createUser", "-cU"}, description = "Create new User")
    boolean createUser;
    @CommandLine.Option(names = {"-fn", "-firstName"}, description = "Set user First Name")
    String firstName;
    @CommandLine.Option(names = {"-ln", "-lastName"}, description = "Set user Last Name")
    String lastName;
    @CommandLine.Option(names = {"-un", "-userName"}, description = "Set user UserName")
    String userName;
    @CommandLine.Option(names = {"-tt", "-taskTitle"}, description = "Set task title for user")
    String taskTitle;
    @CommandLine.Option(names = {"-td", "-taskDescription"}, description = "Set task description for user")
    String taskDescription;


    @Override
    public void run() {
        if(createUser)
        {
            if(firstName == null || lastName == null || userName == null)
            {
                System.err.print("Wrong command call! Please check '-help' options\n" +
                        "Command '-createUser' requires 3 arguments!\n" +
                        "Example: '-createUser -fn='First Name' -ln='Last Name' -un='User Name'\n");
            }
            else
            {
                try
                {
                    User user = new User(firstName, lastName, userName);
                    File file = new File("data.bin");
                    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("data.bin"));
                    boolean append = file.exists();
                    boolean unique = false;
                    Object obj;
                    try
                    {


                        while ((obj = inputStream.readObject()) != null)
                        {
                            if (obj instanceof User)
                            {
                                if(((User) obj).userName.equals(userName))
                                {
                                    unique = true;
                                }
                            }
                        }
                        inputStream.close();
                    } catch (EOFException e)
                    {
                        System.out.print("");
                    }
                    if(!unique)
                    {
                        FileOutputStream fileOutputStream = new FileOutputStream(file, append);
                        AppendableObjectOutputStream oout = new AppendableObjectOutputStream(fileOutputStream, append);
                        oout.writeObject(user);
                        oout.close();
                        fileOutputStream.close();
                        System.out.printf("User %s was created successfully!", userName);

                    }
                    else
                    {
                        System.err.printf("User %s already exist in database!", userName);
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        }
        if(showAllUsers)
        {
            Object obj;
            try
            {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("data.bin"));
                System.out.println("First Name / Last Name / UserName");
                while ((obj = inputStream.readObject()) != null)
                {
                    if (obj instanceof User)
                    {
                        System.out.println(obj);
                    }
                }
                inputStream.close();
            } catch (EOFException e)
            {
                System.out.print("");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(addTask)
        {
            if(userName == null || taskTitle == null || taskDescription == null)
            {
                System.err.print("Wrong command call! Please check '-help' options\n" +
                        "Command '-addTask' requires 3 arguments!\n" +
                        "Example: '-addTask -un='UserName' -tt='Task Title' -td='Task Description'\n");
            }
            else
            {
                Task task = new Task(userName, taskTitle, taskDescription);
                File file = new File("tasksData.bin");
                boolean append = file.exists();
                try
                {
                    FileOutputStream fileOutputStream = new FileOutputStream(file, append);
                    AppendableObjectOutputStream oout = new AppendableObjectOutputStream(fileOutputStream, append);
                    oout.writeObject(task);
                    oout.close();
                    fileOutputStream.close();
                    System.out.printf("Task '%s' for user %s was added successfully", taskTitle, userName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(showTasks)
        {
            if(userName == null)
            {
                System.err.print("Wrong command call! Please check '-help' options\n" +
                                "Command '-showTasks' requires 1 argument!\n" +
                                "Example: '-showTasks -un='UserName'\n");
            }
            else
            {
                Object obj;
                try
                {
                    System.out.println("UserName / Task Title / Task Description\n");
                    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("tasksData.bin"));
                    while ((obj = inputStream.readObject()) != null)
                    {
                        if (obj instanceof Task)
                        {
                                if(((Task) obj).userName.equals(userName))
                                {
                                    System.out.println(((Task) obj).userName + " " + ((Task) obj).taskTitle + " "
                                            + ((Task) obj).taskDescription);
                                }
                        }
                    }
                    inputStream.close();
                } catch (EOFException e)
                {
                    System.out.print("");
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String... args)
    {
        new CommandLine(new MyApplication()).execute(args);
    }
}

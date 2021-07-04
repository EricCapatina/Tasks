import picocli.CommandLine;

import java.io.*;

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
            boolean unique = false;
            File data = new File("data.txt");
            if(firstName == null || lastName == null || userName == null)
            {
                System.err.print("Wrong command call! Please check '-help' options\n" +
                        "Command '-createUser' requires 3 arguments!\n" +
                        "Example: '-createUser -fn='FirstName' -ln='LastName' -un='UserName'\n");
            }
            else
            {
                    System.out.println("UserName / Task Title / Task Description\n");
                    try
                    {
                        if(!unique)
                        {
                            PrintWriter pw = new PrintWriter(new FileOutputStream(data, true));
                            try
                            {
                                BufferedReader bufferedReader = new BufferedReader(new FileReader("data.txt"));
                                String s = "";
                                while((s = bufferedReader.readLine()) != null)
                                {
                                    if(s.contains(userName))
                                    {
                                        unique = true;
                                    }
                                }
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            if(unique)
                            {
                                System.err.printf("User %s already exists!", userName);
                            }
                            else
                            {
                                pw.append("\n").append("User: ").append(firstName).append(",").append(lastName).append(",").append(userName);
                                pw.close();
                                System.out.printf("User %s was created successfully!", userName);
                            }

                        }
                        else
                        {
                            System.err.printf("User %s already exists!", userName);
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }
        }
        if(showAllUsers)
        {
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("data.txt"));
                String s = "";
                System.out.println("First Name / Last Name / UserName");
                while((s = bufferedReader.readLine()) != null)
                {
                    String[] data;
                    data = s.split(",");
                    for (String databaseInfo : data)
                    {
                        System.out.print(databaseInfo + " ");
                    }
                    System.out.println();
                }
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
                try
                {
                    File data = new File("tasksData.txt");
                    PrintWriter pw = new PrintWriter(new FileOutputStream(data, true));
                    pw.append("\n").append("UserName: ").append(userName).append(",").append("Task Title:").
                            append(taskTitle).append(",").append("Task Description:").append(taskDescription);
                    pw.close();
                    System.out.printf("Task '%s' for user %s was created successfully!", taskTitle, userName);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        if(showTasks)
        {
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("tasksData.txt"));
                String s = "";
                System.out.println("UserName / Task Title / Task Description\n");
                while((s = bufferedReader.readLine()) != null)
                {
                    if(s.contains(userName))
                    {
                        String[] data;
                        data = s.split(",");
                        for (String databaseInfo : data)
                        {
                            System.out.print(databaseInfo + " ");
                        }
                        System.out.println();
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String... args)
    {
        new CommandLine(new MyApplication()).execute(args);
    }
}
import picocli.CommandLine;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

@CommandLine.Command(name="MyApplication", mixinStandardHelpOptions = true)

public class MyApplication implements Runnable
{
    @CommandLine.Option(names = {"-createDatabase", "-cD"},  description = "Create empty database for work")
    boolean createDatabase;
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

    Path currentRelativePath = Paths.get("");
    String relativePath = currentRelativePath.toAbsolutePath().toString();
    String url = "jdbc:sqlite:" + relativePath + "/Chat.db";

    @Override
    public void run()
    {
        if(createUser)
        {
            if(firstName == null || lastName == null || userName == null)
            {
                System.err.print("Wrong command call! Please check '-help' options\n" +
                                 "Command '-showTasks' requires 3 arguments!\n" +
                                 "Example: '-showTasks -fn='FirstName' -ln='LastName' -un='UserName'\n");
            }
            else
            {
                String sql = "INSERT INTO users(firstName,lastName,userName) VALUES(?,?,?)";
                try (Connection conn = DriverManager.getConnection(url);
                     PreparedStatement pstmt = conn.prepareStatement(sql))
                {
                    pstmt.setString(1, firstName);
                    pstmt.setString(2, lastName);
                    pstmt.setString(3, userName);
                    pstmt.executeUpdate();
                    System.out.printf("User has been created successfully!\n" +
                                      "First Name = %s, Last Name = %s, UserName = %s\n", firstName, lastName, userName);
                }
                catch (SQLException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        if(showAllUsers)
        {
            String sql = "SELECT firstName, lastName, userName from users";

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql))
            {
                while (rs.next())
                {
                    System.out.println("First Name = " + "'" + rs.getString("firstName") + "'" + "\t" +
                                       "Last Name = " + "'" + rs.getString("lastName") + "'" + "\t" +
                                       "UserName = " + "'" + rs.getString("userName") + "'");
                }
            } catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
        if(addTask)
        {
            if(userName == null || taskTitle == null || taskDescription == null)
            {
                System.err.print("Wrong command call! Please check '-help' options\n" +
                                "Command '-addTask' requires 2 arguments!\n" +
                                "Example: '-addTask -un='UserName' -tt='Task Title' -td='Task Description'\n");
            }
            else
            {
                String sql = "INSERT INTO tasks(userName, taskTitle, taskDescription) VALUES(?,?,?)";

                try (Connection conn = DriverManager.getConnection(url);
                     PreparedStatement pstmt = conn.prepareStatement(sql))
                {
                    pstmt.setString(1, userName);
                    pstmt.setString(2, taskTitle);
                    pstmt.setString(3, taskDescription);
                    pstmt.executeUpdate();
                    System.out.printf("Task with title %s was successfully added for user %s", taskTitle, userName);
                }
                catch (SQLException e)
                {
                    System.out.println(e.getMessage());
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
                String sql = "SELECT userName, taskTitle, taskDescription FROM tasks WHERE userName=" + "'" + userName + "'";

                try (Connection conn = DriverManager.getConnection(url);
                     Statement stmt  = conn.createStatement();
                     ResultSet rs    = stmt.executeQuery(sql))
                {
                    while (rs.next())
                    {

                        System.out.println("UserName = " + "'" + rs.getString("userName") + "' " +
                                            "Task Title = " + "'" + rs.getString("taskTitle") + "'" + "\t" +
                                            "Task Description = " + "'" + rs.getString("taskDescription") + "'");
                    }
                } catch (SQLException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        if(createDatabase)
        {
            createDataBase();
        }
    }

    public void createDataBase()
    {
        String sqlStmtForTasks =
                "CREATE TABLE IF NOT EXISTS tasks (\n"
                        + "	userName text NOT NULL,\n"
                        + "	taskTitle text NOT NULL,\n"
                        + "	taskDescription text NOT NULL\n"
                        + ");";
        String sqlStmtForUsers =
                "CREATE TABLE IF NOT EXISTS users (\n"
                        + "	userId integer NOT NULL PRIMARY KEY AUTOINCREMENT,\n"
                        + "	firstName text NOT NULL,\n"
                        + "	lastName text NOT NULL,\n"
                        + "	userName text NOT NULL UNIQUE\n"
                        + ");";

        try (Connection conn = DriverManager.getConnection(url))
        {
            if (conn != null)
            {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName()
                        +  "\nA new database has been created.");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement())
        {
            stmt.execute(sqlStmtForTasks);
            stmt.execute(sqlStmtForUsers);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String... args)
    {
        new CommandLine(new MyApplication()).execute(args);
    }
}

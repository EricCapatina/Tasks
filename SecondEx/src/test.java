import picocli.CommandLine;

import java.io.*;
import java.util.Scanner;

public class test
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        String firstName = sc.nextLine();
//        String lastName = sc.nextLine();
//        String userName = sc.nextLine();
//        User user = new User(firstName, lastName, userName);
//        File file = new File("data.bin");
//        boolean append = file.exists();
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(file, append);
//            AppendableObjectOutputStream oout = new AppendableObjectOutputStream(fileOutputStream, append);
//            oout.writeObject(user);
//            oout.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data.bin"));
            User u = (User) objectInputStream.readObject();
            while(true)
            {
                System.out.println(u.firstName + " " + u.lastName + " " + u.userName);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}






//import picocli.CommandLine;
//
//        import java.io.*;
//        import java.nio.file.Path;
//        import java.nio.file.Paths;
//
//@CommandLine.Command(name="MyApplication", mixinStandardHelpOptions = true)
//
//public class MyApplication implements Runnable
//{
//    @CommandLine.Option(names = {"-showTasks", "-sT"},  description = "Show user tasks")
//    boolean showTasks;
//    @CommandLine.Option(names = {"-addTask", "-aT"}, description = "Add new task")
//    boolean addTask;
//    @CommandLine.Option(names = {"-showAllUsers", "-sAU"}, description = "Show all users")
//    boolean showAllUsers;
//    @CommandLine.Option(names = {"-createUser", "-cU"}, description = "Create new User")
//    boolean createUser;
//    @CommandLine.Option(names = {"-fn", "-firstName"}, description = "Set user First Name")
//    String firstName;
//    @CommandLine.Option(names = {"-ln", "-lastName"}, description = "Set user Last Name")
//    String lastName;
//    @CommandLine.Option(names = {"-un", "-userName"}, description = "Set user UserName")
//    String userName;
//    @CommandLine.Option(names = {"-tt", "-taskTitle"}, description = "Set task title for user")
//    String taskTitle;
//    @CommandLine.Option(names = {"-td", "-taskDescription"}, description = "Set task description for user")
//    String taskDescription;
//
////    Path currentRelativePath = Paths.get("");
////    String relativePath = currentRelativePath.toAbsolutePath().toString();
//
//    @Override
//    public void run() {
//        if(createUser)
//        {
//            boolean unique = false;
//            User user = new User(firstName, lastName, userName);
//            try {
//                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data.txt"));
//                objectOutputStream.writeObject(user);
//                objectOutputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
////            try
////            {
////
////                BufferedReader bufferedReader = new BufferedReader(new FileReader("data.txt"));
////                String s = "";
////                System.out.println("UserName / Task Title / Task Description\n");
////                while((s = bufferedReader.readLine()) != null)
////                {
////                    if(s.contains(userName))
////                    {
////                        unique = true;
////                    }
////                }
////            } catch (Exception e)
////            {
////                e.printStackTrace();
////            }
////            if(firstName == null || lastName == null || userName == null)
////            {
////                System.err.print("Wrong command call! Please check '-help' options\n" +
////                        "Command '-showTasks' requires 3 arguments!\n" +
////                        "Example: '-showTasks -fn='FirstName' -ln='LastName' -un='UserName'\n");
////            }
////            else
////            {
////                if(!unique)
////                {
////                    System.out.println("UserName / Task Title / Task Description\n");
////                    try
////                    {
////                        File data = new File("data.txt");
////                        PrintWriter pw = new PrintWriter(new FileOutputStream(data, true));
////                        pw.append("\n").append("User: ").append(firstName).append(",").append(lastName).append(",").append(userName);
////                        pw.close();
////                        System.out.printf("User %s was created successfully!", userName);
////                    } catch (Exception e)
////                    {
////                        e.printStackTrace();
////                    }
////                }
////                else
////                {
////                    System.out.printf("User %s already exists!", userName);
////                }
////            }
//        }
//        if(showAllUsers)
//        {
//            try
//            {
//                BufferedReader bufferedReader = new BufferedReader(new FileReader("data.txt"));
//                String s = "";
//                System.out.println("First Name / Last Name / UserName");
//                while((s = bufferedReader.readLine()) != null)
//                {
//                    String[] data;
//                    data = s.split(",");
//                    for (String databaseInfo : data)
//                    {
//                        System.out.print(databaseInfo + " ");
//                    }
//                    System.out.println();
//                }
//            } catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//        if(addTask)
//        {
//            if(userName == null || taskTitle == null || taskDescription == null)
//            {
//                System.err.print("Wrong command call! Please check '-help' options\n" +
//                        "Command '-showTasks' requires 3 arguments!\n" +
//                        "Example: '-showTasks -fn='FirstName' -ln='LastName' -un='UserName'\n");
//            }
//            else
//            {
//                try
//                {
//                    File data = new File("tasksData.txt");
//                    PrintWriter pw = new PrintWriter(new FileOutputStream(data, true));
//                    pw.append("\n").append("UserName: ").append(userName).append(",").append("Task Title:").
//                            append(taskTitle).append(",").append("Task Description:").append(taskDescription);
//                    pw.close();
//                    System.out.printf("Task '%s' for user %s was created successfully!", taskTitle, userName);
//                } catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//        if(showTasks)
//        {
//            try
//            {
//                BufferedReader bufferedReader = new BufferedReader(new FileReader("tasksData.txt"));
//                String s = "";
//                System.out.println("UserName / Task Title / Task Description\n");
//                while((s = bufferedReader.readLine()) != null)
//                {
//                    if(s.contains(userName))
//                    {
//                        String[] data;
//                        data = s.split(",");
//                        for (String databaseInfo : data)
//                        {
//                            System.out.print(databaseInfo + " ");
//                        }
//                        System.out.println();
//                    }
//                }
//            } catch (Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String... args)
//    {
//        new CommandLine(new MyApplication()).execute(args);
//    }
//}


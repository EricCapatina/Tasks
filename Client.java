import java.util.*;

public class Client
{
    public static void main(String[] args)
    {
        System.out.println(birthdayCakeCandles());
    }
    public static int birthdayCakeCandles()
    {
        int count = 0;
        int tempNumber = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the size of candles:");
        int n = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the height of candles:");
        String[] height = scanner.nextLine().split(" ");
        List<Integer> candles = new ArrayList<>();
        for(String elem : height)
        {
            candles.add(Integer.parseInt(elem));
        }
        for (Integer candle : candles)
        {
            if (tempNumber < candle)
            {
                tempNumber = candle;
            }
        }
        for(int value : candles)
        {
            if(value == tempNumber)
            {
                count++;
            }
        }
        return count;
    }
}

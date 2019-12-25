import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;


public class Main {

    public static void main(String[] args) {

        int count = 0;
        String urlRoot = "https://skillbox.ru/";
        StringBuilder a = new StringBuilder();
        a.append(urlRoot + "\n");
        boolean recordNestedData = false; // установка флага для отображения вложений.

        long start = System.currentTimeMillis();
        a.append(new ForkJoinPool().invoke(new RecursTasks(urlRoot, count, recordNestedData)));
        long finish = System.currentTimeMillis();

        try {
            FileWriter file = null;
            if(recordNestedData)
            {
                 file = new FileWriter("mapSiteWithNested.txt");
            }
            else
            {
                file = new FileWriter("mapSiteNotNested.txt");
            }
            file.write(a.toString());
            file.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println((finish - start)/1000);

    }
}























import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class RecursTasks extends RecursiveTask<StringBuilder>
{
    private final String root;
    private ArrayList<String> list = new ArrayList<>();
    private StringBuilder urls = new StringBuilder();
    private int count;
    private boolean recordNestedData;
    private Document doc;
    private StringBuilder tab = new StringBuilder();


    public RecursTasks(String root, int count, boolean recordNestedData)
    {
        this.root = root;
        this.count = count;
        this.recordNestedData = recordNestedData;
    }

    @Override
    protected StringBuilder compute()
    {
        count++;

        for (int i = 0; i <count; i++)
        {
            tab.append("\t");
        }

        try
        {
            Thread.sleep(500);
            doc = Jsoup.connect(root).get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Elements elements = doc.select("a[href^=https://skillbox.ru]");


        for (Element element : elements)
        {
            String absoluteUrl = element.absUrl("href");

            if(!list.contains(absoluteUrl) && !absoluteUrl.matches(".+.pdf$") && !absoluteUrl.matches("https://skillbox.ru"))
            {
                list.add(absoluteUrl);
            }
            else if(recordNestedData)
            {
                urls.append(tab + absoluteUrl + "\n");
                continue;
            }
        }

        List<RecursTasks> recursTasksList = new ArrayList<>();

        if(!list.isEmpty())
        {
            //"Переходми в эту секцию"
            for (String url : list)
            {
                //"Запускаем поток ";
                urls.append(tab + url + "\n");
                RecursTasks task = new RecursTasks(url, count, recordNestedData);
                task.fork();
                /*Появляется узкое место в коде, я пробывал создать отдельный список задач,
                * добавлять в них task и после выхода из цикла созадть новый и запустить task.join()
                * но тогда в строке в которую мы пишем, все добвавляется по очереди */

                recursTasksList.add(task); // добавляю задачи в список запущенных задач.
            }
        }
        else
        {
            //"Конец потока"
            Thread.interrupted();
        }

        //Ниже перебираю список и получаю строки и добавляю в StringBuilder
        for(RecursTasks task: recursTasksList){
            urls.append(task.join());
        }

        return urls;
    }
}

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class RecursTasks extends RecursiveTask<String> {

    private final String root;
    private final int maxBodySize = 20480000;
    ArrayList<String> list = new ArrayList<>();

    public RecursTasks(String root) {
        this.root = root;
    }

    @Override
    protected String compute() {

        String absUrl;

        Document doc = null;
        try
        {
            Thread.sleep(500);
            doc = Jsoup.connect(root).maxBodySize(maxBodySize).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Elements elements = doc.select("a[href^=https://skillbox.ru]");



        for (Element element : elements) {
            absUrl = element.absUrl("href");
            if(!list.contains(absUrl) && !absUrl.matches(".+.pdf$") && !absUrl.matches("https://skillbox.ru")){
                System.out.println("Записываем в список" + absUrl);
                list.add(absUrl);
            }else{
                System.out.println("также есть " + absUrl);
                continue;
            }
        }
        if(!list.isEmpty()) {
            System.out.println("Переходми в эту секцию");
            for (String url : list) {
                System.out.println("Запускаем поток " + url );
                RecursiveTask task = new RecursTasks(url);
                task.fork();
                task.join();
            }
        }else{
            System.out.println("Конец потока");
            Thread.interrupted();
        }

        return "0";
    }
}

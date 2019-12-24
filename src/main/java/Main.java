import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.ForkJoinPool;


public class Main {

    public static void main(String[] args) {

        String urlRoot = "https://skillbox.ru/";

        new ForkJoinPool().invoke(new RecursTasks(urlRoot));




    }
}























import network.db.DatabaseHandler;
import utility.Program;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;


public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (args.length == 0) {
                System.out.println("Не передан аргумент с путем к config файлу.");
                return;
            }
            Properties properties = new Properties();
            properties.load(new FileReader(args[0]));
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            if (url == null || username == null || password == null) {
                System.out.println("В config файле не настроены ключевые поля.");
                return;
            }
            DatabaseHandler databaseHandler = new DatabaseHandler(url, username, password);
            Program program = Program.getInstance();
            program.setDatabaseHandler(databaseHandler);
            program.start();
        } catch (IOException e) {
            System.out.println("Ошибка при считывании config файла, остановка программы.");
        }
//        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//        for (int i = 0; i < 47; i++) {
//            final int taskNumber = i;
//            executorService.execute(() -> {
//                System.out.println("Task " + taskNumber + " on " + Thread.currentThread().getName());
//            });
//        }
    }
}

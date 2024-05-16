package utility;

import network.*;
import network.db.DatabaseHandler;
import shared.Request;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.*;


/**
 * Класс-синглтон программы.
 * Позволяет запускать и останавливать работу программы.
 * Хранит в себе историю команд, список команд, название системной переменной,
 * индикатор работы программы в виде булева значения,
 * индикатор наличия ошибки в виде булева значения.
 */
public class Program implements ProgramInterface {
    private static Program instance;
    private boolean workingStatus;
    private boolean isError;
    private DatabaseHandler databaseHandler;
    private DatagramChannel channel;
    private RequestLogic requestLogic;
    private ResponseLogic responseLogic;
    private final ResponseBuilder responseBuilder = new ResponseBuilder();
    private final Map<String, LinkedList<String>> history = new HashMap<>();

    private Program() {

    }

    public RequestLogic getRequestLogic() {
        return requestLogic;
    }

    public ResponseLogic getResponseLogic() {
        return responseLogic;
    }

    public ResponseBuilder getResponseBuilder() {
        return responseBuilder;
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    /**
     * Выводит в консоль историю команд (последние 10 команд).
     */
    public String printHistory(String username) {
        return history.get(username).toString();
    }


    public void updateHistory(String username, String commandName) {
        if (!history.containsKey(username)) {
            history.put(username, new LinkedList<>());
        }
        LinkedList<String> historyList = history.get(username);
        if (historyList.size() >= 10) {
            historyList.removeFirst();
        }
        historyList.addLast(commandName);
    }

    /**
     * Возвращает статус работы программы в булевом значении.
     *
     * @return статус работы программы в булевом значении
     */
    public boolean isWorkingStatus() {
        return workingStatus;
    }

    /**
     * Метод, отвечающий за запуск программы.
     * Запуск происходит, если нет ошибок и статус программы в выключенном состоянии.
     */
    @Override
    public void start() {
        prepare();
        if (!workingStatus && !isError) {
            workingStatus = true;
            ThreadPoolExecutor threadPoolExecutor1 = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            ThreadPoolExecutor threadPoolExecutor2 = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            ThreadPoolExecutor threadPoolExecutor3 = (ThreadPoolExecutor) Executors.newCachedThreadPool();

            while (workingStatus) {
                Future<Request> future = threadPoolExecutor1.submit(() -> requestLogic.receive());
                Request request;
                try {
                    request = future.get();
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Ошибка при работе с потоком.");
                    continue;
                }
                if (request == null) continue;

                threadPoolExecutor2.execute(() -> {
                    RequestHandler requestHandler = new RequestHandler(request);
                    requestHandler.handle();

                    String username = request.getUsername();
                    threadPoolExecutor3.execute(() -> {
                        responseLogic.send(requestLogic.getClientAddress(username), responseBuilder.get(username));
                    });
                });
//                String string;
//                try {
//                    string = future2.get();
//                } catch (InterruptedException | ExecutionException e) {
//                    System.out.println("Ошибка при работе с потоком.");
//                    continue;
//                }
//                if (string == null) continue;
            }
        }
    }

    /**
     * Метод, отвечающий за остановку программы.
     */
    @Override
    public void stop() {
        workingStatus = false;
        System.out.println("Завершение работы.");
        System.out.close();
    }

    public void stopWithError(String message) {
        isError = true;
        System.out.println(message);
        stop();
    }

    private void prepare() {
        try {
            databaseHandler.connect(); // connection to db
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            ClientBinder clientBinder = new ClientBinder(channel);
            clientBinder.bind();
            requestLogic = new RequestLogic(channel);
            responseLogic = new ResponseLogic(channel);

        } catch (SQLException e) {
            e.printStackTrace();
            stopWithError("Ошибка при подключении к базе данных.");
        } catch (IOException e) {
            stopWithError("Ошибка при подключении клиента к серверу.");
        }
    }

    public static synchronized Program getInstance() {
        if (instance == null) {
            instance = new Program();
        }
        return instance;
    }
}

package utility;

import network.*;
import network.db.DatabaseHandler;
import shared.Request;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


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
    private final DatabaseHandler databaseHandler = new DatabaseHandler("jdbc:postgresql://localhost:6734/studs", "s408992", "TN7ob335SA9B09IE");
    private DatagramChannel channel;
    private RequestLogic requestLogic;
    private ResponseLogic responseLogic;
    private final ResponseBuilder responseBuilder = new ResponseBuilder();
    private final LinkedList<String> history = new LinkedList<>();

    public final List<String> commands = Arrays.asList(
            "help",
            "info",
            "show",
            "insert",
            "update",
            "remove_key",
            "clear",
            "save",
            "execute_script",
            "exit",
            "remove_greater",
            "remove_lower",
            "history",
            "min_by_creation_date",
            "print_ascending",
            "print_unique_number_of_participants"
    );


    private Program() {
        try {
            databaseHandler.connect(); // connection to db
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            ClientBinder clientBinder = new ClientBinder(channel);
            clientBinder.bind();
            requestLogic = new RequestLogic(channel);
            responseLogic = new ResponseLogic(channel);

        } catch (SQLException e) {
            stopWithError("Ошибка при подключении к базе данных.");
        } catch (IOException e) {
            stopWithError("Ошибка при подключении клиента к серверу.");
        }
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

    /**
     * Возвращает список команд.
     *
     * @return список команд.
     */
    public List<String> getCommands() {
        return commands;
    }

    /**
     * Выводит в консоль историю команд (последние 10 команд).
     */
    public String printHistory() {
        return history.toString();
    }

    /**
     * Обновляет состояние истории команд.
     *
     * @param s команда, которую надо добавить
     */
    public void updateHistory(String s) {
        if (history.size() == 10) {
            history.removeFirst();
        }
        history.addLast(s);
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
        if (!workingStatus && !isError) {
            workingStatus = true;
            while (workingStatus) {
                Request request = getRequestLogic().receive();
                if (request == null) continue;
                RequestHandler requestHandler = new RequestHandler(request);
                requestHandler.handle();
                responseLogic.send(requestLogic.getClientAddress(), responseBuilder.get());
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


    public static synchronized Program getInstance() {
        if (instance == null) {
            instance = new Program();
        }
        return instance;
    }
}

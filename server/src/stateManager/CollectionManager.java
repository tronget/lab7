package stateManager;

import models.MusicBand;
import network.db.DatabaseManager;

import java.util.Hashtable;

/**
 * Класс-Синглтон, хранящий в себе коллекцию и реализацию команд, которые управляют самой коллекцией.
 */
public class CollectionManager {
    private Hashtable<String, MusicBand> collection;
    private static CollectionManager instance;

    /**
     * При создании CollectionManager создается объект File,
     * который принимает путь к файлу через системную переменную.
     * Если файл не существует, он создается; если файл не является файлом, программа останавливается.
     * Если все прошло успешно, то загружаются данные из файла в поле collection.
     * Затем добавляются все команды в hashmap-у commandsMapWithoutArgs или commandsMapWithArg.
     */
    private CollectionManager() {
        this.collection = DatabaseManager.getMusicBandsTable();
    }

    /**
     * Возвращает исходную коллекцию.
     *
     * @return исходная коллекция
     */
    public Hashtable<String, MusicBand> getCollection() {
        return new Hashtable<>(collection);
    }

    /**
     * Устанавливает коллекцию.
     *
     * @param ht новая коллекция
     */
    public void setCollection(Hashtable<? extends String, ? extends MusicBand> ht) {
        collection = new Hashtable<>(ht);
    }


    /**
     * Метод проверяет, есть ли в коллекции элемент с заданным ключом.
     *
     * @param key ключ для проверки
     * @return boolean значение: true, если в коллекции есть элемент с таким ключом, иначе false
     */
    public boolean hasSuchKey(String key) {
        return collection.containsKey(key);
    }


    public static CollectionManager getInstance() {
        if (instance == null) {
            instance = new CollectionManager();
        }
        return instance;
    }
}

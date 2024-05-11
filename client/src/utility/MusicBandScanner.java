package utility;

import builder.MusicBandBuilder;
import models.MusicBand;

/**
 * Класс, реализующий считывание объекта класса {@link MusicBand}.
 */
public class MusicBandScanner {
    /**
     * @return считанный объект класса {@link MusicBand} или Null в случае ошибки считывания
     */
    public static MusicBand scan() {
        MusicBandBuilder musicBandBuilder = new MusicBandBuilder();
        musicBandBuilder.buildMusicBand();
        System.out.println("Ввод окончен успешно!");
        return musicBandBuilder.getResult();
    }
}

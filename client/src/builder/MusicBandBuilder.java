package builder;

import models.Coordinates;
import models.MusicBand;
import models.MusicGenre;
import models.Studio;

import java.time.LocalDateTime;

public class MusicBandBuilder implements Builder {
    private MusicBand musicBand = new MusicBand();

    public void buildMusicBand() {
        buildName();
        buildCoordinates();
        buildNumberOfParticipants();
        buildEstablishmentDate();
        buildMusicGenre();
        buildStudio();
    }

    public void buildName() {
        String name = AbstractBuilder.buildString("Имя музыкальной группы");
        musicBand.setName(name);
    }

    public void buildCoordinates() {
        int x = AbstractBuilder.buildIntWithCondition(
                "Координата по X целое число (-768 < X < 2147483648)",
                "Ограничение: Целое число (-768 < X < 2147483648)!",
                (num -> num > -768));
        double y = AbstractBuilder.buildDouble(
                "Координата по Y",
                "Ограничение: Вещественное число!");
        Coordinates coordinates = new Coordinates(x, y);
        musicBand.setCoordinates(coordinates);
    }

    public void buildNumberOfParticipants() {
        int numberOfParticipants = AbstractBuilder.buildIntWithCondition(
                "Кол-во участников",
                "Ограничение: Целое число > 0!",
                x -> x > 0);
        musicBand.setNumberOfParticipants(numberOfParticipants);
    }

    public void buildEstablishmentDate() {
        LocalDateTime establishmentDate = AbstractBuilder.buildLocalDateTime(
                "Дата основания через \"/\" (Год/Месяц/День/Час/Минута/Секунда)",
                "Неправильный формат ввода данных!");
        musicBand.setEstablishmentDate(establishmentDate);
    }

    public void buildMusicGenre() {
        MusicGenre musicGenre = AbstractBuilder.buildEnumValue(
                "Жанр музыки (ROCK, PSYCHEDELIC_CLOUD_RAP, SOUL)",
                "Неправильный формат ввода данных!",
                MusicGenre.class);
        musicBand.setGenre(musicGenre);
    }

    public void buildStudio() {
        String studioName = AbstractBuilder.buildString("Название студии");
        String studioAddress = AbstractBuilder.buildString("Адрес");
        Studio studio = new Studio(studioName, studioAddress);
        musicBand.setStudio(studio);
    }


    @Override
    public void reset() {
        musicBand = new MusicBand();
    }

    @Override
    public MusicBand getResult() {
        return new MusicBand(musicBand);
    }
}

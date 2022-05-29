package com.koley.musrights.domains;

public enum Genres {

    BLUES("Блюз"),
    CHANSON("Шансон"),
    CLASSICAL("Классика"),
    COUNTRY("Кантри"),
    DANCE("Танцевальная"),
    FOLK("Народная"),
    GOSPEL("Госпел"),
    HIP_HOP("Хип-хоп"),
    JAZZ("Джаз"),
    MUSICAL("Мюзикл"),
    OPERA("Опера"),
    POP("Поп"),
    PUNK_ROCK("Панк-рок"),
    RAP_AND_BEATS("R&B"),
    ROCK("Рок"),
    ROCK_N_ROLL("рок-н-ролл"),
    ROMANCE("романс"),
    SPIRITUAL("Духовная"),
    VOCAL("Вокал");

    private final String genre;
    Genres(String genre)
    {
        this.genre = genre;
    }

    public String getGenre(){
        return this.genre;
    }
}

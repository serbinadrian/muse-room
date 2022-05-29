package com.koley.musrights.datasets;

import com.koley.musrights.domains.Composition;
import com.koley.musrights.domains.Genres;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MusicDataset {
    public List<Composition> compositions = new ArrayList<>();
    public int size = 0;
    public MusicDataset(){
        addData("05.10.2022", "one girl/one boy", "Tyhins", Genres.BLUES);
        addData("05.11.2019", "A Summer Day", "Tyhins", Genres.ROCK_N_ROLL);
        addData("15.12.2021", "The Unknown", "Crossfade", Genres.BLUES);
        addData("16.01.2022", "Skin", "Holly Henry", Genres.CHANSON);
        addData("04.02.2020", "I Do", "Sasha Holiday ", Genres.BLUES);
        addData("09.03.2020", "Wildfire", "Seafret", Genres.BLUES);
        addData("12.04.2021", "loverkillers", "ANGELXII", Genres.BLUES);
        addData("17.05.2022", "There", "Koda", Genres.ROCK_N_ROLL);
        addData("25.06.2022", "kids", "the kooks", Genres.ROCK_N_ROLL);
        addData("30.07.2022", "Patriot", "Operace", Genres.ROCK_N_ROLL);
        addData("14.08.2022", "Can’t Stop Party Tonight", "smkngklls", Genres.ROCK_N_ROLL);
        addData("07.09.2019", "die with u baby", "younggoth", Genres.CHANSON);
        addData("09.10.2019", "I gave you a choice", "as the stars fall", Genres.CHANSON);
        addData("13.09.2022", "some kind of love", "lil skies", Genres.CHANSON);
        addData("14.08.2022", "beat it.", "162", Genres.CHANSON);
        addData("26.07.2021", "we are the dead ", "david", Genres.ROCK);
        addData("25.07.2020", "burn", "birdy", Genres.ROCK);
        addData("25.04.2020", "let him go", "birdy", Genres.ROCK);
        addData("05.05.2020", "SOS", "Portishead", Genres.ROCK);
        addData("05.05.2020", "000.sick.", "scarlxrd", Genres.ROCK);
        addData("31.06.2021", "so sad, so bad", "convolk", Genres.MUSICAL);
        addData("05.07.2021", "my sweet boy", "placeboy", Genres.MUSICAL);
        addData("04.02.2022", "simple man", "shinedown", Genres.MUSICAL);
        addData("01.01.2021", "tonight", "rich", Genres.MUSICAL);
        addData("15.03.2019", "better", "nothing", Genres.MUSICAL);
        addData("18.03.2019", "she's the prettiest girl at the party", "frnkiero", Genres.DANCE);
        addData("02.05.2021", "medicine", "daughter", Genres.DANCE);
        addData("08.06.2021", "Wywf?", "ask", Genres.DANCE);
        addData("18.10.2021", "I feel", "people", Genres.DANCE);
        addData("13.11.2020", "A little story", "DVRST", Genres.DANCE);
        addData("03.12.2020", "missing home", "nineishuman", Genres.DANCE);
        addData("14.02.2022", "Trigger of love", "JAWNY", Genres.CLASSICAL);
        addData("14.01.2021", "Freaxxx", "BrokenCYDE", Genres.CLASSICAL);
        addData("16.03.2022", "friday", "rebecca", Genres.CLASSICAL);
        addData("06.03.2019", "my evening", "YD", Genres.CLASSICAL);
        addData("06.05.2019", "neon jungle", "CloZee", Genres.CLASSICAL);
        addData("06.06.2019", "Kyoto", "Bonsaye", Genres.COUNTRY);
        addData("12.07.2020", "killer", "shyler", Genres.COUNTRY);
        addData("14.07.2021", "VAMPIRE", "DANIELFROMSALEM", Genres.COUNTRY);
        addData("18.08.2022", "VIOLENCE", "COLLIDING", Genres.COUNTRY);
        addData("14.04.2021", "RIOT", "SERG", Genres.COUNTRY);
        addData("17.09.2021", "YOU", "VACATION", Genres.FOLK);
        addData("12.12.2021", "SERG", "AMBER (FEAT. RYO)", Genres.FOLK);
        addData("05.11.2022", "BAMBAM", "VACATION (FEAT. LUGA)", Genres.FOLK);
        addData("06.05.2022", "PRETEND", "KILL JASPER", Genres.FOLK);
        addData("07.06.2020", "WYM?", "MALACHII", Genres.FOLK);
        addData("17.10.2021", "FIRE EMOJI III", "YB", Genres.GOSPEL);
        addData("28.10.2019", "Devil", "$LOTHBOI ", Genres.GOSPEL);
        addData("27.07.2019", "Thirst Trap", "ZaddyWithABaddy", Genres.GOSPEL);
        addData("22.10.2022", "Snowing", "Mahaji", Genres.GOSPEL);
        addData("11.11.2021", "Lost", "Amni", Genres.GOSPEL);
        addData("26.12.2021", "Gear", "SoFaygo", Genres.HIP_HOP);
        addData("29.04.2022", "lonely vacation", "ty lorenzo", Genres.HIP_HOP);
        addData("19.05.2020", "Batman", "Delivery Boys", Genres.HIP_HOP);
        addData("19.05.2020", "Dreams", "Yacobucci", Genres.HIP_HOP);
        addData("14.05.2020", "Don't Lie to Me", "Wett Brain", Genres.HIP_HOP);
        addData("11.10.2021", "M3", "kiLLa Laharl", Genres.JAZZ);
        addData("22.11.2022", "Landslide", "Sleepa", Genres.JAZZ);
        addData("25.06.2019", "fax or nah??", "93FEETOFSMOKE", Genres.JAZZ);
        addData("26.04.2022", "ABRAKADABRA", "Sinoda", Genres.JAZZ);
        addData("05.01.2019", "Expiration", "YCK", Genres.JAZZ);
        addData("24.08.2022", " One Call Aw", "Jeffrey", Genres.OPERA);
        addData("14.09.2022", "MAX", "Sinizter", Genres.OPERA);
        addData("06.10.2019", "As Above, So Below", "Tumaggz", Genres.OPERA);
        addData("18.11.2022", "Living the Dream", "Expiration", Genres.OPERA);
        addData("19.05.2022", "I NEED U", "Cairn", Genres.OPERA);
        addData("15.06.2022", "Virtual World", "Lucus", Genres.POP);
        addData("07.03.2022", "Fall", "Lord Gasp", Genres.POP);
        addData("16.04.2022", "Dream", "Lord Gasp", Genres.POP);
        addData("15.01.2022", "Saitama", "Monto", Genres.POP);
        addData("25.05.2022", "ARIZONA", "contradash", Genres.POP);
        addData("29.06.2022", "white lie", "Underd0g", Genres.PUNK_ROCK);
        addData("17.12.2022", "Dancin' In The Moonlight", "FULMETALPARKA", Genres.PUNK_ROCK);
        addData("15.12.2020", "Gym Leader Interlude", "MAX", Genres.PUNK_ROCK);
        addData("12.11.2021", "stay young", "good gasoline", Genres.PUNK_ROCK);
        addData("03.04.2021", "Hollywood Party", "iON LIL GUT", Genres.PUNK_ROCK);
        addData("04.03.2022", "Text Back No Way", "Faceless", Genres.RAP_AND_BEATS);
        addData("05.09.2022", "trying", "midwxst", Genres.RAP_AND_BEATS);
        addData("06.08.2020", "BARRI", "EDDIE FRESCO", Genres.RAP_AND_BEATS);
        addData("15.09.2022", "DOPEHEAD", "STARATS", Genres.RAP_AND_BEATS);
        addData("18.07.2019", "U Don't Even Know M3", "Jakkyboi", Genres.RAP_AND_BEATS);
        addData("29.05.2019", "L.A. Shawty", "RainingOnRoses", Genres.ROMANCE);
        addData("24.04.2022", "HEAVY METAL", "Paris Texas", Genres.ROMANCE);
        addData("25.10.2020", "anxiety", "MEANFACE", Genres.ROMANCE);
        addData("23.06.2021", "Gravity", "YouSane", Genres.ROMANCE);
        addData("23.06.2021", "Serv", "Gorilla", Genres.ROMANCE);
        addData("24.04.2021", "Sky", "Table", Genres.SPIRITUAL);
        addData("23.04.2021", "I.M.41", "Love u", Genres.SPIRITUAL);
        addData("25.06.2021", "NICO", "Take him", Genres.VOCAL);
        addData("14.01.2019", "DOUGA", "Pager", Genres.VOCAL);
        addData("23.06.2020", "Fits", "Genres", Genres.VOCAL);
        addData("12.10.2022", "Collage", "Strange sky", Genres.VOCAL);
        addData("23.08.2020", "459", "good things", Genres.VOCAL);
        addData("15.06.2021", "Vaan", "Billy", Genres.VOCAL);
    }

    private void addData(String sDate1, String name, String author, Genres genre){
        Date date = null;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy").parse(sDate1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Composition composition = new Composition();
        composition.setGenre(genre);
        composition.setUploadDate(date);
        composition.setName(name);
        composition.setAuthor(author);
        compositions.add(composition);
        this.size++;
    }
}

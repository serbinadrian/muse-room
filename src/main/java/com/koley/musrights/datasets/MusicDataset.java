package com.koley.musrights.datasets;

import com.koley.musrights.domains.Composition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MusicDataset {
    public List<Composition> compositions = new ArrayList<>();
    public int size = 0;
    public MusicDataset(){
        addData("05.10.2022", "one girl/one boy", "Tyhins");
        addData("05.11.2019", "A Summer Day", "Tyhins");
        addData("15.12.2021", "The Unknown", "Crossfade");
        addData("16.01.2022", "Skin", "Holly Henry");
        addData("04.02.2020", "I Do", "Sasha Holiday ");
        addData("09.03.2020", "Wildfire", "Seafret");
        addData("12.04.2021", "loverkillers", "ANGELXII");
        addData("17.05.2022", "There", "Koda");
        addData("25.06.2022", "kids", "the kooks");
        addData("30.07.2022", "Patriot", "Operace");
        addData("14.08.2022", "Can’t Stop Party Tonight", "smkngklls");
        addData("07.09.2019", "die with u baby", "younggoth");
        addData("09.10.2019", "I gave you a choice", "as the stars fall");
        addData("13.09.2022", "some kind of love", "lil skies");
        addData("14.08.2022", "beat it.", "162");
        addData("26.07.2021", "we are the dead ", "david");
        addData("25.07.2020", "burn", "birdy");
        addData("25.04.2020", "let him go", "birdy");
        addData("05.05.2020", "SOS", "Portishead");
        addData("05.05.2020", "000.sick.", "scarlxrd");
        addData("31.06.2021", "so sad, so bad", "convolk");
        addData("05.07.2021", "my sweet boy", "placeboy");
        addData("04.02.2022", "simple man", "shinedown");
        addData("01.01.2021", "tonight", "rich");
        addData("15.03.2019", "better", "nothing");
        addData("18.03.2019", "she's the prettiest girl at the party", "frnkiero");
        addData("02.05.2021", "medicine", "daughter");
        addData("08.06.2021", "Wywf?", "ask");
        addData("18.10.2021", "I feel", "people");
        addData("13.11.2020", "A little story", "DVRST");
        addData("03.12.2020", "missing home", "nineishuman");
        addData("14.02.2022", "Trigger of love", "JAWNY");
        addData("14.01.2021", "Freaxxx", "BrokenCYDE");
        addData("16.03.2022", "friday", "rebecca");
        addData("06.03.2019", "my evening", "YD");
        addData("06.05.2019", "neon jungle", "CloZee");
        addData("06.06.2019", "Kyoto", "Bonsaye");
        addData("12.07.2020", "killer", "shyler");
        addData("14.07.2021", "VAMPIRE", "DANIELFROMSALEM");
        addData("18.08.2022", "VIOLENCE", "COLLIDING");
        addData("14.04.2021", "RIOT", "SERG");
        addData("17.09.2021", "YOU", "VACATION");
        addData("12.12.2021", "SERG", "AMBER (FEAT. RYO)");
        addData("05.11.2022", "BAMBAM", "VACATION (FEAT. LUGA)");
        addData("06.05.2022", "PRETEND", "KILL JASPER");
        addData("07.06.2020", "WYM?", "MALACHII");
        addData("17.10.2021", "FIRE EMOJI III", "YB");
        addData("28.10.2019", "Devil", "$LOTHBOI ");
        addData("27.07.2019", "Thirst Trap", "ZaddyWithABaddy");
        addData("22.10.2022", "Snowing", "Mahaji");
        addData("11.11.2021", "Lost", "Amni");
        addData("26.12.2021", "Gear", "SoFaygo");
        addData("29.04.2022", "lonely vacation", "ty lorenzo");
        addData("19.05.2020", "Batman", "Delivery Boys");
        addData("19.05.2020", "Dreams", "Yacobucci");
        addData("14.05.2020", "Don't Lie to Me", "Wett Brain");
        addData("11.10.2021", "M3", "kiLLa Laharl");
        addData("22.11.2022", "Landslide", "Sleepa");
        addData("25.06.2019", "fax or nah??", "93FEETOFSMOKE");
        addData("26.04.2022", "ABRAKADABRA", "Sinoda");
        addData("05.01.2019", "Expiration", "YCK");
        addData("24.08.2022", " One Call Aw", "Jeffrey");
        addData("14.09.2022", "MAX", "Sinizter");
        addData("06.10.2019", "As Above, So Below", "Tumaggz");
        addData("18.11.2022", "Living the Dream", "Expiration");
        addData("19.05.2022", "I NEED U", "Cairn");
        addData("15.06.2022", "Virtual World", "Lucus");
        addData("07.03.2022", "Fall", "Lord Gasp");
        addData("16.04.2022", "Dream", "Lord Gasp");
        addData("15.01.2022", "Saitama", "Monto");
        addData("25.05.2022", "ARIZONA", "contradash");
        addData("29.06.2022", "white lie", "Underd0g");
        addData("17.12.2022", "Dancin' In The Moonlight", "FULMETALPARKA");
        addData("15.12.2020", "Gym Leader Interlude", "MAX");
        addData("12.11.2021", "stay young", "good gasoline");
        addData("03.04.2021", "Hollywood Party", "iON LIL GUT");
        addData("04.03.2022", "Text Back No Way", "Faceless");
        addData("05.09.2022", "trying", "midwxst");
        addData("06.08.2020", "BARRI", "EDDIE FRESCO");
        addData("15.09.2022", "DOPEHEAD", "STARATS");
        addData("18.07.2019", "U Don't Even Know M3", "Jakkyboi");
        addData("29.05.2019", "L.A. Shawty", "RainingOnRoses");
        addData("24.04.2022", "HEAVY METAL", "Paris Texas");
        addData("25.10.2020", "anxiety", "MEANFACE");
        addData("23.06.2021", "Gravity", "YouSane");
    }

    private void addData(String sDate1, String name, String author){
        Date date = null;
        try {
            date = new SimpleDateFormat("dd.MM.yyyy").parse(sDate1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Composition composition = new Composition();
        composition.setUploadDate(date);
        composition.setName(name);
        composition.setAuthor(author);
        compositions.add(composition);
        this.size++;
    }
}

package com.interpixel.wibumeter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button calculate, apaini;
    TextView wibuPower, animeName;
    EditText name, tanggalLahir;
    Calendar kalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculate = (Button) findViewById(R.id.wibubutton);
        wibuPower = (TextView) findViewById(R.id.wibupower);
        name =  (EditText) findViewById(R.id.Name);
        tanggalLahir = (EditText) findViewById(R.id.TanggalLahir);
        apaini = (Button) findViewById(R.id.apaini);
        animeName = (TextView) findViewById(R.id.namaAnime);

        kalender = Calendar.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            tanggalLahir.setShowSoftInputOnFocus(false);
        }

        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                kalender.set(Calendar.YEAR, year);
                kalender.set(Calendar.MONTH, month);
                kalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);

                tanggalLahir.setText(sdf.format(kalender.getTime()));
            }
        };

        tanggalLahir.setOnClickListener(new EditText.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this,datePicker, 2000, 1, 1).show();
            }
        });

        tanggalLahir.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    new DatePickerDialog(MainActivity.this,datePicker, 2000, 1, 1).show();
                }
            }
        });

        calculate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = name.getText().toString().toLowerCase().trim();
                String tanggal = tanggalLahir.getText().toString().toLowerCase().trim();

                if(nama.isEmpty() || tanggal.isEmpty()){
                    Toast.makeText(getBaseContext(),"Silahkan isi data", Toast.LENGTH_SHORT).show();
                }else{
                    String wibuLevel = calculateWibuPower(nama, tanggal);
                    wibuPower.setText(wibuLevel);
                    animeName.setText(getAnime(Integer.parseInt(wibuLevel)));
                }

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null){
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        apaini.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, About.class));
            }
        });
    }

    private String calculateWibuPower(String name, String date){
        byte[] hash = null;
        String base = name + date + "WIBU";
        int[] angka = new int[4];
        try {
            MessageDigest digestor = MessageDigest.getInstance("SHA-256");
            hash = digestor.digest(base.getBytes(StandardCharsets.UTF_8));
            angka[0] = hash[1];
            angka[1] = hash[7];
            angka[2] = hash[9];
            angka[3] = hash[4];

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < angka.length; i++){
            angka[i] = Math.abs(angka[i]);
            angka[i] = angka[i] % 10;
        }
        String result = "" + angka[0] + angka[1] + angka[2] + angka[3];
        return result;
    }

    private String getAnime(int wibuLevel){
        String animeName = "";

        String[][] anime = new String[10][];

        anime[0] = new String[]{
                "Naruto",
                "Dragon Ball",
                "Doraemon",
                "Beyblade",
                "Yu-Gi-Oh!",
                "Fairy Tail",
                "Chibi Maruko chan",
                "One Punch Man",
                "Sengoku Basara",
                "Cardfight Vanguard!",
                "Future Card Buddyfight",
                "Cowboy Bebop",
                "Space Dandy",
                "JoJo Bizarre Advanture",
                "Tokyo Ravens",
                "InuYasha",
                "Fullmetal Alchemist"
        };

        anime[1] = new String[]{
                "One Piece",
                "Sailor Moon",
                "Saint Seiya",
                "Meitantei Conan",
                "Mobile Suit Gundam",
                "Bleach",
                "Slam Dunk",
                "Gintama",
                "Danshi Koukousei no Nichijou",
                "Black Clover",
                "Project K",
                "Himouto! Umaru-chan",
                "InuxBoku SS",
                "Non Non Biyori",
                "Noragami",
                "Tamako Market",
                "Nurarihyon no Mago",
                "Shingeki no Kyojin",
                "Soul Eater",
                "Gun Gale Online",
                "Nisekoi",
                "Hajime no Ippo",
                "Great Teacher Onizuka"

        };

        anime[2] = new String[]{
                "Gakuen Toshi Asterisk",
                "HunterxHunter",
                "Cyborg Kuro Chan",
                "Code Geass",
                "Black Bullet",
                "Eyeshield 21",
                "Eleven Eyes",
                "Nanatsu no Taizai",
                "Tokyo Ghoul",
                "Black Rock Shooter",
                "Sword Art Online",
                "Fate Series",
                "SKET Dance",
                "Kiseijuu",
                "Handa-kun",
                "Sakamoto desu-ga?",
                "Shokugeki no Souma",
                "Kill Me Baby",
                "Log Horizon",
                "Lucky Star",
                "No Game no Life",
                "Tonari no Seki-kun",
                "Chihayafuru",
                "Durarara!!",
                "Aldnoah Zero",
                "Samurai 7",
                "Tegamibachi",
                "Clockwort Planet",
                "Akagami no Shirayuki-hime"
        };

        anime[3] = new String[]{
                "Monogatari Series",
                "Chaos;Child",
                "Robotics;Notes",
                "Beelzebub",
                "Akuma no Riddle",
                "Danganronpa",
                "Hanebado!",
                "Prince of Tennis",
                "Ansatsu Kyoushitsu",
                "Ao no Exorcist",
                "Death Parade",
                "Kuroshitsuji",
                "Golden Time",
                "Ajin",
                "Guilty Crown",
                "Shigatsu wa Kimi no Uso",
                "Hyouka",
                "Inuyashiki",
                "Occultic;Nine",
                "3D Kanojo: Real Girl",
                "Kyoukai no Kanata",
                "Mahouka Koukou no Rettousei",
                "Nanbaka",
                "Yowamushi Pedal",
                "Plastic Memories",
                "The iDOLM@STER",
                "Working!!",
                "Yuru Yuri",
                "Ore Monogatari!!",
                "Violet Evergarden",
                "Bungou Stray Dogs"
        };

        anime[4] = new String[]{
                "Haikyuu!",
                "Steins;Gate",
                "Chaos;Head",
                "Boku no Hero Academia",
                "Death Note",
                "Star Driver",
                "White Album",
                "91 Days",
                "Acchi Kocchi",
                "Kuroko no Basuke",
                "Aoki Hagane no Arpeggio",
                "Rakudai Kishi no Cavalry",
                "Boku Dake ga Inai Machi",
                "Elfen Lied",
                "Gamers",
                "Glasslip",
                "Gosick",
                "Hayate no Gotoku!",
                "Higurashi no Naku Koro ni",
                "K-On!",
                "Kakegurui",
                "Kitakubu Katsudou Kiroku",
                "Ballroom e Youkoso",
                "Kono Subarahshi Sekai ni Shukufuku wo!",
                "Love Live!",
                "Mangaka-san to Assistant-san to",
                "Mayo Chiki!",
                "Nichijou",
                "Psycho-Pass",
                "Rail Wars",
                "Re:Zero kara Hajimeru Isekai Seikatsu",
                "Shinryaku! Ika Musume",
                "Suzumiya Haruhi no Yuutsu",
                "Toradora",
                "Wotaku ni Koi wa Muzukashii",
                "Yahari Ore no Seishun Love Comedy wa Machigatteiru",
                "Serial Experiment Lain"
        };

        anime[5] = new String[]{
                "Free! Eternal Summer",
                "Harukana Receive",
                "Hataraku Saibou",
                "To aru Kagaku no Railgun",
                "To aru Majutsu no Index",
                "Kokoro Connect",
                "Orange",
                "Infinite Stratos",
                "Byousoku 5cm",
                "Koe no Katachi",
                "Nagi no Asukara",
                "Tantei Opera Milky Homes",
                "Yuri on Ice",
                "Another",
                "Blend S",
                "Charlotte",
                "Code:Breaker",
                "Dagashi Kashi",
                "Devil Survivor",
                "Gakkougurashi",
                "Gekkan Shoujo Nozaki-kun",
                "Girls und Panzer",
                "Ishuukan Friend",
                "Kaichou wa Maid Sama",
                "Kotoura-san",
                "Maoyuu Maou Yuusha",
                "Mirai Nikki",
                "New Game",
                "Yamada-Kun to Nananin no Majou",
                "Spirited Away",
                "Ore no Kanojo to Osananajimi ga Shurana Sugiru",
                "Ore, Twintail ni Narimasu",
                "Saki"
        };

        anime[6] = new String[]{
                "Binan Koukou Chikyuu Bou",
                "Keijo!!!!!!!!",
                "Angel Beats",
                "Ore no Imouto wa Konnani Kawaii Wake ga nai",
                "Clannad",
                "Darling in The Franxx",
                "Jikan wo Kakeru Shoujo",
                "Kimi no Na wa",
                "Accel World",
                "Akame ga Kill!",
                "Boku wa Tomodachi ga Sukunai",
                "Sakurasou no Pet na Kanojou",
                "Chunnibyou demo Koi ga Shitai!",
                "Date a Live",
                "Denpa-teki na Kanojo",
                "Air Gear",
                "Gabriel Dropout",
                "Back Street Girls: Gokudolls",
                "Haiyore! Nyaruko San",
                "Hataraku Maou-sama!",
                "Hentai Ouji to Warawanai Neko",
                "Jinsei",
                "Kami nomi zo Shiru Sekai",
                "Kobayashi-san Chi no Maid Dragon",
                "Love Lab",
                "Magi",
                "Ousama Game",
                "Ouran Koukou Host Club",
                "Ranpo Kitan",
                "Sabage-bu!",
                "Saenai Heroine no Sodetaka",
                "Seitokai no Ichizon",
                "Seitokai Yakuindomo",
                "Servant x Service",
                "Shimoneta to Iu Gainen ga Sonzai Shinai Taikutsu na Sekai",
                "Shinsekai Yori",
                "Dog Days",
                "Satsuriku no Tenshi"
        };

        anime[7] = new String[]{
                "Isekai Maou to Shoukan Shoujo no Dorei Majutsu",
                "Dungeon ni Deai wo Motomeru no wa Machigatteiru Darou ka?",
                "Absolute Duo",
                "Ano hi Mita Hana no Namae wo Bokutachi wa Mada Shiranai",
                "Photo Kano",
                "Secret Garden",
                "Trinity Seven",
                "Seven Deadly Sins",
                "Asobi ni Iku yo!",
                "Baka to Test",
                "Btooom!",
                "Rosario Vampire",
                "Corpse Party",
                "Hoshizora e Kakaru Hashi",
                "Imouto sae Ireba Ii",
                "Isuca",
                "Ixion Saga DT",
                "Kono Naka ni Hitori, Imouto ga Iru",
                "Kyou no 5 no 2",
                "Machine-Doll wa Kizutsukanai",
                "Mahou Sensou",
                "Mahou Shoujo Madoka Magica",
                "Mayoi Neko Overrun",
                "Nekopara",
                "Nazo no Kanojo X",
                "Net-juu no Susume",
                "Netogame no Yome wa Onnanoko ja Nai to Omotta?",
                "Papa no Iukoto wo Kikinasai!",
                "Rokujouma no Shinryakusa!?",
                "Sankarea",
                "Super Sonico",
                "Sora no Otoshimono",
                "Strike the Blood"
        };

        anime[8] = new String[]{
                "Highschool DxD",
                "Gochuumon wa Usagi Desuka?",
                "To-Love Ru",
                "B-gata H-kei",
                "Kampfer",
                "Boku no Kanojo ga Majimesugiru Sho-bitch na Ken",
                "C^3",
                "Citrus",
                "Highschool of The Dead",
                "Dakara Boku wa, H ga Dekinai",
                "Eromanga Sensei",
                "Freezing",
                "Green Green",
                "Himegoto",
                "Hyakka Ryouran",
                "Kiss x Sis",
                "Koe de Oshigoto!",
                "Kuzu no Honkai",
                "Kuusen Madoushi Kouhosei no Kyoukan",
                "Maji de Watashi ni Koi Shinasai!",
                "Monster Musume no iru Nichijou",
                "Musaigen no Phantom World",
                "Okusama ga Setokaichou",
                "Oda Nobuna no Yabou",
                "Oniichan dakedo Ai sae Areba Kankenai yo ne!",
                "Prison School",
                "Ryuuou no Oshigoto!",
                "Saikin, Imouto no Yousu ga Chotto Okashiinda ga",
                "School Days",
                "Upotte!!",
                "Walkure Romanze"
        };

        anime[9] = new String[]{
                "Yosuga no Sora",
                "Boku no Pico",
                "Shinmai Maou no Testament",
                "Aki Sora Yume no Naka",
                "Drop-Out",
                "Oideyo, Mizuru Kei-Land",
                "Mujaki na Rakuen",
                "Euphoria",
                "Mankitsu Happening",
                "Harem Time",
                "Bikini Warriors",
                "Bible Black",
                "Futabu",
                "Kodomo no Jikan",
                "Ladies vs Butlers",
                "Maken-Ki",
                "Imouto Paradise",
                "Nagasarete Airantou",
                "Netsuzou TRap",
                "Ro-Kyu-Bu",
                "Yuusha ni Narenakatta Ore wa Shibushibu Shuushoku wo Ketsui Shimashita",
                "Ikkitousen",
                "Discipline",
                "Boku dake no Hentai Kanojo",
                "Hentai Kansoku",
                "Oni Chichi",
                "Sensitive Pornograph",
                "Eroge! H mo Game wa Kaihatsu Zanmai",
                "Princess Lover!",
                "KanojoxKanojoxKanojo",
                "Hatsu Inu",
                "Kuroinu",
                "HHH Triple Ecchi",
                "Gakuen de Jikan yo Tomare",
                "Baku Ane",
                "Kyonyuu Fantasy",
                "Otome Dori",
                "Resort Boin",
                "Nee Summer!",
                "Fella Pure",
                "Toshi Densetsu",
                "Akibakei Kanojo",
                "Aniyome wa Ijippari",
                "Taimanin Asagi",
                "Aneimo",
                "TSF Monogatari",
                "JK Bitch ni Shiboraretai",
                "Cleavage",
                "Boy Meets Harem",
                "Yakin Byoutou",
                "Ojousama wa H ga Osuki",
                "Bakunyuu Oyako",
                "Kimekishi Lilia",
                "Milk Junkie: Shinmai-hen",
                "Energy Kyouka",
                "Chichiiro Toiko",
                "Gakuen Saimin",
                "Kangoku Senkan",
                "Nudist Bitch ni Shuugaku Ryoukou de!!",
                "Tamashii Insert",
                "Genkaku Cool na Sensei ga Aheboteochi",
                "Oppai Life",
                "Rin x Sen",
                "Rape! Rape! Rape!",
                "Fella Hame",
                "Youkoso! Sukebe Elf no Mori e",
                "Ai no Katachi",
                "Rinkan Club"
        };

        int segment = wibuLevel / 1000;
        int indeks = wibuLevel % anime[segment].length;
        animeName = anime[segment][indeks];

        return animeName;
    }
}



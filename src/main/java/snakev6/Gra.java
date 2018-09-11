package snakev6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.Timer;
import static snakev6.Snakev6.managerOkien;

public class Gra extends JPanel implements ActionListener {

    public static final int DLUGOSCX = 560;
    public static final int DLUGOSCY = 460;
    public static final int SZEROKOSC_CALEGO_OKNA = 640;
    public static final int WYSOKOSC_CALEGO_OKNA = 650;
    public static final int MARGINES_LEWY = 40;
    public static final int MARGINES_GORNY_TYTUL = 10;
    public static final int MARGINES_GORNY_POLEGRY = 100;
    private static final int SZEROKOSC_PRZYCISKU = 70;
    private static final int WYSOKOSC_PRZYCISKU = 30;
    public static final int WYSOKOSC_OBRAZKA_TYTULU = 60;
    public static final int PROG_PIERWSZY_BOMBA = 5;//mam dwie bomby po jego przekorczeniu
    public static final int PROG_DRUGI_BOMBA = 10;//mam 3 bomby po jego przekorczeniu
    public static final int PROG_TRZECI_BOMBA = 15;//mam 4 bomby po jego przekorczeniu
    public static final int PROG_CZWARTY_BOMBA = 20;//mam 5 bomb po jego przekorczeniu

    private static int szerokosc_obrazka_glowy;
    private static int wysokosc_obrazka_glowy;

    private static int szer_jablka;
    private static int wys_jablka;

    private Image nazwa;
    private Image glowa;
    private Image czlon;
    private Image jablko;
    private Image bomba;
    private Image glowaGora, glowaDol, glowaPrawo, glowaLewo;
    public static Timer zegar;
    public static int szybkoscporuszania = 160;

    public static boolean lewo = false;
    public static boolean prawo = false;
    public static boolean gora = false;
    public static boolean dol = false;

    public static boolean poczatekGry = true;

    public static int pozycja_x_weza = DLUGOSCX / 2 + MARGINES_LEWY;
    public static int pozycja_y_weza = ((DLUGOSCY / 2) + MARGINES_GORNY_POLEGRY) - 10;

    public int polozenie_x_jablka;
    public int polozenie_y_jablka;

    public int polozenie_x_bomby;
    public int polozenie_y_bomby;

    public static int stopnie;

    private static Rectangle glowaKontur, czlonKontur, bombaKontur;

    public static int liczba_czlonow = 1;
    public static ArrayList<Integer> wspolrzedneX_czlonow = new ArrayList<>();
    public static ArrayList<Integer> wspolrzedneY_czlonow = new ArrayList<>();

    public static int[] wspolrzedneX_bomby = new int[5];
    public static int[] wspolrzedneY_bomby = new int[5];

    private boolean stanWyciszeniaGry = false;

    private JButton pause, start, zamknij, wroc, restart, wycisz;
    private JPanel panelNaPrzyciski;
    private JLabel wynik;

    public Gra() {
        zegar = new Timer(szybkoscporuszania, this);

        przygotuj_plansze();
        tworzPrzyciski();
        add(panelNaPrzyciski);
        tabelaZWynikiem();
        add(wynik);
    }

    private void tabelaZWynikiem() {

        wynik = new JLabel("Twój wynik to:   " + (liczba_czlonow - 1));
        wynik.setBounds(SZEROKOSC_CALEGO_OKNA - MARGINES_LEWY * 4, MARGINES_GORNY_TYTUL + WYSOKOSC_OBRAZKA_TYTULU, MARGINES_LEWY * 3, MARGINES_GORNY_POLEGRY - MARGINES_GORNY_TYTUL - WYSOKOSC_OBRAZKA_TYTULU);

    }

    private void tworzPrzyciski() {

        panelNaPrzyciski = new JPanel();
        pause = new JButton();

        pause.setFocusable(false);
        pause.setText("Pauza");
        pause.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        pause.setPreferredSize(new Dimension(SZEROKOSC_PRZYCISKU, WYSOKOSC_PRZYCISKU));
        pause.addActionListener(this);

        start = new JButton();

        start.setFocusable(false);
        start.setText("Wznów");
        start.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        start.setPreferredSize(new Dimension(SZEROKOSC_PRZYCISKU, WYSOKOSC_PRZYCISKU));
        start.addActionListener(this);

        zamknij = new JButton("Zamknij");
        zamknij.setFocusable(false);
        zamknij.setPreferredSize(new Dimension(SZEROKOSC_PRZYCISKU, WYSOKOSC_PRZYCISKU));
        zamknij.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        zamknij.addActionListener(this);

        wroc = new JButton("Wróć");
        wroc.setFocusable(false);
        wroc.setText("Wróć");
        wroc.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        wroc.setPreferredSize(new Dimension(SZEROKOSC_PRZYCISKU, WYSOKOSC_PRZYCISKU));
        wroc.addActionListener(this);

        restart = new JButton("Restart");
        restart.setFocusable(false);
        restart.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        restart.setPreferredSize(new Dimension(SZEROKOSC_PRZYCISKU, WYSOKOSC_PRZYCISKU));
        restart.addActionListener(this);

        wycisz = new JButton("Wycisz");
        wycisz.setFocusable(false);
        wycisz.setFont(new Font("Times New Roman", Font.PLAIN, 10));
        wycisz.setPreferredSize(new Dimension(SZEROKOSC_PRZYCISKU, WYSOKOSC_PRZYCISKU));
        wycisz.addActionListener(this);

        panelNaPrzyciski.add(restart);
        panelNaPrzyciski.add(zamknij);
        panelNaPrzyciski.add(pause);
        panelNaPrzyciski.add(start);
        panelNaPrzyciski.add(wroc);
        panelNaPrzyciski.add(wycisz);
        panelNaPrzyciski.setVisible(true);
        panelNaPrzyciski.setBounds(50, (MARGINES_GORNY_POLEGRY + DLUGOSCY), 500, 40);

    }

    private void przygotuj_plansze() {
        addKeyListener(new Klawisze());
        zaladuj_grafiki();
        setFocusable(true);
        setDoubleBuffered(true);

        setPreferredSize(new Dimension(SZEROKOSC_CALEGO_OKNA, WYSOKOSC_CALEGO_OKNA));
        setLayout(null);
        przygotuj_gre();

    }

    private void przygotuj_gre() {
        losuj_pozycje_jablka();

        losuj_pozycje_bomby();
        wspolrzedneX_czlonow.add(0, pozycja_x_weza);
        wspolrzedneY_czlonow.add(0, pozycja_y_weza);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        rysuj_plansze_gry(g);
        if (sprawdzCzyDotknalesKrawedzi() || sprawdzKolizjeZCzlonem() || liczba_czlonow == 0) {
            try {
                if (stanWyciszeniaGry == false) {
                    new Music().koniecGryDzwiek();
                }
            } catch (FileNotFoundException ex) {
            }
            koniecGry(g);
            zegar.stop();
        }

        try {
            //      draw((Graphics2D)g);//rzutuje rysowanie grafiki2d na zwykle ktore mialem
            rysuj_weza(g);//poprzednia funkcja rysujaca weza
        } catch (IOException ex) {
            Logger.getLogger(Gra.class.getName()).log(Level.SEVERE, null, ex);
        }
        rysuj_bombe(g);
        rysuj_jablko(g);

    }

    private void rysuj_plansze_gry(Graphics g) {

        g.setColor(Color.WHITE);
        g.drawRect(MARGINES_LEWY, MARGINES_GORNY_TYTUL, DLUGOSCX, WYSOKOSC_OBRAZKA_TYTULU);//utworzenie prostokata w ktorym znajduje sie logo gry

        g.setColor(Color.white);//rysowanie prostokata w ktorym bedzie toczyla sie gra
        g.drawRect(MARGINES_LEWY, MARGINES_GORNY_POLEGRY, DLUGOSCX, DLUGOSCY);

        g.setColor(Color.BLACK); //wypelnienie kolorem czarnym pola gry
        g.fillRect(MARGINES_LEWY, MARGINES_GORNY_POLEGRY, DLUGOSCX, DLUGOSCY);

        g.drawImage(nazwa, MARGINES_LEWY, MARGINES_GORNY_TYTUL, DLUGOSCX, WYSOKOSC_OBRAZKA_TYTULU, this);//rysuje nazwe gry 

    }

    private void rysuj_weza(Graphics g) throws IOException {

        BufferedImage bimg = ImageIO.read(new File("glowa3.jpg"));


        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(stopnie), szerokosc_obrazka_glowy/2, wysokosc_obrazka_glowy/2);
        BufferedImageOp bio = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        bimg = bio.filter(bimg, null);
        g.drawImage(bimg, wspolrzedneX_czlonow.get(0), wspolrzedneY_czlonow.get(0), this);

        if (sprawdzCzyDotknalesKrawedzi() == false) {
            for (int i = 0; i < liczba_czlonow; i++) {
                if (i == 0) {
                    g.drawImage(bimg, wspolrzedneX_czlonow.get(0), wspolrzedneY_czlonow.get(0), this);

                } else {
                    g.drawImage(czlon, wspolrzedneX_czlonow.get(i), wspolrzedneY_czlonow.get(i), this);
                }
            }
        }
    }

    private void rysuj_jablko(Graphics g) {
        g.drawImage(jablko, polozenie_x_jablka, polozenie_y_jablka, this);

    }

    private void rysuj_bombe(Graphics g) {

        if (liczba_czlonow - 1 < PROG_PIERWSZY_BOMBA) {
            g.drawImage(bomba, wspolrzedneX_bomby[0], wspolrzedneY_bomby[0], this); // rysuje 1 bombe
        } else if (liczba_czlonow - 1 < PROG_DRUGI_BOMBA) {
            for (int i = 0; i < 2; i++) {
                g.drawImage(bomba, wspolrzedneX_bomby[i], wspolrzedneY_bomby[i], this); //rysuje 2 bombe
            }
        } else if (liczba_czlonow - 1 < PROG_TRZECI_BOMBA) {
            for (int i = 0; i < 3; i++) {
                g.drawImage(bomba, wspolrzedneX_bomby[i], wspolrzedneY_bomby[i], this); // rysuje 3 bomby
            }

        } else if (liczba_czlonow - 1 < PROG_CZWARTY_BOMBA) {
            for (int i = 0; i < 4; i++) {
                g.drawImage(bomba, wspolrzedneX_bomby[i], wspolrzedneY_bomby[i], this);//rysuje 4 bomby
            }
        } else if (liczba_czlonow - 1 >= PROG_CZWARTY_BOMBA) {
            for (int i = 0; i < 5; i++) {
                g.drawImage(bomba, wspolrzedneX_bomby[i], wspolrzedneY_bomby[i], this);//rysuje 5 bomby
            }
        }

    }

    private void losuj_pozycje_jablka() {
        boolean czyPozycjaCzlonu = false;
        Random losuj = new Random();
        int x = losuj.nextInt(MARGINES_LEWY + DLUGOSCX);
        int y = losuj.nextInt(MARGINES_GORNY_POLEGRY + DLUGOSCY);

        for (int i = 0; i < wspolrzedneX_czlonow.size(); i++) {
            if (x == wspolrzedneX_czlonow.get(i) && y == wspolrzedneY_czlonow.get(i)) {
                czyPozycjaCzlonu = true;
            }

        }

        if ((x > MARGINES_LEWY && x < MARGINES_LEWY + DLUGOSCX)
                && (y > MARGINES_GORNY_POLEGRY && y < MARGINES_GORNY_POLEGRY + DLUGOSCY)
                && (x != pozycja_x_weza && y != pozycja_y_weza)
                && (x % szerokosc_obrazka_glowy == 0 && y % wysokosc_obrazka_glowy == 0) && czyPozycjaCzlonu == false) {
            polozenie_x_jablka = x;
            polozenie_y_jablka = y;
        } else {
            losuj_pozycje_jablka();
        }

    }

    private void losuj_pozycje_bomby() {

        Random losuj = new Random();
        int i = 0, k = 0;
        while (i < 5) {
            boolean pozycja_czlonu = false;
            int x = losuj.nextInt(MARGINES_LEWY + DLUGOSCX);
            for (int l = 0; l < 5; l++) {
                if (x == wspolrzedneX_bomby[l]) {
                    pozycja_czlonu = true;
                }
            }

            if ((x > MARGINES_LEWY && x < MARGINES_LEWY + DLUGOSCX)
                    && (x != pozycja_x_weza)
                    && (x % szerokosc_obrazka_glowy == 0)
                    && pozycja_czlonu == false) {
                wspolrzedneX_bomby[i] = x;
                i++;
            }

        }

        while (k < 5) {
            int y = losuj.nextInt(MARGINES_GORNY_POLEGRY + DLUGOSCY);
            boolean pozycja_czlonu = false;
            for (int l = 0; l < 5; l++) {
                if (y == wspolrzedneY_bomby[l]) {
                    pozycja_czlonu = true;
                }
            }

            if ((y > MARGINES_GORNY_POLEGRY && y < MARGINES_GORNY_POLEGRY + DLUGOSCY)
                    && (y != pozycja_y_weza)
                    && (y % wysokosc_obrazka_glowy == 0)
                    && pozycja_czlonu == false) {

                wspolrzedneY_bomby[k] = y;
                k++;
            }
        }

    }

    private void zjedzenie_bomby() {
        int opcja = 0;
        if (liczba_czlonow - 1 < PROG_PIERWSZY_BOMBA) {
            opcja = 1;
        } else if (liczba_czlonow - 1 < PROG_DRUGI_BOMBA) {
            opcja = 2;
        } else if (liczba_czlonow - 1 < PROG_TRZECI_BOMBA) {
            opcja = 3;
        } else if (liczba_czlonow - 1 < PROG_CZWARTY_BOMBA) {
            opcja = 4;
        } else if (liczba_czlonow - 1 >= PROG_CZWARTY_BOMBA) {
            opcja = 5;
        }

        switch (opcja) {
            case 1:
                bombaKontur = new Rectangle(wspolrzedneX_bomby[0], wspolrzedneY_bomby[0], szer_jablka, wys_jablka);
                if (bombaKontur.intersects(glowaKontur)) {
                    odejmowanie_czlonow();
                }
                break;

            case 2:
                for (int i = 0; i < 2; i++) {
                    bombaKontur = new Rectangle(wspolrzedneX_bomby[i], wspolrzedneY_bomby[i], szer_jablka, wys_jablka);
                    if (bombaKontur.intersects(glowaKontur)) {
                        odejmowanie_czlonow();
                    }
                }
                break;
            case 3:
                for (int i = 0; i < 3; i++) {
                    bombaKontur = new Rectangle(wspolrzedneX_bomby[i], wspolrzedneY_bomby[i], szer_jablka, wys_jablka);
                    if (bombaKontur.intersects(glowaKontur)) {
                        odejmowanie_czlonow();
                    }
                }
                break;
            case 4:
                for (int i = 0; i < 4; i++) {
                    bombaKontur = new Rectangle(wspolrzedneX_bomby[i], wspolrzedneY_bomby[i], szer_jablka, wys_jablka);
                    if (bombaKontur.intersects(glowaKontur)) {
                        odejmowanie_czlonow();
                    }

                }
                break;
            case 5:
                for (int i = 0; i < 5; i++) {
                    bombaKontur = new Rectangle(wspolrzedneX_bomby[i], wspolrzedneY_bomby[i], szer_jablka, wys_jablka);
                    if (bombaKontur.intersects(glowaKontur)) {
                        odejmowanie_czlonow();
                    }
                }
                break;
        }

    }

    private void odejmowanie_czlonow()//odejmowanie czlonow po zjedzeniu bomby i aktualizacja wyniku oraz wywolyawnie dzwiekow zjedzenia bomby
    {
        liczba_czlonow -= 1;
        wynik.setText("Twój wynik to:  " + (liczba_czlonow - 1));
        losuj_pozycje_bomby();
        if ((liczba_czlonow - 1 != 0) && (liczba_czlonow - 1) % 10 == 0) {
            zegar.setDelay(szybkoscporuszania += 20);
        }

        try {
            if (stanWyciszeniaGry == false) {
                new Music().zjedzenieBomby();
            }
        } catch (FileNotFoundException ex) {
        }

    }

    private void zjedzenie_jablka() {
        if (sprawdzCzyZjadlesJablko()) {

            if ((liczba_czlonow - 1 != 0) && (liczba_czlonow - 1) % 10 == 0) {

                zegar.setDelay(szybkoscporuszania -= 20);

            }
            for (int i = 1; i < liczba_czlonow; i++) {

                wspolrzedneX_czlonow.add(i, wspolrzedneX_czlonow.set(i - 1, wspolrzedneX_czlonow.get(i - 1)));
                wspolrzedneY_czlonow.add(i, wspolrzedneY_czlonow.set(i - 1, wspolrzedneY_czlonow.get(i - 1)));
            }
            if (lewo) {
                wspolrzedneX_czlonow.set(0, wspolrzedneX_czlonow.get(0) - szerokosc_obrazka_glowy);
            }
            if (prawo) {
                wspolrzedneX_czlonow.set(0, wspolrzedneX_czlonow.get(0) + szerokosc_obrazka_glowy);
            }
            if (dol) {
                wspolrzedneY_czlonow.set(0, wspolrzedneY_czlonow.get(0) + wysokosc_obrazka_glowy);
            }
            if (gora) {
                wspolrzedneY_czlonow.set(0, wspolrzedneY_czlonow.get(0) - wysokosc_obrazka_glowy);
            }
            losuj_pozycje_bomby();
            losuj_pozycje_jablka();
        }

    }

    private boolean sprawdzCzyZjadlesJablko() {

        glowaKontur = new Rectangle(wspolrzedneX_czlonow.get(0), wspolrzedneY_czlonow.get(0), szerokosc_obrazka_glowy, wysokosc_obrazka_glowy);

        Rectangle jablkoPolozenie1 = new Rectangle(polozenie_x_jablka, polozenie_y_jablka, szer_jablka, wys_jablka);
        if (jablkoPolozenie1.intersects(glowaKontur)) {
            liczba_czlonow += 1;
            wynik.setText("Twój wynik to:  " + (liczba_czlonow - 1));

            try {
                if (stanWyciszeniaGry == false) {
                    new Music().zjedzenieJablka();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Gra.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        } else {
            return false;
        }

    }

    private boolean sprawdzKolizjeZCzlonem() {
        glowaKontur = new Rectangle(wspolrzedneX_czlonow.get(0), wspolrzedneY_czlonow.get(0), szerokosc_obrazka_glowy, wysokosc_obrazka_glowy);

        for (int i = 1; i < liczba_czlonow; i++) {
            czlonKontur = new Rectangle(wspolrzedneX_czlonow.get(i), wspolrzedneY_czlonow.get(i), szerokosc_obrazka_glowy, wysokosc_obrazka_glowy);
            if (czlonKontur.intersects(glowaKontur)) {

                return true;
            }

        }
        return false;
    }

    private void zapiszDoBazy() throws SQLException {
        if (sprawdzCzyDotknalesKrawedzi() || sprawdzKolizjeZCzlonem()) {
            String nick = JOptionPane.showInputDialog("                          Przegrałeś.\n Prosze podaj swój nick w celu zapisania wyniku");
            Wyniki.liczbaRekordówAktualnieZapisanych++;
            Wyniki.zapiszWynik(Wyniki.polaczenie, nick, liczba_czlonow - 1);

        }
    }

    private void koniecGry(Graphics g) {

        String wiadomosc = "Koniec Gry. Przegrales";

        g.setColor(Color.red);
        g.drawString(wiadomosc, 300, 300);

    }

    private boolean sprawdzCzyDotknalesKrawedzi() {

        if (wspolrzedneX_czlonow.get(0) > (DLUGOSCX + MARGINES_LEWY - szerokosc_obrazka_glowy)//sprawdzenie krawedzi prawej pola gry
                || wspolrzedneX_czlonow.get(0) < (MARGINES_LEWY)//sprawdzenie krawedzi lewej pola gry
                || wspolrzedneY_czlonow.get(0) < MARGINES_GORNY_POLEGRY//sprawdzenie krawedzi gornej pola gry
                || wspolrzedneY_czlonow.get(0) > MARGINES_GORNY_POLEGRY + DLUGOSCY - wysokosc_obrazka_glowy) {//sprawdzenie krawedzi dolnej pola gry
            return true;
        } else {
            return false;
        }
    }

    private void zaladuj_grafiki() {
        ImageIcon nazwapomoc = new ImageIcon("tytul.jpg");
        nazwa = nazwapomoc.getImage();
        ImageIcon pomocznicza = new ImageIcon("czlon2.jpg");
        czlon = pomocznicza.getImage();
        ImageIcon nazwapomoc2 = new ImageIcon("glowa3.jpg");
        glowa = nazwapomoc2.getImage();
        ImageIcon nazwapomoc3 = new ImageIcon("jablko2.jpg");
        jablko = nazwapomoc3.getImage();
        ImageIcon nazwapomoc4 = new ImageIcon("niejesc.jpg");
        bomba = nazwapomoc4.getImage();

        ImageIcon nazwapomoc5 = new ImageIcon("glowa3_dol.jpg");
        glowaDol = nazwapomoc5.getImage();

        ImageIcon nazwapomoc6 = new ImageIcon("glowa3_gora.jpg");
        glowaGora = nazwapomoc6.getImage();

        ImageIcon nazwapomoc7 = new ImageIcon("glowa3_prawo.jpg");
        glowaPrawo = nazwapomoc7.getImage();

        ImageIcon nazwapomoc8 = new ImageIcon("glowa3_lewo.jpg");
        glowaLewo = nazwapomoc8.getImage();

        szerokosc_obrazka_glowy = (glowa.getWidth(this));
        wysokosc_obrazka_glowy = (glowa.getHeight(this));
        szer_jablka = (jablko.getWidth(this));
        wys_jablka = (jablko.getHeight(this));
    }

    private void ruch() {

        for (int i = liczba_czlonow - 1; i > 0; i--) {

            wspolrzedneX_czlonow.set(i, wspolrzedneX_czlonow.get(i - 1));
            wspolrzedneY_czlonow.set(i, wspolrzedneY_czlonow.get(i - 1));

        }

        if (prawo && wspolrzedneX_czlonow.get(0) < (DLUGOSCX + MARGINES_LEWY)) {

            wspolrzedneX_czlonow.set(0, (wspolrzedneX_czlonow.get(0) + szerokosc_obrazka_glowy));
        }
        if (lewo && wspolrzedneX_czlonow.get(0) > MARGINES_LEWY - szerokosc_obrazka_glowy) {

            wspolrzedneX_czlonow.set(0, (wspolrzedneX_czlonow.get(0) - szerokosc_obrazka_glowy));
        }
        if (gora && wspolrzedneY_czlonow.get(0) > MARGINES_GORNY_POLEGRY - wysokosc_obrazka_glowy) {

            wspolrzedneY_czlonow.set(0, (wspolrzedneY_czlonow.get(0) - wysokosc_obrazka_glowy));
        }
        if (dol && wspolrzedneY_czlonow.get(0) < DLUGOSCY + MARGINES_GORNY_POLEGRY) {

            wspolrzedneY_czlonow.set(0, (wspolrzedneY_czlonow.get(0) + wysokosc_obrazka_glowy));
        }

    }

    private void restartGry() {
        przygotuj_gre();
        zegar.start();
        lewo = false;
        prawo = false;
        dol = false;
        gora = false;
        poczatekGry = true;
        liczba_czlonow = 1;
        szybkoscporuszania = 150;
        wynik.setText("Twój wynik to:  0");

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object przycisk = e.getSource();
        if (przycisk == pause) {
            Gra.zegar.stop();
        }
        if (przycisk == wroc) {

            if (zegar.isRunning() == false) {
                try {
                    zapiszDoBazy();
                } catch (SQLException ex) {
                }
            }

            managerOkien.show(Snakev6.oknoMieszczaceInne, "powitanie");

        }
        if (przycisk == zamknij) {
            System.exit(0);

        }
        if (przycisk == wycisz) {

            if (stanWyciszeniaGry == false) {
                stanWyciszeniaGry = true;

            } else {
                stanWyciszeniaGry = false;

            }

        }
        if (przycisk == start) {
            Gra.zegar.start();

        }

        if (przycisk == restart) {

            if (zegar.isRunning() == false) {
                try {
                    zapiszDoBazy();
                } catch (SQLException ex) {
                }
            }
            restartGry();

        }

        ruch();
        zjedzenie_bomby();
        zjedzenie_jablka();
        repaint();

    }

}

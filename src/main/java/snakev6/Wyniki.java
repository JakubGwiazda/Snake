package snakev6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static snakev6.Gra.DLUGOSCX;
import static snakev6.Gra.MARGINES_GORNY_TYTUL;
import static snakev6.Gra.MARGINES_LEWY;
import static snakev6.Gra.WYSOKOSC_OBRAZKA_TYTULU;
import snakev6.Snakev6;
import snakev6.Snakev6;
import static snakev6.Snakev6.managerOkien;

public class Wyniki extends JPanel implements ActionListener {

    private static int szerokoscPrzycisku = 180;
    private static int wysokoscPrzycisku = 30;
    private static int ilosc_wierszy = 16;
    private static int ilosc_kolumn = 3;
    public static int liczbaRekordówAktualnieZapisanych = 0;
    private JButton wroc, kasuj, zamknij, sortujRosnaco, sortujMalejaco;
    private JPanel panelZPrzyciskami, panelZTablicaWynikow;
    private JTable  tabelaWynikow;
    private static DefaultTableModel modelTabeli;
    public static Connection polaczenie;
    public static ResultSet wynik;

    public Wyniki() throws SQLException {

        this.setLayout(null);

        tworzPrzyciskiWynik();

        this.add(panelZPrzyciskami);
        this.setBackground(Color.BLUE);

        tworzTabeleWynikow();
        this.add(panelZTablicaWynikow);

    }

    public void tworzTabeleWynikow() {

        Dimension wymiarTabeli = new Dimension(300, 350);

        modelTabeli = new DefaultTableModel(ilosc_wierszy, ilosc_kolumn);

        panelZTablicaWynikow = new JPanel();
        panelZTablicaWynikow.setVisible(true);
        panelZTablicaWynikow.setBounds(MARGINES_LEWY, MARGINES_GORNY_TYTUL + WYSOKOSC_OBRAZKA_TYTULU + 100, wymiarTabeli.width, wymiarTabeli.height);
        tabelaWynikow = new JTable();
        tabelaWynikow.setModel(modelTabeli);
        tabelaWynikow.setRowHeight(20);
        tabelaWynikow.setPreferredSize(wymiarTabeli);
        modelTabeli.setValueAt("Lp.", 0, 0);
        modelTabeli.setValueAt("Nick", 0, 1);
        modelTabeli.setValueAt("Liczba punktów", 0, 2);
        for (int i = 1; i <= 15; i++) {
            modelTabeli.setValueAt(i, i, 0);
        }

        tabelaWynikow.setEnabled(false);
        tabelaWynikow.setVisible(true);

        panelZTablicaWynikow.add(tabelaWynikow);

    }

    public void tworzPrzyciskiWynik() {
        panelZPrzyciskami = new JPanel();

        Font czcionka = new Font("Times New Roman", Font.PLAIN, 10);
        Dimension rozmiar = new Dimension(szerokoscPrzycisku, wysokoscPrzycisku);
        wroc = new JButton("Wróć");
        kasuj = new JButton("Kasuj wyniki");
        zamknij = new JButton("Zamknij");
        sortujMalejaco = new JButton("Sortuj od najmniejszego wyniku");
        sortujRosnaco = new JButton("Sortuj od najwiekszego wyniku");

        wroc.setFont(czcionka);
        zamknij.setFont(czcionka);
        kasuj.setFont(czcionka);
        sortujMalejaco.setFont(czcionka);
        sortujRosnaco.setFont(czcionka);

        wroc.setPreferredSize(rozmiar);
        zamknij.setPreferredSize(rozmiar);
        kasuj.setPreferredSize(rozmiar);
        sortujMalejaco.setPreferredSize(rozmiar);
        sortujRosnaco.setPreferredSize(rozmiar);

        wroc.setFocusable(false);
        zamknij.setFocusable(false);
        kasuj.setFocusable(false);
        sortujMalejaco.setFocusable(false);
        sortujRosnaco.setFocusable(false);

        wroc.addActionListener(this);
        kasuj.addActionListener(this);
        zamknij.addActionListener(this);
        sortujMalejaco.addActionListener(this);
        sortujRosnaco.addActionListener(this);

        panelZPrzyciskami.add(kasuj);
        panelZPrzyciskami.add(sortujMalejaco);
        panelZPrzyciskami.add(sortujRosnaco);
        panelZPrzyciskami.add(wroc);
        panelZPrzyciskami.add(zamknij);
        panelZPrzyciskami.setBounds(DLUGOSCX - szerokoscPrzycisku, MARGINES_GORNY_TYTUL + WYSOKOSC_OBRAZKA_TYTULU + szerokoscPrzycisku, szerokoscPrzycisku + 20, wysokoscPrzycisku * 6);
        panelZPrzyciskami.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.drawRect(MARGINES_LEWY, MARGINES_GORNY_TYTUL, DLUGOSCX, WYSOKOSC_OBRAZKA_TYTULU);//utworzenie prostokata w ktorym znajduje sie logo gry
        g.drawImage(Gra.getNazwa(), MARGINES_LEWY, MARGINES_GORNY_TYTUL, DLUGOSCX, WYSOKOSC_OBRAZKA_TYTULU, this);//rysuje nazwe gry 

    }

    public static void bazaDanych() throws SQLException {
        polaczenie = DriverManager.getConnection("jdbc:sqlite:wyniki.db");
        Statement statment = polaczenie.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS wynikiGry (\n"
                + "	Lp INTEGER PRIMARY KEY AUTOINCREMENT ,\n"
                + "	Nick text ,\n"
                + "	Punkty integer,\n"
                + "     Data TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");";

        statment.execute(sql);


    }

    public static void zapiszWynik(Connection polaczenie, String nick, int liczbaPunktow) throws SQLException {

        String sqlSprawdzenieLiczbyRekordow = " select count(*) from wynikiGry";

        PreparedStatement zapytanie = polaczenie.prepareStatement(sqlSprawdzenieLiczbyRekordow);
        ResultSet wynik = zapytanie.executeQuery();
        int liczbaRekordow = wynik.getInt(1);
        if (liczbaRekordow < 15) {
            String sql = "INSERT INTO wynikiGry (Nick,Punkty) VALUES(?,?)";

            PreparedStatement prepStatment = polaczenie.prepareStatement(sql);

            prepStatment.setString(1, nick);
            prepStatment.setInt(2, liczbaPunktow);
            prepStatment.execute();

        } else {

            // zapis wyników w momencie gdy w bazie danych jest  15 rekordów
            String sqlAktualizacjaDanych = " update wynikiGry set Nick=\"" + nick + "\", Punkty = " + liczbaPunktow + " \n"
                    + "                                           where Data=(select min(Data) from wynikiGry where\n"
                    + "                                           punkty=(select min(Punkty) from wynikiGry)) \n"
                    + "                                           \n"
                    + "\n"
                    + "";
            PreparedStatement zapytanieOAktualizacjeDanych = polaczenie.prepareStatement(sqlAktualizacjaDanych);
            zapytanieOAktualizacjeDanych.execute();

        }

    }

    public static void wyswietlRekordy(Connection polaczenie, String nazwaTabeli) throws SQLException {

        String sql = "SELECT * FROM " + nazwaTabeli;
        Statement zapytanie = polaczenie.createStatement();
        wynik = zapytanie.executeQuery(sql);
        int wiersz = 1;

        while (wynik.next() && wiersz < 16) {

            modelTabeli.setValueAt(wynik.getString("Nick"), wiersz, 1);
            modelTabeli.setValueAt(wynik.getString("Punkty"), wiersz, 2);
            wiersz++;
        }
    }

    public static void sortowanieRosnace(Connection polaczenie, String nazwaTabeli) throws SQLException {
        String sql = "SELECT * FROM " + nazwaTabeli + " ORDER BY " + "Punkty" + " DESC";

        PreparedStatement poleceniePrzygotowane = polaczenie.prepareStatement(sql);
        wynik = poleceniePrzygotowane.executeQuery();

        int wiersz = 1;

        while (wynik.next() && wiersz <= 15) {

            modelTabeli.setValueAt(wynik.getString("Nick"), wiersz, 1);
            modelTabeli.setValueAt(wynik.getString("Punkty"), wiersz, 2);
            wiersz++;
        }
    }

    public static void sortowanieMalejace(Connection polaczenie, String nazwaTabeli) throws SQLException {
        String sql = "SELECT * FROM " + nazwaTabeli + " ORDER BY " + "Punkty" + " ASC";

        PreparedStatement poleceniePrzygotowane = polaczenie.prepareStatement(sql);
        wynik = poleceniePrzygotowane.executeQuery();

        int wiersz = 1;

        while (wynik.next() && wiersz <= 15) {

            modelTabeli.setValueAt(wynik.getString("Nick"), wiersz, 1);
            modelTabeli.setValueAt(wynik.getString("Punkty"), wiersz, 2);
            wiersz++;
        }

    }

    public static void kasujWyniki(Connection polaczenie, String nazwaTabeli) throws SQLException {
        String sql = "DELETE FROM " + nazwaTabeli;
        PreparedStatement przygotowanePolecenie = polaczenie.prepareStatement(sql);
        przygotowanePolecenie.execute();
        for (int i = 1; i <= 15; i++) {
            modelTabeli.setValueAt(null, i, 1);
            modelTabeli.setValueAt(null, i, 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object przycisk = e.getSource();

        if (przycisk == wroc) {
            managerOkien.show(Snakev6.oknoMieszczaceInne, "powitanie");
        }
        if (przycisk == kasuj) {

        }
        if (przycisk == zamknij) {
            System.exit(0);
        }
        if (przycisk == sortujRosnaco) {
            try {
                sortowanieRosnace(polaczenie, "wynikiGry");

            } catch (SQLException ex) {
            }
        }

        if (przycisk == sortujMalejaco) {
            try {
                sortowanieMalejace(polaczenie, "wynikiGry");
            } catch (SQLException ex) {
            }
        }
        if (przycisk == kasuj) {
            try {
                kasujWyniki(polaczenie, "wynikiGry");

            } catch (SQLException ex) {
            }
        }

    }

}

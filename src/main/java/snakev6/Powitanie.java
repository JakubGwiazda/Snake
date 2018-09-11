package snakev6;

import snakev6.Gra;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;
import static snakev6.Snakev6.managerOkien;

public class Powitanie extends JPanel implements ActionListener {

    private int szerokoscPrzycisku = 140;
    private int wysokoscPrzycisku = 30;

    public static JButton graj, zamknij, wyniki, wycisz, odcisz;

    public Powitanie() {

        this.setLayout(null);
        graj = new JButton("Graj");
        zamknij = new JButton("Zamknij");
        wyniki = new JButton("Tabela Wyników");
        wycisz = new JButton("Wycisz dźwięki");
        odcisz = new JButton("Włącz dźwięki");

        graj.setBounds(Gra.SZEROKOSC_CALEGO_OKNA / 2, Gra.WYSOKOSC_CALEGO_OKNA / 2 - 180, szerokoscPrzycisku, wysokoscPrzycisku);
        wycisz.setBounds(Gra.SZEROKOSC_CALEGO_OKNA / 2, Gra.WYSOKOSC_CALEGO_OKNA / 2 - 140, szerokoscPrzycisku, wysokoscPrzycisku);
        odcisz.setBounds(Gra.SZEROKOSC_CALEGO_OKNA / 2, Gra.WYSOKOSC_CALEGO_OKNA / 2 - 100, szerokoscPrzycisku, wysokoscPrzycisku);
        wyniki.setBounds(Gra.SZEROKOSC_CALEGO_OKNA / 2, Gra.WYSOKOSC_CALEGO_OKNA / 2 - 60, szerokoscPrzycisku, wysokoscPrzycisku);
        zamknij.setBounds(Gra.SZEROKOSC_CALEGO_OKNA / 2, Gra.WYSOKOSC_CALEGO_OKNA / 2 - 20, szerokoscPrzycisku, wysokoscPrzycisku);

        wyniki.setFocusable(false);
        zamknij.setFocusable(false);
        graj.setFocusable(false);
        wycisz.setFocusable(false);
        odcisz.setFocusable(false);
        
        wyniki.addActionListener(this);
        graj.addActionListener(this);
        zamknij.addActionListener(this);
        wycisz.addActionListener(this);
        odcisz.addActionListener(this);
        
        this.setBackground(Color.red);
        this.add(graj);
        this.add(odcisz);
        this.add(wycisz);
        this.add(zamknij);
        this.add(wyniki);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object przycisk = e.getSource();

        if (przycisk == graj) {
            managerOkien.show(Snakev6.oknoMieszczaceInne, "gra");
            Gra.zegar.start();

        }
        if (przycisk == zamknij) {
            System.exit(0);
        }
        if (przycisk == wycisz) {
            try {
                new Music().wyciszMyzykeTla();
            } catch (FileNotFoundException ex) {
            }
        }
        if(przycisk==odcisz)
        {
            try {
                new Music().odciszMuzykeTla();
            } catch (FileNotFoundException ex) {
            }  
            
        }
        if (przycisk == wyniki) {
            managerOkien.show(Snakev6.oknoMieszczaceInne, "wyniki");
            try {
                Wyniki.wyswietlRekordy(Wyniki.polaczenie, "wynikiGry");
            } catch (SQLException ex) {
                Logger.getLogger(Powitanie.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}

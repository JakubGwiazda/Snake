/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakev6;

import snakev6.Gra;
import snakev6.Klawisze;

import snakev6.Wyniki;
import java.awt.CardLayout;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Snakev6 extends JFrame {

    public static JFrame oknoGlowne = new JFrame("Snake");

    public static JPanel oknoMieszczaceInne = new JPanel();
    public static CardLayout managerOkien = new CardLayout();

    public Snakev6() throws SQLException {

        JPanel oknoPowitalne = new Powitanie();
        JPanel oknoGry = new Gra();
        JPanel oknoWynikow = new Wyniki();
        oknoMieszczaceInne.addKeyListener(new Klawisze());
        oknoMieszczaceInne.setFocusable(true);

        oknoMieszczaceInne.setLayout(managerOkien);
        oknoMieszczaceInne.add(oknoPowitalne, "powitanie");
        oknoMieszczaceInne.add(oknoGry, "gra");
        oknoMieszczaceInne.add(oknoWynikow, "wyniki");
        managerOkien.show(oknoMieszczaceInne, "powitanie");

        oknoGlowne.setSize(Gra.SZEROKOSC_CALEGO_OKNA, Gra.WYSOKOSC_CALEGO_OKNA);
        oknoGlowne.setResizable(false);
        oknoGlowne.add(oknoMieszczaceInne);
        oknoGlowne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        oknoGlowne.setVisible(true);

        try {
            new Music().dzwiekTla();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Snakev6.class.getName()).log(Level.SEVERE, null, ex);
        }

        Wyniki.bazaDanych();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {
                    new Snakev6();
            
                } catch (SQLException ex) {
                }

            }
        });

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakev6;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import static snakev6.Gra.zegar;




public class Klawisze extends KeyAdapter{
  
    
    @Override
    public void keyPressed(KeyEvent e)
    {

     if(e.getKeyCode()==KeyEvent.VK_RIGHT&&!Gra.lewo)
    {
      Gra.stopnie=90;
        Gra.prawo=true;
        Gra.dol=false;
         Gra.gora=false;
        
        
    }
    if(e.getKeyCode()==KeyEvent.VK_LEFT&&!Gra.prawo)
    {
        Gra.stopnie=-90;
        Gra.lewo=true;
        Gra.dol=false;
        Gra.gora=false;
    }
    if(e.getKeyCode()==KeyEvent.VK_UP&&!Gra.dol)
    {
      Gra.stopnie=0;
        Gra.gora=true;
        Gra.prawo=false;
        Gra.lewo=false;
 
        
    }
    if(e.getKeyCode()==KeyEvent.VK_DOWN&&!Gra.gora)
    {
       Gra.stopnie=180;
      Gra.dol=true;
      Gra.prawo=false;
      Gra.lewo=false;
     
    }

        
   
    }
    
}

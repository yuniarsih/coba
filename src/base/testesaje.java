/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

/**
 *
 * @author Pramudya
 */
//import java.awt.Component;
//import java.text.DecimalFormat;
//import java.text.DecimalFormatSymbols;
//import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
//import java.util.Random;
//import java.util.Date;
//import javax.swing.JOptionPane;
//import org.springframework.security.crypto.bcrypt.BCrypt;
public class testesaje {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        String rand = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
//        int range = (rand.length()) ;
//        String Result = "";
//        for (int i = 0; i < 6; i++) {
//            int getValue = (int)(Math.random() * range);
//            char CharPoss = rand.charAt(getValue);
//            Result += CharPoss;
//        }
//        System.out.println(Result);
//    }
    
    public static void main(String[] args) {
        
//        Date thisDay = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat("EEEE");
//        System.out.println(ft.format(thisDay));
//        String number = "10000000";
//        int amount = Integer.parseInt(number);
//        DecimalFormat formatter = new DecimalFormat("#,###");
//        System.out.println(formatter.format(amount));
//        
//        String paswot = "admin1234";
//        String pw_hash = BCrypt.hashpw(paswot, BCrypt.gensalt(10));
//        System.out.println(pw_hash);
//        
//        if (BCrypt.checkpw("admin12345", pw_hash)){
//            System.out.println("benard");
//        }
//        else{
//            System.out.println("salahcoy");
//        }

        LocalDate harinii = LocalDate.now();
        System.out.println((harinii));
        System.out.println("7 hari sebelumnya adalah " + harinii.plusDays(-7));
    }
}

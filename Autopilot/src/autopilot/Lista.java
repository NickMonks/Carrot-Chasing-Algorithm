/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autopilot;
import gui.CarrotPanel;
import gui.ControlPanel;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author nimongar
 */
// OBJETIVO: que sea capaz de, leer el txt, enviarlo a una variable tipo arraylist con lat, long y a la vez, escribirlo en la gui

public class Lista {
    
    private Cruise cruise;
    private ControlPanel carrot;
    private int n =0;
    // Meter AQUI la dirección del txt
    private String direccion ="";
    
    public void Leer(String direccion) throws IOException{
        // lee el txt y lo pone en el wp 
        try {
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            String bfRead;
        while (bf.readLine() != null ){
            // Lectura de lat y lon (string)
            String temp = bf.readLine();
            String []splits = temp.split(", Longitud:");
            String LonIndex = (splits[0].replace("Latitud:","").trim());
            String LatIndex = splits[1].trim();
            // Escritura en WP 
            cruise.writeWP(Float.parseFloat(LatIndex),Float.parseFloat(LonIndex));

           
        }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
        }
 
       
    }
    
    
    



// public void writeWP(float lat, float lon){
//        Waypoint_Leg.add(1, new Waypoint(lat, lon));
//    }


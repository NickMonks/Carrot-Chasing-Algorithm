/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xplane.demos;

import autopilot.DataXplane;
import xplane.XplaneInputOutput;

/**
 *
 * @author my.oñ
 */
public class ReadDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XplaneInputOutput xplane;
        DataXplane data; // Estructura de datos, objeto, que contiene todo lo que necesitamos conocer sobre el Xplane
        
        xplane = new XplaneInputOutput(); // Queremos pasarle cosas al Xplane (input) desde aquí (output)

        try {
            Thread.sleep(1000); // wait a little before reading
        } catch (InterruptedException ex) {
        }

        data = xplane.read(); // va a leer los datos que nos proporciona el autopiloto
        System.out.println("Latitude " + data.getLat() + " Longitude " + data.getLon() + " Altitude " + data.getAlt());
        //Si queremos añadirle otra variable bastaría con poner data.getX(); 

        xplane.stop();

    }

}

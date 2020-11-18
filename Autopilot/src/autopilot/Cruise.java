/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autopilot;

import xplane.XplaneInputOutput;
import com.bbn.openmap.proj.Length;
import com.bbn.openmap.proj.coords.LatLonPoint;
import java.util.*;

/**
 *
 * @author
 */
public class Cruise implements PhaseController {

    private ActionXplane actions;
    private ControlP lateral_guidance;
    private ControlP lateral_control;
    private ControlP vertical_guidance;
    private ControlP vertical_control;
    private int contador = 1;
    private float WPk1_lat, WPk1_lon;
    private float WPk2_lat, WPk2_lon;
    private int n = 0;
    ArrayList<Waypoint> Waypoint_Leg; 

    public Cruise() {
        actions = new ActionXplane();

       
        lateral_control = new ControlP(0.05f, -20, 20);
        lateral_guidance = new ControlP(0.2f, -1, 1);
        vertical_control = new ControlP(0.006f, -15, 15);
        vertical_guidance = new ControlP(0.6f, -1, 1);
        
        Waypoint_Leg = new ArrayList<Waypoint>(); 
        
// Se crea un ArrayList para almacenar los puntos dentro.
        // De este modo, cuando se llama a crucero, solo lo crea 
        // UNA VEZ.
    }

    @Override
    public ActionXplane computeAction(DataXplane data) {

        final float DTK = 97, DALT = 2200; // Rumbo de Orvus
        float ias, tas, roll, track, altitude, pitch; // inputs
        float ailerons, elevators; // outputs
        float DROLL, DPITCH, roll_err, pitch_err, alt_err, spd_err, track_error; // errors
        float lat, lon;

       



        
// -------- LECTURA DE DATOS DESDE XPLANE --------------------------------------

        tas = data.getTas();
        roll = data.getRoll();
        track = data.getHpath();
        altitude = data.getAlt();
        pitch = data.getPitch();
        ias = data.getIas();
        lat = data.getLat();
        lon = data.getLon();
        
// ------- GUIADO LATERAL ------------------------------------------------------
// Se llama a la posición n y n-1 (siendo el n inicial n=1). de este modo, se cogerá 
// al inicio el primer y segundo valor de la lista, hasta que se cumpla el tramo
    
        
        WPk1_lat = Waypoint_Leg.get(n - 1).getLat();
        WPk2_lat = Waypoint_Leg.get(n).getLat();
        WPk1_lon = Waypoint_Leg.get(n - 1).getLon();
        WPk2_lon = Waypoint_Leg.get(n).getLon();
        
// ------- ALGORITMO DE LA ZANAHORIA -----------------------------------
// Tras conocer el valor de lon lat en cada WP, se procede a aplicar el 
// algoritmo de la zanahoria.

float DTK_CARROT = Carrot_Computing(lat, lon, WPk1_lat, WPk1_lon, WPk2_lat, WPk1_lon);

track_error = DTK_CARROT - track;
if (track_error > 180) {
    track_error = track_error - 360;
}
// System.out.println("El track correcto es: " + track_error);
        
// ----- CONDICION DE CAMBIO DE FASE -------------------------------------------
// Condiciones impuestas de la literatura página  207
// Sumas uno a la variable n, y coges el waypoint inicial ahora como el final de antes. 
// si antes n=1, ahora n=1, y get (n-1) era el ultimo del leg anterior. 

if ((dist2 < L1) || (dist1 * Math.cos(lambda) >= dist3)) { 
    actions.setPhase(Autopilot.CRUISE);
     n++;
} else {
    actions.setPhase(Autopilot.CRUISE);
}
        
// ------------ GUIADO LATERAL -------------------------------------------------
        
        System.out.println("DTK: " + DTK_CARROT);
        DROLL = lateral_guidance.control(track_error);
        roll_err = DROLL - roll;
        ailerons = lateral_control.control(roll_err);

        // VERTICAL GUIDANCE -------------------------------------------------------
        //GUIDANCE LOOP
        alt_err = DALT - altitude;
        DPITCH = vertical_guidance.control(alt_err);
        pitch_err = DPITCH - pitch;
        elevators = vertical_control.control(pitch_err);

        // SET ACTIONS--------------------------------------------------------------
        actions.setAilerons(ailerons);
        actions.setElevators(elevators);
        actions.setRudder(0); // Le estamos diciendo que nos ponga el valor del rudder que hemos calculado a través del control
        actions.setThrottle(0.65f); // Imposición inicial para que gire bien
        actions.setBrake(0);
        actions.setFlaps(0.0f);
        actions.setPhase(Autopilot.CRUISE);
        contador = 0;

        

        return actions;
    }

    private double XN;
    private double XE;
    private double WPk1_N;
    private double WPk1_E;
    private double WPk2_N;
    private double WPk2_E;
    private double L1 = 600.0 / 111000;
    private float DTK;
    private double R = 6378000; // radio de la Tierra en k
    private double d1, d2, d3;
    private double lambda;
    private double xi;
    private double psi;
    private double dchange;
    private double PN;
    private double PE;
    private double dist1;
    private double dist2;
    private double dist3;
    private double angle, distKmFormat1, angleFormat1;

    private float Carrot_Computing(float lat, float lon, float WPk1_lat, 
                    float WPk1_lon, float WPk2_lat, float WPk2_lon) {

// Se coge como sistema de referencia el el meridiano de greenwich,
        // ---------Posicion avion: ----------------
        XN = lat;
        XE = lon;
        LatLonPoint Avion = new LatLonPoint.Double(XN, XE);

        // -------------Posicion inicial:----------
        WPk1_N = (double) WPk1_lat;
        WPk1_E = (double) WPk1_lon;
        LatLonPoint WPk1 = new LatLonPoint.Double(WPk1_N, WPk1_E);

        //---------- Posicion final-------------------
        WPk2_N = (double) WPk2_lat;
        WPk2_E = (double) WPk2_lon;
        LatLonPoint WPk2 = new LatLonPoint.Double(WPk2_N, WPk2_E);

        //  --------- FORMULAS CARROT ---------------------
        
        psi = Math.atan2((WPk2_E - WPk1_E), (WPk2_N - WPk1_N)); // en radianes 
        xi = Math.atan2((XE - WPk1_E), (XN - WPk1_N)); // en radianes
        lambda = Math.abs(Math.abs(psi) - Math.abs(xi)); // en radianes
        d1 = Math.sqrt(Math.pow(XE - WPk1_E, 2) + Math.pow(XN - WPk1_N, 2)); // en grados
        
        // Bucle condicional para cambiar el valor de L1
        if (lambda > (Math.PI / 2)) {
            L1 = Math.max(L1, d1);
        } else {
            double distanceCH = d1 * Math.sin(lambda);
            if (distanceCH > L1) {
                L1 = distanceCH * 1.1;
            }
        }

        double dis_WPk_P = d1 * Math.cos(lambda) + Math.sqrt(Math.pow(L1, 2) - 
                            (Math.pow(d1, 2) * Math.pow(Math.sin(lambda), 2))); 
        // en grados, distancia entre el punto que se busca y el punto inicial
        
        // Cálculo de d2 --> En grados para poder comparar con L1
        dist2 = Math.sqrt(Math.pow(WPk2_E-XE, 2) + Math.pow(WPk1_N-XN, 2)); // en grados
        
        // ------- Condicion ||WPk1,Wpk2|| -----------
        dist3 = WPk1.distance(WPk2); // distancia en grados
        
        // Monitorización de condiciones
        double cond1double = (dist2 - L1);
        float cond1 = (float) cond1double;
        double cond2double = (dist1 * Math.cos(lambda) - dist3);
        float cond2 = (float) cond2double;


        System.out.println("Condición 1:  " + cond1);
        System.out.println("Condición 2:  " + cond2);

        //-------- Coordenadas P----------------------
        // Calculo del punto P
        double PN = WPk1_N + dis_WPk_P * Math.cos(psi);
        double PE = WPk1_E + dis_WPk_P * Math.sin(psi);
        System.out.println("PN zanahoria: " + PN + " PE zanahoria:" + PE);

        // ----- Cálculo del DTK --------------
        LatLonPoint PNLLA = new LatLonPoint.Double(PN, PE);
        LatLonPoint avionLLA = new LatLonPoint.Double(XN, XE);
        double distRad = avionLLA.distance(PNLLA); // distance in radians
        double distKm = Length.KM.fromRadians(distRad);
        angle = 180 * avionLLA.azimuth(PNLLA) / Math.PI;
        if (angle < 0) {
            angle = angle + 360;
        }
        DTK = (float) angle;


        return DTK;

    }

    // Este método almacena el WP al final del arraylist. A medida que va leyendo el txt, 
    // sucede que va añadiendose mientras lee el wp. 
    
     public void writeWP(float lat, float lon){
        Waypoint_Leg.add(new Waypoint(lat, lon));
    }
}

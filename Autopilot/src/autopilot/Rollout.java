/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autopilot;

import xplane.XplaneInputOutput;

/**
 *
 * @author 
 */

// La salida ORVUS 1B
public class Rollout implements PhaseController {

    private ActionXplane actions; // Objeto tipo ActionXplane común a todas las fases.
    private ControlP rudder_control; // Suministra el controlador proporcional (PID)

    public Rollout() { // Constructor
        actions = new ActionXplane();
        rudder_control = new ControlP(0.03f, -1, 1); // Constante K, rango del rudder (-1,1)

    }

    @Override
    public ActionXplane computeAction(DataXplane data) {

        float DTK = 118.1f; // references Valor de track deseado
        final float TAS_MAX = 70; // termination conditions
        float tas, head, track,lat,lon; // inputs
        float rudder; // outputs
        float track_error; // errors

        // READ DATA----------------------------------------------------------------
        tas = data.getTas();
        track = data.getHpath();
        head = data.getHead();
        lat = data.getLat();
        lon = data.getLon();
        // Punto inicial de salida del aeropuerto
        float WPk1_lat = 39.4963f;
        float WPk1_lon = -0.4999f;
        // Punto final de la pista
        float latWP2 = 39.483563f ;
        float lonWP2 = -0.466631f;
        
        if (Math.abs(track-head)>0.5) // Para cuando haya viento, si no lo hay , no hay problema, se quita
             track = head;

//        Carrot_Chasing c1 = new Carrot_Chasing(lat,lon,WPk1_lat,WPk1_lon,latWP2,lonWP2);
//            c1.start();
        
        
        // LATERAL GUIDANCE---------------------------------------------------------
        // CONTROL LOOP
        
        track_error = DTK - track;
        rudder = rudder_control.control(track_error);


       // Es el bucle

        // SET ACTIONS--------------------------------------------------------------   
        actions.setAilerons(0); 
        actions.setElevators(0);
        actions.setRudder(rudder); // Le estamos diciendo que nos ponga el valor del rudder que hemos calculado a través del control
        actions.setThrottle(1);
        actions.setBrake(0);
        actions.setFlaps(0.0f);

        // SET NEXT FLIGHT PHASE----------------------------------------------------
        if (tas > TAS_MAX) {
            actions.setPhase(Autopilot.ROTATE);
        } else {
            actions.setPhase(Autopilot.ROLLOUT); 
// Este else es necesario porque en Autopilot.java se está haciendo un caso iterativo en el que vuelve a entrar en la función hasta que suceda la condición
        }

        return actions;
    }

}

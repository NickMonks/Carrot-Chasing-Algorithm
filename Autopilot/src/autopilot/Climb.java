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
public class Climb implements PhaseController {

    private ActionXplane actions;
    private ControlP lateral_guidance;
    private ControlP lateral_control;
    private ControlP vertical_guidance;
    private ControlP vertical_control;

    public Climb() {
        actions = new ActionXplane();
        // MAKE INSTANCES OF CONTROLLERS
        lateral_control = new ControlP(0.05f, -20, 20);
        lateral_guidance = new ControlP(0.2f,-1,1);
        vertical_control = new ControlP(0.001f,-15,15);
        vertical_guidance = new ControlP(-1f,-1,1);
    }

    @Override
    public ActionXplane computeAction(DataXplane data) {
        
        
        final float DTK = 140, DTAS = 90; // references
        final float ALTITUDE_MAX = 500; // termination conditions
        float ias,tas, roll, track, altitude, pitch; // inputs
        float ailerons, elevators; // outputs
        float DROLL, DPITCH, roll_err, pitch_err, spd_err, track_error; // errors
        // Definimos las coordenadas de MULAT por lo que necesitamos LAT y Lon DEL AVIÓN
      
        
        
         // Lee los datos necesarios para computar los errores.----------------------------------------------------------------
        ias = data.getIas();
        tas = data.getTas();
        roll = data.getRoll();
        track = data.getHpath();
        altitude = data.getAlt();
        pitch = data.getPitch();

        // LATERAL GUIDANCE---------------------------------------------------------
        // GUIDANCE LOOP
        
          track_error = DTK -track;
        DROLL = lateral_guidance.control(track_error);
        roll_err = DROLL-roll;
        ailerons = lateral_control.control(roll_err);
        


        // VERTICAL GUIDANCE -------------------------------------------------------
        //GUIDANCE LOOP
        spd_err = DTAS -ias;
        DPITCH = vertical_guidance.control(spd_err);
        pitch_err = DPITCH-pitch;
        elevators = vertical_control.control(pitch_err);

        // SET ACTIONS--------------------------------------------------------------
        actions.setAilerons(ailerons); 
        actions.setElevators(elevators);
        actions.setRudder(0); // Le estamos diciendo que nos ponga el valor del rudder que hemos calculado a través del control
        actions.setThrottle(1);
        actions.setBrake(0);
        actions.setFlaps(0.0f);
        
        // SET NEXT FLIGHT PHASE----------------------------------------------------
        if (altitude > ALTITUDE_MAX) {
            actions.setPhase(Autopilot.CRUISE);
           // Queremos saber la coorenada inicial de la recta de crucero para poder aplicar el método de la zanahoria.
        } else {
            actions.setPhase(Autopilot.CLIMB); 
// Este else es necesario porque en Autopilot.java se está haciendo un caso iterativo en el que vuelve a entrar en la función hasta que suceda la condición
        }


        return actions;
    }

}

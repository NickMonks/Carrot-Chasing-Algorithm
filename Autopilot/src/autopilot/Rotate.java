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
public class Rotate implements PhaseController {

    private ActionXplane actions;
    private ControlP lateral_control; // El de dentro
    private ControlP lateral_guidance; // El de fuera, el grande
    

    
    public Rotate() {
        actions = new ActionXplane();
        lateral_control = new ControlP(0.05f, -20, 20);
        lateral_guidance = new ControlP(0.2f,-1,1);
    }

    @Override
    public ActionXplane computeAction(DataXplane data) {

        final float DTK = 120; // references
        final float ALTITUDE_MAX = 400; // termination conditions
        float tas, roll, track, altitude; // inputs
        float ailerons, elevators; // outputs
        float DROLL, roll_err, track_error; // errors
        
        // READ DATA----------------------------------------------------------------
        tas = data.getTas();
        track = data.getHpath();
        roll =data.getRoll();
        altitude = data.getAlt();
        
        
        // LATERAL GUIDANCE---------------------------------------------------------
        // GUIDANCE LOOP
        track_error = DTK -track;
        DROLL = lateral_guidance.control(track_error);
        roll_err = DROLL-roll;
        ailerons = lateral_control.control(roll_err);

        // VERTICAL GUIDANCE -------------------------------------------------------
        elevators = 0.01f;

        // SET ACTIONS--------------------------------------------------------------
        actions.setAilerons(ailerons); 
        actions.setElevators(elevators);
        actions.setRudder(0); // Le estamos diciendo que nos ponga el valor del rudder que hemos calculado a través del control
        actions.setThrottle(1);
        actions.setBrake(0);
        actions.setFlaps(0.0f);
        // SET NEXT FLIGHT PHASE----------------------------------------------------
        if (altitude > ALTITUDE_MAX) {
            actions.setPhase(Autopilot.CLIMB);
        } else {
            actions.setPhase(Autopilot.ROTATE); 
// Este else es necesario porque en Autopilot.java se está haciendo un caso iterativo en el que vuelve a entrar en la función hasta que suceda la condición
        }

        return actions;
    }

}

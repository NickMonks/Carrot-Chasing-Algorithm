/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xplane.demos;

import autopilot.ActionXplane;
import autopilot.DataXplane;
import xplane.XplaneInputOutput;

/**
 *
 * @author my.oñ
 */
public class WriteDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         XplaneInputOutput xplane;
         ActionXplane action;
         
         xplane = new XplaneInputOutput(); // se define el objeto xplane
         action = new ActionXplane(); // Envía cosas al xplane
         action.setAilerons(-1); 
         action.setElevators(0);
         action.setRudder(-1);
         action.setThrottle(1);
         action.setBrake(0);
         action.setFlaps(1);
         xplane.write(action);
         
         xplane.stop();
         
               
        
    }
    
}

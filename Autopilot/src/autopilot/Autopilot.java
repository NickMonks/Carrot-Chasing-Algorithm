/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autopilot;

import xplane.XplaneInputOutput;
import gui.ControlPanel;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jvila
 */
public class Autopilot extends Thread {
    
    public static final int ROLLOUT = 1;
    public static final int ROTATE = 2;
    public static final int CLIMB = 3;
    public static final int CRUISE = 4;
 
    
    XplaneInputOutput xplane;
    ControlPanel mygui;
    private boolean running;
    private int sampleRate;
    
    private DataXplane data;
    private DataXplane data2;
    private ActionXplane action;
    
    private PhaseController pController;
    private Rollout rollOut;
    private Rotate rotate;
    private Climb climb;
    private Cruise cruise;
   
   


  
    public Autopilot(ControlPanel mygui) {
        
        this.xplane = new XplaneInputOutput();
        this.mygui = mygui;
        
        try {
            Thread.sleep(1000); // wait a few before send actions
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        rollOut = new Rollout(); // inicialize phases
        rotate = new Rotate();
        climb = new Climb();
        cruise = new Cruise();
        
        

        running = true;
        sampleRate = 200;
        this.start();
    }
    

    
    @Override
    public void run() {

       pController = rollOut; // initial phase Acordarnos que esto debe ser así, NO EMPEZAR EN CRUISE
       //pController = cruise;
        while (running) { // control loop

            data = xplane.read();
            mygui.display(data);
          
            action = pController.computeAction(data);
            xplane.write(action);
            mygui.display(action);

            switch (action.getPhase()) {
                case ROLLOUT:
                    pController = rollOut;
                    break;
                case ROTATE:
                    pController = rotate;
                    break;
                case CLIMB:
                    pController = climb;
                    break;
                case CRUISE:
                    pController = cruise;
                    break;
                                    
            }

            try {
                Thread.sleep(sampleRate);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        xplane.stop();
    }
    
    public void stopMe(){
        running = false;
    }  
    
}

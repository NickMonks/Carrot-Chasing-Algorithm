/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autopilot;

/**
 *
 * @author 
 */
public class ActionXplane {

    private int phase; // current phase
    private float ailerons;
    private float elevators;
    private float rudder;
    private float throttle;
    private float brake;
    private float flaps;
   
    public ActionXplane() {
        ailerons = 0;
        elevators = 0;
        rudder = 0;
        throttle = 0;
        brake = 1;
        flaps = 0;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setAilerons(float ailerons) {
        this.ailerons = ailerons;
    }

    public void setElevators(float elevators) {
        this.elevators = elevators;
    }

    public void setRudder(float rudder) {
        this.rudder = rudder;
    }

    public void setThrottle(float throttle) {
        this.throttle = throttle;
    }

    public void setBrake(float brake) {
        this.brake = brake;
    }

    public void setFlaps(float flaps) {
        this.flaps = flaps;
    }

    public int getPhase() {
        return phase;
    }

    public float getAilerons() {
        return ailerons;
    }

    public float getElevators() {
        return elevators;
    }

    public float getRudder() {
        return rudder;
    }

    public float getThrottle() {
        return throttle;
    }

    public float getBrake() {
        return brake;
    }

    public float getFlaps() {
        return flaps;
    }

}

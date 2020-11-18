/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autopilot;

/**
 *
 * @author jvila
 */
public class ControlP {
    
    float Kp;
    float minv;
    float maxv;

    public ControlP(float Kp, float minv, float maxv) {
        this.Kp = Kp;
        this.minv = minv;
        this.maxv = maxv;
    } 

    public float control(float err) {
        float action;
        action = Kp * err;
        action = Math.min(maxv, action);
        action = Math.max(minv, action);
        return action;
    }
    
}

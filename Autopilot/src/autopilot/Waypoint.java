/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autopilot;

/**
 *
 * @author Nico
 */
public class Waypoint { // Las funciones getter y setter guardando la latitud y longitud que le metes.
    float lat;
    float lon;
        
    
    public Waypoint(float lat, float lon){
        this.lat =lat;
        this.lon =lon;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
    
    
}

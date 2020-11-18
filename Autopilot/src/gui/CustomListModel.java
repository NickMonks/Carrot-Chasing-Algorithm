/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import autopilot.Waypoint;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Nico
 */
public class CustomListModel extends AbstractListModel {

    ArrayList<Waypoint> Waypoint_Leg;

    @Override
    public int getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getElementAt(int index) {
        Waypoint WP = Waypoint_Leg.get(index);
        float[] WP_data = null;
        WP_data[0] = WP.getLon();
        WP_data[1] = WP.getLat();

        return WP_data;

    }
    
     

}

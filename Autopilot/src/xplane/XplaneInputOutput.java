/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xplane;

/**
 *
 * @author angel
 */
import autopilot.ActionXplane;
import autopilot.DataXplane;
import gui.ControlPanel;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import xplane.XPlaneInterface;


public class XplaneInputOutput {

    private XPlaneInterface xpi;
    private DataXplane data;

    public XplaneInputOutput() {

        try {
            xpi = new XPlaneInterface("127.0.0.1", 9500, 49000, "DATAGroupConfig.xml");
            xpi.unregisterDATAMessages("*");
            xpi.registerDATAMessages("3,4,8,13,14,16,17,18,20,25"); // 20 lat lon alt replaces  22 23 24
            xpi.startReceiving();
        } catch (SocketException ex) {
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);

        } catch (UnknownHostException ex) {
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);

        }
        data = new DataXplane();

    }

    public DataXplane read() {

        data.setLat(xpi.getValue("position.lat"));
        data.setLon(xpi.getValue("position.lon"));
        data.setAlt(xpi.getValue("position.altMsl"));
        data.setRoll(xpi.getValue("orientation.roll"));
        data.setPitch(xpi.getValue("orientation.pitch"));
        data.setHead(xpi.getValue("orientation.headingTrue"));
        data.setAoa(xpi.getValue("orientation.alfa"));
        data.setBeta(xpi.getValue("orientation.beta"));
        data.setVpath(xpi.getValue("orientation.vpath"));
        data.setHpath(xpi.getValue("orientation.hpath"));
        data.setBrake(xpi.getValue("controls.brake"));
        data.setIas(xpi.getValue("position.ias"));
        data.setTas(xpi.getValue("position.tas"));
        data.setGs(xpi.getValue("position.gs"));
        data.setVs(xpi.getValue("position.vs"));
        return data;

    }

    public void write(ActionXplane action) {

        xpi.setValue("controls.aileronsPosition", action.getAilerons());
        xpi.setValue("controls.elevatorsPosition", action.getElevators());
        xpi.setValue("controls.rudderPosition", action.getRudder());
        xpi.setValue("engine.throttleCommand1", action.getThrottle());
        xpi.setValue("controls.brake", action.getBrake());
        xpi.setValue("controls.flapPosition", action.getFlaps());

    }

    public void stop() {

        xpi.stop();

    }

}

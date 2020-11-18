/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autopilot;

/**
 *
 * @author aljipe1
 */
public class DataCruise {
    private double XN;
    private double XE;
    private double WPk1_N;
    private double WPk1_E;
    private double WPk2_N;
    private double WPk2_E;
    private double L1 = 600.0 / 111000;
    private double DTKdouble;
    private float DTK;
    private double R = 6378000; // radio de la Tierra en km
    private double a, a1, c, c1, dist; // Parámetros del método de Haversine para d2
    private double d1, d2, d3;
    private double lambda;
    private double xi;
    private double psi;
    private double dchange;
    private double PN;
    private double PE;
    private double dist1;
    private double dist2;
    private double dist3;
    private double angle, distKmFormat1, angleFormat1;
    
    // .---------------------------------------------------------------------------------------
    public double getDTK() {
        return DTK;
    }

    public double getL1() {
        return L1;
    }

    public double getXN() {
        return XN;
    }

    public double getXE() {
        return XE;
    }

    public double getWPk1_N() {
        return WPk1_N;
    }

    public double getWPk1_E() {
        return WPk1_E;
    }

    public double getWPk2_N() {
        return WPk2_N;
    }

    public double getWPk2_E() {
        return WPk2_E;
    }

    public double getD1() {
        return d1;
    }

    public double getD2() {
        return d2;
    }

    public double getLambda() {
        return lambda;
    }

    public double getXi() {
        return xi;
    }

    public double getPsi() {
        return psi;
    }


    public void setXN(double XN) {
        this.XN = XN;
    }

    public void setXE(double XE) {
        this.XE = XE;
    }

    public void setWPk1_N(double WPk1_N) {
        this.WPk1_N = WPk1_N;
    }

    public void setWPk1_E(double WPk1_E) {
        this.WPk1_E = WPk1_E;
    }

    public void setWPk2_N(double WPk2_N) {
        this.WPk2_N = WPk2_N;
    }

    public void setWPk2_E(double WPk2_E) {
        this.WPk2_E = WPk2_E;
    }

    public void setL1(double L1) {
        this.L1 = L1;
    }

    public void setDTK(float DTK) {
        this.DTK = DTK;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public void setXi(double xi) {
        this.xi = xi;
    }

    public void setPsi(double psi) {
        this.psi = psi;
    }

    public void setDist1(double dist1) {
        this.dist1 = dist1;
    }

    public void setPN(double PN) {
        this.PN = PN;
    }

    public void setPE(double PE) {
        this.PE = PE;
    }

    public double getPN() {
        return PN;
    }

    public double getPE() {
        return PE;
    }
}




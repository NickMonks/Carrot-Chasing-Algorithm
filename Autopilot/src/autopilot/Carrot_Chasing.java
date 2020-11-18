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
public class Carrot_Chasing {

    double XN;
    double XE;
    double WPk1_N;
    double WPk1_E;
    double WPk2_N;
    double WPk2_E;
    double L1 = 150; // Según bibliografía, habría que ponderar
    double DTKdouble;
    float DTK;
    boolean running = true;
    double R = 6378000; // radio de la Tierra en km
    double a, c, dist; // Parámetros del método de Haversine para d2
    double d1, d2, d3;
    double dchange;
    double lambda;
    double xi;
    double psi;
  
   

    public Carrot_Chasing(float lat, float lon, float WPk1_lat, float WPk1_lon, float WPk2_lat, float WPk2_lon) {
        // Se supone Tierra Plana por lo que se pondera multiplicando cada grado por 111 km
        this.XN = (double) lat;
        this.XE = lon;
        this.WPk1_N = (double) WPk1_lat;
        this.WPk1_E = (double) WPk1_lon;
        this.WPk2_N = (double) WPk2_lat;
        this.WPk2_E = (double) WPk2_lon;

    }

    public void ejecuta() {

        psi = Math.atan2((WPk2_N*111000 - WPk1_N*111000),(WPk2_E*111000 - WPk1_E*111000)); //radianes
        xi = Math.atan2((XN*111000 - WPk1_N*111000),(XE*111000 - WPk1_E*111000)); // radianes
        lambda = Math.abs(psi) - Math.abs(xi); // radianes
        d1 = Math.sqrt(Math.pow(XE*111000 - WPk1_E*111000, 2) + Math.pow(XN*111000 - WPk1_N*111000, 2)); // en metros
        // Bucle condicional para cambiar el valor de L1
          if (Math.abs(lambda)>Math.PI/2){
           L1 = Math.max(L1, d1);
       }else{
           if (d1*Math.sin(lambda)>L1){
               L1 = d1*Math.sin(lambda)*1.1;
           }
       }
        double dis_WPk_P = d1 * Math.cos(lambda) + Math.sqrt(Math.pow(L1, 2)-(Math.pow(d1, 2) * Math.pow(Math.sin(lambda), 2))); // en metros, distancia entre el punto que se busca y el punto inicial
        // Para calcular la condición 2 de cambio a la siguiente fase:
        double difLatc = Math.toRadians(WPk2_N - WPk1_N); // se traduce a radianes para que la fórmula sea correcta
        double difLonc = Math.toRadians(WPk2_E - WPk1_E);
        
        a = Math.sin(difLatc / 2) * Math.sin(difLatc / 2) * Math.cos(Math.toRadians(WPk1_N)) * Math.cos(Math.toRadians(WPk2_N)) * Math.sin(difLonc / 2) * Math.sin(difLonc / 2);
        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        d3 = R * c;

        // Coordenadas P
        double PN = WPk1_N*111000 + dis_WPk_P * Math.cos(psi);
        double PE = WPk1_E*111000 + dis_WPk_P * Math.sin(psi);

        // Cálculo del DTK -> Método Haversine
        double y = Math.sin(PE - XE) * Math.cos(PN);
        double x = Math.cos(XN) * Math.sin(PN)
                - Math.sin(XN) * Math.cos(PN) * Math.cos(PE - XE);
        DTKdouble = Math.atan2(y, x);
        // Para asegurarse que los ángulos son siempre positivos
  
        DTK = (float) Math.toDegrees(DTKdouble)+180; 
        
        System.out.println("x zanahoria: " + x +" y zanahoria:"+y);

        // Cálculo de d2 --> Método de Haversine
        double difLat = WPk2_N-XN;
        double difLon = WPk2_E-XE;
        a = Math.sin(difLat / 2) * Math.sin(difLat / 2) * Math.cos(XN) * Math.cos(WPk2_N) * Math.sin(difLon / 2) * Math.sin(difLon / 2);
        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        d2 = R * c;
 // 
    }

}

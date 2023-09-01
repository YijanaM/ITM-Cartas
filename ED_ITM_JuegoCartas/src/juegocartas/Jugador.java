package juegocartas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import static juegocartas.NombreCarta.AS;
import static juegocartas.NombreCarta.CINCO;
import static juegocartas.NombreCarta.CUATRO;
import static juegocartas.NombreCarta.DOS;
import static juegocartas.NombreCarta.JACK;
import static juegocartas.NombreCarta.KING;
import static juegocartas.NombreCarta.NUEVE;
import static juegocartas.NombreCarta.OCHO;
import static juegocartas.NombreCarta.QUEEN;
import static juegocartas.NombreCarta.SEIS;
import static juegocartas.NombreCarta.SIETE;
import static juegocartas.NombreCarta.TRES;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int DISTANCIA = 40;
    private int MARGEN = 5;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        for (int i = 0; i < cartas.length; i++) {
            cartas[i].mostrar(pnl, MARGEN + TOTAL_CARTAS * DISTANCIA - i * DISTANCIA, MARGEN);
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No hay grupos";
        int[] contadores = new int[NombreCarta.values().length];

        for (int i = 0; i < cartas.length; i++) {
            contadores[cartas[i].getNombre().ordinal()]++;
        }

        int totalGrupos = 0;
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] >= 2) {
                totalGrupos++;
            }
        }
        if (totalGrupos > 0) {
            mensaje = "Los grupos encontrados fueron:\n";
            for (int i = 0; i < contadores.length; i++) {
                if (contadores[i] >= 2) {
                    mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
                }
            }
        }

        return mensaje;
    }
    
    public String getEscalera() {
        String mensaje = "";
        List<List<Carta>> secuencias = encontrarSecuencias(cartas);
        int puntaje = encontrarPuntaje(cartas);
        mensaje += "El puntaje del jugador es de: " + puntaje + "\n\n"; 
        if(!secuencias.isEmpty()){
            mensaje += "El jugador tiene las siguientes figuras de la misma pinta en escalera: \n";
        }else{
        
            mensaje += "El jugador no tiene figuras de la misma pinta en escalera";
        }
        for (List<Carta> secuencia : secuencias) {
            
            for (Carta carta : secuencia) {
                mensaje += carta.getNombre()+ " de " + carta.getPinta() + ", ";
            }
            mensaje += "\n";
        }
        return mensaje;
    }

    private List<List<Carta>> encontrarSecuencias(Carta[] cartas) {
         List<List<Carta>> secuencias = new ArrayList<>();
                  
        for (Pinta pinta : Pinta.values()) {
             
            List<Carta> cartasMismaPinta = new ArrayList<>();
            for (Carta carta : cartas) {
                if (carta.getPinta()== pinta) {
                    cartasMismaPinta.add(carta);
                    
                }
            }
            
           
            cartasMismaPinta.sort((a, b) -> a.getNombre().compareTo(b.getNombre()));
           
            List<Carta> secuenciaActual = new ArrayList<>();
            for (int i = 0; i < cartasMismaPinta.size() - 1; i++) {
                secuenciaActual.add(cartasMismaPinta.get(i));
                
                if (cartasMismaPinta.get(i + 1).getNombre().ordinal() != cartasMismaPinta.get(i).getNombre().ordinal() + 1) {
                    
                    if (secuenciaActual.size() >= 2) {
                        secuencias.add(new ArrayList<>(secuenciaActual));
                        
                    }
                    secuenciaActual.clear();
                }
            }

            if (secuenciaActual.size() >= 2) {
                secuencias.add(new ArrayList<>(secuenciaActual));
            }
        }
        
        return secuencias;
    }

    private int encontrarPuntaje(Carta[] cartas) {
        int puntaje = 0;
        for (Carta carta : cartas) {
            int cardValue = getCardValue(carta);
            puntaje += cardValue;
            
        }
        return puntaje;
               
    }

    private int getCardValue(Carta carta) {
            
         switch (carta.getNombre()) {
            case AS:
            case JACK:
            case QUEEN:
            case KING:
            case DIEZ:
                return 10;
            case DOS:
                return 2;
            case TRES:
                return 3;
            case CUATRO:
                return 4;
            case CINCO:
                return 5;
            case SEIS:
                return 6;
            case SIETE:
                return 7;
            case OCHO:
                return 8;
            case NUEVE:
                return 9;
            default:
                return 0; // Carta no reconocida, valor nulo
        }
    }
    
}

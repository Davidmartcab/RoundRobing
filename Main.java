import java.util.*;
import java.io.*;

public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();

    static ArrayList<Integer> rondas = new ArrayList<Integer>();
    
    static ArrayList<Integer> turnos = new ArrayList<Integer>();

    static ArrayList<Integer> procesing = new ArrayList<Integer>();

    static ArrayList<Boolean> finish = new ArrayList<Boolean>();

    static ArrayList<String> resultado = new ArrayList<String>();

    static int count = 0;
    public static void main(String[] args) throws IOException{
        //Cantidad de procesos que se van a realizar.
        int nProcesos = 20;

        //Nº de quantum.
        int quantum = 6;

        //Rango de numeros para la generación aleatória.
        int num = 40;

        //1 = Aleatorio, 2 = Manual
        int elec = 1;

        switch(elec){
            case 1:
                datos(nProcesos, num);
            break;
            case 2:
            int f = 0;
            while(f == 0){
                System.out.println("Dime cuantos procesos quieres hacer: ");
                String s = br.readLine();
                try {
                    nProcesos = Integer.parseInt(s);
                    f = 1;
                } catch (Exception e) {
                    System.out.println("Error");
                }
            }
            manual(nProcesos);
            break;
            default:
                // datos(nProcesos, num);
            break;
        }

        System.out.println("\033[H\033[2J");
        System.out.println("List: \n" + list);
        order(nProcesos);
        System.out.println("Turnos: " + turnos);
        System.out.println("Rondas: " + rondas);
        process(quantum);
        if(count == 1){
            System.out.println("Ha habido un error.");
        }else{
            see();
            System.out.println("- : preparado.\nx : en ejecución.\n· : terminado.");
        }
    }


    static void datos(int nProcesos, int num){

        int prioridad = 0, tiempo = 0, llegada = 0;
        double  prioridadD = 0, timepoD = 0;

        for (int i = 0; i < nProcesos; i++) {
            ArrayList<Integer> subLista2 = new ArrayList<Integer>();
            //Creo números aleatórios
            timepoD = Math.random()*(num);
            prioridadD = Math.random()*(num);
            //Cambio de double a Int
            tiempo = (int)timepoD;
            prioridad = (int)prioridadD;
            llegada = i;
            //Los añado a la sbulista
            subLista2.add(prioridad);
            subLista2.add(tiempo);
            subLista2.add(llegada);
            //Añado la sublista a la Lista
            list.add(subLista2);
            procesing.add(0);
            rondas.add(0);
            finish.add(false);
            resultado.add("");
        }
    }


    static void order(int nProcesos){

        for (int i = 0; i < nProcesos; i++) {
            int c = 0;
            for (int j = 0; j < nProcesos; j++) {
                int priori1 = list.get(i).get(0), priori2 = list.get(j).get(0);
                int llegada1 = list.get(i).get(2), llegada2 = list.get(j).get(2);
                if (priori1 < priori2) {
                    c += 1;
                }else if (priori1 == priori2){
                    if(llegada1 < llegada2){
                        c += 1;
                    }else{

                    }
                }
            }
            turnos.add(c);
        }
        for (int i = 0; i < turnos.size(); i++) {
            rondas.set(turnos.get(i), i);
        }

        // for (int i = 0; i < turnos.size(); i++) {
        //     System.out.println(turnos.get(i));
        //     rondas.set(turnos.get(i), i);
        // }


    }


    static void process(int quantum){
        int e = 0;
        while(e == 0){
            for (int i = 0; i < rondas.size(); i++){
                int pos = rondas.get(i);
                for (int j = 0; j < quantum; j++){
                    int procesando = procesing.get(pos);
                    int tiempo = list.get(pos).get(1);
                    boolean acabado = finish.get(pos);
                    if(acabado == true){

                        break;
                    }else{
                        procesando += 1;
                        for (int k = 0; k < resultado.size(); k++) {
                            if(k == pos){
                                resultado.set(k, resultado.get(k) + "X");
                            }else{
                                if(finish.get(k)){
                                    resultado.set(k, resultado.get(k) + "·");
                                }else{
                                    resultado.set(k, resultado.get(k) + "-");
                                }
                                
                            }
                        }
                        
                    }
                    if(procesando == tiempo){
                        finish.set(pos, true);
                    }else{
                        procesing.set(pos, procesando);
                    }
                }
            }
            int logitud = resultado.get(0).length();
            e = 1;
            if(logitud > 10000){
                count = 1;
            }else{
                for(int i = 0; i < finish.size(); i++) {
                    if(finish.get(i) == false){
                        e = 0;
                    }
                }
            }

            
                
            
        }
    }

    
    static void see(){
        for(int l = 0; l < resultado.size(); l++) {
            System.out.println(resultado.get(l)+"\n");
        }
        System.out.println(finish);
    }


    static void manual(int nP) throws IOException{
        for(int i = 0; i < nP; i++) {
            int e = 0;
            int prio = 0, tiempo = 0;
            String n1 = "", frase = "";
            while(e == 0){
                System.out.println("Dime el proceso Nº " + (i+1) + "(Prioridad-Tiempo): ");
                frase = br.readLine();
                try {
                    for (int j = 0; j < frase.length(); j++){
                        if(frase.charAt(j) == '-'){
                            if(n1.equals("")){
                                n1 = "0";
                            }
                            prio += Integer.parseInt(n1);
                            n1 = "";
                        }else{
                            n1 += ""+frase.charAt(j);
                        }
                    }
                    if(n1.equals("")){
                        n1 = "0";
                    }
                    tiempo = Integer.parseInt(n1);
                    ArrayList<Integer> subL = new ArrayList<Integer>();
                    subL.add(prio);
                    subL.add(tiempo);
                    subL.add(i);
                    procesing.add(0);
                    rondas.add(0);
                    finish.add(false);
                    resultado.add("");
                    
                    list.add(subL);
                    
                    e = 1;
                } catch (Exception l) {
                    System.out.println("Error.");
                }
                
            }
            
        }

    }

}

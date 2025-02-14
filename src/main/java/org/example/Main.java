package org.example;

import static org.example.Utils.Utils.pedirInt;

public class Main {

    private static ControlJuego control = ControlJuego.getInstance();

    public static void main(String[] args) {
        int opcInicial;

        do {
            opcInicial = menuInicial();

            switch (opcInicial) {
                case 1:
                    control.seleccionarLanzadera();
                    System.out.println();
                    int opc;

                    do{
                        opc =  menuLanzadera();
                        System.out.println();

                        switch (opc){
                            case 1:
                                control.planificarLanzamiento();
                                break;
                            case 2:
                                control.mostrarPersonalDisponible();
                                System.out.println();
                                break;
                            case 3:
                                control.mostrarEstadoLanzadera();
                                break;
                            case 4:
                                control.mostrarEstadoProximoLanzamiento();
                                break;
                            case 5:
                                control.embarcarTripulacion();
                                break;
                            case 6:
                                control.cargarSuministros();
                                break;
                            case 7:

                                break;
                            case 8:

                                break;
                            case 9:

                                break;
                            case 10:

                                break;
                            case 11:
                                System.out.println("Volviendo");
                                System.out.println();
                                break;
                        }
                    }while(opc != 11);
                    break;
                case 2:
                    System.out.println("Saliendo...");
                    control.cerrarSesion();
                    break;
            }
        }while(opcInicial != 2);
    }

    public static int menuInicial(){

        System.out.println("   Bienvenido a la Agencia Espacial");
        System.out.println("======================================");
        System.out.println("1. Elegir Lanzadera.");
        System.out.println("2. Salir.");
        System.out.print("Selecciona tu opcion: ");

        return pedirInt();
    }

    public static int menuLanzadera(){

        System.out.println("1. Planificar Lanzamiento.");
        System.out.println("2. Mostrar Personal Disponible.");
        System.out.println("3. Mostrar Estado de la Lanzadera.");
        System.out.println("4. Mostrar Estado del Proximo Lanzamiento.");
        System.out.println("5. Embarcar Tripulación.");
        System.out.println("6. Cargar Suministros.");
        System.out.println("7. Cancelar Lanzamiento.");
        System.out.println("8. Posponer Lanzamiento.");
        System.out.println("9. Realizar Lanzamiento.");
        System.out.println("10. Rellenar Tanques Lanzadera.");
        System.out.println("11. Volver.");

        System.out.print("Selecciona tu opcion: ");

        return pedirInt();
    }
}
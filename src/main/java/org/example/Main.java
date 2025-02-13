package org.example;

import static org.example.Utils.Utils.pedirInt;

public class Main {

    private static ControlJuego control = ControlJuego.getInstance();

    public static void main(String[] args) {
        control.establecerModificador();
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

                        switch (opc){
                            case 1:

                                break;
                            case 2:

                                break;

                            case 3:

                                break;
                            case 4:

                                break;
                            case 5:

                                break;
                            case 6:

                                break;
                            case 7:

                                break;
                            case 8:

                                break;
                            case 9:

                                break;
                            case 10:
                                System.out.println("Volviendo");
                                System.out.println();
                                break;
                        }
                    }while(opc != 10);
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
        System.out.println("1. Elegir Lanzadera");
        System.out.println("2. Salir");
        System.out.print("Selecciona tu opcion: ");

        return pedirInt();
    }

    public static int menuLanzadera(){

        System.out.println("1. Planificar Lanzamiento");
        System.out.println("2. Mostrar Personal Disponible");
        System.out.println("3. Mostrar Estado de la Lanzadera");
        System.out.println("4. Embarcar Tripulación");
        System.out.println("5. Cargar Suministros.");
        System.out.println("6. Cancelar Lanzamiento");
        System.out.println("7. Posponer Lanzamiento");
        System.out.println("8. Realizar Lanzamiento");
        System.out.println("9. Rellenar Tanques Lanzadera");
        System.out.println("10. Volver");

        System.out.print("Selecciona tu opcion: ");

        return pedirInt();
    }
}
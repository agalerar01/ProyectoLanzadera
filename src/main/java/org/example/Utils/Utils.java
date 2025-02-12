package org.example.Utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {

    public static int pedirInt() {
        Scanner es = new Scanner(System.in);
        int num = 0;
        boolean error;

        do{
            try{
                num = es.nextInt();
                error = false;
            }catch (InputMismatchException e){
                error = true;
                System.out.println("Error Introduce de nuevo el valor");
                es.nextLine();
            }
        }while(error == true);

        return num;
    }

    public static String pedirString() {
        Scanner es = new Scanner(System.in);
        String string = es.nextLine();

        return string;
    }
}

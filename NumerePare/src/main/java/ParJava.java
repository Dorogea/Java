import java.util.ArrayList;
import java.util.Scanner;

public class ParJava {

    public static void main(String[] args) 
    {
        Scanner numarPar = new Scanner(System.in);

        System.out.print("Introduceti cateva numere : ");
        String input = numarPar.nextLine();

        String[] matrice = input.split(" ");
        int[] intArr = new int[matrice.length];
        for (int i = 0; i < matrice.length; i++) 
        {
            intArr[i] = Integer.parseInt(matrice[i]);
        }

        ArrayList<Integer> nrPar = new ArrayList<>();
        for (int num : intArr) 
        {
            if (num % 2 == 0) 
            {
                nrPar.add(num);
            }
        }

        System.out.println("Numerele pare sunt: " + nrPar);
    }
}
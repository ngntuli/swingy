package swingy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileMethods {

    public static int inpuInt;
    private static String acceptInputString = "";

    public static String acceptingInput() {
        try {
            BufferedReader readIn = new BufferedReader(new
                    InputStreamReader(System.in));

            String string;
            string = readIn.readLine();
            acceptInputString = string;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return (acceptInputString);
    }

    public static int rightInt(int from, int to) {

        String str;

        while (true) {
            str = "";

            while (str.equals(""))
                str = acceptingInput();

            if (!str.matches("^[0-9]+"))
                System.err.println("Please Enter Correct Integer!");
            else if ((Integer.parseInt(acceptInputString) >= from && Integer.parseInt(acceptInputString) <= to))
                return (inpuInt = Integer.parseInt(str));
            else
                System.out.println("ENTER VALUE From " + from + " to " + to);
        }
    }
}


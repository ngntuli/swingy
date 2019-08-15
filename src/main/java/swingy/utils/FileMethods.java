package swingy.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileMethods {
    public static String acceptInputString = "";
    private static File file = null;
    private static String FILENAME = "playerStats.txt";
    private static PrintWriter output;
    private static List<String> heroStats = new ArrayList<String>();

    public static void addHeroInfo(String adh) {
        heroStats.add(adh);
        System.out.println("Hero is ADDED on the List");
    }

    public static void showHeroesInfo() {
        System.out.println("\n Product completed as below");
        for (int i = 0; i < heroStats.size(); i++) {
            System.out.println(heroStats.get(i));
        }
    }

    public static void createFile() {
        try {
            if (file == null) {
                file = new File(FILENAME);
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile() {
        try {
            output = new PrintWriter(file);
            for (int i = 0; i < heroStats.size(); i++)
                output.println(heroStats.get(i));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeFile() {
        if (output != null)
            output.close();
    }

    public static void writeToList() {
        try {
            file = new File(FILENAME);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null)
                heroStats.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static int rightInt() {
        String string;

        while (true) {
            string = "";

            while (string.equals(""))
                string = acceptingInput();

            if (!string.matches("^[0-9]+"))
                System.out.println(Colors.ANSI_RED + "Enter right number" + Colors.ANSI_RESET);
            else
                return Integer.parseInt(string);
        }
    }
}

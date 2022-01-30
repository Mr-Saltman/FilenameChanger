package Tool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    static List<File> filesInFolder;
    static String name;
    static String season;
    static String start_episode;
    static String filetype;
    static int counter;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (true) {
            counter++;
            if(!(counter > 1)){ System.out.println("=== Filename Changer by ACS ==="); }
            System.out.print("Enter directory (nothing for current): ");

            input = scanner.nextLine();

            if (input.equals("")) {
                System.out.println("Current directory");
                input = System.getProperty("user.dir");
            } else {
                System.out.println("Directory: " + input);
            }

            System.out.println("");

            try {
                filesInFolder = Files.walk(Paths.get(input))
                        .filter(Files::isRegularFile)
                        .map(Path::toFile)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (File file : filesInFolder) {
                System.out.println(file.getName());
            }

            System.out.println("\nWhat is the name of the Series?");
            name = scanner.nextLine();

            System.out.println("What is the season of the series?");
            season = scanner.nextLine();
            if (Integer.parseInt(season) < 10) {
                season = "0" + season;
            }

            System.out.println("What episode (number) is the first one?");
            start_episode = scanner.nextLine();

            System.out.println("What filetype are your videos? (without '.')");
            filetype = "." + scanner.nextLine();

            System.out.println("\nInformation gathered: " + name + ", Season: " + season + ", Episode(Start): " + start_episode + "\n");

            for (File file : filesInFolder) {
                if (Integer.parseInt(start_episode) < 10) {
                    start_episode = "0" + start_episode;
                }
                String newFilename = name + " - s" + season + "e" + start_episode + filetype;
                boolean success = file.renameTo(new File(file.getParentFile(), newFilename));
                System.out.println(newFilename + "; Success: " + success);

                start_episode = "" + (Integer.parseInt(start_episode) + 1);
            }

            System.out.println("\nDo you want to exit the application? (y/n)");

            input = scanner.nextLine();

            switch(input){
                case "y":
                    System.exit(0);
                case "n":
            }
        }
    }
}

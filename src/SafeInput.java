import java.util.Scanner;

public class SafeInput {

    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = "";
        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        } while (retString.length() == 0);
        return retString;
    }

    public static int getInt(Scanner pipe, String prompt) {
        int retVal = 0;
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextInt()) {
                retVal = pipe.nextInt();
                pipe.nextLine();
                valid = true;
            } else {
                String trash = pipe.nextLine();
                System.out.println("Error: " + trash + " is not a valid integer.");
            }
        } while (!valid);

        return retVal;
    }

    public static double getDouble(Scanner pipe, String prompt) {
        double retVal = 0;
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextDouble()) {
                retVal = pipe.nextDouble();
                pipe.nextLine();
                valid = true;
            } else {
                String trash = pipe.nextLine();
                System.out.println("Error: " + trash + " is not a valid double.");
            }
        } while (!valid);

        return retVal;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int retVal = 0;
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: ");

            if (pipe.hasNextInt()) {
                retVal = pipe.nextInt();
                pipe.nextLine();

                if (retVal >= low && retVal <= high) {
                    valid = true;
                } else {
                    System.out.println("Error: number must be between " + low + " and " + high + ".");
                }
            } else {
                String trash = pipe.nextLine();
                System.out.println("Error: " + trash + " is not a valid integer.");
            }

        } while (!valid);

        return retVal;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double retVal = 0;
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: ");

            if (pipe.hasNextDouble()) {
                retVal = pipe.nextDouble();
                pipe.nextLine(); // clear buffer

                if (retVal >= low && retVal <= high) {
                    valid = true;
                } else {
                    System.out.println("Error: number must be between " + low + " and " + high + ".");
                }
            } else {
                String trash = pipe.nextLine();
                System.out.println("Error: " + trash + " is not a valid double.");
            }

        } while (!valid);

        return retVal;
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String response = "";
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + ": ");
            response = pipe.nextLine();

            if (response.matches(regEx)) {
                valid = true;
            } else {
                System.out.println("Error: Input must match the pattern " + regEx);
            }

        } while (!valid);

        return response;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String response = "";
        boolean valid = false;

        do {
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = pipe.nextLine().trim();

            if (response.equalsIgnoreCase("Y")) {
                return true;
            } else if (response.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Error: Please enter Y or N.");
            }

        } while (!valid);

        return false;
    }
    public static void prettyHeader(String msg) {
        int totalWidth = 60;

        // top line
        for (int i = 0; i < totalWidth; i++) {
            System.out.print("*");
        }
        System.out.println();

        // middle line
        int spaces = totalWidth - msg.length() - 6; // 6 stars on sides
        int padLeft = spaces / 2;
        int padRight = spaces - padLeft;

        System.out.print("***");
        for (int i = 0; i < padLeft; i++) System.out.print(" ");
        System.out.print(msg);
        for (int i = 0; i < padRight; i++) System.out.print(" ");
        System.out.println("***");

        // bottom line
        for (int i = 0; i < totalWidth; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class FileListMaker {
    private static ArrayList<String> list = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static Path currentFile = null;  // current loaded file
    private static boolean needsToBeSaved = false; // dirty flag

    public static void main(String[] args) {
        String choice;
        do {
            displayMenu();
            choice = SafeInput.getRegExString(sc, "Choose an option: ", "[AaDdIiMmOoSsCcVvQq]").toUpperCase();

            switch (choice) {
                case "A": // Add item
                    addItem();
                    break;
                case "D": // Delete item
                    deleteItem();
                    break;
                case "I": // Insert item
                    insertItem();
                    break;
                case "M": // Move item
                    moveItem();
                    break;
                case "O": // Open file
                    openFile();
                    break;
                case "S": // Save file
                    saveFile();
                    break;
                case "C": // Clear list
                    clearList();
                    break;
                case "V": // View list
                    viewList();
                    break;
                case "Q": // Quit
                    quitProgram();
                    break;
            }

        } while (!choice.equalsIgnoreCase("Q"));
    }

    private static void displayMenu() {
        System.out.println("\n--- List Menu ---");
        System.out.println("A – Add item");
        System.out.println("D – Delete item");
        System.out.println("I – Insert item");
        System.out.println("M – Move item");
        System.out.println("O – Open list file");
        System.out.println("S – Save list");
        System.out.println("C – Clear list");
        System.out.println("V – View list");
        System.out.println("Q – Quit");
    }

    private static void addItem() {
        String item = SafeInput.getNonZeroLenString(sc, "Enter item to add:");
        list.add(item);
        needsToBeSaved = true;
        System.out.println("Item added.");
    }

    private static void deleteItem() {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }
        viewList();
        int index = SafeInput.getRangedInt(sc, "Enter item number to delete:", 1, list.size()) - 1;
        String removed = list.remove(index);
        needsToBeSaved = true;
        System.out.println("Removed: " + removed);
    }

    private static void insertItem() {
        String item = SafeInput.getNonZeroLenString(sc, "Enter item to insert:");
        int index = SafeInput.getRangedInt(sc, "Enter position to insert at (1-" + (list.size() + 1) + "):", 1, list.size() + 1) - 1;
        list.add(index, item);
        needsToBeSaved = true;
        System.out.println("Item inserted.");
    }

    private static void moveItem() {
        if (list.size() < 2) {
            System.out.println("Need at least 2 items to move.");
            return;
        }
        viewList();
        int from = SafeInput.getRangedInt(sc, "Enter item number to move:", 1, list.size()) - 1;
        int to = SafeInput.getRangedInt(sc, "Enter new position for this item:", 1, list.size()) - 1;
        String item = list.remove(from);
        list.add(to, item);
        needsToBeSaved = true;
        System.out.println("Item moved.");
    }

    private static void openFile() {
        if (needsToBeSaved) {
            boolean save = SafeInput.getYNConfirm(sc, "Unsaved changes exist. Save first?");
            if (save) {
                saveFile();
            }
        }
        String filename = SafeInput.getNonZeroLenString(sc, "Enter file name to open (.txt):");
        Path file = Path.of("src/" + filename);
        try {
            list = new ArrayList<>(Files.readAllLines(file));
            currentFile = file;
            needsToBeSaved = false;
            System.out.println("File loaded: " + filename);
        } catch (IOException e) {
            System.out.println("Failed to open file: " + e.getMessage());
        }
    }

    private static void saveFile() {
        try {
            if (currentFile == null) { // new file
                String filename = SafeInput.getNonZeroLenString(sc, "Enter file name to save (.txt):");
                currentFile = Path.of("src/" + filename);
            }
            Files.write(currentFile, list);
            needsToBeSaved = false;
            System.out.println("List saved to " + currentFile.getFileName());
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void clearList() {
        if (SafeInput.getYNConfirm(sc, "Are you sure you want to clear the list?")) {
            list.clear();
            needsToBeSaved = true;  // list changed
            System.out.println("List cleared. Old file is safe until you save.");
        }
    }

    private static void viewList() {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }
        System.out.println("Current List:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ": " + list.get(i));
        }
    }

    private static void quitProgram() {
        if (needsToBeSaved) {
            boolean save = SafeInput.getYNConfirm(sc, "Unsaved changes exist. Save before quitting?");
            if (save) {
                saveFile();
            }
        }
        System.out.println("Exiting program. Goodbye!");
    }
}
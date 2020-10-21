package controller;

import model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Gianpaolo & Brigthen
 */
public class ServiceMenuController implements Serializable {

    public static final String FILE_NAME = "data/menu.txt";
    private static ArrayList<ServiceMenuItem> menu = new ArrayList<>();

    public ServiceMenuController() {
    }

    public static void displayMainMenu() {
        //Write main function menu instructions
        Scanner sc = new Scanner(System.in);

        System.out.println("\n===============================================");
        System.out.println("          Room Service Menu Function");
        System.out.println("===============================================");

        System.out.print("1.Add Room Service Menu" +
                "\n2.Update Room Service Menu" +
                "\n3.Remove Room Service Menu" +
                "\nOther Numerical Number to Exit" +
                "\nChoice:");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                addMenuItem();
                break;
            case 2:
                updateMenuItem();
                break;
            case 3:
                removeMenuItem();
                break;
            default:
                break;
        }
    }

    public static void displayMenuItems() {
        //Display (iterate) menu list - index each item
        System.out.println("ID - Name @ Price");
        for (int i = 0; i < menu.size(); i++) {
            //System.out.println(i + " - " + menu.get(i).getName() + " @ $" + menu.get(i).getPrice());
            System.out.printf("%s - %s @ $%.2f", i, menu.get(i).getName(), menu.get(i).getPrice());
        }
    }

    public static void addMenuItem() {
        //Write console instructions
        //Get new item details
        Scanner sc = new Scanner(System.in);

        System.out.println("=================");
        System.out.println("Add New Menu Item");
        System.out.println("=================");

        System.out.print("Menu Item Name: ");
        String name = sc.nextLine();

        System.out.print("Menu Item Descirption: ");
        String desc = sc.nextLine();

        double price = 0.0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("Menu Item Price: ");
                price = Double.parseDouble(sc.nextLine());
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid price input");
                System.out.println("Exception - " + e);
                valid = false;
            }
        }

        //Create new item obj and add to menu list
        menu.add(new ServiceMenuItem(name, desc, price));
    }

    public static void updateMenuItem() {
        //Write console instructions
        //Get item to update (use list index as identifier)
        Scanner sc = new Scanner(System.in);

        System.out.println("================");
        System.out.println("Update Menu Item");
        System.out.println("================");

        displayMenuItems();

        int index = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("ID of item to update: ");
                index = Integer.parseInt(sc.nextLine());
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid input");
                System.out.println("Exception - " + e);
                valid = false;
            }
        }

        //Go over each item detail update (use set methods)
        System.out.println("=======================================");
        System.out.println("    Updating Menu ID " + index);
        System.out.println("    Enter '.' to retain the information");
        System.out.println("=======================================");

        System.out.println("Item Name - OLD: " + menu.get(index).getName());
        System.out.print("          - NEW: ");
        String newName = sc.nextLine();
        if (!newName.equals(".")) {
            menu.get(index).setName(newName);
        }

        System.out.println("Item Description - OLD: " + menu.get(index).getDescription());
        System.out.print("                 - NEW: ");
        String newDesc = sc.nextLine();
        if (!newDesc.equals(".")) {
            menu.get(index).setDescription(newDesc);
        }

        System.out.print("Item Price - OLD: " + menu.get(index).getPrice());
        double newPrice = 0.0;
        valid = false;

        while (!valid) {
            try {
                System.out.print("           - NEW: ");
                String input = sc.nextLine();

                if (input.equals(".")) {
                    break;
                }

                newPrice = Double.parseDouble(input);
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid price input");
                System.out.println("Exception - " + e);
                valid = false;
            }
        }

        menu.get(index).setPrice(newPrice);

        System.out.println("\nFinished Updating Menu Item\n");
    }

    public static void removeMenuItem() {
        //Write console instructions
        //Get item to remove (use list index as identifier)
        Scanner sc = new Scanner(System.in);

        System.out.println("================");
        System.out.println("Remove Menu Item");
        System.out.println("================");

        displayMenuItems();

        int index = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("ID of item to remove: ");
                index = Integer.parseInt(sc.nextLine());
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid input");
                System.out.println("Exception - " + e);
                valid = false;
            }
        }

        //Confirm action
        System.out.print("Are you sure you would like to remove - " + menu.get(index) + "? (y/n) ");
        String choice = sc.nextLine();
        if (choice.equals("n") || choice.equals("N")) {
            System.out.println("Delete cancelled");
            return;
        }

        //Remove item from menu list
        String deletedName = menu.get(index).getName();
        menu.remove(index);
        System.out.println("Successfully deleted - " + deletedName);
    }

    public static ArrayList<ServiceMenuItem> getMenu() {
        return menu;
    }

    public static void saveData() {
        try {
            FileWriter FileWriter = new FileWriter(FILE_NAME);
            StringBuilder sb = new StringBuilder();

            for (ServiceMenuItem i : menu) {
                sb.append(i.getName()).append(",");
                sb.append(i.getDescription()).append(",");
                sb.append(i.getPrice()).append(",");
                sb.append("\n");
            }

            FileWriter.write(sb.toString());
            FileWriter.flush();
            FileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadData() {
        menu = new ArrayList<>();

        try {
            // Reading the object from a file
            // FileReader fileReader = new FileReader(stored_File_Name);
            File fileReader = new File(FILE_NAME);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                String dataPerLine = scanner.nextLine();
                String[] arrSplit = dataPerLine.split(",");

                String name = arrSplit[0];
                String description = arrSplit[1];
                double price = Double.parseDouble(arrSplit[2]);

                menu.add(new ServiceMenuItem(name, description, price));
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            //  check the conditions after loading the data if room is null
            if (menu.isEmpty()) {
                menu = new ArrayList<>();
            }
        }
    }
}

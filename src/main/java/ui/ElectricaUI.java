package ui;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.sun.deploy.util.StringUtils;
import exceptions.ElectricaException;
import model.*;
import controller.ClientController;;

public class ElectricaUI {
    public ClientController ctrl;
    Scanner in;

    public ElectricaUI(ClientController ctrl) {
        this.ctrl = ctrl;
        this.in = new Scanner(System.in);
    }

    public ClientController getCtrl() {
        return this.ctrl;
    }

    public Scanner getIn() {
        return this.in;
    }

    public void setCtrl(ClientController newCtrl) {
        this.ctrl = newCtrl;
    }

    public void setIn(Scanner newIn) {
        this.in = newIn;
    }

    public void printMenu() {
        String menu;
        menu = "Electrica Admin Menu: \n";
        menu += "\t 1 - to add a new client; \n";
        menu += "\t 2 - to add a new index; \n";
        menu += "\t 3 - to list the current invoices (and possible penalties or pending payment) of the subscribers; \n";
        menu += "\t 0 - exit \n";

        System.out.println(menu);
    }


    public void run() {
        boolean run = true;
        int cmd;
        while (run) {
            printMenu();


            cmd = readInt();

            switch (cmd) {
                case 0:
                    run = false;
                    break;
                case 1:
                    addClient();
                    break;
                case 2:
                    addIndex();
                    break;
                case 3:
                    getClientInvoices();
                    break;
                default:
            }
        }
    }

    public Integer readInt() {
        String baseCommand;
        baseCommand = in.next();
        in.nextLine();
        if (baseCommand.matches("[0-9]+")) {
            return Integer.parseInt(baseCommand);
        }
        return null;
    }

    public void addIndex() {
        System.out.println("Enter name:");
        String name = in.nextLine();
        System.out.println("Enter address:");
        String address = in.nextLine();
        System.out.println("Enter id:");
        String id = in.nextLine();
        Client c = new Client(name, address, id);


        System.out.println("Enter the YEAR:");
        Integer year = readInt();
        System.out.println("Enter the MONTH:");
        Integer month = readInt();
        System.out.println("Enter toPay:");
        Float sumToPay = readFloat();

        try {
            ctrl.addClientIndex(c, year, month, sumToPay);
        } catch (ElectricaException e) {
            System.out.println(e.getMessage());
        }
    }

    private Float readFloat() {
        String baseCommand;
        baseCommand = in.next();
        in.nextLine();
        if (baseCommand.matches("[0-9]+")) {
            return Float.parseFloat(baseCommand);
        }
        return null;
    }

    public void addClient() {
        System.out.println("Enter name:");
        String name = in.nextLine();
        System.out.println("Enter address:");
        String address = in.nextLine();
        System.out.println("Enter id:");
        String id = in.nextLine();

        try {
            ctrl.addClient(name, address, id);
        } catch (ElectricaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getClientInvoices() {
        System.out.println("list the current invoices :");

        System.out.println("Enter name:");
        String name = in.nextLine();
        System.out.println("Enter address:");
        String address = in.nextLine();
        System.out.println("Enter id:");
        String id = in.nextLine();


        System.out.println(ctrl.listIssue(name, address, id));
    }
}


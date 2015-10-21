package fundstarterclient;

import com.sun.corba.se.spi.activation.Server;
import fundstarter.ServerMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by xavier on 19-10-2015.
 */
public class ShowMenus {
    private CommandFormat command;

    public ShowMenus(Menu menu, CommandFormat command) {
        this.command = command;
    }
    public ShowMenus(){
        this.command = new CommandFormat();
    }


    public void initiateMenuDrivenWithClient(ObjectInputStream in, DataOutputStream out) throws IOException, ClassNotFoundException {
        ServerMessage message;
        Menu menu1 = new Menu();
        menu1.addOption("Login");
        menu1.addOption("Sign Up");
        menu1.addOption("Quit");
        menu1.setAnswerPrompt("Please enter your choice: ");

        System.out.println(menu1.toString());

        int optionChosen = messageInteractionWithClient(menu1);

        do{
            switch(optionChosen){
                case 1:
                    this.login();
                    break;
                case 2:
                    this.signUp();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Please choose an option between 1 and 3");
            }
            out.writeUTF(command.getCommand());
            System.out.println(command.getCommand());
            message = (ServerMessage) in.readObject();
        }while(message.verifyAnswer(message) == 1);

        this.secondMenuDrivenWithClient(in, out);

    }

    public void secondMenuDrivenWithClient(ObjectInputStream in, DataOutputStream out) throws IOException, ClassNotFoundException {
        Menu menu2 = new Menu();
        menu2.addOption("Projects");
        menu2.addOption("Personal Area");
        menu2.addOption("Logout");
        menu2.setAnswerPrompt("Please enter your choice: ");

        System.out.printf(menu2.toString());

        int optionChosen = messageInteractionWithClient(menu2);

        switch(optionChosen){
            case 1:
                thirdMenuDrivenWithClient(in, out);
                break;
            case 2:
                personalAreaSubMenu(in, out);
                break;
            case 3:
                this.initiateMenuDrivenWithClient(in, out);
                break;
            default:
        }

    }

    public void personalAreaSubMenu(ObjectInputStream in, DataOutputStream out) throws IOException, ClassNotFoundException {
        ServerMessage message;
        Menu subMenu = new Menu();
        subMenu.addOption("Messages");
        subMenu.addOption("Offer reward");
        subMenu.addOption("Back");
        subMenu.setAnswerPrompt("Please enter your choice: ");

        System.out.printf(subMenu.toString());

        int optionChosen = messageInteractionWithClient(subMenu);

        do{
            switch(optionChosen){
                case 1:
                    command.setCommand("messages");
                    break;
                case 2:
                    this.offerReward();
                    break;
                case 3:
                    secondMenuDrivenWithClient(in, out);
                    break;
                default:
                    System.out.println("Choose an option between 1 and 5");
                    break;
            }
            out.writeUTF(command.getCommand());
            //System.out.println(command.getCommand());
            message = (ServerMessage) in.readObject();
        }while(message.verifyAnswer(message) == 1);

    }

    public void thirdMenuDrivenWithClient(ObjectInputStream in, DataOutputStream out) throws IOException, ClassNotFoundException {
        ServerMessage message;
        Menu menu3 = new Menu();
        command.setCommand("");

        menu3.addOption("Projects in Progress");
        menu3.addOption("Expired projects");
        menu3.addOption("New project");
        menu3.addOption("My projects");
        menu3.addOption("Back");

        System.out.print(menu3.toString());

        int optionChosen = messageInteractionWithClient(menu3);

        do{
            switch(optionChosen){
                case 1:
                    command.setCommand("projects in progress");
                    break;
                case 2:
                    command.setCommand("expired projects");
                    break;
                case 3:
                    this.newProject();
                    break;
                case 4:
                    command.setCommand("my projects");
                    break;
                case 5:
                    secondMenuDrivenWithClient(in, out);
                    break;
                default:
                    System.out.println("Choose an option between 1 and 5");
                    break;
            }
            out.writeUTF(command.getCommand());
            //System.out.println(command.getCommand());
            message = (ServerMessage) in.readObject();
        }while(message.verifyAnswer(message) == 1);
    }

    public int messageInteractionWithClient(Menu menu){
        String rawOptionInput = "";
        int optionInput = 0;
        Boolean inputValidation;
        Scanner scan = new Scanner(System.in);

        do {
            try{
                rawOptionInput = scan.nextLine();
                optionInput = Integer.parseInt(rawOptionInput);
                inputValidation = true;

                OptionList menuAssociatedOptionList = menu.getOptionsList();
                if (optionInput <= 0 || optionInput > menuAssociatedOptionList.getSize())
                    throw new InputMismatchException();
            } catch (InputMismatchException e) {
                inputValidation = false;
            }
        } while(!inputValidation);

        return optionInput;
    }

    // Login method
    public void login(){
        Scanner scan = new Scanner(System.in);

        command.setCommand("login");
        System.out.print("Username: ");
        command.appendString(scan.nextLine());
        System.out.print("Password: ");
        command.appendString(scan.nextLine());
    }

    public void signUp(){
        Scanner scan = new Scanner(System.in);
        command.setCommand("signup");
        System.out.print("Username: ");
        command.appendString(scan.nextLine());
        System.out.print("Password: ");
        command.appendString(scan.nextLine());
        System.out.print("Repeat Password: ");
        command.appendString(scan.nextLine());
    }

    public void newProject(){
        Scanner scan = new Scanner(System.in);

        command.setCommand("newproject");
        System.out.print("Project Name: ");
        command.appendString(scan.nextLine());
        command.appendString("'");
        System.out.print("Description: ");
        command.appendString(scan.nextLine());
        command.appendString("'");
        System.out.print("Limit date (DD/MM/YYYY): ");
        command.appendString(scan.nextLine());
        System.out.print("Amount requested: ");
        command.appendString(scan.nextLine());
    }

    public void offerReward(){
        Scanner scan = new Scanner(System.in);

        command.setCommand("reward");
        System.out.print("Project Name: ");
        command.appendString(scan.nextLine());
        System.out.print("Price: ");
        command.appendString(scan.nextLine());
    }


}

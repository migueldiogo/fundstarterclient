package fundstarterclient;

import fundstarter.Command;
import fundstarter.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Created by xavier on 19-10-2015.
 */
public class ShowMenus {
    private Command command2;
    private String loggedPerson;

    public ShowMenus(Menu menu, Command command, String loggedPerson) {
        this.command2 = command;
        this.loggedPerson = loggedPerson;
    }
    public ShowMenus(){
        this.command2 = new Command();
        this.loggedPerson = "";
    }


    public void initiateMenuDrivenWithClient(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        ServerMessage message;
        Menu menu1 = new Menu();
        Command testCommand = null;
        menu1.addOption("Login");
        menu1.addOption("Sign Up");
        menu1.addOption("Quit");
        menu1.setAnswerPrompt("Please enter your choice: ");


        System.out.println(menu1.toString());

        int optionChosen = messageInteractionWithClient(menu1);

        switch(optionChosen){
            case 1:
                testCommand = this.login();
                out.writeObject(testCommand);
                message = (ServerMessage) in.readObject();
                System.out.println((String)message.getContent());
                if(message.decodeLogin() == 0)
                    this.secondMenuDrivenWithClient(in, out);
                else
                    this.initiateMenuDrivenWithClient(in, out);     break;
            case 2:
                testCommand = this.signUp();
                out.writeObject(testCommand);
                message = (ServerMessage) in.readObject();
                System.out.println((String) message.getContent());
                this.initiateMenuDrivenWithClient(in, out);        break;
            case 3:
                System.exit(0);
            default:
                System.out.println("Please choose an option between 1 and 3"); break;
        }

        //this.secondMenuDrivenWithClient(in, out);
    }

    public void secondMenuDrivenWithClient(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Menu menu2 = new Menu();
        menu2.addOption("Projects");
        menu2.addOption("Personal Area");
        menu2.addOption("Logout");
        menu2.setAnswerPrompt("Please enter your choice: ");

        System.out.printf(menu2.toString());

        int optionChosen = messageInteractionWithClient(menu2);

        switch(optionChosen){
            case 1:
                thirdMenuDrivenWithClient(in, out);         break;
            case 2:
                personalAreaSubMenu(in, out);               break;
            case 3:
                this.initiateMenuDrivenWithClient(in, out); break;
            default:
                System.out.println("Choose an option between 1 and 3");
        }

    }

    public void personalAreaSubMenu(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        ServerMessage message;
        Menu subMenu = new Menu();
        subMenu.addOption("View messages");
        subMenu.addOption("Offer reward");
        subMenu.addOption("View balance");
        subMenu.addOption("View rewards");
        subMenu.addOption("Offer reward to person");
        subMenu.addOption("Back");
        subMenu.setAnswerPrompt("Please enter your choice: ");

        System.out.printf(subMenu.toString());

        int optionChosen = messageInteractionWithClient(subMenu);

        //do{
            switch(optionChosen){
                case 1:
                    viewBalance(in, out);                   break;
                case 2:
                    this.offerReward();                         break;
                case 3:
                    command2.setCommand("view");
                    command2.addArgument("balance");             break;
                case 4:
                    command2.setCommand("view");
                    command2.addArgument("rewards");             break;
                case 5:
                    offerRewardToPerson();                      break;
                case 6:
                    secondMenuDrivenWithClient(in, out);        break;
                default:
                    System.out.println("Choose an option between 1 and 6"); break;
            }
            System.out.println(command2.getCommand());
            out.writeObject(command2);
            //message = (ServerMessage) in.readObject();
        //}while(message.verifyAnswer(message) == 1);

        // After doing something, back to main menu
        personalAreaSubMenu(in, out);

    }

    public void thirdMenuDrivenWithClient(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        ServerMessage message;
        Menu menu3 = new Menu();
        command2.setCommand("");

        menu3.addOption("Projects in Progress");
        menu3.addOption("Expired projects");
        menu3.addOption("New project");
        menu3.addOption("My projects");
        menu3.addOption("Back");

        System.out.print(menu3.toString());

        int optionChosen = messageInteractionWithClient(menu3);


        switch(optionChosen){
            case 1:
                command2.setCommand("list");
                command2.addArgument("inprogess");           break;
            case 2:
                command2.setCommand("list expired");
                command2.addArgument("expired");             break;
            case 3:
                this.newProject();                          break;
            case 4:
                command2.setCommand("list");
                command2.addArgument("myprojects");
                command2.addArgument(loggedPerson);          break;
            case 5:
                secondMenuDrivenWithClient(in, out);        break;
            default:
                System.out.println("Choose an option between 1 and 5");     break;
        }
        out.writeObject(command2);
        System.out.println(command2.getCommand());
        //message = (ServerMessage) in.readObject();


        if(optionChosen == 1){
            this.projectDetails();
        }

        thirdMenuDrivenWithClient(in, out);
    }

    public void projectDetailsSubMenu(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        Menu subMenu = new Menu();
        String projectName = ""; // TODO //

        subMenu.addOption("Pledge");
        subMenu.addOption("Messages");
        subMenu.addOption("Details");
        subMenu.addOption("Send message");
        subMenu.addOption("Back");
        System.out.println(subMenu.toString());

        int optionChosen = messageInteractionWithClient(subMenu);

        switch(optionChosen){
            case 1:
                pledge(projectName);            break;
            case 2:
                command2.setCommand("messages"); break;
            case 3:
                projectDetailsSubMenu(in, out); break;
            case 4:
                sendMessage();                  break;
            default:
                System.out.println("Choose an option between 1 and 4"); break;
        }
        out.writeObject(command2);
        System.out.println(command2.getCommand());

        projectDetailsSubMenu(in, out);
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
    public Command login(){
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("login");
        System.out.print("Username: "); command.addArgument(loggedPerson = scan.nextLine());
        System.out.print("Password: "); command.addArgument(scan.nextLine());

        return command;
    }

    public Command signUp(){
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("signup");
        System.out.print("Username: ");
        command.addArgument(scan.nextLine());
        System.out.print("Password: ");
        command.addArgument(scan.nextLine());
        System.out.print("Repeat Password: ");  command.addArgument(scan.nextLine());

        return command;
    }

    public void newProject(){
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("newproject");
        System.out.print("Project Name: ");
        command.addArgument(scan.nextLine());
        System.out.print("Description: ");
        command.addArgument(scan.nextLine());
        System.out.print("Limit date (DD/MM/YYYY): ");
        command.addArgument(scan.nextLine());
        System.out.print("Amount requested: ");
        command.addArgument(scan.nextLine());
        System.out.print("Rewards: ");  command.addArgument(scan.nextLine());
    }

    public void offerReward(){
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("reward");
        System.out.print("Project Name: ");
        command.addArgument(scan.nextLine());
        System.out.print("Reward: ");   command.addArgument(scan.nextLine());
    }

    public void pledge(String projectName){
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("pledge");
        command.addArgument(projectName);
        System.out.print("Price");  command.addArgument(scan.nextLine());
    }

    public void projectDetails(){
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        System.out.print("Choose project: ");
        command.setCommand("details");  command.addArgument(scan.nextLine());
    }

    public void sendMessage(){
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("sendtext");
        System.out.print("Project Name: ");
        command.addArgument(scan.nextLine());
        System.out.println("Message: ");
        command.addArgument(scan.nextLine());
        command.addArgument(loggedPerson);
    }

    public void offerRewardToPerson(){
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("sendreward");
        System.out.print("Person name: ");
        command.addArgument(scan.nextLine());
        System.out.print("Reward: ");
        command.addArgument(scan.nextLine());
        command.addArgument(loggedPerson);
    }

    public void viewBalance(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException
        command2.setCommand("view");
        command2.addArgument("messages");
    }


}

package fundstarterclient;

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
    private Command command;
    private String loggedPerson;

    public ShowMenus(Menu menu, Command command, String loggedPerson) {
        this.command = command;
        this.loggedPerson = loggedPerson;
    }
    public ShowMenus(){
        this.command = new Command();
        this.loggedPerson = "";
    }


    public void initiateMenuDrivenWithClient(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        ServerMessage message;
        Menu menu1 = new Menu();
        menu1.addOption("Login");
        menu1.addOption("Sign Up");
        menu1.addOption("Quit");
        menu1.setAnswerPrompt("Please enter your choice: ");

        System.out.println(menu1.toString());

        int optionChosen = messageInteractionWithClient(menu1);

        //do{
            switch(optionChosen){
                case 1:
                    loggedPerson = this.login();       break;
                case 2:
                    this.signUp();                     break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Please choose an option between 1 and 3"); break;
            }
            System.out.println(command.getCommand());
            out.writeObject(command);
        //}while(message.verifyAnswer(message) == 1);

        this.secondMenuDrivenWithClient(in, out);

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
                // TODO //
        }

    }

    public void personalAreaSubMenu(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        ServerMessage message;
        Menu subMenu = new Menu();
        subMenu.addOption("Messages");
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
                    command.setCommand("view messages");        break;
                case 2:
                    this.offerReward();                         break;
                case 3:
                    command.setCommand("view balance");         break;
                case 4:
                    command.setCommand("view rewards");         break;
                case 5:
                    offerRewardToPerson();                              break;
                case 6:
                    secondMenuDrivenWithClient(in, out);        break;
                default:
                    System.out.println("Choose an option between 1 and 5"); break;
            }
            System.out.println(command.getCommand());
            out.writeObject(command);
            //message = (ServerMessage) in.readObject();
        //}while(message.verifyAnswer(message) == 1);

        // After doing something, back to main menu
        personalAreaSubMenu(in, out);

    }

    public void thirdMenuDrivenWithClient(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
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


        switch(optionChosen){
            case 1:
                command.setCommand("list inprogress");      break;
            case 2:
                command.setCommand("list expired");         break;
            case 3:
                this.newProject();                          break;
            case 4:
                command.setCommand("list myprojects " + loggedPerson);      break;
            case 5:
                secondMenuDrivenWithClient(in, out);        break;
            default:
                System.out.println("Choose an option between 1 and 5");     break;
        }
        out.writeObject(command);
        System.out.println(command.getCommand());
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
                command.setCommand("messages"); break;
            case 3:
                projectDetailsSubMenu(in, out); break;
            case 4:
                sendMessage();                  break;
            default:
                System.out.println("Choose an option between 1 and 4"); break;
        }
        out.writeObject(command);
        System.out.println(command.getCommand());

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
    public String login(){
        Scanner scan = new Scanner(System.in);

        command.setCommand("login");
        System.out.print("Username: "); command.arguments.add(loggedPerson = scan.nextLine());
        System.out.print("Password: "); command.arguments.add(scan.nextLine());

        return loggedPerson;
    }

    public void signUp(){
        Scanner scan = new Scanner(System.in);
        command.setCommand("signup");
        System.out.print("Username: ");
        command.arguments.add(scan.nextLine());
        System.out.print("Password: ");
        command.arguments.add(scan.nextLine());
        System.out.print("Repeat Password: ");  command.arguments.add(scan.nextLine());
    }

    public void newProject(){
        Scanner scan = new Scanner(System.in);

        command.setCommand("newproject");
        System.out.print("Project Name: ");
        command.arguments.add(scan.nextLine());
        System.out.print("Description: ");
        command.arguments.add(scan.nextLine());
        System.out.print("Limit date (DD/MM/YYYY): ");
        command.arguments.add(scan.nextLine());
        System.out.print("Amount requested: ");
        command.arguments.add(scan.nextLine());
        System.out.print("Rewards: ");  command.arguments.add(scan.nextLine());
    }

    public void offerReward(){
        Scanner scan = new Scanner(System.in);

        command.setCommand("reward");
        System.out.print("Project Name: ");
        command.arguments.add(scan.nextLine());
        System.out.print("Reward: ");   command.arguments.add(scan.nextLine());
    }

    public void pledge(String projectName){
        Scanner scan = new Scanner(System.in);

        command.setCommand("pledge");
        command.arguments.add(projectName);
        System.out.print("Price");  command.arguments.add(scan.nextLine());
    }

    public void projectDetails(){
        Scanner scan = new Scanner(System.in);

        System.out.print("Choose project: ");
        command.setCommand("details");  command.arguments.add(scan.nextLine());
    }

    public void sendMessage(){
        Scanner scan = new Scanner(System.in);

        command.setCommand("sendtext");
        System.out.print("Project Name: ");
        command.arguments.add(scan.nextLine());
        System.out.println("Message: ");
        command.arguments.add(scan.nextLine());
        command.arguments.add(loggedPerson);
    }

    public void offerRewardToPerson(){
        Scanner scan = new Scanner(System.in);

        command.setCommand("sendreward");
        System.out.print("Person name: ");
        command.arguments.add(scan.nextLine());
        System.out.print("Reward: ");
        command.arguments.add(scan.nextLine());
        command.arguments.add(loggedPerson);
    }


}

package fundstarterclient;

import fundstarter.Command;
import fundstarter.ServerMessage;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Created by xavier on 19-10-2015.
 */
public class ShowMenus {
    private Command command2;
    private String loggedPerson;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ShowMenus(Menu menu, Command command, String loggedPerson) {
        this.command2 = command;
        this.loggedPerson = loggedPerson;
    }
    public ShowMenus(ObjectInputStream inputStream, ObjectOutputStream outputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.loggedPerson = "";
    }

    public void start() {
        mainMenu();
    }


    public void mainMenu() {


        ServerMessage message = null;

        Menu menu1 = new Menu();
        Command testCommand = null;
        menu1.addOption("Login");
        menu1.addOption("Sign Up");
        menu1.addOption("Quit");
        menu1.setAnswerPrompt("Please enter your choice: ");


        System.out.println(menu1.toString());

        int optionChosen = readOptionChosenByUser(menu1);

        switch(optionChosen){
            case 1:
                do {
                    try {
                        sendCommandToServer(login());
                        message = receiveResponseFromServer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                while (message.isErrorHappened());

                mainMenuWithUserLoggedIn();
            break;
            case 2:
                testCommand = this.signUp();
                outputStream.writeObject(testCommand);
                message = (ServerMessage) inputStream.readObject();
                System.out.println((String) message.getContent());
                this.mainMenu(inputStream, outputStream);        break;
            case 3:
                System.exit(0);
            default:
                System.out.println("Please choose an option between 1 and 3"); break;
        }

        //this.mainMenuWithUserLoggedIn(in, out);
    }

    public void mainMenuWithUserLoggedIn() {
        boolean voltar = false;

        Menu menu2 = new Menu();
        menu2.addOption("Projects");
        menu2.addOption("Personal Area");
        menu2.addOption("Logout");
        menu2.setAnswerPrompt("Please enter your choice: ");


        do {
            System.out.println(menu2.toString());

            int optionChosen = readOptionChosenByUser(menu2);

            switch (optionChosen) {
                case 1:
                    voltar = !thirdMenuDrivenWithClient();
                    break;
                case 2:
                    personalAreaSubMenu();
                    break;
                case 3:
                    this.mainMenu();
                    break;
                default:
                    System.out.println("Choose an option between 1 and 3");
            }
        } while (voltar);

    }

    public void personalAreaSubMenu() {
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

        int optionChosen = readOptionChosenByUser(subMenu);

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
                    mainMenuWithUserLoggedIn(in, out);        break;
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

    public boolean thirdMenuDrivenWithClient() {
        ServerMessage message;
        Menu menu3 = new Menu();
        command2.setCommand("");

        menu3.addOption("Projects in Progress");
        menu3.addOption("Expired projects");
        menu3.addOption("New project");
        menu3.addOption("My projects");
        menu3.addOption("Back");

        System.out.print(menu3.toString());

        int optionChosen = readOptionChosenByUser(menu3);


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
                return false;
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

        int optionChosen = readOptionChosenByUser(subMenu);

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


    public int readOptionChosenByUser(Menu menu){
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

    public void sendCommandToServer(Command command) throws IOException{
        outputStream.writeObject(command);
    }

    public ServerMessage receiveResponseFromServer() throws IOException{
        ServerMessage message = null;
        try {
            message = (ServerMessage) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not Found: " + e.getMessage());
        }
        return message;
    }


}

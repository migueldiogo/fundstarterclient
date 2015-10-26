package fundstarterclient;

import fundstarter.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Created by xavier on 19-10-2015.
 */
public class ShowMenus {
    private String loggedPerson;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ShowMenus(Menu menu, String loggedPerson) {
        this.loggedPerson = loggedPerson;
    }
    public ShowMenus(ObjectInputStream inputStream, ObjectOutputStream outputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.loggedPerson = "";
    }

    public void start() throws IOException, ParseException {
        mainMenu();
    }


    public void mainMenu() throws IOException, ParseException {
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
                try{
                    sendCommandToServer(signUp());
                    message = receiveResponseFromServer();
                } catch(IOException e){
                    e.printStackTrace();
                }

                this.mainMenu();
                break;
            case 3:
                System.exit(0);
            default:
                System.out.println("Please choose an option between 1 and 3"); break;
        }
    }

    public void mainMenuWithUserLoggedIn() throws IOException, ParseException {
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
                    voltar = !projectMenuLoggedIn();
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

    public boolean personalAreaSubMenu() throws IOException, ParseException {
        ServerMessage message;
        Menu subMenu = new Menu();

        subMenu.addOption("View messages");
        subMenu.addOption("Offer gift to other person");
        subMenu.addOption("View balance");
        subMenu.addOption("View rewards");
        subMenu.addOption("Offer reward to person");
        subMenu.addOption("Send message to other user");
        subMenu.addOption("Cancel project");
        subMenu.addOption("Back");
        subMenu.setAnswerPrompt("Please enter your choice: ");

        System.out.printf(subMenu.toString());

        int optionChosen = readOptionChosenByUser(subMenu);

        switch (optionChosen) {
            case 1:
                viewMessages();
                break;
            case 2:
                offerGiftToOtherPerson();
                break;
            case 3:
                viewBalance();
                break;
            case 4:
                viewRewards();
                break;
            case 5:
                offerRewardToPerson();
                break;
            case 6:
                sendMessageToOtherUser();
                break;
            case 7:
                cancelProject();
            case 8:
                return false;
            default:
                System.out.println("Choose an option between 1 and 6");
                break;
        }

        return true;
    }

    public boolean projectMenuLoggedIn() throws IOException {
        boolean voltar = true;
        ServerMessage message;
        Menu menu3 = new Menu();

        menu3.addOption("Projects in Progress");
        menu3.addOption("Expired projects");
        menu3.addOption("New project");
        menu3.addOption("Pledge a project");
        menu3.addOption("Send Message to Project");
        menu3.addOption("Project Details");
        menu3.addOption("Back");

        do{
            System.out.print(menu3.toString());

            int optionChosen = readOptionChosenByUser(menu3);

            switch(optionChosen){
                case 1:
                    projectsInProgress();           break;
                case 2:
                    projectsExpired();              break;
                case 3:
                    newProject();                   break;
                case 4:
                    pledge();                       break;
                case 5:
                    sendMessage();                  break;
                case 6:
                    projectDetails();               break;
                case 7:
                    return false;
                default:
                    System.out.println("Choose an option between 1 and 7");     break;
            }
        }while(voltar);

        return true;
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

    public void newProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        Project newProject = new Project();
        ArrayList<Reward> rewards = new ArrayList<>();
        Reward reward = new Reward();
        String projectName = "";

        System.out.print("Project Name: "); newProject.setName(projectName = scan.nextLine());
        System.out.print("Description: ");  newProject.setDescription(scan.nextLine());
        System.out.print("Limit date (DD/MM/YYYY): "); newProject.setDate(scan.nextLine());
        System.out.print("Goal: "); newProject.setGoal(scan.nextDouble());
        System.out.print("Rewards: ");  reward.setProjectName(projectName);
        System.out.print("Min pledge: "); reward.setPledgeMin(scan.nextDouble());
        System.out.print("Gift: "); reward.setGiftName(scan.nextLine());
        newProject.setRewards(rewards);

        this.sendObjectToServer(newProject);
        this.receiveResponseFromServer();

    }

    public void offerGiftToOtherPerson() throws IOException {
        Scanner scan = new Scanner(System.in);
        AttributedGifts gift = new AttributedGifts();

        System.out.print("To: ");           gift.setSendTo(scan.nextLine());
        System.out.print("Project Name: "); gift.setProjectName(scan.nextLine());
        System.out.print("Gift: ");         gift.setGiftName(scan.nextLine());
        gift.setSendFrom(loggedPerson);

        this.sendObjectToServer(gift);
        this.receiveResponseFromServer();
    }

    public void pledge() throws IOException {
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("pledge");
        System.out.print("Project Name: "); command.addArgument(scan.nextLine());
        System.out.print("Amount: ");       command.addArgument(scan.nextLine());
        System.out.print("Decision: ");     command.addArgument(scan.nextLine());

        this.sendObjectToServer(command);
        this.receiveResponseFromServer();
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
        System.out.print("Project Name: "); command.addArgument(scan.nextLine());
        System.out.println("Message: ");    command.addArgument(scan.nextLine());
        command.addArgument(loggedPerson);
    }

    public void offerRewardToPerson() throws IOException {
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("sendreward");
        System.out.print("Person name: ");  command.addArgument(scan.nextLine());
        System.out.print("Reward: ");       command.addArgument(scan.nextLine());
        command.addArgument(loggedPerson);

        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    // View
    public void viewMessages() throws  IOException{
        Command command = new Command();

        command.setCommand("view");
        command.addArgument("messages");
        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void viewBalance() throws IOException {
        Command command = new Command();

        command.setCommand("view");
        command.addArgument("balance");
        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void viewRewards() throws IOException {
        Command command = new Command();

        command.setCommand("view");
        command.addArgument("rewards");
        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void sendCommandToServer(Command command) throws IOException{
        outputStream.writeObject(command);
    }

    public void sendObjectToServer(Object object) throws IOException {
        outputStream.writeObject(object);
    }

    public ServerMessage receiveResponseFromServer() throws IOException{
        ServerMessage message = null;
        try {
            message = (ServerMessage) inputStream.readObject();
            System.out.println(message.getContent());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not Found: " + e.getMessage());
        }
        return message;
    }

    public void projectsInProgress() throws IOException{
        Command command = new Command();

        command.setCommand("listInProgress");
        this.sendCommandToServer(command);
        ArrayList<Project> projectsInProgress = new ArrayList<Project>();
        projectsInProgress = (ArrayList<Project>)receiveResponseFromServer().getContent();
        for(Project proj : projectsInProgress){
            System.out.println(proj.toString());
        }
        //System.out.println(projectsInProgress.toString());
    }

    public void projectsExpired() throws IOException{
        Command command = new Command();

        command.setCommand("listExpired");
        this.sendCommandToServer(command);
        ArrayList<Project> projectsInProgress = new ArrayList<Project>();
        projectsInProgress = (ArrayList<Project>)receiveResponseFromServer().getContent();
        for(Project proj : projectsInProgress){
            System.out.println(proj.toString());
        }
    }

    public void listMessages(String projectName) throws IOException{
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("messages");
        command.addArgument(projectName);
        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void sendMessageToOtherUser() throws IOException, ParseException {
        Scanner scan = new Scanner(System.in);
        Message newMessage = new Message();
        String date = new Date().toString();
        Date dateFormat = new SimpleDateFormat("yyyy/MM/dd").parse(date);

        newMessage.setSendFrom(loggedPerson);
        System.out.print("To: ");   newMessage.setSendTo(scan.nextLine());
        System.out.print("Text: "); newMessage.setText(scan.nextLine());
        newMessage.setData(dateFormat.toString());

        this.sendObjectToServer(newMessage);
        this.receiveResponseFromServer();
    }

    public void cancelProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        CancelProject cancelProject = new CancelProject();

        System.out.print("Project Name: "); cancelProject.setProjectName(scan.nextLine());

        this.sendObjectToServer(cancelProject);
        this.receiveResponseFromServer();
    }
}

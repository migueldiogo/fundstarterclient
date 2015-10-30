package fundstarterclient;

import fundstarter.*;

import java.io.*;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Created by xavier on 19-10-2015.
 */
public class ShowMenus {
    private String loggedPerson;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Actions action;
    private Connection connection;

    public ShowMenus(Menu menu, String loggedPerson) {
        this.loggedPerson = loggedPerson;
    }
    public ShowMenus(ObjectInputStream inputStream, ObjectOutputStream outputStream, Connection connection){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.loggedPerson = "";
        this.action = new Actions(inputStream, outputStream);
        this.connection = connection;
    }

    public void start() throws IOException, ParseException {
        mainMenu();
    }


    public boolean mainMenu() throws IOException, ParseException {
        ServerMessage message = null;
        boolean voltar = true;

        Menu menu1 = new Menu();
        menu1.addOption("Login");
        menu1.addOption("Sign Up");
        menu1.addOption("Quit");
        menu1.setAnswerPrompt("Please enter your choice: ");

        System.out.println(menu1.toString());

        int optionChosen = readOptionChosenByUser(menu1);

        do {
            switch (optionChosen) {
                case 1:
                    try {
                        loggedPerson = action.login();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    mainMenuWithUserLoggedIn();
                    break;
                case 2:
                    try{
                        action.signUp();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Please choose an option between 1 and 3");
                    break;
            }
        }while(voltar);

        return voltar;
    }

    public boolean mainMenuWithUserLoggedIn() throws IOException, ParseException {
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
                    try {
                        voltar = !projectMenuLoggedIn();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 2:
                    try {
                        voltar = !personalAreaSubMenu();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 3:
                    try {
                        action.logout();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    voltar = false;
                    break;
                default:
                    System.out.println("Choose an option between 1 and 3");
            }
        } while (voltar);

        return true;

    }

    public boolean personalAreaSubMenu() throws IOException, ParseException {
        boolean voltar = true;
        Menu subMenu = new Menu();

        subMenu.addOption("View messages");
        subMenu.addOption("View balance");
        subMenu.addOption("View rewards");
        subMenu.addOption("Offer reward to person");
        subMenu.addOption("Send message to other user");
        subMenu.addOption("Cancel project");
        subMenu.addOption("Add Admin to Project");
        subMenu.addOption("Back");
        subMenu.setAnswerPrompt("Please enter your choice: ");

        do{
            System.out.printf(subMenu.toString());

            int optionChosen = readOptionChosenByUser(subMenu);

            switch (optionChosen) {
                case 1:
                    try {
                        action.viewMessages();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 2:
                    try {
                        action.viewBalance();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 3:
                    try {
                        action.viewRewards();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 4:
                    try {
                        action.offerRewardToPerson();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 5:
                    try {
                        action.sendMessageToOtherUser();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 6:
                    try {
                        action.cancelProject();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 7:
                    try {
                        action.addAdmin();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 8:
                    return false;
                default:
                    System.out.println("Choose an option between 1 and 8");
                    break;
            }
        }while(voltar);

        return true;
    }

    public boolean projectMenuLoggedIn() throws IOException {
        boolean voltar = true;
        Menu menu3 = new Menu();

        menu3.addOption("Projects in Progress");
        menu3.addOption("Expired projects");
        menu3.addOption("New project");
        menu3.addOption("Pledge a project");
        menu3.addOption("Send Message to Project");
        menu3.addOption("Project Details");
        menu3.addOption("Add reward to Project");
        menu3.addOption("Remove reward to Project");
        menu3.addOption("Add extra level");
        menu3.addOption("Remove extra level");
        menu3.addOption("Back");
        menu3.setAnswerPrompt("Please enter your choice: ");

        do{
            System.out.print(menu3.toString());

            int optionChosen = readOptionChosenByUser(menu3);

            switch(optionChosen){
                case 1:
                    try {
                        action.projectsInProgress();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 2:
                    try {
                        action.projectsExpired();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 3:
                    try {
                        action.newProject();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;

                case 4:
                    try {
                        action.pledge();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;

                case 5:
                    try {
                        action.sendMessageToProject();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;

                case 6:
                    try {
                        action.projectDetails();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;

                case 7:
                    try {
                        action.addRewardToProject();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;

                case 8:
                    try {
                        action.removeRewardFromProject();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 9:
                    try{
                        action.addExtraLevelToProject();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 10:
                    try {
                        action.removeExtraLevelToProject();
                    } catch (IOException e) {
                        connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    break;
                case 11:
                    return false;
                default:
                    System.out.println("Choose an option between 1 and 11");     break;
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


}

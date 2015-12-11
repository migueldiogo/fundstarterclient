package fundstarterclient;

import fundstarter.*;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Created by xavier on 19-10-2015.
 */
public class ShowMenus {
    private String loggedPerson;
    private Action action;
    private Connection connection;
    private Protection protection;

    public ShowMenus(Connection connection){
        this.loggedPerson = "";
        this.connection = connection;
        this.protection = new Protection();
    }

    public void start() {
        mainMenu();
    }


    public boolean mainMenu() {
        ServerMessage message = null;
        boolean voltar = true;

        Menu menu1 = new Menu();
        menu1.addOption("Login");
        menu1.addOption("Sign Up");
        menu1.addOption("Quit");

        do {
            System.out.println(menu1.toString());

            int optionChosen = readOptionChosenByUser(menu1);

            switch (optionChosen) {
                case 1:
                    action = new Action(connection);
                    loggedPerson = action.login();
                    voltar = mainMenuWithUserLoggedIn();
                    break;
                case 2:
                    action = new Action(connection);
                    action.signUp();
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

    public boolean mainMenuWithUserLoggedIn() {
        boolean voltar = false;

        Menu menu2 = new Menu();
        menu2.addOption("Projects");
        menu2.addOption("Personal Area");
        menu2.addOption("Logout");

        ServerMessage serverMessage = new ServerMessage();

        do {
            System.out.println(menu2.toString());

            int optionChosen = readOptionChosenByUser(menu2);

            switch (optionChosen) {
                case 1:
                    voltar = !projectMenuLoggedIn();
                    break;
                case 2:
                    voltar = !personalAreaSubMenu();
                    break;
                case 3:
                    action = new Action(connection);
                    try {
                        serverMessage = action.logout();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    voltar = false;
                    break;
                default:
                    System.out.println("Choose an option between 1 and 3");
            }
        } while (voltar);

        return true;

    }

    public boolean personalAreaSubMenu() {
        boolean voltar = true;
        Menu subMenu = new Menu();
        ServerMessage serverMessage = null;

        subMenu.addOption("View messages");
        subMenu.addOption("View balance");
        subMenu.addOption("View rewards");
        subMenu.addOption("View pledges");
        subMenu.addOption("Offer reward to person");
        subMenu.addOption("Send message to other user");
        subMenu.addOption("Send message to project");
        subMenu.addOption("Cancel project");
        subMenu.addOption("Back");

        do{
            System.out.printf(subMenu.toString());

            int optionChosen = readOptionChosenByUser(subMenu);

            switch (optionChosen) {
                case 1:
                    action = new Action(connection);
                    try {
                        serverMessage = action.getUserMessages();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        if(serverMessage.isErrorHappened())
                            System.out.println(serverMessage.getContent());
                        else{
                            new Table().printTableOfMessages((ArrayList<Message>)serverMessage.getContent());

                        }
                    }
                    break;
                case 2:
                    action = new Action(connection);
                    try {
                        serverMessage = action.getBalance();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        new Table("Balance", serverMessage.getContent() + "€").printTable();
                        //System.out.println(serverMessage.getContent());
                    }
                    break;
                case 3:
                    action = new Action(connection);
                    try {
                        serverMessage = action.getRewardsFromUser();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        if(serverMessage.isErrorHappened())
                            System.out.println(serverMessage.getContent());
                        else{
                            new Table().printTableOfAttributedRewards((ArrayList<Reward>) serverMessage.getContent());
                        }
                    }
                    break;
                case 4:
                    action = new Action(connection);
                    try {
                        serverMessage = action.getPledgesFromUser();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        if(serverMessage.isErrorHappened())
                            System.out.println(serverMessage.getContent());
                        else{
                            new Table().printTableOfPledges((ArrayList<Pledge>) serverMessage.getContent());
                        }
                    }
                    break;
                case 5:
                    action = new Action(connection);
                    try {
                        serverMessage = action.sendRewardToUser();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;
                case 6:
                    action = new Action(connection);
                    try {
                        serverMessage = action.sendMessageToOtherUser();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;
                case 7:
                    action = new Action(connection);
                    try {
                        serverMessage = action.sendMessageToProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;

                case 8:
                    action = new Action(connection);
                    try {
                        serverMessage = action.cancelProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;

                case 9:
                    return false;
                default:
                    System.out.println("Choose an option between 1 and 8");
                    break;
            }
        }while(voltar);

        return true;
    }

    public boolean projectMenuLoggedIn() {
        boolean voltar = true;
        ServerMessage serverMessage = null;
        Menu menu3 = new Menu();

        menu3.addOption("New project");
        menu3.addOption("Pledge a project");
        menu3.addOption("Projects in Progress");
        menu3.addOption("Expired projects");

        menu3.addOption("Send Message from Project");

        menu3.addOption("View Project Messages");
        menu3.addOption("View Project Details");
        menu3.addOption("View Project Rewards");
        menu3.addOption("View Project Goals");
        menu3.addOption("View Project Options");

        menu3.addOption("Add Reward to Project");
        menu3.addOption("Add Goal to Project");
        menu3.addOption("Add Option to Project");
        menu3.addOption("Add Question to Project");
        menu3.addOption("Add Admin to Project");

        menu3.addOption("Remove reward from Project");
        menu3.addOption("Remove extra level from Project");
        menu3.addOption("Back");

        do{
            System.out.print(menu3.toString());
            int optionChosen = readOptionChosenByUser(menu3);

            switch(optionChosen){
                case 1:
                    action = new Action(connection);
                    try {
                        serverMessage = action.newProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;
                case 2:
                    action = new Action(connection);
                    try {
                        serverMessage = action.pledge();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;
                case 3:
                    action = new Action(connection);
                    try {
                        serverMessage = action.getInProgressProjects();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        if(serverMessage.isErrorHappened())
                            System.out.println(serverMessage.getContent());
                        else{
                            new Table().printTableOfProjects((ArrayList<Project>) serverMessage.getContent());
                        }
                    }
                    break;
                case 4:
                    action = new Action(connection);
                    try {
                        serverMessage = action.getExpiredProjects();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        if(serverMessage.isErrorHappened())
                            System.out.println(serverMessage.getContent());
                        else{
                            new Table().printTableOfProjects((ArrayList<Project>) serverMessage.getContent());
                        }
                    }
                    break;

                case 5:
                    action = new Action(connection);
                    try {
                        serverMessage = action.sendMessageFromProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    }
                    finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;


                case 6:
                    action = new Action(connection);
                    try {
                        serverMessage = action.getProjectMessages();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        if(serverMessage.isErrorHappened())
                            System.out.println(serverMessage.getContent());
                        else{
                            new Table().printTableOfMessages((ArrayList<Message>)serverMessage.getContent());

                        }
                    }
                    break;

                case 7:
                    action = new Action(connection);
                    try {
                        serverMessage = action.getProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {

                        if (!serverMessage.isErrorHappened()) {

                            Table table = new Table();
                            Project project = (Project) serverMessage.getContent();

                            table.printProject(project);

                        }
                        else
                            System.out.println("This project doesn't exist.");

                    }
                    break;
                case 8:
                    action = new Action(connection);
                    try {
                        serverMessage = action.getRewardsFromProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        if (!serverMessage.isErrorHappened()) {
                            Table table = new Table();
                            ArrayList<Reward> rewards = (ArrayList<Reward>) serverMessage.getContent();

                            table.printTableOfRewards(rewards);

                        }
                        else
                            System.out.println("This project doesn't exist.");
                    }
                    break;
                case 9:
                    action = new Action(connection);
                    try {
                        serverMessage = action.getGoalsFromProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        if (!serverMessage.isErrorHappened()) {
                            Table table = new Table();
                            ArrayList<Goal> goals = (ArrayList<Goal>) serverMessage.getContent();

                            table.printTableOfGoals(goals);

                        }
                        else
                            System.out.println("This project doesn't exist.");
                    }
                    break;
                case 10:
                    action = new Action(connection);
                    try {
                        serverMessage = action.getProjectOptions();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        if (!serverMessage.isErrorHappened()) {
                            Table table = new Table();
                            ArrayList<DecisionOption> options = (ArrayList<DecisionOption>) serverMessage.getContent();

                            table.printTableOfOptions(options);

                        }
                        else
                            System.out.println("This project doesn't exist.");
                    }
                    break;
                case 11:
                    action = new Action(connection);
                    try {
                        serverMessage = action.addRewardToProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;
                case 12:
                    action = new Action(connection);
                    try{
                        serverMessage = action.addGoalToProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;
                case 13:
                    action = new Action(connection);
                    try{
                        serverMessage = action.addOptionToProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;
                case 14:
                    action = new Action(connection);
                    try{
                        serverMessage = action.addQuestionToProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;
                case 15:
                    action = new Action(connection);
                    try {
                        serverMessage = action.addAdminToProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;

                case 16:
                    action = new Action(connection);
                    try {
                        serverMessage = action.removeRewardFromProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;

                case 17:
                    action = new Action(connection);
                    try {
                        serverMessage = action.removeGoalFromProject();
                    } catch (IOException e) {
                        serverMessage = connection.handleServerFailOver(action.getCommand(), loggedPerson);
                    } finally {
                        System.out.println(serverMessage.getContent());
                    }
                    break;
                case 18:
                    return false;
                default:
                    System.out.println("Choose an option between 1 and 17");     break;
            }
        }while(voltar);

        return true;
    }

    public int readOptionChosenByUser(Menu menu){
        int optionInput = 0;
        Boolean inputValidation;
        Scanner scan = new Scanner(System.in);

        do {
            try{
                optionInput = protection.verifyUserChoice();
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

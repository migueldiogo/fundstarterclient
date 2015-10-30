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

    public ShowMenus(Menu menu, String loggedPerson) {
        this.loggedPerson = loggedPerson;
    }
    public ShowMenus(ObjectInputStream inputStream, ObjectOutputStream outputStream){
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.loggedPerson = "";
        this.action = new Actions(inputStream, outputStream);
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
                    do {
                        try {
                            action.sendCommandToServer(action.login());
                            message = action.receiveResponseFromServer();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    while (message.isErrorHappened());

                    mainMenuWithUserLoggedIn();
                    break;
                case 2:
                    try {
                        action.sendCommandToServer(action.signUp());
                        message = action.receiveResponseFromServer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    this.mainMenu();
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
                    voltar = !personalAreaSubMenu();
                    break;
                case 3:
                    action.logout();
                    voltar = !this.mainMenu();
                    break;
                default:
                    System.out.println("Choose an option between 1 and 3");
            }
        } while (voltar);

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
                    action.viewMessages();
                    break;
                case 2:
                    action.viewBalance();
                    break;
                case 3:
                    action.viewRewards();
                    break;
                case 4:
                    action.offerRewardToPerson();
                    break;
                case 5:
                    action.sendMessageToOtherUser();
                    break;
                case 6:
                    action.cancelProject();
                    break;
                case 7:
                    action.addAdmin();
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
                    action.projectsInProgress();           break;
                case 2:
                    action.projectsExpired();              break;
                case 3:
                    action.newProject();                   break;
                case 4:
                    action.pledge();                       break;
                case 5:
                    action.sendMessageToProject();         break;
                case 6:
                    action.projectDetails();               break;
                case 7:
                    action.addRewardToProject();           break;
                case 8:
                    action.removeRewardFromProject();      break;
                case 9:
                    action.addExtraLevelToProject();       break;
                case 10:
                    action.removeExtraLevelToProject();    break;
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

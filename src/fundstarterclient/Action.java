package fundstarterclient;

import fundstarter.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by xavier on 28-10-2015.
 */
public class Action {
    private Command command;
    private Connection connection;

    public Action(Connection connection) {
        this.connection = connection;
        System.out.println();
    }

    public String login() {
        Scanner scan = new Scanner(System.in);
        ServerMessage serverMessage = null;
        String username = "";
        do {
            command = new Command();

            command.setCommand("login");
            System.out.print("Username: ");
            command.addArgument(username = scan.nextLine());
            System.out.print("Password: ");
            command.addArgument(scan.nextLine());

            try {
                sendCommandToServer(command);
                serverMessage = receiveResponseFromServer();
            } catch (IOException e) {
                serverMessage = connection.handleServerFailOver(command, "");
            } finally {
                System.out.println(serverMessage.getContent().toString());
            }

        }
        while(serverMessage.isErrorHappened());

        return username;

    }

    public void signUp() throws IOException {
        Scanner scan = new Scanner(System.in);

        ServerMessage serverMessage = null;
        do {
            command = new Command();


            command.setCommand("signup");
            System.out.print("Username: ");
            command.addArgument(scan.nextLine());
            System.out.print("Password: ");
            command.addArgument(scan.nextLine());
            System.out.print("Repeat Password: ");
            command.addArgument(scan.nextLine());

            try {
                sendCommandToServer(command);
                serverMessage = receiveResponseFromServer();

            } catch (IOException e) {
                serverMessage = connection.handleServerFailOver(command, "");
            } finally {
                System.out.println(serverMessage.getContent().toString());
            }


        }
        while(serverMessage.isErrorHappened());

    }

    public ServerMessage newProject() throws IOException {
        command = new Command();
        command.setCommand("newProject");

        Scanner scan = new Scanner(System.in);
        Project newProject = new Project();
        ArrayList<Reward> rewards = new ArrayList<>();
        ArrayList<String> answers = new ArrayList<>();
        ArrayList<Extra> extras = new ArrayList<>();
        Question question = new Question();
        String projectName = "", input = "";
        double pledgeMin, goalMin;

        System.out.print("Project Name: ");
        newProject.setName(projectName = scan.nextLine());

        System.out.print("Description: ");
        newProject.setDescription(scan.nextLine());

        System.out.print("Limit date (YYYYMMDD): ");
        newProject.setDate(scan.nextLine());

        System.out.print("Goal: ");
        newProject.setGoal(scan.nextDouble());
        scan.nextLine();

        System.out.print("Question: ");
        question.setQuestion(scan.nextLine());
        question.setProjectName(projectName);

        System.out.print("Answers: ");

        do {
            input = scan.nextLine();
            if (!input.equals(""))
                answers.add(input);
        } while(!input.equals(""));

        question.setAnswers(answers);
        newProject.setQuestion(question);
        System.out.println("Rewards: ");

        do {
            Reward reward = new Reward();
            reward.setProjectName(projectName);
            System.out.print("Min pledge: ");
            reward.setPledgeMin(pledgeMin = scan.nextDouble());
            scan.nextLine();
            System.out.print("Reward: ");
            reward.setGiftName(scan.nextLine());
            if(reward.getPledgeMin() != 0)
                rewards.add(reward);
        } while(pledgeMin != 0);
        newProject.setRewards(rewards);
        System.out.println("Extra Rewards: ");
        do {
            Extra extra = new Extra();
            extra.setProjectName(projectName);
            System.out.print("Min Goal: ");
            extra.setGoalMin(goalMin = scan.nextDouble()); scan.nextLine();
            System.out.print("Reward: ");
            extra.setDescription(scan.nextLine());
            if(extra.getGoalMin() != 0)
                extras.add(extra);
        } while(goalMin != 0);
        newProject.setExtras(extras);
        command.setAttachedObject(newProject);

        this.sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage pledge() throws IOException {
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("pledge");
        System.out.print("Project Name: ");
        command.addArgument(scan.nextLine());
        System.out.print("Amount: ");
        command.addArgument(scan.nextLine());
        System.out.print("Decision: ");
        command.addArgument(scan.nextLine());

        this.sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage projectDetails() throws IOException {
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Project receiveProject;

        command.setCommand("details");
        System.out.print("Choose project: ");
        command.addArgument(scan.nextLine());

        this.sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage sendMessageToProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Message newMessage = new Message();

        command.setCommand("sendMessageToProject");
        System.out.print("Project Name: ");
        newMessage.setSendTo(scan.nextLine());
        System.out.println("Message: ");
        newMessage.setText(scan.nextLine());

        command.setAttachedObject(newMessage);
        this.sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage offerRewardToPerson() throws IOException {
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("sendReward");
        System.out.print("Project name: ");
        command.addArgument(scan.nextLine());
        System.out.print("Reward: ");
        command.addArgument(scan.nextLine());
        System.out.print("Person name: ");
        command.addArgument(scan.nextLine());

        sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    // View
    public ServerMessage viewMessages() throws  IOException{
        command = new Command();

        command.setCommand("viewMessages");
        sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage viewBalance() throws IOException {
        command = new Command();

        command.setCommand("viewBalance");
        sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage viewRewards() throws IOException {
        command = new Command();

        command.setCommand("viewRewards");
        sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage projectsInProgress() throws IOException{
        command = new Command();

        command.setCommand("listInProgress");
        sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage projectsExpired() throws IOException{
        command = new Command();
        ArrayList<Project> projectsExpired;
        ServerMessage message = null;

        command.setCommand("listExpired");
        sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public void listMessages(String projectName) throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("messages");
        command.addArgument(projectName);
        sendCommandToServer(command);
        receiveResponseFromServer();
    }

    public ServerMessage sendMessageToOtherUser() throws IOException {
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("sendMessage");
        System.out.print("To: ");
        command.addArgument(scan.nextLine());
        System.out.print("Text: ");
        command.addArgument(scan.nextLine());

        this.sendObjectToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage cancelProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("cancelProject");
        System.out.print("Project Name: ");
        command.addArgument(scan.nextLine());

        this.sendObjectToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage addAdmin() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("addAdminToProject");
        System.out.print("Project Name: ");
        command.addArgument(scan.nextLine());
        System.out.print("New Admin Username: ");
        command.addArgument(scan.nextLine());

        this.sendObjectToServer(command);
        return receiveResponseFromServer();
    }

    public void logout() throws IOException{
        command = new Command();

        command.setCommand("logout");
        this.sendObjectToServer(command);
        receiveResponseFromServer();
    }

    public ServerMessage addRewardToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Reward reward = new Reward();

        command.setCommand("addRewardToProject");
        System.out.print("Project Name: ");
        reward.setProjectName(scan.nextLine());
        System.out.print("Pledge min: ");
        reward.setPledgeMin(scan.nextDouble());
        scan.nextLine();
        System.out.print("Gift name: ");
        reward.setGiftName(scan.nextLine());

        command.setAttachedObject(reward);
        sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage removeRewardFromProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Reward reward = new Reward();

        command.setCommand("removeRewardFromProject");
        System.out.print("Project Name: ");
        reward.setProjectName(scan.nextLine());
        System.out.print("Pledge min: ");
        reward.setPledgeMin(scan.nextDouble()); scan.nextLine();
        System.out.print("Gift name: ");
        reward.setGiftName(scan.nextLine());

        command.setAttachedObject(reward);
        sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage addExtraLevelToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Extra extra = new Extra();

        command.setCommand("addExtraToProject");
        System.out.print("Project Name: ");
        extra.setProjectName(scan.nextLine());
        System.out.print("Min Goal: ");
        extra.setGoalMin(scan.nextDouble());
        scan.nextLine();
        System.out.print("Description: ");
        extra.setDescription(scan.nextLine());

        command.setAttachedObject(extra);
        sendCommandToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage removeExtraLevelToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Extra extra = new Extra();

        command.setCommand("removeExtraFromProject");
        System.out.print("Project Name: ");
        extra.setProjectName(scan.nextLine());
        System.out.print("Min Goal: ");
        extra.setGoalMin(scan.nextDouble());
        scan.nextLine();
        System.out.print("Description: ");
        extra.setDescription(scan.nextLine());

        command.setAttachedObject(extra);
        sendCommandToServer(command);
        return receiveResponseFromServer();
    }


    public void sendCommandToServer(Command command) throws IOException {
        connection.getOutputStream().writeObject(command);
    }

    public void sendObjectToServer(Object object) throws IOException {
        assert object != null;
        connection.getOutputStream().writeObject(object);
    }

    public ServerMessage receiveResponseFromServer() throws IOException{
        ServerMessage message = null;
        try {
            message = (ServerMessage) connection.getInputStream().readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not Found: " + e.getMessage());
        }

        assert message != null;

        return message;
    }

    public Command getCommand() {
        return command;
    }

}

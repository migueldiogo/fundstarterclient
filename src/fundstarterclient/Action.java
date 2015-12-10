package fundstarterclient;

import com.sun.corba.se.spi.activation.Server;
import fundstarter.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by xavier on 28-10-2015.
 */
public class Action {
    private Command command;
    private Connection connection;
    private Protection protection;

    public Action(Connection connection) {
        this.connection = connection;
        this.protection = new Protection();
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

    public void signUp() {
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

    public ServerMessage logout() throws IOException {
        this.sendObjectToServer(ServerCommandFactory.logout());
        return receiveResponseFromServer();
    }

    public ServerMessage getBalance() throws IOException {
        sendCommandToServer(ServerCommandFactory.getBalance());
        return receiveResponseFromServer();
    }

    // TODO
    public ServerMessage newProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        Project project = new Project();
        String parts[];

        System.out.print("Project Name: ");
        project.setName(scan.nextLine());

        System.out.print("Description: ");
        project.setDescription(scan.nextLine());

        // Date
        do{
            System.out.print("Limit date (YYYY-MM-DD): ");
            String rawDate = scan.nextLine();
            parts = rawDate.split("-");
            if(protection.verifyDate(parts) != 0)  System.out.println("Wrong input, try it again.");    //FIX
        }while(protection.verifyDate(parts) != 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(parts[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(parts[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[2]));
        project.setExpirationDate(new Date(calendar.getTime().getTime()));



        double firstGoalValue = protection.verifyDouble("First Goal Value: ");
        project.setFirstGoalValue(firstGoalValue);

        System.out.print("Question: ");
        project.setQuestion(scan.nextLine());

        this.sendCommandToServer(ServerCommandFactory.newProject(project));
        return receiveResponseFromServer();
    }

    public ServerMessage cancelProject() throws IOException{
        int projectId;

        projectId = protection.verifyInt("Project ID: ");

        this.sendObjectToServer(ServerCommandFactory.cancelProject(projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage addAdminToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        int projectId;

        projectId = protection.verifyInt("Project ID: ");

        System.out.print("New Admin Username: ");
        String username = scan.nextLine();

        this.sendObjectToServer(ServerCommandFactory.addAdminToProject(username, projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage addGoalToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        Goal goal = new Goal();

        int projectId = protection.verifyInt("Project ID: ");

        double minGoal = protection.verifyDouble("Min Goal: ");
        goal.setAmount(minGoal);

        System.out.print("Description: ");
        goal.setExtraDescription(scan.nextLine());

        sendCommandToServer(ServerCommandFactory.addGoalToProject(goal, projectId));
        return receiveResponseFromServer();
    }


    public ServerMessage addRewardToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        Reward reward = new Reward();

        int projectId = protection.verifyInt("Project ID: ");

        double pledgeMin = protection.verifyDouble("Pledge Min: ");
        reward.setMinAmount(pledgeMin);

        System.out.print("Gift name: ");
        reward.setDescription(scan.nextLine());

        sendCommandToServer(ServerCommandFactory.addRewardToProject(reward, projectId));
        return receiveResponseFromServer();

    }

    public ServerMessage addQuestionToProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        int projectId;
        String question;

        projectId = protection.verifyInt("Project ID: ");
        System.out.print("Question: ");
        question = scan.nextLine();

        sendCommandToServer(ServerCommandFactory.addQuestionToProject(question, projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage addOptionToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        int projectId;
        String option;

        projectId = protection.verifyInt("Project ID: ");
        System.out.print("Option: ");
        option = scan.nextLine();

        sendCommandToServer(ServerCommandFactory.addOptionToProject(new DecisionOption(projectId, option), projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage removeGoalFromProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        Goal goal = new Goal();
        int projectId;

        projectId = protection.verifyInt("Project ID: ");

        double minGoal = protection.verifyDouble("Min Goal: ");
        goal.setAmount(minGoal);

        System.out.print("Description: ");
        goal.setExtraDescription(scan.nextLine());

        sendCommandToServer(ServerCommandFactory.removeGoalFromProject(goal, projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage removeRewardFromProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        int rewardId, projectId;

        projectId = protection.verifyInt("Project ID: ");

        rewardId = protection.verifyInt("Reward ID: ");

        sendCommandToServer(ServerCommandFactory.removeRewardFromProject(rewardId, projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage getProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        int projectId;

        projectId = protection.verifyInt("Project ID: ");

        this.sendCommandToServer(ServerCommandFactory.getProject(projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage getRewardsFromProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        int projectId;

        projectId = protection.verifyInt("Project ID: ");

        this.sendCommandToServer(ServerCommandFactory.getRewardsFromProject(projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage getGoalsFromProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        int projectId;

        projectId = protection.verifyInt("Project ID: ");

        this.sendCommandToServer(ServerCommandFactory.getGoalsFromProject(projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage getPledgesFromUser() throws IOException {
        sendCommandToServer(ServerCommandFactory.getPledgesFromUser());
        return receiveResponseFromServer();
    }

    public ServerMessage getRewardsFromUser() throws IOException {
        sendCommandToServer(ServerCommandFactory.getRewardsFromUser());
        return receiveResponseFromServer();
    }

    public ServerMessage getProjectMessages() throws  IOException{
        Scanner scan = new Scanner(System.in);
        int projectId;

        projectId = protection.verifyInt("Project ID: ");
        sendCommandToServer(ServerCommandFactory.getProjectMessages(projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage getUserMessages() throws  IOException{
        sendCommandToServer(ServerCommandFactory.getUserMessages());
        return receiveResponseFromServer();
    }

    public ServerMessage getExpiredProjects() throws IOException{
        sendCommandToServer(ServerCommandFactory.getExpiredProjects());
        return receiveResponseFromServer();
    }

    public ServerMessage getInProgressProjects() throws IOException{
        sendCommandToServer(ServerCommandFactory.getInProgressProjects());
        return receiveResponseFromServer();
    }

    public ServerMessage getProjectOptions() throws IOException{
        int projectId;

        projectId = protection.verifyInt("Project ID: ");

        sendCommandToServer(ServerCommandFactory.getProjectOptions(projectId));
        return receiveResponseFromServer();
    }



    public ServerMessage sendMessageFromProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        Message message = new Message();
        int projectId;

        projectId = protection.verifyInt("Project ID: ");
        message.setProjectId(projectId);

        System.out.println("Message: ");
        message.setText(scan.nextLine());

        this.sendCommandToServer(ServerCommandFactory.sendMessageFromProject(message));
        return receiveResponseFromServer();
    }

    // TODO responder a mensagens precisaria só de mensagem id
    public ServerMessage sendMessageToProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        Message message = new Message();
        int projectId;

        projectId = protection.verifyInt("Project ID: ");
        message.setProjectId(projectId);

        System.out.println("Message: ");
        message.setText(scan.nextLine());

        this.sendCommandToServer(ServerCommandFactory.sendMessageFromProject(message));
        return receiveResponseFromServer();
    }

    public ServerMessage sendRewardToUser() throws IOException {
        int rewardId, toUserId;

        rewardId = protection.verifyInt("Reward ID: ");

        toUserId = protection.verifyInt("Person ID: ");

        sendCommandToServer(ServerCommandFactory.sendRewardToUser(rewardId, toUserId));
        return receiveResponseFromServer();
    }

    public ServerMessage pledge() throws IOException {
        Scanner scan = new Scanner(System.in);
        Pledge pledge = new Pledge();
        int projectId;

        projectId = protection.verifyInt("Project ID: ");
        pledge.setProjectId(projectId);

        double amount = protection.verifyDouble("Amount: ");
        pledge.setAmount(amount);

        System.out.print("Decision: ");
        pledge.setDecision(scan.nextInt());

        this.sendCommandToServer(ServerCommandFactory.pledge(pledge));
        return receiveResponseFromServer();
    }

    @Deprecated
    public ServerMessage sendMessageToOtherUser() throws IOException {
        return new ServerMessage();
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

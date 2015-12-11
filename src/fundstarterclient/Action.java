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
        String password1 = "", password2 = "", username = "";

        ServerMessage serverMessage = null;
        do {
            command = new Command();

            // TODO verificar se funca signup com pass diferente
            do{
                command.setCommand("signup");
                System.out.print("Username: ");
                username = scan.nextLine();

                System.out.print("Password: ");
                password1 = scan.nextLine();

                System.out.print("Repeat Password: ");
                password2 = scan.nextLine();
            } while(protection.verifyPassword(password1, password2) != 0);

            command.addArgument(username);
            command.addArgument(password1);

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

        String projectName = protection.verifyString("Project Name: ");
        project.setName(projectName);

        String description = protection.verifyString("Description: ");
        project.setDescription(description);

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

        String question = protection.verifyString("Question: ");
        project.setQuestion(question);

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
        int projectId;

        projectId = protection.verifyInt("Project ID: ");

        String username = protection.verifyString("New Admin Username: ");

        this.sendObjectToServer(ServerCommandFactory.addAdminToProject(username, projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage addGoalToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        Goal goal = new Goal();

        int projectId = protection.verifyInt("Project ID: ");

        double minGoal = protection.verifyDouble("Min Goal: ");
        goal.setAmount(minGoal);

        System.out.print("Extra Description: ");
        goal.setExtraDescription(scan.nextLine());

        sendCommandToServer(ServerCommandFactory.addGoalToProject(goal, projectId));
        return receiveResponseFromServer();
    }


    public ServerMessage addRewardToProject() throws IOException{
        Reward reward = new Reward();

        int projectId = protection.verifyInt("Project ID: ");

        double pledgeMin = protection.verifyDouble("Pledge Min: ");
        reward.setMinAmount(pledgeMin);

        String giftName = protection.verifyString("Gift name: ");
        reward.setDescription(giftName);

        sendCommandToServer(ServerCommandFactory.addRewardToProject(reward, projectId));
        return receiveResponseFromServer();

    }

    public ServerMessage addQuestionToProject() throws IOException {
        int projectId;
        String question;

        projectId = protection.verifyInt("Project ID: ");

        question = protection.verifyString("Question: ");

        sendCommandToServer(ServerCommandFactory.addQuestionToProject(question, projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage addOptionToProject() throws IOException{
        int projectId;
        String option;

        projectId = protection.verifyInt("Project ID: ");

        option = protection.verifyString("Option: ");

        sendCommandToServer(ServerCommandFactory.addOptionToProject(new DecisionOption(projectId, option), projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage removeGoalFromProject() throws IOException{
        Goal goal = new Goal();
        int projectId;

        projectId = protection.verifyInt("Project ID: ");

        double minGoal = protection.verifyDouble("Min Goal: ");
        goal.setAmount(minGoal);

        sendCommandToServer(ServerCommandFactory.removeGoalFromProject(goal, projectId));
        return receiveResponseFromServer();
    }

    // TODO verificar com cliente e db
    public ServerMessage removeRewardFromProject() throws IOException{
        int rewardId, projectId;

        projectId = protection.verifyInt("Project ID: ");

        rewardId = protection.verifyInt("Reward ID: ");

        sendCommandToServer(ServerCommandFactory.removeRewardFromProject(rewardId, projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage getProject() throws IOException {
        int projectId;

        projectId = protection.verifyInt("Project ID: ");

        this.sendCommandToServer(ServerCommandFactory.getProject(projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage getRewardsFromProject() throws IOException {
        int projectId;

        projectId = protection.verifyInt("Project ID: ");

        this.sendCommandToServer(ServerCommandFactory.getRewardsFromProject(projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage getGoalsFromProject() throws IOException {
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

    // TODO
    public ServerMessage sendMessageFromProject() throws IOException {
        Message message = new Message();
        int projectId;

        projectId = protection.verifyInt("Project ID: ");
        message.setProjectId(projectId);

        String textMessage = protection.verifyString("Message: ");
        message.setText(textMessage);

        this.sendCommandToServer(ServerCommandFactory.sendMessageFromProject(message));
        return receiveResponseFromServer();
    }

    public ServerMessage sendMessageToProject() throws IOException {
        Message message = new Message();
        int projectId;

        projectId = protection.verifyInt("Project ID: ");
        message.setProjectId(projectId);

        String textMessage = protection.verifyString("Message: ");
        message.setText(textMessage);

        this.sendCommandToServer(ServerCommandFactory.sendMessageFromProject(message));
        return receiveResponseFromServer();
    }

    public ServerMessage sendRewardToUser() throws IOException {
        int pledgeId;
        String toUserName;

        pledgeId = protection.verifyInt("Pledge ID: ");

        toUserName = protection.verifyString("Username: ");

        sendCommandToServer(ServerCommandFactory.sendRewardToUser(pledgeId, toUserName));
        return receiveResponseFromServer();
    }

    public ServerMessage pledge() throws IOException {
        Pledge pledge = new Pledge();
        int projectId;

        projectId = protection.verifyInt("Project ID: ");
        pledge.setProjectId(projectId);

        double amount = protection.verifyDouble("Amount: ");
        pledge.setAmount(amount);

        String decision = protection.verifyString("Decision: ");
        pledge.setDecisionDescription(decision);

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

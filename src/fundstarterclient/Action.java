package fundstarterclient;

import com.sun.corba.se.spi.activation.Server;
import fundstarter.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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

        System.out.print("Project Name: ");
        project.setName(scan.nextLine());

        System.out.print("Description: ");
        project.setDescription(scan.nextLine());

        System.out.print("Limit date (YYYY-MM-DD): ");
        String rawDate = scan.nextLine();
        String[] parts = rawDate.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(parts[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(parts[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[2])-1);
        project.setExpirationDate(calendar.getTime());

        System.out.print("First Goal Value: ");
        project.setFirstGoalValue(scan.nextDouble());
        scan.nextLine();

        System.out.print("Question: ");
        project.setQuestion(scan.nextLine());

        this.sendCommandToServer(ServerCommandFactory.newProject(project));
        return receiveResponseFromServer();
    }

    public ServerMessage cancelProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        int projectId;
        System.out.print("Project id: ");
        projectId = scan.nextInt(); scan.nextLine();
        this.sendObjectToServer(ServerCommandFactory.cancelProject(projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage addAdminToProject() throws IOException{
        Scanner scan = new Scanner(System.in);

        System.out.print("Project Id: ");
        int projectId = scan.nextInt(); scan.nextLine();
        System.out.print("New Admin Username: ");
        String username = scan.nextLine();

        this.sendObjectToServer(ServerCommandFactory.addAdminToProject(username, projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage addGoalToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        int projectId;
        Goal goal = new Goal();

        System.out.print("Project Id: ");
        projectId = scan.nextInt(); scan.nextLine();
        System.out.print("Min Goal: ");
        goal.setAmount(scan.nextDouble());
        scan.nextLine();
        System.out.print("Description: ");
        goal.setExtraDescription(scan.nextLine());

        sendCommandToServer(ServerCommandFactory.addGoalToProject(goal, projectId));
        return receiveResponseFromServer();
    }


    public ServerMessage addRewardToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        Reward reward = new Reward();
        int projectId;

        System.out.print("Project Name: ");
        projectId = scan.nextInt(); scan.nextLine();
        System.out.print("Pledge min: ");
        reward.setMinAmount(scan.nextDouble());
        scan.nextLine();
        System.out.print("Gift name: ");
        reward.setDescription(scan.nextLine());

        sendCommandToServer(ServerCommandFactory.addRewardToProject(reward, projectId));
        return receiveResponseFromServer();

    }

    public ServerMessage addQuestionToProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        int projectId;
        String question;

        System.out.print("Project Id: ");
        projectId = scan.nextInt(); scan.nextLine();
        System.out.print("Question: ");
        question = scan.nextLine();

        sendCommandToServer(ServerCommandFactory.addQuestionToProject(question, projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage addOptionToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        int projectId;
        String option;

        System.out.print("Project Id: ");
        projectId = scan.nextInt(); scan.nextLine();
        System.out.print("Option: ");
        option = scan.nextLine();

        sendCommandToServer(ServerCommandFactory.addOptionToProject(new DecisionOption(projectId, option), projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage removeGoalFromProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        Goal goal = new Goal();
        int projectId;

        System.out.print("Project Id: ");
        projectId = scan.nextInt(); scan.nextLine();
        System.out.print("Min Goal: ");
        goal.setAmount(scan.nextDouble());
        scan.nextLine();
        System.out.print("Description: ");
        goal.setExtraDescription(scan.nextLine());

        sendCommandToServer(ServerCommandFactory.removeGoalFromProject(goal, projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage removeRewardFromProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        int rewardId, projectId;

        System.out.print("Project id: ");
        projectId = scan.nextInt(); scan.nextLine();
        System.out.print("Reward id: ");
        rewardId = scan.nextInt(); scan.nextLine();

        sendCommandToServer(ServerCommandFactory.removeRewardFromProject(rewardId, projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage getProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        int projectId;

        System.out.print("Choose project id: ");
        projectId = scan.nextInt(); scan.nextLine();

        this.sendCommandToServer(ServerCommandFactory.getProject(projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage getRewardsFromProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        int projectId;

        System.out.print("Choose project id: ");
        projectId = scan.nextInt(); scan.nextLine();

        this.sendCommandToServer(ServerCommandFactory.getRewardsFromProject(projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage getGoalsFromProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        int projectId;

        System.out.print("Choose project id: ");
        projectId = scan.nextInt(); scan.nextLine();

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

        System.out.print("Project: ");
        projectId = scan.nextInt(); scan.nextLine();
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
        Scanner scan = new Scanner(System.in);
        System.out.print("Project Id: ");
        int projectId = scan.nextInt(); scan.nextLine();

        sendCommandToServer(ServerCommandFactory.getProjectOptions(projectId));
        return receiveResponseFromServer();
    }



    public ServerMessage sendMessageFromProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        Message message = new Message();

        System.out.print("Project Id: ");
        message.setProjectId(scan.nextInt());
        System.out.println("Message: ");
        message.setText(scan.nextLine());

        this.sendCommandToServer(ServerCommandFactory.sendMessageFromProject(message));
        return receiveResponseFromServer();
    }

    // TODO responder a mensagens precisaria só de mensagem id
    public ServerMessage sendMessageToProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        Message message = new Message();

        System.out.print("Project Id: ");
        message.setProjectId(scan.nextInt());
        System.out.println("Message: ");
        message.setText(scan.nextLine());

        this.sendCommandToServer(ServerCommandFactory.sendMessageFromProject(message));
        return receiveResponseFromServer();
    }

    public ServerMessage sendRewardToUser() throws IOException {
        Scanner scan = new Scanner(System.in);
        int rewardId, toUserId;

        System.out.print("Reward: ");
        rewardId = scan.nextInt(); scan.nextLine();
        System.out.print("Person name: ");
        toUserId = scan.nextInt(); scan.nextLine();

        sendCommandToServer(ServerCommandFactory.sendRewardToUser(rewardId, toUserId));
        return receiveResponseFromServer();
    }

    public ServerMessage pledge() throws IOException {
        Scanner scan = new Scanner(System.in);
        Pledge pledge = new Pledge();
        System.out.print("Project Name: ");
        pledge.setProjectId(scan.nextInt());
        System.out.print("Amount: ");
        pledge.setAmount(scan.nextDouble());
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

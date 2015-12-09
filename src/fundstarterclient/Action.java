package fundstarterclient;

import com.sun.corba.se.spi.activation.Server;
import fundstarter.*;

import java.io.IOException;
import java.io.Serializable;
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
/*
        Scanner scan = new Scanner(System.in);
        Project newProject = new Project();
        ArrayList<Reward> rewards = new ArrayList<>();
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
                question.addAnswer(input, 0);
        } while(!input.equals(""));

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
        */
        return new ServerMessage();
    }

    public ServerMessage cancelProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        String projectId;
        System.out.print("Project id: ");
        projectId = scan.nextLine();
        this.sendObjectToServer(ServerCommandFactory.cancelProject(projectId));
        return receiveResponseFromServer();
    }

    public ServerMessage addAdminToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("addAdminToProject");
        System.out.print("Project Id: ");
        String projectId = scan.nextLine();
        System.out.print("New Admin Username: ");
        String username = scan.nextLine();

        this.sendObjectToServer(command);
        return receiveResponseFromServer();
    }

    public ServerMessage addGoalToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        String projectId;
        Goal goal = new Goal();

        command.setCommand("addGoalToProject");
        System.out.print("Project Name: ");
        projectId = scan.nextLine();
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
        String projectId;

        command.setCommand("addRewardToProject");
        System.out.print("Project Name: ");
        projectId = scan.nextLine();
        System.out.print("Pledge min: ");
        reward.setMinAmount(scan.nextDouble());
        scan.nextLine();
        System.out.print("Gift name: ");
        reward.setDescription(scan.nextLine());

        sendCommandToServer(ServerCommandFactory.addRewardToProject(reward, projectId));
        return receiveResponseFromServer();

    }

    // TODO
    public ServerMessage addQuestionToProject() throws IOException {
            return new ServerMessage();
    }

    // TODO
    public ServerMessage addOptionToServer() throws IOException{
        return new ServerMessage();
    }

    public ServerMessage removeGoalFromProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        Goal goal = new Goal();
        String projectId;

        System.out.print("Project Name: ");
        projectId = scan.nextLine();
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
        String rewardId;

        System.out.print("Reward id: ");
        rewardId = scan.nextLine();

        sendCommandToServer(ServerCommandFactory.removeRewardFromProject(rewardId));
        return receiveResponseFromServer();
    }

    public ServerMessage getProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        String projectId;

        System.out.print("Choose project id: ");
        projectId = scan.nextLine();

        this.sendCommandToServer(ServerCommandFactory.getProject(projectId));
        return receiveResponseFromServer();
    }

    // TODO
    public ServerMessage getRewardsFromProject() throws IOException {
        return new ServerMessage();
    }

    // TODO
    public ServerMessage getGoalsFromProject() throws IOException {
        return new ServerMessage();
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
        String projectId;

        System.out.print("Project: ");
        projectId = scan.nextLine();
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
        String rewardId, toUserId;


        System.out.print("Reward: ");
        rewardId = scan.nextLine();
        System.out.print("Person name: ");
        toUserId = scan.nextLine();

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

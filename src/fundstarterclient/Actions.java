package fundstarterclient;

import fundstarter.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by xavier on 28-10-2015.
 */
public class Actions {
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Command command;

    public Actions(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public String login() throws IOException {
        Scanner scan = new Scanner(System.in);
        ServerMessage serverMessage;
        String username = "";
        do {
            command = new Command();

            command.setCommand("login");
            System.out.print("Username: ");
            command.addArgument(username = scan.nextLine());
            System.out.print("Password: ");
            command.addArgument(scan.nextLine());

            sendCommandToServer(command);
            serverMessage = receiveResponseFromServer();
        }
        while(serverMessage.isErrorHappened());

        return username;

    }

    public void signUp() throws IOException {
        Scanner scan = new Scanner(System.in);

        ServerMessage serverMessage;
        do {
            command = new Command();

            command.setCommand("signup");
            System.out.print("Username: ");
            command.addArgument(scan.nextLine());
            System.out.print("Password: ");
            command.addArgument(scan.nextLine());
            System.out.print("Repeat Password: ");  command.addArgument(scan.nextLine());

            sendCommandToServer(command);
            serverMessage = receiveResponseFromServer();
        }
        while(serverMessage.isErrorHappened());

    }

    public void newProject() throws IOException {
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

        System.out.print("Project Name: "); newProject.setName(projectName = scan.nextLine());
        System.out.print("Description: ");  newProject.setDescription(scan.nextLine());
        System.out.print("Limit date (YYYYMMDD): "); newProject.setDate(scan.nextLine());
        System.out.print("Goal: "); newProject.setGoal(scan.nextDouble());  scan.nextLine();
        System.out.print("Question: "); question.setQuestion(scan.nextLine());
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
            System.out.print("Min pledge: ");   reward.setPledgeMin(pledgeMin = scan.nextDouble()); scan.nextLine();
            System.out.print("Reward: ");       reward.setGiftName(scan.nextLine());
            if(reward.getPledgeMin() != 0)
                rewards.add(reward);
        } while(pledgeMin != 0);
        newProject.setRewards(rewards);
        System.out.println("Extra Rewards: ");
        do {
            Extra extra = new Extra();
            extra.setProjectName(projectName);
            System.out.print("Min Goal: ");     extra.setGoalMin(goalMin = scan.nextDouble()); scan.nextLine();
            System.out.print("Reward: ");       extra.setDescription(scan.nextLine());
            if(extra.getGoalMin() != 0)
                extras.add(extra);
        } while(goalMin != 0);
        newProject.setExtras(extras);
        command.setAttachedObject(newProject);

        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void pledge() throws IOException {
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("pledge");
        System.out.print("Project Name: "); command.addArgument(scan.nextLine());
        System.out.print("Amount: ");       command.addArgument(scan.nextLine());
        System.out.print("Decision: ");     command.addArgument(scan.nextLine());

        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void projectDetails() throws IOException {
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Project receiveProject;

        command.setCommand("details");
        System.out.print("Choose project: ");   command.addArgument(scan.nextLine());

        this.sendCommandToServer(command);
        receiveProject = (Project) receiveResponseFromServer().getContent();
        if(receiveProject != null)
            receiveProject.toString();
    }

    public void sendMessageToProject() throws IOException {
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Message newMessage = new Message();

        command.setCommand("sendMessageToProject");
        System.out.print("Project Name: "); newMessage.setSendTo(scan.nextLine());
        System.out.println("Message: ");    newMessage.setText(scan.nextLine());

        command.setAttachedObject(newMessage);
        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void offerRewardToPerson() throws IOException {
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("sendReward");
        System.out.print("Project name: "); command.addArgument(scan.nextLine());
        System.out.print("Reward: ");       command.addArgument(scan.nextLine());
        System.out.print("Person name: ");  command.addArgument(scan.nextLine());

        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    // View
    public void viewMessages() throws  IOException{
        command = new Command();
        ArrayList<Message> messages;

        command.setCommand("viewMessages");
        this.sendCommandToServer(command);
        messages = (ArrayList<Message>)receiveResponseFromServer().getContent();
        for(Message mess : messages){
            System.out.println(mess.toString());
        }
    }

    public void viewBalance() throws IOException {
        command = new Command();

        command.setCommand("viewBalance");
        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void viewRewards() throws IOException {
        command = new Command();
        ArrayList<AttributedReward> rewards;

        command.setCommand("viewRewards");
        sendCommandToServer(command);
        rewards = (ArrayList<AttributedReward>)receiveResponseFromServer().getContent();
        for(AttributedReward reward : rewards){
            System.out.println(reward.toString());
        }
    }




    public void projectsInProgress() throws IOException{
        command = new Command();
        ArrayList<Project> projectsInProgress;
        ServerMessage message = null;

        command.setCommand("listInProgress");
        this.sendCommandToServer(command);
        message = receiveResponseFromServer();
        if(!message.isErrorHappened()){
            projectsInProgress = (ArrayList<Project>) message.getContent();
            for(Project proj : projectsInProgress){
                System.out.println(proj.toString());
            }
        }


    }

    public void projectsExpired() throws IOException{
        command = new Command();
        ArrayList<Project> projectsExpired;
        ServerMessage message = null;

        command.setCommand("listExpired");
        this.sendCommandToServer(command);
        message = receiveResponseFromServer();
        if(!message.isErrorHappened()){
            projectsExpired = (ArrayList<Project>)message.getContent();
            for(Project proj : projectsExpired){
                System.out.println(proj.toString());
            }
        }

    }

    public void listMessages(String projectName) throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("messages");
        command.addArgument(projectName);
        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void sendMessageToOtherUser() throws IOException {
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("sendMessage");
        System.out.print("To: ");   command.addArgument(scan.nextLine());
        System.out.print("Text: "); command.addArgument(scan.nextLine());

        this.sendObjectToServer(command);
        this.receiveResponseFromServer();
    }

    public void cancelProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("cancelProject");
        System.out.print("Project Name: "); command.addArgument(scan.nextLine());

        this.sendObjectToServer(command);
        this.receiveResponseFromServer();
    }

    public void addAdmin() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();

        command.setCommand("addAdminToProject");
        System.out.print("Project Name: ");         command.addArgument(scan.nextLine());
        System.out.print("New Admin Username: ");   command.addArgument(scan.nextLine());

        this.sendObjectToServer(command);
        this.receiveResponseFromServer();
    }

    public void logout() throws IOException{
        command = new Command();

        command.setCommand("logout");
        this.sendObjectToServer(command);
        this.receiveResponseFromServer();
    }

    public void addRewardToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Reward reward = new Reward();

        command.setCommand("addRewardToProject");
        System.out.print("Project Name: ");     reward.setProjectName(scan.nextLine());
        System.out.print("Pledge min: ");       reward.setPledgeMin(scan.nextDouble()); scan.nextLine();
        System.out.print("Gift name: ");        reward.setGiftName(scan.nextLine());

        command.setAttachedObject(reward);
        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void removeRewardFromProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Reward reward = new Reward();

        command.setCommand("removeRewardFromProject");
        System.out.print("Project Name: ");     reward.setProjectName(scan.nextLine());
        System.out.print("Pledge min: ");       reward.setPledgeMin(scan.nextDouble()); scan.nextLine();
        System.out.print("Gift name: ");        reward.setGiftName(scan.nextLine());

        command.setAttachedObject(reward);
        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void addExtraLevelToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Extra extra = new Extra();

        command.setCommand("addExtraToProject");
        System.out.print("Project Name: ");     extra.setProjectName(scan.nextLine());
        System.out.print("Min Goal: ");         extra.setGoalMin(scan.nextDouble());    scan.nextLine();
        System.out.print("Description: ");      extra.setDescription(scan.nextLine());

        command.setAttachedObject(extra);
        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void removeExtraLevelToProject() throws IOException{
        Scanner scan = new Scanner(System.in);
        command = new Command();
        Extra extra = new Extra();

        command.setCommand("removeExtraFromProject");
        System.out.print("Project Name: ");     extra.setProjectName(scan.nextLine());
        System.out.print("Min Goal: ");         extra.setGoalMin(scan.nextDouble());    scan.nextLine();
        System.out.print("Description: ");      extra.setDescription(scan.nextLine());

        command.setAttachedObject(extra);
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

    public Command getCommand() {
        return command;
    }

}

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

    public Actions(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public Command login(){
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("login");
        System.out.print("Username: "); command.addArgument(scan.nextLine());
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
        AttributedReward gift = new AttributedReward();

        System.out.print("To: ");           gift.setSendTo(scan.nextLine());
        System.out.print("Project Name: "); gift.setProjectName(scan.nextLine());
        System.out.print("Gift: ");         gift.setGiftName(scan.nextLine());

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
    }

    public void offerRewardToPerson() throws IOException {
        Scanner scan = new Scanner(System.in);
        Command command = new Command();

        command.setCommand("sendReward");
        System.out.print("Person name: ");  command.addArgument(scan.nextLine());
        System.out.print("Reward: ");       command.addArgument(scan.nextLine());

        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    // View
    public void viewMessages() throws  IOException{
        Command command = new Command();
        ArrayList<Message> messages;

        command.setCommand("viewMessages");
        this.sendCommandToServer(command);
        messages = (ArrayList<Message>)receiveResponseFromServer().getContent();
        for(Message mess : messages){
            System.out.println(mess.toString());
        }
    }

    public void viewBalance() throws IOException {
        Command command = new Command();

        command.setCommand("viewBalance");
        this.sendCommandToServer(command);
        this.receiveResponseFromServer();
    }

    public void viewRewards() throws IOException {
        Command command = new Command();
        ArrayList<AttributedReward> rewards;

        command.setCommand("viewRewards");
        sendCommandToServer(command);
        rewards = (ArrayList<AttributedReward>)receiveResponseFromServer().getContent();
        for(AttributedReward reward : rewards){
            System.out.println(reward.toString());
        }
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
        ArrayList<Project> projectsInProgress;

        command.setCommand("listInProgress");
        this.sendCommandToServer(command);
        projectsInProgress = (ArrayList<Project>)receiveResponseFromServer().getContent();
        for(Project proj : projectsInProgress){
            System.out.println(proj.toString());
        }
    }

    public void projectsExpired() throws IOException{
        Command command = new Command();
        ArrayList<Project> projectsInProgress;

        command.setCommand("listExpired");
        this.sendCommandToServer(command);
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
        Command command = new Command();

        command.setCommand("sendMessage");
        System.out.print("To: ");   command.addArgument(scan.nextLine());
        System.out.print("Text: "); command.addArgument(scan.nextLine());

        this.sendObjectToServer(command);
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

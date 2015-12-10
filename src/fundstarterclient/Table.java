package fundstarterclient;

import com.bethecoder.ascii_table.ASCIITable;
import fundstarter.*;

import java.util.ArrayList;

/**
 * Created by Miguel Prata Leal on 31/10/15.
 */
public class Table {
    String [] header;
    String [][] data;

    public Table() {
    }

    public Table(String[] header, String[][] data) {
        this.header = header;
        this.data = data;
    }
    public Table(String header, String data) {
        this.header = new String[1];
        this.data = new String[1][1];

        this.header[0] = header;
        this.data[0][0] = data;
    }

    public void printTableOfProjects(ArrayList<Project> listOfProjects) {
        header = new String[6];
        header[0] = "Expiration Date";
        header[1] = "Project ID";
        header[2] = "Project Name";
        header[3] = "Goal";
        header[4] = "Amount Earned";
        header[5] = "Progress";

        data = new String[listOfProjects.size()][6];
        for (int i = 0; i < listOfProjects.size(); i++) {
            Project project = listOfProjects.get(i);
            data[i][0] = project.getExpirationDate().toString();
            data[i][1] = project.getProjectId() + "";
            data[i][2] = project.getName();
            data[i][3] = project.getFirstGoalValue() + "€";
            data[i][4] = project.getTotalAmountEarned() + "€";
            data[i][5] = (Math.round(project.getPercentageOfProgress() * 100.0)/100.0) + "%";
        }

        ASCIITable.getInstance().printTable(header, data);

    }

    public void printProject(Project project) {
        header = new String[8];
        header[0] = "Project ID";
        header[1] = "Project Name";
        header[2] = "Description";
        header[3] = "Expiration Date";
        header[4] = "Goal";
        header[5] = "Amount Earned";
        header[6] = "Question";
        header[7] = "Progress";

        data = new String[1][8];
        data[0][0] = project.getProjectId() + "";
        data[0][1] = project.getName();
        data[0][2] = project.getDescription();
        data[0][3] = project.getExpirationDate().toString();
        data[0][4] = project.getFirstGoalValue() + "€";
        data[0][5] = project.getTotalAmountEarned() + "€";
        data[0][6] = project.getQuestion();
        data[0][7] = (Math.round(project.getPercentageOfProgress() * 100.0)/100.0) + "%";

        ASCIITable.getInstance().printTable(header, data);
    }

    public void printTableOfMessages(ArrayList<Message> listOfMessages) {
        header = new String[4];
        header[0] = "Date";
        header[1] = "From";
        header[2] = "Message";
        header[3] = "Project Associate";


        data = new String[listOfMessages.size()][4];
        for (int i = 0; i < listOfMessages.size(); i++) {
            Message message = listOfMessages.get(i);
            data[i][0] = message.getDate().toString();
            data[i][1] = "" + message.getPledgerUserId();
            data[i][2] = message.getText();
            data[i][3] = "" + message.getProjectId();

        }
        ASCIITable.getInstance().printTable(header, data);

    }

    public void printTableOfAttributedRewards(ArrayList<Reward> rewards) {
        header = new String[4];
        header[0] = "Project";
        header[1] = "Reward ID";
        header[2] = "Reward";
        header[3] = "Estado";

        data = new String[rewards.size()][4];
        for (int i = 0; i < rewards.size(); i++) {
            Reward reward = rewards.get(i);
            data[i][0] = reward.getRewardId() + "";
            data[i][1] = reward.getProjectName();
            data[i][2] = reward.getDescription();
            data[i][3] = (reward.getDone()) ? "Confirmado" : "Previsto";
        }
        ASCIITable.getInstance().printTable(header, data);

    }

    public void printTableOfRewards(ArrayList<Reward> rewards) {
        header = new String[3];
        header[0] = "Minimum Pledge";
        header[1] = "Reward ID";
        header[2] = "Reward";

        data = new String[rewards.size()][3];
        for (int i = 0; i < rewards.size(); i++) {
            Reward reward = rewards.get(i);
            data[i][0] = reward.getMinAmount() + "€";
            data[i][1] = reward.getRewardId() + "";
            data[i][2] = reward.getDescription();
        }
        ASCIITable.getInstance().printTable(header, data);

    }

    public void printTableOfPledges(ArrayList<Pledge> pledges) {
        header = new String[3];
        header[0] = "Project";
        header[1] = "Amount";
        header[2] = "Decision";


        data = new String[pledges.size()][3];
        for (int i = 0; i < pledges.size(); i++) {
            Pledge pledge = pledges.get(i);
            data[i][0] = pledge.getProjectName();
            data[i][1] = pledge.getAmount() + "€";
            data[i][2] = "" + pledge.getDecision();

        }
        ASCIITable.getInstance().printTable(header, data);

    }

    public void printTableOfGoals(ArrayList<Goal> goals) {
        header = new String[2];
        header[0] = "Minimum Goal";
        header[1] = "Goal";

        data = new String[goals.size()][2];
        for (int i = 0; i < goals.size(); i++) {
            Goal goal = goals.get(i);
            data[i][0] = goal.getAmount() + "€";
            data[i][1] = (goal.getExtraDescription() == null) ? "-" : goal.getExtraDescription();
        }
        ASCIITable.getInstance().printTable(header, data);

    }

    public void printTableOfOptions(String question, ArrayList<DecisionOption>options) {

        System.out.println("Question: " + question);
        header = new String[3];
        header[0] = "Option ID";
        header[1] = "Option";
        header[2] = "Votes";


        data = new String[options.size()][3];

        int i = 0;
        for (DecisionOption option : options) {
            data[i][0] = option.getOptionId() + "";
            data[i][1] = option.getDescription();
            data[i][2] = "" + (int)option.getVotes();
            i++;
        }
        ASCIITable.getInstance().printTable(header, data);

    }



    public void printTable() {
        ASCIITable.getInstance().printTable(header, data);
    }
}

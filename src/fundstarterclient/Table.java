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
        header = new String[5];
        header[0] = "Expiration Date";
        header[1] = "Project Name";
        //header[1] = "Creator";
        //header[2] = "Description";
        //header[3] = "Expiration Date";
        header[2] = "Goal";
        header[3] = "Amount Earned";
        header[4] = "Progress";

        data = new String[listOfProjects.size()][5];
        for (int i = 0; i < listOfProjects.size(); i++) {
            Project project = listOfProjects.get(i);
            data[i][0] = project.getDate();
            data[i][1] = project.getName();
            //data[i][1] = project.getCreator();
            //data[i][2] = project.getDescription();
            //data[i][3] = project.getDate();
            data[i][2] = project.getGoal() + "€";
            data[i][3] = project.getTotalAmountEarned() + "€";
            data[i][4] = project.getPercentageOfProgress() + "%";
        }

        ASCIITable.getInstance().printTable(header, data);

    }

    public void printProject(Project project) {
        header = new String[7];
        header[0] = "Project Name";
        header[1] = "Creator";
        header[2] = "Description";
        header[3] = "Expiration Date";
        header[4] = "Goal";
        header[5] = "Amount Earned";
        header[6] = "Progress";

        data = new String[1][7];
        data[0][0] = project.getName();
        data[0][1] = project.getCreator();
        data[0][2] = project.getDescription();
        data[0][3] = project.getDate();
        data[0][4] = project.getGoal() + "€";
        data[0][5] = project.getTotalAmountEarned() + "€";
        data[0][6] = project.getPercentageOfProgress() + "%";

        ASCIITable.getInstance().printTable(header, data);

    }

    public void printTableOfMessages(ArrayList<Message> listOfMessages) {
        header = new String[3];
        header[0] = "Date";
        header[1] = "From";
        header[2] = "Message";

        data = new String[listOfMessages.size()][3];
        for (int i = 0; i < listOfMessages.size(); i++) {
            Message message = listOfMessages.get(i);
            data[i][0] = message.getData();
            data[i][1] = message.getSendFrom();
            data[i][2] = message.getText();
        }
        ASCIITable.getInstance().printTable(header, data);

    }

    public void printTableOfAttributedRewards(ArrayList<AttributedReward> attributedRewards) {
        header = new String[3];
        header[0] = "Project";
        header[1] = "Reward";
        header[2] = "Estado";

        data = new String[attributedRewards.size()][3];
        for (int i = 0; i < attributedRewards.size(); i++) {
            AttributedReward attributedReward = attributedRewards.get(i);
            data[i][0] = attributedReward.getProjectName();
            data[i][1] = attributedReward.getGiftName();
            data[i][2] = (attributedReward.getDone()) ? "Confirmado" : "Previsto";
        }
        ASCIITable.getInstance().printTable(header, data);

    }

    public void printTableOfRewards(ArrayList<Reward> rewards) {
        header = new String[2];
        header[0] = "Minimum Pledge";
        header[1] = "Reward";

        data = new String[rewards.size()][2];
        for (int i = 0; i < rewards.size(); i++) {
            Reward reward = rewards.get(i);
            data[i][0] = reward.getPledgeMin() + "€";
            data[i][1] = reward.getGiftName();
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
            data[i][2] = pledge.getAnswer();

        }
        ASCIITable.getInstance().printTable(header, data);

    }

    public void printTableOfExtras(ArrayList<Extra> extras) {
        header = new String[2];
        header[0] = "Minimum Goal";
        header[1] = "Extra";

        data = new String[extras.size()][2];
        for (int i = 0; i < extras.size(); i++) {
            Extra extra = extras.get(i);
            data[i][0] = extra.getGoalMin() + "€";
            data[i][1] = extra.getDescription();
        }
        ASCIITable.getInstance().printTable(header, data);

    }

    public void printTableOfQuestion(Question question) {
        System.out.println("Question: " + question.getQuestion());
        header = new String[2];
        header[0] = "Answer";
        header[1] = "Votes";


        data = new String[question.getAnswers().size()][2];

        int i = 0;
        for (Answer answer : question.getAnswers()) {
            data[i][0] = answer.getDescription();
            data[i][1] = "" + answer.getVotes();
            i++;
        }
        ASCIITable.getInstance().printTable(header, data);

    }



    public void printTable() {
        ASCIITable.getInstance().printTable(header, data);
    }
}

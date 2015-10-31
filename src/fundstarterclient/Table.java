package fundstarterclient;

import com.bethecoder.ascii_table.ASCIITable;
import fundstarter.AttributedReward;
import fundstarter.Message;
import fundstarter.Project;

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
        header = new String[4];
        header[0] = "Project Name";
        //header[1] = "Creator";
        //header[2] = "Description";
        //header[3] = "Expiration Date";
        header[1] = "Goal";
        header[2] = "Amount Earned";
        header[3] = "Progress";

        data = new String[listOfProjects.size()][4];
        for (int i = 0; i < listOfProjects.size(); i++) {
            Project project = listOfProjects.get(i);
            data[i][0] = project.getName();
            //data[i][1] = project.getCreator();
            //data[i][2] = project.getDescription();
            //data[i][3] = project.getDate();
            data[i][1] = project.getGoal() + "€";
            data[i][2] = project.getTotalAmountEarned() + "€";
            data[i][3] = project.getPercentageOfProgress() + "%";
        }

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

    public void printTable() {
        ASCIITable.getInstance().printTable(header, data);
    }
}

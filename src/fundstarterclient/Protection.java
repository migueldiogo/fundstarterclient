package fundstarterclient;

import java.util.Scanner;

/**
 * Created by xavier on 09-12-2015.
 */
public class Protection {
    public Protection() {}

    public int verifyProjectId(){
        Scanner scan = new Scanner(System.in);
        boolean inputNeeded = false;
        int projectId = 0;

        // Verifica input, tem que ser um inteiro maior que 0
        while(!inputNeeded){
            System.out.print("Project id: ");
            if(scan.hasNextInt()){
                projectId = scan.nextInt();
                if(projectId < 0) inputNeeded = false;
                else inputNeeded = true;
            }
            else{
                System.out.println("Wrong input, try it again.");
            }
            scan.nextLine();
        }

        return projectId;
    }

    public int verifyRewardId(){
        Scanner scan = new Scanner(System.in);
        boolean inputNeeded = false;
        int rewardId = 0;

        // Verifica input, tem que ser um inteiro maior que 0
        while(!inputNeeded){
            System.out.print("Reward id: ");
            if(scan.hasNextInt()){
                rewardId = scan.nextInt();
                if(rewardId < 0) inputNeeded = false;
                else inputNeeded = true;
            }
            else{
                System.out.println("Wrong input, try it again.");
            }
            scan.nextLine();
        }

        return rewardId;
    }
}

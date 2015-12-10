package fundstarterclient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by xavier on 09-12-2015.
 */
public class Protection {
    public Protection() {}

    public int verifyInt(String st){
        Scanner scan = new Scanner(System.in);
        boolean inputNeeded = false;
        int projectId = 0;

        // Verifica input, tem que ser um inteiro maior que 0
        while(!inputNeeded){
            System.out.print(st);
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


    public double verifyDouble(String st){
        Scanner scan = new Scanner(System.in);
        boolean inputNeeded = false;
        double firstGoalValue = 0.0;

        // Verifica input, tem que ser um double maior que 0
        while(!inputNeeded){
            System.out.print(st);
            if(scan.hasNextDouble()){
                firstGoalValue = scan.nextDouble();
                if(firstGoalValue < 0) inputNeeded = false;
                else inputNeeded = true;
            }
            else{
                System.out.println("Wrong input, try it again.");
            }
            scan.nextLine();
        }

        return firstGoalValue;
    }

    public int verifiyUserChoice(){
        Scanner scan = new Scanner(System.in);
        boolean inputNeeded = false;
        int choice = 0;

        // Verifica input
        while(!inputNeeded){
            System.out.print("Please enter your choice: ");
            if(scan.hasNextInt()){
                choice = scan.nextInt();
                inputNeeded = true;
            }
            else{
                System.out.println("Te choice needs to be an integer.");
            }
            scan.nextLine();
        }

        return choice;
    }

    // Return -1 in error, 0 sucess
    public int verifyDate(String[] parts){
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear(), month = localDate.getMonthValue(), day = localDate.getDayOfMonth();

        if(parts.length != 3)
            return -1;

        int analiseYear = Integer.parseInt(parts[0]), analiseMonth = Integer.parseInt(parts[1]), analiseDay = Integer.parseInt(parts[2]);

        if(analiseYear < year || analiseYear < 0)
            return -1;

        if(parts[0].length() != 4 || parts[1].length() != 2 || parts[2].length() != 2)
            return -1;

        if(analiseYear == year && analiseMonth == month && analiseDay <= day)
            return -1;

        // Ano bissexto
        if(analiseYear % 4 == 0 && (analiseYear % 100 != 0 || analiseYear % 400 == 0)){
            if(analiseMonth == 2 && analiseMonth > 29)
                return -1;
            else
                return 0;
        }

        if((analiseMonth <= 0 || analiseMonth > 12 )
            || (analiseDay <= 0 || analiseDay > 31))
            return -1;

        if((analiseMonth == 4 || analiseMonth == 6 || analiseMonth == 9 || analiseMonth == 11)
            && analiseDay == 31)
            return -1;

        // February
        if(analiseMonth == 2 && analiseDay > 28)
            return -1;

        return 0;
    }

    public String verifyString(String st){
        Scanner scan = new Scanner(System.in);
        String result = "";

        do {
            System.out.print(st);
            result = scan.nextLine();
        } while(result.isEmpty());

        return result;
    }
}

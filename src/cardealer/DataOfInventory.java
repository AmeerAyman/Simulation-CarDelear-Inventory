/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardealer;

import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Ameer Ayman
 */
public class DataOfInventory {
    private double demand_prob[][]; //array holds the demand and their probability
    private double demandCumulativeAndRanges[][];//array holds tha cumulative probability for demand
    private int RandomNumberForDemand[][]; // array holds the random number for the deand table
    private double LeadTime_prob[][]; //array holds the lead time and their probability
    private double LeadTimeCumulativeAndRanges[][]; //array holds tha cumulative probability for lead time
    private int RandomDigitForLeadTime[][]; // array holds the random number for the lead time table
    private int DayAndCycle[][]; // array holds the day and the cycle
    private int BeginningInventory[][]; // array that calculate the calender for the inventory
    Scanner input = new Scanner(System.in);
    int numOfDemand;
    int numOfLeadTime;
    
    DataOfInventory(){
        
    }
    //function to get the demand and the probability
    public void getDemand_And_their_probabilities() 
    {
            //double sum=0.0;
            numOfDemand=Integer.parseInt(JOptionPane.showInputDialog("Enter how many demand values"));
            demand_prob = new double[numOfDemand][2];
            //do{
            //System.out.println("Enter the Demand and their probabilities");
           
            for (int i = 0; i <numOfDemand; i++) {
                for (int j = 0; j < 2; j++) {
                    demand_prob[i][j] = Double.parseDouble(JOptionPane.showInputDialog("Enter Demand And Probability "+(i+1)));
                    //sum+=demand_prob[i][1];
                }
            }
            //}while(sum!=1);

     }
    
    //function calculate tha cumulative probability for demand
    public void calculateCumulativeProbabilityAndRanges_Demand() 
    {
        //String result="";
        double sumForCum = 0.0;
        demandCumulativeAndRanges = new double[numOfDemand][4];
        //result+="Demand\t\t"+"Cumulative\t\t"+"minRange\t\t"+"maxRange\t\t"+"\n";
        for (int i = 0; i <numOfDemand; i++) {
            demandCumulativeAndRanges[i][0]=this.demand_prob[i][0];
            demandCumulativeAndRanges[i][2] = Math.round(sumForCum*100);
            sumForCum += this.demand_prob[i][1];
            demandCumulativeAndRanges[i][1] = sumForCum;
            demandCumulativeAndRanges[i][3] = Math.round(sumForCum*100);
           // result+=demandCumulativeAndRanges[i][0]+"\t\t"+demandCumulativeAndRanges[i][1]+"\t\t"+demandCumulativeAndRanges[i][2]+"\t\t"+demandCumulativeAndRanges[i][3]+"\n";
        }
        //return result;
    }
    
    public void displayDemandCumulativeAndRanges(int numOfDemand) 
    {
        //System.out.println("Cumulitive probability"+"             "+"min range"+"             "+"max range");
        for (int i = 0; i <numOfDemand; i++) {
            for (int j = 0; j < 4; j++) {

                System.out.print(this.demandCumulativeAndRanges[i][j] + "\t");
            }
            System.out.println();
        }
    }
    // function to get the lead time and their probability
    public void getLeadTime_And_their_probabilities() 
    {
        numOfLeadTime=Integer.parseInt(JOptionPane.showInputDialog("Enter how many LeadTime values"));
        LeadTime_prob = new double[numOfLeadTime][2];
        //System.out.println("Enter the Lead Time and their probabilities");
        for (int i = 0; i < numOfLeadTime; i++) {
            for (int j = 0; j < 2; j++) {
                LeadTime_prob[i][j] = Double.parseDouble(JOptionPane.showInputDialog("Enter Leadtime "+(i+1)+"And Probability "+(i+1)));
            }
        }
    }
    // function calculate cumulative and ranges for lead time
    public void calculateCumulativeProbabilityAndRanges_LeadTime() 
    {
        double sumForCum = 0.0;
        LeadTimeCumulativeAndRanges = new double[numOfLeadTime][3];
        for (int i = 0; i < numOfLeadTime; i++) {
            LeadTimeCumulativeAndRanges[i][1] = sumForCum*10;
            sumForCum += this.LeadTime_prob[i][1];
            LeadTimeCumulativeAndRanges[i][0] = sumForCum;
            if(i==numOfLeadTime-1)
                LeadTimeCumulativeAndRanges[i][2] = sumForCum*0;
            else
                LeadTimeCumulativeAndRanges[i][2] = sumForCum*10;
        }
    }
    
    public void displayLeadTimeCumulativeAndRanges(int numOfLeadTime) 
    {
        //System.out.println("Cumulitive probability"+"             "+"min range"+"             "+"max range");
        for (int i = 0; i < numOfLeadTime; i++) {
            for (int j = 0; j < 3; j++) {

                System.out.print(this.LeadTimeCumulativeAndRanges[i][j] + "\t"); 
            }
            System.out.println();
        }
    }
    // function to generate random number and assign the demand
    public void generateRandAndAssignDemand(int numOfDays)
    {
        RandomNumberForDemand=new int[numOfDays][2];
        Random rand = new Random();
        for (int i = 0; i <numOfDays; i++) {
            RandomNumberForDemand[i][0] = rand.nextInt(90) + 10;
            int value=RandomNumberForDemand[i][0];
            for (int j = 0; j < 5; j++) {
                if(value>demandCumulativeAndRanges[j][2] && value<=demandCumulativeAndRanges[j][3])
                    RandomNumberForDemand[i][1]=(int) demandCumulativeAndRanges[j][0];
            }
        }
    }
    // function to get the dya and the cycle for each day
    public void calculateDayAndCycle(int numOfDays)
    {
        int cycle=1;
        int j=1;
            DayAndCycle=new int[numOfDays][2];
            int counter=1;
            for(int i=0;i<numOfDays;i++){
                DayAndCycle[i][0]=j;
                DayAndCycle[i][1]=cycle;
                if(counter==5){
                    cycle++;
                    counter=1;
                    j=1;
                }
                else{
                    counter++;
                    j++;
                }
            }
    }
    // function to genrate random number for the lead time and calculate how many day until order arrives 
    public void GenerateRandomDigitForLeadTime_CalculateDaysUntilOrderArrives(int numOfDays)
    {
        RandomDigitForLeadTime=new int[numOfDays][2];
        Random rand = new Random();
        int counter=0;
        int schadualedToArrive=2;
            for(int i=0;i<numOfDays;i++){
                RandomDigitForLeadTime[i][0]=0;
                
                if(schadualedToArrive>=DayAndCycle[i][0])
                    RandomDigitForLeadTime[i][1]=schadualedToArrive-DayAndCycle[i][0];
                else
                    RandomDigitForLeadTime[i][1]=0;
                if(counter==4) {
                    RandomDigitForLeadTime[i][0]=rand.nextInt(10) + 1;
                    int value=RandomDigitForLeadTime[i][0];
                    for (int j = 0; j < 3; j++) {
                        if(value>LeadTimeCumulativeAndRanges[j][1] && value<=LeadTimeCumulativeAndRanges[j][2])
                            schadualedToArrive=(int) LeadTime_prob[j][0];
                    }
                    RandomDigitForLeadTime[i][1]=schadualedToArrive;
                    counter=0;
                }
                    else
                        counter++;
            }
    }
    
    // function calculate the calender of the inventory
    public String calculateBeginningInventory(int numOfDays,int BeginningOfInventory,int firstOrderOfUnits,int scheduledToArrive,int maxInventory)
    {
        String result="";
        //BeginningOfInventory=3;
        //firstOrderOfUnits=8;
        //scheduledToArrive=2;
        //maxInventory=11;
        BeginningInventory=new int[numOfDays][4];
        BeginningInventory[0][0]=BeginningOfInventory;
        
        if(BeginningInventory[0][0]<RandomNumberForDemand[0][1])
            BeginningInventory[0][1]=0;
        else
            BeginningInventory[0][1]=BeginningInventory[0][0]-RandomNumberForDemand[0][1];
        
        if(BeginningInventory[0][0]-RandomNumberForDemand[0][1]<0)
            BeginningInventory[0][2]=Math.abs(BeginningInventory[0][0]-RandomNumberForDemand[0][1]);
        else
            BeginningInventory[0][2]=0;
        
        BeginningInventory[0][3]=firstOrderOfUnits;
        //int counter=1;
        int scheduledArrives=scheduledToArrive;
        
        result+="Day\t"+"Cycle\t"+"beginInventory\t"+"Demand\t"+"EndInventory\t"+"Shortage\t"+"order\t"+"RandomLead\t"+"DaysUntilRecieves"+"\n";
        result+="---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"+"\n";
        result+=DayAndCycle[0][0]+"\t"+DayAndCycle[0][1]+"\t"+BeginningInventory[0][0]+"\t"+RandomNumberForDemand[0][1]+"\t"+BeginningInventory[0][1]+"\t"+BeginningInventory[0][2]+"\t"+BeginningInventory[0][3]+"\t"+RandomDigitForLeadTime[0][0]+"\t"+RandomDigitForLeadTime[0][1]+"\n";
        
        for(int i=1;i<numOfDays;i++){
            int orderQuantityFromColumnOrderQuantity=0;
            for (int j = i; j>=0; j--) {
                if(BeginningInventory[j][3]>0){
                    orderQuantityFromColumnOrderQuantity=BeginningInventory[j][3];
                    break;
                }
            }
            if(DayAndCycle[i][0]-scheduledArrives==1)
                BeginningInventory[i][0]=BeginningInventory[i-1][1]+orderQuantityFromColumnOrderQuantity;
            else
                BeginningInventory[i][0]=BeginningInventory[i-1][1];
            
            if(BeginningInventory[i-1][2]>0 && BeginningInventory[i][0]>0)
                BeginningInventory[i][1]=BeginningInventory[i][0]-RandomNumberForDemand[i][1]-BeginningInventory[i-1][2];
            else if(BeginningInventory[i][0]<RandomNumberForDemand[i][1])
                BeginningInventory[i][1]=0;
            else
                BeginningInventory[i][1]=BeginningInventory[i][0]-RandomNumberForDemand[i][1];
            
            if(BeginningInventory[i][0]-RandomNumberForDemand[i][1]<0)
                BeginningInventory[i][2]=Math.abs(BeginningInventory[i][0]-RandomNumberForDemand[i][1]);
            else
                BeginningInventory[i][2]=0;
            
            if(DayAndCycle[i][0]%5==0)
                BeginningInventory[i][3]=maxInventory-BeginningInventory[i][1]+BeginningInventory[i][2];
            else
                BeginningInventory[i][3]=0;
            
            int value=RandomDigitForLeadTime[i][0];
            for (int j = 0; j < 3; j++) {
                        if(value>LeadTimeCumulativeAndRanges[j][1] && value<=LeadTimeCumulativeAndRanges[j][2])
                            scheduledArrives=(int) LeadTime_prob[j][0];
                    }
            result+="---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"+"\n";
            result+=DayAndCycle[i][0]+"\t"+DayAndCycle[i][1]+"\t"+BeginningInventory[i][0]+"\t"+RandomNumberForDemand[i][1]+"\t"+BeginningInventory[i][1]+"\t"+BeginningInventory[i][2]+"\t"+BeginningInventory[i][3]+"\t"+RandomDigitForLeadTime[i][0]+"\t"+RandomDigitForLeadTime[i][1]+"\n";
        }
        return result;
    }
    // calaculate average ending inventory
    public double averageEndingInventory(int numOfDays)
    {
        int totalEndingInventory=0;
        double averageEnding;
        for (int i = 0; i < numOfDays; i++) {
            totalEndingInventory+=BeginningInventory[i][1];
        }
        averageEnding=totalEndingInventory/numOfDays;
        return averageEnding;
    }
    // calculate shoratge
    public int calculateShortage(int numOfDays)
    {
        int shortage=0;
        for (int i = 0; i < numOfDays; i++) {
            if(BeginningInventory[i][2]>0)
                shortage++;
            }
        return shortage;
    }
    
}

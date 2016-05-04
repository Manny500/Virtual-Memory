import java.io.*;
import java.util.*;

public class RANFileGEN{
  public static void main(String[] args){
    
    int readW = 0;
    String rW = null;
    Random rand = new Random();
    int randomPID = 0;
    int randomAdd = 512;
    int moveU = 0;
    
     //write inside a try to maake sure all errors are handled
    try{
      
      //write the the specified file
      PrintWriter outFile = new PrintWriter("Large-Input-Processess.txt");
      
      //for loop calculates the new process details
      for(int i = 0; i < 20; i++){
        
        randomPID = 100 + rand.nextInt((106 - 100) + 1);
        
        if(moveU == 0){
        
        randomAdd++;
        
        moveU = 1 + rand.nextInt((4 - 1) + 1);
        }else{
          
          moveU--;
        }
        
        if(readW == 0){
          
          rW = "W";
        readW = 2 + rand.nextInt((15 - 2) + 1);
        
        }else {  
          
          readW--;
          
          rW = "R";
      }
        //write new process to output file
        outFile.println(randomPID+", "+randomAdd+", "+rW);
      }
      
      for(int i = 0; i < 20; i++){
        
        randomPID = 107 + rand.nextInt((112 - 107) + 1);
        
       if(moveU == 0){
        
        randomAdd++;
        
        moveU = 1 + rand.nextInt((4 - 1) + 1);
        }else{
          
          moveU--;
        }
        
        if(readW == 0){
          
          rW = "W";
        readW = 2 + rand.nextInt((15 - 2) + 1);
        
        }else {  
          
          readW--;
          
          rW = "R";
      }
        //write new process to output file
        outFile.println(randomPID+", "+randomAdd+", "+rW);
      }
      
      for(int i = 0; i < 20; i++){
        
        randomPID = 113 + rand.nextInt((118 - 113) + 1);
        
        if(moveU == 0){
        
        randomAdd++;
        
        moveU = 1 + rand.nextInt((4 - 1) + 1);
        }else{
          
          moveU--;
        }
        
        if(readW == 0){
          
          rW = "W";
        readW = 2 + rand.nextInt((15 - 2) + 1);
        
        }else {  
          
          readW--;
          
          rW = "R";
      }
        //write new process to output file
        outFile.println(randomPID+", "+randomAdd+", "+rW);
      }
      
      outFile.close();
      
    }catch(Exception e){
      
      System.out.println(e);
      
    }
    
  }
}
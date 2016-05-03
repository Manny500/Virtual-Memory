//Imports
import java.io.*;
import java.util.*;

/*
 * @Author Manny + Conrad
 */

public class Parse{
  
  //Instance  Variables
  ArrayList<Process> list;
  Process process;
  String readWrite;
  int address;
  int pid;
  
  //Constructor
  public Parse(){
    
    list = new ArrayList<Process>();
    
  }
  ////////////////////////////METHODS//////////////////////////////////
  
  /*
   * @return ArrayList<Process> list of process
   * gets the ArrayList list
   */
  public ArrayList<Process> getList(){
    
    return this.list;
  } //end of size method  
  
  /*
   * @return int size of arraylist
   * gets the size of the arraylist
   */
  public int size(){
    
    return list.size();
  } //end of size method  
  
  /*
   * @param file is the file to read from 
   * reads a file and creates process depending on the file
   */
  public void addToDataBase(File file){
    
    try{
      
      //assign the file to the scanner
      Scanner sc = new Scanner(file);
      String line;
      String[] lineVector;
      String str;
      
      //this loop goes through the file and scans until the very end
      while (sc.hasNextLine()){ 
        
        //get next line, read the entire line separated by commans into liveVector
        line = sc.nextLine();
        lineVector = line.split(",");
        
        pid = Integer.parseInt(lineVector[0]); 
        //System.out.println("pid: "+pid);
        
        str = lineVector[1]; 
        str = str.replaceAll("\\s+",""); //in order to get rid of white spaces
        address = Integer.parseInt(str);
        //System.out.println("address: "+address);
        
        readWrite = lineVector[2]; 
        readWrite = readWrite.replaceAll("\\s+","");
        //System.out.println("read/write: "+readWrite);
        
        process = new Process(pid, address, readWrite);
        list.add(process);
        
      }
      //will catch the the file if the program doesnt find the file
    }catch (FileNotFoundException e) {
      
      System.out.println("Invalid file path.");
      
    }catch (NumberFormatException e) {   
      
      System.out.println(e);
      
    } 
    
  }//end of addToDataBase
  
////////////////////////End of Methods//////////////////////////////////////////////////// 
}//end of Parse
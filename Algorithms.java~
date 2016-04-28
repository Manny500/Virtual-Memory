//Imports
import java.io.*;
import java.util.*;

/*
 * @Author Manny + Conrad
 */

public class Algorithms{
  
  //Instance  Variables
  ArrayList<Process> sortingL = new ArrayList<Process>();
  Parse parse = new Parse();
  Process process;
  Process process1;
  Boolean modify;
  int virtualSizeRep = 16; //64 (2^16)KB virtual memory
  int physicalSizeRep = 11; //2 (2^11)KB physical memory
  int physicalAddress;
  int faultCount; 
  int pageNum; //frame number, after that its a new page?
  int access;
  int empty;
  int time;
  int pid;

  //Constructor
  public Algorithms(){
    
  }
  ////////////////////////////METHODS/////////////////////////////////////////////
  
  /*
   * @param algorthm specifies what scheduling algorithm to use
   * @param fileName specifies what file to get the process information from
   * @param pageSize is the size of the page
   * Will get the processes and apply the scheduling algorithm to them
   */
  public void manager(String algorithm, String pageSize, String fileName){
    
    //convert the string pageSize into an int
    int result = Integer.parseInt(pageSize);
    int pageSizebitRep = log(result, 2);
    int frameNum = (int)Math.pow(2,(physicalSizeRep - pageSizebitRep));
    
    if( (result >= 32) && (result <= 512) && (result % 2) == 0){
      
      //parse input file
      File processFile = new File(fileName); 
      
      //create a datastructure to store the new processes from database
      ArrayList<Process> list = new ArrayList<Process>();
      
      //add the process to database
      parse.addToDataBase(processFile);
      
      //get the process from the database
      list = parse.getList();
      
      //performs the different scheduling algorithms
      //depending on the parameters specifications
      if(algorithm == "opra"){
        
        //opra(list);
        
      }else if(algorithm == "fifo"){
        
        fifo(list, frameNum);
        
      }else if(algorithm == "lru"){
        
        //lru(list);
        
      }else if(algorithm == "sca"){
        
        //sca(list);
        
      }else if(algorithm == "esc"){
        
        //esc(list);
        
      }else if(algorithm == "hybrid"){
        
        //hybrid(list);
        
      }
      
    }else{
      
      System.out.println("Page size is not between the allowed range");
      
    }
    
  }//end of manager
  
  /*
   * @param 
   * Optimal page replacement algorithm
   */
  public void opra(){
    
  }//end of opra
  
  /*
   * @param list contains the process to be scheduled
   * @param frameNumber is the frame numbers to use in the scheduler
   * FIFO first in first out algorithm
   */
  public void fifo(ArrayList<Process> list, int frameNum){
    
    //Instance Variables
    Process[] myList = new Process[frameNum];
    physicalAddress = 0;
    faultCount = 0;
    modify = null;
    pageNum = 1; //frame number, after that its a new page?
    access = -1;
    empty = -1;
    time = 0;
    
    //clear the sorting list, make sure it is empty
    sortingL.clear();

    //loop through all the process in the list
    while(!list.isEmpty()){
      
      //obtain the next process to schedule
      process = list.get(0);
      pid = process.getPid();
      list.remove(0); //make sure to remove, avoid duplicates
      
      //check if we have to modify any of the frames
      for(int x = 0; x < frameNum; x++){
        
        if( myList[x] != null){
          
          //check if any of the spots are a hit
          if(pid != myList[x].getPid()){
            
            modify = true;
            
            //else if not null and equal, it means we have a hit
          }else{
            
            access = x;
            modify = false;
            break;
          }
          
          //means that there is an empty spot
        }else{
          
          empty = x;
          modify = true;
          break;
        }
      }
      
      //If we have to modify, calculate which frame to modify
      if(modify == true){
        
        //if there is a space open
        if(empty != -1){
          
          //assign the process to the empty space/frame
          myList[empty] = process;
          
          physicalAddress = physicalAddress + process.getAddress() ; //+myList[empty].getAddress();
          
          System.out.println("loaded page #"+ pageNum +" of processes #"+ process.getPid() + " to frame #"+ empty +" with no replacement.");
          System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
          
          //reset the empty variable
          empty = -1;
        }else{
          
          //add to sorting list
          for(int x = 0; x < frameNum; x++){
            
            sortingL.add(myList[x]);
            
          }
          
          //sorting list gives us process that has the lowest allocation time
          //aka first one to be allocated out of the list
          sort(sortingL, "allocationTime min-max");
          
          //get the first one allocated to the frames
          //list is sorted already 
          process1 = sortingL.get(0);
          sortingL.clear();//make sure to clear, dont want repeated processes or old ones
          
          //check which full frame to replace
          for(int x = 0; x < frameNum; x++){
            
            if(process1.getPid() == myList[x].getPid() ){
              
              myList[x] = process;
              process.setAllocationTime(time);
              
              physicalAddress = physicalAddress + process.getAddress() ;
              
              System.out.println("loaded page #"+ pageNum +" of processes #"+ process.getPid() + " to frame #"+ x +" with replacement.");
              System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
              break;
            }
            
          }
        }
        
        //since we modified we have a page fault
        faultCount ++;
        
       //if not modifications just print info and move on
      }else{
        
        System.out.println("no page fault. accessed frame #"+ access);
        System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
        
      }
      
      //time helps us determine how long/when a process was allocated
      time++; 
      
    }
    
    //print summary info
    System.out.println("Number of page faults: " + faultCount +". Number of memory accesses: " + 11);
    
  }//end of fifo
  
  /*
   * @param 
   * LRU least recently used
   */
  public void lru(){
    
  }//end of lru
  
  /*
   * @param
   * Second Chance Algorithm
   */
  public void sca(){
    
  }//end of sca
  
  /*
   * @param
   * Enhanced second chance algorithm
   */
  public void esca(){
    
  }//end of esca
  
  /*
   * @param 
   * Hybrid
   */
  public void hybrid(){
    
  }//end of hybrid
  
  /*
   * @param x is the number to be computer
   * @param base is the log base number
   * @return int is the log(base) of x
   * log in order to obtain the bit representation of the bytes
   */
  public int log(int x, int base){
    
    return (int) (Math.log(x) / Math.log(base));
    
  }//end of log
  
  /*
   * @param takes list in a list of process
   * @param order specifies the way to sort the list
   * @returns ArrayList<Process> a sorted list of process according to their arrival time
   */ 
  public ArrayList<Process> sort(ArrayList<Process> list, String order){
    
    //lower values are first: 0 1 2 3 4 5 
    if(order == "allocationTime min-max"){
      
      //start of the insertion sort algorithm
      for (int i = 1; i < list.size(); i++){
        
        Process process = list.get(i);
        int index = process.getAllocationTime(); 
        int j = i;
        
        //if neighbors are out of order swap them
        while (j > 0 && list.get(j-1).getAllocationTime() > index){
          
          Process process1 =list.get(j-1);
          list.set(j-1, process);
          list.set(j,process1);
          j--;
          
        }
        
      }
    }
    
    return list;
  }//end of sort
  
////////////////////////End of Methods//////////////////////////////////////////////////// 
}//end of Algorithms class
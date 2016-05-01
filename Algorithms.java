//Imports
import java.io.*;
import java.util.*;

/*
 * @Author Manny + Conrad
 */

public class Algorithms{
  
  //Instance  Variables
  ArrayList<Process> sortingL = new ArrayList<Process>();
  ArrayList<Process> processList = new ArrayList<Process>();
  ArrayList<Process> oldList = new ArrayList<Process>();
  Parse parse = new Parse();
  String virtualAddress = null;
  String frameSizeB = null;
  String offsetS = null;
  Process process1;
  Process process;
  Boolean modify;
  int virtualSizeRep = 16; //64 (2^16)KB virtual memory
  int physicalSizeRep = 11; //2 (2^11)KB physical memory
  int physicalAddress;
  int memoryCount;
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
    int offset = physicalSizeRep - pageSizebitRep; 
    int frameSize = (result/frameNum); //calculate the size of each frame
    
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
        
        opra(list, frameNum, frameSize, offset);
        
      }else if(algorithm == "fifo"){
        
        //fifo(list, frameNum, frameSize, offset);
        
      }else if(algorithm == "lru"){
        
        //lru(list);
        
      }else if(algorithm == "sca"){
        
        //sca(list);
        
      }else if(algorithm == "esc"){
        
        //esc(list);
        
      }else if(algorithm == "hybrid"){
        
        //hybrid(list, frameNum, frameSize, offset);
        
      }
      
    }else{
      
      System.out.println("Page size is not between the allowed range");
      
    }
    
  }//end of manager
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
  /*
   * @param 
   * Optimal page replacement algorithm
   */
  public void opra(ArrayList<Process> list, int frameNum, int frameSize, int offset)
  {
    //Instance Variables
    Process[] myList = new Process[frameNum];//these are the frames
    virtualAddress = null;
    physicalAddress = 0;
    frameSizeB = null;
    memoryCount = 0;
    offsetS = null;
    faultCount = 0;
    modify = null;
    pageNum = 1; //frame number, after that its a new page?
    access = -1;
    empty = -1;
    time = 0;
    
    //clear the sorting list, make sure it is empty
    sortingL.clear();
    
    //loop through all the process in the list as they come in
    while(!list.isEmpty())
    {
      
      //obtain the next process to schedule
      process = list.get(0);
      pid = process.getPid();
      list.remove(0); //make sure to remove, avoid duplicates
      
      //check if we have to modify any of the frames
      for(int x = 0; x < frameNum; x++)
      {
        
        if( myList[x] != null)
        {
          
          //check if any of the spots are a hit
          if(pid != myList[x].getPid())
          {
            
            modify = true;
            
            //else if not null and equal, it means we have a hit
          }else
          {
            
            access = x;
            modify = false;
            break;
          }
          
          //means that there is an empty spot
        }else
        {
          
          empty = x;
          modify = true;
          break;
        }
      }
      
      //If we have to modify, calculate which frame to modify
      if(modify == true)
      {
        
        //reads only acces memory once
        if(process.getReadWrite().equals("R"))
        {
          
          memoryCount++;
          
          //if dirty must access memory twice
        }else if(process.getReadWrite().equals("W"))
        {
          
          memoryCount = memoryCount + 2;
        }
        
        //if there is a space open
        if(empty != -1)
        {
          
          //assign the process to the empty space/frame
          myList[empty] = process;
          
          //get binary rep of the virtual address
          virtualAddress = Integer.toBinaryString((process.getAddress()));
          
          //get the offset bits
          virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
          offsetS = virtualAddress.substring(0,offset);
          offsetS = new StringBuilder(offsetS).reverse().toString(); //reverse offset to get actual value
          
          //translate the virtual adress to physical address
          //convert to binary string
          frameSizeB = Integer.toBinaryString((frameSize*empty));
          frameSizeB = frameSizeB.concat(offsetS);
          physicalAddress = Integer.parseInt(frameSizeB,2);
          
          //print information
          System.out.println("loaded page #"+ pageNum +" of processes #"+ process.getPid() + " to frame #"+ empty +" with no replacement.");
          System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
          
          //reset the empty variable
          empty = -1;
        }else
        {
          
          //add to sorting list
          for(int x = 0; x < frameNum; x++)
          {
            
            sortingL.add(myList[x]);
            
          }
          
          processList = list;//sets the processlist to list, keeps track of the remaining processes
          
          //sorting list gives us process that has the lowest allocation time
          //aka first one to be allocated out of the list
          sort(sortingL, "optimal");
          
          //get the first one allocated to the frames
          //list is sorted already 
          process1 = sortingL.get(0);
          sortingL.clear();//make sure to clear, dont want repeated processes or old ones
          
          //check which full frame to replace
          for(int x = 0; x < frameNum; x++)
          {
            
            if(process1.getPid() == myList[x].getPid() )
            {
              
              myList[x] = process;
              process.setAllocationTime(time);
              
              //get binary rep of the virtual address
              virtualAddress = Integer.toBinaryString((process.getAddress()));
              
              //get the offset bits
              virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
              offsetS = virtualAddress.substring(0,offset);
              offsetS = new StringBuilder(offsetS).reverse().toString(); //reverse offset to get actual value
              
              //translate the virtual adress to physical address
              //convert to binary string
              frameSizeB = Integer.toBinaryString((frameSize*x));
              frameSizeB = frameSizeB.concat(offsetS);
              physicalAddress = Integer.parseInt(frameSizeB,2);
              
              //print frame information
              System.out.println("loaded page #"+ pageNum +" of processes #"+ process.getPid() + " to frame #"+ x +" with replacement.");
              System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
              break;
            }
            
          }
        }
        
        //since we modified we have a page fault
        faultCount ++;
        
        //if not modifications just print info and move on
      }else
      {
        
        //check for dirty bit
        if(process.getReadWrite().equals("W"))
        {
          
          memoryCount++;
        }
        
        //get binary rep of the virtual address
        virtualAddress = Integer.toBinaryString((process.getAddress()));
        
        //get the offset bits
        virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
        offsetS = virtualAddress.substring(0,offset);
        offsetS = new StringBuilder(offsetS).reverse().toString();//reverse offset to get actual value
        
        //translate the virtual adress to physical address
        //convert to binary string
        frameSizeB = Integer.toBinaryString((frameSize*access));
        frameSizeB = frameSizeB.concat(offsetS);
        physicalAddress = Integer.parseInt(frameSizeB,2);
        
        //print frame information
        System.out.println("no page fault. accessed frame #"+ access);
        System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
        
      }
      
      //time helps us determine how long/when a process was allocated
      time++; 
      
    }
    
    //print summary info
    System.out.println("Number of page faults: " + faultCount +". Number of memory accesses: " + memoryCount);
    
  }//end of opra
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * @param list contains the process to be scheduled
   * @param frameNumber is the frame numbers to use in the scheduler
   * @param frameSize is the size of each frame
   * @param offset is the number of digits needed for the offset
   * FIFO first in first out scheduling algorithm
   */
  public void fifo(ArrayList<Process> list, int frameNum, int frameSize, int offset){
    
    //Instance Variables
    Process[] myList = new Process[frameNum];
    virtualAddress = null;
    physicalAddress = 0;
    frameSizeB = null;
    memoryCount = 0;
    offsetS = null;
    faultCount = 0;
    modify = null;
    pageNum = 1; //frame number, after that its a new page?
    access = -1;
    empty = -1;
    time = 0;
    
    //clear the sorting list, make sure it is empty
    sortingL.clear();
    
    //loop through all the process in the list as they come in
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
        
        //reads only acces memory once
        if(process.getReadWrite().equals("R")){
          
          memoryCount++;
          
          //if dirty must access memory twice
        }else if(process.getReadWrite().equals("W")){
          
          memoryCount = memoryCount + 2;
        }
        
        //if there is a space open
        if(empty != -1){
          
          //assign the process to the empty space/frame
          myList[empty] = process;
          
          //get binary rep of the virtual address
          virtualAddress = Integer.toBinaryString((process.getAddress()));
          
          //get the offset bits
          virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
          offsetS = virtualAddress.substring(0,offset);
          offsetS = new StringBuilder(offsetS).reverse().toString(); //reverse offset to get actual value
          
          //translate the virtual address to physical address
          //convert to binary string
          frameSizeB = Integer.toBinaryString((frameSize*empty));
          frameSizeB = frameSizeB.concat(offsetS);
          physicalAddress = Integer.parseInt(frameSizeB,2);
          
          //print information
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
              
              //get binary rep of the virtual address
              virtualAddress = Integer.toBinaryString((process.getAddress()));
              
              //get the offset bits
              virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
              offsetS = virtualAddress.substring(0,offset);
              offsetS = new StringBuilder(offsetS).reverse().toString(); //reverse offset to get actual value
              
              //translate the virtual adress to physical address
              //convert to binary string
              frameSizeB = Integer.toBinaryString((frameSize*x));
              frameSizeB = frameSizeB.concat(offsetS);
              physicalAddress = Integer.parseInt(frameSizeB,2);
              
              //print frame information
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
        
        //check for dirty bit
        if(process.getReadWrite().equals("W")){
          
          memoryCount++;
        }
        
        //get binary rep of the virtual address
        virtualAddress = Integer.toBinaryString((process.getAddress()));
        
        //get the offset bits
        virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
        offsetS = virtualAddress.substring(0,offset);
        offsetS = new StringBuilder(offsetS).reverse().toString();//reverse offset to get actual value
        
        //translate the virtual adress to physical address
        //convert to binary string
        frameSizeB = Integer.toBinaryString((frameSize*access));
        frameSizeB = frameSizeB.concat(offsetS);
        physicalAddress = Integer.parseInt(frameSizeB,2);
        
        //print frame information
        System.out.println("no page fault. accessed frame #"+ access);
        System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
        
      }
      
      //time helps us determine how long/when a process was allocated
      time++; 
      
    }
    
    //print summary info
    System.out.println("Number of page faults: " + faultCount +". Number of memory accesses: " + memoryCount);
    
  }//end of fifo
  
  /*
   * @param 
   * LRU least recently used
   */
  public void lru(ArrayList<Process> list, int frameNum, int frameSize, int offset)
  {
    //Instance Variables
    Process[] myList = new Process[frameNum];//these are the frames
    virtualAddress = null;
    physicalAddress = 0;
    frameSizeB = null;
    memoryCount = 0;
    offsetS = null;
    faultCount = 0;
    modify = null;
    pageNum = 1; //frame number, after that its a new page?
    access = -1;
    empty = -1;
    time = 0;
    
    //clear the sorting list, make sure it is empty
    sortingL.clear();
    
    //loop through all the process in the list as they come in
    while(!list.isEmpty())
    {
      
      //obtain the next process to schedule
      process = list.get(0);
      pid = process.getPid();
      oldList.add(list.remove(0)); //make sure to remove, avoid duplicates, adds to old list
      
      //check if we have to modify any of the frames
      for(int x = 0; x < frameNum; x++)
      {
        
        if( myList[x] != null)
        {
          
          //check if any of the spots are a hit
          if(pid != myList[x].getPid())
          {
            
            modify = true;
            
            //else if not null and equal, it means we have a hit
          }else
          {
            
            access = x;
            modify = false;
            break;
          }
          
          //means that there is an empty spot
        }else
        {
          
          empty = x;
          modify = true;
          break;
        }
      }
      
      //If we have to modify, calculate which frame to modify
      if(modify == true)
      {
        
        //reads only acces memory once
        if(process.getReadWrite().equals("R"))
        {
          
          memoryCount++;
          
          //if dirty must access memory twice
        }else if(process.getReadWrite().equals("W"))
        {
          
          memoryCount = memoryCount + 2;
        }
        
        //if there is a space open
        if(empty != -1)
        {
          
          //assign the process to the empty space/frame
          myList[empty] = process;
          
          //get binary rep of the virtual address
          virtualAddress = Integer.toBinaryString((process.getAddress()));
          
          //get the offset bits
          virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
          offsetS = virtualAddress.substring(0,offset);
          offsetS = new StringBuilder(offsetS).reverse().toString(); //reverse offset to get actual value
          
          //translate the virtual adress to physical address
          //convert to binary string
          frameSizeB = Integer.toBinaryString((frameSize*empty));
          frameSizeB = frameSizeB.concat(offsetS);
          physicalAddress = Integer.parseInt(frameSizeB,2);
          
          //print information
          System.out.println("loaded page #"+ pageNum +" of processes #"+ process.getPid() + " to frame #"+ empty +" with no replacement.");
          System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
          
          //reset the empty variable
          empty = -1;
        }else
        {
          
          //add to sorting list
          for(int x = 0; x < frameNum; x++)
          {
            
            sortingL.add(myList[x]);
            
          }
          
          processList = list;//sets the processlist to list, keeps track of the remaining processes
          
          //sorting list gives us process that has the lowest allocation time
          //aka first one to be allocated out of the list
          sort(sortingL, "lru");
          
          //get the first one allocated to the frames
          //list is sorted already 
          process1 = sortingL.get(0);
          sortingL.clear();//make sure to clear, dont want repeated processes or old ones
          
          //check which full frame to replace
          for(int x = 0; x < frameNum; x++)
          {
            
            if(process1.getPid() == myList[x].getPid() )
            {
              
              myList[x] = process;
              process.setAllocationTime(time);
              
              //get binary rep of the virtual address
              virtualAddress = Integer.toBinaryString((process.getAddress()));
              
              //get the offset bits
              virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
              offsetS = virtualAddress.substring(0,offset);
              offsetS = new StringBuilder(offsetS).reverse().toString(); //reverse offset to get actual value
              
              //translate the virtual adress to physical address
              //convert to binary string
              frameSizeB = Integer.toBinaryString((frameSize*x));
              frameSizeB = frameSizeB.concat(offsetS);
              physicalAddress = Integer.parseInt(frameSizeB,2);
              
              //print frame information
              System.out.println("loaded page #"+ pageNum +" of processes #"+ process.getPid() + " to frame #"+ x +" with replacement.");
              System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
              break;
            }
            
          }
        }
        
        //since we modified we have a page fault
        faultCount ++;
        
        //if not modifications just print info and move on
      }else
      {
        
        //check for dirty bit
        if(process.getReadWrite().equals("W"))
        {
          
          memoryCount++;
        }
        
        //get binary rep of the virtual address
        virtualAddress = Integer.toBinaryString((process.getAddress()));
        
        //get the offset bits
        virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
        offsetS = virtualAddress.substring(0,offset);
        offsetS = new StringBuilder(offsetS).reverse().toString();//reverse offset to get actual value
        
        //translate the virtual adress to physical address
        //convert to binary string
        frameSizeB = Integer.toBinaryString((frameSize*access));
        frameSizeB = frameSizeB.concat(offsetS);
        physicalAddress = Integer.parseInt(frameSizeB,2);
        
        //print frame information
        System.out.println("no page fault. accessed frame #"+ access);
        System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
        
      }
      
      //time helps us determine how long/when a process was allocated
      time++; 
      
    }
    
    //print summary info
    System.out.println("Number of page faults: " + faultCount +". Number of memory accesses: " + memoryCount);
    
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
   * @param list contains the process to be scheduled
   * @param frameNumber is the frame numbers to use in the scheduler
   * @param frameSize is the size of each frame
   * @param offset is the number of digits needed for the offset
   * Hybrid is a modification of the fifo algorithm, algorithm called clock
   */
  public void hybrid(ArrayList<Process> list, int frameNum, int frameSize, int offset){
    
    //Instance Variables
    Process[] myList = new Process[frameNum];
    virtualAddress = null;
    physicalAddress = 0;
    frameSizeB = null;
    memoryCount = 0;
    offsetS = null;
    faultCount = 0;
    modify = null;
    pageNum = 1; //frame number, after that its a new page?
    access = -1;
    empty = -1;
    time = 0;
    
    //clear the sorting list, make sure it is empty
    //sortingL.clear();
    
    //loop through all the process in the list as they come in
    while(!list.isEmpty()){
      
      //obtain the next process to schedule
      process = list.get(0);
      pid = process.getPid();
      list.remove(0); //make sure to remove, avoid duplicates
      
      //check if we have to modify any of the frames, check for hits, empty spots
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
        
        //check how many times we need to access memory
        //reads only acces memory once
        if(process.getReadWrite().equals("R")){
          
          memoryCount++;
          
          //if dirty must access memory twice
        }else if(process.getReadWrite().equals("W")){
          
          memoryCount = memoryCount + 2;
        }
        
        //if there is a space open
        if(empty != -1){
          
          //assign the process to the empty space/frame
          //assign used bit to 1
          process.setUsedBit(1);
          myList[empty] = process;
          
          //get binary rep of the virtual address
          virtualAddress = Integer.toBinaryString((process.getAddress()));
          
          //get the offset bits
          virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
          offsetS = virtualAddress.substring(0,offset);
          offsetS = new StringBuilder(offsetS).reverse().toString(); //reverse offset to get actual value
          
          //translate the virtual adress to physical address
          //convert to binary string
          frameSizeB = Integer.toBinaryString((frameSize*empty));
          frameSizeB = frameSizeB.concat(offsetS);
          physicalAddress = Integer.parseInt(frameSizeB,2);
          
          //print information
          System.out.println("loaded page #"+ pageNum +" of processes #"+ process.getPid() + " to frame #"+ empty +" with no replacement.");
          System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
          
          //reset the empty variable
          empty = -1;
        }else{
          
          //look for the used bit equal to 0
          //check which full frame to replace
          for(int x = 0; x < frameNum; x++){
            
            if(myList[x].getUsedBit() == 0){
              
              process1 = myList[x];
              
              break;
            }else
              
              //set to zero and go to next process
              myList[x].setUsedBit(0);
            
            if(x == (frameNum - 1)){
              
              //reset the counter so we can go searching through the process again
              x = 0;
            }
              
          }
          
          //check which full frame to replace
          for(int x = 0; x < frameNum; x++){
            
            if(process1.getPid() == myList[x].getPid() ){
              
              myList[x] = process;
              process.setAllocationTime(time);
              
              //get binary rep of the virtual address
              virtualAddress = Integer.toBinaryString((process.getAddress()));
              
              //get the offset bits
              virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
              offsetS = virtualAddress.substring(0,offset);
              offsetS = new StringBuilder(offsetS).reverse().toString(); //reverse offset to get actual value
              
              //translate the virtual adress to physical address
              //convert to binary string
              frameSizeB = Integer.toBinaryString((frameSize*x));
              frameSizeB = frameSizeB.concat(offsetS);
              physicalAddress = Integer.parseInt(frameSizeB,2);
              
              //print frame information
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
        
        //check for dirty bit
        if(process.getReadWrite().equals("W")){
          
          memoryCount++;
        }
        
        //set used bit to 1, representing that it should stay for atleast one more round
        process.setUsedBit(1);
        
        //get binary rep of the virtual address
        virtualAddress = Integer.toBinaryString((process.getAddress()));
        
        //get the offset bits
        virtualAddress = new StringBuilder(virtualAddress).reverse().toString();
        offsetS = virtualAddress.substring(0,offset);
        offsetS = new StringBuilder(offsetS).reverse().toString();//reverse offset to get actual value
        
        //translate the virtual adress to physical address
        //convert to binary string
        frameSizeB = Integer.toBinaryString((frameSize*access));
        frameSizeB = frameSizeB.concat(offsetS);
        physicalAddress = Integer.parseInt(frameSizeB,2);
        
        //print frame information
        System.out.println("no page fault. accessed frame #"+ access);
        System.out.println("     Virtual Address: " + process.getAddress() + " -> Physical Address: " + physicalAddress);
        
      }
      
      //time helps us determine how long/when a process was allocated
      time++; 
      
    }
    
    //print summary info
    System.out.println("Number of page faults: " + faultCount +". Number of memory accesses: " + memoryCount);
    
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
  public ArrayList<Process> sort(ArrayList<Process> list, String order)
  {
    HashMap<Integer,Integer> processTimes = new HashMap<Integer,Integer>();//pids and times
    
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
    }else if(order.equalsIgnoreCase("lru"))
    {
      for(int i = 0; i<oldList.size(); i++)
      {
        //filling hashmap with pids and times
        if(!processTimes.containsKey(oldList.get(i).getPid()))
        {
          processTimes.put(oldList.get(i).getPid(), i);//pid and when found
        }
      }
      
      for(int i = 0; i<list.size()-1; i++)
      {
        if(processTimes.containsKey(list.get(i).getPid()) && processTimes.containsKey(list.get(i+1).getPid()))
        {
          //comparing the times, higher means used more recently
          if(processTimes.get(list.get(i).getPid()) > processTimes.get(list.get(i).getPid()))
          {
            Process proc = list.get(i);
            list.set(i,list.get(i+1));
            list.set(i+1, proc);
            i=0;//resets if a change was made to the list
          }
          
          //if process not found then it will be set as the next to be removed
          //meaning it has never been used before
        }else if(processTimes.containsKey(list.get(i+1).getPid()))
        {
          processTimes.put(list.get(i).getPid(),0);//not found so pid should be lower than earlier
          list.add(list.remove(i));
          i=0;
        }else if(processTimes.containsKey(list.get(i).getPid()))
        {
          processTimes.put(list.get(i+1).getPid(),0);//not found so pid should be lower than earlier
          list.add(list.remove(i+1));
          i=0;
        }else//neither i nor i+1 found
        {
          processTimes.put(list.get(i).getPid(),0);//not found so pid should be lower than earlier
          list.add(list.remove(i));
          processTimes.put(list.get(i+1).getPid(),0);//not found so pid should be lower than earlier
          list.add(list.remove(i+1));
          i=0;
        }
      }
      
    }else if(order.equalsIgnoreCase("optimal"))
    {
      for(int i = 0; i<processList.size(); i++)
      {
        //filling hashmap with pids and times
        if(!processTimes.containsKey(processList.get(i).getPid()))
        {
          processTimes.put(processList.get(i).getPid(), -i);//pid and negative when found
        }
      }
      
      for(int i = 0; i<list.size()-1; i++)
      {
        if(processTimes.containsKey(list.get(i).getPid()) && processTimes.containsKey(list.get(i+1).getPid()))
        {
          //comparing the times
          if(processTimes.get(list.get(i).getPid()) < processTimes.get(list.get(i).getPid()))
          {
            Process proc = list.get(i);
            list.set(i,list.get(i+1));
            list.set(i+1, proc);
            i=0;//resets if a change was made to the list
          }
          
          //if process not found then it will be set as the next to be removed
          //meaning the process will not occur again
        }else if(processTimes.containsKey(list.get(i+1).getPid()))
        {
          processTimes.put(list.get(i).getPid(),0);//not found so pid should be higher than negatives earlier
          list.add(list.remove(i));
          i=0;
        }else if(processTimes.containsKey(list.get(i).getPid()))
        {
          processTimes.put(list.get(i+1).getPid(),0);//not found so pid should be higher than negatives earlier
          list.add(list.remove(i+1));
          i=0;
        }else//neither i nor i+1 found
        {
          processTimes.put(list.get(i).getPid(),0);//not found so pid should be higher than negatives earlier
          list.add(list.remove(i));
          processTimes.put(list.get(i+1).getPid(),0);//not found so pid should be higher than negatives earlier
          list.add(list.remove(i+1));
          i=0;
        }
      }
    }
    
    return list;
  }//end of sort
  
////////////////////////End of Methods//////////////////////////////////////////////////// 
}//end of Algorithms class
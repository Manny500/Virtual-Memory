//Imports
import java.util.*;

/*
 * @Author Manny + Conrad 
 */

public class Process{
  
  //Instance Variables
  String readWrite;
  int allocationTime;
  int address;
  int usedBit;
  int pid;
  int modBit; //the second chance bit
  int refBit; //the esca bit
  

  //Constructors
  public Process(){
    
  }
  
  public Process(int pid, int address, String readWrite){
    
    this.pid = pid;
    this.readWrite = readWrite;
    this.address = address;
    this.allocationTime = 0;
    this.usedBit = -1;
    this.modBit = 0;
    this.refBit = 0;
    
  }
////////////////////////////METHODS//////////////////////////////////

  /*
   * @return int gets the pid of the process
   * will get the pid of the process
   */
  public int getPid(){
    
    return this.pid;
  }//end of getPid
  
   /*
   * @return int gets the pid of the process
   * will get the pid of the process
   */
  public void setPid(int pid){
    
    this.pid = pid;
  }//end of setPid
  
  /*
   * @return int gets the Address of the process
   * will get the Address of the process
   */
  public int getAddress(){
    
    return this.address;
  }//end of getAddress
  
  /*
   * @param time sets the Address of the process
   * will set the Address of the process
   */
  public void setAddress(int address){
    
    this.address = address;
  }//end of setAddress
 
  /*
   * @return int gets the ReadWrite of the process
   * will get the ReadWrite of the process
   */
  public String getReadWrite(){
    
    return this.readWrite;
  }//end of getReadWrite
  
  /*
   * @param p sets the readWrite of the process
   * will get the readWrite of the process
   */
  public void setReadWrite(String readWrite){
    
    this.readWrite = readWrite;
  }//end of setreadWrite
  
  /*
   * @return int gets the allocationTime of the process
   * will get the allocationTime of the process
   */
  public int getAllocationTime(){
    
    return this.allocationTime;
  }//end of getallocationTime
  
  /*
   * @param time sets the allocationTime of the process
   * will set the allocationTime of the process
   */
  public void setAllocationTime(int time){
    
    this.allocationTime = time;
  }//end of getallocationTime
  
   /*
   * @return int gets the usedBit of the process
   * will get the usedBit of the process
   */
  public int getUsedBit(){
    
    return this.usedBit;
  }//end of getusedBit
  
   /*
   * @return int gets the usedBit of the process
   * will get the usedBit of the process
   */
  public void setUsedBit(int usedBit){
    
    this.usedBit = usedBit;
  }//end of setusedBit
  
  /*
   * will switch the second chance bit to 0
   */
  public void sc0()
  {
    this.modBit = 0;
  }
  
  /*
   * will switch the second chance bit to 1
   */
  public void sc1()
  {
    this.modBit = 1;
  }
  
  /*
   * will return the second chance bit
   */
  public int getChance()
  {
    return this.modBit;
  }
  
  /*
   * will set the reference bit to 0
   */
  public void ref0()
  {
    this.refBit = 0;
  }
  
  /*
   * will set the reference bit to 1
   */
  public void ref1()
  {
    this.refBit = 1;
  }
  
  /*
   * will return the reference bit
   */
  public int getRef()
  {
    return this.refBit;
  }
  
////////////////////////End of Methods//////////////////////////////////////////////////// 
}//end of process
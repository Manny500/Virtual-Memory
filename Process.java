//Imports
import java.util.*;

/*
 * @Author Manny + Conrad 
 */

public class Process{
  
  //Instance Variables
  String readWrite;
  int address;
  int pid;
  int allocationTime;
  
  //Constructors
  public Process(){
    
  }
  
  public Process(int pid, int address, String readWrite){
    
    this.pid = pid;
    this.readWrite = readWrite;
    this.address = address;
    this.allocationTime = 0;
    
  }
////////////////////////////METHODS//////////////////////////////////

  /*
   * @return int gets the pid of the process
   * will get the pid of the process
   */
  public int getPid(){
    
    return this.pid = pid;
  }//end of getPid
  
   /*
   * @return int gets the pid of the process
   * will get the pid of the process
   */
  public void setPid(int pid){
    
    this.pid = pid;
  }//end of setPid
  
  /*
   * @return int gets the Burst_time of the process
   * will get the Burst_time of the process
   */
  public int getAddress(){
    
    return this.address;
  }//end of getAddress
  
  /*
   * @param time sets the Burst_time of the process
   * will set the Burst_time of the process
   */
  public void setAddress(int address){
    
    this.address = address;
  }//end of setAddress
 
  /*
   * @return int gets the Priority of the process
   * will get the Priority of the process
   */
  public String getReadWrite(){
    
    return this.readWrite;
  }//end of getReadWrite
  
  /*
   * @param p sets the Priority of the process
   * will get the Priority of the process
   */
  public void setReadWrite(String readWrite){
    
    this.readWrite = readWrite;
  }//end of setReadWrite
  
  /*
   * @return int gets the Burst_time of the process
   * will get the Burst_time of the process
   */
  public int getAllocationTime(){
    
    return this.allocationTime;
  }//end of getBurst_time
  
  /*
   * @param time sets the Burst_time of the process
   * will set the Burst_time of the process
   */
  public void setAllocationTime(int time){
    
    this.allocationTime = time;
  }//end of getBurst_time
  
////////////////////////End of Methods//////////////////////////////////////////////////// 
}//end of process
//Imports
//Imports
import java.io.*;
import java.util.*;

/*
 * @Author Manny + Conrad
 */

public class VirtualMemoryManager{
  public static void main(String[] args){
    
    //Instantiate classes
    Algorithms alg = new Algorithms();
    
    //generate Large process file, analysis purposes
    //"algorithm", "page size 32-512B (must be a power of two) (check that it is in that range)", "Input file name"
    alg.manager("fifo", "512", "Small-Input-Processess.txt");
    
  }//end of Main
}//end of Virtual-Memory-Manager
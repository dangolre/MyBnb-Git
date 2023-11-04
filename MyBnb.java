/** 
   Description : This programming assignment introduces a new rental room servicen called MyBnb. It utilizes the RentalRoom class which includes an ID, 
                 the number of beds, and room availability. MyBnb reads information from a file, stores it in the Arraylist, and uses it to assign  
                 appropriate room to customers and the number of staffs needed to clean the occupied rooms.
  
   Name : Reeya Dangol
*/

//importing
import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * Class that represents MyBnb
 */
public class MyBnb
{
    //data fields
   
   /**
      * file object is created
   */
   private File file;
   
   /**
      * creates a new array list 
   */
   private List<RentalRoom> rentalRoom = new ArrayList<>();
   
   
   /**
      * Initializes a object with all properties specified
      * @param filename represents the file from which the informations are read 
   */
   
   public MyBnb(String filename)
   {
      this.file = new File(filename);
      
      //file handling
      
      try 
      {  
         // creates a new scanner object and reads the contents of file
         Scanner scan = new Scanner(file);
         
         // true while there is next line in the file
         while (scan.hasNextLine())
         {  
            // reads and stores the value
            String line = scan.nextLine();
            String[] parts = line.split(" ");
            
            // separate the propertyID with number of beds
            String propertyID = parts[0]; 
            int numOfBeds = Integer.parseInt(parts[1]);
            
            // adds to the RentalRoom
            rentalRoom.add(new RentalRoom(propertyID, numOfBeds));
         }
         
         // closes the scanner object
         scan.close();
      }
      
      // catches the error if the file is not found and displays error message
      catch (FileNotFoundException error) 
      {
         System.out.println("An error occurred. " + error.getMessage()); 
      }
   }
   
   
   /**
      * counts the number of room with specific number of beds 
      * @return count of room with the number of beds matching the value of the argument
   */
   
   public int numberOfRooms(int beds)
   {  
      int count = 0;
      for (RentalRoom room : rentalRoom)
      {
         if (room.getNumBeds() == beds)
         count ++;
      }
      return count;
   }
   
   
   /**
      * assigns the first available room to the customer with atleast the number of beds as the customer wants 
      * @return roomID of the suitable room or "NONE" if there is no available room
   */
   
   public String chooseRoom(int minBeds)
   {
      for (RentalRoom room : rentalRoom)
      {
         if (room.getNumBeds() >= minBeds && room.isAvailable())
         {
            room.setAvailable(false);
            return room.getID();
         }       
      }
      return "NONE";  
   }
   
   
   /**
      * gives the number of staff needed to clean the occupied rooms
      * rooms with three or more beds require 2 staffs
      * group of three rooms with one or two beds require 1 staff and one or two additional rooms requires one additional staff
      * @return total number of staff required to clean the occupied rooms
   */
   
   public int numberOfStaffNeeded()
   {
      int staffNeeded = 0;
      int roomToClean = 0; 
           
      for (RentalRoom room : rentalRoom)
      {
         if (!room.isAvailable())
         {
            if (room.getNumBeds() >= 3)
            {
               staffNeeded += 2;
            }
            else
            {
               roomToClean++;
            }
         }
      }
      staffNeeded += (int)(Math.ceil((double)roomToClean / 3));
      return staffNeeded;
   }  
   
}

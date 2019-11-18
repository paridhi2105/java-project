/*Paternity Testing helps determine the genetic father and mother of a child.
In practice,a segment of child DNA is taken and replicated in a PCR(Polymerase Chain Reaction) machine and is subjected to electrophoresis.
The DNA segments segregate based on their molecular weights.
Similarly, the DNA samples of mother and father are run in a parallel lane. The child DNA segment positions are then mapped with the father's 
and mother's sample's positions and the identity of child is confirmed.

HERE, the child DNA is taken as a string from the user. We made two methods which help determine the possible sequences of the father and 
mother of the child. First, we try to match possible father DNA from the file father.txt and for each of these, the possible mother combination
is printed. If it cannot be found, we display an exception showing that it did not match.
A clone is a complete genetic copy of an individual. So, if the child DNA completely matches a father/mother DNA, then we display it is a clone.*/

/*---------------------------------------PROGRAM TO IMPLEMENT REAL-LIFE PATERNITY TESTING PROCEDURE.-----------------------------------------------------------------*/

import java.util.Scanner;  //importing Scanner class from util package
import java.io.*;         //importing io package
import java.lang.*;      //default package

class InvalidException extends Exception  //generating custom exception and inheritance of Exception class
{
    InvalidException(String string)
    {
        super(string);  //super() keyword to refer to super class's constructor
    }
}

class ChildDna
{
    String s;  // Instance variable for child DNA string
    StringBuilder str=new StringBuilder();

    ChildDna(String s)  // Parametrized constructor
    {
        this.s=s.toUpperCase();  // using 'this' keyword to refer to current class's instance variable AND using inbuilt method of String class
    }

    boolean PotentialFathers(String tmp)  //a method that will return true if any DNA sample in the father file matches certain characters of the input child DNA    
    {    
        str.setLength(0);  //to clear the stringbuilder for every new DNA string of father to be analysed
        int count=0;
        for(int i=0;i<9;++i)  // a finite loop
        {
            if(s.charAt(i)!=tmp.charAt(i))  // using charAt() method of String class
            {
                String j=String.valueOf(i); //converting int to String by using valueOf() method 
                str.append(j+" ");  // appending those indices to stringbuilder which were not matched in father DNA
            }
            else count++;
        }
        if (count==9) 
        {
            System.out.println("Given child is a clone with sequence:\n"+tmp); 
            System.exit(0);  //used to exit from the program
        }
        if(count>=1 && count!=9)  //conditional statement
        { 
            return true;
        }
        else 
        {
            try
            {
                throw new InvalidException("Did not match");  // using the 'throw' keyword to explicitly throw an exception when no matching indices are found
            }
            catch(InvalidException e)
            {
                System.out.println(e);
            }
            return false;
        }
    }

    boolean PotentialMothers(String tmp)  // returns true if the unmatched child DNA indices match a given mother DNA      
    {
        int count=0;
        for(int i=0;i<9;++i)
        {
            if(s.charAt(i)==tmp.charAt(i))
                count++; 
        }
        if (count==9) 
        {
            System.out.println("Given child is a clone with sequence:\n"+tmp); 
            System.exit(0);
        }
        for(String i:str.toString().split(" ",0))  //used a for each loop to iterate the unmatched indices of child DNA splitted using a String split() method
        {
            int j=Integer.parseInt(i);  //using parseInt() method of Integer wrapper class 
            if(s.charAt(j)==tmp.charAt(j)) 
                continue;
            else 
            {
                try
                {
                    throw new InvalidException("Did not match");
                }
                catch(InvalidException e)
                {
                    System.out.println(e);
                }
                return false;
            }
        }
        return true;
    }
}

public class PaternityTest  //use of access modifier 'public'
{
    public static void main(String args[])  //Java main() method
    {
        Scanner sc=new Scanner(System.in);  //using scanner class to take input from user
        System.out.println("Enter child DNA String consisting of 9 characters");
        String s=sc.next();
        ChildDna obj=new ChildDna(s);  //instantiating ChildDna class
        try  //using a try-catch block for exception handling of checked exceptions
        {
            FileReader father = new FileReader("father.txt");  //file handling using filereader
            BufferedReader brf = new BufferedReader(father);   //BufferedReader to read from file
            FileReader mother = new FileReader("mother.txt"); 
            BufferedReader brm = new BufferedReader(mother);
            String tmpf=brf.readLine();  // readLine() method of BufferedReader class to read the file line by line
            while(tmpf!=null)
            {   
                if(obj.PotentialFathers(tmpf))  
                {
                    String tmpm=brm.readLine();
                    while(tmpm!=null)
                    {
                        if(obj.PotentialMothers(tmpm))
                        {    
                            System.out.println("DNA sequence of father is:"+"\n"+tmpf+"\n"+"DNA sequence of mother is"+"\n"+tmpm);
                        }
                        tmpm=brm.readLine();
                    }
                }
                tmpf=brf.readLine();
            }
        }
        catch(Exception e)
        {
            System.out.println(e+"\nException handled");
        }
        finally
        { 
            System.out.println("ANALYSIS FINISHED");  //finally block with a try-catch block
        }  
    }
}

/*------------------------------------------------------------------------ END OF PROGRAM----------------------------------------------------------------------------*/
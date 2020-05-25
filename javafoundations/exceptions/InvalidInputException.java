//********************************************************************
//  InvalidInputException.java     Java Foundations
//
//  Represents the situation in which the input is invalid.
//********************************************************************

package javafoundations.exceptions;

public class InvalidInputException extends RuntimeException{
   //------------------------------------------------------------------
   //  Sets up this exception with an appropriate message.
   //------------------------------------------------------------------
   public InvalidInputException (String message)
   {
      super (message);
   }
}
//Trevor Nichols
//QuestionTree
//Section: AM; William Ceriale

//Creates an instance of QuestionTree. A game where the computer tries to 
//guess the user's object by asking yes/no questions. The computer grows 
//smarter with each game it loses by obtaining information from the user and
//updating its questions and objects.
import java.io.*;
import java.util.*;

public class QuestionTree {
    private QuestionNode initialQuestion;
    private Scanner console;
    
    //Constructs a new instance of QuestionTree
    public QuestionTree(){
        this.initialQuestion = new QuestionNode("computer");
        this.console = new Scanner(System.in); 
    }
    
    //Replaces the current QuestionTree with a new QuestionTree given a 
    //scanner with data in standard format.
    public void read(Scanner input){
        this.initialQuestion = this.readHelper(input);
    }
    
    //Assembles a new QuestionTree given a scanner and returns a QuestionNode.
    private QuestionNode readHelper(Scanner input){
        String nodeType = input.nextLine().trim();
        String nodeData = input.nextLine().trim();
        QuestionNode current = new QuestionNode(nodeData);
        
        if(nodeType.equals("Q:")){
            current.yesLeft = this.readHelper(input);
            current.noRight = this.readHelper(input);
        }
        return current;
    }
    
    //Saves the current instance of QuestionsTree, in standard format, that the
    //computer is using given a PrintStream.
    public void write(PrintStream output){
        this.write(this.initialQuestion, output);        
    }
    
    //Saves the current state of the game given a QuestionNode and a 
    //PrintStream.
    private void write(QuestionNode current, PrintStream output){                      
        if(current.yesLeft == null && current.noRight == null){
            output.println("A:");
            output.println(current.statement);   
        
        }else{
            output.println("Q:");
            output.println(current.statement);
            this.write(current.yesLeft, output);
            this.write(current.noRight, output);
        } 
     }
    
    //The computer asks the user a series of yes/no questions before eventually
    //guessing an object, if the computer guesses the object correctly then
    //the game is over. Otherwise, prompts the user for information about their
    //object and updates the current QuestionTree.
    public void askQuestions(){
        this.initialQuestion = askQuestions(this.initialQuestion);
    }
    
    //Given a QuestionNode assists in asking the user questions and updating
    //the QuestionTree and returns a QuestionNode.
    private QuestionNode askQuestions(QuestionNode currentQuestion){
        if(currentQuestion.yesLeft == null && currentQuestion.noRight == null){
            System.out.print("Would your object happen to be ");
            if(this.yesTo(currentQuestion.statement +"?")){
                System.out.println("Great, I got it right!");
            }else{
                currentQuestion = this.gatherNewInfo(currentQuestion);         
            }
        }else{
            if(this.yesTo(currentQuestion.statement)){
                currentQuestion.yesLeft = this.askQuestions
                                                     (currentQuestion.yesLeft);
            }else{
                currentQuestion.noRight = this.askQuestions
                                                     (currentQuestion.noRight);
            }
        }
        return currentQuestion;
    }
    
    
    //Given a QuestionNode gathers information about a new object in order to
    //update the current QuestionTree and returns a QuestionNode.
    private QuestionNode gatherNewInfo(QuestionNode current){
        System.out.print("What is the name of your object? ");
        QuestionNode newObject = new QuestionNode
                                        (console.nextLine().trim());
        System.out.println("Please give me a yes/no question that");
        System.out.println("distinguishes between your object");
        System.out.print("and mine--> ");
        String newQuestion = console.nextLine().trim();
        if(this.yesTo("And what is the answer for your object?")){
            return new QuestionNode(newQuestion, newObject, current);
        }
        return new QuestionNode(newQuestion, current, newObject);  
    }
    
    // post: asks the user a question, forcing an answer of "y" or "n";
    //       returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }
}

//Trevor Nichols
//QuestionNode
//Section: AM; William Ceriale

//This class represents a single QuestionNode to use with QuestionTree
public class QuestionNode {
    public final String statement;
    public QuestionNode yesLeft;
    public QuestionNode noRight;
    
    //constructs a new QuestionNode given a String.
    public QuestionNode(String newStatement){
        this(newStatement, null, null);
    }
    
    //Constructs a new QuestionNode given a string and 2 other
    //QuestionNodes.
    public QuestionNode(String newQuestion, QuestionNode left,
                                            QuestionNode right){
        this.statement = newQuestion;
        this.yesLeft = left;
        this.noRight = right;
    }

}

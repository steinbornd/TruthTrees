package queue;

public class TruthTreeNode {
	
	private String pcSentence;
	private TruthTreeNode leftChild;
	private TruthTreeNode rightChild;
	private TruthTreeNode parent;
	private boolean checked;
	private boolean closed;

//a constructor
	public TruthTreeNode(String pcSentence) {
		this.pcSentence = pcSentence;
		leftChild = null;
		rightChild = null;
		parent = null;
		checked = false;
		closed = false;
	}
//another constructor - not needed for this program
	public TruthTreeNode(String pcSentence,TruthTreeNode leftChild, TruthTreeNode rightChild, TruthTreeNode parent, boolean checked, boolean closed) {
		this.pcSentence = pcSentence;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.parent = parent;
		this.checked = checked;
		this.closed = closed;
	}
//get the left component of a conjunction, disjunction, conditional or biconditional
	public String getLeftComponent(String expression) {
	
		int index = 1;
		int counter = 1;
		do {
			switch(expression.charAt(index)) {
				case 'p':
				case 'q':
				case 'r':
				case 's':
				case 't':
					counter--;
					index++;
					break;
				case '-':
					index++;
					break;
				case 'v':
				case '&':
				case '>':
				case '=':
					counter++;
					index++;
					break;
			}
		} while (counter != 0 && index < expression.length());
			return expression.substring(1,index);
	}
//get the left component of a conjunction, disjunction, conditional or biconditional
	public String getRightComponent(String expression) {
	
		int index = 1;
		int counter = 1;
		do {
			
			switch(expression.charAt(index)) {
				case 'p':
				case 'q':
				case 'r':
				case 's':
				case 't':
					counter--;
					index++;
					break;
				case '-':
					index++;
					break;
				case 'v':
				case '&':
				case '>':
				case '=':
					counter++;
					index++;
					break;
			}
		} while (counter > 0 && index < expression.length());
			return expression.substring(index);
	}
//a setter method for the "cargo"
	public void setPcSentence(String pcSentence) {
		this.pcSentence = pcSentence;
	}
//a getter method
	public String getPcSentence() {
		return pcSentence;
	}
//You need to supply the rest of the setters and getters
	public void setLeftChild(TruthTreeNode leftChild){
		this.leftChild = leftChild;
	}
	public void setRightChild(TruthTreeNode rightChild){
		this.rightChild = rightChild;
	}
	public TruthTreeNode getLeftChild(){
		return leftChild;
	}
	public TruthTreeNode getRightChild(){
		return rightChild;
	}
	public void setParent(TruthTreeNode parent){
		this.parent = parent;
	}
	public TruthTreeNode getParent(){
		return parent;
	}
	public void setChecked(boolean checked){
		this.checked = checked;
	}
	public void setClosed(boolean closed){
		this.closed = closed;
	}
	public boolean getClosed(){
		return closed;
	}
	public boolean getChecked(){
		return checked;
	}
//A method to determine whether the sentence is a disjunction
//You need to supply similar methods for the other 8 kinds of sentences
	public boolean isDisjunction(String pcSentence) {
		if (pcSentence.charAt(0) == 'v')
			return true;
		return false;
	}
	public boolean isConjunction(String pcSentence){
	     if(pcSentence.charAt(0) == '&'){
	          return true;
	     }
	     return false;
	}
	//FIX ALL BELOW THIS
	public boolean isBiconditional(String pcSentence){
	     if(pcSentence.charAt(0) == '='){
	          return true;
	     }
	     return false;
	}
	
	public boolean isConditional(String pcSentence){
	     if(pcSentence.charAt(0) == '>'){
	          return true;
	     }
	     return false;
	}
	public boolean isNegatedDisjunction(String pcSentence){
		return false;
	}
	public boolean isNegatedConjunction(String pcSentence){
		if((pcSentence.charAt(0)== '-') && (pcSentence.charAt(1)== '&')){
			return true;
		}
		return false;
	}
	public boolean isDoubleNegation(String pcSentence){
		if((pcSentence.charAt(0)== '-') && (pcSentence.charAt(1)== '-')){
			return true;
		}
		return false;
	}
	public boolean isNegatedBiconditional(String pcSentence){
		if((pcSentence.charAt(0)== '-') && (pcSentence.charAt(1)== '=')){
			return true;
		}
		return false;
	}
	public boolean isNegatedConditional(String pcSentence){
		if((pcSentence.charAt(0)== '-') && (pcSentence.charAt(1)== '>')){
			return true;
		}
		return false;
	}
	
//A method for attaching new leaves
	public void attach(String pcSentence) {
		
		//if the sentence is a disjunction, attach like this
		if (this.isDisjunction(pcSentence) ) {
			TruthTreeNode left = new TruthTreeNode(this.getLeftComponent(pcSentence));
			this.setLeftChild(left);
			left.setParent(this);
			//if it's atomic or the negation of an atomic sentence, check it
			if (left.getPcSentence().length() <= 2)
				left.setChecked(true);
			closeBranch(left);
			TruthTreeNode right = new TruthTreeNode(this.getRightComponent(pcSentence));
			this.setRightChild(right);
			right.setParent(this);
			//if it's atomic or the negation of an atomic sentence, check it
			if (right.getPcSentence().length() <= 2)
				right.setChecked(true);
			closeBranch(right);
		}
		
		//if the sentence is a conjunction attach like this
		if (this.isConjunction(pcSentence)) {
			TruthTreeNode left = new TruthTreeNode(this.getLeftComponent(pcSentence));
			this.setLeftChild(left);
			left.setParent(this);
			//if it's atomic or the negation of an atomic sentence, check it
			if (left.getPcSentence().length() <= 2)
				left.setChecked(true);
			//have to check for closure here
			boolean isClosed = closeBranch(left);
			
			TruthTreeNode leftleft = new TruthTreeNode(this.getRightComponent(pcSentence));
			left.setLeftChild(leftleft);
			leftleft.setParent(left);
			//if it's atomic or the negation of an atomic sentence, check it
			if (leftleft.getPcSentence().length() <= 2)
				leftleft.setChecked(true);
			//if the branch was closed because of the left conjunct, close the branch here
			if (isClosed) {
				leftleft.setClosed(true);
			}
			else {
				closeBranch(leftleft);
			}
		}
		//if it is conditional, check and split into 2 branches with 
		// -left on one branch and right on the other
		if (this.isConditional(pcSentence)){
			TruthTreeNode negatedLeft = new TruthTreeNode("-" + this.getLeftComponent(pcSentence));
			this.setLeftChild(negatedLeft);
			negatedLeft.setParent(this);
			if (negatedLeft.getPcSentence().length() <= 2)
				negatedLeft.setChecked(true);
			closeBranch(negatedLeft);
			
			TruthTreeNode right = new TruthTreeNode(this.getRightComponent(pcSentence));
			this.setRightChild(right);
			right.setParent(this);
			//if it's atomic or the negation of an atomic sentence, check it
			if (right.getPcSentence().length() <= 2)
				right.setChecked(true);
			closeBranch(right);
		}
		// if biconditional then check and split into 2 branches
		// left and right on one side and negative left and negative right on the other
		if (this.isBiconditional(pcSentence)){
			TruthTreeNode left = new TruthTreeNode(this.getLeftComponent(pcSentence));
			this.setLeftChild(left);
			left.setParent(this);
			if (left.getPcSentence().length() <= 2)
				left.setChecked(true);
			//have to check for closure here
			closeBranch(left);
			boolean isClosed = left.getClosed();
			
			TruthTreeNode leftleft = new TruthTreeNode(this.getRightComponent(pcSentence));
			left.setLeftChild(leftleft);
			leftleft.setParent(left);
			if (leftleft.getPcSentence().length() <= 2)
				leftleft.setChecked(true);
			
			TruthTreeNode right = new TruthTreeNode("-" + this.getLeftComponent(pcSentence));
			this.setRightChild(right);
			right.setParent(this);
			if (right.getPcSentence().length() <= 2)
				right.setChecked(true);
			//have to check for closure here
			closeBranch(right);
			boolean Closed = right.getClosed();
			
			TruthTreeNode rightright = new TruthTreeNode("-" + this.getRightComponent(pcSentence));
			right.setRightChild(rightright);
			rightright.setParent(right);
			if(rightright.getPcSentence().length() <=2)
				rightright.setChecked(true);
			
			//if the branch was closed because of the left conjunct, close the branch here
			if (isClosed) {
				leftleft.setClosed(true);
			}
			else {
				closeBranch(leftleft);
			}						
			//if the branch was closed because of the left conjunct, close the branch here
			if (Closed) {
				rightright.setClosed(true);
			}
			else {
				closeBranch(rightright);
			}
		}
		//if the sentence is a negated disjunction, attach like this
		// check and put negative of both left and right on b
		if (this.isNegatedDisjunction(pcSentence)){
			String disjunction = pcSentence.substring(1);
			TruthTreeNode negatedLeft = new TruthTreeNode("-" + this.getLeftComponent(disjunction));
			this.setLeftChild(negatedLeft);
			negatedLeft.setParent(this);
			if (negatedLeft.getPcSentence().length() <= 2)
				negatedLeft.setChecked(true);
			closeBranch(negatedLeft);
			boolean isClosed = negatedLeft.getClosed();
			 
			TruthTreeNode negatedRight = new TruthTreeNode("-" + this.getRightComponent(disjunction));
			negatedLeft.setLeftChild(negatedRight);
			negatedRight.setParent(negatedLeft);
			
			//if it's atomic or negation still needed
			if (negatedRight.getPcSentence().length() <= 2)
				negatedRight.setChecked(true);
			//if the branch was closed because of the left conjunct, close the branch here
			if (isClosed) {
				negatedRight.setClosed(true);
			}
			else {
				closeBranch(negatedRight);
			}
		}
		
		//check and split b into 2 branches putting negative of one on one branch and negative of the other on the other branch
		//if the sentence is a negated conjunction, attach like this
		// take off the negation
		// check and split into two branches putting negative left on one side
		// negative right on the other
		if (this.isNegatedConjunction(pcSentence)){
			String conjunction = pcSentence.substring(1);
			TruthTreeNode negatedLeft = new TruthTreeNode("-" + this.getLeftComponent(conjunction));
			this.setLeftChild(negatedLeft);
			negatedLeft.setParent(this);
			//if it's atomic or the negation of an atomic sentence, check it
			if (negatedLeft.getPcSentence().length() <= 2)
				negatedLeft.setChecked(true);
			closeBranch(negatedLeft);
			 
			TruthTreeNode negatedRight = new TruthTreeNode("-" + this.getRightComponent(conjunction));
			this.setRightChild(negatedRight);
			negatedRight.setParent(this);
			//if it's atomic or the negation of an atomic sentence, check it
			if (negatedRight.getPcSentence().length() <=2)
				negatedRight.setChecked(true);
			closeBranch(negatedRight);
		}
		//remove the negative
		//split into two branches
		// put left and negative right on the left
		//put negative left and right on the right
		if (this.isNegatedBiconditional(pcSentence)){
			String biconditional = pcSentence.substring(1);
			TruthTreeNode left = new TruthTreeNode(this.getLeftComponent(biconditional));
			this.setLeftChild(left);
			left.setParent(this);
			if (left.getPcSentence().length() <= 2)
				left.setChecked(true);
			closeBranch(left);
			boolean isClosed = left.getClosed();
			
			TruthTreeNode negatedRight = new TruthTreeNode("-" + this.getRightComponent(biconditional));
			left.setLeftChild(negatedRight);
			negatedRight.setParent(left);
			if (negatedRight.getPcSentence().length() <= 2)
				negatedRight.setChecked(true);
			
			TruthTreeNode negatedLeft = new TruthTreeNode("-" + this.getLeftComponent(biconditional));
			this.setRightChild(negatedLeft);
			negatedLeft.setParent(this);
			if (negatedLeft.getPcSentence().length() <= 2)
				negatedLeft.setChecked(true);
			closeBranch(negatedLeft);
			boolean Closed = negatedLeft.getClosed();
			
			TruthTreeNode right = new TruthTreeNode(this.getRightComponent(biconditional));
			negatedLeft.setLeftChild(right);
			right.setParent(negatedLeft);
			if (right.getPcSentence().length() <= 2)
				right.setChecked(true);
			//if the branch was closed because of the left conjunct, close the branch here
			if (isClosed) {
				negatedRight.setClosed(true);
			}
			else {
				closeBranch(negatedRight);
			}
			//if the branch was closed because of the left conjunct, close the branch here
			if (Closed) {
				right.setClosed(true);
			}
			else {
				closeBranch(right);
			}
		}
		//take off both negatives and put it on the right
		if (this.isDoubleNegation(pcSentence)){
			TruthTreeNode left = new TruthTreeNode(pcSentence.substring(2));
			this.setLeftChild(left);
			left.setParent(this);
			if (left.getPcSentence().length() <= 2)
				left.setChecked(true);
			closeBranch(left);
		}
		if (this.isNegatedConditional(pcSentence)){
			String conditional = pcSentence.substring(1);
			TruthTreeNode left = new TruthTreeNode(this.getLeftComponent(conditional));
			this.setLeftChild(left);
			left.setParent(this);
			if (left.getPcSentence().length() <= 2)
				left.setChecked(true);
			closeBranch(left);
			boolean isClosed = left.getClosed();
			
			
			TruthTreeNode negatedRight = new TruthTreeNode("-" + this.getRightComponent(conditional));
			this.setRightChild(negatedRight);
			negatedRight.setParent(left);
			//if it's atomic or negation still needed
			if (negatedRight.getPcSentence().length() <= 2)
				negatedRight.setChecked(true);
			//if the branch was closed because of the left conjunct, close the branch here
			if (isClosed) {
				negatedRight.setClosed(true);
			}
			else {
				closeBranch(negatedRight);
			}
		}		
	}
	
//A method that closes a branch that needs to be closed
//returns true if the branch was closed
	public boolean closeBranch(TruthTreeNode leaf) {
		TruthTreeNode temp = leaf;
			while (temp.getParent() != null) {
				temp = temp.getParent();
				if (isContradiction(leaf.getPcSentence(), temp.getPcSentence())) {
					leaf.setClosed(true);
					return true;
				}
			}
		return false;
	}	
//A method that returns true if sentence1 and sentence2 constitute a contradiction
//Implement it!!
	public boolean isContradiction(String sentence1, String sentence2) {
		String substring2 = sentence2.substring(1);
		String substring1 = sentence1.substring(1);
		if (sentence1.charAt(0)=='-'){
			if (substring1.compareTo(sentence2) == 0){
				return true;
			}
		}
		if (sentence2.charAt(0)=='-'){
			if (substring2.compareTo(sentence1) == 0){
				return true;
			}
		}
		return false;
	}
}

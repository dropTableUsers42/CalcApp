package calcApp.model;

public class Paranthesis extends CalculatableObject {
	
	public Paranthesis(int paranthesisLevel, ParanthesisType pType){
		this.paranthesisLevel = paranthesisLevel;
		this.pType = pType;
	}
	
	public Paranthesis(ParanthesisType pType){
		this.pType = pType;
		this.paranthesisLevel = -1;
	}
	
	private CalculatableObjectType type = CalculatableObjectType.Paranthesis;
	
	private int paranthesisLevel;
	
	public static enum ParanthesisType{
		Opening("("),
		Closing(")");
		
		private String string;
		
		ParanthesisType(String string){
			this.string = string;
		}
		
		public String getParanthesisString(){
			return this.string;
		}
	}
	
	private ParanthesisType pType;
	
	@Override
	public String toString(){
		return pType.getParanthesisString();
	}
	
	@Override
	public CalculatableObjectType getType(){
		return type;
	}
	
	@Override
	public String getTypeString(){
		return type.getTypeString();
	}
	
	@Override
	public Paranthesis clone(){
		Paranthesis copy = new Paranthesis(this.paranthesisLevel, this.pType);
		return copy;
	}
	
	public void setParanthesisLevel(int level){
		this.paranthesisLevel = level;
	}
	
	/**
	 * @return ParanthesisLevel if set, -1 otherwise
	 */
	public int getParanthesisLevel(){
		return paranthesisLevel;
	}
	
	public ParanthesisType getParanthesisType(){
		return pType;
	}
}

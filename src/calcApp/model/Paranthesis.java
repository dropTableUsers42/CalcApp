package calcApp.model;

public class Paranthesis extends CalculatableObject {
	
	private CalculatableObjectType type = CalculatableObjectType.Paranthesis;
	
	private int paranthesisLevel;
	
	public static enum ParanthesisType{
		Opening,
		Closing
		//TODO: Add method to get string if needed
	}
	
	private ParanthesisType pType;
	
	@Override
	public CalculatableObjectType getType(){
		return type;
	}
	
	@Override
	public String getTypeString(){
		return type.getTypeString();
	}
	
	public int getParanthesisLevel(){
		return paranthesisLevel;
	}
	
	public ParanthesisType getParanthesisType(){
		return pType;
	}
}

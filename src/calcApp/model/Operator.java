package calcApp.model;

public class Operator extends CalculatableObject {
	
	private CalculatableObjectType type = CalculatableObjectType.Operator;
	
	public static enum OperatorType{
		Plus,
		Minus,
		Multiply,
		Divide
	}
	
	private OperatorType opType;
	
	@Override
	public CalculatableObjectType getType() {
		return type;
	}

	@Override
	public String getTypeString(){
		return type.getTypeString();
	}
	
	public OperatorType getOperatorType(){
		return opType;
	}
	
	

}

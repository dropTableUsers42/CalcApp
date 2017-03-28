package calcApp.model;

public class Operator extends CalculatableObject {
	
	public Operator(OperatorType opType){
		this.opType = opType;
	}
	
	private CalculatableObjectType type = CalculatableObjectType.Operator;
	
	public static enum OperatorType{
		Plus("+"),
		Minus("-"),
		Multiply("*"),
		Divide("/");
		
		private String string;
		
		OperatorType(String string){
			this.string = string;
		}
		
		public String getOperatorString(){
			return this.string;
		}
	}
	
	private OperatorType opType;
	
	@Override
	public String toString(){
		return opType.getOperatorString();
	}
	
	@Override
	public CalculatableObjectType getType() {
		return type;
	}

	@Override
	public String getTypeString(){
		return type.getTypeString();
	}
	
	@Override
	public Operator clone(){
		Operator copy = new Operator(this.opType);
		return copy;
	}
	
	public OperatorType getOperatorType(){
		return opType;
	}	

}

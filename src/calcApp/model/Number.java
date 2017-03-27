package calcApp.model;

public class Number extends CalculatableObject {
	
	public Number(float value){
		this.value=value;
	}

	private CalculatableObjectType type = CalculatableObjectType.Number;
	
	@Override
	public String toString(){
		return Float.toString(this.value);
	}
	
	@Override
	public CalculatableObjectType getType(){
		return type;
	}
	
	@Override
	public String getTypeString(){
		return type.getTypeString();
	}
	
	private float value;
	
	public float getValue(){
		return value;
	}
	
}

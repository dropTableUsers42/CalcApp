package calcApp.model;

public class Number extends CalculatableObject {
	
	public Number(float value){
		this.value=value;
	}

	private CalculatableObjectType type = CalculatableObjectType.Number;
	
	@Override
	public String toString(){
		String temp=Float.toString(this.value);
		if(this.value<0){
			temp="("+temp+")";
		}
		
		return temp;
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
	
	public void setValue(float value){
		this.value = value;
	}
	
	@Override
	public Number clone(){
		Number copy = new Number(this.value);
		return copy;
	}
	
}

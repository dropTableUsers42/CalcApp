package calcApp.model;

/**
 * Model Class for CalculatableObject (Paranthesis,Operators,Numbers etc)
 * 
 * @author Rwitaban Goswami
 * 
 */
public abstract class CalculatableObject {
	
	/**
	 * Enum enlisting type of CalculatableObject
	 * 
	 * @author Rwitaban Goswami
	 *
	 */
	public static enum CalculatableObjectType {
		Paranthesis("PARA"),
		Operator("OPER"),
		Number("NUM");
		//TODO: Add support for functions
		
		private String typeString;
		
		CalculatableObjectType(String string){
			this.typeString=string;
		}
		
		public String getTypeString(){
			return this.typeString;
		}
	}
	
	/**
	 * @return the enum type of the Calculatable Object
	 */
	public abstract CalculatableObjectType getType();
	
	/**
	 * @return the string of the type of CalculatableObjectType
	 */
	public abstract String getTypeString();
	
	@Override
	public abstract String toString();
}

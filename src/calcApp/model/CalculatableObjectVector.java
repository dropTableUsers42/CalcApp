package calcApp.model;

import java.util.Vector;
import calcApp.model.CalculatableObject.CalculatableObjectType;
import calcApp.model.Paranthesis.ParanthesisType;


/**
 * Model Class for an array storing the CalculatableObjects to be worked upon
 * 
 * @author Rwitaban Goswami
 */
public class CalculatableObjectVector {
	
	public CalculatableObjectVector(){

	}
	
	@Override
	public String toString(){
		String temp = new String("");
		
		for(CalculatableObject cTemp: this.objV){
			temp+=cTemp.toString();
		}
		
		return temp;
	}
	
	private Vector<CalculatableObject> objV = new Vector<CalculatableObject>();
	
	public void add(int index, CalculatableObject e){
		objV.add(index, e);
	}
	
	public void add(CalculatableObject e){
		objV.add(e);
	}
	
	/**
	 * Returns result as float
	 * @param num1
	 * @param op Operator
	 * @param num2
	 * @return result of operation
	 */
	private static float calculateFloatValue(Number num1, Operator op, Number num2){
		switch(op.getOperatorType()){
		case Plus: return num1.getValue()+num2.getValue();
		case Minus: return num1.getValue()-num2.getValue();
		case Divide: return num1.getValue()/num2.getValue();
		case Multiply: return num1.getValue()*num2.getValue();
		default: return 0;
		}
	}
	
	/**
	 * Recursive method, returns the ObjectVector passed, after performing the operations
	 * @param objV ObjectVector to be operated on
	 * @param paranthesisLevel Maximum paranthesisLevel in objV
	 * @return the ObjectVector passed, with the only element as the result Number
	 */
	public static CalculatableObjectVector calculateValue(CalculatableObjectVector cObjV, 
																			int paranthesisLevel){
		
		Vector<CalculatableObject> objV = cObjV.objV;
		
		if(objV.size()==1 || paranthesisLevel==0){
			return cObjV;
		}
		
		for(int i=0; i<objV.size(); i++){
			CalculatableObject temp = objV.elementAt(i);
			if(temp.getType()==CalculatableObjectType.Paranthesis
					&& ((Paranthesis)temp).getParanthesisType()==ParanthesisType.Opening
					&& ((Paranthesis)temp).getParanthesisLevel()==paranthesisLevel){
				
				//System.out.print("Object Found");
				//If (num) then resolve as num
				if(objV.elementAt(i+2).getType()==CalculatableObjectType.Paranthesis
						&&((Paranthesis)temp).getParanthesisType()==ParanthesisType.Closing){
					
					objV.remove(i);
					objV.remove(i+1);
				}
				
				if(objV.elementAt(i+2).getType()==CalculatableObjectType.Operator){
					
					Number num1 = (Number)objV.elementAt(i+1);
					Operator op = (Operator)objV.elementAt(i+2);
					Number num2 = (Number)objV.elementAt(i+3);
					
					float ftemp = calculateFloatValue(num1,op,num2); //(num1 + num2)
					objV.set(i, new Number(ftemp)); //num num1 + num2)
					objV.remove(i+1); //num + num2)
					objV.remove(i+1); //num num2)
					objV.remove(i+1); //num )
					objV.remove(i+1); //num
				}
				
			}
			
		}
		System.out.print(objV.size());
		return calculateValue(cObjV,paranthesisLevel-1);
	}
	
	//TODO:Remove this
	public Vector<CalculatableObject> getObjectVector(){
		return this.objV;
	}

}

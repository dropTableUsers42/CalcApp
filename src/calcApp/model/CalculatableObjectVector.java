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
	
	private Vector<CalculatableObject> objV;
	
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
	public static Vector<CalculatableObject> calculateValue(Vector<CalculatableObject> objV, 
																			int paranthesisLevel){
		
		if(objV.size()==1){
			return objV;
		}
		
		for(int i=0; i<objV.size(); i++){
			CalculatableObject temp = objV.elementAt(i);
			if(temp.getType()==CalculatableObjectType.Paranthesis
					&& ((Paranthesis)temp).getParanthesisType()==ParanthesisType.Opening
					&& ((Paranthesis)temp).getParanthesisLevel()==paranthesisLevel){
				
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
		
		return calculateValue(objV,paranthesisLevel-1);
	}

}

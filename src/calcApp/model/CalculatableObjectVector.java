package calcApp.model;

import java.lang.Math;
import java.util.Vector;

import calcApp.model.CalculatableObject.CalculatableObjectType;
import calcApp.model.Operator.OperatorType;
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
	 * Inserts paranthesis around the operator if the element at index is operator
	 * @param index 
	 */
	public void insertParaAroundOperator(int index){
		
		this.setParaLevels();
		
		if(!(this.objV.elementAt(index).getType()==CalculatableObjectType.Operator)){
			return;
		}
		
		//Insert para before
		if(this.objV.elementAt(index-1).getType()==CalculatableObjectType.Number){
			objV.insertElementAt(new Paranthesis(ParanthesisType.Opening), index-1);
		} else if(this.objV.elementAt(index-1).getType()==CalculatableObjectType.Paranthesis){
			int paraLevel = ((Paranthesis)this.objV.elementAt(index-1)).getParanthesisLevel();
			for(int i=index-2; i>=0; i--){
				if(this.objV.elementAt(i).getType()==CalculatableObjectType.Paranthesis
						&& ((Paranthesis)this.objV.elementAt(i)).getParanthesisLevel()==paraLevel
						&& ((Paranthesis)this.objV.elementAt(i)).getParanthesisType()
																	==ParanthesisType.Opening){
					objV.insertElementAt(new Paranthesis(ParanthesisType.Opening), i);
					break;
				}
			}
		}
		
		index++;
		
		//Insert para after
		if(this.objV.elementAt(index+1).getType()==CalculatableObjectType.Number){
			objV.insertElementAt(new Paranthesis(ParanthesisType.Closing), index+2);
		} else if(this.objV.elementAt(index+1).getType()==CalculatableObjectType.Paranthesis){
			int paraLevel = ((Paranthesis)this.objV.elementAt(index+1)).getParanthesisLevel();
			for(int i=index+2; i<objV.size(); i++){
				if(this.objV.elementAt(i).getType()==CalculatableObjectType.Paranthesis
						&& ((Paranthesis)this.objV.elementAt(i)).getParanthesisLevel()==paraLevel
						&& ((Paranthesis)this.objV.elementAt(i)).getParanthesisType()
																	==ParanthesisType.Closing){
					objV.insertElementAt(new Paranthesis(ParanthesisType.Closing), i+1);
				}
			}
		}
		
		this.setParaLevels();
	}
	
	/**
	 * Inserts paranthesis around operators according to operator precedence
	 */
	public void insertPara(){
		for(int i=0; i<this.objV.size(); i++){
			if(this.objV.elementAt(i).getType()==CalculatableObjectType.Operator
				&& ((Operator)this.objV.elementAt(i)).getOperatorType()==OperatorType.Multiply){
				
				insertParaAroundOperator(i);
				i++;
			}
		}
		for(int i=0; i<this.objV.size(); i++){
			if(this.objV.elementAt(i).getType()==CalculatableObjectType.Operator
				&& ((Operator)this.objV.elementAt(i)).getOperatorType()==OperatorType.Divide){
				
				insertParaAroundOperator(i);
				i++;
			}
		}
		for(int i=0; i<this.objV.size(); i++){
			if(this.objV.elementAt(i).getType()==CalculatableObjectType.Operator
				&& ((Operator)this.objV.elementAt(i)).getOperatorType()==OperatorType.Plus){
					
				insertParaAroundOperator(i);
				i++;
			}
		}
		for(int i=0; i<this.objV.size(); i++){
			if(this.objV.elementAt(i).getType()==CalculatableObjectType.Operator
				&& ((Operator)this.objV.elementAt(i)).getOperatorType()==OperatorType.Minus){
				
				insertParaAroundOperator(i);
				i++;
			}
		}
	}
	
	/**
	 * Recursive method, returns the ObjectVector passed, after performing the operations
	 * @param objV ObjectVector to be operated on
	 * @param paranthesisLevel Maximum paranthesisLevel in objV
	 * @return the ObjectVector passed, with the only element as the result Number
	 */
	public static CalculatableObjectVector calculateValue(CalculatableObjectVector cObjV){
		
		cObjV.setParaLevels();
		int paranthesisLevel = cObjV.maxParaLevel();
		Vector<CalculatableObject> objV = cObjV.objV;
		
		if(objV.size()==1 || paranthesisLevel==0 || !isValid(cObjV)){
			return cObjV;
		}
		
		for(int i=0; i<objV.size(); i++){
			CalculatableObject temp = objV.elementAt(i);
			if(temp.getType()==CalculatableObjectType.Paranthesis
					&& ((Paranthesis)temp).getParanthesisType()==ParanthesisType.Opening
					&& ((Paranthesis)temp).getParanthesisLevel()==paranthesisLevel){
				
				//System.out.print("Object Found");
				//If (num) then resolve as num
				if(objV.elementAt(i+2).getType()==CalculatableObjectType.Paranthesis){
					
					objV.remove(i);
					objV.remove(i+1);
				} else if(objV.elementAt(i+2).getType()==CalculatableObjectType.Operator){
					
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
		return calculateValue(cObjV);
	}
	
	public void calculateFinalValueOfVector(){
		insertPara();
		calculateValue(this);
	}
	
	/**
	 * Sets the paranthesisLevel of all parenthesis in the vector
	 */
	public void setParaLevels(){
		int level=0;
		for(CalculatableObject temp: this.objV){
			if(temp.getType()==CalculatableObjectType.Paranthesis
					&& ((Paranthesis)temp).getParanthesisType()==ParanthesisType.Opening){
				level++;
				((Paranthesis)temp).setParanthesisLevel(level);
			}
			if(temp.getType()==CalculatableObjectType.Paranthesis
					&& ((Paranthesis)temp).getParanthesisType()==ParanthesisType.Closing){
				((Paranthesis)temp).setParanthesisLevel(level);
				level--;
			}
		}
	}
	
	/**
	 * @return The maximum paranthesisLevel in the Vector
	 */
	public int maxParaLevel(){
		int maxLevel=0;
		for(CalculatableObject temp: this.objV){
			if(temp.getType()==CalculatableObjectType.Paranthesis){
				maxLevel=Math.max(maxLevel, ((Paranthesis)temp).getParanthesisLevel());
			}
		}
		return maxLevel;
	}
	
	public static boolean isValid(CalculatableObjectVector cObjV){
		
		Vector<CalculatableObject> objV = cObjV.objV;
		
		int count=0;
		for(int i=0; i<objV.size(); i++){
			if(objV.elementAt(i).getType()==CalculatableObjectType.Paranthesis
					&& ((Paranthesis)objV.elementAt(i)).getParanthesisType()==ParanthesisType.Opening){
				count++;
			}
			if(objV.elementAt(i).getType()==CalculatableObjectType.Paranthesis
					&& ((Paranthesis)objV.elementAt(i)).getParanthesisType()==ParanthesisType.Closing){
				count--;
			}
		}
		
		return count==0;
	}

}

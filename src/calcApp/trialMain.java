package calcApp;

import calcApp.model.CalculatableObject;
import calcApp.model.CalculatableObjectVector;
import calcApp.model.Paranthesis;
import calcApp.model.Paranthesis.ParanthesisType;
import calcApp.model.Number;
import calcApp.model.Operator;
import calcApp.model.Operator.OperatorType;

public class trialMain {

	public static void main(String[] args) {
		CalculatableObjectVector objV = new CalculatableObjectVector();
		
		objV.add(new Paranthesis(1,ParanthesisType.Opening));
		objV.add(new Number(2));
		objV.add(new Operator(OperatorType.Multiply));
		objV.add(new Number(3));
		objV.add(new Paranthesis(1,ParanthesisType.Closing));
		
		System.out.println(objV);
		
		CalculatableObjectVector.calculateValue(objV, 1);
		
		System.out.print(objV.getObjectVector().size());
		System.out.print("\n"+((Number)objV.getObjectVector().elementAt(0)).getValue());
	}

}

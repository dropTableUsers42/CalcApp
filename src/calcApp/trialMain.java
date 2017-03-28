package calcApp;

import calcApp.model.CalculatableObjectVector;
import calcApp.model.Number;
import calcApp.model.Operator;
import calcApp.model.Operator.OperatorType;
import calcApp.model.Paranthesis;
import calcApp.model.Paranthesis.ParanthesisType;

public class trialMain {

	public static void main(String[] args) {
		CalculatableObjectVector objV = new CalculatableObjectVector();
		
		//objV.add(new Paranthesis(ParanthesisType.Opening));
		objV.add(new Number(8));
		objV.add(new Operator(OperatorType.Plus));
		objV.add(new Number(3));
		objV.add(new Operator(OperatorType.Multiply));
		objV.add(new Number(6));
		objV.add(new Operator(OperatorType.Plus));
		objV.add(new Paranthesis(ParanthesisType.Opening));
		objV.add(new Number(5));
		objV.add(new Paranthesis(ParanthesisType.Closing));
		//objV.add(new Paranthesis(ParanthesisType.Closing));
		
		System.out.println(objV);
		
		objV.calculateFinalValueOfVector();
		
		System.out.print(objV);
		
	}

}

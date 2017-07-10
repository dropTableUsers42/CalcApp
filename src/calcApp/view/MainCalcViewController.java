package calcApp.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import calcApp.MainApp;
import calcApp.model.CalculatableObject;
import calcApp.model.CalculatableObject.CalculatableObjectType;
import calcApp.model.CalculatableObjectVector;
import calcApp.model.Number;
import calcApp.model.Operator;
import calcApp.model.Operator.OperatorType;
import calcApp.model.Paranthesis;
import calcApp.model.Paranthesis.ParanthesisType;

public class MainCalcViewController {

	@FXML
	private Label result;
	
	@FXML
	private TextField stringEntered;
	
	@FXML
	private Button button0;
	@FXML
	private Button button1;
	@FXML
	private Button button2;
	@FXML
	private Button button3;
	@FXML
	private Button button4;
	@FXML
	private Button button5;
	@FXML
	private Button button6;
	@FXML
	private Button button7;
	@FXML
	private Button button8;
	@FXML
	private Button button9;
	@FXML
	private Button buttonDecimal;
	
	@FXML
	private Button buttonPlus;
	@FXML
	private Button buttonMinus;
	@FXML
	private Button buttonDivide;
	@FXML
	private Button buttonMultiply;
	@FXML
	private Button buttonPlusMinus;
	
	@FXML
	private Button buttonOpenPara;
	@FXML
	private Button buttonClosePara;
	
	@FXML
	private Button buttonEquals;
	
	@FXML
	private Button buttonC;
	@FXML
	private Button buttonCE;
	@FXML
	private Button buttonBack;
	
	@FXML
	private Button buttonNext;
	@FXML
	private Button buttonPrev;
	
	private MainApp mainApp;
	
	private CalculatableObjectVector objV;
	private CalculatableObjectVector finalObjV;
	
	private boolean isMinus;
	
	public MainCalcViewController(){
		isMinus = false;
	}
	
	@FXML
	private void initialize(){
		//result.setText("");
	}
	
	@FXML
	private void handleNumberButtonPressed(ActionEvent event){
		char num = '0';
		
		Button tempButton = (Button)event.getSource();
		num = tempButton.getText().charAt(0);
		
		addNumber(num);
		showResult();
		showStringEntered();
	}
	
	@FXML
	private void handleDecimalButtonPressed(){
		mainApp.setDecimalPosition(1);
	}
	
	@FXML
	private void handleOperatorPressed(ActionEvent event){
		
		Button tempButton = (Button)event.getSource();
		String opPressed = tempButton.getText();
		char ch = opPressed.charAt(0);
		addOperator(ch);
		mainApp.setDecimalPosition(0);
		isMinus=false;
		showStringEntered();
		showResult();
	}
	
	@FXML
	private void handleParanthesisPressed(ActionEvent event){
		Button tempButton = (Button)event.getSource();
		String paraPressed = tempButton.getText();
		char ch = paraPressed.charAt(0);
		addParanthesis(ch);
		mainApp.setDecimalPosition(0);
		isMinus=false;
		showStringEntered();
		showResult();
		
	}
	
	@FXML
	public void handleCButtonPressed(){
		objV.removeAllElements();
		mainApp.setCaretPosition(0);
		mainApp.setDecimalPosition(0);
		isMinus=false;
		showStringEntered();
		showResult();
	}
	
	@FXML
	public void handleCEButtonPressed(){
		if(objV.size()==0){
			handleCButtonPressed();
			return;
		}
		if(mainApp.getCaretPosition()==0){
			return;
		}
		objV.remove(mainApp.getCaretPosition()-1);
		mainApp.setCaretPosition(mainApp.getCaretPosition()-1);
		mainApp.setDecimalPosition(0);
		isMinus=false;
		showStringEntered();
		showResult();
	}
	
	@FXML
	public void handlePlusMinusButtonPressed(){
		this.isMinus = !isMinus;
		if(mainApp.getCaretPosition()==0){
			return;
		}
		if(objV.elementAt(mainApp.getCaretPosition()-1).getType()==CalculatableObjectType.Number){
			float value = ((Number)objV.elementAt(mainApp.getCaretPosition()-1)).getValue();
			value = 0 - value;
			((Number)objV.elementAt(mainApp.getCaretPosition()-1)).setValue(value);;
		}
		showStringEntered();
		showResult();
	}
	
	@FXML
	public void handleEqualsButtonPressed(){
		objV.removeAllElements();
		objV.add(finalObjV.elementAt(0));
		mainApp.setCaretPosition(objV.size());
		mainApp.setDecimalPosition(0);
		isMinus=false;
		showResult();
		showStringEntered();
	}
	
	@FXML
	public void handlePrevButtonPressed(){
		if(mainApp.getCaretPosition()==0){
			return;
		}
		
		mainApp.setCaretPosition(mainApp.getCaretPosition()-1);
		showStringEntered();
		setDecimalPos();
	}
	
	@FXML
	public void handleNextButtonPressed(){
		if(mainApp.getCaretPosition()==objV.size()){
			return;
		}
		
		mainApp.setCaretPosition(mainApp.getCaretPosition()+1);
		showStringEntered();
		setDecimalPos();
	}
	
	private void setDecimalPos(){
		if(mainApp.getCaretPosition()!=0){
			CalculatableObject obj = objV.elementAt(mainApp.getCaretPosition()-1);
			if(obj.getType()==CalculatableObjectType.Number){
				int pos=0;
				for(int i=0; i<obj.toString().length(); i++){
					if(obj.toString().charAt(i)=='.'){
						pos=i;
						break;
					}
				}
				isMinus = ((Number)obj).getValue()<0;
				if(isMinus){
					mainApp.setDecimalPosition(obj.toString().length()-pos-1);
				} else {
					mainApp.setDecimalPosition(obj.toString().length()-pos);
				}
			}
		}
	}
	
	/**
	 * Adds the number passed into the objV vector
	 * @param numToAdd
	 */
	private void addNumber(char numToAdd){
		Number currNumber;
		
		int newPos;
		
		if(mainApp.getCaretPosition()==0){
			currNumber = new Number(0);
			objV.add(0,currNumber);
			newPos=1;
		} else if(mainApp.getCaretPosition()==objV.size()
				&& objV.elementAt(objV.size()-1).getType()==CalculatableObjectType.Number){
			currNumber = (Number)(objV.elementAt(objV.size()-1));
			newPos=objV.size();
		} else if(mainApp.getCaretPosition()==objV.size()) {
			currNumber = new Number(0);
			objV.add(currNumber);
			newPos=objV.size();
		} else if(objV.elementAt(mainApp.getCaretPosition()-1).getType()==CalculatableObjectType.Number){
			currNumber = (Number)(objV.elementAt(mainApp.getCaretPosition()-1));
			newPos = mainApp.getCaretPosition();
		} else {
			currNumber = new Number(0);
			objV.add(mainApp.getCaretPosition(),currNumber);
			newPos = mainApp.getCaretPosition()+1;
		}
		
		float value = ((Number)currNumber).getValue();
		String numString;
		if(value==(long)value){
			numString = String.format("%d", (long)value);
		} else {
			numString=Float.toString(value);
		}
		if(mainApp.getDecimalPosition()==1){
			numString+='.';
			mainApp.setDecimalPosition(mainApp.getDecimalPosition()+1);
		}
		if(numString=="0"){
			numString = String.valueOf(numToAdd);
		} else {
			numString += numToAdd;
		}
		float newNum = Float.parseFloat(numString);
		currNumber.setValue(newNum);
		mainApp.setCaretPosition(newPos);
	}
	
	private void addOperator(char ch){
		Operator op;
		switch(ch){
		case '+': op = new Operator(OperatorType.Plus); break;
		case '-': op = new Operator(OperatorType.Minus); break;
		case '/': op = new Operator(OperatorType.Divide); break;
		case '*': op = new Operator(OperatorType.Multiply); break;
		default: op = new Operator(OperatorType.Plus); break;
		}
		
		if(mainApp.getCaretPosition()==objV.size()){
			objV.add(op);
		} else {
			objV.add(mainApp.getCaretPosition(),op);
		}
		mainApp.setCaretPosition(mainApp.getCaretPosition()+1);
	}
	
	private void addParanthesis(char ch){
		Paranthesis para;
		switch(ch){
		case ')': para = new Paranthesis(ParanthesisType.Closing); break;
		case '(': para = new Paranthesis(ParanthesisType.Opening); break;
		default: para = new Paranthesis(ParanthesisType.Closing); break;
		}
		
		if(mainApp.getCaretPosition()==objV.size()){
			objV.add(para);
		} else {
			objV.add(mainApp.getCaretPosition(),para);
		}
		mainApp.setCaretPosition(mainApp.getCaretPosition()+1);
	}
	
	/**
	 * Show the result of calculation in the result Label
	 */
	private void showResult(){
		calculateResult();
		String s;
		if(!CalculatableObjectVector.isValid(finalObjV)){
			s = "";
		} else if(finalObjV.size()==1 && finalObjV.elementAt(0).getType()==CalculatableObjectType.Number && ((Number)finalObjV.elementAt(0)).getValue()<0){
			s = Float.toString(((Number)finalObjV.elementAt(0)).getValue());
		} else{
			s = finalObjV.toString();
		}
		result.setText(s);
	}
	
	private void calculateResult(){
		finalObjV = objV.clone();
		finalObjV.calculateFinalValueOfVector();
	}
	
	private void showStringEntered(){
		stringEntered.setText(objV.toString());
	}
	
	/**
	 * Is called by MainApp to set reference to itself
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	public void setObjV(CalculatableObjectVector objV){
		this.objV = objV;
	}
}

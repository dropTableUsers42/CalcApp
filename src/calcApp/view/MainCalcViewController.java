package calcApp.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import calcApp.MainApp;
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
		float numToAdd=0;
		try{
			Button tempButton = (Button)event.getSource();
			numToAdd = Float.parseFloat(tempButton.getText());
		} catch(NumberFormatException e){
			e.printStackTrace();
		}
		
		addNumber(numToAdd);
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
		if(objV.size()!=0 &&
				objV.elementAt(objV.size()-1).getType()==CalculatableObjectType.Number){
			mainApp.setCaretPosition(mainApp.getCaretPosition()+1);
		}
		int pos = mainApp.getCaretPosition();
		switch(ch){
		case '+': objV.add(pos,new Operator(OperatorType.Plus)); break;
		case '-': objV.add(pos,new Operator(OperatorType.Minus)); break;
		case '*': objV.add(pos,new Operator(OperatorType.Multiply)); break;
		case '/': objV.add(pos,new Operator(OperatorType.Divide)); break;
		}
		mainApp.setDecimalPosition(0);
		mainApp.setCaretPosition(mainApp.getCaretPosition()+1);
		isMinus=false;
		showStringEntered();
	}
	
	@FXML
	private void handleParanthesisPressed(ActionEvent event){
		Button tempButton = (Button)event.getSource();
		String paraPressed = tempButton.getText();
		char ch = paraPressed.charAt(0);
		if(objV.size()!=0 &&
				objV.elementAt(objV.size()-1).getType()==CalculatableObjectType.Number){
			mainApp.setCaretPosition(mainApp.getCaretPosition()+1);
		}
		int pos = mainApp.getCaretPosition();
		switch(ch){
		case '(': objV.add(pos,new Paranthesis(ParanthesisType.Opening)); break;
		case ')': objV.add(pos,new Paranthesis(ParanthesisType.Closing)); break;
		}
		mainApp.setDecimalPosition(0);
		mainApp.setCaretPosition(mainApp.getCaretPosition()+1);
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
		boolean isNumber = objV.elementAt(objV.size()-1).getType() == CalculatableObjectType.Number;
		objV.remove(objV.size()-1);
		if(isNumber){
			mainApp.setCaretPosition(objV.size());
		} else {
			mainApp.setCaretPosition(objV.size()-1);
		}
		mainApp.setDecimalPosition(0);
		isMinus=false;
		showStringEntered();
		showResult();
	}
	
	@FXML
	public void handlePlusMinusButtonPressed(){
		this.isMinus = !isMinus;
		if(objV.elementAt(mainApp.getCaretPosition()).getType()==CalculatableObjectType.Number){
			float value = ((Number)objV.elementAt(mainApp.getCaretPosition())).getValue();
			value = 0 - value;
			objV.remove(mainApp.getCaretPosition());
			objV.add(new Number(value));
		}
		showStringEntered();
		showResult();
	}
	
	@FXML
	public void handleEqualsButtonPressed(){
		objV.removeAllElements();
		objV.add(finalObjV.elementAt(0));
		showResult();
		showStringEntered();
		mainApp.setCaretPosition(0);
		mainApp.setDecimalPosition(0);
		isMinus=false;
	}
	
	/**
	 * Adds the number passed into the objV vector
	 * @param numToAdd
	 */
	private void addNumber(float numToAdd){
		Number currNumber;
		if(isMinus){
			numToAdd = 0 - numToAdd;
		}
		
		if(objV.size()!=0 &&
				mainApp.getCaretPosition()<objV.size() &&
				objV.elementAt(mainApp.getCaretPosition()).getType()==CalculatableObjectType.Number){
			currNumber = (Number)objV.elementAt(mainApp.getCaretPosition());
		} else{
			currNumber = new Number(0);
			objV.add(mainApp.getCaretPosition(), currNumber);
			mainApp.setDecimalPosition(0);
		}
		
		float f = currNumber.getValue();
		int decimalPosition = mainApp.getDecimalPosition();
		
		if(decimalPosition==0){
			currNumber.setValue(numToAdd+f*10);
		} else{
			for(int i=0; i<decimalPosition; i++){
				numToAdd/=10;
			}
			currNumber.setValue(numToAdd+f);
			mainApp.setDecimalPosition(decimalPosition+1);
		}
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

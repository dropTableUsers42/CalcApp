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
	
	public MainCalcViewController(){
		
	}
	
	@FXML
	private void initialize(){
		//result.setText("");
	}
	
	@FXML
	private void handleNumberButtonPressed(ActionEvent event){
		result.setText("Hello");
		float numToAdd=0;
		try{
			Button tempButton = (Button)event.getSource();
			numToAdd = Float.parseFloat(tempButton.getText());
		} catch(NumberFormatException e){
			e.printStackTrace();
		}
		
		Number currNumber;
		
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
		showStringEntered();
		showResult();
		
	}
	
	@FXML
	public void handleCButtonPressed(){
		objV.removeAllElements();
		mainApp.setCaretPosition(0);
		mainApp.setDecimalPosition(0);
		showStringEntered();
		showResult();
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

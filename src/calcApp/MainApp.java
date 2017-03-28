package calcApp;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import calcApp.model.CalculatableObjectVector;
import calcApp.view.MainCalcViewController;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private CalculatableObjectVector objV = new CalculatableObjectVector();
	private int caretPosition;
	int decimalPosition;
	
	public MainApp(){
		caretPosition=0;
		decimalPosition=0;
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		this.primaryStage.setTitle("CalcApp");
		
		initRootLayout();
		
		showMainCalculatorView();
	}
	
	public void initRootLayout(){
		try{
			//Load Main Calculator View
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/CalculatorRootLayout.fxml"));
			rootLayout = (BorderPane)loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void showMainCalculatorView(){
		try{
			//Load Main Calculator View
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainCalculatorView.fxml"));
			AnchorPane calcView = (AnchorPane)loader.load();
			
			MainCalcViewController controller = loader.getController();
			
			controller.setMainApp(this);
			controller.setObjV(this.objV);
			
			rootLayout.setCenter(calcView);
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void setCaretPosition(int i){
		caretPosition = i;
	}
	
	public int getCaretPosition(){
		return this.caretPosition;
	}
	
	public CalculatableObjectVector getObjV(){
		return this.objV;
	}
	
	public void setDecimalPosition(int i){
		decimalPosition = i;
	}
	
	public int getDecimalPosition(){
		return decimalPosition;
	}

	public static void main(String[] args) {
		launch(args);
	}
}

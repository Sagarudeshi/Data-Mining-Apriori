import java.io.File;

import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class MyController {
	
	Apriori a= new Apriori();
    @FXML
    public TextArea InputArea;
    

    @FXML
    private Button button;

    @FXML
    private TextField ichoice;

    @FXML
    private Label OutputLabel;

    @FXML
    public TextArea OutputArea;

    @FXML
    private Label confidence;

    @FXML
    private TextField iconfidence;
    
    @FXML
    private TextField isupport;

    @FXML
    private Region region;

    @FXML
    private Label choice;

    @FXML
    private Label support;

    @FXML
    private Label InputLabel;

   
	@FXML
    void onClickButton(ActionEvent event) {
		InputArea.clear();
		OutputArea.clear();
    	String icon = iconfidence.getText();
    	a.minConfidence =Double.parseDouble(icon);
    	String isup = isupport.getText();
    	a.minSupport = Double.parseDouble(isup);
    	String file = ichoice.getText();
    	StringBuilder sb = new StringBuilder(".\\src\\");
    	sb.append(file);
    	String newfile = sb.toString() ;
    	File Database = new File(newfile);
    	Apriori.displayTransaction(Database);
    	for(String i : a.saveInput){
    		InputMessage(i);
    	}
    	
    	Apriori.candidateSet(a.transaction);
    	for(String m : a.saveOutput){
    		OutputMessage(m);
    	}
    }
	
	public void InputMessage(String text){
		
		Platform.runLater(() -> InputArea.appendText(text+"\n"));
	}
	
	public void OutputMessage(String text){
		Platform.runLater(() -> OutputArea.appendText(text+"\n"));
	}
}

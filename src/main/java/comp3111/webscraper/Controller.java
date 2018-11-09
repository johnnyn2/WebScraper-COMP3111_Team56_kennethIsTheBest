/**
 * 
 */
package comp3111.webscraper;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;

import java.util.Calendar;
import java.util.List;

import javafx.application.*;

/**
 * 
 * @author kevinw
 *
 *
 * Controller class that manage GUI interaction. Please see document about JavaFX for details.
 * 
 */
public class Controller {

    @FXML
    private MenuItem lastSearch;
	
	@FXML 
    private Label labelCount; 

    @FXML 
    private Label labelPrice; 

    @FXML 
    private Hyperlink labelMin; 

    @FXML 
    private Hyperlink labelLatest; 

    @FXML
    private TextField textFieldKeyword;
    
    @FXML
    private TextArea textAreaConsole;
    
    private WebScraper scraper;
    
    private HostServices hostService;
    
    private List<Item> lastResult;
    
    /**
     * Default controller
     */
    public Controller() {
    	scraper = new WebScraper();
    }

    /**
     * Default initializer. It is empty.
     */
    @FXML
    private void initialize() {
    	lastSearch.setDisable(true);
    	textAreaConsole.setText("");
    	labelCount.setText("<total>");
    	labelPrice.setText("<AvgPrice>");
    	labelMin.setText("<Lowest>");
    	labelLatest.setText("<Latest>");
    }
    
    public void setHostServices(HostServices hostServices) {
    	this.hostService = hostServices;
    }
    
    /**
     * Called when the search button is pressed.
     */
    @FXML
    private void actionSearch() {
    	lastSearch.setDisable(false);
    	System.out.println("actionSearch: " + textFieldKeyword.getText());
    	List<Item> result = scraper.scrape(textFieldKeyword.getText());
    	lastResult.addAll(result);
    	String output = "";
    	for (Item item : result) {
    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
    	}
    	textAreaConsole.setText(output);
    	labelCount.setText(Integer.toString(result.size()));
    	
    	// calculate the avg price
    	int countPrice = 0;
    	double totalPrice = 0.0;
    	Item minItem = null, lastDate = null;
    	
    	// check if result have item inside (result.size() > 0 )
    	if (result.size() !=0) {
	    	for (Item item:result) {
	    		if(item.getPrice()>0.0) {
	    			countPrice++;
	    			totalPrice+= item.getPrice();
	    			// assign the first valid item to MIN or compare the item of MIN and in the result list 
	    			if(minItem == null || minItem.getPrice()>item.getPrice()) {
	    				minItem = item;
	    				labelMin.setOnAction(new EventHandler<ActionEvent>() {
	    					@Override
	    					public void handle(ActionEvent e) {
	    						hostService.showDocument(item.getUrl());
	    					}
	    				});
	    			}
	    			
	    			// assign the first valid item to DATE or compare the item of DATE and in the result list
	    			/*if(lastDate == null || lastDate.getDate().before(item.getDate())){
	    				lastDate = item;
	    				labelLatest.setOnAction(new EventHandler<ActionEvent>() {
	    					@Override
	    					public void handle(ActionEvent e) {
	    						hostService.showDocument(item.getUrl());
	    					}
    				}
	    				*/
	    		}
	    	}
	    	
	    	labelPrice.setText(Double.toString(totalPrice/countPrice));
	    	labelMin.setText(Double.toString(minItem.getPrice()));
	    	//labelLatest.setText(lastDate.getStringDate());
    	} else {
    		labelPrice.setText("-");
    		labelMin.setText("-");
    		labelMin.setOnAction(null);
    		labelLatest.setText("-");
    		labelLatest.setOnAction(null);
    	}
    }
    
    /**
     * Called when the new button is pressed. Very dummy action - print something in the command prompt.
     */
    @FXML
    private void actionNew() {
    	lastSearch.setDisable(true);
    	System.out.println("actionNew");
    }
    @FXML
    private void actionQuit() {
    	Platform.exit();
    	System.exit(0);
    }

    @FXML
    private void actionClose() {
    	initialize();
    	if(lastResult.size()!=0) {
	    	String output = "";
	    	// calculate the avg price
	    	int countPrice = 0;
	    	double totalPrice = 0.0;
	    	Item minItem = null, lastDate = null;
	    	for (Item item : lastResult) {
	    		output += item.getTitle() + "\t" + item.getPrice() + "\t" + item.getUrl() + "\n";
	    		if(item.getPrice()>0.0) {
	    			countPrice++;
	    			totalPrice+= item.getPrice();
	    			// assign the first valid item to MIN or compare the item of MIN and in the result list 
	    			if(minItem == null || minItem.getPrice()>item.getPrice()) {
	    				minItem = item;
	    				labelMin.setOnAction(new EventHandler<ActionEvent>() {
	    					@Override
	    					public void handle(ActionEvent e) {
	    						hostService.showDocument(item.getUrl());
	    					}
	    				});
	    			}
	    			
	    			// assign the first valid item to DATE or compare the item of DATE and in the result list
	    			/*if(lastDate == null || lastDate.getDate().before(item.getDate())){
	    				lastDate = item;
	    				labelLatest.setOnAction(new EventHandler<ActionEvent>() {
	    					@Override
	    					public void handle(ActionEvent e) {
	    						hostService.showDocument(item.getUrl());
	    					}
    				}
	    				*/
	    		}
	    	}
	    	textAreaConsole.setText(output);
	    	labelPrice.setText(Double.toString(totalPrice/countPrice));
	    	labelMin.setText(Double.toString(minItem.getPrice()));
    	}
    }
    
    @FXML
    private void actionAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Team Information");
    	alert.setHeaderText("Team Information: ");
    	alert.setContentText("Name: \tStudent ID: \tGithub account: \nHo Wai Kin\twkhoae\tjohnnyn2\nFung Hing Lun\thlfungad\tvictor0362\nHo Tsz Kiu\ttkhoad\tsarahtkho");
    	alert.showAndWait();
    }
}


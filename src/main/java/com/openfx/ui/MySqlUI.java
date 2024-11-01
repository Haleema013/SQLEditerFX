package com.openfx.ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.openjfx.fx.Menu_Items_FX;

import com.openfx.constants.MySQLConstants;
import com.openfx.handlers.NewMenuItemEventHandler;
import com.openfx.placeholders.ConnectionPlaceHolder;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
// import javafx.scene.web.HTMLEditor;
import javafx.util.Callback;

public class MySqlUI {
	
	public Menu_Items_FX menu_Items_FX;
	public NewMenuItemEventHandler newMenuItemEventHandler;
	
	private ConnectionPlaceHolder connectionPlaceHolder;
	private Statement stmt ;
	public Button refreshButton;
	public TabPane statusSystemVariablesTabpane;
    public Tab statusVariablesTab;
	public Tab systemVariablesTab;
	  
	public Tab globalSystemVariablesTab;
	public Tab sessionSystemVariablesTab;
	public Tab globalStatusVariablesTab;
	public Tab sessionStatusVariablesTab;
	  
	public TabPane systemVariablesTabPane;
	public TabPane statusVariablesTabPane;
	public VBox variablesSecondHalfDisplayVBox;
	  
	public VBox secondHalfDisplayVBox;
	
	public TableView performanceReportTableView;
	public Label particularPerformanceReportLabel;
	
	public MySqlUI(Menu_Items_FX menu_Items_FX,NewMenuItemEventHandler newMenuItemEventHandler) {
		this.menu_Items_FX = menu_Items_FX;
		this.newMenuItemEventHandler = newMenuItemEventHandler;
	}

	public VBox addConnectionCredentials() {
		
		 VBox connectionDetailsVbox = new VBox();
		  
		  GridPane connectionUrlCredentialsGridPane = new GridPane();
		  connectionUrlCredentialsGridPane.setPadding(new Insets(20,10,20,10));
		  connectionUrlCredentialsGridPane.setVgap(5);
		  connectionUrlCredentialsGridPane.setHgap(5);
		  Label jdbcUrlgeneralLabel = new Label("General"); 
		  GridPane.setConstraints(jdbcUrlgeneralLabel, 0, 0);   // column 0 row 0
		  Label jdbcConnectionNameLabel= new Label("Name");
		  GridPane.setConstraints(jdbcConnectionNameLabel, 0, 1);
		  newMenuItemEventHandler.jdbcConnectionName = new TextField("local");
		  newMenuItemEventHandler.jdbcConnectionName.setPrefHeight(15);
		  newMenuItemEventHandler.jdbcConnectionName.setPrefWidth(400);
		 // jdbcConnectionName.setOnKeyTyped(onjdbcUrlTextFieldKeyPressed() );
		  GridPane.setConstraints(newMenuItemEventHandler.jdbcConnectionName, 1, 1);
		  Label jdbcPortUrl = new Label("Port:");
		  GridPane.setConstraints(jdbcPortUrl, 2, 1);
		  newMenuItemEventHandler.jdbcConnectionPort = new TextField("3306");
		  newMenuItemEventHandler.jdbcConnectionPort.setPrefHeight(15);
		  newMenuItemEventHandler.jdbcConnectionPort.setPrefWidth(40);
		  GridPane.setConstraints(newMenuItemEventHandler.jdbcConnectionPort, 3, 1);
		  
		  Label jdbcUrlLabel = new Label("JDBC URL");
		  GridPane.setConstraints(jdbcUrlLabel, 0, 2);
		  newMenuItemEventHandler.jdbcUrlTextField = new TextField("jdbc:mysql://127.0.0.1");
		  newMenuItemEventHandler.jdbcUrlTextField.setPrefHeight(15);
		  newMenuItemEventHandler.jdbcUrlTextField.setPrefWidth(400);
		  newMenuItemEventHandler.jdbcUrlTextField.setOnKeyTyped(newMenuItemEventHandler.onjdbcUrlTextFieldKeyPressed() );
		  GridPane.setConstraints(newMenuItemEventHandler.jdbcUrlTextField, 1, 2);

		  Label jdbcUrlDatabaseNameLabel = new Label("Database ");
		  GridPane.setConstraints(jdbcUrlDatabaseNameLabel, 0, 3);
		  newMenuItemEventHandler.jdbcUrlDatabaseNameField = new TextField();
		  newMenuItemEventHandler.jdbcUrlDatabaseNameField.setPrefHeight(15);
		  newMenuItemEventHandler.jdbcUrlDatabaseNameField.setPrefWidth(200);  
		  newMenuItemEventHandler.jdbcUrlDatabaseNameField.setOnKeyTyped(newMenuItemEventHandler.onjdbcUrlTextFieldKeyPressed() );
		  GridPane.setConstraints(newMenuItemEventHandler.jdbcUrlDatabaseNameField, 1, 3);

		  
		  connectionUrlCredentialsGridPane.getChildren().addAll(jdbcConnectionNameLabel,newMenuItemEventHandler.jdbcConnectionName,jdbcUrlgeneralLabel,jdbcUrlLabel,newMenuItemEventHandler.jdbcConnectionPort,jdbcPortUrl,newMenuItemEventHandler.jdbcUrlTextField
				  ,jdbcUrlDatabaseNameLabel,newMenuItemEventHandler.jdbcUrlDatabaseNameField);
		  connectionDetailsVbox.getChildren().add(connectionUrlCredentialsGridPane);
		  
		  
		  GridPane connectionUsernamePasswordCredentialsGridPane = new GridPane();
		  connectionUsernamePasswordCredentialsGridPane.setPadding(new Insets(20,10,20,10));
		  connectionUsernamePasswordCredentialsGridPane.setVgap(5);
		  connectionUsernamePasswordCredentialsGridPane.setHgap(5);
		  Label jdbcUrlAuthenticationLabel = new Label("Authentication");
		  GridPane.setConstraints(jdbcUrlAuthenticationLabel, 0, 0);   // column 0 row 0
		  Label jdbcAuthenticationUsername = new Label("Username :");
		  GridPane.setConstraints(jdbcAuthenticationUsername, 0, 1);   // column 0 row 0
		  newMenuItemEventHandler.jdbcAuthenticationUsernameTextField = new TextField("root");
		  newMenuItemEventHandler.jdbcAuthenticationUsernameTextField.setPrefHeight(15);
		  newMenuItemEventHandler.jdbcAuthenticationUsernameTextField.setPrefWidth(200);
		  newMenuItemEventHandler.jdbcAuthenticationUsernameTextField.setOnKeyTyped(newMenuItemEventHandler.onjdbcAuthenticationUsernameTextFieldPressed());
		  GridPane.setConstraints(newMenuItemEventHandler.jdbcAuthenticationUsernameTextField, 1, 1);   // column 0 row 0
		  
		  Label jdbcAuthenticationPassword = new Label("Password");
		  GridPane.setConstraints(jdbcAuthenticationPassword, 0, 2);   // column 0 row 0
		  newMenuItemEventHandler.jdbcAuthenticationPasswordField = new PasswordField();
		  newMenuItemEventHandler.jdbcAuthenticationPasswordField.setPrefHeight(15);
		  newMenuItemEventHandler.jdbcAuthenticationPasswordField.setPrefWidth(200);
		  newMenuItemEventHandler.jdbcAuthenticationPasswordField.setOnKeyTyped(newMenuItemEventHandler.onjdbcAuthenticationPasswordFieldPressed());
		  GridPane.setConstraints(newMenuItemEventHandler.jdbcAuthenticationPasswordField, 1, 2);   // column 0 row 0
		  
		  connectionUsernamePasswordCredentialsGridPane.getChildren().addAll(
				  jdbcUrlAuthenticationLabel,jdbcAuthenticationUsername,jdbcAuthenticationPassword,newMenuItemEventHandler.jdbcAuthenticationUsernameTextField,newMenuItemEventHandler.jdbcAuthenticationPasswordField);
		   
		  connectionDetailsVbox.getChildren().add(connectionUsernamePasswordCredentialsGridPane); 
		  
		return connectionDetailsVbox;
		
		
	}
	
	
	
	public TreeItem<String> getmySqlTreeItem(ConnectionPlaceHolder connectionPlaceHolder,ImageView imagemySqlnode,ImageView imagemySqlTablenode,String databseName){
	
		this.connectionPlaceHolder = connectionPlaceHolder;
		this.menu_Items_FX.treeConnectionsView.setCellFactory((TreeView<String> p) -> new MySQLTreecellImpl());
		
		// the root level item , that is the connection name
		TreeItem<String> mySqlTreeItem = new TreeItem<String>(connectionPlaceHolder.getConnectionName(),imagemySqlnode);
		
		//Databases
		TreeItem<String> mySqlTreeItemDatabases = new TreeItem<String>("Databases");
		
		TreeItem<String> loadingTreeItem = new TreeItem<String>("Loading..");
		mySqlTreeItemDatabases.getChildren().add(loadingTreeItem);
		
		mySqlTreeItemDatabases.expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					TreeItem bean = ((TreeItem)((BooleanProperty)observable).getBean()) ;
					String name = ((BooleanProperty)observable).getName();
					Boolean value = ((BooleanProperty)observable).getValue() ;
					System.out.println("observable : "+ "Bean : "+ bean.getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getValue() );		
				
						if(value) {
							// go to the connection and get the Databases
							System.out.println("Databases Expanded!!");
							System.out.println( connectionPlaceHolder.getConnectionName());
							for(Entry<ConnectionPlaceHolder,Connection>  entrySet :  menu_Items_FX.mySqlConnectionsMap.entrySet())
							{
								// MySQL##con12
								if(connectionPlaceHolder.getConnectionName().equalsIgnoreCase(entrySet.getKey().getConnectionName()))
								{
									System.out.println("Current Connection is :"+ entrySet.getKey().getConnectionName() + " of type "+entrySet.getValue());
									Connection currentConnection = entrySet.getValue();
									
									try {
										stmt = currentConnection.createStatement();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									// this thread will continue to look up the databases ans till then the view will show as loading
									// we have added this in thread as we don't want the expand to be held back by this method completion.(Loading... needs to be displayed)
									new Thread(new Runnable() {
									     @Override
									     public void run() {
									         
									    	try (ResultSet rs = stmt.executeQuery("show databases")) {
									    		try {
													Thread.sleep(1000);
												} catch (InterruptedException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												mySqlTreeItemDatabases.getChildren().remove(0);  // Remove the Loading...
												while(rs.next()) {
													  System.out.println(rs.getString(1));
													  TreeItem<String> loadedDatabaseName = new TreeItem<String>(rs.getString(1));
													  
													 TreeItem<String> mySqlTreeItemTables = new TreeItem<String>("Tables",imagemySqlTablenode);
													 TreeItem<String> loadingTreeItemTables = new TreeItem<String>("Loading..");
													 mySqlTreeItemTables.getChildren().add(loadingTreeItemTables);
														
													 TreeItem<String> mySqlTreeItemViews = new TreeItem<String>("Views");
													 TreeItem<String> loadingTreeItemViews = new TreeItem<String>("Loading..");
													 mySqlTreeItemViews.getChildren().add(loadingTreeItemViews);
													 
													 TreeItem<String> mySqlTreeItemProcedures = new TreeItem<String>("Procedures");
													 TreeItem<String> loadingTreeItemProcedures = new TreeItem<String>("Loading..");
													 mySqlTreeItemProcedures.getChildren().add(loadingTreeItemProcedures);
													 
													 TreeItem<String> mySqlTreeItemFunctions = new TreeItem<String>("Functions");
													 TreeItem<String> loadingTreeItemFunctions = new TreeItem<String>("Loading..");
													 mySqlTreeItemFunctions.getChildren().add(loadingTreeItemFunctions);
													 
													 TreeItem<String> mySqlTreeItemEvents = new TreeItem<String>("Events");
													 TreeItem<String> loadingTreeItemEvents = new TreeItem<String>("Loading..");
													 mySqlTreeItemEvents.getChildren().add(loadingTreeItemEvents);
													  
													 loadedDatabaseName.getChildren().add(mySqlTreeItemTables);
													 loadedDatabaseName.getChildren().add(mySqlTreeItemViews);
													 loadedDatabaseName.getChildren().add(mySqlTreeItemProcedures);
													 loadedDatabaseName.getChildren().add(mySqlTreeItemFunctions);
													 loadedDatabaseName.getChildren().add(mySqlTreeItemEvents);
													 
													 //Database expanded
													 loadedDatabaseName.expandedProperty().addListener(new ChangeListener<Boolean>() {
															@Override
															public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																
																System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																Boolean value = ((BooleanProperty)observable).getValue() ;
																if(value) {
																	System.out.println("Particular Databse Expanded !!!"+loadedDatabaseName.getValue());
																}
																else {
																	System.out.println("Particular Databse Collapsed !!!"+loadedDatabaseName.getValue());
																}
															}
													 });
													 
													 
													 // Tables 
													 mySqlTreeItemTables.expandedProperty().addListener(new ChangeListener<Boolean>() {
															@Override
															public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																	
																String name = ((BooleanProperty)observable).getName();
																Boolean value = ((BooleanProperty)observable).getValue() ;
																
																    if(value) {
																		System.out.println("Its aTables expansion!!!");
																		
																		// get the database name and display its tables 
																		TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());
																		
	
																		System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																		
																		new Thread(new Runnable() {
																		     @Override
																		     public void run() {
																		         
																		    	try  {
																		    		stmt.execute("use "+loadedDatabaseName.getValue());
																		    		ResultSet rs = stmt.executeQuery("SHOW FULL TABLES IN "+ loadedDatabaseName.getValue() +" WHERE TABLE_TYPE LIKE 'BASE TABLE'");
																		    		try {
																						Thread.sleep(1000);
																					} catch (InterruptedException e) {
																						// TODO Auto-generated catch block
																						e.printStackTrace();
																					}
																		    		mySqlTreeItemTables.getChildren().remove(0);  // Remove the Loading...
																					while(rs.next()) {
																						  System.out.println(rs.getString(1));
																						  
																						  TreeItem<String> loadedTableName = new TreeItem<String>(rs.getString(1));  // get the table one by one 
																						  loadedTableName.expandedProperty().addListener(new ChangeListener<Boolean>() {
																								@Override
																								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																									System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																										
																									String name = ((BooleanProperty)observable).getName();
																									Boolean value = ((BooleanProperty)observable).getValue() ;
																									if(value) {
																										 System.out.println("Loaded Table Expanded!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																									}else {
																										System.out.println("Loaded Table Collpapsed!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																									}
																								}
																						  }); 
																						  
																						  TreeItem<String> tableColumns = new TreeItem<String>("Columns");
																						  TreeItem<String> loadingTableNameColumns = new TreeItem<String>("Loading..");
																						  tableColumns.getChildren().add(loadingTableNameColumns);
																						  tableColumns.expandedProperty().addListener(new ChangeListener<Boolean>() {
																								@Override
																								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																									System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																										
																									String name = ((BooleanProperty)observable).getName();
																									Boolean value = ((BooleanProperty)observable).getValue() ;
																									if(value) {
																										 System.out.println("Columns Expanded!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																										// get the database name and display its tables 
																											TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());
																											
																											System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																											
																											new Thread(new Runnable() {
																											     @Override
																											     public void run() {
																											    	 try {
																											    		 
																											    		   stmt.execute("use "+loadedDatabaseName.getValue());
																											    		   ResultSet rs = stmt.executeQuery("desc "+loadedTableName.getValue());
																											    		   try {
																																Thread.sleep(100);
																															} catch (InterruptedException e) {
																																// TODO Auto-generated catch block
																																e.printStackTrace();
																															}
																											    		   tableColumns.getChildren().remove(0);  // Remove the Loading...
																															while(rs.next()) {
																																  System.out.println(rs.getString(1) + " , "+rs.getString(2));
																																  TreeItem<String> constraintsName = new TreeItem<String>(rs.getString(1) + " , "+rs.getString(2));
																																  tableColumns.getChildren().add(constraintsName);
																															}
																											    		   
																													     
																													    } catch (SQLException e) {
																															System.out.println("Error during columns expansion");
																															e.printStackTrace();
																														}
																											    	 }
																											}).start();
																										 
																									}else {
																										System.out.println("Columns Collpapsed!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																										tableColumns.getChildren().clear();
																										tableColumns.getChildren().add(loadingTableNameColumns);
																									}
																								}
																						  });
																						  TreeItem<String> tableConstraints = new TreeItem<String>("Constaints");
																						  TreeItem<String> loadingTableNameConstraints = new TreeItem<String>("Loading..");
																						  tableConstraints.getChildren().add(loadingTableNameConstraints);
																						  tableConstraints.expandedProperty().addListener(new ChangeListener<Boolean>() {
																								@Override
																								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																									System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																										
																									String name = ((BooleanProperty)observable).getName();
																									Boolean value = ((BooleanProperty)observable).getValue() ;
																									if(value) {
																										 System.out.println("Constraints Expanded!!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																										 
																										 TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());
																											
																											System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																											
																											new Thread(new Runnable() {
																											     @Override
																											     public void run() {
																											        
																											    	 try {
																											    		 
																											    		    stmt.execute("use "+loadedDatabaseName.getValue());
																											    		    ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE "+loadedTableName.getValue());		
																													        try {
																																Thread.sleep(100);
																															} catch (InterruptedException e) {
																																// TODO Auto-generated catch block
																																e.printStackTrace();
																															}
																													        tableConstraints.getChildren().remove(0);  // Remove the Loading...
																															while(rs.next()) {
																																  System.out.println(rs.getString(2) );
																																  // break down the show table result and get the primary key details
																																  
																																  String mysqlQuerySplit[] = rs.getString(2).split("\n");
																																	
																																	for(int i=0;i<mysqlQuerySplit.length;i++) {
																																		
																																		if(mysqlQuerySplit[i].contains("PRIMARY KEY")) {
																																		
																																			System.out.println( mysqlQuerySplit[i]);
																																			TreeItem<String> foreignKeysName = new TreeItem<String>(mysqlQuerySplit[i]);
																																			tableConstraints.getChildren().add(foreignKeysName);																	  
																																		}
																																		
																																	} 	
																															}
																													        
																													    } catch (SQLException e) {
																															System.out.println("Error during constrains expansion");
																															e.printStackTrace();
																														}
																											    	 }
																											}).start();
																										 
																										 
																									}else {
																										System.out.println("Constraints Collpapsed!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																										tableConstraints.getChildren().clear();
																										tableConstraints.getChildren().add(loadingTableNameConstraints);
																									}
																								}
																						  });
																						  TreeItem<String> tableForeignKeys = new TreeItem<String>("Foreign Keys");
																						  
																						  
																						  
																						  
																						  TreeItem<String> loadingTableNameForeignKeys = new TreeItem<String>("Loading..");
																						  tableForeignKeys.getChildren().add(loadingTableNameForeignKeys);
																						  tableForeignKeys.expandedProperty().addListener(new ChangeListener<Boolean>() {
																								@Override
																								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																									System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																										
																									String name = ((BooleanProperty)observable).getName();
																									Boolean value = ((BooleanProperty)observable).getValue() ;
																									if(value) {
																										 System.out.println("Foreign Key Expanded!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue()); 
																										 
																										TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());
																											
																										System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																											
																											new Thread(new Runnable() {
																											     @Override
																											     public void run() {
																											        
																											    	 try {
																											    		 
																											    		    stmt.execute("use "+loadedDatabaseName.getValue());
																											    		    ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE "+loadedTableName.getValue());		
																													        try {
																																Thread.sleep(100);
																															} catch (InterruptedException e) {
																																// TODO Auto-generated catch block
																																e.printStackTrace();
																															}
																													        tableForeignKeys.getChildren().remove(0);  // Remove the Loading...
																															while(rs.next()) {
																																  System.out.println(rs.getString(2) );
																																  // break down the show table result and get the foreigh key details
																																  
																																  String mysqlQuerySplit[] = rs.getString(2).split("\n");
																																	
																																	for(int i=0;i<mysqlQuerySplit.length;i++) {
																																		
																																		if(mysqlQuerySplit[i].contains("CONSTRAINT") || mysqlQuerySplit[i].contains("FOREIGN KEY")) {
																																		
																																			System.out.println( mysqlQuerySplit[i]);
																																			TreeItem<String> foreignKeysName = new TreeItem<String>(mysqlQuerySplit[i].replace("CONSTRAINT", ""));
																																			 tableForeignKeys.getChildren().add(foreignKeysName);
																																		}
																																	}	 	
																															}
																													        
																													    } catch (SQLException e) {
																															System.out.println("Error during constrains expansion");
																															e.printStackTrace();
																														}
																											    	 }
																											}).start();
																										 
																									}else {
																										System.out.println("Foreign Key Collpapsed!!" + "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																									}
																								}
																						  });
																						  TreeItem<String> tableReferences = new TreeItem<String>("References");
																						  TreeItem<String> loadingTableNameReferences = new TreeItem<String>("Loading..");
																						  tableReferences.getChildren().add(loadingTableNameReferences);
																						  tableReferences.expandedProperty().addListener(new ChangeListener<Boolean>() {
																								@Override
																								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																									System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																										
																									String name = ((BooleanProperty)observable).getName();
																									Boolean value = ((BooleanProperty)observable).getValue() ;
																									if(value) {
																										 System.out.println("References Expanded!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());																																												 
																										 TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());										
																										 System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																												
																										new Thread(new Runnable() {
																											@Override
																											public void run() {
																												        
																												try {
																												    		 
																												    	stmt.execute("use "+loadedDatabaseName.getValue());
																												    	ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE "+loadedTableName.getValue());		
																												    	try {
																															Thread.sleep(100);
																														} catch (InterruptedException e) {
																																	// TODO Auto-generated catch block
																																	e.printStackTrace();
																														}
																												    	tableReferences.getChildren().remove(0);  // Remove the Loading...
																														while(rs.next()) {
																															System.out.println(rs.getString(2) );
																															// break down the show table result and get the foreigh key details
																																	  
																															String mysqlQuerySplit[] = rs.getString(2).split("\n");
																																		
																															for(int i=0;i<mysqlQuerySplit.length;i++) {
																																			
																															if(mysqlQuerySplit[i].contains("CONSTRAINT") || mysqlQuerySplit[i].contains("REFERENCES")) {
																																			
																																System.out.println( mysqlQuerySplit[i]);
																																TreeItem<String> foreignKeysName = new TreeItem<String>(mysqlQuerySplit[i].replace("CONSTRAINT", ""));
																																tableReferences.getChildren().add(foreignKeysName);
																															}
																														}	 	
																													}
																														        
																												} catch (SQLException e) {
																														System.out.println("Error during references expansion");
																														e.printStackTrace();
																													}
																												}
																											}).start();
																									}else {
																										System.out.println("References Collpapsed!!" + "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																										tableReferences.getChildren().clear();
																										tableReferences.getChildren().add(loadingTableNameConstraints);
																									}
																								}
																						  });
																						  TreeItem<String> tableTriggers = new TreeItem<String>("Triggers");
																						  TreeItem<String> loadingTableNameTriggers = new TreeItem<String>("Loading..");
																						  tableTriggers.getChildren().add(loadingTableNameTriggers);
																						  tableTriggers.expandedProperty().addListener(new ChangeListener<Boolean>() {
																								@Override
																								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																									System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																										
																									String name = ((BooleanProperty)observable).getName();
																									Boolean value = ((BooleanProperty)observable).getValue() ;
																									if(value) {
																										 System.out.println("Triggers Expanded!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																										 TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());										
																										 System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																												
																										new Thread(new Runnable() {
																											@Override
																											public void run() {
																												        
																												try {
																												    		 
																												    	stmt.execute("use "+loadedDatabaseName.getValue());
																												    	ResultSet rs = stmt.executeQuery("SHOW TRIGGERS LIKE '"+loadedTableName.getValue()+"'");		
																												    	try {
																															Thread.sleep(100);
																														} catch (InterruptedException e) {
																																	// TODO Auto-generated catch block
																																	e.printStackTrace();
																														}
																												    	tableTriggers.getChildren().remove(0);  // Remove the Loading...
																														while(rs.next()) {
																															System.out.println(rs.getString(1) );
																															// break down the show table result and get the foreigh key details	
																															TreeItem<String> triggerName = new TreeItem<String>(rs.getString(1));
																															tableTriggers.getChildren().add(triggerName);
																															
																															 	
																													}
																														        
																												} catch (SQLException e) {
																														System.out.println("Error during t expansion");
																														e.printStackTrace();
																												}
																											}
																											}).start();
																										 
																									}else {
																										System.out.println("Triggers Collpapsed!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																										tableTriggers.getChildren().clear();
																										tableTriggers.getChildren().add(loadingTableNameConstraints);
																									}
																									
																								}
																						  });
																						  TreeItem<String> tableIndexes = new TreeItem<String>("Indexes");
																						  TreeItem<String> loadingTableNameIndexes = new TreeItem<String>("Loading..");
																						  tableIndexes.getChildren().add(loadingTableNameIndexes);
																						  tableIndexes.expandedProperty().addListener(new ChangeListener<Boolean>() {
																								@Override
																								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																									System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																										
																									String name = ((BooleanProperty)observable).getName();
																									Boolean value = ((BooleanProperty)observable).getValue() ;
																									if(value) {
																										 System.out.println("Indexes Expanded!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																										 TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());										
																										 System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																												
																										new Thread(new Runnable() {
																											@Override
																											public void run() {
																												        
																												try {
																												    		 
																												    	stmt.execute("use "+loadedDatabaseName.getValue());
																												    	ResultSet rs = stmt.executeQuery("SHOW INDEXES FROM "+loadedTableName.getValue()+" FROM "+loadedDatabaseName.getValue());		
																												    	try {
																															Thread.sleep(100);
																														} catch (InterruptedException e) {
																																	// TODO Auto-generated catch block
																																	e.printStackTrace();
																														}
																												    	tableIndexes.getChildren().remove(0);  // Remove the Loading...
																														while(rs.next()) {
																															System.out.println(rs.getString(3)+ " , " + rs.getString(5));
																															// break down the show table result and get the foreigh key details	
																															TreeItem<String> indexName = new TreeItem<String>(rs.getString(3)+ " , " + rs.getString(5));
																															tableIndexes.getChildren().add(indexName);
																															
																															 	
																													}
																													//stmt.close();
																														        
																												} catch (SQLException e) {
																														System.out.println("Error during indexes expansion");
																														e.printStackTrace();
																												}
																											}
																											}).start();
																										
																										
																									}else {
																										System.out.println("Indexes Collpapsed!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																										tableIndexes.getChildren().clear();
																										tableIndexes.getChildren().add(loadingTableNameIndexes);
																									}
																								}
																						  });
																						  
																						  TreeItem<String> tablePartitions = new TreeItem<String>("Partitions");
																						  TreeItem<String> loadingTableNamePartitions = new TreeItem<String>("Loading..");
																						  tablePartitions.getChildren().add(loadingTableNamePartitions);
																						  tablePartitions.expandedProperty().addListener(new ChangeListener<Boolean>() {
																								@Override
																								public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																									System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																										
																									String name = ((BooleanProperty)observable).getName();
																									Boolean value = ((BooleanProperty)observable).getValue() ;
																									if(value) {
																										 System.out.println("Pratitions Expanded!! "+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																										 TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());										
																										 System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																												
																										new Thread(new Runnable() {
																											@Override
																											public void run() {
																												        
																												try {																												    		 
																												    	stmt.execute("use "+loadedDatabaseName.getValue());
																												    	ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE "+loadedTableName.getValue());		
																												    	/*!50100 PARTITION BY RANGE (year(`purchased`))\r\n"
																											 			+ "(PARTITION p0 VALUES LESS THAN (1985) ENGINE = InnoDB,\r\n"
																											 			+ " PARTITION p1 VALUES LESS THAN (1990) ENGINE = InnoDB,\r\n"
																											 			+ " PARTITION p2 VALUES LESS THAN (1995) ENGINE = InnoDB,\r\n"
																											 			+ " PARTITION p3 VALUES LESS THAN (2000) ENGINE = InnoDB,\r\n"
																											 			+ " PARTITION p4 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */ 
																												    	try {
																															Thread.sleep(100);
																														} catch (InterruptedException e) {
																																	// TODO Auto-generated catch block
																																	e.printStackTrace();
																														}
																												    	tablePartitions.getChildren().remove(0);  // Remove the Loading...
																														while(rs.next()) {
																															String createShowTableQuery = rs.getString(2);
																															System.out.println(createShowTableQuery);
																															// select first word after partition and not followed by 'BY' which will be the partition name
																															String[] arrayWithPartitionDescriptions = createShowTableQuery.split("\n");
																														 	for(int i=0;i<arrayWithPartitionDescriptions.length;i++)
																														 	{
																														 		if(arrayWithPartitionDescriptions[i].toUpperCase().contains("PARTITION")) {
																														 			if(arrayWithPartitionDescriptions[i].toUpperCase().contains("PARTITION BY") )
																														 				continue;
																														 			for(int j=0;j<arrayWithPartitionDescriptions[i].split(" ").length;j++) {
																														 				if(arrayWithPartitionDescriptions[i].split(" ")[j].contains("PARTITION") && (arrayWithPartitionDescriptions[i].split(" ")[j].replace("(","").length() == "PARTITION".length()) ) {
																														 					System.out.println(arrayWithPartitionDescriptions[i].split(" ")[j+1]);
																														 					TreeItem<String> partitionName = new TreeItem<String>(arrayWithPartitionDescriptions[i].split(" ")[j+1]);
																																			tablePartitions.getChildren().add(partitionName);
																														 				}
																														 			}
																														 		}
																														 	}
																													}
																													//stmt.close();
																														        
																												} catch (SQLException e) {
																														System.out.println("Error during partition expansion");
																														e.printStackTrace();
																												}
																											}
																											}).start();
																									}else {
																										System.out.println("Partitions Collpapsed!!"+ "Database Selected "+loadedDatabaseName.getValue() +" Tables Selected " +loadedTableName.getValue());
																										tablePartitions.getChildren().clear();
																										tablePartitions.getChildren().add(loadingTableNameIndexes);
																									}
																								}
																						  });
																						  
																						  
																						  loadedTableName.getChildren().add(tableColumns);
																						  loadedTableName.getChildren().add(tableConstraints);
																						  loadedTableName.getChildren().add(tableForeignKeys);
																						  loadedTableName.getChildren().add(tableReferences);
																						  loadedTableName.getChildren().add(tableTriggers);
																						  loadedTableName.getChildren().add(tableIndexes);
																						  loadedTableName.getChildren().add(tablePartitions);
																						  
																						  mySqlTreeItemTables.getChildren().add(loadedTableName);
																						
																					}
																				} catch (SQLException e) {
																					// TODO Auto-generated catch block
																					e.printStackTrace();
																				}
																		     }
																		}).start();
																  }else {
																		System.out.println("Collapsed!!! Tables "+name);
																		mySqlTreeItemTables.getChildren().clear();
																		mySqlTreeItemTables.getChildren().add(loadingTreeItemTables);
																  }
															}
													 });
													 
													 // Views 
													 mySqlTreeItemViews.expandedProperty().addListener(new ChangeListener<Boolean>() {
															@Override
															public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																	
																String name = ((BooleanProperty)observable).getName();
																Boolean value = ((BooleanProperty)observable).getValue() ;
																
																 if(value) {
																	System.out.println("Its Views expansion!!!");
																	
																	// get the database name and display its tables 
																	TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());
																	

																	System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																	
																	new Thread(new Runnable() {
																	     @Override
																	     public void run() {
																	         
																	    	try  {
																	    		stmt.execute("use "+loadedDatabaseName.getValue());
																	    		ResultSet rs = stmt.executeQuery("SHOW FULL TABLES IN "+ loadedDatabaseName.getValue() +" WHERE TABLE_TYPE LIKE 'VIEW'");
																	    		try {
																					Thread.sleep(1000);
																				} catch (InterruptedException e) {
																					// TODO Auto-generated catch block
																					e.printStackTrace();
																				}
																	    		mySqlTreeItemViews.getChildren().remove(0);  // Remove the Loading...
																				while(rs.next()) {
																					  System.out.println(rs.getString(1));
																					  
																					  TreeItem<String> ViewName = new TreeItem<String>(rs.getString(1));
																					  
																					  mySqlTreeItemViews.getChildren().add(ViewName);
																					
																				}
																				//stmt.close();
																			} catch (SQLException e) {
																				// TODO Auto-generated catch block
																				e.printStackTrace();
																			}
																	     }
																	}).start();
																 }
																 else {
																	   System.out.println("Collapsed!!! Views ");
																	   mySqlTreeItemViews.getChildren().clear();
																	   mySqlTreeItemViews.getChildren().add(loadingTreeItemViews);
																 }
															}
													 });
													 
													 // Procedures 
													 mySqlTreeItemProcedures.expandedProperty().addListener(new ChangeListener<Boolean>() {
															@Override
															public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																	
																	System.out.println("Its Procedures expansion!!!"); // from here fix procedures
																	
																	// get the database name and display its tables 
																	TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());
																	

																	System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																	
																	new Thread(new Runnable() {
																	     @Override
																	     public void run() {
																	         
																	    	try  {
																	    		stmt.execute("use "+loadedDatabaseName.getValue());
																	    		ResultSet rs = stmt.executeQuery(" SHOW PROCEDURE STATUS WHERE Db = '"+ loadedDatabaseName.getValue() +"'");
																	    		try {
																					Thread.sleep(1000);
																				} catch (InterruptedException e) {
																					// TODO Auto-generated catch block
																					e.printStackTrace();
																				}
																	    		mySqlTreeItemProcedures.getChildren().remove(0);  // Remove the Loading...
																				while(rs.next()) {
																					  System.out.println(rs.getString(1));
																					  System.out.println(rs.getString(2));
																					  mySqlTreeItemProcedures.getChildren().add(new TreeItem<String>(rs.getString(2)));
																					
																				}
																				//stmt.close();
																			} catch (SQLException e) {
																				// TODO Auto-generated catch block
																				e.printStackTrace();
																			}
																	     }
																	}).start();		
															}
													 });
													 
													 //Functions
													 mySqlTreeItemFunctions.expandedProperty().addListener(new ChangeListener<Boolean>() {
															@Override
															public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
														
																System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																
																System.out.println("Its Functions expansion!!!"); // from here fix procedures
																
																// get the database name and display its tables 
																TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());
																

																System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																
																new Thread(new Runnable() {
																     @Override
																     public void run() {
																         
																    	try  {
																    		stmt.execute("use "+loadedDatabaseName.getValue());
																    		ResultSet rs = stmt.executeQuery(" SHOW FUNCTION STATUS WHERE Db = '"+ loadedDatabaseName.getValue() +"'");
																    		try {
																				Thread.sleep(1000);
																			} catch (InterruptedException e) {
																				// TODO Auto-generated catch block
																				e.printStackTrace();
																			}
																    		mySqlTreeItemFunctions.getChildren().remove(0);  // Remove the Loading...
																			while(rs.next()) {
																				  System.out.println(rs.getString(1));
																				  System.out.println(rs.getString(2));
																				  mySqlTreeItemFunctions.getChildren().add(new TreeItem<String>(rs.getString(2)));
																				
																			}
																			//stmt.close();
																		} catch (SQLException e) {
																			// TODO Auto-generated catch block
																			e.printStackTrace();
																		}
																     }
																}).start();		
																
															 }
													 });
													 
													 // Events
													 mySqlTreeItemEvents.expandedProperty().addListener(new ChangeListener<Boolean>() {
															@Override
															public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																	
																String name = ((BooleanProperty)observable).getName();
																Boolean value = ((BooleanProperty)observable).getValue() ;
																
																 if(value) {
																	System.out.println("Its Events expansion!!!");																	
																	// get the database name and display its tables 
																	TreeItem<String> currentDatabasebean = ((TreeItem<String>)((BooleanProperty)observable).getBean());
																	System.out.println("Current DatabaseSelected Name "+loadedDatabaseName.getValue());
																	
																	new Thread(new Runnable() {
																	     @Override
																	     public void run() {
																	         
																	    	try  {
																	    		stmt.execute("use "+loadedDatabaseName.getValue());
																	    		ResultSet rs = stmt.executeQuery("SHOW EVENTS FROM "+loadedDatabaseName.getValue());
																	    		try {
																					Thread.sleep(1000);
																				} catch (InterruptedException e) {
																					// TODO Auto-generated catch block
																					e.printStackTrace();
																				}
																	    		mySqlTreeItemEvents.getChildren().remove(0);  // Remove the Loading...
																				while(rs.next()) {
																					  System.out.println(rs.getString(1));
																					  TreeItem<String> ViewName = new TreeItem<String>(rs.getString(2));
																					  mySqlTreeItemEvents.getChildren().add(ViewName);
																					
																				}
																				//stmt.close();
																			} catch (SQLException e) {
																				// TODO Auto-generated catch block
																				e.printStackTrace();
																			}
																	     }
																	}).start();
																 }
																 else {
																	   System.out.println("Collapsed!!! Events ");
																	   mySqlTreeItemEvents.getChildren().clear();
																	   mySqlTreeItemEvents.getChildren().add(loadingTreeItemViews);
																 }
															}
													 });
													 
													 // ---------------END------------------
													 
													 // Add the Databse to the Databse tree
													 mySqlTreeItemDatabases.getChildren().add(loadedDatabaseName);
												}
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
									     }
									}).start();
									
								}
							}
						}
						else {
							System.out.println("Databases Collapsed!!!" + name);
							mySqlTreeItemDatabases.getChildren().clear();  // Clear the list
							mySqlTreeItemDatabases.getChildren().add(loadingTreeItem);
						}
		}
		});
		
		// Check if this can be mage a generic code
		// Users
		/*
		TreeItem<String> mySqlTreeItemUsers = new TreeItem<String>("Users");
		TreeItem<String> loadingTreeItemUsers = new TreeItem<String>("Loading..");
		mySqlTreeItemUsers.getChildren().add(loadingTreeItemUsers);
		
		mySqlTreeItemUsers.expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					TreeItem bean = ((TreeItem)((BooleanProperty)observable).getBean()) ;
					String name = ((BooleanProperty)observable).getName();
					Boolean value = ((BooleanProperty)observable).getValue() ;
					System.out.println("observable : "+ "Bean : "+ bean.getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getValue() );		
				
						if(value) {
							// go to the connection and get the Databases
							System.out.println("Users Expanded!!");
							System.out.println( connectionPlaceHolder.getConnectionName());
							for(Entry<ConnectionPlaceHolder,Connection>  entrySet :  menu_Items_FX.mySqlConnectionsMap.entrySet())
							{
								// MySQL##con12
								if(connectionPlaceHolder.getConnectionName().equalsIgnoreCase(entrySet.getKey().getConnectionName()))
								{
									System.out.println("Current Connection is :"+ entrySet.getKey().getConnectionName() + " of type "+entrySet.getValue());
									Connection currentConnection = entrySet.getValue();
									
									try {
										stmt = currentConnection.createStatement();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									// this thread will continue to look up the databases ans till then the view will show as loading
									// we have added this in thread as we don't want the expand to be held back by this method completion.(Loading... needs to be displayed)
									new Thread(new Runnable() {
									     @Override
									     public void run() {
									         
									    	try (ResultSet rs = stmt.executeQuery("select * from mysql.user")) {
									    		
									    		mySqlTreeItemUsers.getChildren().remove(0);  // Remove the Loading...
												while(rs.next()) {
													  System.out.println(rs.getString(1));
													  String userAccount = "'" + rs.getString(2) + "'" + "@" + "'" + rs.getString(1) +"'" ; 
													  TreeItem<String> loadedUserName = new TreeItem<String>(rs.getString(2)+"@"+rs.getString(1));
													  TreeItem<String> loadedUserNameDetailsLoading = new TreeItem<String>("Loading..");
													  loadedUserName.getChildren().add(loadedUserNameDetailsLoading);
													  mySqlTreeItemUsers.getChildren().add(loadedUserName);
													  
													  loadedUserName.expandedProperty().addListener(new ChangeListener<Boolean>() {
															@Override
															public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
																
																	System.out.println("observable : "+ "Bean : "+ ((TreeItem<String>)((BooleanProperty)observable).getBean()).getValue()     +" Name : "+((BooleanProperty)observable).getName() +" Value :" +((BooleanProperty)observable).getName() );
																	
																	String name = ((BooleanProperty)observable).getName();
																	Boolean value = ((BooleanProperty)observable).getValue() ;
																	if(value) {
																		 System.out.println("Users Expanded!! "+ "User Selected "+loadedUserName.getValue() );
																		 
																		 System.out.println("Run "+ "show grants for " +userAccount);

																		 new Thread(new Runnable() {
																		     @Override
																		     public void run() {
																		         
																		    	try  {
																		    		stmt.execute("show grants for " +userAccount);
																		    		ResultSet rs = stmt.executeQuery("show grants for " +userAccount);
																		    		try {
																						Thread.sleep(100);
																					} catch (InterruptedException e) {
																						// TODO Auto-generated catch block
																						e.printStackTrace();
																					}
																		    		loadedUserName.getChildren().remove(0); // Remove the Loading...
																					while(rs.next()) {
																						  System.out.println(rs.getString(1));
																						  loadedUserName.getChildren().add(new TreeItem<String>(rs.getString(1)));
																						
																					}
																					//stmt.close();
																				} catch (SQLException e) {
																					// TODO Auto-generated catch block
																					e.printStackTrace();
																				}
																		     }
																		}).start();		
										 																		 
																	}else {
																		System.out.println("Users Collpapsed!! "+ "User Selected "+loadedUserName.getValue() );
																		  loadedUserName.getChildren().clear();
																		  loadedUserName.getChildren().add(loadedUserNameDetailsLoading);
																	}
																
																}
															});
												}	  			
									    	}catch(SQLException e) {
									    		
									    	//	System.out.println("Error Code is :"+ e.getErrorCode());
									    	//	System.out.println("SQL State is "+ e.getSQLState());
									    	//	System.out.println("Get Cause"+ e.getCause());
									    	//	System.out.println("Get Localized Message" +e.getLocalizedMessage());
									    	//	System.out.println("Get MEssage"+ e.getMessage());
									    		
									    		e.printStackTrace();
									    		
									    		String errorString = "SQL Error["+e.getErrorCode()+"]["+e.getSQLState()+"]:"+e.getLocalizedMessage()+"/n"+e.getMessage();
									    		System.out.println(errorString);
									    	}
									     }
									}).start();
								}
							}
						}else {
							System.out.println("Users Collapsed!!");
							mySqlTreeItemUsers.getChildren().clear();
							mySqlTreeItemUsers.getChildren().add(loadingTreeItemUsers);
						}
			}
		}); */
								
		//Administrator
		TreeItem<String> mySqlTreeItemAdminister = new TreeItem<String>("Administer");
		
		TreeItem<String> mySqlTreeItemAdministration = new TreeItem<String>("Administration");
		mySqlTreeItemAdminister.getChildren().add(mySqlTreeItemAdministration);
		TreeItem<String> mySqlTreeItemAdministerServerStatus = new TreeItem<String>("Server Status");
		mySqlTreeItemAdministration.getChildren().add(mySqlTreeItemAdministerServerStatus);
		TreeItem<String> mySqlTreeItemAdministerClientConnectionss = new TreeItem<String>("Client Connections");
		mySqlTreeItemAdministration.getChildren().add(mySqlTreeItemAdministerClientConnectionss);
		TreeItem<String> mySqlTreeItemAdministerUserandPrivileges = new TreeItem<String>("Users and Privileges");
		mySqlTreeItemAdministration.getChildren().add(mySqlTreeItemAdministerUserandPrivileges);
		TreeItem<String> mySqlTreeItemAdministerStatusandSystemVariables = new TreeItem<String>("Status and System Variables");
		mySqlTreeItemAdministration.getChildren().add(mySqlTreeItemAdministerStatusandSystemVariables);
		
		TreeItem<String> mySqlTreeItemPerformance = new TreeItem<String>("Performance");
		mySqlTreeItemAdminister.getChildren().add(mySqlTreeItemPerformance);
		TreeItem<String> mySqlTreeItemAdministerDashboard = new TreeItem<String>("Dashboard");
		mySqlTreeItemPerformance.getChildren().add(mySqlTreeItemAdministerDashboard);
		TreeItem<String> mySqlTreeItemAdministerPerformanceReports = new TreeItem<String>("Performance Reports");
		mySqlTreeItemPerformance.getChildren().add(mySqlTreeItemAdministerPerformanceReports);
		
		TreeItem<String> mySqlTreeItemServer = new TreeItem<String>("Server");
		mySqlTreeItemAdminister.getChildren().add(mySqlTreeItemServer);
		TreeItem<String> mySqlTreeItemAdministerServerLogs = new TreeItem<String>("Server Logs");
		mySqlTreeItemServer.getChildren().add(mySqlTreeItemAdministerServerLogs);
		
		mySqlTreeItemAdminister.expandedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					
				Boolean value = ((BooleanProperty)observable).getValue() ;
				System.out.println("Its Administer expansion!!!"); 								

					
				 }
		 });
		
		// System Info
		TreeItem<String> mySqlTreeItemSystemInfo = new TreeItem<String>("System Info");
		TreeItem<String> mySqlTreeItemSystemInfoBinaryLogs = new TreeItem<String>("BINARY LOGS");  // This will show Binary Log EVENTS
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoBinaryLogs);
		TreeItem<String> mySqlTreeItemSystemInfoCharacterSet = new TreeItem<String>("CHARACTER SET");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoCharacterSet);
		TreeItem<String> mySqlTreeItemSystemInfoCollation = new TreeItem<String>("COLLATION");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoCollation);
		TreeItem<String> mySqlTreeItemSystemInfoEngines = new TreeItem<String>("ENGINES");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoEngines);
		TreeItem<String> mySqlTreeItemSystemInfoErrors = new TreeItem<String>("ERRORS");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoErrors);
		TreeItem<String> mySqlTreeItemSystemInfoEvents = new TreeItem<String>("EVENTS");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoEvents);
		//TreeItem<String> mySqlTreeItemSystemInfoGrants = new TreeItem<String>("GRANTS");
		//mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoGrants);
		TreeItem<String> mySqlTreeItemSystemInfoOpenTables = new TreeItem<String>("OPEN TABLES");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoOpenTables);
		TreeItem<String> mySqlTreeItemSystemInfoPlugins = new TreeItem<String>("PLUGINS");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoPlugins);
		TreeItem<String> mySqlTreeItemSystemInfoPreviliges = new TreeItem<String>("PRIVILEGES");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoPreviliges);
		TreeItem<String> mySqlTreeItemSystemInfoProcessList = new TreeItem<String>("PROCESS LIST");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoProcessList);
		TreeItem<String> mySqlTreeItemSystemInfoProfiles = new TreeItem<String>("PROFILES");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoProfiles);
		TreeItem<String> mySqlTreeItemSystemInfoReplicas = new TreeItem<String>("REPLICAS");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoReplicas);
		TreeItem<String> mySqlTreeItemSystemInfoSessionStatus = new TreeItem<String>("SESSION STATUS");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoSessionStatus);
		TreeItem<String> mySqlTreeItemSystemInfoGlobalStatus = new TreeItem<String>("GLOBAL STATUS");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoGlobalStatus);
		TreeItem<String> mySqlTreeItemSystemInfoSessionVariables = new TreeItem<String>("SESSION VARIABLES");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoSessionVariables);
		TreeItem<String> mySqlTreeItemSystemInfoGlobalVariables = new TreeItem<String>("GLOBAL VARIABLES");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoGlobalVariables);
		TreeItem<String> mySqlTreeItemSystemInfoWarnings = new TreeItem<String>("WARNINGS");
		mySqlTreeItemSystemInfo.getChildren().add(mySqlTreeItemSystemInfoWarnings);
		
		
		mySqlTreeItem.getChildren().add(mySqlTreeItemDatabases);
	//	mySqlTreeItem.getChildren().add(mySqlTreeItemUsers);
		mySqlTreeItem.getChildren().add(mySqlTreeItemAdminister);
		mySqlTreeItem.getChildren().add(mySqlTreeItemSystemInfo);
				
		return mySqlTreeItem; 
		
	}
	
	
		
	// This is inner class within the main class to capture when tree elements are double clicked.
	final class MySQLTreecellImpl extends TreeCell<String>{
		
		private Connection innercurrentConnection;
		public MySQLTreecellImpl() {
			 super();
			 for(Entry<ConnectionPlaceHolder,Connection>  entrySet :  menu_Items_FX.mySqlConnectionsMap.entrySet())
			{
					// MySQL##con12
					if(connectionPlaceHolder.getConnectionName().equalsIgnoreCase(entrySet.getKey().getConnectionName()))
					{
						System.out.println("Current Connection is :"+ entrySet.getKey().getConnectionName() + " of type "+entrySet.getValue());
						Connection currentConnection = entrySet.getValue();
						innercurrentConnection = currentConnection;		
						try {
								stmt = currentConnection.createStatement();
						} catch (SQLException e) {
									// TODO Auto-generated catch block
								e.printStackTrace();
						}
					}
			 }
			 setOnMouseClicked(event -> {
				 TreeItem<String> ti = getTreeItem();
				 System.out.println("Current Tree item value is -->" + getTreeItem().getValue());
				 
				 String actionTypes[] = {"BINARY LOGS","Server Logs","CHARACTER SET","COLLATION","ENGINES","ERRORS","EVENTS","OPEN TABLES","PLUGINS","PRIVILEGES","PROCESS LIST","PROFILES","REPLICAS","WARNINGS"};
				 String actionTypeQuery[] = {"SHOW BINARY LOGS", "SHOW BINARY LOGS","SHOW CHARACTER SET","SHOW COLLATION","SHOW ENGINES","SHOW ERRORS","SHOW EVENTS IN mysql","SHOW OPEN TABLES","SHOW PLUGINS","SHOW PRIVILEGES",
						 "SHOW PROCESSLIST","SHOW PROFILES","SHOW REPLICAS","SHOW WARNINGS"};
				
				 if(event.getClickCount() == 2) {
					 for( int i=0;i<actionTypes.length;i++) {
						 
						 if(getTreeItem().getValue().equalsIgnoreCase(actionTypes[i])) {
							 int index = i;
						      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
								Platform.runLater(new Runnable() {
									  @Override
									  public void run() { 
									    try  {
									    	ResultSet rs = stmt.executeQuery(actionTypeQuery[index]);
									    	try {
												Thread.sleep(1000);
											} catch (InterruptedException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
											}
									    	
									    	secondHalfDisplayVBox = new VBox();
											Tab mainDisplayTab = new Tab();
											
											mainDisplayTab.setOnClosed(new EventHandler<Event>() {
												@Override
												public void handle(Event event) {
													System.out.println("On Tab close request ");
													// sessionManagerTab = null;
													menu_Items_FX.alltabbedEditors.getTabs().remove(mainDisplayTab);
												}
											});
											
											Node genericNode = new SplitPane();
									        ((SplitPane) genericNode).setOrientation(Orientation.VERTICAL);
									    	((SplitPane) genericNode).setDividerPositions(0.35);  // split pane divider moving a bit lower
											
											TableView resultAsTableView = new TableView();
											VBox topHalfResultTableView = new VBox();
											if (getTreeItem().getValue().equalsIgnoreCase("BINARY LOGS") || getTreeItem().getValue().equalsIgnoreCase("Server Logs")) {
												resultAsTableView = showResultSetInTheTableView(rs,getTreeItem().getValue());
												topHalfResultTableView.getChildren().addAll(addTopHBoxForInfo(getTreeItem().getValue()),resultAsTableView);
												((SplitPane) genericNode).getItems().addAll(topHalfResultTableView,secondHalfDisplayVBox); // Top half of query editer							      
										    
											}
											else if(getTreeItem().getValue().equalsIgnoreCase("EVENTS")) {
												System.out.println("Show events");
												ResultSet rsDatabases = stmt.executeQuery("SHOW DATABASES");
										    	while(rsDatabases.next()){
										    		System.out.println("Executing query "+"SHOW EVENTS FROM "+rsDatabases.getString(1));
										    		stmt = innercurrentConnection.createStatement();
										    		rs = stmt.executeQuery("SHOW EVENTS FROM "+rsDatabases.getString(1));
										    		resultAsTableView = showResultSetInTheTableView(rs,resultAsTableView);
										    	}
										    	topHalfResultTableView.getChildren().addAll(addTopHBoxForInfo(getTreeItem().getValue()),resultAsTableView);
										    	((SplitPane) genericNode).getItems().addAll(topHalfResultTableView,secondHalfDisplayVBox); // Top half of query editer
											}else {
												resultAsTableView = showResultSetInTheTableView(rs);
												resultAsTableView.setMinHeight(menu_Items_FX.size.getHeight() - 210);
												topHalfResultTableView.getChildren().addAll(addTopHBoxForInfo(getTreeItem().getValue()),resultAsTableView);
												genericNode = new VBox();
												((VBox)genericNode).getChildren().add(topHalfResultTableView);
											}
											
											mainDisplayTab.setText(getTreeItem().getValue() + connectionPlaceHolder.getConnectionName());
											
									        mainDisplayTab.setContent(genericNode);
											menu_Items_FX.alltabbedEditors.getTabs().add(mainDisplayTab);

									        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
									        singleSelectionModel.select(mainDisplayTab);
									        
										} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
										}
									  }
									});
						 }
					 }
					 
				 }
				 
				 String performanceReportsTypes[] = {"Total Memory","Total Memory By Event","Total Memory By User","Total Memory By Host","Total Memory By Thread","Top File I/O Activity Report","Top I/O By File By Time","Top I/O By Event Category"
						 ,"Top I/O In Time By Event Categories","Top I/O Time By Uer/Thread","Analysis","With Errors or Warnings","With Full Table Scans","With Runtimes in 95th Percentile","With Sorting","With Temp Tables",
						 "Auto Increment Columns","Flattened Keys","Index Statistics","Object Overview","Redundant Indexes","Table Lock Waits","Table Statistics","Table Statics with Buffer","Tables With Full Table Scans","Unused Indexes",
						 "Global Waits By Time","Wait By User By Time","Wait By Host By Time","Wait Classes By Time","Wait Classes By Avg Time","Buffer Stats By Schema","Buffer Stats By Table","Lock Waits",
						 "User Summary","User File I/O Summary","User File I/O Type Summary","User Stages Summary","User Statement Time Summary","User Statement Type Summary",
						 "Host Summary","Host File I/O Summary","Host File I/O Type Summary","Host Stages Summary","Host Statement Time Summary","Host Statement Type Summary",
						 "Version","Session Info","Latest File I/O","System Config","Session SSL Status","Metrics","Process List","Check Lost Instrumentation"};
				 
				 String performanceReportQueries[] = {"x$memory_global_total","x$memory_global_by_current_bytes","x$memory_by_user_by_current_bytes","x$memory_by_host_by_current_bytes","x$memory_by_thread_by_current_bytes","x$io_global_by_file_by_bytes","x$io_global_by_file_by_latency",
						 "x$io_global_by_wait_by_bytes","x$io_global_by_wait_by_latency","x$io_by_thread_by_latency","x$statement_analysis","statements_with_errors_or_warnings","statements_with_full_table_scans",
						 "x$statements_with_runtimes_in_95th_percentile","statements_with_sorting","statements_with_temp_tables","schema_auto_increment_columns","x$schema_flattened_keys","x$schema_index_statistics","schema_object_overview","schema_redundant_indexes","x$schema_table_lock_waits","x$schema_table_statistics","x$schema_table_statistics_with_buffer","x$schema_tables_with_full_table_scans","schema_unused_indexes",
						 "x$waits_global_by_latency","x$waits_by_user_by_latency"," x$waits_by_host_by_latency","x$wait_classes_global_by_latency","x$wait_classes_global_by_avg_latency","x$innodb_buffer_stats_by_schema","x$innodb_buffer_stats_by_table","x$innodb_lock_waits",
						 "x$user_summary","x$user_summary_by_file_io","x$user_summary_by_file_io_type","x$user_summary_by_stages","x$user_summary_by_statement_latency","x$user_summary_by_statement_type",
						 "x$host_summary","x$host_summary_by_file_io","x$host_summary_by_file_io_type","x$host_summary_by_stages","x$host_summary_by_statement_latency","x$host_summary_by_statement_type",
						 "version","x$session","x$latest_file_io","sys_config","session_ssl_status","metrics","processlist","ps_check_lost_instrumentation"};
				 
				 if(event.getClickCount() == 1) {
					 for( int i=0;i<performanceReportsTypes.length;i++) {
						 if(getTreeItem().getValue().equalsIgnoreCase(performanceReportsTypes[i])) {
							 int index = i;
						      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
								Platform.runLater(new Runnable() {
									  @Override
									  public void run() { 
									    try  {
									    	
									    	ResultSet rs = stmt.executeQuery("Select * from sys."+performanceReportQueries[index]);
									    	
									    	String connectionName = connectionPlaceHolder.getConnectionName();
									    	
									    	System.out.println("Connection Name :"+ connectionName);
									    	particularPerformanceReportLabel.setText(performanceReportsTypes[index]);
									    	performanceReportTableView.getColumns().clear();
									    	performanceReportTableView.getItems().clear();
											showResultSetInTheTableView(rs,performanceReportTableView);
											 
										} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
										}
									  }
									});
						 }
					 }
					 
				 }
				 /*
				 if(event.getClickCount() == 2 && (getTreeItem().getValue().equalsIgnoreCase("BINARY LOGS") || getTreeItem().getValue().equalsIgnoreCase("Server Logs"))) {   
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {
							    	ResultSet rs = stmt.executeQuery("SHOW BINARY LOGS");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
							    	
							    	secondHalfDisplayVBox = new VBox();
									Tab sessionManagerTab = new Tab();
									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView = showResultSetInTheTableView(rs,"binaryLogs");
									VBox binaryLogsDescriptionTableView = new VBox();
									if (getTreeItem().getValue().equalsIgnoreCase("BINARY LOGS")) {
										sessionManagerTab.setText("BINARY LOGS " + connectionPlaceHolder.getConnectionName());
										binaryLogsDescriptionTableView.getChildren().addAll(addTopHBoxForBinaryLogs("Binary"),resultAsTableView);
									}
									else if (getTreeItem().getValue().equalsIgnoreCase("Server Logs")) {
										sessionManagerTab.setText("Server Logs " + connectionPlaceHolder.getConnectionName());
										binaryLogsDescriptionTableView.getChildren().addAll(addTopHBoxForBinaryLogs("Server"),resultAsTableView);
									}
									
									
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.37);  // split pane divider moving a bit lower
							    
							        editerTabSplitPane.getItems().addAll(binaryLogsDescriptionTableView); // Top half of query editer
							      
							        editerTabSplitPane.getItems().add(secondHalfDisplayVBox); // bottom half of query editer
							        
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 } *
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("CHARACTER SET")) {   
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {
							    	ResultSet rs = stmt.executeQuery("SHOW CHARACTER SET");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}

									Tab sessionManagerTab = new Tab("CHARACTER SET " + connectionPlaceHolder.getConnectionName());
									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }/*
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("COLLATION")) {   
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {
							    	ResultSet rs = stmt.executeQuery("SHOW COLLATION");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}

									Tab sessionManagerTab = new Tab("COLLATION " + connectionPlaceHolder.getConnectionName());
									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("ENGINES")) {   
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {
							    	ResultSet rs = stmt.executeQuery("SHOW ENGINES");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("ENGINES " + connectionPlaceHolder.getConnectionName());
									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});						
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }
				 // This can be mage a generic code think about it.
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("ERRORS")) {   
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {
							    	ResultSet rs = stmt.executeQuery("SHOW ERRORS");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("ERRORS " + connectionPlaceHolder.getConnectionName());
									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});						
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("EVENTS")) {   
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {
							    	TableView resultAsTableView = new TableView();
							    	ResultSet rsDatabases = stmt.executeQuery("SHOW DATABASES");
							    	while(rsDatabases.next()){
							    		System.out.println("Executing query "+"SHOW EVENTS FROM "+rsDatabases.getString(1));
							    		stmt = innercurrentConnection.createStatement();
							    		ResultSet rs = stmt.executeQuery("SHOW EVENTS FROM "+rsDatabases.getString(1));
							    		resultAsTableView = showResultSetInTheTableView(rs,resultAsTableView);
							    	}
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("EVENTS " + connectionPlaceHolder.getConnectionName());
									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});						
								
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }
				 
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("GRANTS")) {   
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {
							    	TableView resultAsTableView = new TableView();
							    	ResultSet rsUserDetails = stmt.executeQuery("SELECT USER,HOST FROM MYSQL.USER");
							    	while(rsUserDetails.next()){
							    		System.out.println("Executing query "+"SHOW GRANTS FOR '"+rsUserDetails.getString(1)+"'@'"+rsUserDetails.getString(2)+"'");
							    		stmt = innercurrentConnection.createStatement();
							    		ResultSet rs = stmt.executeQuery("SHOW GRANTS FOR '"+rsUserDetails.getString(1)+"'@'"+rsUserDetails.getString(2)+"'");
							    		resultAsTableView = showResultSetInTheTableView(rs,resultAsTableView);
							    	}
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("GRANTS " + connectionPlaceHolder.getConnectionName());
									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});						
								
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("OPEN TABLES")) {   
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {
							    	ResultSet rs = stmt.executeQuery("SHOW OPEN TABLES");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("OPEN TABLES " + connectionPlaceHolder.getConnectionName());
									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});						
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("PLUGINS")) {   
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {
							    	ResultSet rs = stmt.executeQuery("SHOW PLUGINS");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("PLUGINS " + connectionPlaceHolder.getConnectionName());
									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});						
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 } 
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("PRIVILEGES")) {   
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {
							    	ResultSet rs = stmt.executeQuery("SHOW PRIVILEGES");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("PRIVILEGES " + connectionPlaceHolder.getConnectionName());
									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});						
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("PROCESS LIST")) {   
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
							    	ResultSet rs = stmt.executeQuery(" SHOW PROCESSLIST");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("Session/Process List " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }   
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("PROFILES")) { // display individual profile  
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
							    	ResultSet rs = stmt.executeQuery(" SHOW PROFILES");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("PROFILES " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }  
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("REPLICAS")) { // display individual replica
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
							    	ResultSet rs = stmt.executeQuery(" SHOW REPLICAS");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("REPLICAS " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }   
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("SESSION STATUS")) { 
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
							    	ResultSet rs = stmt.executeQuery(" SHOW SESSION STATUS");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("SESSION " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 } 
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("GLOBAL STATUS")) { 
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
							    	ResultSet rs = stmt.executeQuery(" SHOW GLOBAL STATUS");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("GLOBAL " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 } 
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("SESSION VARIABLES")) { 
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
							    	ResultSet rs = stmt.executeQuery(" SHOW SESSION VARIABLES");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("SESSION VARIABLES " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("GLOBAL VARIABLES")) { 
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
							    	ResultSet rs = stmt.executeQuery(" SHOW GLOBAL VARIABLES");
							    	try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
									}
									Tab sessionManagerTab = new Tab("GLOBAL VARIABLES " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView = showResultSetInTheTableView(rs);
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.67);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(resultAsTableView); // Top half of query editer
							        editerTabSplitPane.getItems().add(new HBox()); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }*/
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("Dashboard")) { 
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
							    	
									Tab sessionManagerTab = new Tab("Dashboard " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									PieChart pieChart = new PieChart();

								    PieChart.Data slice1 = new PieChart.Data("Desktop", 213);
								    PieChart.Data slice2 = new PieChart.Data("Phone"  , 67);
								    PieChart.Data slice3 = new PieChart.Data("Tablet" , 36);

								    pieChart.getData().add(slice1);
								    pieChart.getData().add(slice2);
								    pieChart.getData().add(slice3);
								    
								    
								    
								    NumberAxis xAxis = new NumberAxis();
							        xAxis.setLabel("No of employees");

							        NumberAxis yAxis = new NumberAxis();
							        yAxis.setLabel("Revenue per employee");

							        LineChart lineChart = new LineChart(xAxis, yAxis);

							        XYChart.Series dataSeries1 = new XYChart.Series();
							        dataSeries1.setName("2014");

							        dataSeries1.getData().add(new XYChart.Data( 1, 567));
							        dataSeries1.getData().add(new XYChart.Data( 5, 612));
							        dataSeries1.getData().add(new XYChart.Data(10, 800));
							        dataSeries1.getData().add(new XYChart.Data(20, 480));
							        dataSeries1.getData().add(new XYChart.Data(40, 810));
							        dataSeries1.getData().add(new XYChart.Data(60, 110));
							        dataSeries1.getData().add(new XYChart.Data(80, 850));

							        lineChart.getData().add(dataSeries1);
							        
							        CategoryAxis xAxis1    = new CategoryAxis();
							        xAxis.setLabel("Devices");

							        NumberAxis yAxis1 = new NumberAxis();
							        yAxis.setLabel("Visits");

							        BarChart     barChart = new BarChart(xAxis1, yAxis1);

							        XYChart.Series dataSeries2 = new XYChart.Series();
							        dataSeries1.setName("2014");

							        dataSeries2.getData().add(new XYChart.Data("Desktop", 567));
							        dataSeries2.getData().add(new XYChart.Data("Phone"  , 65));
							        dataSeries2.getData().add(new XYChart.Data("Tablet"  , 23));

							        barChart.getData().add(dataSeries2);
							        
								    VBox piechartvbox = new VBox();
								    piechartvbox.setSpacing(10);
								    piechartvbox.getChildren().addAll(pieChart,lineChart);
								    
								    sessionManagerTab.setContent(piechartvbox);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 } 				 
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("Performance Reports")) { 
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
							    	
							    	String connectionName = connectionPlaceHolder.getConnectionName();
							    	
							    	System.out.println("Connection Name :"+ connectionName);
							    	
									Tab sessionManagerTab = new Tab("Users and Privileges " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TreeView<String> performanceView = generatePerformnaceReports();
									
									// TableView resultAsTableView =  showResultSetInTheTableView(rsUsers);
									// resultAsTableView.setMinHeight(menu_Items_FX.size.getHeight() - 50);
									
									BorderPane performanceReportsBorderPane = new BorderPane();
									VBox vboxLeft = new VBox();
									vboxLeft.setPadding(new Insets(5,5,5,5));
									vboxLeft.setSpacing(5);
									Label performanceReportsLabel = new Label("Reports");
									//userAccountsLabel.setTextFill(Color.BLUEVIOLET);
									vboxLeft.getChildren().add(performanceReportsLabel);
									vboxLeft.getChildren().add(performanceView);
									
									VBox vBoxCenterTop = new VBox();
									vBoxCenterTop.setPadding(new Insets(5,5,5,5));
									vBoxCenterTop.setSpacing(5);
									particularPerformanceReportLabel =  new Label("Nothing Selected");
									//Label particularPerformanceReportDescription =  new Label("Shows total memory allowed");
									//detailForAccountLabel.setTextFill(Color.BLUEVIOLET);
									
									
								    performanceReportTableView = new TableView();
									performanceReportTableView.setMinHeight(menu_Items_FX.size.getHeight() - 250);
									
									
									vBoxCenterTop.getChildren().addAll(particularPerformanceReportLabel,performanceReportTableView);
									
									performanceReportsBorderPane.setTop(addTopHBoxForInfo("Performance Reports"));
									performanceReportsBorderPane.setLeft(vboxLeft);
									performanceReportsBorderPane.setCenter(vBoxCenterTop);
									
									sessionManagerTab.setContent(performanceReportsBorderPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }

							});		
				 }
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("Server Status")) { 
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {
							    	
							    	HashMap<String,String> allVariables = new HashMap<String,String>(); 
							    	ResultSet rsVariables = stmt.executeQuery(" SHOW VARIABLES");
							    	while(rsVariables.next()) {
							    		System.out.println(rsVariables.getString(1)+ " " +rsVariables.getString(2));
							    		allVariables.put(rsVariables.getString(1), rsVariables.getString(2));
							    	}
							    	
							    	HashMap<String,String> allStatus = new HashMap<String,String>();
							    	stmt = innercurrentConnection.createStatement();
							    	ResultSet rsStatus = stmt.executeQuery(" SHOW STATUS");
							    	while(rsStatus.next()) {
							    		System.out.println(rsStatus.getString(1)+ " " +rsStatus.getString(2));
							    		allStatus.put(rsStatus.getString(1), rsStatus.getString(2));
							    	}
							    	
							    	HashMap<String,String> allPlugins = new HashMap<String,String>();
							    	stmt = innercurrentConnection.createStatement();
							    	ResultSet rsPlugins = stmt.executeQuery(" SHOW PLUGINS");
							    	while(rsPlugins.next()) {
							    		System.out.println(rsPlugins.getString(1)+ " " +rsPlugins.getString(2));
							    		allPlugins.put(rsPlugins.getString(1), rsPlugins.getString(2));
							    	}
							    	
							    	String connectionName = connectionPlaceHolder.getConnectionName();
							    	
							    	
									Tab sessionManagerTab = new Tab("SERVER STATUS " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									
									VBox serverStatusVBox = addServerStatus(allVariables,allStatus,allPlugins);
									
									sessionManagerTab.setContent(serverStatusVBox);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});
				 }
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("Client Connections")) { 
				      System.out.println("Double clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
							    	
							    	HashMap<String,String> allVariables = new HashMap<String,String>(); 
							    	ResultSet rsStatusVariables = stmt.executeQuery(" SHOW GLOBAL STATUS");
							    	while(rsStatusVariables.next()) {
							    		allVariables.put(rsStatusVariables.getString(1), rsStatusVariables.getString(2));
							    	}
							    	ResultSet rsGlobalVariables = innercurrentConnection.createStatement().executeQuery(" SHOW GLOBAL VARIABLES");
							    	while(rsGlobalVariables.next()) {
							    		allVariables.put(rsGlobalVariables.getString(1), rsGlobalVariables.getString(2));
							    	}
							    
							    	String connectionName = connectionPlaceHolder.getConnectionName();
							    	
							    	HashMap<String,String> allStatus = new HashMap<String,String>();
							    	stmt = innercurrentConnection.createStatement();
							    	ResultSet rsThreads = stmt.executeQuery("SELECT COALESCE(th.PROCESSLIST_ID,0) as Id,COALESCE(th.PROCESSLIST_USER,'None') as User,COALESCE(th.PROCESSLIST_HOST,'None') as Host,COALESCE(th.PROCESSLIST_DB,'None') as DB,COALESCE(th.PROCESSLIST_COMMAND,'None') as Command,COALESCE(th.PROCESSLIST_TIME,0) as Time,COALESCE(th.PROCESSLIST_STATE,'None') as State,th.THREAD_ID as Thread,th.TYPE as Type,th.NAME as Name,COALESCE(th.PARENT_THREAD_ID,0) as ParentThread,th.INSTRUMENTED as Instrumented,COALESCE(th.PROCESSLIST_INFO,'None') as Info,"
							    			+ "COALESCE(attr.ATTR_VALUE,'None') as Program FROM performance_schema.threads th  LEFT OUTER JOIN performance_schema.session_connect_attrs attr ON th.processlist_id = attr.processlist_id AND (attr.attr_name IS NULL OR attr.attr_name = 'program_name') WHERE th.TYPE <> 'BACKGROUND' ");  // 
							    	
									Tab sessionManagerTab = new Tab("Client Connections " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView =  showResultSetInTheTableView(rsThreads);
									
									VBox clientConnectionsVBox = addclientConnectionThreadDetails(allVariables,resultAsTableView);
									
									
									sessionManagerTab.setContent(clientConnectionsVBox);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }

						
							});		
				 }
				 if(event.getClickCount() == 2 && getTreeItem().getValue().equalsIgnoreCase("Users and Privileges")) { 
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
							    	
							    	ResultSet rsUsers = stmt.executeQuery("select User,Host from mysql.user");
							    
							    	String connectionName = connectionPlaceHolder.getConnectionName();
							    	
							    	System.out.println("Connection Name :"+ connectionName);
							    	
									Tab sessionManagerTab = new Tab("Users and Privileges " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									TableView resultAsTableView =  showResultSetInTheTableView(rsUsers);
									BorderPane userAccountsBorderPane = new BorderPane();
									VBox vboxLeft = new VBox();
									vboxLeft.setPadding(new Insets(5,5,5,5));
									vboxLeft.setSpacing(5);
									Label userAccountsLabel = new Label("User Accounts");
									//userAccountsLabel.setTextFill(Color.BLUEVIOLET);
									vboxLeft.getChildren().add(userAccountsLabel);
									vboxLeft.getChildren().add(resultAsTableView);
									
									HBox hBox = new HBox();
									hBox.setPadding(new Insets(5,5,5,5));
									Button addAccountButton = new Button("Add Account");
									addAccountButton.setMinWidth(80);
									Button deletetButton = new Button("Delete");
									deletetButton.setMinWidth(80);
									Button refreshButton = new Button("Refresh");
									refreshButton.setMinWidth(80);
									hBox.getChildren().add(addAccountButton);
									hBox.getChildren().add(deletetButton);
									hBox.getChildren().add(refreshButton);
									hBox.setSpacing(20);
									vboxLeft.getChildren().add(hBox);
								
									VBox vBoxCenterTop = new VBox();
									vBoxCenterTop.setPadding(new Insets(5,5,5,5));
									vBoxCenterTop.setSpacing(5);
									Label detailForAccountLabel =  new Label("Details for Account - xxxx");
									//detailForAccountLabel.setTextFill(Color.BLUEVIOLET);
									vBoxCenterTop.getChildren().add(detailForAccountLabel);
									
									TabPane accountDetailsTabs = new TabPane();
									accountDetailsTabs.getStyleClass().addAll("databasesflowPane");  // box for the connection tabbed pane
									Tab loginTab = new Tab("Login");
									loginTab.setClosable(false);  
									loginTab.setContent(addAccountLoginCredentials()); // will set fields to connectionDetailsTab
									Tab accountLimitsTab = new Tab("Account Limits");
									accountLimitsTab.setClosable(false);	
									accountLimitsTab.setContent(addAccountLimits());
									Tab accountPrivilegesTab = new Tab("Account Privileges");
									accountPrivilegesTab.setClosable(false);
									accountPrivilegesTab.setContent(addAccountPrivileges());
									Tab schemaPrivilegesTab = new Tab("Schema Privileges");
									schemaPrivilegesTab.setClosable(false);
									schemaPrivilegesTab.setContent(addSchemaPrivileges());
									
									accountDetailsTabs.getTabs().addAll(loginTab,accountLimitsTab,accountPrivilegesTab,schemaPrivilegesTab);
									vBoxCenterTop.getChildren().add(accountDetailsTabs);
									
									userAccountsBorderPane.setTop(addTopHBoxForInfo("Users and Privileges"));
									userAccountsBorderPane.setLeft(vboxLeft);
									userAccountsBorderPane.setCenter(vBoxCenterTop);
									
									sessionManagerTab.setContent(userAccountsBorderPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }
				 if(event.getClickCount() == 2 && (getTreeItem().getValue().equalsIgnoreCase("Status and System Variables") || getTreeItem().getValue().equalsIgnoreCase("SESSION STATUS")
						 || getTreeItem().getValue().equalsIgnoreCase("GLOBAL STATUS") || getTreeItem().getValue().equalsIgnoreCase("SESSION VARIABLES") || getTreeItem().getValue().equalsIgnoreCase("GLOBAL VARIABLES"))) { 
				      System.out.println("Duble clicked on this item"+ getTreeItem().getValue());
						Platform.runLater(new Runnable() {
							  @Override
							  public void run() { 
							    try  {	
		
							    	String connectionName = connectionPlaceHolder.getConnectionName();
							    	System.out.println("Connection Name :"+ connectionName);
							    	
							    	BorderPane mainPopUpborderPane = new BorderPane();
									HBox topHbox = addTopHBoxForInfo("Server Variables");
									TabPane centerTabPane = addCenterTabbedPaneForVariables();
									HBox bottomHbox = addBottomHBoxForVariables();
									mainPopUpborderPane.setTop(topHbox);
									mainPopUpborderPane.setCenter(centerTabPane);;
								//	mainPopUpborderPane.setBottom(bottomHbox);
									
							    	
									Tab sessionManagerTab = new Tab("Status and System Variables " + connectionPlaceHolder.getConnectionName());									
									sessionManagerTab.setOnClosed(new EventHandler<Event>() {
										@Override
										public void handle(Event event) {
											System.out.println("On Tab close request ");
											// sessionManagerTab = null;
											menu_Items_FX.alltabbedEditors.getTabs().remove(sessionManagerTab);
										}
									});
									
									SplitPane editerTabSplitPane = new SplitPane();
							        editerTabSplitPane.setOrientation(Orientation.VERTICAL);
							    	editerTabSplitPane.setDividerPositions(0.70);  // split pane divider moving a bit lower
							    	
							        editerTabSplitPane.getItems().add(mainPopUpborderPane); // Top half of query editer
							        variablesSecondHalfDisplayVBox = new VBox();
							        editerTabSplitPane.getItems().add(variablesSecondHalfDisplayVBox); // bottom half of query editer
							        
									sessionManagerTab.setContent(editerTabSplitPane);
									menu_Items_FX.alltabbedEditors.getTabs().add(sessionManagerTab);

							        SingleSelectionModel<Tab> singleSelectionModel =  menu_Items_FX.alltabbedEditors.getSelectionModel();
							        singleSelectionModel.select(sessionManagerTab);
							        
								} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
								}
							  }
							});		
				 }
				 

				 // -----------------------END------------------
			 });
		}
		
		@Override
		protected void updateItem(String item, boolean empty) {
		    super.updateItem(item, empty);

		    if (item == null || empty) {
		            setText(null);
		    } else {
		          setText(item);
		    }
		 }
		   
		   // reference https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/tree-view.htm
	}
	
	private TableView showResultSetInTheTableView(ResultSet rs)  throws SQLException{
			
		TableView tableView = new TableView();
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);  // to remove the last empty column otherwise added
		tableView.setEditable(true);
        
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HashMap<String,String>>() {

			@Override
			public void changed(ObservableValue<? extends HashMap<String, String>> observable,
					HashMap<String, String> oldValue, HashMap<String, String> newValue) {

				System.out.println("oldValue --->"+oldValue);
				System.out.println("newValue --->"+newValue.keySet().toString());
				for(Map.Entry<String, String> tableValues : newValue.entrySet()) {
					
					System.out.println( tableValues.getKey()+ " "+ tableValues.getValue());
				}
				
				TableViewSelectionModel  selectionModel = tableView.getSelectionModel();
		        ObservableList selectedCells = selectionModel.getSelectedCells();
		        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		        Object val = tablePosition.getTableColumn().getCellData(newValue);
		        System.out.println("Selected Value" + val);
				
			}	
		});

		if(rs.next()) {
	
	    	System.out.println("First calumne "+rs.getString(1));
	    	TableColumn<Map, String> tableColumnName;
	    	Map<String, Object> tableRowValue;
	    	ObservableList<Map<String, Object>> items = FXCollections.<Map<String, Object>>observableArrayList();
	    	
			ResultSetMetaData md = rs.getMetaData();
	        String[] columnNames = new String[md.getColumnCount()];
	        Integer[] columnTypes = new Integer[md.getColumnCount()];
	       
	        
	        for (int i = 0; i < columnNames.length; i++) {
	        	columnNames[i] = md.getColumnLabel(i+1);
	        	System.out.println("Column Name : "+columnNames[i]);
	        	columnTypes[i] =  md.getColumnType(i+1);	   
	        	
	        	tableColumnName = new TableColumn<>(columnNames[i]);
	        	tableColumnName.setMinWidth(150);
	        	tableColumnName.setCellValueFactory(new MapValueFactory<>(columnNames[i]));
	        	// Below code will do cell editing
	        	tableColumnName.setCellFactory( new Callback<TableColumn<Map,String>, TableCell<Map,String>>() {
					@Override
					public TableCell<Map, String> call(TableColumn<Map, String> param) {
						 return new EditingCell();
					}
				});
	        	
	        
	        	tableColumnName.setOnEditCommit( new EventHandler<TableColumn.CellEditEvent<Map,String>>() {		
					@Override
					public void handle(CellEditEvent<Map, String> t) {
						System.out.println("Editing firstName..");
	                	System.out.println("Table View "+ t.getTableView().getItems().get(t.getTablePosition().getRow()));
	                	 t.getTableView().getItems().get(t.getTablePosition().getRow()).replace(t.getTableColumn(), t.getTableView().getItems().get(t.getTablePosition().getRow()).get(t.getTableColumn()));
	                	System.out.println("Table Position"+t.getTablePosition().getRow());
	                	System.out.println("Table Column"+t.getTableColumn());
					}
				});
	        	
	 	        tableView.getColumns().add(tableColumnName);
	        }		
		
	       do {
				  
	        	String[] columnValues = new String[md.getColumnCount()];
	        	tableRowValue = new HashMap<>();
	        	for (int i = 0; i < columnNames.length; i++) {
	        		columnValues[i] =  rs.getString(i+1); 
	        		
	            	tableRowValue.put(columnNames[i], columnValues[i]);
	    	        
	        	}	
	        	items.add(tableRowValue);
	         }	 while (rs.next());

	        
	        tableView.getItems().addAll(items);
		}
		
		return tableView;
}

	private TableView showResultSetInTheTableView(ResultSet rs,TableView tableView)  throws SQLException{
		
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);  // to remove the last empty column otherwise added
		tableView.setEditable(true);
	
        
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HashMap<String,String>>() {

			@Override
			public void changed(ObservableValue<? extends HashMap<String, String>> observable,
					HashMap<String, String> oldValue, HashMap<String, String> newValue) {

				System.out.println("oldValue --->"+oldValue);
				System.out.println("newValue --->"+newValue.keySet().toString());
				for(Map.Entry<String, String> tableValues : newValue.entrySet()) {
					
					System.out.println( tableValues.getKey()+ " "+ tableValues.getValue());
				}
				
				TableViewSelectionModel  selectionModel = tableView.getSelectionModel();
		        ObservableList selectedCells = selectionModel.getSelectedCells();
		        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		        Object val = tablePosition.getTableColumn().getCellData(newValue);
		        System.out.println("Selected Value" + val);
				
			}	
		});
		
		if(rs.next()) {
	
	    	TableColumn<Map, String> tableColumnName;
	    	Map<String, Object> tableRowValue;
	    	ObservableList<Map<String, Object>> items = FXCollections.<Map<String, Object>>observableArrayList();
	    	
			ResultSetMetaData md = rs.getMetaData();
	        String[] columnNames = new String[md.getColumnCount()];
	        Integer[] columnTypes = new Integer[md.getColumnCount()];
	       
	        for (int i = 0; i < columnNames.length; i++) {
	        	columnNames[i] = md.getColumnLabel(i+1);
	        	columnTypes[i] =  md.getColumnType(i+1);	   
	        	
	        	tableColumnName = new TableColumn<>(columnNames[i]);
	        	tableColumnName.setMinWidth(150);
	        	tableColumnName.setCellValueFactory(new MapValueFactory<>(columnNames[i]));
	        	// Below code will do cell editing
	        	tableColumnName.setCellFactory( new Callback<TableColumn<Map,String>, TableCell<Map,String>>() {
					@Override
					public TableCell<Map, String> call(TableColumn<Map, String> param) {
						 return new EditingCell();
					}
				});
	        	
	        
	        	tableColumnName.setOnEditCommit( new EventHandler<TableColumn.CellEditEvent<Map,String>>() {		
					@Override
					public void handle(CellEditEvent<Map, String> t) {
						System.out.println("Editing firstName..");
	                	System.out.println("Table View "+ t.getTableView().getItems().get(t.getTablePosition().getRow()));
	                	 t.getTableView().getItems().get(t.getTablePosition().getRow()).replace(t.getTableColumn(), t.getTableView().getItems().get(t.getTablePosition().getRow()).get(t.getTableColumn()));
	                	System.out.println("Table Position"+t.getTablePosition().getRow());
	                	System.out.println("Table Column"+t.getTableColumn());
					}
				}); 
	 	        tableView.getColumns().add(tableColumnName);
	        }		
		
	       do {
				  
	        	String[] columnValues = new String[md.getColumnCount()];
	        	tableRowValue = new HashMap<>();
	        	for (int i = 0; i < columnNames.length; i++) {
	        		columnValues[i] =  rs.getString(i+1); 
	        		
	            	tableRowValue.put(columnNames[i], columnValues[i]);
	    	        
	        	}	
	        	items.add(tableRowValue);
	         }	 while (rs.next());

	        
	        tableView.getItems().addAll(items);
	        
		}
		
		return tableView;
	}
	
	private TableView showResultSetInTheTableView(ResultSet rs,String inputParam)  throws SQLException{
		
		TableView tableView = new TableView();
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);  // to remove the last empty column otherwise added
		tableView.setEditable(true);
	
        
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HashMap<String,String>>() {

			@Override
			public void changed(ObservableValue<? extends HashMap<String, String>> observable,
					HashMap<String, String> oldValue, HashMap<String, String> newValue) {

			
				System.out.println("oldValue --->"+oldValue);
				System.out.println("newValue --->"+newValue.keySet().toString());
				
				// Do the alignment here , along with is Editable entry by doing look up to MySQLConstants enum
				if(inputParam.equalsIgnoreCase("Status") || inputParam.equalsIgnoreCase("Variables")) {
					variablesSecondHalfDisplayVBox.getChildren().clear();
					for(Map.Entry<String, String> tableValues : newValue.entrySet()) {
						System.out.println( tableValues.getKey()+ " "+ tableValues.getValue());
					
						variablesSecondHalfDisplayVBox.getChildren().add(new Label(tableValues.getKey()+ " "+ tableValues.getValue()));
					}
				}
				if(inputParam.equalsIgnoreCase("BINARY LOGS") || inputParam.equalsIgnoreCase("Server Logs")) {
					secondHalfDisplayVBox.getChildren().clear();
					for(Map.Entry<String, String> tableValues : newValue.entrySet()) {
						System.out.println( tableValues.getKey()+ " "+ tableValues.getValue());
					
						if("Log_name".equalsIgnoreCase(tableValues.getKey()))
							try {
								ResultSet resultSetbinlogEvents =   stmt.executeQuery("SHOW BINLOG EVENTS IN '"+tableValues.getValue()+"'");
								TableView tableView =  showResultSetInTheTableView(resultSetbinlogEvents);
								secondHalfDisplayVBox.getChildren().add(tableView);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
				
				TableViewSelectionModel  selectionModel = tableView.getSelectionModel();
		        ObservableList selectedCells = selectionModel.getSelectedCells();
		        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		        Object val = tablePosition.getTableColumn().getCellData(newValue);
		        System.out.println("Selected Value" + val);
			}	
		});
		if(rs.next()) {
	
	    	TableColumn<Map, String> tableColumnName = null;
	    	Map<String, Object> tableRowValue;
	    	ObservableList<Map<String, Object>> items = FXCollections.<Map<String, Object>>observableArrayList();
	    	
			ResultSetMetaData md = rs.getMetaData();
	        String[] columnNames = new String[md.getColumnCount()];
	        Integer[] columnTypes = new Integer[md.getColumnCount()];
	       
	        for (int i = 0; i < columnNames.length; i++) {
	        	columnNames[i] = md.getColumnLabel(i+1);
	        	columnTypes[i] =  md.getColumnType(i+1);	   
	        	
	        	tableColumnName = new TableColumn<>(columnNames[i]);
	        	tableColumnName.setMinWidth(150);
	        	tableColumnName.setCellValueFactory(new MapValueFactory<>(columnNames[i]));
	        	// Below code will do cell editing
	        	tableColumnName.setCellFactory( new Callback<TableColumn<Map,String>, TableCell<Map,String>>() {
					@Override
					public TableCell<Map, String> call(TableColumn<Map, String> param) {
						 return new EditingCell();
					}
				});
	        		        
	        	tableColumnName.setOnEditCommit( new EventHandler<TableColumn.CellEditEvent<Map,String>>() {		
					@Override
					public void handle(CellEditEvent<Map, String> t) {
						System.out.println("Editing firstName..");
	                	System.out.println("Table View "+ t.getTableView().getItems().get(t.getTablePosition().getRow()));
	                	 t.getTableView().getItems().get(t.getTablePosition().getRow()).replace(t.getTableColumn(), t.getTableView().getItems().get(t.getTablePosition().getRow()).get(t.getTableColumn()));
	                	System.out.println("Table Position"+t.getTablePosition().getRow());
	                	System.out.println("Table Column"+t.getTableColumn());
					}
				});
	 	        tableView.getColumns().add(tableColumnName);
	        }	
	        if(inputParam.equalsIgnoreCase("Status") || inputParam.equalsIgnoreCase("Variables")) {
	        	tableColumnName = new TableColumn<>("Description");
	        	tableColumnName.setCellValueFactory(new MapValueFactory<>("Description"));
	        	tableView.getColumns().add(tableColumnName);
	        }
        	// Below code will do cell editing
        	tableColumnName.setCellFactory( new Callback<TableColumn<Map,String>, TableCell<Map,String>>() {
				@Override
				public TableCell<Map, String> call(TableColumn<Map, String> param) {
					 return new EditingCell();
				}
			});    
        	tableColumnName.setOnEditCommit( new EventHandler<TableColumn.CellEditEvent<Map,String>>() {		
				@Override
				public void handle(CellEditEvent<Map, String> t) {
					System.out.println("Editing firstName..");
                	System.out.println("Table View "+ t.getTableView().getItems().get(t.getTablePosition().getRow()));
                	 t.getTableView().getItems().get(t.getTablePosition().getRow()).replace(t.getTableColumn(), t.getTableView().getItems().get(t.getTablePosition().getRow()).get(t.getTableColumn()));
                	System.out.println("Table Position"+t.getTablePosition().getRow());
                	System.out.println("Table Column"+t.getTableColumn());
				}
			});
	       
		
	       do {
				  
	        	String[] columnValues = new String[md.getColumnCount()];
	        	tableRowValue = new HashMap<>();
	        	for (int i = 0; i < columnNames.length; i++) {
	        		columnValues[i] =  rs.getString(i+1); 
	        		
	            	tableRowValue.put(columnNames[i], columnValues[i]);
	    	        
	        	}	
	        	if(inputParam.equalsIgnoreCase("Status") || inputParam.equalsIgnoreCase("Variables")) {
	        	// look-up value . We are using try catch because if enum doesn't have a value it will throw exception 
	        		try {
	        			tableRowValue.put("Description",MySQLConstants.valueOf(rs.getString(1)).getDescription());
	        		}catch(Exception e) {
	        			System.out.print("Enum Not found for-->"+rs.getString(1));
	        		}
	        	}
	        	items.add(tableRowValue);
	         }	 while (rs.next());

	        
	        tableView.getItems().addAll(items);
		}
		
		return tableView;
	}

	private HBox addBottomHBoxForVariables() {

			 
	        HBox hbox = new HBox();
//	        hbox.setPadding(new Insets(15, 12, 15, 12));
//	        hbox.setSpacing(10);   // Gap between nodes
//	        hbox.setStyle("-fx-background-color: #334000;");
	// Use style class to set properties previously set above (with some changes)      
	        //hbox.getStyleClass().add("hbox");
	        
	        hbox.getStyleClass().add("hbox");

	        
	        return hbox;
	 }
	  
	 
	  
	  
	  private TabPane addCenterTabbedPaneForVariables() {
			 
		 statusSystemVariablesTabpane = new TabPane();  
		  statusSystemVariablesTabpane.setTabMinWidth(250);
		  statusSystemVariablesTabpane.setTabMinHeight(20);
		
		  statusVariablesTab = new Tab("Status Variables");
		  statusVariablesTab.setClosable(false);
		  statusVariablesTab.setContent(getStatusVariables());
	  
		 
		  systemVariablesTab = new Tab("System Variables");
		  systemVariablesTab.setClosable(false);		  
		  systemVariablesTab.setContent(getSystemVariables()); 
		  
		  		  
		  statusSystemVariablesTabpane.getTabs().addAll(statusVariablesTab,systemVariablesTab);
			
		  statusSystemVariablesTabpane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				@Override
				public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
					 // System.out.println("Old Tab Selected -->"+oldValue.getText());
					  System.out.println("New Tab Selected -->"+newValue.getText());
					  
					  if(newValue.getText().equalsIgnoreCase("Global System Variables")) {
						  globalStatusVariablesTab.setContent(getVariablesData("GlobalSystem"));
					  }
					  if(newValue.getText().equalsIgnoreCase("Session System Variables")) {
						  sessionStatusVariablesTab.setContent(getVariablesData("SessionSystem"));
					  }
					  
				}
		  });
		  
		  return statusSystemVariablesTabpane;
			
	  }

	  private HBox addTopHBoxForUserAndPrevileges() {

			 
	        HBox hbox = new HBox();
	        hbox.setPadding(new Insets(10, 12, 10, 12));
	        hbox.setSpacing(10);   // Gap between nodes
	        hbox.setStyle("-fx-background-color: #2f4f4f;");        
	        Text connectToDatabseText = new Text("Users and Privileges");
	        connectToDatabseText.setFont(Font.font("Verdana",20));
	        connectToDatabseText.setFill(Color.WHITE);
	        hbox.getChildren().addAll(connectToDatabseText);
	        
	        return hbox;
	  }
	  
	  private HBox addTopHBoxForVariables() {

			 
	        HBox hbox = new HBox();
	        hbox.setPadding(new Insets(10, 12, 10, 12));
	        hbox.setSpacing(10);   // Gap between nodes
	        hbox.setStyle("-fx-background-color: #2f4f4f;");        
	        Text connectToDatabseText = new Text("Server Variables");
	        connectToDatabseText.setFont(Font.font("Verdana",20));
	        connectToDatabseText.setFill(Color.WHITE);
	        hbox.getChildren().addAll(connectToDatabseText);
	        
	        return hbox;
	  }
	  
	  private HBox addTopHBoxForInfo(String infoType) {
	     
		  HBox hbox = new HBox();
	        hbox.setPadding(new Insets(10, 12, 10, 12));
	        hbox.setSpacing(10);   // Gap between nodes
	        hbox.setStyle("-fx-background-color: #2f4f4f;");        
	        Text connectToDatabseText = new Text(infoType);
	        connectToDatabseText.setFont(Font.font("Verdana",20));
	        connectToDatabseText.setFill(Color.WHITE);
	        hbox.getChildren().addAll(connectToDatabseText);
	        
	        return hbox;
	  }
	  /*
	  private HBox addTopHBoxForBinaryLogs(String logType) {

			 
	        HBox hbox = new HBox();
	        hbox.setPadding(new Insets(10, 12, 10, 12));
	        hbox.setSpacing(10);   // Gap between nodes
	        hbox.setStyle("-fx-background-color: #2f4f4f;");        
	        Text connectToDatabseText = new Text(logType+" Logs");
	        connectToDatabseText.setFont(Font.font("Verdana",20));
	        connectToDatabseText.setFill(Color.WHITE);
	        hbox.getChildren().addAll(connectToDatabseText);
	        
	        return hbox;
	  }*/
	  /*
	  private HBox addTopHBoxForClientConnections() {

			 
	        HBox hbox = new HBox();
	        hbox.setPadding(new Insets(10, 12, 10, 12));
	        hbox.setSpacing(10);   // Gap between nodes
	        hbox.setStyle("-fx-background-color: #2f4f4f;");        
	        Text connectToDatabseText = new Text("Client Connections");
	        connectToDatabseText.setFont(Font.font("Verdana",20));
	        connectToDatabseText.setFill(Color.WHITE);
	        hbox.getChildren().addAll(connectToDatabseText);
	        
	        return hbox;
	  }*/
	  
	  private VBox getStatusVariables() {
		  
		  VBox connectionDetailsVbox = new VBox();
		  
		  statusVariablesTabPane = new TabPane();
		  statusVariablesTabPane.setTabMinWidth(150);
		  statusVariablesTabPane.setTabMinHeight(20);
		  
		  globalStatusVariablesTab = new Tab("Global Status Variables");
		  globalStatusVariablesTab.setClosable(false);
		  globalStatusVariablesTab.setContent(getVariablesData("GlobalStatus"));  // This needs to be done coz we need to see data initially
		  
		  // *****************//

		  sessionStatusVariablesTab = new Tab("Session Status Variables");
		  sessionStatusVariablesTab.setClosable(false);
		  sessionStatusVariablesTab.setContent(getVariablesData("SessionStatus"));
		  
		  
		  statusVariablesTabPane.getTabs().addAll(globalStatusVariablesTab,sessionStatusVariablesTab);
		  
		  connectionDetailsVbox.getChildren().add(statusVariablesTabPane);
		  
		  statusVariablesTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				@Override
				public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
					 // System.out.println("Old Tab Selected -->"+oldValue.getText());
					  System.out.println("New Tab Selected -->"+newValue.getText());
					  
					  if(newValue.getText().equalsIgnoreCase("Global Status Variables")) {
						  globalStatusVariablesTab.setContent(getVariablesData("GlobalStatus"));
					  }
					  if(newValue.getText().equalsIgnoreCase("Session Status Variables")) {
						  sessionStatusVariablesTab.setContent(getVariablesData("SessionStatus"));
					  }
					  
				}
		  });
		  
		  return connectionDetailsVbox;
	  }
	  
	  private VBox getSystemVariables() {
		  
		  VBox connectionDetailsVbox = new VBox();
		  
		  systemVariablesTabPane = new TabPane();
		  systemVariablesTabPane.setTabMinWidth(150);
		  systemVariablesTabPane.setTabMinHeight(20);
		  
		  globalSystemVariablesTab = new Tab("Global System Variables");
		  globalSystemVariablesTab.setClosable(false);
		  globalSystemVariablesTab.setContent(getVariablesData("GlobalSystem"));

		  sessionSystemVariablesTab = new Tab("Session System Variables");
		  sessionSystemVariablesTab.setClosable(false);
		  sessionSystemVariablesTab.setContent(getVariablesData("SessionSystem"));
		    
		  systemVariablesTabPane.getTabs().addAll(globalSystemVariablesTab,sessionSystemVariablesTab);
		  
		  connectionDetailsVbox.getChildren().add(systemVariablesTabPane);
		  
		  systemVariablesTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				@Override
				public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
					 // System.out.println("Old Tab Selected -->"+oldValue.getText());
					  System.out.println("New Tab Selected -->"+newValue.getText());
					  
					  if(newValue.getText().equalsIgnoreCase("Global System Variables")) {
						  globalSystemVariablesTab.setContent(getVariablesData("GlobalSystem"));
					  }
					  if(newValue.getText().equalsIgnoreCase("Session System Variables")) {
						  sessionSystemVariablesTab.setContent(getVariablesData("SessionSystem"));
					  }
					  
				}
		  });
		  
		  return connectionDetailsVbox;
		  

	  }	

	private VBox getVariablesData(String variableType) {
		
	    VBox vBoxMain = new VBox();
		
		GridPane searchDatabasesGridPane= new GridPane();
		searchDatabasesGridPane.setPadding(new Insets(5,0,0,10));
		Label searchDatabasesLabel = new Label("Find");
		TextField searchDatabasesTextField = new TextField();
		refreshButton = new Button("Refresh");
		Label placeholderLabel = new Label("");
		searchDatabasesGridPane.getChildren().addAll(searchDatabasesLabel,searchDatabasesTextField,refreshButton,placeholderLabel);
		GridPane.setConstraints(searchDatabasesLabel, 0, 0); // Column 0 row 0
		GridPane.setHalignment(searchDatabasesLabel,HPos.CENTER);
		searchDatabasesGridPane.getColumnConstraints().add(new ColumnConstraints(50));
		GridPane.setConstraints(searchDatabasesTextField, 1, 0); // Column 1 row 0
		searchDatabasesGridPane.getColumnConstraints().add(new ColumnConstraints(260));
		GridPane.setConstraints(placeholderLabel, 2, 0); // Column 2 row 0
		searchDatabasesGridPane.getColumnConstraints().add(new ColumnConstraints(600));
		GridPane.setConstraints(refreshButton, 3, 0); // Column 3 row 0
		
		
		vBoxMain.getChildren().add(searchDatabasesGridPane);
		
		refreshButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Refresing the variables ...");
				 statusVariablesTab.setContent(getStatusVariables());
				 systemVariablesTab.setContent(getSystemVariables()); 
				 variablesSecondHalfDisplayVBox.getChildren().clear();
			}
		});

		// *****************//
		
		//****************//
		//   2nd level
		
		GridPane gridPaneDatabasesLists = new GridPane();
		gridPaneDatabasesLists.setHgap(10);
		gridPaneDatabasesLists.setPadding(new Insets(5,0,0,15));
		
		TableView resultAsTableView = new TableView();  
		if(variableType.equalsIgnoreCase("GlobalStatus")) {
			
			try {
				ResultSet rsGlobalVariables = stmt.executeQuery(" SHOW GLOBAL STATUS");
				resultAsTableView =  showResultSetInTheTableView(rsGlobalVariables,"Status");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(variableType.equalsIgnoreCase("SessionStatus")) {
			
			try {
				ResultSet rsGlobalVariables = stmt.executeQuery(" SHOW SESSION STATUS");
				resultAsTableView =  showResultSetInTheTableView(rsGlobalVariables,"Status");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(variableType.equalsIgnoreCase("GlobalSystem")) {
			
			try {
				ResultSet rsGlobalVariables = stmt.executeQuery(" SHOW GLOBAL VARIABLES");
				resultAsTableView =  showResultSetInTheTableView(rsGlobalVariables,"Variables");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(variableType.equalsIgnoreCase("SessionSystem")) {
			
			try {
				ResultSet rsGlobalVariables = stmt.executeQuery(" SHOW SESSION VARIABLES");
				resultAsTableView =  showResultSetInTheTableView(rsGlobalVariables,"Variables");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		gridPaneDatabasesLists.getChildren().add(resultAsTableView);
		GridPane.setConstraints(resultAsTableView, 0,0);
		gridPaneDatabasesLists.getColumnConstraints().add(new ColumnConstraints(1000)); // need to check this and fit ass per the screen/tab
		
		
		vBoxMain.getChildren().add(gridPaneDatabasesLists);
		
		//*******************//
		return vBoxMain;
	}		
	
	public Label accountLockedStatus;
	public Button accountlockUnLock;
	public TextField loginNameTextFeild;
	public TextField authenticationTypeTextField;
	public TextField authenticationStringTextField;
	public TextField hostsMatchingTextField;
	public Label passwordExpiredStatusLabel;
	public Label passwordLastchangedDate;
	public TextField passwordTextField;
	public TextField confirmPasswordTextField;
	
	
	public VBox addAccountLoginCredentials() {
		
		  VBox accountDetailsVbox = new VBox();
		  HBox accountLockedHBox = new HBox();
		  accountLockedHBox.setPadding(new Insets(15,0,0,100));
		  accountLockedHBox.setSpacing(20);
		  
		  Label accountLockedLabel = new Label("Account Locked :");
		  accountLockedStatus = new Label("Y/N"); // Look up the status	
		  accountlockUnLock = new Button("Lock/Unlock");
		  accountLockedHBox.getChildren().addAll(accountLockedLabel,accountLockedStatus,accountlockUnLock);
		  accountDetailsVbox.getChildren().add(accountLockedHBox);
		  
		  GridPane accountDetailsGridPane = new GridPane();
		  accountDetailsGridPane.setPadding(new Insets(0,10,20,10));
		  accountDetailsGridPane.setVgap(10);
		  accountDetailsGridPane.setHgap(10);
		  Label loginNameLabel = new Label("Login Name :"); 
		  GridPane.setConstraints(loginNameLabel, 0, 1);   // column 0 row 0
		  loginNameTextFeild = new TextField();
		  loginNameTextFeild.setPrefHeight(15);
		  loginNameTextFeild.setPrefWidth(200);
		 // jdbcConnectionName.setOnKeyTyped(onjdbcUrlTextFieldKeyPressed() );
		  GridPane.setConstraints(loginNameTextFeild, 1, 1);
		  Label loginNameDescriptionLable = new Label("You may create multiple accounts with the same name to connect from different hosts. place holer place holder");
		  loginNameDescriptionLable.setPrefWidth(300);
		  loginNameDescriptionLable.setWrapText(true);
		  GridPane.setConstraints(loginNameDescriptionLable, 2, 1);
		  accountDetailsGridPane.getChildren().addAll(loginNameLabel,loginNameTextFeild,loginNameDescriptionLable);
		  
		  Label authenticationTypeLabel = new Label("Authentication Type :");
		  GridPane.setConstraints(authenticationTypeLabel, 0, 2);
		  authenticationTypeTextField= new TextField();
		  authenticationTypeTextField.setPrefHeight(15);
		  authenticationTypeTextField.setPrefWidth(200);
		  GridPane.setConstraints(authenticationTypeTextField, 1, 2);
		  Label authenticationTypeLabelDescription = new Label("For the standard password and/or host based authication,select 'standard'  place holer place holder  place holer place holder");
		  authenticationTypeLabelDescription.setPrefWidth(300);
		  authenticationTypeLabelDescription.setWrapText(true);
		  GridPane.setConstraints(authenticationTypeLabelDescription, 2, 2);
		  accountDetailsGridPane.getChildren().addAll(authenticationTypeLabel,authenticationTypeTextField,authenticationTypeLabelDescription);
		  
		  Label authenticationStringLabel = new Label("Authentication String :");
		  GridPane.setConstraints(authenticationStringLabel, 0, 3);
		  authenticationStringTextField= new TextField();
		  authenticationStringTextField.setPrefHeight(15);
		  authenticationStringTextField.setPrefWidth(200);
		  GridPane.setConstraints(authenticationStringTextField, 1, 3);
		  Label authenticationStringLabelDescription = new Label("Authentication plugin specific parameters");
		  authenticationStringLabelDescription.setPrefWidth(300);
		  authenticationStringLabelDescription.setWrapText(true);
		  GridPane.setConstraints(authenticationStringLabelDescription, 2, 3);
		  accountDetailsGridPane.getChildren().addAll(authenticationStringLabel,authenticationStringTextField,authenticationStringLabelDescription);
		  
		  
		  Label hostmatchingLabel = new Label("Hosts Matching :");
		  GridPane.setConstraints(hostmatchingLabel, 0, 4);
		  hostsMatchingTextField = new TextField();
		  hostsMatchingTextField.setPrefHeight(15);
		  hostsMatchingTextField.setPrefWidth(100);  
		  GridPane.setConstraints(hostsMatchingTextField, 1, 4);
		  Label hostmatchingDescriptionLable = new Label("% and _ wildcards may be used , % accesses from anywhere");
		  hostmatchingDescriptionLable.setPrefWidth(300);
		  hostmatchingDescriptionLable.setWrapText(true);
		  GridPane.setConstraints(hostmatchingDescriptionLable, 2, 4);
		  accountDetailsGridPane.getChildren().addAll(hostmatchingLabel,hostsMatchingTextField,hostmatchingDescriptionLable);

		  Label passwordexpiredLabel = new Label("Password Expired:");
		  GridPane.setConstraints(passwordexpiredLabel, 0, 5);
		  passwordExpiredStatusLabel = new Label("Y/N");
		  GridPane.setConstraints(passwordExpiredStatusLabel, 1, 5);
		  
		  Label passwordLastchangedLabel = new Label("Password Last Changed:");
		  GridPane.setConstraints(passwordLastchangedLabel, 2, 5);
		  passwordLastchangedDate = new Label("10/12/2024");
		  GridPane.setConstraints(passwordLastchangedDate, 3, 5);
		  accountDetailsGridPane.getChildren().addAll(passwordexpiredLabel,passwordExpiredStatusLabel,passwordLastchangedLabel,passwordLastchangedDate);
		  
		  Label passwordLabel = new Label("Password :");
		  GridPane.setConstraints(passwordLabel, 0, 6);
		  passwordTextField = new TextField();
		  passwordTextField.setDisable(true);
		  passwordTextField.setPrefHeight(15);
		  passwordTextField.setPrefWidth(100);  
		  GridPane.setConstraints(passwordTextField, 1, 6);
		  Label passwordDescriptionLabel = new Label("Enter the password to reset it. Follow the passord requiements");
		  passwordDescriptionLabel.setPrefWidth(300);
		  passwordDescriptionLabel.setWrapText(true);
		  GridPane.setConstraints(passwordDescriptionLabel, 2, 6);
		  accountDetailsGridPane.getChildren().addAll(passwordLabel,passwordTextField,passwordDescriptionLabel);
		  
		  Label confirmPasswordLabel = new Label("Confirm Password :");
		  GridPane.setConstraints(confirmPasswordLabel, 0, 7);
		  confirmPasswordTextField = new TextField();
		  confirmPasswordTextField.setDisable(true);
		  confirmPasswordTextField.setPrefHeight(15);
		  confirmPasswordTextField.setPrefWidth(100);  
		  GridPane.setConstraints(confirmPasswordTextField, 1, 7);
		  Label confirmPasswordDescriptionLabel = new Label("Enter the password again to confirm");
		  confirmPasswordDescriptionLabel.setPrefWidth(300);
		  confirmPasswordDescriptionLabel.setWrapText(true);
		  GridPane.setConstraints(confirmPasswordDescriptionLabel, 2, 7);
		  accountDetailsGridPane.getChildren().addAll(confirmPasswordLabel,confirmPasswordTextField,confirmPasswordDescriptionLabel);
		  
		  accountDetailsVbox.getChildren().add(accountDetailsGridPane);
		  
		  HBox accountButtonsHBox = new HBox();
		  accountButtonsHBox.setPadding(new Insets(15,15,15,100));
		  accountButtonsHBox.setSpacing(30);
		  Button updatePasswordButton = new Button("Update Password");
		  Button expirePasswordButton = new Button("Expire Password");
		  Button revertAccountChangesButton = new Button("Revert");
		  Button saveAccountChangesButton = new Button("Save");

		  accountButtonsHBox.getChildren().addAll(updatePasswordButton,expirePasswordButton,revertAccountChangesButton,saveAccountChangesButton);
		  
		  accountDetailsVbox.getChildren().add(accountButtonsHBox);
		  
		return accountDetailsVbox;
		
		
	}
	
	public VBox addAccountLimits(){
		
		VBox accountLimitsVBox = new VBox();
		
		GridPane accountDetailsGridPane = new GridPane();
		accountDetailsGridPane.setPadding(new Insets(0,10,20,10));
		accountDetailsGridPane.setVgap(10);
		accountDetailsGridPane.setHgap(10);
		
		Label maxQueriesLabel = new Label("Max Queries"); 
		GridPane.setConstraints(maxQueriesLabel, 0, 1);   // column 0 row 0
		TextField maxQueriesTextFeild = new TextField();
		maxQueriesTextFeild.setPrefHeight(15);
		maxQueriesTextFeild.setPrefWidth(200);
		// jdbcConnectionName.setOnKeyTyped(onjdbcUrlTextFieldKeyPressed() );
		GridPane.setConstraints(maxQueriesTextFeild, 1, 1);
		Label maxQueriesDescription = new Label("The number of queries the account can execute within one hour");
		maxQueriesDescription.setPrefWidth(300);
		maxQueriesDescription.setWrapText(true);
		GridPane.setConstraints(maxQueriesDescription, 2, 1);
		accountDetailsGridPane.getChildren().addAll(maxQueriesLabel,maxQueriesTextFeild,maxQueriesDescription);
		
		Label maxUpdatesLabel = new Label("Max Updates"); 
		GridPane.setConstraints(maxUpdatesLabel, 0, 2);   // column 0 row 0
		TextField maxUpdatesTextFeild = new TextField();
		maxUpdatesTextFeild.setPrefHeight(15);
		maxUpdatesTextFeild.setPrefWidth(200);
		// jdbcConnectionName.setOnKeyTyped(onjdbcUrlTextFieldKeyPressed() );
		GridPane.setConstraints(maxUpdatesTextFeild, 1, 2);
		Label maxUpdatesDescription = new Label("The number of updates the account can execute within one hour");
		maxUpdatesDescription.setPrefWidth(300);
		maxUpdatesDescription.setWrapText(true);
		GridPane.setConstraints(maxUpdatesDescription, 2, 2);
		accountDetailsGridPane.getChildren().addAll(maxUpdatesLabel,maxUpdatesTextFeild,maxUpdatesDescription);
		
		
		Label maxConnectionsLabel = new Label("Max Connections"); 
		GridPane.setConstraints(maxConnectionsLabel, 0, 3);   // column 0 row 0
		TextField maxConnectionsTextFeild = new TextField();
		maxConnectionsTextFeild.setPrefHeight(15);
		maxConnectionsTextFeild.setPrefWidth(200);
		// jdbcConnectionName.setOnKeyTyped(onjdbcUrlTextFieldKeyPressed() );
		GridPane.setConstraints(maxConnectionsTextFeild, 1, 3);
		Label maxConnectionsDescription = new Label("The number of updates the account can execute within one hour");
		maxConnectionsDescription.setPrefWidth(300);
		maxConnectionsDescription.setWrapText(true);
		GridPane.setConstraints(maxConnectionsDescription, 2, 3);
		accountDetailsGridPane.getChildren().addAll(maxConnectionsLabel,maxConnectionsTextFeild,maxConnectionsDescription);
		
		Label concurrentConnectionsLabel = new Label("Concurrent Connections"); 
		GridPane.setConstraints(concurrentConnectionsLabel, 0, 4);   // column 0 row 0
		TextField concurrentConnectionsTextFeild = new TextField();
		concurrentConnectionsTextFeild.setPrefHeight(15);
		concurrentConnectionsTextFeild.setPrefWidth(200);
		// jdbcConnectionName.setOnKeyTyped(onjdbcUrlTextFieldKeyPressed() );
		GridPane.setConstraints(concurrentConnectionsTextFeild, 1, 4);
		Label concurrentConnectionsDescription = new Label("The number of updates the account can execute within one hour");
		concurrentConnectionsDescription.setPrefWidth(300);
		concurrentConnectionsDescription.setWrapText(true);
		GridPane.setConstraints(maxConnectionsDescription, 2, 4);
		accountDetailsGridPane.getChildren().addAll(concurrentConnectionsLabel,concurrentConnectionsTextFeild,concurrentConnectionsDescription);
		
		accountLimitsVBox.getChildren().add(accountDetailsGridPane);
		
		HBox accountLimitsButtonshbox = new HBox();
		accountLimitsButtonshbox.setSpacing(30);
		accountLimitsButtonshbox.setPadding(new Insets(20,10,10,500));
		Button revertAccountLimitsButton = new Button("Revert");
		Button saveAccountLimitsButton = new Button("Save");
		accountLimitsButtonshbox.getChildren().addAll(revertAccountLimitsButton,saveAccountLimitsButton);
		  
		accountLimitsVBox.getChildren().add(accountLimitsButtonshbox);
		
		
		return accountLimitsVBox;
	}
	
	public CheckBox alterPrivilegeCheckBox;
	public CheckBox alterRoutinePrivilegeCheckBox;
	public CheckBox createPrivilegeCheckBox;
	public CheckBox createRolePrivilegeCheckBox;
	public CheckBox createRoutinePrivilegeCheckBox;
	public CheckBox createTableSpacePrivilegeCheckBox;
	public CheckBox createTemporaryTablesPrivilegeCheckBox;
	public CheckBox createUserPrivilegeCheckBox;
	public CheckBox createViewPrivilegeCheckBox;
	
	public CheckBox deletePrivilegeCheckBox;  
	public CheckBox dropPrivilegeCheckBox;
	public CheckBox dropRolePrivilegeCheckBox;
	public CheckBox eventPrivilegeCheckBox;
	public CheckBox executePrivilegeCheckBox; 
	public CheckBox filePrivilegeCheckBox;
	public CheckBox grantOptionPrivilegeCheckBox;
	public CheckBox indexPrivilegeCheckBox;
	public CheckBox insertPrivilegeCheckBox;
	public CheckBox lockTablesPrivilegeCheckBox; 
	public CheckBox processPrivilegeCheckBox;
	
	public CheckBox referencesPrivilegeCheckBox; 
	public CheckBox reloadPrivilegeCheckBox; 
	public CheckBox replicationSlavePrivilegeCheckBox; 
	public CheckBox replicationClientPrivilegeCheckBox; 
	public CheckBox selectPrivilegeCheckBox;
	public CheckBox showDatabasesPrivilegeCheckBox; 
	public CheckBox showViewPrivilegeCheckBox; 
	public CheckBox shutdowmPrivilegeCheckBox; 
	public CheckBox superPrivilegeCheckBox; 
	public CheckBox triggerPrivilegeCheckBox; 
	public CheckBox updatePrivilegeCheckBox; 

	public VBox addAccountPrivileges() {
		
		VBox accountPrivilegesVBox = new VBox();
		accountPrivilegesVBox.setSpacing(10);
		accountPrivilegesVBox.setPadding(new Insets(10,10,10,10));
		
		Label globalPrivilegesLable = new Label("Global Privileges");
		CheckBox selectAllCheckBox = new CheckBox("Select All");
		accountPrivilegesVBox.getChildren().addAll(globalPrivilegesLable,selectAllCheckBox);
		

		
		HBox globalPreviligeshbox = new HBox();
		globalPreviligeshbox.setSpacing(50);
		
		VBox firstSetofPrivileges = new VBox();
		firstSetofPrivileges.setSpacing(10);
		firstSetofPrivileges.setPadding(new Insets(10,10,10,10));
		alterPrivilegeCheckBox = new CheckBox("ALTER");
		alterRoutinePrivilegeCheckBox = new CheckBox("ALTER ROUTINE");
		createPrivilegeCheckBox = new CheckBox("CREATE");
		createRolePrivilegeCheckBox = new CheckBox("CREATE ROLE");
		createRoutinePrivilegeCheckBox = new CheckBox("CREATE ROUTINE");
		createTableSpacePrivilegeCheckBox = new CheckBox("CREATE TABLESPACE");
		createTemporaryTablesPrivilegeCheckBox = new CheckBox("CREATE TEMPORARY TABLES");
		createUserPrivilegeCheckBox = new CheckBox("CREATE USER");
		createViewPrivilegeCheckBox = new CheckBox("CREATE VIEW");
		deletePrivilegeCheckBox =  new CheckBox("DELETE");  
		firstSetofPrivileges.getChildren().addAll(alterPrivilegeCheckBox,alterRoutinePrivilegeCheckBox,createPrivilegeCheckBox,createRolePrivilegeCheckBox,createRoutinePrivilegeCheckBox
				,createTableSpacePrivilegeCheckBox,createTemporaryTablesPrivilegeCheckBox,createUserPrivilegeCheckBox,createViewPrivilegeCheckBox,deletePrivilegeCheckBox);
		
		VBox secondSetofPrivileges = new VBox();
		secondSetofPrivileges.setSpacing(10);
		secondSetofPrivileges.setPadding(new Insets(10,10,10,10));
	
		dropPrivilegeCheckBox =  new CheckBox("DROP");
		dropRolePrivilegeCheckBox =  new CheckBox("DROP ROLE");
		eventPrivilegeCheckBox =  new CheckBox("EVENT");
		executePrivilegeCheckBox =  new CheckBox("EXECUTE"); 
		filePrivilegeCheckBox =  new CheckBox("FILE");
		grantOptionPrivilegeCheckBox =  new CheckBox("GRANT OPTION");
		indexPrivilegeCheckBox =  new CheckBox("INDEX");
		insertPrivilegeCheckBox =  new CheckBox("INSERT");
		lockTablesPrivilegeCheckBox =  new CheckBox("LOCK TABLES"); 
		processPrivilegeCheckBox =  new CheckBox("PROCESS");
		secondSetofPrivileges.getChildren().addAll(dropPrivilegeCheckBox,dropRolePrivilegeCheckBox,eventPrivilegeCheckBox,executePrivilegeCheckBox,filePrivilegeCheckBox
				,grantOptionPrivilegeCheckBox,indexPrivilegeCheckBox,insertPrivilegeCheckBox,lockTablesPrivilegeCheckBox,processPrivilegeCheckBox);
		
		VBox thirdSetofPrivileges = new VBox();
		thirdSetofPrivileges.setSpacing(10);
		thirdSetofPrivileges.setPadding(new Insets(10,10,10,10));

		referencesPrivilegeCheckBox  =  new CheckBox("REFERENCES");
		reloadPrivilegeCheckBox  =  new CheckBox("RELOAD");
		replicationSlavePrivilegeCheckBox  =  new CheckBox("REPLICATION SLAVE");
		replicationClientPrivilegeCheckBox  =  new CheckBox("REPLICATION CLIENT"); 
		selectPrivilegeCheckBox  =  new CheckBox("SELECT");
		showDatabasesPrivilegeCheckBox  =  new CheckBox("SHOW DATABASES");
		showViewPrivilegeCheckBox  =  new CheckBox("SHOW VIEW");
		shutdowmPrivilegeCheckBox  =  new CheckBox("SHUT DOWN");
		superPrivilegeCheckBox  =  new CheckBox("SUPER"); 
		triggerPrivilegeCheckBox  =  new CheckBox("TRIGGER");
		updatePrivilegeCheckBox  =  new CheckBox("UPDATe");
		
		thirdSetofPrivileges.getChildren().addAll(referencesPrivilegeCheckBox,reloadPrivilegeCheckBox,replicationSlavePrivilegeCheckBox,replicationClientPrivilegeCheckBox,selectPrivilegeCheckBox,showDatabasesPrivilegeCheckBox,
				showViewPrivilegeCheckBox,shutdowmPrivilegeCheckBox,superPrivilegeCheckBox,triggerPrivilegeCheckBox,updatePrivilegeCheckBox);
		
		globalPreviligeshbox.getChildren().addAll(firstSetofPrivileges,secondSetofPrivileges,thirdSetofPrivileges);
		accountPrivilegesVBox.getChildren().add(globalPreviligeshbox);
		
		return accountPrivilegesVBox;
	}
	
	public VBox addSchemaPrivileges() {
		
		VBox schemaPrivilegesVbox = new VBox();
		schemaPrivilegesVbox.setSpacing(5);
		schemaPrivilegesVbox.setPadding(new Insets(5,10,00,20));
		
		Label selectSchemaDescription = new Label("Select the Schema/s for which the user 'khan' will have the previleges you want to define");
		//selectSchemaDescription.setFont(new Font(12));
		selectSchemaDescription.setTextFill(Color.BLUEVIOLET);
		schemaPrivilegesVbox.getChildren().add(selectSchemaDescription);	
		final ToggleGroup group = new ToggleGroup();
		
		GridPane schemaPrivilegesGridPane = new GridPane();
		schemaPrivilegesGridPane.setPadding(new Insets(5,10,5,10));
		schemaPrivilegesGridPane.setVgap(10);
		schemaPrivilegesGridPane.setHgap(10);
		
		RadioButton allSchemaRadioButton = new RadioButton("All Schema(%)");
		allSchemaRadioButton.setToggleGroup(group);
		allSchemaRadioButton.setSelected(true); 
		GridPane.setConstraints(allSchemaRadioButton, 0, 0);   // column 0 row 0
		Label allSchemasSelectedLabel = new Label();
		allSchemasSelectedLabel.setPrefHeight(15);
		allSchemasSelectedLabel.setPrefWidth(200);
		// jdbcConnectionName.setOnKeyTyped(onjdbcUrlTextFieldKeyPressed() );
		GridPane.setConstraints(allSchemasSelectedLabel, 1, 0);
		Label allsSchemaSelectedDescriptionLabel = new Label("This rule will apply to any schema name");
		allsSchemaSelectedDescriptionLabel.setPrefWidth(300);
		allsSchemaSelectedDescriptionLabel.setWrapText(true);
		GridPane.setConstraints(allsSchemaSelectedDescriptionLabel, 2, 0);
		schemaPrivilegesGridPane.getChildren().addAll(allSchemaRadioButton,allSchemasSelectedLabel,allsSchemaSelectedDescriptionLabel);
		
		RadioButton schemaMatchingPatternRadioButton = new RadioButton("Schemas matching pattern:");
		schemaMatchingPatternRadioButton.setToggleGroup(group);
		GridPane.setConstraints(schemaMatchingPatternRadioButton, 0, 1);   // column 0 row 0
		TextField schemaMatchingPatternTextField = new TextField();
		schemaMatchingPatternTextField.setPrefHeight(15);
		schemaMatchingPatternTextField.setPrefWidth(150);
		// jdbcConnectionName.setOnKeyTyped(onjdbcUrlTextFieldKeyPressed() );
		GridPane.setConstraints(schemaMatchingPatternTextField, 1, 1);
		Label schemaMatchingPatternDescriptionLabel = new Label("You may use % and _ as wildcards in a pattern.");
		schemaMatchingPatternDescriptionLabel.setPrefWidth(300);
		schemaMatchingPatternDescriptionLabel.setWrapText(true);
		GridPane.setConstraints(schemaMatchingPatternDescriptionLabel, 2, 1);
		schemaPrivilegesGridPane.getChildren().addAll(schemaMatchingPatternRadioButton,schemaMatchingPatternTextField,schemaMatchingPatternDescriptionLabel);
		
		
		RadioButton selectedSchemasRadioButton = new RadioButton("Selected Schemas");
		selectedSchemasRadioButton.setToggleGroup(group); 
		GridPane.setConstraints(selectedSchemasRadioButton, 0, 2);   // column 0 row 0
		ComboBox<String> selectedSchemascomboBox = new ComboBox();
		selectedSchemascomboBox.getItems().addAll("mysql","sys","sakila","information_schema","world","others");
		selectedSchemascomboBox.setPrefHeight(15);
		selectedSchemascomboBox.setPrefWidth(200);
		//selectedSchemascomboBox.setValue("mysql");
		selectedSchemascomboBox.setVisibleRowCount(5); // after this their will be scroll bar
		// jdbcConnectionName.setOnKeyTyped(onjdbcUrlTextFieldKeyPressed() );
		GridPane.setConstraints(selectedSchemascomboBox, 1, 2);
		Label selectedSchemaDescription = new Label("Select a specific schema for the rule to apply to:");
		selectedSchemaDescription.setPrefWidth(300);
		selectedSchemaDescription.setWrapText(true);
		GridPane.setConstraints(selectedSchemaDescription, 2, 2);
		schemaPrivilegesGridPane.getChildren().addAll(selectedSchemasRadioButton,selectedSchemascomboBox,selectedSchemaDescription);
		schemaPrivilegesVbox.getChildren().add(schemaPrivilegesGridPane);
		
		HBox addSchemaEntryHbox = new HBox();
		addSchemaEntryHbox.setPadding(new Insets(0,0,0,500));	
		Button addSchemaPrivilegesEntryButton = new Button("Add Schema Entry");
		addSchemaEntryHbox.getChildren().add(addSchemaPrivilegesEntryButton);
		schemaPrivilegesVbox.getChildren().add(addSchemaEntryHbox);

		VBox existingSchemaPriviligesVBox = new VBox();
		existingSchemaPriviligesVBox.setMaxHeight(150);
		existingSchemaPriviligesVBox.setPadding(new Insets(0,10,0,10));
		
		TableView tableView = new TableView();
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);  // to remove the last empty column otherwise added
		tableView.setEditable(true);
        
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HashMap<String,String>>() {

			@Override
			public void changed(ObservableValue<? extends HashMap<String, String>> observable,
					HashMap<String, String> oldValue, HashMap<String, String> newValue) {

				System.out.println("oldValue --->"+oldValue);
				System.out.println("newValue --->"+newValue.keySet().toString());
				for(Map.Entry<String, String> tableValues : newValue.entrySet()) {
					
					System.out.println( tableValues.getKey()+ " "+ tableValues.getValue());
				}
				
				TableViewSelectionModel  selectionModel = tableView.getSelectionModel();
		        ObservableList selectedCells = selectionModel.getSelectedCells();
		        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
		        Object val = tablePosition.getTableColumn().getCellData(newValue);
		        System.out.println("Selected Value" + val);
				
			}	
		});
		TableColumn tableColumnName;
		String[] columnNames = {"Schema","Privileges"};
		   
	    tableColumnName = new TableColumn<>(columnNames[0]);
	    tableColumnName.setMinWidth(50);
	    tableColumnName.setCellValueFactory(new MapValueFactory<>(columnNames[0]));
	    tableView.getColumns().add(tableColumnName);

	    tableColumnName = new TableColumn<>(columnNames[1]);
	    tableColumnName.setMinWidth(250);
	    tableColumnName.setCellValueFactory(new MapValueFactory<>(columnNames[1]));
	    tableView.getColumns().add(tableColumnName);
	    
		ObservableList<HashMap<String,String>> mapdata =
		            FXCollections.observableArrayList();
        HashMap<String,String> schemaMap = new HashMap<String,String>();
        
        schemaMap.put(columnNames[0], "mysql");
        schemaMap.put(columnNames[1], "none");
        mapdata.add(schemaMap);
        
        schemaMap = new HashMap<String,String>();
        schemaMap.put(columnNames[0], "world");
        schemaMap.put(columnNames[1], "tocome");
        mapdata.add(schemaMap);
      
        tableView.setItems(mapdata);
        
        existingSchemaPriviligesVBox.getChildren().add(tableView);
        
        HBox userAccessSchemaDescriptionHbox= new HBox();
        userAccessSchemaDescriptionHbox.setPadding(new Insets(5,5,5,5));
        userAccessSchemaDescriptionHbox.setSpacing(50);
        Label userAccessSchemaDescriptionLabel = new Label("The use 'khan'@'localhost' will have the following access rights to the schema 'mysql'");
        selectSchemaDescription.setTextFill(Color.BLUEVIOLET);
     	Button deleteSchemaPrivilegesEntryButton = new Button("Delete Schema Entry");
     	deleteSchemaPrivilegesEntryButton.setDisable(true);  // enable it when one of the schema entry is selected
        userAccessSchemaDescriptionHbox.getChildren().addAll(userAccessSchemaDescriptionLabel,deleteSchemaPrivilegesEntryButton);
        
        
        HBox userAccessSchemaPriviligeshBox = new HBox();
        userAccessSchemaPriviligeshBox.setSpacing(10);		
        
        VBox firstSetofSchemaPriviliges = new VBox();
        firstSetofSchemaPriviliges.setSpacing(5);
    	selectPrivilegeCheckBox  =  new CheckBox("SELECT");
		updatePrivilegeCheckBox  =  new CheckBox("UPDATE");
		insertPrivilegeCheckBox =  new CheckBox("INSERT");
		executePrivilegeCheckBox =  new CheckBox("EXECUTE"); 
		showViewPrivilegeCheckBox  =  new CheckBox("SHOW VIEW");
		deletePrivilegeCheckBox =  new CheckBox("DELETE");  
		firstSetofSchemaPriviliges.getChildren().addAll(selectPrivilegeCheckBox,updatePrivilegeCheckBox,insertPrivilegeCheckBox,executePrivilegeCheckBox,showViewPrivilegeCheckBox,deletePrivilegeCheckBox);
        
		VBox secondSetofSchemaPriviliges = new VBox();
		secondSetofSchemaPriviliges.setSpacing(5);
		createPrivilegeCheckBox = new CheckBox("CREATE");
		alterPrivilegeCheckBox = new CheckBox("ALTER");
		referencesPrivilegeCheckBox  =  new CheckBox("REFERENCES");
		indexPrivilegeCheckBox =  new CheckBox("INDEX");
		createViewPrivilegeCheckBox = new CheckBox("CREATE VIEW");
		createRoutinePrivilegeCheckBox = new CheckBox("CREATE ROUTINE");
		secondSetofSchemaPriviliges.getChildren().addAll(createPrivilegeCheckBox,alterPrivilegeCheckBox,referencesPrivilegeCheckBox,indexPrivilegeCheckBox,createViewPrivilegeCheckBox,createRoutinePrivilegeCheckBox);
		
		VBox thirdSetofSchemaPriviliges = new VBox();
		thirdSetofSchemaPriviliges.setSpacing(5);
		alterRoutinePrivilegeCheckBox = new CheckBox("ALTER ROUTINE");
		eventPrivilegeCheckBox =  new CheckBox("EVENT");
		dropPrivilegeCheckBox =  new CheckBox("DROP");
		triggerPrivilegeCheckBox  =  new CheckBox("TRIGGER");
		grantOptionPrivilegeCheckBox =  new CheckBox("GRANT OPTION");
		createTemporaryTablesPrivilegeCheckBox = new CheckBox("CREATE TEMPORARY TABLES");
		lockTablesPrivilegeCheckBox =  new CheckBox("LOCK TABLES"); 
		thirdSetofSchemaPriviliges.getChildren().addAll(alterRoutinePrivilegeCheckBox,eventPrivilegeCheckBox,dropPrivilegeCheckBox,triggerPrivilegeCheckBox,grantOptionPrivilegeCheckBox,
				createTemporaryTablesPrivilegeCheckBox,lockTablesPrivilegeCheckBox);
		
		VBox fourthSegmentWithButtonsVbox = new VBox();
		fourthSegmentWithButtonsVbox.setPadding(new Insets(130,10,10,20));
		Button reverPriviligestButton = new Button("Revert Privileges");
		reverPriviligestButton.setDisable(true); // enable them when one of the schema entry is selected
		fourthSegmentWithButtonsVbox.getChildren().add(reverPriviligestButton);
		
		VBox fifthSegmentWithButtonsVbox = new VBox();
		fifthSegmentWithButtonsVbox.setPadding(new Insets(130,10,10,20));
		Button savePrivilegesButton = new Button("Save Privileges");
		savePrivilegesButton.setDisable(true); // enable them when one of the schema entry is selected
		fifthSegmentWithButtonsVbox.getChildren().add(savePrivilegesButton);
		
		 userAccessSchemaPriviligeshBox.getChildren().addAll(firstSetofSchemaPriviliges,secondSetofSchemaPriviliges,thirdSetofSchemaPriviliges,fourthSegmentWithButtonsVbox,fifthSegmentWithButtonsVbox);
		schemaPrivilegesVbox.getChildren().addAll(existingSchemaPriviligesVBox,userAccessSchemaDescriptionHbox,userAccessSchemaPriviligeshBox);  // main vbox
		
		return schemaPrivilegesVbox;
		
	}
	
	private VBox addclientConnectionThreadDetails(HashMap<String,String> allVariables,TableView tableView) {
			
		VBox clientConnectionsVBox = new VBox();
		clientConnectionsVBox.setSpacing(10);
		//clientConnectionsVBox.setPadding(new Insets(0,0,0,0));
		clientConnectionsVBox.getChildren().add(addTopHBoxForInfo("Client Connections"));
		
		HBox clientConnectionsThreadsDescriptionFirstHBox = new HBox();
		clientConnectionsThreadsDescriptionFirstHBox.setSpacing(30);
		clientConnectionsThreadsDescriptionFirstHBox.setPadding(new Insets(0,10,0,10));
		
		Label threadsConnectedLabel = new Label("Threads Connected : "+allVariables.get("Threads_connected"));
		threadsConnectedLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		Label threadsRunningLabel = new Label("Threads Running : "+allVariables.get("Threads_running"));
		threadsRunningLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		Label threadsCreatedLabel = new Label("Threads Created : "+allVariables.get("Threads_created"));
		threadsCreatedLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		Label threadsCachedLabel = new Label("Threads Cached : "+allVariables.get("Threads_cached"));
		threadsCachedLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		Label threadsrejectedLabel = new Label("Rejected : "+allVariables.get("Mysqlx_connections_rejected"));
		threadsrejectedLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));

		clientConnectionsThreadsDescriptionFirstHBox.getChildren().addAll(threadsConnectedLabel,threadsRunningLabel,
				threadsCreatedLabel,threadsrejectedLabel);

		HBox clientConnectionsThreadsDescriptionSecondHBox = new HBox();
		clientConnectionsThreadsDescriptionSecondHBox.setSpacing(30);
		clientConnectionsThreadsDescriptionSecondHBox.setPadding(new Insets(0,10,0,10));
		
		Integer connection_errors_accept = Integer.parseInt(allVariables.get("Connection_errors_accept"));
		Integer connection_errors_internal = Integer.parseInt(allVariables.get("Connection_errors_internal"));
		Integer connection_errors_max_connections = Integer.parseInt(allVariables.get("Connection_errors_max_connections"));
		Integer connection_errors_select = Integer.parseInt(allVariables.get("Connection_errors_select"));
		Integer connection_errors_tcpwrap = Integer.parseInt(allVariables.get("Connection_errors_tcpwrap"));
		Integer totalErrors = connection_errors_accept+connection_errors_internal+connection_errors_max_connections+connection_errors_select+connection_errors_tcpwrap;

		Label totalConnectionsLabel = new Label("Total Connections : "+allVariables.get("connections"));
		totalConnectionsLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		Label connectionsLimitLabel = new Label("Connections Limit : "+allVariables.get("max_connections"));
		connectionsLimitLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		Label abortedClientsLabel = new Label("Aborted Clients : "+allVariables.get("Aborted_clients"));
		abortedClientsLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		Label abortedConnectionsLabel = new Label("Aborted Connections  : "+allVariables.get("Aborted_connects"));
		abortedConnectionsLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		
		HBox totalErrorsTooltopHBox = new HBox();
		totalErrorsTooltopHBox.setSpacing(10);
		Label totalErrorsLabel = new Label("Errors : "+totalErrors);
		totalErrorsLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
        ImageView  informationicon = new ImageView(new Image(getClass().getResourceAsStream("/images/information_icon.png")));
        informationicon.setFitHeight(10);
        informationicon.setFitWidth(10);
        Tooltip toolTip = new Tooltip();
        toolTip.setText("Connection_errors_accept: "+connection_errors_accept+"\nConnection_errors_internal : "+connection_errors_internal
        		+"\nConnection_errors_max_connections :"+connection_errors_max_connections+"\nConnection_errors_select :"+connection_errors_select
        		+"\nConnection_errors_tcpwrap :"+connection_errors_tcpwrap);
        Label errorinformationLabel = new Label();
        errorinformationLabel.setGraphic(informationicon);
        errorinformationLabel.setTooltip(toolTip);
		totalErrorsTooltopHBox.getChildren().addAll(totalErrorsLabel,errorinformationLabel);
        clientConnectionsThreadsDescriptionSecondHBox.getChildren().addAll(totalConnectionsLabel,connectionsLimitLabel,abortedClientsLabel,abortedConnectionsLabel,totalErrorsTooltopHBox);
		
        VBox processListTableViewVbox = new VBox();
        processListTableViewVbox.getChildren().add(tableView);
        processListTableViewVbox.setMaxHeight(350);
        processListTableViewVbox.setPadding(new Insets(5,5,5,5));
        
        
        HBox clientConnectionsButtonsHBox = new HBox();
        clientConnectionsButtonsHBox.setSpacing(500);
        clientConnectionsButtonsHBox.setPadding(new Insets(10,10,10,10));
        
        HBox clientConnectionRefreshButtonshbox = new HBox();
        clientConnectionRefreshButtonshbox.setSpacing(10);
        Label clientConnectionRefreshLabel = new Label("Refresh Rate :");
        ComboBox refreshRateComboBox = new ComboBox();
        refreshRateComboBox.getItems().addAll("Don't Refresh","0.5 Seconds","1 Seconds","2 Seconds","3 Seconds");
        refreshRateComboBox.setValue("Don't Refresh");
        clientConnectionRefreshButtonshbox.getChildren().addAll(clientConnectionRefreshLabel,refreshRateComboBox);
        
        HBox clientConnectionKillButtonshbox = new HBox();
        clientConnectionKillButtonshbox.setSpacing(10);
        Button killQuerysButton = new Button("Kill Query(s)");
        Button killConnectionsButton = new Button("Kill Connection(s)");
        Button refreshButton = new Button("Refresh");
        clientConnectionKillButtonshbox.getChildren().addAll(killQuerysButton,killConnectionsButton,refreshButton);
        
        clientConnectionsButtonsHBox.getChildren().addAll(clientConnectionRefreshButtonshbox,clientConnectionKillButtonshbox);
        
        HBox clientConnectionsHideButtonsHBox = new HBox();
        clientConnectionsHideButtonsHBox.setSpacing(50);
        clientConnectionsHideButtonsHBox.setPadding(new Insets(0,10,0,10));
        CheckBox hideSleepingConnectionsCheckBox = new CheckBox("Hide Sleeping Connections");
        CheckBox hideBackgroundThreadsCheckBox = new CheckBox("Hide Background Threads");
        hideBackgroundThreadsCheckBox.setSelected(true);
        clientConnectionsHideButtonsHBox.getChildren().addAll(hideSleepingConnectionsCheckBox,hideBackgroundThreadsCheckBox);
        
		clientConnectionsVBox.getChildren().addAll(clientConnectionsThreadsDescriptionFirstHBox,clientConnectionsThreadsDescriptionSecondHBox,
				processListTableViewVbox,clientConnectionsButtonsHBox,clientConnectionsHideButtonsHBox);
		
		return clientConnectionsVBox;
	}
	
	 /*private HBox addTopHBoxForServerStatus() {

		 
	        HBox hbox = new HBox();
	        hbox.setPadding(new Insets(10, 12, 10, 12));
	        hbox.setSpacing(10);   // Gap between nodes
	        hbox.setStyle("-fx-background-color: #2f4f4f;");        
	        Text connectToDatabseText = new Text("Server Status");
	        connectToDatabseText.setFont(Font.font("Verdana",20));
	        connectToDatabseText.setFill(Color.WHITE);
	        hbox.getChildren().addAll(connectToDatabseText);
	        
	        return hbox;
	  } */
	 

	private VBox addServerStatus(HashMap<String,String> allVariables,HashMap<String,String> allStatus,HashMap<String,String> allPlugins) {
    	
		VBox serverStatusVBox = new VBox();
		serverStatusVBox.setSpacing(10);
		serverStatusVBox.getChildren().add(addTopHBoxForInfo("Server Status"));
		
		HBox serverRunningDescriptionHbox = new HBox();
		serverRunningDescriptionHbox.setPadding(new Insets(0,0,0,300));
		Label serverRunningDescriptionLabel = new Label("Server Status : Running");
		serverRunningDescriptionLabel.setTextFill(Color.GREEN);
		serverRunningDescriptionLabel.setFont(Font.font("System Regular",FontWeight.BOLD,18));
		serverRunningDescriptionHbox.getChildren().add(serverRunningDescriptionLabel);
		
		HBox instanceDetailsHBox = new HBox();
		instanceDetailsHBox.setSpacing(200);
		instanceDetailsHBox.setPadding(new Insets(0,10,0,10));
		
		GridPane instanceDetailsGridPane = new GridPane();
		//instanceDetailsServerDirectoriesGridPane.setPadding(new Insets(20,10,20,10));
		instanceDetailsGridPane.setVgap(10);
		instanceDetailsGridPane.setHgap(10);
		Label connectionDecriptionLabel = new Label(connectionPlaceHolder.getConnectionName() + " " + "Instance Details");
		connectionDecriptionLabel.setTextFill(Color.BLUEVIOLET);
		connectionDecriptionLabel.setFont(Font.font("System Regular",FontWeight.BOLD,16));
		GridPane.setConstraints(connectionDecriptionLabel, 0, 0);   // column 0 row 0
		Label hostDecriptionLabel = new Label("Host : " );
		GridPane.setConstraints(hostDecriptionLabel, 0, 1);
		Label hostValueLabel = new Label(allVariables.get("hostname"));
		hostValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(hostValueLabel, 1, 1);
		Label socketDecriptionLabel = new Label("Socket :");
		GridPane.setConstraints(socketDecriptionLabel, 0, 2);
		Label socketValueLabel = new Label(allVariables.get("socket"));
		socketValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(socketValueLabel, 1, 2);
		Label portDecriptionLabel = new Label("Port :");
		GridPane.setConstraints(portDecriptionLabel, 0, 3);
		Label portValueLabel = new Label(allVariables.get("port"));
		portValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(portValueLabel, 1, 3);
		Label versionDecriptionLabel = new Label("Version :");
		GridPane.setConstraints(versionDecriptionLabel, 0, 4);
		Label versionValueLabel = new Label(allVariables.get("version"));
		versionValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(versionValueLabel, 1, 4);
		Label compiledForDescriptionLabel = new Label("Compiled For :");
		GridPane.setConstraints(compiledForDescriptionLabel, 0, 5);
		Label compiledForValueLabel = new Label(allVariables.get("version_compile_os") +"("+ allVariables.get("version_compile_machine") + ")");
		compiledForValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(compiledForValueLabel, 1, 5);
		Label runningSinceDescriptionLabel = new Label("Running Since :");
		GridPane.setConstraints(runningSinceDescriptionLabel, 0, 6);
		Label runningSinceValueLabel = new Label(allStatus.get("Uptime"));
		runningSinceValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(runningSinceValueLabel, 1, 6);
			
		instanceDetailsGridPane.getChildren().addAll(connectionDecriptionLabel,hostDecriptionLabel,hostValueLabel,socketDecriptionLabel,socketValueLabel,
		portDecriptionLabel,portValueLabel,versionDecriptionLabel,versionValueLabel,compiledForDescriptionLabel,compiledForValueLabel,runningSinceDescriptionLabel,runningSinceValueLabel);
		
		GridPane serverDirectoriesGridPane = new GridPane();
		serverDirectoriesGridPane.setVgap(10);
		serverDirectoriesGridPane.setHgap(10);
		Label serverDirectoriesLabel = new Label("Server Direcories");
		serverDirectoriesLabel.setTextFill(Color.BLUEVIOLET);
		serverDirectoriesLabel.setFont(Font.font("System Regular",FontWeight.BOLD,16));
		GridPane.setConstraints(serverDirectoriesLabel, 0, 0);   // column 0 row 0
		Label baseDirecoryDescriptionLabel = new Label("Base Directory :" );
		GridPane.setConstraints(baseDirecoryDescriptionLabel, 0, 1); 
		Label baseDirecoryValueLabel = new Label(allVariables.get("basedir"));
		baseDirecoryValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(baseDirecoryValueLabel, 1, 1); 
		Label dataDirecoryDescriptionLabel = new Label("Data Directory :" );
		GridPane.setConstraints(dataDirecoryDescriptionLabel, 0, 2); 
		Label dataDirecoryValueLabel = new Label(allVariables.get("datadir"));
		dataDirecoryValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(dataDirecoryValueLabel, 1, 2); 
		Label pluginDirecoryDescriptionLabel = new Label("Plugin Directory :" );
		GridPane.setConstraints(pluginDirecoryDescriptionLabel, 0, 3); 
		Label pluginDirecoryValueLabel = new Label(allVariables.get("plugin_dir"));
		pluginDirecoryValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(pluginDirecoryValueLabel, 1, 3); 
		Label tempDirecoryDescriptionLabel = new Label("Temp Directory :" );
		GridPane.setConstraints(tempDirecoryDescriptionLabel, 0, 4); 
		Label tempDirecoryValueLabel = new Label(allVariables.get("tmpdir"));
		tempDirecoryValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(tempDirecoryValueLabel, 1, 4); 
		Label errorLogDirecoryDescriptionLabel = new Label("Error Log :" );
		GridPane.setConstraints(errorLogDirecoryDescriptionLabel, 0, 5); 
		Label errorLogDirecoryValueLabel = new Label(allVariables.get("log_error"));
		errorLogDirecoryValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(errorLogDirecoryValueLabel, 1, 5); 
		Label generalLogDirecoryDescriptionLabel = new Label("General Log :" );
		GridPane.setConstraints(generalLogDirecoryDescriptionLabel, 0, 6); 
		Label generalLogDirecoryValueLabel = new Label(allVariables.get("general_log_file"));
		generalLogDirecoryValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(generalLogDirecoryValueLabel, 1, 6); 
		Label slowQueryLogDirecoryDescriptionLabel = new Label("Slow Query Log :" );
		GridPane.setConstraints(slowQueryLogDirecoryDescriptionLabel, 0, 7); 
		Label slowQueryLogDirecoryValueLabel = new Label(allVariables.get("slow_query_log_file"));
		slowQueryLogDirecoryValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(slowQueryLogDirecoryValueLabel, 1, 7);
		
		serverDirectoriesGridPane.getChildren().addAll(serverDirectoriesLabel,baseDirecoryDescriptionLabel,baseDirecoryValueLabel,dataDirecoryDescriptionLabel,dataDirecoryValueLabel,
			pluginDirecoryDescriptionLabel,pluginDirecoryValueLabel,tempDirecoryDescriptionLabel,tempDirecoryValueLabel,
				errorLogDirecoryDescriptionLabel,errorLogDirecoryValueLabel,generalLogDirecoryDescriptionLabel,generalLogDirecoryValueLabel,slowQueryLogDirecoryDescriptionLabel,slowQueryLogDirecoryValueLabel);
		
		instanceDetailsHBox.getChildren().addAll(instanceDetailsGridPane,serverDirectoriesGridPane);
		
		HBox serverFeaturesDescriptionHbox = new HBox();
		serverFeaturesDescriptionHbox.setPadding(new Insets(0,0,0,300));
		Label serverFeaturesDescriptionLabel = new Label("Available Server Features");
		serverFeaturesDescriptionLabel.setTextFill(Color.BLUEVIOLET);
		serverFeaturesDescriptionLabel.setFont(Font.font("System Regular",FontWeight.BOLD,18));
		serverFeaturesDescriptionHbox.getChildren().add(serverFeaturesDescriptionLabel);
		
		HBox serverFeaturesHBox = new HBox();
		serverFeaturesHBox.setSpacing(200);
		serverFeaturesHBox.setPadding(new Insets(0,10,00,100));
    	
    	
		GridPane serverFeaturesLeftHalfGridPane = new GridPane();
		serverFeaturesLeftHalfGridPane.setVgap(10);
		serverFeaturesLeftHalfGridPane.setHgap(10);
		
		Label performanceSchemaDescriptionLabel = new Label("Performance Schema :" );
		GridPane.setConstraints(performanceSchemaDescriptionLabel, 0, 1); 
		Label performanceScheamLabel = new Label(String.valueOf(allPlugins.containsKey("PERFORMANCE_SCHEMA")));
		performanceScheamLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(performanceScheamLabel, 1, 1);
		Label threadPoolDescriptionLabel = new Label("Thread Pool :" );
		GridPane.setConstraints(threadPoolDescriptionLabel, 0, 2); 
		Label threadPoolValueLabel = new Label(String.valueOf(allVariables.containsKey("thread_pool_size")));
		threadPoolValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(threadPoolValueLabel, 1, 2); 
		Label memcachedPluginlabel = new Label("Memcached Plugin : " );
		GridPane.setConstraints(memcachedPluginlabel, 0, 3); 
		Label memcachedPluginValuelabel = new Label(String.valueOf(allPlugins.containsKey("daemon_memcached")));
		memcachedPluginValuelabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(memcachedPluginValuelabel, 1, 3);
		Label smsynReplicationPluginlabel = new Label("Semisync Replication Plugin : " );
		GridPane.setConstraints(smsynReplicationPluginlabel, 0, 4); 
		Label smsynReplicationPluginValuelabel = new Label(String.valueOf(allPlugins.containsKey("rpl_semi_sync_source")));
		smsynReplicationPluginValuelabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(smsynReplicationPluginValuelabel, 1, 4); 
		Label sslAvailabilitylabel = new Label("SSL Availability  : " );
		GridPane.setConstraints(sslAvailabilitylabel, 0, 5); 
		Label sslAvailabilityValuelabel = new Label(String.valueOf(allVariables.containsKey("ssl_ca")));
		sslAvailabilityValuelabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(sslAvailabilityValuelabel, 1, 5);
		Label federatedlabel = new Label("FEDERATED : " );
		GridPane.setConstraints(federatedlabel, 0, 6); 
		Label federatedValuelabel = new Label(String.valueOf(allPlugins.containsKey("FEDERATED")));
		GridPane.setConstraints(federatedValuelabel, 1, 6);
		federatedValuelabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));

		serverFeaturesLeftHalfGridPane.getChildren().addAll(performanceSchemaDescriptionLabel,performanceScheamLabel,threadPoolDescriptionLabel,threadPoolValueLabel,
				memcachedPluginlabel,memcachedPluginValuelabel,smsynReplicationPluginlabel,smsynReplicationPluginValuelabel,sslAvailabilitylabel,sslAvailabilityValuelabel,
				federatedlabel,federatedValuelabel);
		
		GridPane serverFeaturesRigthtHalfGridPane = new GridPane();
		serverFeaturesRigthtHalfGridPane.setVgap(10);
		serverFeaturesRigthtHalfGridPane.setHgap(10);
		
		Label windowAuthenticationDescriptionLabel = new Label("Window Authentication :" );
		GridPane.setConstraints(windowAuthenticationDescriptionLabel, 0, 1); 
		Label windowAuthenticationValueLabel = new Label(String.valueOf(allPlugins.containsKey("authentication_windows")));
		windowAuthenticationValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(windowAuthenticationValueLabel, 1, 1);
		Label passwordValidationDescriptionLabel = new Label("Password Validation : ");
		GridPane.setConstraints(passwordValidationDescriptionLabel, 0, 2); 
		Label passwordValidationValueLabel = new Label(String.valueOf(allVariables.containsKey("validate_password.policy")));
		passwordValidationValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(passwordValidationValueLabel, 1, 2); 
		Label auditLogDescriptionLabel = new Label("Audit Log : ");
		GridPane.setConstraints(auditLogDescriptionLabel, 0, 3); 
		Label auditLogValueLabel = new Label(String.valueOf(allVariables.containsKey("audit_log_file")));
		auditLogValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(auditLogValueLabel, 1, 3); 
		Label firewallDescriptionLabel = new Label("Firewall : ");
		GridPane.setConstraints(firewallDescriptionLabel, 0, 4); 
		Label firewallValueLabel = new Label(String.valueOf(allVariables.containsKey("mysql_firewall_mode")));
		firewallValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(firewallValueLabel, 1, 4); 
		Label firewallTraceDescriptionLabel = new Label("Firewall  Trace: ");
		GridPane.setConstraints(firewallTraceDescriptionLabel, 0, 5); 
		Label firewallTraceValueLabel = new Label(String.valueOf(allVariables.containsKey("mysql_firewall_mode")));
		firewallTraceValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(firewallTraceValueLabel, 1, 5); 
		Label csvDescriptionLabel = new Label("CSV : ");
		GridPane.setConstraints(csvDescriptionLabel, 0, 6); 
		Label csvValueLabel = new Label(String.valueOf(allPlugins.containsKey("CSV")));
		csvValueLabel.setFont(Font.font("System Regular",FontWeight.BOLD,12));
		GridPane.setConstraints(csvValueLabel, 1, 6);     
		
		serverFeaturesRigthtHalfGridPane.getChildren().addAll(windowAuthenticationDescriptionLabel,windowAuthenticationValueLabel,passwordValidationDescriptionLabel,passwordValidationValueLabel,
				auditLogDescriptionLabel,auditLogValueLabel,firewallDescriptionLabel,firewallValueLabel,firewallTraceDescriptionLabel,firewallTraceValueLabel,csvDescriptionLabel,csvValueLabel);
		
		
		serverFeaturesHBox.getChildren().addAll(serverFeaturesLeftHalfGridPane,serverFeaturesRigthtHalfGridPane);
		serverStatusVBox.getChildren().addAll(serverRunningDescriptionHbox,instanceDetailsHBox,serverFeaturesDescriptionHbox,serverFeaturesHBox);
		
		return serverStatusVBox;
	}
	
	private TreeView<String> generatePerformnaceReports() {
		
		
		
		TreeItem<String> rootReportsTreeItem =  new TreeItem<String>("Reports");
		TreeView<String> performanceView = new TreeView<String>();
		performanceView.setCellFactory((TreeView<String> p) -> new MySQLTreecellImpl());
		performanceView.setRoot(rootReportsTreeItem);
		performanceView.setShowRoot(false);
		//treeView.setMinSize(300, size.getHeight()-120);
		//treeConnectionsView.setPrefWidth(300);
		//treeConnectionsView.setMinWidth(100);
		performanceView.setMinHeight(menu_Items_FX.size.getHeight()-200);
		performanceView.setContextMenu(null);
		
		TreeItem<String> memoryUsageTreeItem = new TreeItem<String>("Memory Usage");
		TreeItem<String> memoryUsageTreeItemTotalMemory = new TreeItem<String>("Total Memory");
		TreeItem<String> memoryUsageTreeItemTotalMemoryEvent = new TreeItem<String>("Total Memory By Event");
		TreeItem<String> memoryUsageTreeItemTotalMemoryUser = new TreeItem<String>("Total Memory By User");
		TreeItem<String> memoryUsageTreeItemTotalMemoryHost = new TreeItem<String>("Total Memory By Host");
		TreeItem<String> memoryUsageTreeItemTotalMemoryThread = new TreeItem<String>("Total Memory By Thread");
		
		memoryUsageTreeItem.getChildren().addAll(memoryUsageTreeItemTotalMemory,memoryUsageTreeItemTotalMemoryEvent,memoryUsageTreeItemTotalMemoryUser,memoryUsageTreeItemTotalMemoryHost,memoryUsageTreeItemTotalMemoryThread);
		
		TreeItem<String> hotspotsIOTreeItem = new TreeItem<String>("Hot Spots for I/O");
		TreeItem<String> hotspotsIOTreeItemActivityReport = new TreeItem<String>("Top File I/O Activity Report");
		TreeItem<String> hotspotsIOTreeItemFileTime = new TreeItem<String>("Top I/O By File By Time");
		TreeItem<String> hotspotsIOTreeItemEventCategory = new TreeItem<String>("Top I/O By Event Category");
		TreeItem<String> hotspotsIOTreeItemTimeEventCategories = new TreeItem<String>("Top I/O In Time By Event Categories");
		TreeItem<String> hotspotsIOTreeItemTimeThread = new TreeItem<String>("Top I/O Time By Uer/Thread");
		
		hotspotsIOTreeItem.getChildren().addAll(hotspotsIOTreeItemActivityReport,hotspotsIOTreeItemFileTime,hotspotsIOTreeItemEventCategory,hotspotsIOTreeItemTimeEventCategories,hotspotsIOTreeItemTimeThread);
		
		TreeItem<String> highCostSQLTreeItem = new TreeItem<String>("High Cost SQL Statements");
		TreeItem<String> highCostSQLTreeItemStmtAnalysis = new TreeItem<String>("Analysis");
		TreeItem<String> highCostSQLTreeItemErrorsWarnings = new TreeItem<String>("With Errors or Warnings");
		TreeItem<String> highCostSQLTreeItemTableScans = new TreeItem<String>("With Full Table Scans");
		TreeItem<String> highCostSQLTreeItem95thPercentile = new TreeItem<String>("With Runtimes in 95th Percentile");
		TreeItem<String> highCostSQLTreeItemSorting = new TreeItem<String>("With Sorting");
		TreeItem<String> highCostSQLTreeItemTempTables = new TreeItem<String>("With Temp Tables");
		
		highCostSQLTreeItem.getChildren().addAll(highCostSQLTreeItemStmtAnalysis,highCostSQLTreeItemErrorsWarnings,highCostSQLTreeItemTableScans,highCostSQLTreeItem95thPercentile,highCostSQLTreeItemSorting
				,highCostSQLTreeItemTempTables);

		TreeItem<String> schemaStatisticsTreeItem = new TreeItem<String>("Database Schema Statistics");
		TreeItem<String> schemaStatisticsTreeItemAutoIncrement = new TreeItem<String>("Auto Increment Columns");
		TreeItem<String> schemaStatisticsTreeItemFlattenedKeys = new TreeItem<String>("Flattened Keys");
		TreeItem<String> schemaStatisticsTreeItemIndex = new TreeItem<String>("Index Statistics");
		TreeItem<String> schemaStatisticsTreeItemObject = new TreeItem<String>("Object Overview");
		TreeItem<String> schemaStatisticsTreeItemRedundantIndexes = new TreeItem<String>("Redundant Indexes");
		TreeItem<String> schemaStatisticsTreeItemTableLoackWaits = new TreeItem<String>("Table Lock Waits");
		TreeItem<String> schemaStatisticsTreeItemTableSatictics = new TreeItem<String>("Table Statistics");
		TreeItem<String> schemaStatisticsTreeItemTableStatwithBuffer = new TreeItem<String>("Table Statics with Buffer");
		TreeItem<String> schemaStatisticsTreeItemTableFullScans = new TreeItem<String>("Tables With Full Table Scans");
		TreeItem<String> schemaStatisticsTreeItemUnusedIndexes = new TreeItem<String>("Unused Indexes");
		
		schemaStatisticsTreeItem.getChildren().addAll(schemaStatisticsTreeItemAutoIncrement,schemaStatisticsTreeItemFlattenedKeys,schemaStatisticsTreeItemIndex,schemaStatisticsTreeItemObject
				,schemaStatisticsTreeItemRedundantIndexes,schemaStatisticsTreeItemTableLoackWaits,schemaStatisticsTreeItemTableSatictics,schemaStatisticsTreeItemTableStatwithBuffer,schemaStatisticsTreeItemTableFullScans,schemaStatisticsTreeItemUnusedIndexes);
		
		TreeItem<String> waitTimesTreeItem = new TreeItem<String>("Wait Times");
		TreeItem<String> waitTimesTreeItemGlobalWait = new TreeItem<String>("Global Waits By Time ");
		TreeItem<String> waitTimesTreeItemUserTime = new TreeItem<String>("Wait By User By Time");
		TreeItem<String> waitTimesTreeItemHostTime = new TreeItem<String>("Wait By Host By Time");
		TreeItem<String> waitTimesTreeItemClassesTime = new TreeItem<String>("Wait Classes By Time");
		TreeItem<String> waitTimesTreeItemClassesAvgTime = new TreeItem<String>("Wait Classes By Avg Time");
		
		waitTimesTreeItem.getChildren().addAll(waitTimesTreeItemGlobalWait,waitTimesTreeItemUserTime,waitTimesTreeItemHostTime,waitTimesTreeItemClassesTime,waitTimesTreeItemClassesAvgTime);
		
		TreeItem<String> innoDBStatisticsTreeItem = new TreeItem<String>("InnoDB Statistics");
		TreeItem<String> innoDBStatisticsTreeItemSchemaStats = new TreeItem<String>("Buffer Stats By Schema");
		TreeItem<String> innoDBStatisticsTreeItemTableStats = new TreeItem<String>("Buffer Stats By Table");
		TreeItem<String> innoDBStatisticsTreeItemLockWaits = new TreeItem<String>("Lock Waits");

		innoDBStatisticsTreeItem.getChildren().addAll(innoDBStatisticsTreeItemSchemaStats,innoDBStatisticsTreeItemTableStats,innoDBStatisticsTreeItemLockWaits);

		TreeItem<String> userUtilizationTreeItem = new TreeItem<String>("User Resource Utilization");
		TreeItem<String> userUtilizationTreeItemSummary= new TreeItem<String>("User Summary");
		TreeItem<String> userUtilizationTreeItemIOStats= new TreeItem<String>("User File I/O Summary");
		TreeItem<String> userUtilizationTreeItemSummaryIOTypeStats= new TreeItem<String>("User File I/O Type Summary");
		TreeItem<String> userUtilizationTreeItemSummaryStageStats= new TreeItem<String>("User Stages Summary");
		TreeItem<String> userUtilizationTreeItemSummaryStmtTime= new TreeItem<String>("User Statement Time Summary");
		TreeItem<String> userUtilizationTreeItemSummaryStmtType= new TreeItem<String>("User Statement Type Summary");
		
		userUtilizationTreeItem.getChildren().addAll(userUtilizationTreeItemSummary,userUtilizationTreeItemIOStats,userUtilizationTreeItemIOStats,userUtilizationTreeItemSummaryIOTypeStats,userUtilizationTreeItemSummaryStageStats
				,userUtilizationTreeItemSummaryStmtTime,userUtilizationTreeItemSummaryStmtType);
		

		TreeItem<String> hostUtilizationTreeItem = new TreeItem<String>("Host Resource Utilization");
		TreeItem<String> hostUtilizationTreeItemSummary= new TreeItem<String>("Host Summary");
		TreeItem<String> hostUtilizationTreeItemSummaryIOStats= new TreeItem<String>("Host File I/O Summary");
		TreeItem<String> hostUtilizationTreeItemSummaryIOTypeStats= new TreeItem<String>("Host File I/O Type Summary");
		TreeItem<String> hostUtilizationTreeItemSummaryStagesStmt= new TreeItem<String>("Host Stages Summary");
		TreeItem<String> hostUtilizationTreeItemSummaryStmtTime= new TreeItem<String>("Host Statement Time Summary");
		TreeItem<String> hostUtilizationTreeItemSummaryStmtType= new TreeItem<String>("Host Statement Type Summary");
		
		hostUtilizationTreeItem.getChildren().addAll(hostUtilizationTreeItemSummary,hostUtilizationTreeItemSummaryIOStats,hostUtilizationTreeItemSummaryIOTypeStats,hostUtilizationTreeItemSummaryStagesStmt
				,hostUtilizationTreeItemSummaryStmtTime,hostUtilizationTreeItemSummaryStmtType);
		
		TreeItem<String> otherInformationTreeItem = new TreeItem<String>("Other Information");
		TreeItem<String> versionTreeItem = new TreeItem<String>("Version");
		TreeItem<String> sessionTreeItem = new TreeItem<String>("Session Info");
		TreeItem<String> latestFileioTreeItem = new TreeItem<String>("Latest File I/O");
		TreeItem<String> systemConfigTreeItem = new TreeItem<String>("System Config");
		TreeItem<String> sessionSSLStatusConfigTreeItem = new TreeItem<String>("Session SSL Status");
		TreeItem<String> metricsTreeItem = new TreeItem<String>("Metrics");
		TreeItem<String> processListTreeItem = new TreeItem<String>("Process List");
		TreeItem<String> checkLostInstrumentationTreeItem = new TreeItem<String>("Check Lost Instrumentation"); 
		
		otherInformationTreeItem.getChildren().addAll(versionTreeItem,sessionTreeItem,latestFileioTreeItem,systemConfigTreeItem,sessionSSLStatusConfigTreeItem,metricsTreeItem
				,processListTreeItem,checkLostInstrumentationTreeItem);
		
		rootReportsTreeItem.getChildren().addAll(memoryUsageTreeItem,hotspotsIOTreeItem,highCostSQLTreeItem,schemaStatisticsTreeItem,waitTimesTreeItem,innoDBStatisticsTreeItem
				,userUtilizationTreeItem,hostUtilizationTreeItem,otherInformationTreeItem);
		
		return performanceView;
	}
	 
}

class EditingCell extends TableCell<Map, String> {
	 
    private TextField textField;

    public EditingCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText((String) getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
        
        textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, 
                Boolean arg1, Boolean arg2) {
                    if (!arg2) {  // Comment out this part then it will be editable but never persisted
                       //  commitEdit(textField.getText());  for now no need to commit any cell value
                    }
            }
        });
    }
    
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}

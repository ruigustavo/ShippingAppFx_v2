/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import pt.uc.dei.as.controller.OrderOverviewController;
import pt.uc.dei.as.entity.Order;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

// TODO: Auto-generated Javadoc
/**
 * The Class MainApp.
 */
public class MainApp extends Application {

	/** The primary stage. */
	private Stage primaryStage;
	
	/** The root layout. */
	private BorderPane rootLayout;

	/** The em. */
	public static EntityManager em;
	
	/** The pm. */
	private static PersistenceManager pm;

	/** The orders data. */
	private ObservableList<Order> ordersData = FXCollections.observableArrayList();

	/**
	 * Instantiates a new main app.
	 *
	 * @throws Exception the exception
	 */
	public MainApp() throws Exception {

	}



	/**
	 * Gets the orders data.
	 *
	 * @return the orders data
	 */
	public ObservableList<Order> getOrdersData() {
		return ordersData;
	}

	/**
	 * Sets the orders data.
	 *
	 * @param ordersData the new orders data
	 */
	public void setOrdersData(List<Order> ordersData) {
		this.ordersData.setAll(ordersData);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("ShippingAppFX");
		this.primaryStage.getIcons().add(new Image("/images/sprout.png"));
		Platform.setImplicitExit(true);

		try {
			TypedQuery<Order> query = MainApp.em.createNamedQuery("Order.findAll", Order.class);
			ordersData.setAll(query.getResultList());
		} catch (Exception e) {
			AlertUtil.alert("Could not complete the operation", "Something is wrong!", "Try again or restart the application");
			MainApp.refreshEm();
		}

		initRootLayout();
		showOrderOverview();
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public void stop() throws Exception {
		super.stop();
		Platform.exit();
		System.exit(0);
	}

	/**
	 * Inits the root layout.
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			rootLayout.getStylesheets().add("/stylesheets/DarkTheme.css");

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show order overview.
	 */
	public void showOrderOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/OrderOverview.fxml"));
			AnchorPane orderOverview = (AnchorPane) loader.load();
			orderOverview.getStylesheets().add("/stylesheets/DarkTheme.css");

			rootLayout.setCenter(orderOverview);
			OrderOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Gets the primary stage.
	 *
	 * @return the primary stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Refresh em.
	 */
	public static void refreshEm() {
		MainApp.em.close();
		MainApp.em = MainApp.pm.getEntityManager();
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			if (args != null && args.length == 4) {
				MainApp.pm = new PersistenceManager(args[0], args[1], args[2], args[3]);
			} else if (args != null && args.length == 1) {
				MainApp.pm = new PersistenceManager(args[0]);
			} else {
				MainApp.pm = new PersistenceManager();
			}
			MainApp.em = pm.getEntityManager();
		} catch (Exception e) {
			System.err.println(
					"\nShippingAppFX: Cannot connect to the \"ete_db\" database!. Please check if the database is running and properly configured.\n");
			System.err.println(
					"ShippingAppFX: To bypass the default database configuration please use the following command:");
			System.err.println("java -jar ShippingAppFX-0.0.1.jar <driver> <url> <username> <password> \n");
			System.err.println("ShippingAppFX: Alternatively, just pass the database server IP:");
			System.err.println("java -jar ShippingAppFX-0.0.1.jar <db_server_IP> \n");
			System.exit(-1);
		}
		launch(args);
	}

}

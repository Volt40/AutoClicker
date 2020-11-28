package volt4.autoclicker.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    
    private static AutoClickerController autoClickerController;
    
    private static AutoClickerDaemon autoClicker;
    private static Thread daemonThread;
    
    private static Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        autoClickerController = new AutoClickerController();
    
        final double[] offset = {0, 0};
        autoClickerController.getDraggableElement().setOnMousePressed(event -> {
            offset[0] = event.getSceneX();
            offset[1] = event.getSceneY();
        });
        autoClickerController.getDraggableElement().setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - offset[0]);
            primaryStage.setY(event.getScreenY() - offset[1]);
        });
    
        TextField autoFocusIsStupid = new TextField();
        autoFocusIsStupid.setLayoutX(5);
        autoFocusIsStupid.setLayoutY(5);
        autoFocusIsStupid.setPrefSize(2, 2);
        autoClickerController.getChildren().add(0, autoFocusIsStupid);
        
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(autoClickerController));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Auto Clicker");
        primaryStage.getIcons().add(new Image(Main.class.getClassLoader().getResourceAsStream("icon.png")));
        primaryStage.show();
        
        this.primaryStage = primaryStage;
    }
    
    public static AutoClickerController getController() {
        return autoClickerController;
    }
    
    public static AutoClickerDaemon getAutoClicker() {
        return autoClicker;
    }
    
    private static void setupAutoClickerDaemon() throws NativeHookException {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        autoClicker = new AutoClickerDaemon();
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(autoClicker);
        GlobalScreen.addNativeMouseListener(autoClicker);
    }
    
    private static void startDaemon() {
        daemonThread = new Thread(autoClicker);
        daemonThread.setDaemon(true);
        daemonThread.start();
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void main(String[] args) throws Exception {
        setupAutoClickerDaemon();
        startDaemon();
        launch();
    }
    
}

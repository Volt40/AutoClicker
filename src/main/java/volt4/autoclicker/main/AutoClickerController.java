package volt4.autoclicker.main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class AutoClickerController extends AnchorPane {
    
    public static final double DEFAULT_TIME = 1000;
    public static final double DEFAULT_RANDOM = 0;
    
    private boolean enabled = false;
    private double timeBetweenClicks;
    private double timeBetweenClicksRandom;
    private double pressReleaseDelay;
    private double pressReleaseDelayRandom;
    
    private boolean lookingForInput;
    private boolean lookingForOnInput;
    private boolean lookingForOffInput;
    private boolean justReceivedOnInput;
    private boolean justReceivedOffInput;
    private boolean useMouseForOn;
    private boolean useMouseForOff;
    
    private int onButton;
    private int offButton;
    
    public AutoClickerController() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("AutoClickerLayout.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        closeButton.setOnMouseDragged(event -> event.consume());
        minimizeButton.setOnMouseDragged(event -> event.consume());
        disableEnableBackground.setOnMouseDragged(event -> event.consume());
        
        timeBetweenClicksPrimary.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                timeBetweenClicks = Double.parseDouble(newValue);
            } catch(Exception e) {
                timeBetweenClicks = DEFAULT_TIME;
            }
        });
        timeBetweenClicksSecondary.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                timeBetweenClicksRandom = Double.parseDouble(newValue);
            } catch(Exception e) {
                timeBetweenClicksRandom = DEFAULT_RANDOM;
            }
        });
        pressReleasePrimary.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                pressReleaseDelay = Double.parseDouble(newValue);
            } catch(Exception e) {
                pressReleaseDelay = DEFAULT_TIME;
            }
        });
        pressReleaseSecondary.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                pressReleaseDelayRandom = Double.parseDouble(newValue);
            } catch(Exception e) {
                pressReleaseDelayRandom = DEFAULT_RANDOM;
            }
        });
    }
    
    private static void fixTextAnchor(Text text) {
        AnchorPane.setLeftAnchor(text, (text.getParent().getLayoutBounds().getWidth() / 2d) - (text.getLayoutBounds().getWidth() / 2d));
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public double getTimeBetweenClicks() {
        return timeBetweenClicks;
    }
    
    public double getTimeBetweenClicksRandom() {
        return timeBetweenClicksRandom;
    }
    
    public double getPressReleaseDelay() {
        return pressReleaseDelay;
    }
    
    public double getPressReleaseDelayRandom() {
        return pressReleaseDelayRandom;
    }
    
    public AnchorPane getDraggableElement() {
        return toolBar;
    }
    
    public boolean isLookingForInput() {
        return lookingForInput;
    }
    
    public int getOnButton() {
        return onButton;
    }
    
    public int getOffButton() {
        return offButton;
    }
    
    public boolean isUseMouseForOn() {
        return useMouseForOn;
    }
    
    public boolean isUseMouseForOff() {
        return useMouseForOff;
    }
    
    public void receiveInput(int input, boolean isMouse) {
        if (lookingForOnInput) {
            justReceivedOnInput = true;
            onButton = input;
            useMouseForOn = isMouse;
            if (isMouse)
                toggleOnButtonText.setText("Button " + input);
            else
                toggleOnButtonText.setText(KeyEvent.getKeyText(input));
            fixTextAnchor(toggleOnButtonText);
            lookingForOnInput = false;
        } else if (lookingForOffInput) {
            justReceivedOffInput = true;
            offButton = input;
            useMouseForOff = isMouse;
            if (isMouse)
                toggleOffButtonText.setText("Button " + input);
            else
                toggleOffButtonText.setText(KeyEvent.getKeyText(input));
            fixTextAnchor(toggleOffButtonText);
            lookingForOffInput = false;
        }
        lookingForInput = false;
        timeBetweenClicksPrimary.setDisable(false);
        timeBetweenClicksSecondary.setDisable(false);
        pressReleasePrimary.setDisable(false);
        pressReleaseSecondary.setDisable(false);
    }
    
    @FXML
    private AnchorPane toolBar;
    
    @FXML
    private AnchorPane closeButton;
    
    @FXML
    private AnchorPane minimizeButton;
    
    @FXML
    private AnchorPane disableEnableForeground;
    
    @FXML
    private AnchorPane disableEnableBackground;
    
    @FXML
    private Text disableEnableText;
    
    @FXML
    private AnchorPane toggleOnButtonForeground;
    
    @FXML
    private Text toggleOnButtonText;
    
    @FXML
    private AnchorPane toggleOffButtonForeground;
    
    @FXML
    private Text toggleOffButtonText;
    
    @FXML
    private TextField timeBetweenClicksPrimary;
    
    @FXML
    private TextField timeBetweenClicksSecondary;
    
    @FXML
    private TextField pressReleasePrimary;
    
    @FXML
    private TextField pressReleaseSecondary;
    
    @FXML
    void closeEnter(MouseEvent event) {
        closeButton.setStyle("-fx-background-radius: 3; -fx-background-color: linear-gradient(to right bottom, #e35959, #f04646);");
    }
    
    @FXML
    void closeExit(MouseEvent event) {
        closeButton.setStyle("-fx-background-radius: 3; -fx-background-color: linear-gradient(to right bottom, #2f9dd4, #00e1ff);");
    }
    
    @FXML
    void disableEnableEnter(MouseEvent event) {
        disableEnableForeground.setStyle("-fx-background-radius: 5; -fx-background-color: #43494d;");
    }
    
    @FXML
    void disableEnableExit(MouseEvent event) {
        disableEnableForeground.setStyle("-fx-background-radius: 5; -fx-background-color: #2d3134;");
    }
    
    @FXML
    void minimizeEnter(MouseEvent event) {
        minimizeButton.setStyle("-fx-background-radius: 3; -fx-background-color: linear-gradient(to right bottom, #e35959, #f04646);");
    }
    
    @FXML
    void minimizeExit(MouseEvent event) {
        minimizeButton.setStyle("-fx-background-radius: 3; -fx-background-color: linear-gradient(to right bottom, #2f9dd4, #00e1ff);");
    }
    
    @FXML
    void onClose(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }
    
    @FXML
    void onMinimize(MouseEvent event) {
        Main.getPrimaryStage().setIconified(true);
    }
    
    @FXML
    void onDisableEnable(MouseEvent event) {
        if (enabled){
            disableEnableBackground.setStyle("-fx-background-radius: 5; -fx-background-color:  linear-gradient(to right bottom, #e80505, #f04646);");
            disableEnableText.setStyle("-fx-fill: linear-gradient(to right bottom, #e80505, #f04646);");
            disableEnableText.setText("Disabled");
            enabled = false;
            Main.getAutoClicker().turnOff();
        } else {
            disableEnableBackground.setStyle("-fx-background-radius: 5; -fx-background-color: linear-gradient(to right bottom, #7fe38e, #1ed93a);");
            disableEnableText.setStyle("-fx-fill: linear-gradient(to right bottom, #7fe38e, #1ed93a);");
            disableEnableText.setText("Enabled");
            enabled = true;
        }
        fixTextAnchor(disableEnableText);
    }
    
    @FXML
    void onToggleOff(MouseEvent event) {
        if (justReceivedOffInput || lookingForOffInput) {
            if (!lookingForOffInput)
                justReceivedOffInput = false;
            return;
        }
        lookingForOffInput = true;
        lookingForInput = true;
        toggleOffButtonText.setText("~");
        fixTextAnchor(toggleOffButtonText);
        timeBetweenClicksPrimary.setDisable(true);
        timeBetweenClicksSecondary.setDisable(true);
        pressReleasePrimary.setDisable(true);
        pressReleaseSecondary.setDisable(true);
    }
    
    @FXML
    void onToggleOn(MouseEvent event) {
        if (justReceivedOnInput || lookingForOnInput) {
            if (!lookingForOnInput)
                justReceivedOnInput = false;
            return;
        }
        lookingForOnInput = true;
        lookingForInput = true;
        toggleOnButtonText.setText("~");
        fixTextAnchor(toggleOnButtonText);
        timeBetweenClicksPrimary.setDisable(true);
        timeBetweenClicksSecondary.setDisable(true);
        pressReleasePrimary.setDisable(true);
        pressReleaseSecondary.setDisable(true);
    }
    
    @FXML
    void toggleOffEnter(MouseEvent event) {
        toggleOffButtonForeground.setStyle("-fx-background-radius: 5; -fx-background-color: #43494d;");
    }
    
    @FXML
    void toggleOffExit(MouseEvent event) {
        toggleOffButtonForeground.setStyle("-fx-background-radius: 5; -fx-background-color: #2d3134;");
    }
    
    @FXML
    void toggleOnEnter(MouseEvent event) {
        toggleOnButtonForeground.setStyle("-fx-background-radius: 5; -fx-background-color: #43494d;");
    }
    
    @FXML
    void toggleOnExit(MouseEvent event) {
        toggleOnButtonForeground.setStyle("-fx-background-radius: 5; -fx-background-color: #2d3134;");
    }
    
}

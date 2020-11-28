package volt4.autoclicker.main;

import javafx.application.Platform;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.SwingKeyAdapter;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class AutoClickerDaemon extends SwingKeyAdapter implements Runnable, NativeMouseListener {
    
    private Robot robot;
    private boolean on;
    
    private static void delay(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void turnOff() {
        on = false;
    }
    
    @Override
    public void run() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            Platform.exit();
            System.exit(1);
        }
        while(true) {
            try {
                delay(1); // Makes it work, idk why.
                if (Main.getController().isEnabled()) {
                    if (on) {
                        robot.mousePress(InputEvent.getMaskForButton(1));
                        long delay = Math.round(Main.getController().getPressReleaseDelay());
                        delay += Math.random() * Math.round(Main.getController().getPressReleaseDelayRandom());
                        delay(delay);
                        robot.mouseRelease(InputEvent.getMaskForButton(1));
                    }
                    long delay = Math.round(Main.getController().getTimeBetweenClicks());
                    delay += Math.random() * Math.round(Main.getController().getTimeBetweenClicksRandom());
                    delay(delay);
                }
            } catch (NullPointerException e) { }
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (Main.getController().isLookingForInput()) {
            Main.getController().receiveInput(e.getKeyCode(), false);
            return;
        } else if (Main.getController().isEnabled() && !Main.getController().isUseMouseForOn() && e.getKeyCode() == Main.getController().getOnButton())
            on = true;
        else if (Main.getController().isEnabled() && !Main.getController().isUseMouseForOff() && e.getKeyCode() == Main.getController().getOffButton())
            on = false;
    }
    
    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        if (Main.getController().isLookingForInput()) {
            Main.getController().receiveInput(e.getButton(), true);
            return;
        } else if (Main.getController().isEnabled() && Main.getController().isUseMouseForOn() && e.getButton() == Main.getController().getOnButton())
            on = true;
        else if (Main.getController().isEnabled() && Main.getController().isUseMouseForOff() && e.getButton() == Main.getController().getOffButton())
            on = false;
    }
    
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) { }
    
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) { }
    
    @Override
    public void nativeMousePressed(NativeMouseEvent e) { }
    
    @Override
    public void nativeMouseReleased(NativeMouseEvent e) { }
    
}

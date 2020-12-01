/* 
 * This code is property of Josiah Daniel Ampian.
 * If found, please notify him at josiah.ampian@gmail.com
 * All right reserved by Josiah Daniel Ampian.
 */
package projecty.Externals;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

public class PanelController {
    
    private BufferedImage myScreen;
    private Image background;
    private BackgroundPanel canvas;
    
    public PanelController(Image iImage, BackgroundPanel iPanel) {
        background = iImage;
        canvas = iPanel;
        
        myScreen = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Thread updater = new Thread(GraphicsRunnable);
        updater.start();
    }
    
    public Runnable GraphicsRunnable = () -> {
        Timer graphicsTimer = new Timer();
        TimerTask graphicsTask = new TimerTask() {
            @Override public void run() {
                repaint();
            }
        };
        
        graphicsTimer.schedule(graphicsTask, 0, 10);
    };
    
    private void repaint() {
        Graphics g = myScreen.getGraphics();
        
        g.drawImage(background, 0, 0, null);
        
        
        
        
        background = myScreen.getScaledInstance(myScreen.getWidth(), myScreen.getHeight(), 0);
        canvas.setImage(myScreen);
    }
}

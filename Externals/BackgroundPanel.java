/* 
 * This code is property of Josiah Daniel Ampian.
 * If found, please notify him at josiah.ampian@gmail.com
 * All right reserved by Josiah Daniel Ampian.
 */
package projecty.Externals;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel{
    private Image image;
    
    public BackgroundPanel(int x, int y) {
        image = new BufferedImage(x, y, BufferedImage.TYPE_BYTE_GRAY);
    }
    
    public void setImage(Image image) {
        this.image = image;
        repaint();
    }
    
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}

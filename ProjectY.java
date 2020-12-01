/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projecty;

import java.awt.Image;
import projecty.Externals.ResizableFrame;
import javax.swing.ImageIcon;
import projecty.Externals.PanelController;


public class ProjectY {
    
    public static void main(String[] args) {
        ResizableFrame myFrame = new ResizableFrame();
        
        Image sImage = new ImageIcon(ProjectY.class.getResource("Resources/Art/Skyline.png")).getImage();
        
        PanelController myPanel = new PanelController(sImage, myFrame.contentPanel);
    }
    
}

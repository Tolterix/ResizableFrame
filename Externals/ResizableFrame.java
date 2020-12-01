/* 
 * This code is property of Josiah Daniel Ampian.
 * If found, please notify him at josiah.ampian@gmail.com
 * All right reserved by Josiah Daniel Ampian.
 */
package projecty.Externals;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.lang.reflect.*;
import javax.swing.*;

public class ResizableFrame extends JFrame{
    
    public int[] frameSize;
    public BackgroundPanel contentPanel;
    
    private static ImageIcon ExitIcon = null;
    private static ImageIcon MaxiIcon = null;
    private static ImageIcon MiniIcon = null;
    private static ImageIcon LogoIcon = null;
    
    public ResizableFrame() {
        initialSetup();
        addButtons();
        CreateTitleBar();
        finalSetup();
    }
    
    private static Method resizeMethod = null;
    
    public ResizableFrame(Method iMethod) {
        resizeMethod = iMethod;
    }
    
    public ResizableFrame(ImageIcon[] iImages) {
        ExitIcon = iImages[0];
        MaxiIcon = iImages[1];
        MiniIcon = iImages[2];
        LogoIcon = iImages[3];
        
        initialSetup();
        addButtons();
        CreateTitleBar();
        finalSetup();
    }
    
    private void initialSetup() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frameSize = new int[]{(int)(screenSize.width/1.25), (int)(screenSize.height/1.25)};
        this.setLocation(((screenSize.width/2) - (frameSize[0]/2)), ((screenSize.height/2) - (frameSize[1]/2)));
        
        contentPanel = new BackgroundPanel(frameSize[0], frameSize[1]);
        contentPanel.setLayout(new BorderLayout());
        
        this.setPreferredSize(new Dimension(frameSize[0], frameSize[1]));
        this.setAutoRequestFocus(true);
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
    }
    
    private void addButtons() {
        JButton northButton = new JButton();
        northButton.setBorderPainted(false);
        northButton.setBackground(Color.black);
        northButton.setPreferredSize(new Dimension(frameSize[0], 3));
        northButton.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
        northButton.addMouseMotionListener(new MouseMotionListener() {
            @Override public void mouseDragged(MouseEvent e) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double screenAspect = ((double)screenSize.width/(double)screenSize.height);
                int bY = getFrame().getHeight();
                getFrame().setSize((int)((getFrame().getHeight() + getFrame().getY() - e.getYOnScreen())*screenAspect), (getFrame().getHeight() + getFrame().getY() - e.getYOnScreen()));
                getFrame().setLocation(getFrame().getX(), getFrame().getY() - (getFrame().getHeight() - bY));
                getFrame().getContentPane().revalidate();
                getFrame().sizeFrame();
            }
            @Override public void mouseMoved(MouseEvent e) {}
        });
        this.add(northButton, BorderLayout.NORTH);
        
        JButton westButton = new JButton();
        westButton.setBorderPainted(false);
        westButton.setBackground(Color.black);
        westButton.setPreferredSize(new Dimension(3, frameSize[1]));
        westButton.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
        westButton.addMouseMotionListener(new MouseMotionListener() {
            @Override public void mouseDragged(MouseEvent e) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double screenAspect = ((double)screenSize.width/(double)screenSize.height);
                int bX = getFrame().getWidth();
                getFrame().setSize((getFrame().getWidth() + getFrame().getX() - e.getXOnScreen()), (int)((getFrame().getWidth() + getFrame().getX() - e.getXOnScreen())/screenAspect));
                getFrame().setLocation(getFrame().getX() - (getFrame().getWidth() - bX), getFrame().getY());
                getFrame().getContentPane().revalidate();
                getFrame().sizeFrame();
            }
            @Override public void mouseMoved(MouseEvent e) {}
        });
        this.add(westButton, BorderLayout.WEST);
        
        JButton southButton = new JButton();
        southButton.setBorderPainted(false);
        southButton.setBackground(Color.black);
        southButton.setPreferredSize(new Dimension(frameSize[0], 3));
        southButton.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
        southButton.addMouseMotionListener(new MouseMotionListener() {
            @Override public void mouseDragged(MouseEvent e) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double screenAspect = ((double)screenSize.width/(double)screenSize.height);
                getFrame().setSize((int)((e.getYOnScreen() - getFrame().getY())*screenAspect), (e.getYOnScreen() - getFrame().getY()));
                getFrame().getContentPane().revalidate();
                getFrame().sizeFrame();
            }
            @Override public void mouseMoved(MouseEvent e) {}
        });
        this.add(southButton, BorderLayout.SOUTH);
        
        JButton eastButton = new JButton();
        eastButton.setBorderPainted(false);
        eastButton.setBackground(Color.black);
        eastButton.setPreferredSize(new Dimension(3, frameSize[1]));
        eastButton.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        eastButton.addMouseMotionListener(new MouseMotionListener() {
            @Override public void mouseDragged(MouseEvent e) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double screenAspect = ((double)screenSize.width/(double)screenSize.height);
                getFrame().setSize((e.getXOnScreen() - getFrame().getX()), (int)((e.getXOnScreen() - getFrame().getX())/screenAspect));
                getFrame().getContentPane().revalidate();
                getFrame().sizeFrame();
            }
            @Override public void mouseMoved(MouseEvent e) {}
        });
        this.add(eastButton, BorderLayout.EAST);
        
        //this code will make the app super annoying, always on top will make it the top application always
        //this.setAlwaysOnTop(true);
        /*
        this.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {}
            //this code will make the app taskbar flash to get focus
            @Override public void focusLost(FocusEvent e) {e.getComponent().requestFocus();}
        });
        */
    }
    
    private void finalSetup() {
        contentPanel.add(TitleBar, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.requestFocus();
    }
    
    private static final JPanel TitleBar            = new JPanel(new GridBagLayout());
    private static final JLabel TitleBarLabel       = new JLabel("   ");
    private static final JButton MinimizeButton     = new JButton("");
    private static final JLabel MaximizeButtonSpace = new JLabel("     ");
    private static final JButton MaximizeButton     = new JButton("");
    private static final JLabel ExitButtonSpace     = new JLabel("     ");
    private static final JButton ExitButton         = new JButton("");
    
    private int mTrackerX = 0;
    private int mTrackerY = 0;
    
    private int titleBarSize = 13;
    
    private void CreateTitleBar() {
        if (ExitIcon == null) {
            ExitIcon = drawSingleColorImage(titleBarSize, titleBarSize, new Color(255, 0, 0));
        }
        if (MaxiIcon == null) {
            MaxiIcon = drawSingleColorImage(titleBarSize, titleBarSize, new Color(0, 255, 0));
        }
        if (MiniIcon == null) {
            MiniIcon = drawSingleColorImage(titleBarSize, titleBarSize, new Color(0, 0, 255));
        }
        if (LogoIcon == null) {
            LogoIcon = drawSingleColorImage(titleBarSize, titleBarSize, new Color(200, 200, 200));
        }
        
        ExitButton.setSize(titleBarSize, titleBarSize);
        Image ExitImageInit = ExitIcon.getImage();
        Image ExitImage = ExitImageInit.getScaledInstance(titleBarSize, titleBarSize, java.awt.Image.SCALE_SMOOTH);
        ImageIcon tExitIcon = new ImageIcon(ExitImage);
        ExitButton.setIcon(tExitIcon);
        ExitButton.setMargin(new Insets(0, 0, 0, 0));
        ExitButton.setBackground(null);
        ExitButton.setBorder(null);
        ExitButton.setFocusPainted(false);
        ExitButton.setContentAreaFilled(false);
        
        ExitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        
        MaximizeButton.setSize(titleBarSize, titleBarSize);
        Image MaximizeImageInit = MaxiIcon.getImage();
        Image MaximizeLogo = MaximizeImageInit.getScaledInstance(titleBarSize, titleBarSize, java.awt.Image.SCALE_SMOOTH);
        ImageIcon MaximizeIcon = new ImageIcon(MaximizeLogo);
        MaximizeButton.setIcon(MaximizeIcon);
        MaximizeButton.setMargin(new Insets(0,0,0,0));
        MaximizeButton.setBackground(null);
        MaximizeButton.setBorder(null);
        MaximizeButton.setFocusPainted(false);
        MaximizeButton.setContentAreaFilled(false);
        
        MaximizeButton.addActionListener((ActionEvent e) -> {
            getFrame().setSize(Toolkit.getDefaultToolkit().getScreenSize());
            getFrame().sizeFrame();
        });
        
        MinimizeButton.setSize(titleBarSize, titleBarSize);
        Image MinimizeImageInit = MiniIcon.getImage();
        Image MinimizeLogo = MinimizeImageInit.getScaledInstance(titleBarSize, titleBarSize, java.awt.Image.SCALE_SMOOTH);
        ImageIcon MinimizeIcon = new ImageIcon(MinimizeLogo);
        MinimizeButton.setIcon(MinimizeIcon);
        MinimizeButton.setMargin(new Insets(0,0,0,0));
        MinimizeButton.setBackground(null);
        MinimizeButton.setBorder(null);
        MinimizeButton.setFocusPainted(false);
        MinimizeButton.setContentAreaFilled(false);
        
        MinimizeButton.addActionListener((ActionEvent e) -> {
            getFrame().setState(Frame.ICONIFIED);
        });
        
        Image tempLogo = LogoIcon.getImage();
        Image finalImage = tempLogo.getScaledInstance((titleBarSize*3), titleBarSize, java.awt.Image.SCALE_SMOOTH);
        ImageIcon Logo = new ImageIcon(finalImage);
        
        TitleBarLabel.setIcon(Logo);
        TitleBarLabel.setForeground(Color.white);
        TitleBarLabel.setIconTextGap(frameSize[0] - ((titleBarSize*6)+60));
        
        TitleBar.setPreferredSize(new Dimension(getFrame().getWidth(), (titleBarSize+4)));
        TitleBar.setLocation(0, 0);
        TitleBar.setBackground(Color.black);
        TitleBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, (new java.awt.Color(0, 0, 0))));
        TitleBar.add(TitleBarLabel);
        TitleBar.add(MinimizeButton);
        TitleBar.add(MaximizeButtonSpace);
        TitleBar.add(MaximizeButton);
        TitleBar.add(ExitButtonSpace);
        TitleBar.add(ExitButton);
        
        TitleBar.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                mTrackerX = e.getXOnScreen();
                mTrackerY = e.getYOnScreen();
            }
            @Override public void mousePressed(MouseEvent e) {
                mTrackerX = e.getXOnScreen();
                mTrackerY = e.getYOnScreen();
            }
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });
        
        TitleBar.addMouseMotionListener(new MouseMotionListener () {
            @Override public void mouseDragged(MouseEvent e) {
                int mousePositionX = e.getXOnScreen() - mTrackerX;
                int mousePositionY = e.getYOnScreen() - mTrackerY;
                int newX = getFrame().getLocation().x + mousePositionX;
                int newY = getFrame().getLocation().y + mousePositionY;
                mTrackerX = e.getXOnScreen();
                mTrackerY = e.getYOnScreen();
                getFrame().setLocation(new Point(newX, newY));
            }
            @Override public void mouseMoved(MouseEvent e) {}
        });
    }
    
    private ImageIcon drawSingleColorImage(int x, int y, Color iColor) {
        BufferedImage tImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        Graphics tGraphics = tImage.getGraphics();
        tGraphics.setColor(iColor);
        tGraphics.fillRect(0, 0, x, y);
        return new ImageIcon(tImage);
    }
    
    public ResizableFrame getFrame() {
        return this;
    }
    
    public void sizeFrame() {
        int width = this.getWidth();
        int height = this.getHeight();
        int fontSize = (int)(Math.pow(width, .45));
        
        if (width > ((titleBarSize*6)+60)) {
            TitleBarLabel.setIconTextGap(width-((titleBarSize*6)+60));
        } else {
            TitleBarLabel.setIconTextGap(0);
        }
        
        if (resizeMethod != null) {
            try {
                resizeMethod.invoke(this, null);
            } catch (IllegalAccessException e) {
            } catch (IllegalArgumentException e) {
            } catch (InvocationTargetException e) {}
        }
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenAspect = ((double)screenSize.width/(double)screenSize.height);
        this.setSize((int)(height*screenAspect), height);
        this.getContentPane().revalidate();
    }
    
}

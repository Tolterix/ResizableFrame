/* 
 * This code is property of Josiah Daniel Ampian.
 * If found, please notify him at josiah.ampian@gmail.com
 * All right reserved by Josiah Daniel Ampian.
 */
package projecty.Externals;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import projecty.ProjectY;

public class CustomTextArea extends JLabel{
    
    private int[] ATOZStart = {0,0};
    private int[] atozStart = {0,330};
    private int[] specialStart = {0,660};
    private int[] SPECIALStart = {0,990};
    
    private int[] charSpace = {60,330};
    
    private int fontSpace = 3;
    private int startingSpace = 10;
    private int lineSpace = 70;
    
    private int[] fontSize = {20,70};
    
    private String delimeter = "/n";
    
    private Image backgroundImage;
    private Image textImage;
    private int[] currentCodes;
    
    private String currentText = "";
    private boolean isScheduled = false;
    private Image[] regularChars = new Image[500];
    private Image[] shiftChars = new Image[500];
    
    public CustomTextArea() {
        try {
            BufferedImage charSet = ImageIO.read(ProjectY.class.getResource("Resources/Art/Lettering_2.0.png"));
            int[] atozTemp = atozStart;
            int[] ATOZTemp = ATOZStart;
            for (int i = 65; i < 91; i++) {
                regularChars[i] = charSet.getSubimage(atozTemp[0], atozTemp[1], charSpace[0], charSpace[1]);
                shiftChars[i] = charSet.getSubimage(ATOZTemp[0], ATOZTemp[1], charSpace[0], charSpace[1]);
                atozTemp[0] = atozTemp[0] + charSpace[0];
                ATOZTemp[0] = ATOZTemp[0] + charSpace[0];
            }
            int[] specialTemp = specialStart;
            int[] SPECIALTemp = SPECIALStart;
            for (int i = 44; i < 222; i++) {
                switch (i) {
                    case (58):
                        i = 59;break;
                    case (60):
                        i = 61; break;
                    case (62):
                        i = 91; break;
                    case (94):
                        i = 192; break;
                    case (193):
                        i = 222; break;
                    default:
                        break;
                }
                regularChars[i] = charSet.getSubimage(specialTemp[0], specialTemp[1], charSpace[0], charSpace[1]);
                shiftChars[i] = charSet.getSubimage(SPECIALTemp[0], SPECIALTemp[1], charSpace[0], charSpace[1]);
                specialTemp[0] = specialTemp[0] + charSpace[0];
                SPECIALTemp[0] = SPECIALTemp[0] + charSpace[0];
            }
            regularChars[32] = charSet.getSubimage((charSet.getWidth() - charSpace[0]), 0, charSpace[0], charSpace[1]);
        } catch (IOException ex) {
            Logger.getLogger(CustomTextArea.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setBackgroundImage(Image image) {
        this.backgroundImage = image;
        repaint();
    }
    
    public void setFontSize(int i, int j) {
        fontSize = new int[] {i,j};
        lineSpace = j;
        redoText();
    }
    
    public String getNewText () {
        return currentText;
    }
    
    public void addText(String addedText) {
        currentText = currentText.concat(addedText);
        currentCodes = new int[currentText.length()];
        for (int i = 0; i < currentText.length(); i++) {
            currentCodes[i] = charToCode(currentText.charAt(i));
        }
        redoText();
    }
    
    public void setNewText(String textToSet) {
        currentText = textToSet;
        currentCodes = new int[currentText.length()];
        for (int i = 0; i < currentText.length(); i++) {
            currentCodes[i] = charToCode(currentText.charAt(i));
        }
        redoText();
    }
    
    public void backspaceText() {
        if (currentText.length() > 1) {
            currentText = currentText.substring(0, currentText.length()-1);
        } else {
            currentText = "";
        }
        currentCodes = new int[currentText.length()];
        for (int i = 0; i < currentText.length(); i++) {
            currentCodes[i] = charToCode(currentText.charAt(i));
        }
        redoText();
    }
    
    private void redoText() {
        if (!isScheduled) {
            Timer redoTextTimer = new Timer();
            TimerTask redoTextTask = new TimerTask() {
                @Override
                public void run() {
                    if (currentText.length() == 0) {
                        textImage = new BufferedImage(1, 1500,BufferedImage.TYPE_INT_ARGB);
                    } else {
                        textImage = new BufferedImage((currentText.length()*(fontSize[0]+10)), 1500,BufferedImage.TYPE_INT_ARGB);
                    }
                    Graphics tGraphics = textImage.getGraphics();

                    if (currentText.contains(delimeter)) {
                        int startIndex = 0;
                        int startingChar = 0;
                        String[] splitLines = currentText.split("/n");
                        int lines = splitLines.length;

                        for (int i = 0; i < lines; i++) {
                            for (int j = 0; j < splitLines[i].length(); j++) {
                                if (startIndex > currentCodes.length) {
                                } else if ((currentCodes[startIndex] == 47) && (currentCodes[startIndex+1] == 78)) {
                                } else if (currentCodes[startIndex] < 0) {
                                    tGraphics.drawImage(shiftChars[currentCodes[startIndex]*-1].getScaledInstance(fontSize[0], fontSize[1], 0), (((startIndex - startingChar)*(fontSize[0]+fontSpace)) + startingSpace), (i*lineSpace), null);
                                } else {
                                    tGraphics.drawImage(regularChars[currentCodes[startIndex]].getScaledInstance(fontSize[0], fontSize[1], 0), (((startIndex - startingChar)*(fontSize[0]+fontSpace)) + startingSpace), (i*lineSpace), null);
                                }
                                startIndex++;
                            }
                            startIndex = startIndex + 2;
                            startingChar = startIndex;
                        }
                    } else {
                        for (int i = 0; i < currentCodes.length; i++) {
                            if (currentCodes[i] < 0) {
                                tGraphics.drawImage(shiftChars[currentCodes[i]*-1].getScaledInstance(fontSize[0], fontSize[1], 0), ((i*(fontSize[0]+fontSpace)) + startingSpace), 0, null);
                            } else {
                                tGraphics.drawImage(regularChars[currentCodes[i]].getScaledInstance(fontSize[0], fontSize[1], 0), ((i*(fontSize[0]+fontSpace)) + startingSpace), 0, null);
                            }
                        }
                    }

                    repaint();
                    tGraphics.dispose();
                    isScheduled = false;
                }
            };
            
            redoTextTimer.schedule(redoTextTask, 50);
            
        }
    }
    
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, 2000, 2000, this);
        g.drawImage(textImage, 0, 0, this);
        
        /*
        File outputfile = new File("test1.png");
        try {
            ImageIO.write((RenderedImage) textImage, "png", outputfile);
        } catch (IOException ex) {
            Logger.getLogger(CustomTextArea.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
    
    private int charToCode(char inputChar) {
        switch (inputChar) {
            case (' '):
                return 32;
            case (','):
                return 44;
            case ('-'):
                return 45;
            case ('.'):
                return 46;
            case ('/'):
                return 47;
            case ('0'):
                return 48;
            case ('1'):
                return 49;
            case ('2'):
                return 50;
            case ('3'):
                return 51;
            case ('4'):
                return 52;
            case ('5'):
                return 53;
            case ('6'):
                return 54;
            case ('7'):
                return 55;
            case ('8'):
                return 56;
            case ('9'):
                return 57;
            case (';'):
                return 59;
            case ('='):
                return 61;
            case ('a'):
                return 65;
            case ('b'):
                return 66;
            case ('c'):
                return 67;
            case ('d'):
                return 68;
            case ('e'):
                return 69;
            case ('f'):
                return 70;
            case ('g'):
                return 71;
            case ('h'):
                return 72;
            case ('i'):
                return 73;
            case ('j'):
                return 74;
            case ('k'):
                return 75;
            case ('l'):
                return 76;
            case ('m'):
                return 77;
            case ('n'):
                return 78;
            case ('o'):
                return 79;
            case ('p'):
                return 80;
            case ('q'):
                return 81;
            case ('r'):
                return 82;
            case ('s'):
                return 83;
            case ('t'):
                return 84;
            case ('u'):
                return 85;
            case ('v'):
                return 86;
            case ('w'):
                return 87;
            case ('x'):
                return 88;
            case ('y'):
                return 89;
            case ('z'):
                return 90;
            case ('['):
                return 91;
            case ('\\'):
                return 92;
            case (']'):
                return 93;
            case ('`'):
                return 192;
            case ('\''):
                return 222;
            case ('<'):
                return -44;
            case ('_'):
                return -45;
            case ('>'):
                return -46;
            case ('?'):
                return -47;
            case (')'):
                return -48;
            case ('!'):
                return -49;
            case ('@'):
                return -50;
            case ('#'):
                return -51;
            case ('$'):
                return -52;
            case ('%'):
                return -53;
            case ('^'):
                return -54;
            case ('&'):
                return -55;
            case ('*'):
                return -56;
            case ('('):
                return -57;
            case (':'):
                return -59;
            case ('+'):
                return -61;
            case ('A'):
                return -65;
            case ('B'):
                return -66;
            case ('C'):
                return -67;
            case ('D'):
                return -68;
            case ('E'):
                return -69;
            case ('F'):
                return -70;
            case ('G'):
                return -71;
            case ('H'):
                return -72;
            case ('I'):
                return -73;
            case ('J'):
                return -74;
            case ('K'):
                return -75;
            case ('L'):
                return -76;
            case ('M'):
                return -77;
            case ('N'):
                return -78;
            case ('O'):
                return -79;
            case ('P'):
                return -80;
            case ('Q'):
                return -81;
            case ('R'):
                return -82;
            case ('S'):
                return -83;
            case ('T'):
                return -84;
            case ('U'):
                return -85;
            case ('V'):
                return -86;
            case ('W'):
                return -87;
            case ('X'):
                return -88;
            case ('Y'):
                return -89;
            case ('Z'):
                return -90;
            case ('{'):
                return -91;
            case ('|'):
                return -92;
            case ('}'):
                return -93;
            case ('~'):
                return -192;
            case ('"'):
                return -222;
            default:
                return 32;
            
        }
    }
}

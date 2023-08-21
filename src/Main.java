import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {
    private int height = 800;
    private int widht = 1500;
    private class MyPanel extends JPanel{
        private double ratio = (double) widht/height;
        private BufferedImage img;
        private int yPos = -180;
        private int PicHeight;
        private int PicWidth;
        private int xPos = (int)(ratio*yPos);
        MyPanel() {
            this.addMouseMotionListener(new MouseMover());
            try {
                img = ImageIO.read(new File("src/imgs/tapok.jpg"));
                PicHeight = img.getHeight();
                PicWidth = img.getWidth();
            } catch (IOException b) {
                System.exit(0);
            }
        }
        @Override
        protected void paintComponent(Graphics g) {
            // you can setup any bias, for example:
            // xPos +=3;
            // yPos +=5;
            Random r = new Random();
            xPos+=r.nextInt(-10,10);
            yPos+=r.nextInt(-10,10);
            if(yPos>=height)
                yPos=0;
            if(xPos>=widht){
                xPos=0;
            }
            g.clearRect(-100,-100,10000,10000);
            g.drawImage(img, xPos, yPos, this);
            boolean yExit = yPos+PicHeight>height;
            boolean xExit = xPos+PicWidth>widht;
            if(yExit&&!xExit){
                BufferedImage cutImg = img.getSubimage(0,-yPos+height, PicWidth,yPos+PicHeight-height);
                g.drawImage(cutImg, xPos,0,this);
            }
            if(xExit&&!yExit){
                BufferedImage cutImg = img.getSubimage(widht-xPos,0,PicWidth+xPos-widht,PicHeight);
                g.drawImage(cutImg, 0,yPos,this);
            }
            if(xExit&&yExit){
                BufferedImage cutImgX = img.getSubimage(widht-xPos,0,PicWidth+xPos-widht,height-yPos);
                g.drawImage(cutImgX, 0,yPos,this);
                BufferedImage cutImgY = img.getSubimage(0,-yPos+height, widht-xPos,yPos+PicHeight-height);
                g.drawImage(cutImgY, xPos,0,this);
                BufferedImage cutImg = img.getSubimage(widht-xPos,-yPos+height, PicWidth+xPos-widht,yPos+PicHeight-height);
                g.drawImage(cutImg, 0,0,this);
            }


        }
        class MouseMover extends MouseMotionAdapter{
            @Override
            public void mouseDragged(MouseEvent e) {
                xPos = e.getX();
                yPos = e.getY();
            }
        }

    }
    public Main(){
        JFrame fr = new JFrame();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocation(new Point(0,0));
        fr.setSize(new Dimension(widht,height));
        MyPanel panel = new MyPanel();
        fr.add(panel);
        fr.setVisible(true);
        Timer timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new Main();
    }
}

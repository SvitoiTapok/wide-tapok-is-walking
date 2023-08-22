import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sun {
    private final int height = 800;
    private final int width = 1500;
    private class Panel extends JPanel{
        private BufferedImage imageSun;
        private BufferedImage imageEarth;
        private BufferedImage imageMoon;
        private int sunWidth;
        private int sunHeight;
        private int earthWidth;
        private int earthHeight;
        private int moonWidth;
        private int moonHeight;
        private double earthAngle = 0;
        private double moonAngle = 0;
        private final int xCenter = 600;
        private final int yCenter = 400;
        private final int rotationRadiusEarth = 300;
        private final int rotationRadiusMoon = 50;

        Panel(){
            try {
                imageSun = ImageIO.read(new File("src/imgs/Sun.jpg"));
                imageEarth = ImageIO.read(new File("src/imgs/Earth.jpg"));
                imageMoon = ImageIO.read(new File("src/imgs/moon.jpg"));
                System.out.println(imageEarth.getData());
                System.out.println(imageSun.getData());
                sunHeight = imageSun.getHeight()/2;
                sunWidth = imageSun.getWidth()/2;
                earthHeight = imageEarth.getHeight()/2;
                earthWidth = imageEarth.getWidth()/2;
                moonHeight = imageMoon.getHeight()/2;
                moonWidth = imageMoon.getWidth()/2;

            }catch (IOException e){
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.black);
            g.fillRect(0,0,2000, 1000);
            System.out.println(earthAngle);
            g.drawImage(imageSun,  xCenter-sunWidth, yCenter-sunHeight,this);
            int earthCenterx = (int)(xCenter-rotationRadiusEarth*Math.cos(Math.toRadians(earthAngle)));
            int earthCentery = (int)(yCenter-rotationRadiusEarth*Math.sin(Math.toRadians(earthAngle)));
            g.drawImage(imageEarth, earthCenterx-earthWidth, earthCentery-earthHeight, this);
            int moonCenterx = (int)(earthCenterx+rotationRadiusMoon*Math.cos(Math.toRadians(moonAngle)));
            int moonCentery = (int)(earthCentery+rotationRadiusMoon*Math.sin(Math.toRadians(moonAngle)));
            g.drawImage(imageMoon, moonCenterx-moonWidth, moonCentery-moonHeight, this);
            moonAngle = (moonAngle+10)%360;
            earthAngle = (earthAngle+0.1)%360;
        }
    }
    public Sun(){
        JFrame fr = new JFrame();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setSize(new Dimension(width, height));
        Panel p = new Panel();
        fr.add(p);
        fr.setVisible(true);
        Timer timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.repaint();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new Sun();
    }
}

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class DVD {
    private int height = 800;
    private int widht = 1500;
    private class AnotherPanel extends JPanel{
        private Random r = new Random();
        private int xPos = 0;
        private int yPos = 0;
        private int xVelocity = 5;
        private int yVelocity = 3;
        private int picHeight;
        private int picWidth;
        private BufferedImage img;
        private BufferedImage basedImg;
        AnotherPanel(){
            try {
                //BufferedImage image = ImageIO.read(new File("src/imgs/dvd2.jpg"));
                //basedImg = new BufferedImage((image.getWidth()+1)/2,(image.getHeight()+1)/2,image.getType());
                //AffineTransform a = AffineTransform.getScaleInstance(0.5,0.5);
                //BufferedImageOp b = new AffineTransformOp(a, AffineTransformOp.TYPE_BILINEAR);
                //b.filter(image, basedImg);
                basedImg = ImageIO.read(new File("src/imgs/dvd2.jpg"));
                picHeight = basedImg.getHeight();
                picWidth = basedImg.getWidth();

                int [] color = getRandomColor();
                WritableRaster raster0 = basedImg.getRaster();
                img = new BufferedImage(picWidth,picHeight,basedImg.getType());
                WritableRaster raster = img.getRaster();
                for(int i = 0; i<raster.getHeight(); i++){
                    for (int j = 0; j<raster.getWidth(); j++){
                        int[] colorx = raster0.getPixel(j,i, new int[3]);
                        if(colorx[0]<10&&colorx[1]<10&&colorx[2]<10)
                            raster.setPixel(j,i,color);
                    }
                }
            }catch (IOException x){
                System.exit(0);
            }
        }

        @Override
        public void paintComponent(Graphics gg) {
            Graphics2D g = (Graphics2D)gg;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.black);
            g.fillRect(0,0,2000,2000);

            if (xPos+picWidth>=widht-10||xPos<0){

                int [] color = getRandomColor();
                xVelocity *= -1;
                xVelocity += -xPos/Math.abs(xPos);
                WritableRaster raster0 = basedImg.getRaster();
                img = new BufferedImage(picWidth,picHeight,basedImg.getType());
                WritableRaster raster = img.getRaster();
                for(int i = 0; i<raster.getHeight(); i++){
                    for (int j = 0; j<raster.getWidth(); j++){
                        int[] colorx = raster0.getPixel(j,i, new int[3]);
                        if(colorx[0]<10&&colorx[1]<10&&colorx[2]<10)
                            raster.setPixel(j,i,color);
                    }
                }
                //img.setData(raster);
            }
            if (yPos+picHeight>=height-35||yPos<0){
                if(yPos<0)
                    System.out.println(yVelocity);
                int [] color = getRandomColor();

                yVelocity *= -1;
                yVelocity = yVelocity-1*yPos/Math.abs(yPos);
                if(yPos<0)
                    System.out.println(yVelocity+ " " + yPos);
                WritableRaster raster0 = basedImg.getRaster();
                img = new BufferedImage(picWidth,picHeight,basedImg.getType());
                WritableRaster raster = img.getRaster();
                for(int i = 0; i<raster.getHeight(); i++){
                    for (int j = 0; j<raster.getWidth(); j++){
                        int[] colorx = raster0.getPixel(j,i, new int[3]);
                        if(colorx[0]<10&&colorx[1]<10&&colorx[2]<10)
                            raster.setPixel(j,i,color);
                    }
                }
                //img.setData(raster);
            }
            xPos+=xVelocity;
            yPos+=yVelocity;
            g.drawImage(img,xPos,yPos,this);

        }
        private int[] getRandomColor(){
            int[] color = {r.nextInt(256), r.nextInt(256),r.nextInt(256)};
            return color;
        }
    }
    public DVD(){
        JFrame fr = new JFrame();
        fr.setBackground(Color.black);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocation(new Point(0,0));
        fr.setSize(new Dimension(widht,height));
        AnotherPanel panel = new AnotherPanel();
        panel.setBackground(Color.black);
        fr.add(panel);
        fr.setVisible(true);
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new DVD();
    }
}

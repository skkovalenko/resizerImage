import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer extends Thread {
    private File[] files;
    private int newWidth;
    private String dstFolder;
    private long start;

    public ImageResizer(File[] files, int newWidth, String dstFolder, long start){
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
    }



    @Override
    public void run() {
        try
        {
            for(File file : files)
            {
                BufferedImage image = ImageIO.read(file);
                if(image == null) {
                    continue;
                }

                int targetHeight = image.getHeight() / (image.getWidth() / newWidth);

                BufferedImage targetImage = getScaledInstance(image, newWidth, targetHeight);
                File fileN = new File(dstFolder + "/" + file.getName());
                ImageIO.write(targetImage, "jpg", fileN);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Finished after start: " + (System.currentTimeMillis() - start) + " ms");
    }
    private BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight){
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage ret = img;
        do{
            if(w > targetWidth){
                w /= 2;
                if(w < targetWidth){
                    w = targetWidth;
                }
            }
            if(h > targetHeight){
                h /= 2;
                if(h < targetHeight){
                    h = targetHeight;
                }
            }
            BufferedImage tmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(ret, 0,0,w,h,null);

            ret = tmp;
        }while (w != targetWidth || h != targetHeight);
        return ret;
    }
}

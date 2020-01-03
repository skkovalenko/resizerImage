
import java.io.File;

public class Main
{
    private static int newWidth = 300;
    private static String srcFolder = "Path from";
    private static String dstFolder = "Path To";

    public static void main(String[] args)
    {
        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        File[] files = srcDir.listFiles();

        int numberOfItemsInThread = files.length / numberOfCores;
        int numberOfItemsInLasthread = numberOfItemsInThread;
        if(numberOfItemsInThread * numberOfCores != files.length){
            numberOfItemsInLasthread += files.length - numberOfItemsInThread * numberOfCores;
        }

        for (int i = 0; i < numberOfCores; i++){
            if(i + 1 == numberOfCores){
                File[] files1 = new File[numberOfItemsInLasthread];
                System.arraycopy(files, files.length - numberOfItemsInLasthread, files1, 0, files1.length);
                ImageResizer resizer = new ImageResizer(files1, newWidth, dstFolder, start);
                resizer.start();
                continue;
            }
            File[] files1 = new File[numberOfItemsInThread];
            System.arraycopy(files, numberOfItemsInThread * (i + 1) - numberOfItemsInThread, files1, 0, files1.length);
            ImageResizer resizer = new ImageResizer(files1, newWidth, dstFolder, start);
            resizer.start();
        }
    }
}

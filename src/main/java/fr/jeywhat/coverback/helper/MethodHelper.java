package fr.jeywhat.coverback.helper;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class MethodHelper {

    public static byte[] convertURLtoByteArray(String path){
        try {
            URL url = new URL(path);
            var bufferimage = ImageIO.read(url);
            var output = new ByteArrayOutputStream();
            ImageIO.write(bufferimage, "jpg", output );
            return output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

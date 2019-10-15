package fr.jeywhat.coverback.helper;

import lombok.Getter;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Getter
public class GameHelper {

    //TODO : If no image return default image
    public static byte[] convertURLtoByteArray(String path) {
        try {
            URL url = new URL(path);
            var bufferimage = ImageIO.read(url);
            var output = new ByteArrayOutputStream();
            ImageIO.write(bufferimage, "jpg", output);
            return output.toByteArray();
        } catch (IOException ignored) {
            return null;
        }

    }

    public static boolean isSwitchGame(File file){
        String nameFile = file.toString();
        return nameFile.endsWith(".xci") || nameFile.endsWith(".nsp");
    }

}

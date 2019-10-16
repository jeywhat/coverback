package fr.jeywhat.coverback.helper;

import lombok.Getter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

    public static String encodeFileToBase64Binary(String path){
        byte[] imgBytes = convertURLtoByteArray(path);
        if(imgBytes == null)
            return null;
        return new String(Base64.getMimeEncoder().encode(imgBytes), StandardCharsets.UTF_8);
    }

    public static boolean isSwitchGame(File file){
        String nameFile = file.toString();
        return nameFile.endsWith(".xci") || nameFile.endsWith(".nsp");
    }

}

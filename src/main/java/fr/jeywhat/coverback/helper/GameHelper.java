package fr.jeywhat.coverback.helper;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

@Getter
@Component
public class GameHelper {


    public static byte[] convertURLtoByteArray(String path) {
        try {
            URL url = new URL(path);
            var bufferimage = ImageIO.read(url);
            var output = new ByteArrayOutputStream();
            ImageIO.write(bufferimage, "jpg", output);
            return output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

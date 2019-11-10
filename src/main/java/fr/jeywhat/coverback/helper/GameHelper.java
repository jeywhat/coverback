package fr.jeywhat.coverback.helper;

import fr.jeywhat.coverback.model.Game;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.List;

@Getter
public class GameHelper {

    private static final Logger logger = LoggerFactory.getLogger(GameHelper.class);

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

    public static byte[] getBytesImageURI(String pathImg, String defaultImgName) {
        if(pathImg == null || pathImg.isEmpty()){
            try {
                InputStream resource = new ClassPathResource(
                        defaultImgName).getInputStream();
                return resource.readAllBytes();
            } catch (IOException e) {
                logger.error("Can not find  the default image : {}", defaultImgName);
                return null;
            }
        }else{
            return convertURLtoByteArray(pathImg);
        }
    }

    public static boolean isSupportedFile(File file, List<String> supportedExtensions, List<String> ignoredPrefixes){
        Game game = new Game(file);
        return ignoredPrefixes.stream().noneMatch(i -> game.getName().equals(i)) && supportedExtensions.stream().anyMatch(file.toString()::endsWith);
    }

}

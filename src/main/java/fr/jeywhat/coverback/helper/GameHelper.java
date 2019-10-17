package fr.jeywhat.coverback.helper;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

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

    public static File getFileResourcesAssets(String nameFile) throws IOException {
        File dirAssets = new ClassPathResource("assets").getFile();
        Optional<File> defaultImage = Arrays.stream(Objects.requireNonNull(dirAssets.listFiles())).filter(f -> nameFile.equals(f.getName())).findFirst();

        if(defaultImage.isEmpty()){
            return null;
        }

        return defaultImage.get();
    }

    public static byte[] getBytesImageURI(String pathImg, String defaultImgName){
        if(pathImg.isBlank()){
            try {
                return FileUtils.readFileToByteArray(Objects.requireNonNull(getFileResourcesAssets(defaultImgName)));
            } catch (IOException e) {
                logger.error("Can not find  the default image : {}", defaultImgName);
                return null;

            }
        }else{
            return convertURLtoByteArray(pathImg);
        }
    }

    public static boolean isSwitchGame(File file){
        String nameFile = file.toString();
        return nameFile.endsWith(".xci") || nameFile.endsWith(".nsp");
    }

}

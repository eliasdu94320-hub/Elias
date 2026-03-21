import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Playground {

    private ArrayList<Sprite> environment = new ArrayList<>();

    public Playground(String pathName) {

        try {
            BufferedImage imageTree = ImageIO.read(new File("./img/tree.png"));
            BufferedImage imageGrass = ImageIO.read(new File("./img/grass.png"));
            BufferedImage imageRock = ImageIO.read(new File("./img/rock.png"));

            int imageTreeWidth = imageTree.getWidth();
            int imageTreeHeight = imageTree.getHeight();

            int imageGrassWidth = imageGrass.getWidth();
            int imageGrassHeight = imageGrass.getHeight();

            int imageRockWidth = imageRock.getWidth();
            int imageRockHeight = imageRock.getHeight();

            BufferedReader reader = new BufferedReader(new FileReader(pathName));
            String line = reader.readLine();

            int lineNumber = 0;

            while (line != null) {

                int columnNumber = 0;

                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {

                    switch (element) {

                        case 'T' -> environment.add(new SolidSprite(
                                columnNumber * imageTreeWidth,
                                lineNumber * imageTreeHeight,
                                imageTree,
                                imageTreeWidth,
                                imageTreeHeight
                        ));

                        case ' ' -> environment.add(new Sprite(
                                columnNumber * imageGrassWidth,
                                lineNumber * imageGrassHeight,
                                imageGrass,
                                imageGrassWidth,
                                imageGrassHeight
                        ));

                        case 'R' -> environment.add(new SolidSprite(
                                columnNumber * imageRockWidth,
                                lineNumber * imageRockHeight,
                                imageRock,
                                imageRockWidth,
                                imageRockHeight
                        ));
                    }

                    columnNumber++;
                }

                lineNumber++;
                line = reader.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> list = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) {
                list.add(sprite);
            }
        }
        return list;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> list = new ArrayList<>();
        for (Sprite sprite : environment) {
            list.add(sprite);
        }
        return list;
    }
}
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Playground {

    private ArrayList<Sprite> environment = new ArrayList<>();

    public Playground(String pathName) {

        try {
            final Image imageTree = ImageIO.read(new File("./images/img/tree.png"));
            final Image imageGrass = ImageIO.read(new File("./images/img/grass.png"));
            final Image imageRock = ImageIO.read(new File("./images/img/rock.png"));
            final Image imageTrap = ImageIO.read(new File("./images/img/trap.png"));

            final int imageTreeWidth = imageTree.getWidth(null);
            final int imageTreeHeight = imageTree.getHeight(null);

            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);

            final int imageRockWidth = imageRock.getWidth(null);
            final int imageRockHeight = imageRock.getHeight(null);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();

            int lineNumber = 0;

            while (line != null) {
                int columnNumber = 0;

                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {

                    switch (element) {

                        case 'X':
                            environment.add(new Trap(
                                    columnNumber * imageRockWidth,
                                    lineNumber * imageRockHeight,
                                    imageTrap,
                                    imageRockWidth,
                                    imageRockHeight
                            ));
                            break;

                        case 'T':
                            environment.add(new SolidSprite(
                                    columnNumber * imageTreeWidth,
                                    lineNumber * imageTreeHeight,
                                    imageTree,
                                    imageTreeWidth,
                                    imageTreeHeight
                            ));
                            break;

                        case ' ':
                            environment.add(new Sprite(
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrass,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            break;

                        case 'R':
                            environment.add(new SolidSprite(
                                    columnNumber * imageRockWidth,
                                    lineNumber * imageRockHeight,
                                    imageRock,
                                    imageRockWidth,
                                    imageRockHeight
                            ));
                            break;

                    }

                    columnNumber++;
                }

                lineNumber++;
                line = bufferedReader.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSprites = new ArrayList<>();

        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) {
                solidSprites.add(sprite);
            }
        }

        return solidSprites;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> list = new ArrayList<>();

        for (Sprite sprite : environment) {
            list.add(sprite);
        }

        return list;
    }
}
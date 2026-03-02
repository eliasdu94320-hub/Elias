import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;

public class Main {

    JFrame displayZoneFrame;
    RenderEngine renderEngine;

    public Main() throws Exception {

        displayZoneFrame = new JFrame("Link");
        displayZoneFrame.setSize(400, 600);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        renderEngine = new RenderEngine();

        Timer renderTimer = new Timer(50, e -> renderEngine.update());
        renderTimer.start();

        displayZoneFrame.getContentPane().add(renderEngine);

        DynamicSprite hero = new DynamicSprite(
                200,
                300,
                ImageIO.read(new File("./images/img/heroTileSheetLowRes.png")),
                48,
                50
        );

        renderEngine.addToRenderList(hero);

        displayZoneFrame.setVisible(true);
    }


    public static void main(String[] args) throws Exception {
        new Main();
    }
}

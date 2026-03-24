import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {

    JFrame displayZoneFrame;
    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public Main() throws Exception {

        displayZoneFrame = new JFrame("The Legend Of Zelda");
        displayZoneFrame.setSize(400, 600);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        renderEngine = new RenderEngine();
        physicEngine = new PhysicEngine();


        DynamicSprite hero = new DynamicSprite(
                100,
                300,
                ImageIO.read(new File("./images/img/heroTileSheetLowRes.png")),
                48,
                50
        );

        renderEngine.setHero(hero);

        gameEngine = new GameEngine(hero, renderEngine);

        Playground playground = new Playground("./images/data/level1.txt");

        for (Displayable sprite : playground.getSpriteList()) {
            renderEngine.addToRenderList(sprite);
        }


        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);


        physicEngine.setEnvironment(playground.getSolidSpriteList());


        Timer renderTimer = new Timer(50, e -> renderEngine.update());
        Timer gameTimer = new Timer(50, e -> gameEngine.update());
        Timer physicTimer = new Timer(50, e -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}
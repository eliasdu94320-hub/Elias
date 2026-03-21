import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Main {

    JFrame displayZoneFrame;
    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public Main() throws Exception {


        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(400, 600);
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        renderEngine = new RenderEngine();
        physicEngine = new PhysicEngine();

// ⚠️ CHEMIN CORRIGÉ ICI
        DynamicSprite hero = new DynamicSprite(
                0,
                300,
                ImageIO.read(new File("./images/img/heroTileSheetLowRes.png")),
                48,
                50
        );


        SolidSprite rock = new SolidSprite(
                250,
                300,
                ImageIO.read(new File("./images/img/rock.png")),
                64,
                64
        );


        gameEngine = new GameEngine(hero);


        renderEngine.addToRenderList(hero);
        renderEngine.addToRenderList(rock);

        physicEngine.addToMovingSpriteList(hero);

        ArrayList<Sprite> environment = new ArrayList<>();
        environment.add(rock);
        physicEngine.setEnvironment(environment);


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
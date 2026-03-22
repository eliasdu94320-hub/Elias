import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {

    private ArrayList<Displayable> renderList;
    private DynamicSprite hero;
    private long startTime = System.currentTimeMillis();  // Pour le Timer

    public RenderEngine() {
        renderList = new ArrayList<>();
    }

    public void setRenderList(ArrayList<Displayable> renderList) {
        this.renderList = renderList;
    }

    public void addToRenderList(Displayable displayable) {
        renderList.add(displayable);
    }
    public void setHero(DynamicSprite hero){
        this.hero = hero;
    }



    @Override
    public void paint(Graphics g) {
        // Barre de vie
        super.paint(g);
        for (Displayable displayable : renderList) {
            displayable.draw(g);
        }
        g.setColor(java.awt.Color.RED);
        g.fillRect(10, 10, 200, 20);
        g.setColor(java.awt.Color.GREEN);
        g.fillRect(10, 10, hero.getHealth() * 2, 20);

        long elapsed = (System.currentTimeMillis() - startTime) / 1000; //Mise en place du Timer
        g.setColor(java.awt.Color.BLACK);
        g.drawString("Temps : " + elapsed + "s", 10, 50);

        if (hero.getHealth() <= 0) {                     // Affichage du Game Over
            g.setColor(java.awt.Color.BLACK);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
            g.drawString("GAME OVER", 80, 300);
        }

    }

    @Override
    public void update() {
        repaint();
    }
}
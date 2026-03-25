import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {

    private final int GAME_DURATION = 30; // durée de la partie

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

    public long getRemainingTime() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        long remaining = GAME_DURATION - elapsed;
        return Math.max(remaining, 0);
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
        long remaining = GAME_DURATION - elapsed;

        if (remaining < 0) remaining = 0;

        g.drawString("Temps : " + remaining + "s", 10, 50);
        g.setColor(java.awt.Color.BLACK);


        if (hero.getHealth() <= 0 || remaining == 0) {                 // Affichage du Game Over
            g.setColor(java.awt.Color.BLACK);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
            g.drawString("GAME OVER", 80, 300);
        }
        if (hero.isWin()) {
            g.setColor(java.awt.Color.BLUE);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
            g.drawString("Gagné !", 100, 300);
        }



    }

    @Override
    public void update() {
        repaint();
    }
}
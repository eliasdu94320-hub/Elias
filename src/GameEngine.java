import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {

    private final DynamicSprite hero;

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    @Override
    public void update() {
// volontairement vide
    }

    @Override
    public void keyPressed(KeyEvent e) {

        hero.setWalking(true);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> hero.setDirection(Direction.NORTH);
            case KeyEvent.VK_DOWN -> hero.setDirection(Direction.SOUTH);
            case KeyEvent.VK_LEFT -> hero.setDirection(Direction.WEST);
            case KeyEvent.VK_RIGHT -> hero.setDirection(Direction.EAST);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        hero.setWalking(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
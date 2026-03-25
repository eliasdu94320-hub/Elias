import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {

    private boolean isWalking = false;
    private double speed = 5;
    private boolean win = false;

    public boolean isInvincible() {
        return System.currentTimeMillis() - lastDamageTime < INVINCIBILITY_TIME; // conditions de clignotement
    }

    private final int spriteSheetNumberOfColumn = 10;
    private int timeBetweenFrame = 200;

    private Direction direction = Direction.SOUTH;

    private int health = 100;

    private long lastDamageTime = 0;
    private final long INVINCIBILITY_TIME = 2000;

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setWalking(boolean walking) {
        this.isWalking = walking;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }
    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isWin() {
        return win;
    }

    private void move() {
        if (!isWalking) return;

        switch (direction) {
            case NORTH -> this.y -= speed;
            case SOUTH -> this.y += speed;
            case EAST -> this.x += speed;
            case WEST -> this.x -= speed;
        }
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment) {

        Rectangle2D.Double futureHitBox;

        switch (direction) {
            case NORTH -> futureHitBox = new Rectangle2D.Double(x, y - speed, width, height);
            case SOUTH -> futureHitBox = new Rectangle2D.Double(x, y + speed, width, height);
            case EAST -> futureHitBox = new Rectangle2D.Double(x + speed, y, width, height);
            case WEST -> futureHitBox = new Rectangle2D.Double(x - speed, y, width, height);
            default -> futureHitBox = new Rectangle2D.Double(x, y, width, height);
        }

        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite && !(sprite instanceof Trap)) {
                if (futureHitBox.intersects(sprite.getHitBox())) {
                    return false;
                }
            }
        }

        return true;
    }

    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (health <= 0){
            return;  // Le personnage ne répond plus si les PV sont en dessous de 0
        }

        if (isMovingPossible(environment)) {
            move();
        }

        long currentTime = System.currentTimeMillis();

        for (Sprite sprite : environment) {
            if (sprite instanceof Trap) {
                if (this.getHitBox().intersects(sprite.getHitBox())
                        && currentTime - lastDamageTime > INVINCIBILITY_TIME) {

                    takeDamage(10);
                    lastDamageTime = currentTime;
                }

            }
            if (sprite instanceof Exit) {
                if (this.getHitBox().intersects(sprite.getHitBox())) {
                    setWin(true);
                }
            }
        }
    }

    @Override
    public void draw(Graphics g) {

        int index = (int)((System.currentTimeMillis() / timeBetweenFrame) % spriteSheetNumberOfColumn);
        int attitude = direction.getFrameLineNumber();

        int srcX1 = index * (int) width;
        int srcY1 = attitude * (int) height;
        int srcX2 = (index + 1) * (int) width;
        int srcY2 = (attitude + 1) * (int) height;


        if (isInvincible()) {   // Clignotement quand on se fait toucher
            if ((System.currentTimeMillis() / 100) % 2 == 0) {
                return;
            }
        }

        g.drawImage(image,
                (int)x, (int)y, (int)(x + width), (int)(y + height),
                srcX1, srcY1, srcX2, srcY2,
                null);
    }
}
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {

    private boolean isWalking = false;
    private double speed = 5;
    private final int spriteSheetNumberOfColumn = 10;
    private int timeBetweenFrame = 200;
    private Direction direction = Direction.SOUTH;

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setWalking(boolean walking) {
        this.isWalking = walking;
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

        Rectangle2D.Double hitBox;

        switch (direction) {
            case NORTH -> hitBox = new Rectangle2D.Double(x, y - speed, width, height);
            case SOUTH -> hitBox = new Rectangle2D.Double(x, y + speed, width, height);
            case EAST -> hitBox = new Rectangle2D.Double(x + speed, y, width, height);
            case WEST -> hitBox = new Rectangle2D.Double(x - speed, y, width, height);
            default -> hitBox = new Rectangle2D.Double(x, y, width, height);
        }

        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite && sprite != this) {

                Rectangle2D.Double other =
                        new Rectangle2D.Double(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

                if (hitBox.intersects(other)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            move();
            for (Sprite sprite : environment) {
                if (sprite instanceof SolidSprite) {
                    if (this.getHitBox().intersects(sprite.getHitBox())) {
                        takeDamage(10);
                    }
                }
            }
        }
    }

    private int health = 100;

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    @Override
    public void draw(Graphics g) {

        int index = (int)((System.currentTimeMillis() / timeBetweenFrame) % spriteSheetNumberOfColumn);
        int attitude = direction.getFrameLineNumber();

        int srcX1 = index * (int) width;
        int srcY1 = attitude * (int) height;
        int srcX2 = (index + 1) * (int) width;
        int srcY2 = (attitude + 1) * (int) height;

        g.drawImage(image,
                (int)x, (int)y, (int)(x + width), (int)(y + height),
                srcX1, srcY1, srcX2, srcY2,
                null);
    }
}
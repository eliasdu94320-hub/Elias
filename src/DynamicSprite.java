import java.awt.*;

public class DynamicSprite extends SolidSprite {

    private boolean isWalking = true;
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

    @Override
    public void draw(Graphics g) {

        int index = (int)((System.currentTimeMillis() / timeBetweenFrame) % spriteSheetNumberOfColumn);

        int attitude = direction.getFrameLineNumber();

        int sx1 = (int)(index * width);
        int sy1 = (int)(attitude * height);
        int sx2 = (int)((index + 1) * width);
        int sy2 = (int)((attitude + 1) * height);

        g.drawImage(
                image,
                (int)x,
                (int)y,
                (int)(x + width),
                (int)(y + height),
                sx1,
                sy1,
                sx2,
                sy2,
                null
        );
    }
}


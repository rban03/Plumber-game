import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class Puzzle extends JButton {

    private final int sizeX = 100, sizeY = 100;
    private int posX, posY;
    private boolean source, end;
    private PuzzleType type;
    /** dwie mapy uzyte do ruchu obrotowego klocków*/
    private static HashMap<PuzzleType, Image> types_images;
    private static HashMap<PuzzleType, PuzzleType> types_nexttypes;

    public boolean visited = false;

    static{
        types_images = new HashMap<>();
        types_nexttypes = new HashMap<>();

        /** ładowanie grafik dla konkretnych typów klocka i do hashmapy*/
        types_images.put(PuzzleType.LEFT_RIGHT, Toolkit.getDefaultToolkit().createImage("graphics/LEFT_RIGHT.png"));
        types_images.put(PuzzleType.UP_DOWN, Toolkit.getDefaultToolkit().createImage("graphics/UP_DOWN.png"));

        types_images.put(PuzzleType.LEFT_DOWN, Toolkit.getDefaultToolkit().createImage("graphics/LEFT_DOWN.png"));
        types_images.put(PuzzleType.LEFT_UP, Toolkit.getDefaultToolkit().createImage("graphics/LEFT_UP.png"));
        types_images.put(PuzzleType.RIGHT_UP, Toolkit.getDefaultToolkit().createImage("graphics/RIGHT_UP.png"));
        types_images.put(PuzzleType.RIGHT_DOWN, Toolkit.getDefaultToolkit().createImage("graphics/RIGHT_DOWN.png"));

        /** zastąpienie jednego typu klocka drugim */
        types_nexttypes.put(PuzzleType.LEFT_RIGHT, PuzzleType.UP_DOWN);
        types_nexttypes.put(PuzzleType.UP_DOWN, PuzzleType.LEFT_RIGHT);

        types_nexttypes.put(PuzzleType.LEFT_DOWN, PuzzleType.LEFT_UP);
        types_nexttypes.put(PuzzleType.LEFT_UP, PuzzleType.RIGHT_UP);
        types_nexttypes.put(PuzzleType.RIGHT_UP, PuzzleType.RIGHT_DOWN);
        types_nexttypes.put(PuzzleType.RIGHT_DOWN, PuzzleType.LEFT_DOWN);
    }
    /** zwyswietlenie poczatkowych grafik klosków*/
    private String tag;
    public Puzzle(PuzzleType type, int posX, int posY, boolean source, boolean end, String tag, Window window) {
        this.setSize(sizeX, sizeY);
        setIcon(new ImageIcon(types_images.get(type)));

        this.posX = posX;
        this.posY = posY;
        this.source = source;
        this.end = end;
        this.type = type;
        this.tag = tag;

        if(!source && !end)
        this.addActionListener(e->{
            loadAndReplaceNextGraphic();
            window.win();
        });
    }

    /** warunki sprawdzenia ustawienia elementów, kiedy się ze sobą łaczą */
    public static boolean connected(Puzzle first, Puzzle second){
        System.out.println("first[" + first.tag + " | posX: " + first.posX + " |  posY:" + first.posY + "] " + first.type + " second ["+ second.tag + " | posX: " + second.posX + " |  posY:" + second.posY + "]" + second.type + " res = " +
                ((first.type == PuzzleType.LEFT_RIGHT && second.type == PuzzleType.LEFT_RIGHT && first.posY == second.posY && first.posX != second.posX) ||
                        (first.type == PuzzleType.UP_DOWN && second.type == PuzzleType.UP_DOWN && first.posY != second.posY && first.posX == second.posX) ||

                        (first.type == PuzzleType.LEFT_RIGHT && second.type == PuzzleType.LEFT_DOWN && first.posY != second.posY && first.posX == second.posX) ||
                        (first.type == PuzzleType.LEFT_DOWN && second.type == PuzzleType.LEFT_UP && first.posY != second.posY && first.posX == second.posX) ||
                        (first.type == PuzzleType.LEFT_UP && second.type == PuzzleType.LEFT_RIGHT && first.posY == second.posY && first.posX != second.posX) ||

                        (first.type == PuzzleType.LEFT_RIGHT && second.type == PuzzleType.RIGHT_DOWN && first.posY == second.posY && first.posX != second.posX) ||
                        (first.type == PuzzleType.RIGHT_DOWN && second.type == PuzzleType.RIGHT_UP && first.posY != second.posY && first.posX == second.posX) ||
                        (first.type == PuzzleType.RIGHT_UP && second.type == PuzzleType.LEFT_RIGHT && first.posY == second.posY && first.posX != second.posX)));

        return  (first.type == PuzzleType.LEFT_RIGHT && second.type == PuzzleType.LEFT_RIGHT && first.posY == second.posY && first.posX != second.posX) ||
                (first.type == PuzzleType.UP_DOWN && second.type == PuzzleType.UP_DOWN && first.posY != second.posY && first.posX == second.posX) ||

                (first.type == PuzzleType.LEFT_RIGHT && second.type == PuzzleType.LEFT_DOWN && first.posY == second.posY && first.posX != second.posX) ||
                (first.type == PuzzleType.LEFT_DOWN && second.type == PuzzleType.LEFT_UP && first.posY != second.posY && first.posX == second.posX) ||
                (first.type == PuzzleType.LEFT_UP && second.type == PuzzleType.LEFT_RIGHT && first.posY == second.posY && first.posX != second.posX) ||

                (first.type == PuzzleType.LEFT_RIGHT && second.type == PuzzleType.RIGHT_DOWN && first.posY == second.posY && first.posX != second.posX) ||
                (first.type == PuzzleType.RIGHT_DOWN && second.type == PuzzleType.RIGHT_UP && first.posY != second.posY && first.posX == second.posX) ||
                (first.type == PuzzleType.RIGHT_UP && second.type == PuzzleType.LEFT_RIGHT && first.posY == second.posY && first.posX != second.posX);


    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isSource() {
        return source;
    }

    public boolean isEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "tag["+tag+"]";
    }

    /** zastąpienie starego ustawienia klocka nowym pobranym z hashmapy*/
    private void loadAndReplaceNextGraphic(){
        //System.out.print("Type[" + tag + "] " + type + " -> ");
        type = types_nexttypes.get(type);
        setIcon(new ImageIcon(types_images.get(type)));
        repaint();
        System.out.println(type + " isSource:" + source + " isEnd:" + isEnd());
    }

    /** losowanie ustawienia początkowego elementów*/
    public static PuzzleType getRandomHorizontalPuzzle(){
        PuzzleType[] puzzleTypes = {PuzzleType.LEFT_DOWN, PuzzleType.LEFT_UP, PuzzleType.RIGHT_UP, PuzzleType.RIGHT_DOWN};

        return puzzleTypes[new Random().nextInt(puzzleTypes.length)];
    }

    public static PuzzleType getRandomVerticalPuzzle(){
        PuzzleType[] puzzleTypes = {PuzzleType.LEFT_RIGHT, PuzzleType.UP_DOWN};

        return puzzleTypes[new Random().nextInt(puzzleTypes.length)];
    }

    public enum PuzzleType{
        LEFT_RIGHT,
        UP_DOWN,
        LEFT_DOWN,
        LEFT_UP,
        RIGHT_UP,
        RIGHT_DOWN
    }
}

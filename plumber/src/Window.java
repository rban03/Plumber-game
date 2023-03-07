import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

    /** wielkosc mapy */
    private int sizeX = 3, sizeY = 3;

    /** czas gry */
    private int time;
    private boolean win;
    private Puzzle[][] puzzles;

    private Window window;
    
    public Window() {

        puzzles = new Puzzle[sizeX][sizeY];
        loadMap();
        window = this;

        /** powrót do menu u dołu */
        JButton menu = new JButton("Menu");
        menu.setSize(80,30);
        menu.setLocation(225, 300);
        menu.setOpaque(false);
        menu.setContentAreaFilled(false);
        menu.setBorderPainted(false);
        menu.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("graphics\\Mmini.png")));
        menu.addActionListener(e -> {
            window.dispose();
            new Thread(() -> new Menu()).start();
        });
        add(menu);

        /** wyjście z gry u dołu*/
        JButton wyjscie;
        wyjscie = new JButton("WYJŚCIE");
        wyjscie.setSize(110,30);
        wyjscie.setLocation(100, 300);
        wyjscie.setOpaque(false);
        wyjscie.setContentAreaFilled(false);
        wyjscie.setBorderPainted(false);
        wyjscie.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("graphics\\wy.png")));
        wyjscie.setVisible(true);

        /** Zegar liczący czas rozgrywki*/
        JLabel timer = new JLabel("Czas:0");
        timer.setSize(100, 30);
        timer.setLocation(10, 300);

        new Thread(() -> {
            while (true) {
                if (win)
                    return;

                try {
                    Thread.sleep(1000);
                    time++;
                    wyjscie.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            timer.setText( "pauza");
                            JOptionPane.showMessageDialog(timer,"wyjście z gry");
                            System.exit(1);
                        }
                    });
                    timer.setText("Czas:" + time + " s");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        add(timer);
        add(wyjscie);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(sizeX * 100 + 16, sizeY * 100 + 68);
        this.setLayout(null);
        this.setLocation(500,400);
        this.setResizable(false);
        this.setVisible(true);
    }

    /** ładowanie tablicy, ustawienie rur na odpowiednich miejscach */
    private void loadMap() {
        puzzles[0][0] = new Puzzle(Puzzle.PuzzleType.LEFT_RIGHT, 0, 0, true, false, "0", this);
        puzzles[0][1] = new Puzzle(Puzzle.getRandomHorizontalPuzzle(), 0, 1, false, false, "1", this);
        puzzles[0][2] = new Puzzle(Puzzle.getRandomHorizontalPuzzle(), 0, 2, false, false, "2", this);
        puzzles[1][0] = new Puzzle(Puzzle.getRandomVerticalPuzzle(), 1, 0, false, false, "3", this);
        puzzles[1][1] = new Puzzle(Puzzle.getRandomVerticalPuzzle(), 1, 1, false, false, "4", this);
        puzzles[1][2] = new Puzzle(Puzzle.getRandomVerticalPuzzle(), 1, 2, false, false, "5", this);
        puzzles[2][0] = new Puzzle(Puzzle.getRandomHorizontalPuzzle(), 2, 0, false, false, "6", this);
        puzzles[2][1] = new Puzzle(Puzzle.getRandomHorizontalPuzzle(), 2, 1, false, false, "7", this);
        puzzles[2][2] = new Puzzle(Puzzle.PuzzleType.LEFT_RIGHT, 2, 2, false, true, "8", this);

        for (int i = 0; i < sizeX; i++)
            for (int j = 0; j < sizeY; j++) {
                puzzles[i][j].setLocation(i * 100, j * 100);
                add(puzzles[i][j]);
            }
    }

    public Puzzle findSurroundingPuzzles(Puzzle puzzle) {
        /** sprawdzenie lokalizacji elementu*/
        int x = puzzle.getPosX(), y = puzzle.getPosY();

        /** ustawienie ze ten element został juz sprawdzony*/
        if (puzzle.visited) return null;

        puzzle.visited = true;
        System.out.println("surr f:" + puzzle);

        if (puzzle.isEnd()) return puzzle;


        Puzzle left = null, right = null, up = null, down = null;

        if (x > 0)
            left = puzzles[x - 1][y];
        if (x < puzzles.length - 1)
            right = puzzles[x + 1][y];
        if (y > 0)
            up = puzzles[x][y - 1];
        if (y < puzzles[0].length - 1)
            down = puzzles[x][y + 1];

        Puzzle ret = null;

        /** sprawdzenie czy puzle się ze sobą łaczą worząc ciag*/
        if (left != null && !left.visited && Puzzle.connected(puzzle, left))
            ret = findSurroundingPuzzles(left);

        if (right != null && !right.visited && Puzzle.connected(puzzle, right) && ret == null)
            ret = findSurroundingPuzzles(right);

        if (up != null && !up.visited && Puzzle.connected(puzzle, up) && ret == null)
            ret = findSurroundingPuzzles(up);

        if (down != null && !down.visited && Puzzle.connected(puzzle, down) && ret == null)
            ret = findSurroundingPuzzles(down);

        return ret;
    }

    /** metoda która sorawdza czy wygraliśmy*/
    public void win() {

        /** sprawdzanie po kolei wszytkich pól na tablicy*/
        for (int i = 0; i < sizeX; i++)
            for (int j = 0; j < sizeY; j++)
                puzzles[j][i].visited = false;

        /** zwritka od find */
        Puzzle puzzle = findSurroundingPuzzles(puzzles[0][0]);

        /** sprawdzenie czy zwócony element jest ostatnim prawidłowym i wyświetlenie wyniku gry */
        if (puzzle != null && puzzle.isEnd()) {
            System.out.println("Wygrana!");
            win = true;
            JOptionPane.showMessageDialog(this,"Gratulacje, wygrałeś w " + time + " s!");
            System.exit(1);
        }
        else
            System.out.println("Brak wygranej!");
    }
}

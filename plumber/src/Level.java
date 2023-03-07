import javax.swing.*;
import java.awt.*;

public class Level extends JFrame {

    private Level level;

    public Level () {
        level = this;

        /** parametry okna zawierającego ognośniki do leveli*/
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(null);
        this.setLocation(500,400);
        this.setResizable(false);
        this.setVisible(true);

        /** powrót do menu*/
        this.setContentPane(new Level.MyJComponent());
        JButton menu = new JButton("Menu");
        menu.setSize(80,30);
        menu.setLocation(400, 400);
        menu.setOpaque(false);
        menu.setContentAreaFilled(false);
        menu.setBorderPainted(false);
        menu.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("graphics\\Mmini.png")));
        menu.addActionListener(e -> {
            level.dispose();
            new Thread(() -> new Menu()).start();
        });
        add(menu);

        /** przycisk kierujący nas do rozwiązania pierwszego levelu*/
        this.setContentPane(new Level.MyJComponent());
        JButton L1 = new JButton("Menu");
        L1.setSize(110,100);
        L1.setLocation(50, 50);
        L1.setOpaque(false);
        L1.setContentAreaFilled(false);
        L1.setBorderPainted(false);
        L1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("graphics\\L1.png")));
        L1.addActionListener(e -> {
            level.dispose();
            new Thread(() -> new Window()).start();
        });
        add(L1);


    }

    /** tło */
    public class MyJComponent extends JComponent{
        private Image image;
        public MyJComponent() {
            image = Toolkit.getDefaultToolkit().createImage("graphics/menu_background2.png");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }

}

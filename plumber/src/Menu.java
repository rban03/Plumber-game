import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    public Menu(){
        init();
    }

    /** tworzenie widoku menu startowego*/
    public void init(){
        this.setSize(360,320);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocation(500,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setName("Menu");
        this.setContentPane(new MyJComponent());
        Menu menu = this;
        JLabel logo;
        JButton start;
        JButton instrukcja;

        /** logo gry */
        logo = new JLabel();
        logo.setSize(300,80);
        logo.setLocation(20,10);
        logo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("graphics\\H1.png")));
        add(logo);

        /** przycisk startu kierujący do wyboru levelu*/
        start = new JButton("Nowa gra");
        start.setSize(150,36);
        start.setLocation(getWidth()/2-start.getWidth()/2, 140);
        start.setOpaque(false);
        start.setContentAreaFilled(false);
        start.setBorderPainted(false);
        start.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("graphics\\S1.png")));
        start.addActionListener(e -> {
            menu.dispose();
            new Thread(() -> new Level()).start();
        });

        add(start);
        this.setVisible(true);

        /** przycisk instrukcji kierujący do zasad gry*/
        instrukcja = new JButton("instrukcja");
        instrukcja.setSize(150,36);
        instrukcja.setLocation(getWidth()/2-instrukcja.getWidth()/2, 240);
        instrukcja.setOpaque(false);
        instrukcja.setContentAreaFilled(false);
        instrukcja.setBorderPainted(false);
        instrukcja.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("graphics\\instrukcja.png")));
        instrukcja.addActionListener(e -> {
            menu.dispose();
            new Thread(() -> new Instruction()).start();
        });


        add(instrukcja);
        instrukcja.setVisible(true);
    }

    /** wczytanie grafiki tła*/
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

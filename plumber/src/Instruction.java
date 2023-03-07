import javax.swing.*;
import java.awt.*;

public class Instruction extends JFrame {

    private Instruction instruction;

    public Instruction() {

        instruction = this;

        /** powrót do menu */
        this.setContentPane(new Instruction.MyJComponent());
        JButton menu = new JButton("Menu");
        menu.setSize(80,30);
        menu.setLocation(400, 400);
        menu.setOpaque(false);
        menu.setContentAreaFilled(false);
        menu.setBorderPainted(false);
        menu.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("graphics\\Mmini.png")));
        menu.addActionListener(e -> {
            instruction.dispose();
            new Thread(() -> new Menu()).start();
        });
        add(menu);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(null);
        this.setLocation(500,400);
        this.setResizable(false);
        this.setVisible(true);
    }

    /** treść instrukcji */
    public class MyJComponent extends JComponent{
        private Image image;
        public MyJComponent() {
            image = Toolkit.getDefaultToolkit().createImage("graphics/ins.png");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI {

    private JFrame frame;

    private JPanel mainPanel = new JPanel(new GridLayout(2, 1)); // contains everything
    // private JPanel playerPanel = new JPanel(new GridLayout(1, 2, 10, 5));
    private JPanel textPanel = new JPanel();

    private JTextArea gameplay = new JTextArea("", 20, 60);
    private JScrollPane scroll = new JScrollPane(gameplay);

    private JPanel p1Panel = new JPanel(new BorderLayout());
    private JPanel p2Panel = new JPanel(new BorderLayout());

    private JPanel p1Info = new JPanel(new FlowLayout()); // contains p1 health, charges
    private JPanel p2Info = new JPanel(new FlowLayout()); // contains p2 health, charges

    private JPanel p1Actions = new JPanel(new GridLayout(1, 3)); // contains p1 buttons
    private JPanel p2Actions = new JPanel(new GridLayout(1, 3)); // contains p2 buttons

    private JButton p1charge = new JButton("Charge");
    private JButton p2charge = new JButton("Charge");
    private JButton p1bang = new JButton("Bang");
    private JButton p2bang = new JButton("Bang");
    private JButton p1block = new JButton("Block");
    private JButton p2block = new JButton("Block");

    private JLabel charges = new JLabel();
    private JLabel health = new JLabel();
    private JLabel name = new JLabel();

    public GUI() {

        frame = new JFrame();

        Container contentPane = frame.getContentPane();
        contentPane.setPreferredSize(new Dimension(800, 600));

        gameplay.setEditable(false);

        p1Info.add(name);
        p1Info.add(charges);
        p1Info.add(health);
        p1Actions.add(p1charge);
        p1Actions.add(p1bang);
        p1Actions.add(p1block);
        p1Panel.add(p1Info, BorderLayout.CENTER);
        p1Panel.add(p1Actions, BorderLayout.SOUTH);

        // p2Info.add(p2hp);
        // p2Info.add(p2c);
        // p2Actions.add(p2charge);
        // p2Actions.add(p2bang);
        // p2Actions.add(p2block);
        // p2Panel.add(p2Info, BorderLayout.CENTER);
        // p2Panel.add(p2Actions, BorderLayout.SOUTH);

        textPanel.add(scroll);

        mainPanel.add(p1Panel);
        // playerPanel.add(p2Panel);
        // mainPanel.add(playerPanel);
        mainPanel.add(textPanel);
        frame.add(mainPanel);

        frame.setFocusable(true);
        frame.setResizable(false);
        frame.setTitle("GAMEBOI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public void setUpActions() {

        gameplay.append("Welcome to Charge/Reload! \nStart Playing Now!");

        // ===========================================================================

        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == p1charge) {

                } else if (ae.getSource() == p1bang) {

                } else if (ae.getSource() == p1block) {

                }
            }
        };
        p1charge.addActionListener(action);
        p1bang.addActionListener(action);
        p1block.addActionListener(action);

        // ------------------------------------------------------------------------------------------------

        // ActionListener chargeP2 = new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent ae) {
        // // add actions here
        // }
        // };
        // p2charge.addActionListener(chargeP2);

        // ActionListener bangP2 = new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent ae) {
        // // add action
        // }
        // };
        // p2bang.addActionListener(bangP2);

        // ActionListener blockP2 = new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent ae) {
        // // add action
        // }
        // };
        // p2block.addActionListener(blockP2);
    }
}

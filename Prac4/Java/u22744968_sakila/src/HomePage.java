import javax.swing.*;

public class HomePage extends JFrame
{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;

    public HomePage(String title)
    {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setContentPane(mainPanel);
//        this.pack();
    }
}

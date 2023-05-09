import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddFilm extends JFrame {
    private JTextField textField1;
    private HomePage homePage;

    public AddFilm(String title, HomePage homePage) 
    {
        super(title);
        this.homePage = homePage;
        this.homePage.setVisible(false);
        this.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosed(WindowEvent e) 
            {
                homePage.setVisible(true);
            }
        });
    }
}

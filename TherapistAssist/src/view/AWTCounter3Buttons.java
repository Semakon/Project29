package view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// An AWT GUI program inherits the top-level container java.awt.Frame
public class AWTCounter3Buttons extends Frame {
    private TextField tfCount;
    private Button btnCountUp, btnCountDown, btnReset;
    private int count = 0;

    // Constructor to setup the GUI components and event handlers
    public AWTCounter3Buttons () {
        setLayout(new FlowLayout());

        Panel panel = new Panel(new FlowLayout());
        panel.add(new Label("lol"));

        add(panel);

        add(new Label("Counter"));          // an anonymous instance of Label
        tfCount = new TextField("0", 20);
        tfCount.setEditable(false);         // read-only
        add(tfCount);                       // "super" Frame adds tfCount

        btnCountUp = new Button("Count Up");
        add(btnCountUp);

        // Construct an anonymous instance of an anonymous inner class.
        // The source Button adds the anonymous instance as ActionEvent listener
        btnCountUp.addActionListener(evt -> tfCount.setText(++count + ""));

        btnCountDown = new Button("Count Down");
        add(btnCountDown);
        btnCountDown.addActionListener(evt -> tfCount.setText(--count + ""));

        btnReset = new Button("Reset");
        add(btnReset);
        btnReset.addActionListener(evt -> {
            count = 0;
            tfCount.setText("0");
        });

        // Allocate an anonymous instance of an anonymous inner class
        // that extends WindowAdapter.
        // "super" Frame adds this instance as WindowEvent listener.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                System.exit(0);  // terminate the program
            }
        });


        setTitle("AWT Counter");
        setSize(400, 80);
        setVisible(true);
    }

    // The entry main method
    public static void main(String[] args) {
        new AWTCounter3Buttons();  // Let the constructor do the job
    }
}
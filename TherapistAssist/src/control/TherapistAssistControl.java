package control;

import model.TherapistAssist;
import view.TherapistAssistGUI;

import java.util.Observable;
import java.util.Observer;

/**
 * Author:  Martijn
 * Date:    5-1-2017
 */
public class TherapistAssistControl implements Observer {

    private TherapistAssistGUI view;
    private TherapistAssist model;

    public TherapistAssistControl() {
        this.view = new TherapistAssistGUI();
        this.model = new TherapistAssist();
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Input: " + arg);
    }

    public static void main(String[] args) {
        new TherapistAssistControl();   // Let the constructor do the job
    }

}

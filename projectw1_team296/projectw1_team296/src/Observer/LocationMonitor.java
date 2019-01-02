package Observer;

import Model.GUI_extras.ErrorBox;

import java.util.Observable;
import java.util.Observer;

public class LocationMonitor implements Observer{

    @Override
    public void update(Observable o, Object arg) {
        ErrorBox.display("Yay!","You have visited a new city!");
    }
}

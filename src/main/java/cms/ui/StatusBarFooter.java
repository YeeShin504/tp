package cms.ui;

import cms.model.person.Person;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;
    @FXML
    private Label totalPersonsStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given visible person list.
     */
    public StatusBarFooter(ObservableList<Person> personList) {
        super(FXML);
        saveLocationStatus.setText("CS2103T Course Manager");
        totalPersonsStatus.textProperty().bind(Bindings.createStringBinding(
                () -> personList.size() + (personList.size() == 1 ? " person listed" : " persons listed"),
                personList));
    }

}

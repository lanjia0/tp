package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label priority;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        setLabelTextAndVisibility(phone, person.getPhone().value);
        setLabelTextAndVisibility(address, person.getAddress().value);
        setLabelTextAndVisibility(email, person.getEmail().value);

        // Handle priority with prefix
        String priorityValue = person.getPriority().getValue();
        if (priorityValue == null || priorityValue.trim().isEmpty() || priorityValue.equalsIgnoreCase("NONE")) {
            priority.setVisible(false);
            priority.setManaged(false);
        } else {
            priority.setText("Priority: " + priorityValue);
            priority.setVisible(true);
            priority.setManaged(true);
            setPriorityStyle(person.getPriority());
        }

        // Handle tags
        if (person.getTags().isEmpty()) {
            tags.setVisible(false);
            tags.setManaged(false);
        } else {
            tags.getChildren().clear();
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            tags.setVisible(true);
            tags.setManaged(true);
        }
    }

    /**
     * Sets the text of a label and controls its visibility based on whether the value is empty.
     * If the value is empty, the label is hidden and marked as unmanaged to collapse the space.
     *
     * @param label The label to update.
     * @param value The text value to set.
     */
    private void setLabelTextAndVisibility(Label label, String value) {
        if (value == null || value.trim().isEmpty()) {
            label.setText("");
            label.setVisible(false);
            label.setManaged(false);
        } else {
            label.setText(value.trim());
            label.setVisible(true);
            label.setManaged(true);
        }
    }

    /**
     * Sets the CSS style for the priority label based on priority level.
     */
    private void setPriorityStyle(Priority priority) {
        this.priority.getStyleClass().clear();
        this.priority.getStyleClass().add("priority-label");
        switch (priority.getValue().toUpperCase()) {
        case "HIGH":
            this.priority.getStyleClass().add("priority-high");
            break;
        case "MEDIUM":
            this.priority.getStyleClass().add("priority-medium");
            break;
        case "LOW":
            this.priority.getStyleClass().add("priority-low");
            break;
        case "NONE":
        default:
            this.priority.getStyleClass().add("priority-none");
            break;
        }
    }
}

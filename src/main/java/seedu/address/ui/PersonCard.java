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
    private Label age;
    @FXML
    private Label priority;
    @FXML
    private Label occupation;
    @FXML
    private Label incomeBracket;
    @FXML
    private Label lastContactedDate;
    @FXML
    private FlowPane tags;
    @FXML
    private Label dncLabel;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        occupation.setText(person.getOccupation().toString());
        age.setText("Age: " + person.getAge().value);
        priority.setText("Priority: " + person.getPriority().getValue());
        lastContactedDate.setText("Last Contacted: " + person.getLastContactedDate().toDisplayString());
        setPriorityStyle(person.getPriority());

        // Hide fields if they are empty
        hideIfEmpty(phone, person.getPhone().value);
        hideIfEmpty(address, person.getAddress().value);
        hideIfEmpty(email, person.getEmail().value);
        hideIfEmpty(occupation, person.getOccupation().value);

        if (person.getTags().isEmpty()) {
            tags.setManaged(false);
            tags.setVisible(false);
        } else {
            setIncomeBracketText(person);
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        }
    }

    /**
     * Hides the label if the value is empty or blank.
     */
    private void hideIfEmpty(Label label, String value) {
        if (value == null || value.trim().isEmpty()) {
            label.setVisible(false);
            label.setManaged(false);
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

    /**
     * Sets the income bracket text and style.
     */
    private void setIncomeBracketText(Person person) {
        if (person.getIncomeBracket() == null) {
            this.incomeBracket.setText("Income Bracket: Not Set");
            this.incomeBracket.getStyleClass().clear();
            this.incomeBracket.getStyleClass().add("income-bracket-label");
            this.incomeBracket.getStyleClass().add("income-bracket-not-set");
        } else {
            this.incomeBracket.setText("Income Bracket: " + person.getIncomeBracket().getValue());
            this.incomeBracket.getStyleClass().clear();
            this.incomeBracket.getStyleClass().add("income-bracket-label");
            String bracketLevel = person.getIncomeBracket().getValue().toLowerCase().replace(" ", "-");
            this.incomeBracket.getStyleClass().add("income-bracket-" + bracketLevel);
        }
    }
}

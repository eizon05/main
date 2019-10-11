package seedu.address.ui.itinerary;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.itinerary.events.EnterCreateEventCommand;
import seedu.address.model.Model;
import seedu.address.model.itinerary.event.Event;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.Page;
import seedu.address.ui.template.PageWithSidebar;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventsPage extends Page<AnchorPane> {

    private static final String FXML = "itinerary/events/EventsPage.fxml";

    @FXML
    private VBox eventCardContainer;

    @FXML
    private Label nameLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label endDateLabel;

    @FXML
    private Label destinationLabel;

    @FXML
    private Label inventoryLabel;

    @FXML
    private Label totalBudgetLabel;

    @FXML
    private Label bookingLabel;


    public EventsPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        eventCardContainer.getChildren().clear();
        List<Event> events = model.getPageStatus().getDay().getEventList().internalUnmodifiableList;

        List<Node> eventCards = IntStream.range(0, events.size())
                .mapToObj(i -> Index.fromZeroBased(i))
                .map(index -> {
                    EventCard eventCard = new EventCard(events.get(index.getZeroBased()), index);
                    return eventCard.getRoot();
                }).collect(Collectors.toList());
        eventCardContainer.getChildren().addAll(FXCollections.observableArrayList(eventCards));
    }

    @FXML
    private void handleAddEvent() {
        mainWindow.executeGuiCommand(EnterCreateEventCommand.COMMAND_WORD);
    }

    @FXML
    private void handlePreferences() {
        mainWindow.executeGuiCommand(EnterPrefsCommand.COMMAND_WORD);
    }

}
